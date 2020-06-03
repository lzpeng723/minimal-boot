# 基类介绍
## Entity
### BaseEntity
BaseEntity: 所有实体的基类
``` java
public class BaseEntity implements Serializable {

	// id 主键
    private String id;

	// 是否已启用
    private Boolean enabled;

	// 创建时间
    private Date createTime;

	// 最后更新时间
    private Date updateTime;

	// 创建者 id
    private String createBy;

	// 最后更新者 id
    private String updateBy;

	// 版本号
    private Long version;

	// 备注
    private String remark;

}
```
### TreeEntity
TreeEntity: 树形结构实体的基类
``` java
public abstract class TreeEntity<T extends TreeEntity<T>> extends BaseEntity {

    // 顺序号
    private Integer orderNum;

    // 父节点
    private T parent;

    // 子节点
    private List<T> children = new ArrayList<>();

    // 父节点 Id 不存数据库,接收前台参数使用
    private String parentId;

}
```
### LeftTreeRightTableEntity
LeftTreeRightTableEntity: 左树右表结构实体的基类
``` java
public class LeftTreeRightTableEntity<T extends TreeEntity<T>> extends BaseEntity {

    // 左树节点 Id 不存数据库,接收前台参数使用
    private String treeId;

    // 左树节点
    private T tree;
}

```

## Repository
### BaseRepository
BaseRepository: 所有Repository的基类
``` java
@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, String>, JpaSpecificationExecutor<T>, QuerydslPredicateExecutor<T> {

    // 得到不在 ids 列表中的实体
    List<T> findAllByIdNotIn(Iterable<String> ids);

    // 更新单据状态(由子类实现)
    default int updateEnabled(@Param("id") String id, @Param("enabled") Boolean enabled) {
        Class<?> userClass = ClassUtils.getUserClass(getClass());
        throw new RuntimeException("请在" + userClass.getName() + "中实现 updateEnabled(String id,Boolean enabled) 方法");
    }

}
```
### TreeRepository
TreeRepository: 树形结构Repository的基类
``` java
@NoRepositoryBean
public interface TreeRepository<T extends TreeEntity<T>> extends BaseRepository<T> {

    // 返回树形结构并排序
    List<T> findByParentNullOrderByOrderNum();
}
```
### LeftTreeRightTableRepository
LeftTreeRightTableRepository: 左树右表结构Repository的基类
``` java
@NoRepositoryBean
public interface LeftTreeRightTableRepository<Tree extends TreeEntity<Tree>, Entity extends LeftTreeRightTableEntity<Tree>> extends BaseRepository<Entity> {
}

```
## Service
### BaseService
BaseService: 所有业务层的基类
```java

```

## Controller