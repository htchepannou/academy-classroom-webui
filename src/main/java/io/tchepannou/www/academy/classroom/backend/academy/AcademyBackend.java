package io.tchepannou.www.academy.classroom.backend.academy;

import io.tchepannou.www.academy.classroom.backend.Backend;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("application.backend.AcademyBackend")
public class AcademyBackend extends Backend{

    public CourseResponse findCourseById(final Integer id) throws AcademyException{
        final String uri = String.format("%s/academy/v1/courses/%s", getUrl(), id);
        return http.get(uri, CourseResponse.class);
    }

    public LessonListResponse findLessonsByCourseId(final Integer courseId){
        final String uri = String.format("%s/academy/v1/courses/%s/lessons", getUrl(), courseId);
        return http.get(uri, LessonListResponse.class);
    }

    public SegmentListResponse findSegmentsByCourseIdByLessonId(final Integer courseId, final Integer lessonId){
        final String uri = String.format("%s/academy/v1/courses/%s/segments?lessonId=%s", getUrl(), courseId, lessonId);
        return http.get(uri, SegmentListResponse.class);
    }

    public LessonResponse findLessonById(final Integer courseId, final Integer lessonId){
        final String uri = String.format("%s/academy/v1/courses/%s/lessons/%s", getUrl(), courseId, lessonId);
        return http.get(uri, LessonResponse.class);
    }

    public SegmentResponse findSegmentById(final Integer courseId, final Integer segmentId){
        final String uri = String.format("%s/academy/v1/courses/%s/segments/%s", getUrl(), courseId, segmentId);
        return http.get(uri, SegmentResponse.class);
    }

    public VideoResponse findVideoById(final Integer videoId){
        final String uri = String.format("%s/academy/v1/videos/%s", getUrl(), videoId);
        return http.get(uri, VideoResponse.class);
    }

    public void start(final Integer studentId, final Integer segmentId){
        final String uri = String.format("%s/academy/v1/attendances/students/%s/segments/%s/start", getUrl(), studentId, segmentId);
        http.post(uri);
    }

    public void done(final Integer studentId, final Integer segmentId){
        final String uri = String.format("%s/academy/v1/attendances/students/%s/segments/%s/done", getUrl(), studentId, segmentId);
        http.post(uri);
    }

    public AttendanceResponse findAttendance(final Integer studentId, final Integer courseId){
        final String uri = String.format("%s/academy/v1/attendances/students/%s/courses/%s", getUrl(), studentId, courseId);
        return http.get(uri, AttendanceResponse.class);
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
