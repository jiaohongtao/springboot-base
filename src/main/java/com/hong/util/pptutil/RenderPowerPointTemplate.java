package com.hong.util.pptutil;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.*;

import java.io.*;
import java.text.DateFormat;
import java.util.*;

/**
 * 读取模板PPT生成新的PPT文件
 *
 * @author Promsing(张有博)
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/09/30
 */
@Slf4j
public class RenderPowerPointTemplate extends BasePowerPointFileUtil {

    public final static String WEEK_CONTENT_DIR = "C:\\Users\\jx\\Desktop\\WeekContent\\";

    /**
     * 读取PPT模板
     *
     * @param powerPoint ppt
     * @param rankType   路径
     * @return ppt文件 路径
     */
    public static String renderPowerPointTemplate(InputStream powerPoint, Map<String, String> contentMap, String rankType) throws IOException {
        //List<WeekAnalyseModel>是我们项目自己定义的model，可改成其他业务的model
        if (powerPoint == null) {
            return null;
        }
        //创建一个幻灯片
        XMLSlideShow slideShow = new XMLSlideShow(powerPoint);
        //从幻灯片中获取每个页
        List<XSLFSlide> slides = slideShow.getSlides();
        //遍历每一页PPT
        //幻灯片布局，文本框，矩形框之类的，遍历一页PPT中的所有控件
        // List shapes = ((Slide) slides.get(i)).getShapes();
        List<XSLFShape> shapes3 = slides.get(PPTPage.weekContent.getCode()).getShapes();
        List<XSLFShape> shapes11 = slides.get(PPTPage.weekSummary.getCode()).getShapes();
        List<XSLFShape> shapes4 = slides.get(PPTPage.codePic.getCode()).getShapes();
        List<XSLFShape> shapes5 = slides.get(PPTPage.chandao.getCode()).getShapes();
        List<XSLFShape> shapes13 = slides.get(PPTPage.lastWeek.getCode()).getShapes();

        String weekContent = contentMap.get("weekContent");
        // 本周工作、本周工作总结
        for (XSLFShape shape : shapes3) {
            if (shape instanceof XSLFTextBox) {
                ((XSLFTextBox) shape).setText(weekContent);
                /*XSLFTextBox box = (XSLFTextBox) shape;
                box.setText(weekContent);*/
                break;
            }
        }
        for (XSLFShape shape : shapes11) {
            if (shape instanceof XSLFTextBox) {
                ((XSLFTextBox) shape).setText(weekContent);
                break;
            }
        }

        // 代码截图
        String codePath = WEEK_CONTENT_DIR + "code.png";
        for (XSLFShape shape : shapes4) {
            if (shape instanceof XSLFPictureShape) {
                XSLFPictureShape pictureShape = (XSLFPictureShape) shape;
                PictureData pictureData = pictureShape.getPictureData();
                byte[] bytes = BufferStreamForByte(new File(codePath), 1024);
                try {
                    pictureData.setData(bytes);
                } catch (IOException e) {
                    log.error("IOException: ", e);
                }
                break;
            }
        }
        // 技术文档截图
        String docUrl = contentMap.get("chandaoUrl");
        String docPath = WEEK_CONTENT_DIR + "chandao.png";
        for (XSLFShape shape : shapes5) {
            if (shape instanceof XSLFTextBox) {
                ((XSLFTextBox) shape).setText(docUrl);
            }
            if (shape instanceof XSLFPictureShape) {
                XSLFPictureShape pictureShape = (XSLFPictureShape) shape;
                PictureData pictureData = pictureShape.getPictureData();
                byte[] bytes = BufferStreamForByte(new File(docPath), 1024);
                try {
                    pictureData.setData(bytes);
                } catch (IOException e) {
                    log.error("IOException: ", e);
                }
            }
        }
        // 下周工作
        String lastWeekContent = contentMap.get("lastweek");
        for (XSLFShape shape : shapes13) {
            if (shape instanceof XSLFTextBox) {
                ((XSLFTextBox) shape).setText(lastWeekContent);
                break;
            }
        }

        //新PPT的位置，file就是新的PPT文件
        String pptName = "焦洪涛-" + DateFormat.getDateInstance().format(new Date()) + "-海盒大数据云产品线 SDP周报.pptx";
        File file = new File(rankType + pptName);
        slideShow.write(new FileOutputStream(file));
        log.info("新文件的路径:{}", file.getAbsolutePath());
        return file.getAbsolutePath();
    }

