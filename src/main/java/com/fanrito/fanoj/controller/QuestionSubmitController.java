package com.fanrito.fanoj.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fanrito.fanoj.annotation.AuthCheck;
import com.fanrito.fanoj.common.BaseResponse;
import com.fanrito.fanoj.common.ErrorCode;
import com.fanrito.fanoj.common.ResultUtils;
import com.fanrito.fanoj.constant.UserConstant;
import com.fanrito.fanoj.exception.BusinessException;
import com.fanrito.fanoj.model.dto.question.QuestionQueryRequest;
import com.fanrito.fanoj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.fanrito.fanoj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.fanrito.fanoj.model.entity.Question;
import com.fanrito.fanoj.model.entity.QuestionSubmit;
import com.fanrito.fanoj.model.entity.User;
import com.fanrito.fanoj.model.vo.QuestionSubmitVO;
import com.fanrito.fanoj.service.QuestionSubmitService;
import com.fanrito.fanoj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 *
 * @author <a href="https://github.com/Fanrito">范奇森</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RestController
//@RequestMapping("/question_submit")
@Slf4j
@Deprecated
public class QuestionSubmitController {



}
