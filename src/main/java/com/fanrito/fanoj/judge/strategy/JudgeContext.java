package com.fanrito.fanoj.judge.strategy;

import com.fanrito.fanoj.model.dto.question.JudgeCase;
import com.fanrito.fanoj.judge.codesandbox.model.JudgeInfo;
import com.fanrito.fanoj.model.entity.Question;
import com.fanrito.fanoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeContext {
    private JudgeInfo judgeInfo;
    private List<String> inputList;
    private List<String> outputList;
    List<JudgeCase> judgeCaseList;
    private Question question;
    private QuestionSubmit questionSubmit;
}
