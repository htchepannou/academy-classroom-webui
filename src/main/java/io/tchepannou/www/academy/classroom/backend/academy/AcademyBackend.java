package io.tchepannou.www.academy.classroom.backend.academy;

import io.tchepannou.www.academy.classroom.backend.Backend;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientResponseException;

@Component
@ConfigurationProperties("application.backend.AcademyBackend")
public class AcademyBackend extends Backend{

    public CourseResponse findCourseById(final Integer id) throws AcademyException{
        try {
            final String uri = String.format("%s/academy/v1/courses/%s", getUrl(), id);
            return rest.getForEntity(uri, CourseResponse.class).getBody();
        } catch (RestClientResponseException e){
            throw new AcademyException(e.getRawStatusCode(), e.getResponseBodyAsString(), e);
        }
    }

    public LessonListResponse findLessonsByCourseId(final Integer courseId){
        try {
            final String uri = String.format("%s/academy/v1/courses/%s/lessons", getUrl(), courseId);
            return rest.getForEntity(uri, LessonListResponse.class).getBody();
        } catch (RestClientResponseException e){
            throw new AcademyException(e.getRawStatusCode(), e.getResponseBodyAsString(), e);
        }
    }

    public SegmentListResponse findSegmentsByCourseIdByLessonId(final Integer courseId, final Integer lessonId){
        try {
            final String uri = String.format("%s/academy/v1/courses/%s/segments?lessonId=%s", getUrl(), courseId, lessonId);
            return rest.getForEntity(uri, SegmentListResponse.class).getBody();
        } catch (RestClientResponseException e){
            throw new AcademyException(e.getRawStatusCode(), e.getResponseBodyAsString(), e);
        }
    }

    public LessonResponse findLessonById(final Integer courseId, final Integer lessonId){
        try {
            final String uri = String.format("%s/academy/v1/courses/%s/lessons/%s", getUrl(), courseId, lessonId);
            return rest.getForEntity(uri, LessonResponse.class).getBody();
        } catch (RestClientResponseException e){
            throw new AcademyException(e.getRawStatusCode(), e.getResponseBodyAsString(), e);
        }
    }

    public SegmentResponse findSegmentById(final Integer courseId, final Integer segmentId){
        try {
            final String uri = String.format("%s/academy/v1/courses/%s/segments/%s", getUrl(), courseId, segmentId);
            return rest.getForEntity(uri, SegmentResponse.class).getBody();
        } catch (RestClientResponseException e){
            throw new AcademyException(e.getRawStatusCode(), e.getResponseBodyAsString(), e);
        }
    }

    public VideoResponse findVideoById(final Integer videoId){
        final String uri = String.format("%s/academy/v1/videos/%s", getUrl(), videoId);
        return rest.getForEntity(uri, VideoResponse.class).getBody();
    }
}
