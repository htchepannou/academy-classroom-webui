package io.tchepannou.www.academy.classroom.controller;

import io.tchepannou.www.academy.classroom.model.CourseModel;
import io.tchepannou.www.academy.classroom.model.LessonModel;
import io.tchepannou.www.academy.classroom.model.SegmentModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = {"stub"})
public class ClassroomControllerStubIT extends ControllerStubSupport{
    @Autowired
    private ClassroomController controller;


    @Test
    public void shouldOpenClassroomAndLandOnFirstSegment() throws Exception {
        // GIVEN
        final ExtendedModelMap model = new ExtendedModelMap();

        // WHEN
        controller.index(500, model, request);

        // THEN
        final LessonModel lesson = (LessonModel)model.get("lesson");
        assertThat(lesson.getId()).isEqualTo(501);

        final SegmentModel segment = (SegmentModel)model.get("segment");
        assertThat(segment.getId()).isEqualTo(50101);
    }

    @Test
    public void shouldOpenTextSegment() throws Exception {
        // GIVEN
        final ExtendedModelMap model = new ExtendedModelMap();

        // WHEN
        controller.index(100, 101, 10102, model, request);

        // THEN
        assertThat(model).hasSize(4);

        final CourseModel course = (CourseModel)model.get("course");
        assertCourse(course);

        final LessonModel lesson = (LessonModel)model.get("lesson");
        assertLesson(lesson);

        final SegmentModel segment = (SegmentModel)model.get("segment");
        assertThat(segment.getId()).isEqualTo(10102);
        assertThat(segment.getVideoId()).isNull();
        assertThat(segment.getTitle()).isEqualTo("Markdown Syntax");
        assertThat(segment.getRank()).isEqualTo(2);
        assertThat(segment.getType()).isEqualTo("text");
        assertThat(segment.getSummary()).isEqualTo("Sample summary #2");
        assertThat(segment.getDescription()).isEqualTo("<p>Sample description #2</p>\n");

        assertThat(model.get("nextUrl")).isEqualTo("/classroom/100/101/10102/done");
    }

    @Test
    public void shouldOpenVideoSegment() throws Exception {
        // GIVEN
        final ExtendedModelMap model = new ExtendedModelMap();

        // WHEN
        controller.index(100, 101, 10112, model, request);

        // THEN
        assertThat(model).hasSize(4);

        final CourseModel course = (CourseModel)model.get("course");
        assertCourse(course);

        final LessonModel lesson = (LessonModel)model.get("lesson");
        assertLesson(lesson);

        final SegmentModel segment = (SegmentModel)model.get("segment");
        assertThat(segment.getId()).isEqualTo(10112);
        assertThat(segment.getVideoId()).isEqualTo(10112);
        assertThat(segment.getTitle()).isEqualTo("Markdown Syntax Practice");
        assertThat(segment.getRank()).isEqualTo(12);
        assertThat(segment.getType()).isEqualTo("video");
        assertThat(segment.getSummary()).isEqualTo("Sample summary");
        assertThat(segment.getDescription()).isEqualTo("<p>Sample description</p>\n");
        assertThat(segment.getDurationSecond()).isEqualTo(215);
        assertThat(segment.getDuration()).isEqualTo("03:35");

        assertThat(model.get("nextUrl")).isEqualTo("/classroom/100/101/10112/done");
    }

    @Test
    public void shouldOpenQuizSegment() throws Exception {
        // GIVEN
        final ExtendedModelMap model = new ExtendedModelMap();

        // WHEN
        controller.index(100, 101, 10111, model, request);

        // THEN
        assertThat(model).hasSize(4);

        final CourseModel course = (CourseModel)model.get("course");
        assertCourse(course);

        final LessonModel lesson = (LessonModel)model.get("lesson");
        assertLesson(lesson);

        final SegmentModel segment = (SegmentModel)model.get("segment");
        assertThat(segment.getId()).isEqualTo(10111);
        assertThat(segment.getVideoId()).isEqualTo(10111);
        assertThat(segment.getQuizId()).isEqualTo(10111);
        assertThat(segment.getTitle()).isEqualTo("Readable READMEs with Markdown");
        assertThat(segment.getRank()).isEqualTo(11);
        assertThat(segment.getType()).isEqualTo("quiz");
        assertThat(segment.getSummary()).isEqualTo("Sample summary #11");
        assertThat(segment.getDescription()).isEqualTo("<p>Sample description #11</p>\n");

        assertThat(model.get("nextUrl")).isEqualTo("/classroom/100/101/10111/done");
    }


    @Test
    public void shouldFinishSegment() throws Exception {
        // GIVEN
        final ExtendedModelMap model = new ExtendedModelMap();

        // WHEN
        final String nextUrl = controller.done(100, 101, 10112, request);

        // THEN
        assertThat(model).isEmpty();
        assertThat(nextUrl).isEqualTo("redirect:/classroom/100/101/10113");
    }

    @Test
    public void shouldFlagAttendedSegments() throws Exception {
        // GIVEN
        final ExtendedModelMap model = new ExtendedModelMap();

        // WHEN
        controller.index(100, model, request);

        // THEN
        final CourseModel course = (CourseModel)model.get("course");
        final List<SegmentModel> segments = course.getLessons().get(0).getSegments();
        assertThat(segments).hasSize(13);
        for (final SegmentModel seg : segments){
            int id = seg.getId();
            assertThat(seg.isAttended()).isEqualTo(id == 10101 || id == 10112);
        }
    }

    @Test
    public void doneShouldRedirectToClassroomWhenAllSegmentNotDone() throws Exception {
        // GIVEN
        final ExtendedModelMap model = new ExtendedModelMap();

        // WHEN
        final String result = controller.done(100, model, request);

        // THEN
        assertThat(result).isEqualTo("redirect:/classroom/100");
    }

    @Test
    public void doneShouldRedirectToDoneWhenAllSegmentDone() throws Exception {
        // GIVEN
        final ExtendedModelMap model = new ExtendedModelMap();

        // WHEN
        final String result = controller.done(500, model, request);

        // THEN
        assertThat(result).isEqualTo("done");

        assertThat(model).hasSize(1);
        final CourseModel course = (CourseModel)model.get("course");
        assertThat(course.getId()).isEqualTo(500);
    }


    //-- private
    private void assertCourse(final CourseModel course){
        assertThat(course.getId()).isEqualTo(100);
        assertThat(course.getTitle()).isEqualTo("How to write README");
        assertThat(course.getSummary()).isEqualTo("Learn the importance of well documented code and see how to craft meaningful READMEs.");
        assertThat(course.getDescription()).isEqualTo("<p>Documentation is an important part of the development process. Learn to write READMEs using Markdown so your code can be used by other humans!</p>\n");
        assertThat(course.getLanguage()).isEqualTo("en");
        assertThat(course.getLevel()).isEqualTo("all");
        assertThat(course.getStatus()).isEqualTo("published");
        assertThat(course.getDuration()).isEqualTo("12 min");
    }

    private void assertLesson(final LessonModel lesson){
        assertThat(lesson.getId()).isEqualTo(101);
        assertThat(lesson.getTitle()).isEqualTo("Writing READMEs");
        assertThat(lesson.getRank()).isEqualTo(1);
    }
}
