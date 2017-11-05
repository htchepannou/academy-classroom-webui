package io.tchepannou.www.academy.classroom.backend;

import io.tchepannou.academy.client.quiz.QuizAnswerRequest;
import io.tchepannou.academy.client.quiz.QuizAnswerResponse;
import io.tchepannou.academy.client.quiz.QuizResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("application.backend.QuizBackend")
public class QuizBackend extends Backend{
    public QuizResponse findQuizById(final Integer id){
        final String uri = String.format("%s/%s", getUrl(), id);
        return restClient.get(uri, QuizResponse.class).getBody();
    }

    public QuizAnswerResponse answerQuiz(final Integer id, final List<String> values){
        final QuizAnswerRequest request = new QuizAnswerRequest();
        request.setValues(values);

        final String uri = String.format("%s/%s/answer", getUrl(), id);
        return restClient.post(uri, request, QuizAnswerResponse.class).getBody();

    }
}
