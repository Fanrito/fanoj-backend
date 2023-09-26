package com.fanrito.fanoj.judge.codesandbox;

import com.fanrito.fanoj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.fanrito.fanoj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.fanrito.fanoj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂（根据字符串参数创建指定的代码沙箱实例）
 */
public class CodeSandboxFactory {
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
