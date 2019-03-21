package com.family.mgr.pojo;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: yuandh
 * @Description:
 * @Date: Created in 14:51 2019/3/21
 * @Modified By:
 */
@Data
@Table(name = "family_user")
public class UserPojo {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private String age;

    @Column(name = "id_cord")
    private String idCord;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private String status;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "update_time")
    private String updateTime;



}
