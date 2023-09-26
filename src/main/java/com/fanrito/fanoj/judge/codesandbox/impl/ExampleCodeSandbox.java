package com.fanrito.fanoj.judge.codesandbox.impl;

import com.fanrito.fanoj.judge.codesandbox.CodeSandbox;
import com.fanrito.fanoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.fanrito.fanoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.fanrito.fanoj.judge.codesandbox.model.JudgeInfo;
import com.fanrito.fanoj.model.enums.JudgeInfoMessageEnum;
import com.fanrito.fanoj.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱（仅用来跑通流程）
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {

        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String language = executeCodeRequest.getLanguage();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(100L);
        judgeInfo.setMemory(100L);
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
