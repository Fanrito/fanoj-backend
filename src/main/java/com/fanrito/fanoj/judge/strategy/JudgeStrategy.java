package com.fanrito.fanoj.judge.strategy;

import com.fanrito.fanoj.judge.codesandbox.model.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {
    /**
     * 判题执行
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
