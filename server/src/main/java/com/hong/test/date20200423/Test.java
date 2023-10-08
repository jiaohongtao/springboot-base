package com.hong.test.date20200423;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.EntityUtils;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年04月23日
 */
public class Test {

    public static void main(String[] args) {
        String emptyStr = "ABCD";

        System.out.println(StringUtils.uncapitalize(emptyStr));

        String name = "c:/a/bc/cd/efg.abc.txt";
        System.out.println(FilenameUtils.getFullPath(name));

        int[] objects = new int[]{};

        ArrayUtils.isSorted(objects);


        System.out.println(DigestUtils.md5Hex("abcd"));
        System.out.println(DigestUtils.md5Hex("abcdef"));
        System.out.println(DigestUtils.md5Hex("abcdefgh"));
        System.out.println(DigestUtils.md5Hex("123456"));
    }
}
