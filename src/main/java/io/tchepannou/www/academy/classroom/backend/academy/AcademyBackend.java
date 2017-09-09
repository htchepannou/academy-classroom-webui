package io.tchepannou.www.academy.classroom.backend.academy;

import io.tchepannou.www.academy.classroom.backend.academy.dto.CourseResponse;
import io.tchepannou.www.academy.classroom.backend.academy.dto.LessonListResponse;
import io.tchepannou.www.academy.classroom.backend.academy.dto.LessonResponse;
import io.tchepannou.www.academy.classroom.backend.academy.dto.SegmentListResponse;
import io.tchepannou.www.academy.classroom.backend.academy.dto.SegmentResponse;
import io.tchepannou.www.academy.classroom.backend.academy.dto.VideoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConfigurationProperties("backend.academy")
public class AcademyBackend {
    private String url;

    @Autowired
    private RestTemplate rest;

    public CourseResponse findCourseById(final Integer id){
        final String uri = String.format("%s/academy/v1/courses/%s", url, id);
        return rest.getForEntity(uri, CourseResponse.class).getBody();
    }

    public LessonListResponse findLessonsByCourseId(final Integer courseId){
        final String uri = String.format("%s/academy/v1/courses/%s/lessons", url, courseId);
        return rest.getForEntity(uri, LessonListResponse.class).getBody();
    }

    public SegmentListResponse findSegmentsByCourseIdByLessonId(final Integer courseId, final Integer lessonId){
        final String uri = String.format("%s/academy/v1/courses/%s/segments?lessonId=%s", url, courseId, lessonId);
        return rest.getForEntity(uri, SegmentListResponse.class).getBody();
    }

    public LessonResponse findLessonById(final Integer courseId, final Integer lessonId){
        final String uri = String.format("%s/academy/v1/courses/%s/lessons/%s", url, courseId, lessonId);
        return rest.getForEntity(uri, LessonResponse.class).getBody();
    }

    public SegmentResponse findSegmentById(final Integer courseId, final Integer segmentId){
        final String uri = String.format("%s/academy/v1/courses/%s/segments/%s", url, courseId, segmentId);
        return rest.getForEntity(uri, SegmentResponse.class).getBody();
    }

    public VideoResponse findVideoById(final Integer videoId){
        final String uri = String.format("%s/academy/v1/videos/%s", url, videoId);
        return rest.getForEntity(uri, VideoResponse.class).getBody();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }
}
