package io.tchepannou.www.academy.classroom.controller;

import io.tchepannou.www.academy.classroom.backend.academy.AcademyBackend;
import io.tchepannou.www.academy.classroom.backend.academy.CourseResponse;
import io.tchepannou.www.academy.classroom.backend.academy.LessonListResponse;
import io.tchepannou.www.academy.classroom.backend.academy.LessonResponse;
import io.tchepannou.www.academy.classroom.backend.academy.SegmentListResponse;
import io.tchepannou.www.academy.classroom.backend.academy.SegmentResponse;
import io.tchepannou.www.academy.classroom.backend.academy.VideoResponse;
import io.tchepannou.www.academy.classroom.model.BaseModel;
import io.tchepannou.www.academy.classroom.model.CourseModel;
import io.tchepannou.www.academy.classroom.model.LessonModel;
import io.tchepannou.www.academy.classroom.model.SegmentModel;
import io.tchepannou.www.academy.classroom.model.SessionModel;
import io.tchepannou.www.academy.classroom.model.VideoModel;
import io.tchepannou.www.academy.classroom.service.AcademyMapper;
import io.tchepannou.www.academy.classroom.service.SessionProvider;
import io.tchepannou.www.academy.classroom.service.UrlProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ClassroomController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassroomController.class);

    @Autowired
    private AcademyBackend academyBackend;

    @Autowired
    private AcademyMapper academyMapper;

    @Autowired
    private UrlProvider urlProvider;

    @Autowired
    private SessionProvider sessionProvider;


    @RequestMapping(value="/classroom/{courseId}")
    public String index(
            @PathVariable final Integer courseId,
            final Model model
    ) {
        return index(courseId, null, null, model);
    }

    @RequestMapping(value="/classroom/{courseId}/{lessonId}/{segmentId}")
    public String index(
            @PathVariable final Integer courseId,
            @PathVariable final Integer lessonId,
            @PathVariable final Integer segmentId,
            final Model model
    ){
        // Course
        final CourseModel course = getCourse(courseId);
        model.addAttribute("course", course);

        // Current lesson
        final List<LessonModel> lessons = loadLessons(course.getId());
        final LessonModel lesson = getLesson(courseId, lessonId, lessons);
        model.addAttribute("lesson", lesson);

        // Current Segment
        final List<SegmentModel> segments = loadSegments(courseId, lesson.getId());
        final SegmentModel segment = getSegment(courseId, segmentId, segments);
        model.addAttribute("segments", segments);
        model.addAttribute("segment", segment);

        // Video
        final VideoModel video = getVideo(segment);
        model.addAttribute("video", video);

        // Next URL
        model.addAttribute("nextUrl", String.format("/classroom/%s/%s/%s/done", course.getId(), lesson.getId(), segment.getId()));
        return "classroom";
    }

    @RequestMapping(value="/classroom/{courseId}/{lessonId}/{segmentId}/done")
    public String done(
            @PathVariable final Integer courseId,
            @PathVariable final Integer lessonId,
            @PathVariable final Integer segmentId,
            final HttpServletRequest request

    ){
        // Event
        final Optional<SessionModel> session = sessionProvider.getCurrentSession(request);
        if (session.isPresent()) {
            academyBackend.done(session.get().getRoleId(), segmentId);
        }

        // Next URL
        final CourseModel course = getCourse(courseId);
        final List<LessonModel> lessons = loadLessons(course.getId());
        final List<SegmentModel> segments = loadSegments(courseId, lessonId);
        final String nextUrl = getNextUrl(course, lessonId, segmentId, lessons, segments);

        LOGGER.info("Redirecting to {}", nextUrl);
        return "redirect:" + nextUrl;
    }

    @RequestMapping(value="/classroom/{courseId}/done")
    public String done (@PathVariable final Integer courseId, final Model model){
        final CourseModel course = getCourse(courseId);
        model.addAttribute("course", course);
        return "done";
    }


    //-- Private
    protected String getNextUrl(
            final CourseModel course,
            final Integer lessonId,
            final Integer segmentId,
            final List<LessonModel> lessons,
            final List<SegmentModel> segments
    ){
        final int lessonIndex = getIndex(lessonId, lessons);
        final int segmentIndex = getIndex(segmentId, segments);
        int nextLessonIndex = -1;
        int nextSegmentIndex = -1;

        if (segmentIndex+1 < segments.size()){
            nextLessonIndex = lessonIndex;
            nextSegmentIndex = segmentIndex + 1;
        } else if (lessonIndex+1 < lessons.size()){
            nextLessonIndex = lessonIndex + 1;
            nextSegmentIndex = 0;
        }

        if (nextLessonIndex != -1 && nextSegmentIndex != -1){
            final Integer nextLessonId = lessons.get(nextLessonIndex).getId();
            final Integer nextSegmentId = segments.get(nextSegmentIndex).getId();
            return urlProvider.getSegmentUrl(course.getId(), nextLessonId, nextSegmentId);
        }
        return "/classroom/" + course.getId() + "/done";
    }


    private int getIndex(Integer lessonId, List<? extends BaseModel> lessons){
        for (int i=0 ; i<lessons.size() ; i++){
            if (lessons.get(i).getId().equals(lessonId)){
                return i;
            }
        }
        return -1;
    }

    private CourseModel getCourse(final Integer id){
        final CourseResponse response = academyBackend.findCourseById(id);
        return academyMapper.toCourseModel(response.getCourse());
    }

    private LessonModel getLesson(final Integer courseId, final Integer lessonId, final List<LessonModel> lessons){
        if (lessonId != null){
            final LessonResponse response = academyBackend.findLessonById(courseId, lessonId);
            return academyMapper.toLessonModel(response.getLesson());
        } else {
            return lessons.get(0);
        }
    }

    private List<LessonModel> loadLessons(final Integer courseId){
        final LessonListResponse response = academyBackend.findLessonsByCourseId(courseId);
        return response.getLessons().stream()
                .map(s -> academyMapper.toLessonModel(s))
                .collect(Collectors.toList());
    }

    private List<SegmentModel> loadSegments(final Integer courseId, final Integer lessonId){
        final SegmentListResponse response = academyBackend.findSegmentsByCourseIdByLessonId(courseId, lessonId);
        return response.getSegments().stream()
                .map(s -> academyMapper.toSegmentModel(s))
                .collect(Collectors.toList());
    }

    private SegmentModel getSegment(final Integer courseId, final Integer segmentId, final List<SegmentModel> segments){
        final Integer id = segmentId == null ? segments.get(0).getId() : segmentId;

        final SegmentResponse response = academyBackend.findSegmentById(courseId, id);
        return academyMapper.toSegmentModel(response.getSegment());

    }

    private VideoModel getVideo(final SegmentModel segment){
        final Integer videoId = segment.getVideoId();
        if (videoId == null){
            return null;
        }

        final VideoResponse videoResponse = academyBackend.findVideoById(videoId);
        return academyMapper.toVideoModel(videoResponse.getVideo());
    }
}