    /**
     * 读取PPT模板
     *
     * @param powerPoint ppt
     * @param lists      实体
     * @param rankType   路径
     */
    public static void renderPowerPointTemplateOfCertificate(InputStream powerPoint, List<WeekAnalyseModel> lists, String rankType) throws IOException {
        System.out.println(lists);
        //List<WeekAnalyseModel>是我们项目自己定义的model，可改成其他业务的model
        if (powerPoint == null) {
            return;
        }
        //创建一个幻灯片
        XMLSlideShow slideShow = new XMLSlideShow(powerPoint);
        //从幻灯片中获取每个页
        List<XSLFSlide> slides = slideShow.getSlides();
        //遍历每一页PPT
        for (int i = 3; i < slides.size() - 1; i++) {
            //幻灯片布局，文本框，矩形框之类的，遍历一页PPT中的所有控件
            // List shapes = ((Slide) slides.get(i)).getShapes();
            List<XSLFShape> shapes = slides.get(i).getShapes();
            // 本周工作、本周工作总结
            if (i == 3 || i == 11) {
                for (XSLFShape shape : shapes) {
                    System.out.println(shape);
                    if (shape instanceof XSLFTextBox) {
                        XSLFTextBox box = (XSLFTextBox) shape;
                        box.setText("AAAA\nBBB");
                    }
                }
            }
            // 代码截图
            if (i == 4) {
                for (XSLFShape shape : shapes) {
                    System.out.println(shape);
                    if (shape instanceof XSLFPictureShape) {
                        XSLFPictureShape pictureShape = (XSLFPictureShape) shape;
                        PictureData pictureData = pictureShape.getPictureData();
                        byte[] bytes = BufferStreamForByte(new File("C:\\Users\\jx\\Desktop\\imgs\\back.jpg"), 1024);
                        try {
                            pictureData.setData(bytes);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            // 技术文档截图
            if (i == 4) {
                for (XSLFShape shape : shapes) {
                    System.out.println(shape);
                    if (shape instanceof XSLFPictureShape) {
                        XSLFPictureShape pictureShape = (XSLFPictureShape) shape;
                        PictureData pictureData = pictureShape.getPictureData();
                        byte[] bytes = BufferStreamForByte(new File("C:\\Users\\jx\\Desktop\\imgs\\back.jpg"), 1024);
                        try {
                            pictureData.setData(bytes);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            // 下周工作总结
            if (i == 13) {
                for (XSLFShape shape : shapes) {
                    System.out.println(shape);
                    if (shape instanceof XSLFTextBox) {
                        XSLFTextBox box = (XSLFTextBox) shape;
                        box.setText("AAAA\nBBB");
                    }
                }
            }
        }

        //新PPT的位置，file就是新的PPT文件
        File file = new File(rankType + "test.pptx");
        OutputStream outputStreams = new FileOutputStream(file);
        slideShow.write(outputStreams);
        // FileUpLoadUtil.T_THREAD_LOCAL.set(file.getAbsolutePath());
        System.out.println("新文件的路径:" + file.getAbsolutePath());

    }

    public static Map<String, String> weekContentRead(File file) {
        Map<String, String> content = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String len;
            while ((len = bufferedReader.readLine()) != null) {
                String name = len.substring(0, len.indexOf(":"));
                String value = len.substring(len.indexOf(":") + 1);
                if (!value.contains(" ")) {
                    content.put(name, value);
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (String s : value.split(" ")) {
                        stringBuilder.append(s).append("\n");
                    }
                    content.put(name, stringBuilder.toString());
                }
            }
        } catch (IOException e) {
            log.error("IOException: ", e);
        }
        return content;

    }

    public static void main(String[] args) throws FileNotFoundException {
        try {
            File file = new File(WEEK_CONTENT_DIR + "WeekAll.txt");
            Map<String, String> contentMap = weekContentRead(file);
            InputStream inputStream = new FileInputStream(WEEK_CONTENT_DIR + "周报Template.pptx");

            renderPowerPointTemplate(inputStream, contentMap, WEEK_CONTENT_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}