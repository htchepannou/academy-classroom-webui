package io.tchepannou.www.academy.classroom.service;

import org.springframework.stereotype.Component;

@Component
public class UrlProvider {
    public String getSegmentUrl(final Integer courseId, final Integer lessonId, final Integer segmentId){
        if (segmentId == null){
            return lessonId == null
                    ? String.format("/classroom/%s", courseId)
                    : String.format("/classroom/%s/%s", courseId, lessonId);
        } else {
            return String.format("/classroom/%s/%s/%s", courseId, lessonId, segmentId);
        }
    }
}
