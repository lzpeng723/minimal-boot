//package com.lzpeng.framework.model;
//
//import lombok.Data;
//import org.springframework.data.annotation.*;
//import org.springframework.data.mongodb.config.EnableMongoAuditing;
//
//import java.io.Serializable;
//import java.util.Date;
//
///**
// * @date: 2020/3/9
// * @time: 14:20
// * @author:   李志鹏
// */
//@Data
//@EnableMongoAuditing
//public abstract class BaseMongoEntity implements Serializable {
//    @Id
//    private String id;
//    @CreatedDate
//    private Date createTime;
//    @LastModifiedDate
//    private Date updateTime;
//    @CreatedBy
//    private String createBy;
//    @LastModifiedBy
//    private String updateBy;
//}
