package com.hong.util.common;

import com.hong.bean.Constant;
import com.hong.bean.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件操作
 * href:
 * https://blog.csdn.net/weixin_43728713/article/details/121155888
 * https://blog.csdn.net/m0_48983233/article/details/122893008
 *
 * @author jiaohongtao
 * @version 1.1 修改 2022/7/7
 * @since 2021年03月12日
 */
@Slf4j
public class FileOperate {

    // 静态文件目录前缀
    public static final String STATIC_PRE = "static/";
    public static final String SPLIT_ONE = " ";
    public static final String IGNORE_PRE_ONE = "#";

    /**
     * 获取文件绝对路径
     *
     * @param path 路径（注意开始没有斜杠）：static/EasyDataExcel.xlsx
     * @return 文件路径
     */
    public static String getResourceFilePath(String path) throws FileNotFoundException {
        return getResourceFile(path).getPath();
    }

    /**
     * 获取文件
     *
     * @param path 路径（注意开始没有斜杠）：static/EasyDataExcel.xlsx
     * @return 文件
     */
    public static File getResourceFile(String path) throws FileNotFoundException {
        return ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + path);
    }

    /**
     * 获取静态文件内容 静态文件内容格式
     * a b
     * c d
     *
     * @param fileName 文件名
     * @return map内容
     */
    public static Map<String, Object> getStaticFileMap(String fileName) {
        return getClassFileMap(STATIC_PRE + fileName);
    }

    /**
     * 获取静态文件内容 静态文件内容格式
     * a b
     * c d
     *
     * @param filePath 文件路径
     * @return map内容
     */
    public static Map<String, Object> getClassFileMap(String filePath) {
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        Map<String, Object> map = new HashMap<>();
        try {
            InputStream inputStream = classPathResource.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            while (bufferedReader.ready()) {
                String content = bufferedReader.readLine();
                if ("".equals(content) || content.startsWith(IGNORE_PRE_ONE)) {
                    continue;
                }
                String[] split = content.split(SPLIT_ONE);
                if (split.length > 1) {
                    map.put(split[0], split[1]);
                }
            }
            return map;
        } catch (IOException e) {
            log.error("获取异常：", e);
            return map;
        }
    }

    public static Map<String, Object> getListMap(List<String> list, String splitChar, String ignorePre, String ignoreEnd) {
        splitChar = StringUtils.isBlank(splitChar) ? SPLIT_ONE : splitChar;
        ignorePre = StringUtils.isBlank(ignorePre) ? IGNORE_PRE_ONE : ignorePre;

        Map<String, Object> map = new HashMap<>();
        for (String content : list) {
            // 如果该行为空，或开头匹配成功，则不处理该行
            if ("".equals(content) || content.endsWith(ignorePre)) {
                continue;
            }

            // 如果结尾匹配不为空且匹配成功，则不处理该行
            if (StringUtils.isNotBlank(ignoreEnd) && content.endsWith(ignoreEnd)) {
                continue;
            }
            String[] split = content.split(splitChar);
            if (split.length > 1) {
                map.put(split[0], split[1]);
            }
        }
        return map;
    }

    public static List<String> getStaticClassFileList(String filePath) {
        return getClassFileList(STATIC_PRE + filePath);
    }

    /**
     * 获取静态文件内容 静态文件内容格式
     * a b
     * c d
     *
     * @param filePath 文件路径
     * @return list
     */
    public static List<String> getClassFileList(String filePath) {
        ClassPathResource classPathResource = new ClassPathResource(filePath);
        List<String> list = new ArrayList<>();
        try {
            InputStream inputStream = classPathResource.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            while (bufferedReader.ready()) {
                list.add(bufferedReader.readLine());
            }
            return list;
        } catch (IOException e) {
            log.error("获取异常：", e);
            return list;
        }
    }


    //方法一
    @Deprecated
    public static Result getFileContentTwo(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        List<String> list = new ArrayList<>();
        while (br.ready()) {
            list.add(br.readLine());
        }
        return Result.success(list);
    }

    //方法二
    public static Result getFileContent(String filePath) throws Exception {
        File file = new File(filePath);
        //判断文件是否存在
        if (!file.isFile() || !file.exists()) {
            log.error("该文件不存在");
            return Result.failed("该文件不存在");
        }

        //考虑到编码格式
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), getFileCode(filePath));
        BufferedReader bufferedReader = new BufferedReader(read);
        List<String> list = new ArrayList<>();
        while (bufferedReader.ready()) {
            // String lineTxt;
            // while ((lineTxt = bufferedReader.readLine()) != null) {
            list.add(bufferedReader.readLine());
        }
        read.close();

        return Result.success(list);
    }


    /**
     * 判断文本文件的字符集，文件开头三个字节表明编码格式
     *
     * @param filePath 文件路径
     */
    public static String getFileCode(String filePath) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
            // 读者注： bis.mark(0);修改为 bis.mark(100);我用过这段代码，需要修改上面标出的地方。
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                bis.close();
                // 文件编码为 ANSI
                return charset;
            } else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                // 文件编码为 Unicode
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
                // 文件编码为 Unicode big endian
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                // 文件编码为 UTF-8
                charset = "UTF-8";
                checked = true;
            }
            bis.reset();
            if (!checked) {
                while ((read = bis.read()) != -1) {
                    if (read >= 0xF0) {
                        break;
                    }
                    if (0x80 <= read && read <= 0xBF) {
                        // 单独出现BF以下的，也算是GBK
                        break;
                    }
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            // 双字节 (0xC0 - 0xDF)
                            // (0x80 - 0xBF),也可能在GB编码内
                            continue;
                        } else {
                            break;
                        }
                    } else if (0xE0 <= read) { // 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                            }
                        }
                        break;
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("--文件-> 【{}】 采用的字符集为: 【{}】", filePath, charset);
        return charset;
    }

    /**
     * 获取clientId
     *
     * @return clientId
     */
    public static String getMyselfClientId() {
        List<String> staticClassFileList = FileOperate.getStaticClassFileList(Constant.MYSELF_NAME);
        Map<String, Object> listMap = FileOperate.getListMap(staticClassFileList, null, Constant.IGNORE_PRE_ONE, null);
        Object object = listMap.get(Constant.CLIENT_ID);
        return Objects.isNull(object) ? "" : object.toString();
    }

    /**
     * 获取文件属性
     *
     * @param filePath 文件地址
     */
    public static Map<String, Object> getAttrs(String filePath) throws IOException {
        // 指定自己的目标文件
        // File file = new File("C:\\Users\\jx\\Desktop\\test.txt");
        File file = new File(filePath);
        long fileModify = file.lastModified();

        // 根据文件的绝对路径获取Path
        Path path = Paths.get(file.getAbsolutePath());
        // 根据path获取文件的基本属性类
        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
        // 从基本属性类中获取文件创建时间
        FileTime fileTime = attrs.creationTime();

        // 将文件创建时间转成毫秒
        long millis = fileTime.toMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 毫秒转成时间字符串
        String time = dateFormat.format(new Date(millis));
        String modifyTime = dateFormat.format(new Date(attrs.lastModifiedTime().toMillis()));
        // System.out.println(time);

        Map<String, Object> map = new HashMap<>();
        map.put("createTime", time);
        map.put("modifyTime", modifyTime);
        map.put("size", attrs.size());
        map.put("path", file.getPath());
        map.put("fileModify", dateFormat.format(new Date(fileModify)));
        return map;
    }

    /**
     * 获取桌面路径
     *
     * @return 桌面路径
     */
    public static String getDeskPath() {
        // 获取桌面路径
        return System.getProperty("user.home") + "/Desktop";
    }

    public static void main(String[] args) throws Exception {
        // getStaticFileMap("myself").forEach((k, v) -> System.out.println(k + " " + v));

        // getFileMap("static/myself").forEach((k, v) -> System.out.println(k + " " + v));

        String filePath = "C:\\Users\\jx\\Desktop\\myself";
        //getFileCode(filePath);

        System.out.println(getFileContent(filePath));

        // System.out.println(getFileContent(filePath));
    }
}
