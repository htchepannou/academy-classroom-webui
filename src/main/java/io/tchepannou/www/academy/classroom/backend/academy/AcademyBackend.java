package io.tchepannou.www.academy.classroom.backend.academy;

import io.tchepannou.www.academy.classroom.backend.Backend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("application.backend.AcademyBackend")
public class AcademyBackend extends Backend{
    private static final Logger LOGGER = LoggerFactory.getLogger(AcademyBackend.class);

    public CourseResponse findCourseById(final Integer id) throws AcademyException{
        final String uri = String.format("%s/academy/v1/courses/%s", getUrl(), id);
        return http.get(uri, CourseResponse.class);
    }

    public VideoResponse findVideoById(final Integer videoId){
        final String uri = String.format("%s/academy/v1/videos/%s", getUrl(), videoId);
        return http.get(uri, VideoResponse.class);
    }

    public void updateStudent(final Integer courseId, final Integer segmentId, final Integer roleId){
        try {
            final String uri = String.format("%s/academy/v1/courses/%s/students", getUrl(), courseId);
            final StudentRequest request = new StudentRequest();
            request.setRoleId(roleId);
            request.setSegmentId(segmentId);
            http.post(uri, request, StudentResponse.class);
        } catch (Exception e){
            LOGGER.error("Unable to update student", e);
        }
    }

    public StudentResponse findStudent(final Integer courseId, final Integer roleId){
        final String uri = String.format("%s/academy/v1/courses/%s/students/%s", getUrl(), courseId, roleId);
        return http.get(uri, StudentResponse.class);
    }

    public QuizResponse findQuizById(final Integer id){
        final String uri = String.format("%s/academy/v1/quiz/%s", getUrl(), id);
        return http.get(uri, QuizResponse.class);
    }

    public QuizAnswerResponse answerQuiz(final Integer id, final List<String> values){
        final QuizAnswerRequest request = new QuizAnswerRequest();
        request.setValues(values);

        final String uri = String.format("%s/academy/v1/quiz/%s/answer", getUrl(), id);
        return http.post(uri, request, QuizAnswerResponse.class);

    }
}
