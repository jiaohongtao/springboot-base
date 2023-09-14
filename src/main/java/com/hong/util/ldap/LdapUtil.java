package com.hong.util.ldap;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 1.填写连接信息
 * 2.jdk导入证书(keytool -import -file ca.crt -keystore ./cacerts -alias server)
 * 3.测试连接获取信息
 * href: https://codeleading.com/article/2264795977
 */
public class LdapUtil {

    public static void main(String[] args) throws NamingException {
        // TLS_CACERTDIR   /etc/openldap/certs
        //
        //# Turning this off breaks GSSAPI used with krb5 when rdns = false
        //SASL_NOCANON    on
        //URI ldaps://box101.hadoop.com
        //BASE dc=hadoop,dc=com
        //TLS_CACERT /etc/ipa/ca.crt
        //SASL_MECH GSSAPI


        // String url = "ldaps://box101.hadoop.com";
        String url = "ldaps://box101.hadoop.com:636";
        String basedn = "dc=hadoop,dc=com";  // basedn
        String root = "uid=admin,cn=users,cn=accounts,dc=hadoop,dc=com";  // 用户
        String pwd = "12345678";  // pwd


        url = "ldaps://sdp246.hadoop.com:636";
        basedn = "dc=hadoop,dc=com";  // basedn
        root = "uid=admin,cn=users,cn=accounts,dc=hadoop,dc=com";  // 用户
        pwd = "mima123456";  // pwd

        LdapContext ctx = ldapConnect(url, root, pwd);//集团 ldap认证

        /*List<LdapUser> users = readLdap(ctx, basedn);//获取集团ldap中用户信息
        users.forEach(user -> System.out.println(JSONObject.toJSONString(user)));*/

        // getAll(ctx, basedn);

        // 获取一个，但是出来两个
        /*List<LdapUser> admin = getUser("admin", ctx, basedn);
        admin.forEach(a -> System.out.println(JSONObject.toJSONString(a)));*/

//        addUser(ctx);

        String cn = "jiaoadd0011";
        String dn = "uid=" + cn + ",cn=users,cn=accounts,dc=hadoop,dc=com";
        delete(dn, ctx);
        if (ctx != null) {
            ctx.close();
        }
    }

    public static void addUser(LdapContext ctx) {
        LdapUser ldapUser = new LdapUser("jiaoadd0011", "焦", "1683900001", "12345678",
                "焦", "jiaoadd0011@hadoop.com", "添加的", "1683900001", "1683900001");
        boolean b = addUser(ldapUser, ctx);
        System.out.println(b);
    }

    /**
     * 获取ldap认证
     *
     * @param url
     * @param root
     * @param pwd
     * @return
     */
    public static LdapContext ldapConnect(String url, String root, String pwd) {
        String factory = "com.sun.jndi.ldap.LdapCtxFactory";
        String simple = "simple";
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, factory);
        env.put(Context.PROVIDER_URL, url);
        env.put(Context.SECURITY_AUTHENTICATION, simple);
        env.put(Context.SECURITY_PRINCIPAL, root);
        env.put(Context.SECURITY_CREDENTIALS, pwd);

        env.put(Context.SECURITY_PROTOCOL, "ssl");

