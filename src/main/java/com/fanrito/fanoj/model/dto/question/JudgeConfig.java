package com.fanrito.fanoj.model.dto.question;

import lombok.Data;

/**
 * 判题配置
 */
@Data
public class JudgeConfig {
    /**
     * 时间限制
     */
    private Long timeLimit;
    /**
     * 内存限制(KB)
     */
    private Long memoryLimit;
    /**
     * 堆栈限制(KB)
     */
    private Long stackLimit;
}
