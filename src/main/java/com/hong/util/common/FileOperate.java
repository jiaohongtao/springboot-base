package com.hong.util.common;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 文件操作
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2021年03月12日
 */
public class FileOperate {

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
}
