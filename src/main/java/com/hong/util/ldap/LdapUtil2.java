package com.hong.util.ldap;

import lombok.extern.slf4j.Slf4j;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Ldap工具，所有方法可用
 * https://blog.csdn.net/qq271003351/article/details/119115630
 */
@Slf4j
public class LdapUtil2 {
    private static DirContext ctx;
    // LDAP服务器端口默认为389,LDAPS 默认 636
    private static final String LDAP_URL = "ldaps://sdp246.hadoop.com:636";
    // LDAP驱动
    private static final String LDAP_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
    // private static final String ROOT = "dc=freeipa,dc=yesall,dc=com,dc=cn";
    private static final String ROOT = "dc=hadoop,dc=com";
    private static final String admin = "admin";
    private static final String pass = "mima123456";

    static {
        getLoginContext(admin, pass);
    }

    /**** 测试 ****/
    public static void main(String[] args) {

        // getLoginContext("jiaoldap","12345678");
        // addUser("jiaoldap2", "12345678");

        modifyUserSn("jiaoldap", "JiaoLdap-modify");

        // delete("jiaoldap");
        // search();
    }

    // 通过连接LDAP服务器对用户进行认证，返回LDAP对象
    public static DirContext getLoginContext(String user, String password) {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");  // none不进行认证  simple 使用弱的任何证方式（明文密码）
        env.put(Context.SECURITY_CREDENTIALS, password);
        //用户名称，cn,ou,dc 分别：用户，组，域
        env.put(Context.SECURITY_PRINCIPAL, "uid=" + user + ",cn=users,cn=accounts,dc=hadoop,dc=com");
        env.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_FACTORY);
        env.put(Context.PROVIDER_URL, LDAP_URL);
        // cn=属于哪个组织结构名称，ou=某个组织结构名称下等级位置编号
        try {
            ctx = new InitialDirContext(env);
            System.out.println("认证成功");
        } catch (javax.naming.AuthenticationException e) {
            System.out.println("认证失败" + e.getMessage());
        } catch (Exception e) {
            System.out.println("认证出错:" + e.getMessage());
        }
        return ctx;
    }

    public static void addUser(String username, String password) {
        try {
            BasicAttributes attrs = new BasicAttributes();

            BasicAttribute objclassSet = new BasicAttribute("objectclass");
            objclassSet.add("top");
            objclassSet.add("person");
            objclassSet.add("organizationalperson");
            objclassSet.add("inetorgperson");
            objclassSet.add("inetuser");
            objclassSet.add("posixaccount");
            objclassSet.add("krbprincipalaux");
            objclassSet.add("krbticketpolicyaux");
            objclassSet.add("ipaobject");
            objclassSet.add("ipasshuser");
            objclassSet.add("ipaSshGroupOfPubKeys");
            objclassSet.add("mepOriginEntry");
            attrs.put(objclassSet);

            BasicAttribute pass = new BasicAttribute("userpassword", password);
            attrs.put(pass);
            /*attrs.put("uidNumber", "1683900001");
            attrs.put("gidNumber", "1683900001");*/
            attrs.put("homeDirectory", "/home/" + username);
            attrs.put("initials", username);
            attrs.put("givenName", username);
            attrs.put("gecos", username);
            attrs.put("displayName", username);
            attrs.put("loginShell", "/bin/sh");
            attrs.put("uid", username);
            attrs.put("cn", username);
            attrs.put("sn", username);
            attrs.put("krbPrincipalName", username + "@HADOOP.COM");
            attrs.put("krbCanonicalName", username + "@HADOOP.COM");
            attrs.put("krbPwdPolicyReference", "cn=ipausers,cn=HADOOP.COM.CN,cn=kerberos,dc=hadoop,dc=com");
            attrs.put("memberOf", "cn=ipausers,cn=groups,cn=accounts,dc=hadoop,dc=com");

            ctx.createSubcontext("uid=" + username + ",cn=users,cn=accounts," + ROOT, attrs);
            log.info("");
        } catch (Exception e) {
            System.out.println("Exception in add():" + e);
        }
        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void modifyUserSn(String username, String sn) {
        try {
            // 创建 ModificationItem 数组
            ModificationItem[] mods = new ModificationItem[1];
            // 创建 ModificationItem 对象，并设置操作类型和属性值
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("sn", sn));

            // 调用 modifyAttributes() 方法进行修改
            ctx.modifyAttributes("uid=" + username + ",cn=users,cn=accounts," + ROOT, mods);

            log.info("更新成功");
        } catch (Exception e) {
            System.out.println("Exception in modify():" + e);
        }
        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void delete(String userName) {
        try {
            ctx.destroySubcontext("uid=" + userName + ",cn=users,cn=accounts," + ROOT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
    }

    public static void search() {
        try {
            //查询
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
            // constraints.setSearchScope(SearchControls.ONELEVEL_SCOPE);
            // 查询所有用户
            NamingEnumeration<SearchResult> en = ctx.search(ROOT, "(uid=jiao007)", constraints);
            // NamingEnumeration<SearchResult> en = ctx.search(ROOT, "objectclass = ipatokenOTPConfig", constraints);
            // NamingEnumeration<SearchResult> en = ctx.search(ROOT, "(objectClass=*)", constraints);
            // NamingEnumeration en = ctx.search(ROOT, "ipatokenOTPkey=5f9795b7-019a-4e44-852b-a6a434817b7d", constraints);
            // NamingEnumeration en = ctx.search(ROOT, "description=*", constraints);
            while (en != null && en.hasMoreElements()) {
                System.out.println();
                SearchResult obj = en.nextElement();

                if (obj == null) {
                    System.out.println(obj);
                    continue;
                }

                System.out.println("name:" + obj.getName());
                Attributes attrs = obj.getAttributes();
                if (attrs == null) {
                    System.out.println("No   attributes ");
                    continue;
                }

                NamingEnumeration ae = attrs.getAll();
                while (ae.hasMoreElements()) {
                    Attribute attr = (Attribute) ae.next();
                    String attrId = attr.getID();
                    Enumeration vals = attr.getAll();

                    while (vals.hasMoreElements()) {
                        System.out.print(attrId + ":   ");
                        Object o = vals.nextElement();
                        if (o instanceof byte[]) {
                            System.out.println(new String((byte[]) o));
                        } else {
                            /*if (attrId.equals("ipatokenOTPkey")) {
                                System.out.println(StringUtility.base64Encode(o.toString().getBytes()));
                            }*/
                            System.out.println(o);
                        }
                    }
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ctx != null) {
            try {
                ctx.close();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }

    }
}