        LdapContext ctx = null;
        Control[] connCtls = null;
        try {
            ctx = new InitialLdapContext(env, connCtls);
            System.out.println("认证成功:" + url);
        } catch (javax.naming.AuthenticationException e) {
            System.out.println("认证失败：");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("认证出错：");
            e.printStackTrace();
        }
        return ctx;
    }

    /**
     * 添加组
     *
     * @param lu
     * @param ctx
     * @return
     */
    public static boolean addGoups(LdapUser lu, LdapContext ctx) {
        BasicAttributes attrsbu = new BasicAttributes();
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        objclassSet.add("posixGroup");
        objclassSet.add("top");
        attrsbu.put(objclassSet);
        attrsbu.put("cn", lu.getCn());//显示账号
        attrsbu.put("userPassword", "{crypt}x");//显示
        attrsbu.put("gidNumber", strToint(lu.getCn()));//显示组id
        attrsbu.put("memberUid", lu.getCn());//显示账号
        try {
            String cn = "cn=" + lu.getCn() + ",ou=Group,dc=tcjf,dc=com";
            System.out.println(cn);
            ctx.createSubcontext(cn, attrsbu);
            System.out.println("添加用户group成功");
            return true;
        } catch (Exception e) {
            System.out.println("添加用户group失败");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加用户
     *
     * @param lu
     * @param ctx
     * @return
     */
    public static boolean addUser(LdapUser lu, LdapContext ctx) {
        BasicAttributes attrsbu = new BasicAttributes();
        BasicAttribute objclassSet = new BasicAttribute("objectClass");
        // objclassSet.add("account");
        objclassSet.add("posixAccount");
        objclassSet.add("inetOrgPerson");
        objclassSet.add("top");
        objclassSet.add("shadowAccount");
        attrsbu.put(objclassSet);
        // attrsbu.put("uid", lu.getUid());//显示账号
        attrsbu.put("sn", lu.getSn());//显示姓名
        attrsbu.put("cn", lu.getCn());//显示账号
        attrsbu.put("gecos", lu.getCn());//显示账号
        attrsbu.put("userPassword", lu.getUserPassword());//显示密码
        attrsbu.put("displayName", lu.getDisplayName());//显示描述
        attrsbu.put("mail", lu.getMail());//显示邮箱
        attrsbu.put("homeDirectory", "/home/" + lu.getCn());//显示home地址
        attrsbu.put("loginShell", "/bin/bash");//显示shell方式
        /*attrsbu.put("uidNumber", strToint(lu.getUidNum()));//显示用户id
        attrsbu.put("gidNumber", strToint(lu.getGidNum()));//显示组id*/
        attrsbu.put("uidNumber", lu.getUidNum());//显示用户id
        attrsbu.put("gidNumber", lu.getGidNum());//显示组id

        try {
            // String dn = "uid=" + lu.getCn() + ",ou=People,dc=tcjf,dc=com";
            String dn = "uid=" + lu.getCn() + ",cn=users,cn=accounts,dc=hadoop,dc=com";
            System.out.println(dn);
            ctx.createSubcontext(dn, attrsbu);
            System.out.println("添加用户成功");
            return true;
        } catch (Exception e) {
            System.out.println("添加用户失败");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改属性
     *
     * @param lu
     * @param ctx
     * @return
     */
    public static boolean modifyInformation(LdapUser lu, LdapContext ctx) {
        try {
            ModificationItem[] mods = new ModificationItem[1];
            String dn = "uid=" + lu.getCn() + ",ou=People,dc=tcjf,dc=com";
            /*添加属性*/
            //  Attribute attr0 = new BasicAttribute("description", "测试");
            //  mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE,attr0);

            /*修改属性*/
            Attribute attr0 = new BasicAttribute("userPassword", lu.getUserPassword());
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr0);

            /*删除属性*/
            //  Attribute attr0 = new BasicAttribute("description", "测试");
            //  mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attr0);
            ctx.modifyAttributes(dn, mods);
            System.out.println("修改成功");
            return true;
        } catch (NamingException ne) {
            System.out.println("修改失败");
            ne.printStackTrace();
            return false;
        }

    }

    /**
     * 删除
     *
     * @param dn
     * @param ctx
     * @return
     */
    public static boolean delete(String dn, LdapContext ctx) {
        try {
            ctx.destroySubcontext(dn);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取用户信息
     *
     * @param ctx
     * @param basedn
     * @return
     */
    public static List<LdapUser> getUser(String username, LdapContext ctx, String basedn) {

        List<LdapUser> lm = new ArrayList<>();
        try {
            if (ctx != null) {
                //过滤条件
                String filter = "(&(objectClass=*)(uid=" + username + "))";
                String[] attrPersonArray = {"uid", "userPassword", "displayName", "cn", "sn", "mail", "description"};
                SearchControls searchControls = new SearchControls();//搜索控件
                searchControls.setSearchScope(2);//搜索范围
                searchControls.setReturningAttributes(attrPersonArray);
                //1.要搜索的上下文或对象的名称；2.过滤条件，可为null，默认搜索所有信息；3.搜索控件，可为null，使用默认的搜索控件
                NamingEnumeration<SearchResult> answer = ctx.search(basedn, filter, searchControls);
                while (answer.hasMore()) {
                    SearchResult result = answer.next();
                    NamingEnumeration<? extends Attribute> attrs = result.getAttributes().getAll();
                    LdapUser lu = new LdapUser();
                    while (attrs.hasMore()) {
                        Attribute attr = attrs.next();
                        if ("userPassword".equals(attr.getID())) {
                            Object value = attr.get();
                            lu.setUserPassword(new String((byte[]) value));
                        } else if ("uid".equals(attr.getID())) {
                            lu.setUid(attr.get().toString());
                        } else if ("displayName".equals(attr.getID())) {
                            lu.setDisplayName(attr.get().toString());
                        } else if ("cn".equals(attr.getID())) {
                            lu.setCn(attr.get().toString());
                        } else if ("sn".equals(attr.getID())) {
                            lu.setSn(attr.get().toString());
                        } else if ("mail".equals(attr.getID())) {
                            lu.setMail(attr.get().toString());
                        } else if ("description".equals(attr.getID())) {
                            lu.setDescription(attr.get().toString());
                        }
                    }
                    if (lu.getUid() != null) {
                        lm.add(lu);
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("获取用户信息异常:");
            e.printStackTrace();
        }

        return lm;
    }

    /**
     * 获取用户信息
     *
     * @param ctx
     * @param basedn
     * @return
     */
    public static List<LdapUser> getAll(LdapContext ctx, String basedn) {

        List<LdapUser> lm = new ArrayList<>();
        try {
            if (ctx != null) {
                //过滤条件
                String filter = "(&(objectClass=*)(uid=*))";
                String[] attrPersonArray = {"uid", "userPassword", "displayName", "cn", "sn", "mail", "description"};
                SearchControls searchControls = new SearchControls();//搜索控件
                searchControls.setSearchScope(2);//搜索范围
                searchControls.setReturningAttributes(attrPersonArray);
                //1.要搜索的上下文或对象的名称；2.过滤条件，可为null，默认搜索所有信息；3.搜索控件，可为null，使用默认的搜索控件
                NamingEnumeration<SearchResult> search = ctx.search(basedn, filter, searchControls);
                while (search.hasMore()) {
                    SearchResult next = search.next();
                    NamingEnumeration<? extends Attribute> all = next.getAttributes().getAll();

                    StringBuilder user = new StringBuilder();

                    while (all.hasMore()) {
                        Attribute next1 = all.next();
                        user.append(next1.getID()).append(": ").append(next1.get()).append(" ");
                    }
                    System.out.println(user);
                    System.out.println("---------------------------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("获取用户信息异常:");
            e.printStackTrace();
        }

        return lm;
    }

    /**
     * 获取用户信息
     *
     * @param ctx
     * @param basedn
     * @return
     */
    public static List<LdapUser> readLdap(LdapContext ctx, String basedn) {

        List<LdapUser> lm = new ArrayList<>();
        try {
            if (ctx != null) {
                //过滤条件
                String filter = "(&(objectClass=*)(uid=*))";
                String[] attrPersonArray = {"uid", "userPassword", "displayName", "cn", "sn", "mail", "description"};
                SearchControls searchControls = new SearchControls();//搜索控件
                searchControls.setSearchScope(2);//搜索范围
                searchControls.setReturningAttributes(attrPersonArray);
                //1.要搜索的上下文或对象的名称；2.过滤条件，可为null，默认搜索所有信息；3.搜索控件，可为null，使用默认的搜索控件
                NamingEnumeration<SearchResult> answer = ctx.search(basedn, filter, searchControls);
                while (answer.hasMore()) {
                    SearchResult result = answer.next();
                    NamingEnumeration<? extends Attribute> attrs = result.getAttributes().getAll();
                    LdapUser lu = new LdapUser();
                    while (attrs.hasMore()) {
                        Attribute attr = attrs.next();
                        if ("userPassword".equals(attr.getID())) {
                            Object value = attr.get();
                            lu.setUserPassword(new String((byte[]) value));
                        } else if ("uid".equals(attr.getID())) {
                            lu.setUid(attr.get().toString());
                        } else if ("displayName".equals(attr.getID())) {
                            lu.setDisplayName(attr.get().toString());
                        } else if ("cn".equals(attr.getID())) {
                            lu.setCn(attr.get().toString());
                        } else if ("sn".equals(attr.getID())) {
                            lu.setSn(attr.get().toString());
                        } else if ("mail".equals(attr.getID())) {
                            lu.setMail(attr.get().toString());
                        } else if ("description".equals(attr.getID())) {
                            lu.setDescription(attr.get().toString());
                        }
                    }
                    if (lu.getUid() != null) {
                        lm.add(lu);
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("获取用户信息异常:");
            e.printStackTrace();
        }

        return lm;
    }

    /**
     * string 转 数值
     *
     * @param m
     * @return
     */
    public static String strToint(String m) {
        if (m == null || "".equals(m)) {
            return "-1";
        }
        char[] a = m.toCharArray();
        StringBuilder sbu = new StringBuilder();
        for (char c : a) {
            sbu.append((int) c);
        }
        System.out.println(sbu);
        return sbu.toString();
    }

}