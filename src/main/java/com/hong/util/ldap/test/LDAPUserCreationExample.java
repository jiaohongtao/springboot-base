package com.hong.util.ldap.test;

import com.alibaba.fastjson.JSONObject;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.support.LdapUtils;

import javax.naming.Context;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import java.util.Hashtable;

public class LDAPUserCreationExample {

    public static void main(String[] args) {

//        addPerson();
        deletePerson();
    }

    public static void create() {
        // LDAP连接信息
        String ldapUrl = "ldaps://sdp246.hadoop.com:636";
        // String adminDn = "cn=admin,dc=hadoop,dc=com"; // LDAP管理员用户（具有适当的权限）
        String adminDn = "uid=admin,cn=users,cn=accounts,dc=hadoop,dc=com"; // LDAP管理员用户（具有适当的权限）
        String adminPassword = "mima123456";

        // 用户信息参数
        String userCn = "John Doe";
        String userSn = "Doe";
        String userUid = "johndoe";
        String userPassword = "password123";
        String userEmail = "johndoe@hadoop.com";
        String userOu = "users"; // 用户所属的组织单位或部门

        // 创建LDAP连接上下文
        Hashtable<String, Object> env = new Hashtable<>();
        // Map<String, String> env = new HashMap<>();
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
//        env.put(Context.SECURITY_AUTHENTICATION, "kerberos");
        env.put(Context.SECURITY_PRINCIPAL, adminDn);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);

        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setBase("dc=hadoop,dc=com"); // 设置基准DN
        contextSource.setUrl(ldapUrl);
        contextSource.setUserDn(adminDn);
        contextSource.setPassword(adminPassword);
        contextSource.setPooled(true);
        contextSource.setBaseEnvironmentProperties(env);
        contextSource.afterPropertiesSet();

        LdapTemplate ldapTemplate = new LdapTemplate(contextSource);

        // 创建用户条目的属性集合
        Attributes attributes = new BasicAttributes();
        Attribute objectClassAttribute = new BasicAttribute("objectclass");
        objectClassAttribute.add("top");
        objectClassAttribute.add("person");
        objectClassAttribute.add("inetOrgPerson");
        attributes.put(objectClassAttribute);
        attributes.put(new BasicAttribute("cn", userCn));
        attributes.put(new BasicAttribute("sn", userSn));
        attributes.put(new BasicAttribute("uid", userUid));
        attributes.put(new BasicAttribute("userPassword", userPassword));
        attributes.put(new BasicAttribute("mail", userEmail));
        attributes.put(new BasicAttribute("ou", userOu));

        // uid=admin,cn=users,cn=accounts,dc=hadoop,dc=com
        // 创建用户条目的DN
        DistinguishedName dn = new DistinguishedName();
        // dn.add("ou", userOu); // 设置用户所属的组织单位或部门
        dn.add("dc", "com");
        dn.add("dc", "hadoop");
        dn.add("cn", "accounts");
        dn.add("cn", userOu); // 设置用户的通用名称（cn）
        dn.add("uid", userUid); // 设置用户的通用名称（cn）

        // 将用户条目绑定到LDAP目录
        ldapTemplate.bind(dn, null, attributes);

        System.out.println("LDAP用户创建成功");
    }

    /**
     * 添加的用户不能被找到
     */
    @Deprecated
    public static void addPerson() {
        // LDAP连接信息
        String ldapUrl = "ldaps://sdp246.hadoop.com:636";
        // String adminDn = "cn=admin,dc=hadoop,dc=com"; // LDAP管理员用户（具有适当的权限）
        String adminDn = "uid=admin,cn=users,cn=accounts,dc=hadoop,dc=com"; // LDAP管理员用户（具有适当的权限）
        String adminPassword = "mima123456";

        // 用户信息参数
        String username = "jiaotest001";
        String userPassword = "12345678";

        // 创建LDAP连接上下文
        Hashtable<String, Object> env = new Hashtable<>();
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adminDn);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);

        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setBase("dc=hadoop,dc=com"); // 设置基准DN
        contextSource.setUrl(ldapUrl);
        contextSource.setUserDn(adminDn);
        contextSource.setPassword(adminPassword);
        contextSource.setPooled(true);
        contextSource.setBaseEnvironmentProperties(env);
        contextSource.afterPropertiesSet();

        LdapTemplate ldapTemplate = new LdapTemplate(contextSource);

        String uidNumber = "1683900002", gidNumber = "1683900002";

        PersonEntity personEntity = new PersonEntity(LdapUtils.newLdapName("uid=" + username), username, userPassword,
                "/home" + username, username, username, username, username, "/bin/sh",
                username, username, username + "@HADOOP.COM", username + "@HADOOP.COM",
                "cn=ipausers,cn=HADOOP.COM.CN,cn=kerberos,dc=hadoop,dc=com",
                "cn=ipausers,cn=groups,cn=accounts,dc=hadoop,dc=com", uidNumber, gidNumber);

        JSONObject.toJSONString(personEntity);

        ldapTemplate.create(personEntity);
    }


    public static void deletePerson() {
        // LDAP连接信息
        String ldapUrl = "ldaps://sdp246.hadoop.com:636";
        // String adminDn = "cn=admin,dc=hadoop,dc=com"; // LDAP管理员用户（具有适当的权限）
        String adminDn = "uid=admin,cn=users,cn=accounts,dc=hadoop,dc=com"; // LDAP管理员用户（具有适当的权限）
        String adminPassword = "mima123456";

        // 用户信息参数
        String username = "jiaotest001";
        String userPassword = "password123";

        // 创建LDAP连接上下文
        Hashtable<String, Object> env = new Hashtable<>();
        env.put(Context.PROVIDER_URL, ldapUrl);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, adminDn);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);

        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setBase("dc=hadoop,dc=com"); // 设置基准DN
        contextSource.setUrl(ldapUrl);
        contextSource.setUserDn(adminDn);
        contextSource.setPassword(adminPassword);
        contextSource.setPooled(true);
        contextSource.setBaseEnvironmentProperties(env);
        contextSource.afterPropertiesSet();

        LdapTemplate ldapTemplate = new LdapTemplate(contextSource);

        String uidNumber = "1683900002", gidNumber = "1683900002";

        PersonEntity personEntity = new PersonEntity(LdapUtils.newLdapName("uid=" + username), username, "no-need",
                "/home" + username, username, username, username, username, "/bin/sh",
                username, username, username + "@HADOOP.COM", username + "@HADOOP.COM",
                "cn=ipausers,cn=HADOOP.COM.CN,cn=kerberos,dc=hadoop,dc=com",
                "cn=ipausers,cn=groups,cn=accounts,dc=hadoop,dc=com", uidNumber, gidNumber);

        ldapTemplate.delete(personEntity);
    }
}
