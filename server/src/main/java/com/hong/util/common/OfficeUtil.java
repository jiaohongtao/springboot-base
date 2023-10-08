package com.hong.util.common;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * office工具
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2021年03月25日
 */
public class OfficeUtil {

    /**
     * word转pdf
     *
     * @param file word文件
     */
    public static void docToPdf(MultipartFile file, HttpServletResponse response) {
        /*获取上传文件名*/
        String filename = file.getOriginalFilename();
        /*获取上传word的输入流,返回输出流*/
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = file.getInputStream();
            outputStream = response.getOutputStream();

            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            try {
                filename = URLEncoder.encode(filename + ".pdf", "UTF-8");
                response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            } catch (UnsupportedEncodingException e) {
                response.setHeader("Content-Disposition", "attachment;filename=error.pdf");
            }
            IConverter converter = LocalConverter.builder().build();
            converter.convert(inputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();

            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
