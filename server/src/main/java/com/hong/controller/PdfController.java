package com.hong.controller;

import com.hong.util.common.PdfUtils;
import com.itextpdf.text.DocumentException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 图片转pdf
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年08月27日
 */
@Controller
@Api(tags = "图片转pdf")
@RequestMapping("/image")
public class PdfController {

    @ApiOperation(value = "转换", httpMethod = "POST")
    @PostMapping("/to/pdf")
    public void imageToPdf(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        try {
            PdfUtils.imageToPdf(file, response);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "页面", httpMethod = "GET")
    @GetMapping("/page")
    public String imageToPdfPage() {
        return "imageToPdf";
    }
}
