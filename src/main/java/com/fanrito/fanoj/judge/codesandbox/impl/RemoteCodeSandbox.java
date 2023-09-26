package com.fanrito.fanoj.judge.codesandbox.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResource;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.fanrito.fanoj.common.ErrorCode;
import com.fanrito.fanoj.exception.BusinessException;
import com.fanrito.fanoj.judge.codesandbox.CodeSandbox;
import com.fanrito.fanoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.fanrito.fanoj.judge.codesandbox.model.ExecuteCodeResponse;
import org.apache.commons.lang3.StringUtils;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {

        System.out.println("远程代码沙箱");
        String url = "http://192.168.1.108:8090/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandBox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
