package com.lzpeng.project.sys.service;

import com.lzpeng.common.response.QueryResult;
import com.lzpeng.framework.domain.BaseEntity;
import com.lzpeng.framework.util.TreeEntityUtil;
import com.lzpeng.project.sys.domain.Menu;
import com.lzpeng.project.sys.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色 业务层
 * @date: 2020-2-20
 * @time: 23:23:26
 * @author: 李志鹏
 */
@Service
@Transactional(rollbackOn = Exception.class)
public class RoleService extends AbstractRoleService {

    @Autowired
    private MenuService menuService;

    /**
     * 保存前操作
     * @param role
     * @return
     */
    @Override
    protected boolean beforeSave(Role role) {
        Collection<Menu> menus = role.getMenus();
        menus = TreeEntityUtil.flatData(menus);
        role.setMenus(menus);
        return true;
    }

    /**
     * 分配权限
     * @param id 角色id
     * @param permissions 权限id数组
     * @return
     */
    public Role setPermissions(String id, String[] permissions) {
        Role role = findById(id);
        List<Menu> menus = menuService.findAllById(Arrays.asList(permissions));
        role.setMenus(menus);
        role = save(role);
        return role;
    }

    @Override
    public QueryResult<Role> query(int page, int size, Role model) {
        QueryResult<Role> result = super.query(page, size, model);
        for (Role role : result.getList()) {
            Collection<Menu> menus = role.getMenus();
            menus = TreeEntityUtil.flatData(menus);
            role.setMenus(menus);
        }
        return result;
    }

    /**
     * 得到角色未拥有的权限列表
     * @param roleId
     * @return
     */
    public List<Menu> noPermissions(String roleId) {
        Role role = findById(roleId);
        if (role.getMenus().isEmpty()) {
            return menuService.findAll();
        }
        List<String> menuIds = role.getMenus().stream().map(BaseEntity::getId).distinct().collect(Collectors.toList());
        List<Menu> menus = menuService.findAllByIdNotIn(menuIds);
        menus = TreeEntityUtil.flatData(menus);
        return menus;
    }
}
