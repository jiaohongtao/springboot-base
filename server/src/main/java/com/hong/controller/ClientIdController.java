package com.hong.controller;

import com.hong.bean.Constant;
import com.hong.bean.Result;
import com.hong.util.common.ClientIdUtil;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * clientId控制层
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/07/07
 */
@Api(value = "clientId")
@RestController
@RequestMapping("/clientId")
public class ClientIdController {

    @GetMapping("/test/{clientId}")
    public Result test(@PathVariable(Constant.CLIENT_ID) String clientId) {
        return ClientIdUtil.isMatchSuccess(clientId);
    }
}
