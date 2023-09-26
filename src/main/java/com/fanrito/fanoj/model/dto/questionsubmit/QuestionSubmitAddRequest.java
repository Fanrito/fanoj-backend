package com.fanrito.fanoj.model.dto.questionsubmit;

import com.fanrito.fanoj.model.dto.question.JudgeCase;
import com.fanrito.fanoj.model.dto.question.JudgeConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 创建请求
 *
 * @author <a href="https://github.com/Fanrito">范奇森</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@Data
public class QuestionSubmitAddRequest implements Serializable {

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 代码
     */
    private String code;

    /**
     * 编程语言
     */
    private String language;

    private static final long serialVersionUID = 1L;
}