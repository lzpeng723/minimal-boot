package com.lzpeng.project.sys.service;

import cn.hutool.core.bean.BeanUtil;
import com.lzpeng.framework.util.TreeEntityUtil;
import com.lzpeng.framework.web.config.UserAuditor;
import com.lzpeng.project.sys.domain.Menu;
import com.lzpeng.project.sys.domain.MenuMeta;
import com.lzpeng.project.sys.domain.MenuType;
import com.lzpeng.project.sys.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单 业务层
 * @date: 2020-2-20
 * @time: 23:23:26
 * @author: 李志鹏
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class MenuService extends AbstractMenuService {

    @Autowired
    private UserAuditor userAuditor;

    /**
     * 得到用户路由菜单
     * @return
     */
    public List<Menu> getRouters() {
        UserDetails userDetails = userAuditor.getCurrentUser();
        if (userDetails instanceof User) {
            User user = (User) userDetails;
            List<Menu> menus = user.getRoles().stream()
                    .flatMap(role -> role.getMenus().stream())
                    .distinct()
                    // 去掉功能类权限菜单
                    .filter(menu -> !menu.getType().equals(MenuType.FUNCTION))
                    .peek(menu -> {
                        MenuMeta meta = new MenuMeta();
                        // 拷贝元数据信息
                        BeanUtil.copyProperties(menu, meta);
                        // 设置元数据信息
                        menu.setMeta(meta);
                    })
                    .collect(Collectors.toList());
            return TreeEntityUtil.treeData(menus);
        }
        return Arrays.asList();
    }

    /**
     * 获得所有路由菜单
     * 不是功能(按钮)且不是外链的菜单
     * @return
     */
    public List<Menu> getAllRouters() {
        List<Menu> menus = menuRepository.findAllByTypeNotAndFrameIsFalse(MenuType.FUNCTION);
        return TreeEntityUtil.treeData(menus);
    }
}
