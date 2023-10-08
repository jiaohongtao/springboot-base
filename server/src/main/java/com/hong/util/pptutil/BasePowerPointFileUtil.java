package com.hong.util.pptutil;

import org.apache.commons.io.FileUtils;
import org.apache.poi.sl.usermodel.Shape;
import org.apache.poi.sl.usermodel.*;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Iterator;

/**
 * PowerPoint文件工具基类
 * 通用的PowerPoint文件工具基类，可用于从PowerPoint文档中抽取文本信息
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/09/30
 */
public class BasePowerPointFileUtil {

    /**
     * 渲染、绘制文本框
     *
     * @param shape
     * @param data
     */
    public static void renderShapeAndPicture(Shape shape, WeekAnalyseModel data, String rankType) {
        //判断是否是文本框
        if (shape instanceof TextShape) {
            BasePowerPointFileUtil.replace(shape, data, rankType);
        } else if (shape instanceof GroupShape) {
            Iterator groupShapes = ((GroupShape) shape).iterator();
            while (groupShapes.hasNext()) {
                Shape groupShape = (Shape) groupShapes.next();
                BasePowerPointFileUtil.renderShapeAndPicture(groupShape, data, rankType);
            }
        } else if (shape instanceof TableShape) {
            TableShape tableShape = ((TableShape) shape);
            int column = tableShape.getNumberOfColumns();
            int row = tableShape.getNumberOfRows();
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < column; c++) {
                    BasePowerPointFileUtil.replace(tableShape.getCell(r, c), data, rankType);
                }
            }
        } else if (shape instanceof PictureShape) {
            //判断是否是图片框
            PictureShape pictureShape = (PictureShape) shape;
            PictureData pictureData = pictureShape.getPictureData();
            byte[] bytes = BufferStreamForByte(URLToFile(data.getPictureURL()), 1024);
            try {
                pictureData.setData(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    /**
     * 替换模板PPT中的值
     *
     * @param shape
     * @param weekAnalyseModel
     */
    public static void replace(Shape shape, WeekAnalyseModel weekAnalyseModel, String rankType) {
        //List<WeekAnalyseModel>是我们项目自己定义的model，可改成其他业务的model
        if (shape instanceof TextShape) {

            String replaceText = ((XSLFTextShape) shape).getText();
            XSLFTextRun xslfTextRun = null;
            //替换数据的业务逻辑，待优化
            switch (replaceText) {
                case "姓名：闪耀姓名1":
                    xslfTextRun = ((XSLFTextShape) shape).setText("姓名：" + weekAnalyseModel.getUserName());
                    break;
                case "积分：闪耀分数1":
                    xslfTextRun = ((XSLFTextShape) shape).setText("积分：" + weekAnalyseModel.getWeekData());
                    break;
                case "闪耀1连击ヾ":
                    xslfTextRun = ((XSLFTextShape) shape).setText("闪耀" + weekAnalyseModel.getListNumber() + "连击ヾ");
                    break;
                case "姓名：闪耀姓名2":
                    xslfTextRun = ((XSLFTextShape) shape).setText("姓名：" + weekAnalyseModel.getUserName());
                    break;
                case "积分：闪耀分数2":
                    xslfTextRun = ((XSLFTextShape) shape).setText("积分：" + weekAnalyseModel.getWeekData());
                    break;
                case "闪耀2连击ヾ":
                    xslfTextRun = ((XSLFTextShape) shape).setText("闪耀" + weekAnalyseModel.getListNumber() + "连击ヾ");
                    break;

            }

            //空值过滤，设置样式
            if (xslfTextRun != null) {
                if ("闪耀之星".equals(rankType) || "进步之星".equals(rankType)) {
                    setTextStyle(xslfTextRun);
                } else if ("闪耀之星荣誉证书".equals(rankType) || "进步之星荣誉证书".equals(rankType)) {
                    setTextStyleCertificate(xslfTextRun);
                }
            }
        }
    }

    /**
     * 设置字体样式
     *
     * @param xslfTextRun
     */
    private static void setTextStyle(XSLFTextRun xslfTextRun) {
        xslfTextRun.setFontFamily("等线(正文)");
        Color color = new Color(255, 255, 255);
        xslfTextRun.setFontColor(color);
        xslfTextRun.setFontSize(40.0);
        xslfTextRun.setBold(true);
    }

    /**
     * 设置证书字体样式
     *
     * @param xslfTextRun
     */
    private static void setTextStyleCertificate(XSLFTextRun xslfTextRun) {
        xslfTextRun.setFontFamily("宋体");
        Color color = new Color(0, 0, 0);
        xslfTextRun.setFontColor(color);
        xslfTextRun.setFontSize(32.0);
        xslfTextRun.setBold(true);
    }

    /**
     * 将文件转为字节数组
     *
     * @param file
     * @param size
     * @return
     */
    public static byte[] BufferStreamForByte(File file, int size) {
        byte[] content = null;
        try {
            BufferedInputStream bis = null;
            ByteArrayOutputStream out = null;
            try {
                FileInputStream input = new FileInputStream(file);
                bis = new BufferedInputStream(input, size);
                byte[] bytes = new byte[1024];
                int len;
                out = new ByteArrayOutputStream();
                while ((len = bis.read(bytes)) > 0) {
                    out.write(bytes, 0, len);
                }

                bis.close();
                content = out.toByteArray();
            } finally {
                if (bis != null) {
                    bis.close();
                }
                if (out != null) {
                    out.close();
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return content;

    }

    /**
     * 读取网络中的图片
     *
     * @param url https://www.kziyue.com/wp-content/uploads/2019/06/5bca-hxyuaph9825616.jpg
     * @return
     */
    public static File URLToFile(String url) {
        File file1 = new File("test.mp4");
        try {

            URL url1 = new URL(url);
            FileUtils.copyURLToFile(url1, file1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        File absoluteFile = file1.getAbsoluteFile();
        return file1;
    }


}