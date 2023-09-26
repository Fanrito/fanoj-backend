package com.fanrito.fanoj.judge;

import com.fanrito.fanoj.judge.strategy.DefaultJudgeStrategy;
import com.fanrito.fanoj.judge.strategy.JavaLanguageJudgeStrategy;
import com.fanrito.fanoj.judge.strategy.JudgeContext;
import com.fanrito.fanoj.judge.strategy.JudgeStrategy;
import com.fanrito.fanoj.judge.codesandbox.model.JudgeInfo;
import com.fanrito.fanoj.model.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {
    /**
     * 判题执行
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }
}
