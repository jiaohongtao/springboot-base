package com.hong.controller;

import com.hong.util.common.OfficeUtil;
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
 * pdf接口
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2021年03月25日
 */
@Api(tags = "pdf接口")
@Controller
public class ToPdfController {

    @ApiOperation(value = "doc转换pdf", httpMethod = "POST")
    @PostMapping("/doc/to/pdf")
    public void docToPdf(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        OfficeUtil.docToPdf(file, response);
    }

    @ApiOperation(value = "页面", httpMethod = "GET")
    @GetMapping("/doc/page")
    public String imageToPdfPage() {
        return "docToPdf";
    }
}
