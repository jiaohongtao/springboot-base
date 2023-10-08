package com.hong.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年10月28日
 */
public class ExportUtil {

    public static void main(String[] args) {
        // tc();
        writer2();
    }

    public static void writer1() {

        List<String> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd");
        List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
        List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
        List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
        List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");

        List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);
        //通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("E:/Mixed/writeTest.xlsx");
        //通过构造方法创建writer
        //ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

        //跳过当前行，既第一行，非必须，在此演示用
        // writer.passCurrentRow();

        //合并单元格后的标题行，使用默认标题样式
        writer.merge(row1.size() - 1, "测试标题");
        //一次性写出内容，强制输出标题
        writer.write(rows, true);
        //关闭writer，释放内存
        writer.close();

        System.out.println("导出成功");
    }

    public static void writer2() {
        Map<String, Object> row1 = new LinkedHashMap<>();
        row1.put("姓名", "张三");
        row1.put("年龄", 23);
        row1.put("成绩", 88.32);
        row1.put("是否合格", true);
        row1.put("考试日期", DateUtil.date());

        Map<String, Object> row2 = new LinkedHashMap<>();
        row2.put("姓名", "李四");
        row2.put("年龄", 33);
        row2.put("成绩", 59.50);
        row2.put("是否合格", false);
        row2.put("考试日期", DateUtil.date());

        List<Map<String, Object>> rows = CollUtil.newArrayList(row1, row2);

        // 通过工具类创建writer
        ExcelWriter writer = ExcelUtil.getWriter("E:/writeMapTest.xlsx");
        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(rows.size() - 1, "一班成绩单");
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(rows, true);
        writer.writeCellValue(1, rows.size() + 1, "");
        writer.write(rows, true);
        // 关闭writer，释放内存
        writer.close();

        System.out.println("导出成功");
    }


    /*private static void tc() throws IOException {
        //创建文件输入流
        FileInputStream fis = new FileInputStream("E:\\Mixed\\abcd.xlsx");
        //创建reader
        ExcelReader reader = ExcelUtil.getReader(fis);
        //读取全部数据
        List<Map<String, Object>> maps = reader.readAll();
        //---------在实际的开发中,数据是从数据库中获取的-----------
        //创建bigWriter
        ExcelWriter bigWriter = ExcelUtil.getBigWriter();
        //获取表格的头部信息
        if (maps != null) {
            Map<String, Object> stringObjectMap = maps.get(0);
            Set<String> strings = stringObjectMap.keySet();
            //设定表格的表头
            bigWriter.writeHeadRow(strings);
        }
        //定义数据集合
        List<Collection<Object>> objectsList = new ArrayList<>();
        maps.forEach((e) -> {
            //获取集合中,每条数据
            Collection<Object> values = e.values();
            //将所有的数据,都添加到数据集合中
            objectsList.add(values);
        });
        //写数据
        bigWriter.write(objectsList);
        //指定文件流向
        bigWriter.setDestFile(new File("E:\\Mixed\\nnnnnnnn.xlsx"));
        //刷新输出流
        bigWriter.flush();
        //关闭输出流
        bigWriter.close();
        //关闭读入流
        reader.close();
        //关闭输入流
        fis.close();
        System.out.println("导出成功");
    }*/
}
