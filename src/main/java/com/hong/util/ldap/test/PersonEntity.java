package com.hong.util.ldap.test;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Entry(objectClasses = {
        "top", "person", "organizationalperson", "inetorgperson", "inetuser",
        "posixaccount", "krbprincipalaux", "krbticketpolicyaux", "ipaobject",
        "ipasshuser", "ipaSshGroupOfPubKeys", "mepOriginEntry"})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {

    @Id
    @JsonIgnore // 必写
    private Name distinguishedName;
    private String uid;
    private String userpassword;
    private String homeDirectory;
    private String initials;
    private String givenName;
    private String gecos;
    private String displayName;
    private String loginShell;
    private String cn;
    private String sn;
    private String krbPrincipalName;
    private String krbCanonicalName;
    private String krbPwdPolicyReference;
    private String memberOf;
    private String uidNumber;
    private String gidNumber;




    /*@Id
    @JsonIgnore // 必写
    private Name distinguishedName;

    *//* 登录账号 *//*
     *//*@Attribute(name = "sAMAccountName")
    private String loginName;*//*

     *//* 用户姓名 *//*
    @Attribute(name = "cn")
    private String userName;

    *//*  邮箱 *//*
    @Attribute(name = "mail")
    private String email;

    @Attribute(name = "sn")
    private String sn;

    *//*@Attribute(name = "dn")
    private String dn;*//*

    @Attribute(name = "uid")
    private String uid;

    @Attribute(name = "givenname")
    private String givenName;

    // extend
    *//* Full Name(姓+名) *//*
    @Attribute(name = "description")
    private String description;

    *//* 登录密码 *//*
    @Attribute(name = "userPassword")
    private String userPassword;

    @Attribute(name = "loginShell")
    private String loginShell;

    *//* posixAccount要求该属性 *//*
    @Attribute(name = "homeDirectory")
    private String homeDirectory;

    *//* posixAccount要求该属性 *//*
    @Attribute(name = "uidNumber")
    private String uidNumber;

    *//* posixAccount要求该属性 *//*
    @Attribute(name = "gidNumber")
    private String gidNumber;

    *//**
     * commonName（通用名称）用于指定对象的全名，通常用于表示人员、组织单位等实体的名称。
     * cn的值通常是该对象在目录中的唯一名称。
     *//*
    // @DnAttribute(value = "cn", index = 1)
    @DnAttribute(value = "cn")
    private String cn;

    public PersonEntity(String email, String uid) {
        this.email = email;
        this.uid = uid;
    }*/


}
