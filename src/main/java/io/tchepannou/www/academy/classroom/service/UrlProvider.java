package io.tchepannou.www.academy.classroom.service;

import org.springframework.stereotype.Component;

@Component
public class UrlProvider {
    public String getSegmentUrl(Integer courseId, Integer lessonId, Integer segmentId){
        return String.format("/classroom/%s/%s/%s", courseId, lessonId, segmentId);
    }
}
