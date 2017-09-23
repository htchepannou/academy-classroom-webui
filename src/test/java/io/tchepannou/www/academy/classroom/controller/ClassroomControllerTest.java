package io.tchepannou.www.academy.classroom.controller;

import io.tchepannou.www.academy.classroom.model.CourseModel;
import io.tchepannou.www.academy.classroom.model.LessonModel;
import io.tchepannou.www.academy.classroom.model.SegmentModel;
import io.tchepannou.www.academy.classroom.service.UrlProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClassroomControllerTest {
    @Mock
    private UrlProvider urlProvider;

    @InjectMocks
    private ClassroomController controller;

    private CourseModel course;
    private List<LessonModel> lessons;
    private List<SegmentModel> segments;

    @Before
    public void setUp(){
        course = createCourse(1);

        lessons = Arrays.asList(
                createLesson(1, 1),
                createLesson(2, 2),
                createLesson(3, 3)
        );

        segments = Arrays.asList(
                createSegment(11, 1),
                createSegment(12, 2),
                createSegment(13, 3)
        );

        when(urlProvider.getSegmentUrl(anyInt(), anyInt(), anyInt())).then(
                (a) -> "/classroom/" + a.getArguments()[0] + "/" + a.getArguments()[1] + "/" + nullToEmpty(a.getArguments()[2])
        );

    }

    @Test
    public void getNextUrl() throws Exception {
        // When
        assertThat(controller.getNextUrl(course.getId(), 1, 11, lessons, segments)).isEqualTo("/classroom/1/1/12");
        assertThat(controller.getNextUrl(course.getId(), 1, 12, lessons, segments)).isEqualTo("/classroom/1/1/13");
        assertThat(controller.getNextUrl(course.getId(), 1, 13, lessons, segments)).isEqualTo("/classroom/1/2/");
    }

    @Test
    public void getNextUrlLastLesson() throws Exception {
        // When
        assertThat(controller.getNextUrl(course.getId(), 3, 11, lessons, segments)).isEqualTo("/classroom/1/3/12");
        assertThat(controller.getNextUrl(course.getId(), 3, 12, lessons, segments)).isEqualTo("/classroom/1/3/13");
        assertThat(controller.getNextUrl(course.getId(), 3, 13, lessons, segments)).isEqualTo("/classroom/1/done");
    }

    private CourseModel createCourse(int id){
        final CourseModel course = new CourseModel();
        course.setId(id);
        return course;
    }

    private LessonModel createLesson (int id, int rank){
        final LessonModel lesson = new LessonModel();
        lesson.setId(id);
        lesson.setRank(rank);
        return lesson;
    }

    private SegmentModel createSegment(int id, int rank){
        final SegmentModel segment = new SegmentModel();
        segment.setId(id);
        segment.setRank(rank);
        return segment;
    }

    private String nullToEmpty(Object obj){
        return obj == null ? "" : obj.toString();
    }
}
