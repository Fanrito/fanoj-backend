package com.fanrito.fanoj.judge;

import com.fanrito.fanoj.model.entity.QuestionSubmit;

public interface JudgeService {
    QuestionSubmit doJudge(long questionSubmitId);
}
