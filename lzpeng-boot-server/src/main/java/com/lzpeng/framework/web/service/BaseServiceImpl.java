package com.lzpeng.framework.web.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.TypeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lzpeng.common.response.QueryResult;
import com.lzpeng.common.utils.ExcelUtils;
import com.lzpeng.framework.domain.BaseEntity;
import com.lzpeng.framework.model.BatchModel;
import com.lzpeng.framework.util.BeanUtils;
import com.lzpeng.framework.web.repository.BaseRepository;
import com.lzpeng.project.tool.domain.TableInfo;
import com.lzpeng.project.tool.service.TableInfoService;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * 基础的 service
 * @date: 2020/2/4
 * @time: 15:14
 * @author: 李志鹏
 */
@Transactional(rollbackOn = Exception.class)
public abstract class BaseServiceImpl<Entity extends BaseEntity> {

    /**
     * 默认路径
     */
    protected static final String USER_DIR = System.getProperty("user.dir");

    /**
     * 临时文件路径
     */
    private static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    @Autowired
    protected ObjectMapper objectMapper;
    /**
     * 不能使用@Autowired 有 bug 不能泛型注入
     */
    protected BaseRepository<Entity> baseRepository;
    /**
     * Querydsl 处理复杂查询
     */
    //@Autowired
    //protected JPAQueryFactory jpaQueryFactory;
    /**
     * 数据字典Service
     */
    @Autowired
    private TableInfoService tableInfoService;
    /**
     * 文件上传下载Service
     */
    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 查询条件
     * 模糊匹配
     * 忽略空值
     * 忽略大小写
     * 忽略字段，即不管password是什么值都不加入查询条件
     */
    private ExampleMatcher matcher = ExampleMatcher.matching()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withIgnoreNullValues()
            .withIgnoreCase()
            .withIgnorePaths("password");

    /**
     * 获取数据字典
     *
     * @return
     */
    public TableInfo getTableInfo() {
        Class<Entity> clazz = getEntityClass();
        return tableInfoService.findByClassName(clazz.getName());
    }


    /**
     * 保存
     *
     * @param entity
     * @return
     */
    public Entity save(Entity entity) {
        if (beforeSave(entity)) {
            entity = baseRepository.save(entity);
            afterSave(entity);
            return entity;
        } else {
            throw new RuntimeException("保存失败");
        }
    }

    /**
     * 保存列表
     *
     * @param entities
     * @return
     */
    public List<Entity> saveAll(Iterable<Entity> entities) {
        Assert.notNull(entities, "Entities must not be null!");
        List<Entity> result = new ArrayList<Entity>();
        for (Entity entity : entities) {
            result.add(save(entity));
        }
        return result;
    }


    /**
     * 删除
     *
     * @param id
     */
    public void delete(String id) {
        baseRepository.deleteById(id);
    }

    /**
     * 删除所有
     */
    public void deleteAll() {
        baseRepository.deleteAll();
    }

    /**
     * 更新
     *
     * @param id
     * @param model
     * @return
     */
    public Entity update(String id, Entity model) {
        Optional<Entity> optional = baseRepository.findById(id);
        if (optional.isPresent()) {
            Entity entity = optional.get();
            BeanUtils.convertEmptyCollectionToNull(model);
            BeanUtil.copyProperties(model, entity, CopyOptions.create().setIgnoreNullValue(true));
            return save(entity);
        }
        return null;
    }

    /**
     * Jpa Example分页查询
     * @param page
     * @param size
     * @return
     */
    public QueryResult<Entity> query(int page, int size) {
        return query(page, size, (Entity)null);
    }

    /**
     * Jpa Example分页查询
     * @param page
     * @param size
     * @param model
     * @return
     */
    public QueryResult<Entity> query(int page, int size, Entity model) {
        // 处理不正确的页码
        page = optimizePage(page);
        // 处理不正确的每页数据量
        size = optimizeSize(size);
        // 得到分页对象
        Pageable pageable = getPageable(page, size);
        Page<Entity> pageResult;
        if (model == null) {
            // 没有传查询条件
            pageResult = baseRepository.findAll(pageable);
        } else {
            // 将空白字符 或 undefined 设置为 null
            BeanUtils.convertBlankToNull(model);
            pageResult = baseRepository.findAll(Example.of(model, matcher), pageable);
        }
        // 执行查询后操作
        return new QueryResult(pageResult.getContent(), pageResult.getTotalElements(), pageResult.getNumber() + 1, pageResult.getTotalPages());
    }

