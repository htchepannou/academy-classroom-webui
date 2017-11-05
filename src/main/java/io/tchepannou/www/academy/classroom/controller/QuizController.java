package io.tchepannou.www.academy.classroom.controller;

import io.tchepannou.academy.client.quiz.QuizAnswerResponse;
import io.tchepannou.academy.client.quiz.QuizResponse;
import io.tchepannou.www.academy.classroom.backend.QuizBackend;
import io.tchepannou.www.academy.classroom.model.QuizModel;
import io.tchepannou.www.academy.classroom.model.QuizValidationResultModel;
import io.tchepannou.www.academy.classroom.service.AcademyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
public class QuizController {
    @Autowired
    private QuizBackend quizBackend;

    @Autowired
    private AcademyMapper academyMapper;


    @RequestMapping(value="/quiz/{quizId}")
    public ModelAndView getQuiz(@PathVariable  final Integer quizId){

        final QuizResponse response = quizBackend.findQuizById(quizId);
        final QuizModel quiz =  academyMapper.toQuizModel(response.getQuiz());
        quiz.setChoices(
                response.getQuiz().getChoices().stream()
                        .map(s -> academyMapper.toQuizChoiceModel(s))
                        .collect(Collectors.toList())
        );

        final ModelAndView model = new ModelAndView("quiz");
        model.addObject("quiz", quiz);
        return model;
    }

    @RequestMapping(value="/quiz/{quizId}/answer")
    public @ResponseBody QuizValidationResultModel answer(
            @PathVariable final Integer quizId,
            final HttpServletRequest request
    ){
        final String[] values = request.getParameterValues("value");
        final QuizAnswerResponse response = quizBackend.answerQuiz(quizId, Arrays.asList(values));

        final QuizValidationResultModel result = new QuizValidationResultModel();
        result.setValid(response.isValid());
        result.setMessage(response.getMessage());
        return result;
    }
}
