package com.hong.util.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 身份证工具
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年12月23日
 */
@Slf4j
public class IDCardUtil {
    /**
     * 根据身份证号判断性别
     *
     * @param idNumber 身份证号
     * @return sex
     */
    public static String judgeGender(String idNumber) throws IllegalArgumentException {
        // System.out.println(idNumber.length());
        if (idNumber.length() != 18 && idNumber.length() != 15) {
            throw new IllegalArgumentException("身份证号长度错误");
        }
        int gender;
        if (idNumber.length() == 18) {
            //如果身份证号18位，取身份证号倒数第二位
            char c = idNumber.charAt(idNumber.length() - 2);
            gender = Integer.parseInt(String.valueOf(c));
        } else {
            //如果身份证号15位，取身份证号最后一位
            char c = idNumber.charAt(idNumber.length() - 1);
            gender = Integer.parseInt(String.valueOf(c));
        }
        // System.out.println("gender = " + gender);
        if (gender % 2 == 1) {
            return "男";
        } else {
            return "女";
        }
    }

    /**
     * 根据身份证的号码算出当前身份证持有者的年龄
     *
     * @return age
     */
    public static int countAge(String idNumber) {
        if (idNumber.length() != 18 && idNumber.length() != 15) {
            throw new IllegalArgumentException("身份证号长度错误");
        }
        String year;
        String yue;
        String day;
        if (idNumber.length() == 18) {
            year = idNumber.substring(6).substring(0, 4);// 得到年份
            yue = idNumber.substring(10).substring(0, 2);// 得到月份
            day = idNumber.substring(12).substring(0, 2);//得到日
        } else {
            year = "19" + idNumber.substring(6, 8);// 年份
            yue = idNumber.substring(8, 10);// 月份
            day = idNumber.substring(10, 12);//日
        }
        Date date = new Date();// 得到当前的系统时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fyear = format.format(date).substring(0, 4);// 当前年份
        String fyue = format.format(date).substring(5, 7);// 月份
        String fday = format.format(date).substring(8, 10);//
        int age = 0;
        if (Integer.parseInt(yue) == Integer.parseInt(fyue)) {//如果月份相同
            if (Integer.parseInt(day) <= Integer.parseInt(fday)) {//说明已经过了生日或者今天是生日
                age = Integer.parseInt(fyear) - Integer.parseInt(year);
            }
        } else {

            if (Integer.parseInt(yue) < Integer.parseInt(fyue)) {
                //如果当前月份大于出生月份
                age = Integer.parseInt(fyear) - Integer.parseInt(year);
            } else {
                //如果当前月份小于出生月份,说明生日还没过
                age = Integer.parseInt(fyear) - Integer.parseInt(year) - 1;
            }
        }
        // System.out.println("age = " + age);
        return age;
    }

    public static String getProvince(String idNumber) {
        String proNum = idNumber.substring(0, 3);
        System.out.println(proNum);
        ClassPathResource classPathResource = new ClassPathResource("static/province.txt");
        // StringBuilder buffer = new StringBuilder();
        try {
            InputStream inputStream = classPathResource.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
            while (bufferedReader.ready()) {
                // buffer.append((char) bufferedReader.read());
                String addresses = bufferedReader.readLine();
                String[] split = addresses.split(" ");
                if (proNum.equals(split[1])) {
                    // System.out.println(split[0]);
                    return split[0];
                }
                // System.out.println(addresses);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(buffer.toString());
        return "目前只允许查询中国境内的市，你是中国的吗，呜呜";
    }

    /**
     * 根据身份证号获取省/县
     *
     * @param idNumber 身份证号
     * @param type     0/1 省/县
     * @return 城市
     */
    public static String getCityOrProvince(String idNumber, int type) {
        String idNum = idNumber.substring(0, type == 0 ? 3 : 6);
        String path = type == 0 ? "static/province.txt" : "static/city.txt";
        ClassPathResource classPathResource = new ClassPathResource(path);
        try {
            InputStream inputStream = classPathResource.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
            while (bufferedReader.ready()) {
                String addresses = bufferedReader.readLine();
                if ("".equals(addresses)) {
                    continue;
                }
                String[] split = addresses.split(" ");
                if (idNum.equals(split[1])) {
                    return split[0];
                } else {
                    // 部分地区代码并不匹配，如河北省衡水市131，但是河北省为130，所以用前两位匹配
                    if (type == 0) {
                        if (split[1].substring(0, 2).equals(idNum.substring(0, 2))) {
                            return split[0];
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("未知异常：", e);
        }
        return type == 0 ? "目前只允许查询中国境内的市，你是中国的吗，呜呜" : "目前只允许查询河北省内的县/区，呜呜";
    }

    /**
     * 根据身份证号获取省/县
     *
     * @param idNumber 身份证号
     * @return 城市
     */
    public static String getCity(String idNumber) {
        String idNum = idNumber.substring(0, 6);
        String path = "static/all-city-false.txt";
        ClassPathResource classPathResource = new ClassPathResource(path);
        try {
            InputStream inputStream = classPathResource.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
            String area = "";
            while (bufferedReader.ready()) {
                String addresses = bufferedReader.readLine();
                if ("".equals(addresses)) {
                    continue;
                }
                String[] split = addresses.split(" ");
                if (idNum.equals(split[0])) {
                    area = split[1];
                    break;
                }
            }
            return "".equals(area) ? "外星人？" : area;
        } catch (IOException e) {
            log.error("未知异常：", e);
            return "未知错误";
        }
    }

    public static void main(String[] args) {
        /*String idNumber = "130527199606212014";
        System.out.println(judgeGender(idNumber));
        System.out.println(countAge(idNumber));*/
        // System.out.println(HttpClientUitl.doHttpGet("http://www.syx7.com/Citydm.asp", new ArrayList<>()));

        // System.out.println(getProvince("130**************4"));
        System.out.println(getCity("930527199606212014"));
    }
}
