package io.tchepannou.www.academy.classroom.backend.academy;

import io.tchepannou.academy.client.course.CourseResponse;
import io.tchepannou.academy.client.course.StudentRequest;
import io.tchepannou.academy.client.course.StudentResponse;
import io.tchepannou.academy.client.quiz.QuizAnswerRequest;
import io.tchepannou.academy.client.quiz.QuizAnswerResponse;
import io.tchepannou.academy.client.quiz.QuizResponse;
import io.tchepannou.academy.client.video.VideoResponse;
import io.tchepannou.www.academy.classroom.backend.Backend;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("application.backend.CourseBackend")
public class CourseBackend extends Backend{
    public CourseResponse findCourseById(final Integer id) {
        final String uri = String.format("%s/academy/v1/courses/%s", getUrl(), id);
        return restClient.get(uri, CourseResponse.class).getBody();
    }

    public VideoResponse findVideoById(final Integer videoId){
        final String uri = String.format("%s/academy/v1/videos/%s", getUrl(), videoId);
        return restClient.get(uri, VideoResponse.class).getBody();
    }

    public void updateStudent(final Integer courseId, final Integer segmentId, final Integer roleId){
        final String uri = String.format("%s/academy/v1/courses/%s/students", getUrl(), courseId);
        final StudentRequest request = new StudentRequest();
        request.setRoleId(roleId);
        request.setSegmentId(segmentId);
        restClient.post(uri, request, StudentResponse.class);
    }

    public StudentResponse findStudent(final Integer courseId, final Integer roleId){
        final String uri = String.format("%s/academy/v1/courses/%s/students/%s", getUrl(), courseId, roleId);
        return restClient.get(uri, StudentResponse.class).getBody();
    }

    public QuizResponse findQuizById(final Integer id){
        final String uri = String.format("%s/academy/v1/quiz/%s", getUrl(), id);
        return restClient.get(uri, QuizResponse.class).getBody();
    }

    public QuizAnswerResponse answerQuiz(final Integer id, final List<String> values){
        final QuizAnswerRequest request = new QuizAnswerRequest();
        request.setValues(values);

        final String uri = String.format("%s/academy/v1/quiz/%s/answer", getUrl(), id);
        return restClient.post(uri, request, QuizAnswerResponse.class).getBody();

    }
}