    /**
     * QueryDsl分页查询
     * @param page
     * @param size
     * @param predicate
     * @return
     */
    public QueryResult<Entity> query(int page, int size, Predicate predicate) {
        // 处理不正确的页码
        page = optimizePage(page);
        // 处理不正确的每页数据量
        size = optimizeSize(size);
        // 得到分页对象
        Pageable pageable = getPageable(page, size);
        Page<Entity> pageResult;
        if (predicate == null) {
            // 没有传查询条件
            pageResult = baseRepository.findAll(pageable);
        } else {
            // 将空白字符 或 undefined 设置为 null
            // BeanUtils.convertBlankToNull(model);
            pageResult = baseRepository.findAll(predicate, pageable);
        }
        // 执行查询后操作
        return new QueryResult(pageResult.getContent(), pageResult.getTotalElements(), pageResult.getNumber() + 1, pageResult.getTotalPages());
    }

    /**
     * 复杂条件查询
     *
     * @return
     */
    public Entity findOne(Specification<Entity> specification) {
        Optional<Entity> optional = baseRepository.findOne(specification);
        return optional.orElse(null);
    }

    /**
     * 查询单个实体
     *
     * @return
     */
    public Entity findOne(Entity model) {
        Optional<Entity> optional = baseRepository.findOne(Example.of(model));
        return optional.orElse(null);
    }

