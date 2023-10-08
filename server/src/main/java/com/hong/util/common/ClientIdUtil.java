package com.hong.util.common;

import com.hong.bean.Result;

/**
 * clientId工具
 *
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/07/07
 */
public class ClientIdUtil {

    public static Result isMatchSuccess(String clientId) {
        return clientId.equals(FileOperate.getMyselfClientId()) ? Result.success("匹配密钥成功") : Result.failed("请使用正确密钥");
    }
}
