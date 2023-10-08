package com.hong.test.data20210126.fund;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基金分析：最近一段时间都有收益
 * href: https://blog.csdn.net/weixin_40986713/article/details/114637661
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2021年03月12日
 */
public class FundSearch {
    static String api = "https://fundapi.eastmoney.com/fundtradenew.aspx?sc=1n&st=desc&cp=&ct=&cd=&ms=&fr=&plevel=&fst=&ftype=&fr1=&fl=0&isab=1";

    private static final List<String> TYPE_LIST = new ArrayList<>();

    static {
        //股票型
        TYPE_LIST.add("&ft=gp");
        //混合型
        TYPE_LIST.add("&ft=hh");
        //债券型
        TYPE_LIST.add("&ft=zq");
        //指数型
        TYPE_LIST.add("&ft=zs");
        //QDII
        TYPE_LIST.add("&ft=qdii");
        //FOF
        TYPE_LIST.add("&ft=fof");
    }

    public static void main(String[] args) {
        List<Fund> funds = getAllFunds();
        System.out.println("共扫描基金" + funds.size() + "只");
        System.out.println("搜索近1周涨幅大于1%，近1月涨幅大于4%，近3月涨幅大于12%，近6月涨幅大于24%的基金，近1年涨大于48%的基金");
        funds = funds.stream().
                filter(
                        e -> e.getDealStatus() == 1 && e.getWeekRise() > 1
                                &&
                                e.getMonthRise() > 4 && e.getThreeMonthsRise() > 12
                                &&
                                e.getSixMonthsRise() > 24 && e.getOneYearRise() > 48)
                .collect(Collectors.toList());
        System.out.println("符合条件的基金共" + funds.size() + "只");
        funds.forEach(e -> System.out.println(e.getRowData()));
    }


    public static List<Fund> getAllFunds() {
        List<Fund> dataList = new ArrayList<>();
        TYPE_LIST.forEach(type -> {
            dataList.addAll(getTypeFunds(api, type));
        });
        return dataList;
    }

    public static List<Fund> getTypeFunds(String API, String type) {
        String body = HttpUtil.get(API + type + "&pi=1&pn=1000");
        Integer pages = getPages(body);
        List<Fund> dataList = new ArrayList<>(parseFundData(body));
        for (int i = 0; i < pages; i++) {
            body = HttpUtil.get(API + type + "&pi=" + (i + 2) + "&pn=1000");
            dataList.addAll(parseFundData(body));
        }
        return dataList;
    }

    public static List<Fund> parseFundData(String text) {
        List<Fund> funds = new ArrayList<>();
        text = text.replace("var rankData = ", "");
        text = text.replace(";", "");
        JSONObject json = JSONUtil.parseObj(text);
        JSONArray array = json.getJSONArray("datas");
        for (int i = 0; i < array.size(); i++) {
            Fund fund = conversion(array.getStr(i));
            funds.add(fund);
        }
        return funds;
    }

    public static Integer getPages(String text) {
        text = text.replace("var rankData = ", "");
        text = text.replace(";", "");
        JSONObject json = JSONUtil.parseObj(text);
        return json.getInt("allPages");
    }

    public static Fund conversion(String text) {
        String[] args = text.split("\\|");
        Fund fund = new Fund();
        fund.setRowData(text);
        fund.setCode(args[0]);
        fund.setName(args[1]);
        fund.setType(args[2]);
        fund.setDate(args[3]);
        fund.setNetValue(StrToDouble(args[4]));
        fund.setDayRise(StrToDouble(args[5]));
        fund.setWeekRise(StrToDouble(args[6]));
        fund.setMonthRise(StrToDouble(args[7]));
        fund.setThreeMonthsRise(StrToDouble(args[8]));
        fund.setSixMonthsRise(StrToDouble(args[9]));
        fund.setOneYearRise(StrToDouble(args[10]));
        fund.setTwoYearsRise(StrToDouble(args[11]));
        fund.setThreeYearsRise(StrToDouble(args[12]));
        fund.setCurYearRise(StrToDouble(args[13]));
        fund.setHistoryRise(StrToDouble(args[14]));
        fund.setPoundage(StrToDouble(args[18]));
        fund.setDtStatus(Integer.valueOf(args[22]));
        fund.setDealStatus(Integer.valueOf(args[23]));
        return fund;
    }

    public static Double StrToDouble(String str) {
        str = str.replace(",", "");
        return StringUtils.isEmpty(str) ? 0 : Double.parseDouble(str);
    }

}