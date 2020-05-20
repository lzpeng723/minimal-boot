package com.lzpeng.project.sys.repository;

import com.lzpeng.project.sys.domain.Menu;
import com.lzpeng.framework.web.repository.TreeRepository;
import com.lzpeng.project.sys.domain.MenuType;
import io.swagger.annotations.Api;

import java.util.List;

/**
 * 菜单 数据层
 * @date: 2020-2-20
 * @time: 23:46:54
 * @author: 李志鹏
 */
@Api(tags = "菜单 Entity")
public interface MenuRepository extends TreeRepository<Menu> {

    /**
     * 查找不是此类型且不是外链的菜单
     * @param menuType 菜单类型
     * // @param frame 是否外链
     * @return
     */
    List<Menu> findAllByTypeNotAndFrameIsFalse(MenuType menuType);

}
