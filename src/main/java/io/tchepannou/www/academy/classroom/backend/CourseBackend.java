package io.tchepannou.www.academy.classroom.backend;

import io.tchepannou.academy.client.course.CourseResponse;
import io.tchepannou.academy.client.course.StudentRequest;
import io.tchepannou.academy.client.course.StudentResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application.backend.CourseBackend")
public class CourseBackend extends Backend{
    public CourseResponse findCourseById(final Integer id) {
        final String uri = String.format("%s/%s", getUrl(), id);
        return restClient.get(uri, CourseResponse.class).getBody();
    }

    public void updateStudent(final Integer courseId, final Integer segmentId, final Integer roleId){
        final String uri = String.format("%s/%s/students", getUrl(), courseId);
        final StudentRequest request = new StudentRequest();
        request.setRoleId(roleId);
        request.setSegmentId(segmentId);
        restClient.post(uri, request, StudentResponse.class);
    }

    public StudentResponse findStudent(final Integer courseId, final Integer roleId){
        final String uri = String.format("%s/%s/students/%s", getUrl(), courseId, roleId);
        return restClient.get(uri, StudentResponse.class).getBody();
    }
}
