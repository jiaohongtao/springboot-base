package com.hong.util.bloom;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/09/20
 */
public class BloomTest {

    public static void main(String[] args) {

        /*BitMapBloomFilter filter = new BitMapBloomFilter(10);

        filter.add("123");
        filter.add("abcd");
        filter.add("abc");
        filter.add("aaa");
        // System.out.println(filter.contains("abc"));

        BloomFilter<CharSequence> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 20000, 1E-7);

        bloomFilter.put("abcd");
        bloomFilter.put("aaa");
        bloomFilter.put("a");
        bloomFilter.put("aaaaaa");

        System.out.println(bloomFilter.mightContain("aaa"));*/

        // impala-4.1.0-release-started.tgz
        String path = "C:\\Users\\jx\\Desktop\\tmp\\geoJson.json";
        path = "C:\\Users\\jx\\Desktop\\tmp\\impala_tmp\\impala-4.1.0-release-started.tgz";
        // path = "C:\\Users\\jx\\Desktop\\tmp\\impala_tmp\\impala_depen.zip";
        path = "C:\\Users\\jx\\Desktop\\tmp\\impala_tmp\\aa.txt";
        // readFileByFiles(path);

        // readFileByFileReader(path);

        // readFileByBufferedReader(path);
        readFileFileChannel(path);
    }

    public static void readFileByFiles(String pathname) {
        Path path = Paths.get(pathname);
        try {
            /*
             * 使用readAllLines的时候，小文件可以很快读取.
             * 那么更大的文件，读取的肯定会爆了。
             */
            //List<String> lines = Files.readAllLines(path);

            byte[] bytes = Files.readAllBytes(path);
            String str = new String(bytes);
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFileByFileReader(String pathname) {
        File file = new File(pathname);
        FileReader fileReader;
        BufferedReader bufferedReader;
        try {

            BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter("C:\\Users\\jx\\Desktop\\tmp\\impala_tmp\\impala_depen2.zip"));

            fileReader = new FileReader(file);

            bufferedReader = new BufferedReader(fileReader);
            String line;
            StringBuilder buffer = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                // 一行一行地处理...
                //System.out.println(line);
                //处理字符串,并不会将字符串保存真正保存到内存中
                // 这里简单模拟下处理操作.
                // buffer.append(line.substring(0, 1));
                // System.out.println(line);

                bufferedWriter.write(line);
            }
            System.out.println("buffer.length:" + buffer.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //TODO close处理.
        }
    }

    public static void readFileByBufferedReader(String pathname) {

        File file = new File(pathname);
        BufferedReader reader = null;
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            //使用BufferedReader,每次读入1M数据.减少IO.如：
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream, Charset.defaultCharset());
            reader = new BufferedReader(inputStreamReader, 1 * 1024 * 1024);
            String tempString = null;
            StringBuffer buffer = new StringBuffer();
            while ((tempString = reader.readLine()) != null) {
                //System.out.println(tempString);
                //处理字符串,并不会将字符串保存真正保存到内存中
                // 这里简单模拟下处理操作.
                // buffer.append(tempString.substring(0, 1));
            }
            System.out.println("buffer.length:" + buffer.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //TODO close处理
        }
    }

    public static void readFileFileChannel(String pathname) {
        File file = new File(pathname);
        FileInputStream fileInputStream;

        FileOutputStream outputStream;
        try {

            outputStream = new FileOutputStream("C:\\Users\\jx\\Desktop\\tmp\\impala_tmp\\ambari-agent.log.22");
            fileInputStream = new FileInputStream(file);
            FileChannel fileChannel = fileInputStream.getChannel();

            FileChannel outputStreamChannel = outputStream.getChannel();


            int capacity = 1 * 1024 * 1024;//1M
            ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
            StringBuilder buffer = new StringBuilder();
            while (fileChannel.read(byteBuffer) != -1) {
                //读取后，将位置置为0，将limit置为容量, 以备下次读入到字节缓冲中，从0开始存储
                byteBuffer.clear();
                byte[] bytes = byteBuffer.array();
                String str = new String(bytes);
                //System.out.println(str);
                //处理字符串,并不会将字符串保存真正保存到内存中
                // 这里简单模拟下处理操作.
                /*buffer.append(str, 0, 10);
                System.out.println(buffer);*/
                buffer = new StringBuilder();
                buffer.append(str);
                System.out.println(buffer);

                if (!str.trim().equals("")) {

                    outputStreamChannel.write(ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8)));
                }
            }


            System.out.println("buffer.length:" + buffer.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //TODO close处理.
        }

    }
}
