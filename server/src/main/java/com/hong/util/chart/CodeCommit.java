package com.hong.util.chart;

import com.alibaba.fastjson.JSONObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/10/13
 */
public class CodeCommit extends JFrame {

    // public CodeCommit(String title, String[] categories, double[] values) {
    public CodeCommit(String title, List<JSONObject> simpleCode) {
        super(title);

        /*// 创建数据集
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < categories.length; i++) {
            dataset.setValue(values[i], "统计数", categopries[i]);
        }*/
        DefaultCategoryDataset dataset = getDataset(simpleCode);

        // 创建图表
        JFreeChart chart = ChartFactory.createBarChart(
                null, // 图表标题
                null, // x 轴标签
                null, // y 轴标签
                dataset, // 数据集
                PlotOrientation.VERTICAL, // 图表方向
                false, // 是否显示图例
                false, // 是否使用 tooltips
                false // 是否使用 URLs
        );

        // 设置背景色
        chart.setBackgroundPaint(Color.WHITE);

        // 获取图表区域对象
        CategoryPlot plot = chart.getCategoryPlot();

        // 设置 X 轴
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12)); // 设置 x 轴标签字体
        domainAxis.setLabelFont(new Font("宋体", Font.BOLD, 14)); // 设置 x 轴标题字体
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 将 x 轴标签旋转 45 度

        // 设置 Y 轴
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12)); // 设置 y 轴标签字体
        rangeAxis.setLabelFont(new Font("宋体", Font.BOLD, 14)); // 设置 y 轴标题字体

        // 设置柱子颜色
        plot.getRenderer().setSeriesPaint(0, new Color(79, 129, 189));

        // 创建图表面板
        ChartPanel chartPanel = new ChartPanel(chart);

        // 添加到窗口中
        setContentPane(chartPanel);
    }

    public DefaultCategoryDataset getDataset(List<JSONObject> simpleCode) {

        System.out.println(simpleCode);

        // 创建数据集
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        /*for (int i = 0; i < categories.length; i++) {
            dataset.setValue(values[i], "统计数", categories[i]);
        }*/
        for (JSONObject json : simpleCode) {
            dataset.setValue(json.getInteger("codeAddNum"), "统计数", json.getString("codeCommitDate"));
        }

        return dataset;
    }

    public static void main(String[] args) {
        // 示例数据
        List<JSONObject> simpleCode = CodeCommitHttp.getSimpleCode(CodeCommitHttp.getCode());

        // 根据date字段进行排序
        simpleCode.sort(Comparator.comparing(data -> {
            String codeCommitDate = data.getString("codeCommitDate");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = format.parse(codeCommitDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }));

        // 创建窗口并显示图表
        // CodeCommit chart = new CodeCommit("每日统计数", categories, values);
        CodeCommit chart = new CodeCommit("每日统计数", simpleCode);
        chart.setSize(1600, 1000);
        chart.setLocationRelativeTo(null); // 居中显示
        chart.setVisible(true);
    }
}