    /**
     * 复杂条件查询
     *
     * @return
     */
    public List<Entity> findAll(Specification<Entity> specification) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return baseRepository.findAll(specification, sort);
    }

    /**
     * 查询所有
     * @param predicate query dsl 查询条件
     * @return
     */
    public List<Entity> findAll(Predicate predicate) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        Iterable<Entity> iterable = baseRepository.findAll(predicate, sort);
        return ListUtil.toList(iterable);
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<Entity> findAll() {
        return findAll((Entity) null);
    }

    /**
     * 查询所有
     *
     * @param model
     * @return
     */
    public List<Entity> findAll(Entity model) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        if (model == null) {
            // 没有传查询条件
            List<Entity> entities = baseRepository.findAll(sort);
            // afterFindAll(entities);
            return entities;
        } else {
            // 将空白字符 或 undefined 设置为 null
            BeanUtils.convertBlankToNull(model);
            List<Entity> entities = baseRepository.findAll(Example.of(model, matcher), sort);
            // afterFindAll(entities);
            return entities;
        }
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Entity findById(String id) {
        Optional<Entity> optional = baseRepository.findById(id);
        if (optional.isPresent()) {
            Entity entity = optional.get();
            // afterFind(entity);
            return entity;
        }
        return null;
    }


    /**
     * 从 json 导入实体到对象
     *
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    public List<Entity> readDataFromJson(String json) throws JsonProcessingException {
        List<Entity> list = objectMapper.readValue(json, new TypeReference<List<Entity>>() {
        });
        return list;
    }

    /**
     * 从 excel 导入实体到对象
     *
     * @param inputStream excel 文件流
     * @return
     */
    public List<Entity> readDataFromExcel(InputStream inputStream) {
        List<Entity> list = ExcelUtils.readDataFromStream(inputStream, getEntityClass());
        return list;
    }


    /**
     * 从json导入到数据库
     *
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    public List<Entity> importDataFromJson(String json) throws JsonProcessingException {
        List<Entity> collection = readDataFromJson(json);
        return saveAll(collection);
    }


    /**
     * 从 excel导入到数据库
     *
     * @param inputStream excel 文件流
     * @return
     */
    public List<Entity> importDataFromExcel(InputStream inputStream) {
        List<Entity> collection = readDataFromExcel(inputStream);
        return saveAll(collection);
    }
    /**
     * 上传的文件
     * @param file
     * @return
     */
    public List<Entity> importData(MultipartFile file) throws IOException {
        List<Entity> list = null;
        String originalFilename = file.getOriginalFilename();
        String extName = FileUtil.extName(originalFilename.toLowerCase());
        switch (extName) {
            case "json":
                list = importDataFromJson(IoUtil.read(file.getInputStream(), Charset.defaultCharset()));
                break;
            case "xls":
            case "xlsx":
                list = importDataFromExcel(file.getInputStream());
                break;
            case "xml":
                // TODO 导入xml
                break;
            default:
                throw new RuntimeException("不支持的文件类型: " + extName);
        }
        return list;
    }

    /**
     * 导出信息到文件
     * @param ids
     * @param response
     */
    public void exportData(List<String> ids, HttpServletResponse response) throws IOException {
        InputStream inputStream = exportDataToExcel(ids);
        String fileName = getEntityClass().getSimpleName() + System.currentTimeMillis() + ".xlsx";
        fileInfoService.downloadFile(fileName, inputStream, response);
    }

    /**
     * 导出到Excel
     *
     * @param ids 单据 Id
     * @return
     */
    public InputStream exportDataToExcel(List<String> ids) throws IOException {
        List<Entity> list = CollectionUtils.isEmpty(ids) ? findAll() : findAllById(ids);
        Class<Entity> clazz = getEntityClass();
        String tempDir = getTempDir();
        Path path = Paths.get(tempDir, clazz.getSimpleName() + ".xlsx");
        ExcelUtils.writeDataToFile(list, path.toFile());
        InputStream inputStream = Files.newInputStream(path);
        FileUtil.del(tempDir);
        return inputStream;
    }


    /**
     * 批量增删改查
     *
     * @param batch 批量操作的数据
     * @return
     */
    public Object batch(BatchModel<Entity> batch) {
        if (batch.getDelete() != null) {
            List<Entity> entities = baseRepository.findAllById(Arrays.asList(batch.getDelete()));
            baseRepository.deleteInBatch(entities);
            return "批量删除成功";
        }
        if (batch.getUpdate() != null) {
            for (Map.Entry<String, Entity> entry : batch.getUpdate().entrySet()) {
                update(entry.getKey(), entry.getValue());
            }
            return "批量修改成功";
        }
        if (batch.getCreate() != null) {
            saveAll(Arrays.asList(batch.getCreate()));
            return "批量修改成功";
        }
        return null;
    }

    /**
     * 根据 id 序列查找实体
     *
     * @param ids
     * @return
     */
    public List<Entity> findAllById(Iterable<String> ids) {
        return baseRepository.findAllById(ids);
    }

    /**
     * 得到不在 ids 列表中的实体
     *
     * @param ids id 列表
     * @return
     */
    public List<Entity> findAllByIdNotIn(Iterable<String> ids) {
        return baseRepository.findAllByIdNotIn(ids);
    }


    /**
     * 启用
     *
     * @param id
     * @return
     */
    public int enabled(String id) {
        return baseRepository.updateEnabled(id, true);
    }

    /**
     * 禁用
     * @param id
     * @return
     */
    public int disabled(String id) {
        return baseRepository.updateEnabled(id, false);
    }

    /**
     * 计数
     * @return
     */
    public long count() {
        return baseRepository.count();
    }

    /**
     * 根据 model 模糊技计数
     * @param model
     * @return
     */
    public long count(Entity model) {
        if (model == null) {
            // 没有传查询条件
            return count();
        } else {
            // 将空白字符 或 undefined 设置为 null
            BeanUtils.convertBlankToNull(model);
            return baseRepository.count(Example.of(model, matcher));
        }
    }

    /**
     * 计数
     * @param predicate query DSL 查询条件
     * @return
     */
    public long count(Predicate predicate) {
        return baseRepository.count(predicate);
    }


    /**
     * 得到泛型参数
     *
     * @return
     */
    protected Class<Entity> getEntityClass() {
        Type type = TypeUtil.getTypeArgument(getClass());
        if (type != null && type instanceof Class) {
            return (Class<Entity>) type;
        }
        return null;
    }

    /**
     * 得到临时文件夹
     *
     * @return
     */
    protected String getTempDir() {
        return Paths.get(USER_DIR, String.valueOf(System.nanoTime())).toString();
    }


    /**
     * 保存前操作
     *
     * @param entity
     * @return
     */
    protected boolean beforeSave(Entity entity) {
        return true;
    }

    /**
     * 保存前操作
     *
     * @param entities
     * @return
     */
    protected boolean beforeSaveAll(Collection<Entity> entities) {
        for (Entity entity : entities) {
            if (!beforeSave(entity)) {
                return false;
            }
        }
        return true;
    }
    /**
     * 保存后操作
     *
     * @param entity
     */
    protected void afterSave(Entity entity) {

    }

    /**
     * 得到JPA分页对象
     * @param page 第几页
     * @param size 每页几条数据
     * @return
     */
    private Pageable getPageable(int page, int size) {
        //Pageable 和 Page 接口介绍: https://blog.csdn.net/u011781521/article/details/74539330
        Sort sort = Sort.by(Sort.Direction.DESC, "createTime");
        return PageRequest.of(page, size, sort);
    }

    /**
     * 优化每页数据
     * @param size
     * @return
     */
    private int optimizeSize(int size) {
        if (size <= 0) {
            // 如果传入size 不合法则设置为 20
            size = 20;
        }
        return size;
    }

    /**
     * 优化页码
     * @param page
     * @return
     */
    private int optimizePage(int page) {
        if (page <= 0) {
            page = 1;
        }
        //为了适应数据库,将页码减1
        page = page - 1;
        return page;
    }
}
