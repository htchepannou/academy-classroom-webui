package io.tchepannou.www.academy.classroom.backend.academy;

import io.tchepannou.www.academy.classroom.backend.Backend;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.backend.AcademyBackend")
public class AcademyBackend extends Backend{

    public CourseResponse findCourseById(final Integer id) throws AcademyException{
        final String uri = String.format("%s/academy/v1/courses/%s", getUrl(), id);
        return rest.getForEntity(uri, CourseResponse.class).getBody();
    }

    public LessonListResponse findLessonsByCourseId(final Integer courseId){
        final String uri = String.format("%s/academy/v1/courses/%s/lessons", getUrl(), courseId);
        return rest.getForEntity(uri, LessonListResponse.class).getBody();
    }

    public SegmentListResponse findSegmentsByCourseIdByLessonId(final Integer courseId, final Integer lessonId){
        final String uri = String.format("%s/academy/v1/courses/%s/segments?lessonId=%s", getUrl(), courseId, lessonId);
        return rest.getForEntity(uri, SegmentListResponse.class).getBody();
    }

    public LessonResponse findLessonById(final Integer courseId, final Integer lessonId){
        final String uri = String.format("%s/academy/v1/courses/%s/lessons/%s", getUrl(), courseId, lessonId);
        return rest.getForEntity(uri, LessonResponse.class).getBody();
    }

    public SegmentResponse findSegmentById(final Integer courseId, final Integer segmentId){
        final String uri = String.format("%s/academy/v1/courses/%s/segments/%s", getUrl(), courseId, segmentId);
        return rest.getForEntity(uri, SegmentResponse.class).getBody();
    }

    public VideoResponse findVideoById(final Integer videoId){
        final String uri = String.format("%s/academy/v1/videos/%s", getUrl(), videoId);
        return rest.getForEntity(uri, VideoResponse.class).getBody();
    }

    public void start(final Integer studentId, final Integer segmentId){
        final String uri = String.format("%s/academy/v1/attendances/students/%s/segments/%s/start", getUrl(), studentId, segmentId);
        rest.postForLocation(uri, null);
    }

    public void done(final Integer studentId, final Integer segmentId){
        final String uri = String.format("%s/academy/v1/attendances/students/%s/segments/%s/done", getUrl(), studentId, segmentId);
        rest.postForLocation(uri, null);
    }

    public AttendanceResponse findAttendance(final Integer studentId, final Integer courseId){
        final String uri = String.format("%s/academy/v1/attendances/students/%s/courses/%s", getUrl(), studentId, courseId);
        return rest.getForEntity(uri, AttendanceResponse.class).getBody();
    }
}
