package com.fanrito.fanoj.judge;

import cn.hutool.json.JSONUtil;
import com.fanrito.fanoj.common.ErrorCode;
import com.fanrito.fanoj.exception.BusinessException;
import com.fanrito.fanoj.judge.codesandbox.CodeSandbox;
import com.fanrito.fanoj.judge.codesandbox.CodeSandboxFactory;
import com.fanrito.fanoj.judge.codesandbox.CodeSandboxProxy;
import com.fanrito.fanoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.fanrito.fanoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.fanrito.fanoj.judge.strategy.JudgeContext;
import com.fanrito.fanoj.model.dto.question.JudgeCase;
import com.fanrito.fanoj.judge.codesandbox.model.JudgeInfo;
import com.fanrito.fanoj.model.entity.Question;
import com.fanrito.fanoj.model.entity.QuestionSubmit;
import com.fanrito.fanoj.model.enums.JudgeInfoMessageEnum;
import com.fanrito.fanoj.model.enums.QuestionSubmitStatusEnum;
import com.fanrito.fanoj.service.QuestionService;
import com.fanrito.fanoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type: example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 1) 传入题目的提交id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        // 2）如果不为等待状态，就不重复执行
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 3）更改判题的状态为“判题中”, 防止重复执行
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 4) 调用沙箱，获取到执行结果

        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        // 5) 根据沙箱的执行结果，设置题目的判题状态和信息

        // 判断是否是编译错误
        JudgeInfo judgeInfo = new JudgeInfo();
        if (Objects.equals(executeCodeResponse.getMessage(), "java.lang.RuntimeException: 编译错误")) {
            judgeInfo.setMessage(JudgeInfoMessageEnum.COMPILE_ERROR.getValue());
        } else {
            JudgeContext judgeContext = new JudgeContext();
            judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
            judgeContext.setInputList(inputList);
            judgeContext.setOutputList(outputList);
            judgeContext.setJudgeCaseList(judgeCaseList);
            judgeContext.setQuestion(question);
            judgeContext.setQuestionSubmit(questionSubmit);
            judgeInfo = judgeManager.doJudge(judgeContext);
        }

        // 6) 修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        QuestionSubmit questionSubmitResult = questionSubmitService.getById(questionId);
        return questionSubmitResult;
    }
}
