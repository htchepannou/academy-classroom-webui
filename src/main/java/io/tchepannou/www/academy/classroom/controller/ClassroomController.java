package io.tchepannou.www.academy.classroom.controller;

import io.tchepannou.www.academy.classroom.backend.academy.AcademyBackend;
import io.tchepannou.www.academy.classroom.backend.academy.AttendanceResponse;
import io.tchepannou.www.academy.classroom.backend.academy.CourseAttendanceDto;
import io.tchepannou.www.academy.classroom.backend.academy.CourseResponse;
import io.tchepannou.www.academy.classroom.backend.academy.LessonListResponse;
import io.tchepannou.www.academy.classroom.backend.academy.LessonResponse;
import io.tchepannou.www.academy.classroom.backend.academy.QuizAnswerResponse;
import io.tchepannou.www.academy.classroom.backend.academy.QuizResponse;
import io.tchepannou.www.academy.classroom.backend.academy.SegmentDto;
import io.tchepannou.www.academy.classroom.backend.academy.SegmentListResponse;
import io.tchepannou.www.academy.classroom.backend.academy.SegmentResponse;
import io.tchepannou.www.academy.classroom.backend.academy.VideoResponse;
import io.tchepannou.www.academy.classroom.model.BaseModel;
import io.tchepannou.www.academy.classroom.model.CourseModel;
import io.tchepannou.www.academy.classroom.model.LessonModel;
import io.tchepannou.www.academy.classroom.model.QuizModel;
import io.tchepannou.www.academy.classroom.model.QuizValidationResultModel;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
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
            final Model model,
            final HttpServletRequest request
    ) {
        return index(courseId, null, null, model, request);
    }

    @RequestMapping(value="/classroom/{courseId}/{lessonId}/{segmentId}")
    public String index(
            @PathVariable final Integer courseId,
            @PathVariable final Integer lessonId,
            @PathVariable final Integer segmentId,
            final Model model,
            final HttpServletRequest request
    ){
        openSegment(courseId, lessonId, segmentId, model, request);
        return "classroom";
    }

    @RequestMapping(value="/classroom/{courseId}/{lessonId}/{segmentId}/quiz/answer")
    public @ResponseBody QuizValidationResultModel answer(
            @PathVariable final Integer courseId,
            @PathVariable final Integer lessonId,
            @PathVariable final Integer segmentId,
            final HttpServletRequest request
    ){
        final SegmentDto segment = academyBackend.findSegmentById(courseId, segmentId).getSegment();
        final String[] values = request.getParameterValues("value");
        final QuizAnswerResponse response = academyBackend.answerQuiz(segment.getQuizId(), Arrays.asList(values));

        final QuizValidationResultModel result = new QuizValidationResultModel();
        result.setValid(response.isValid());
        result.setMessage(response.getMessage());
        return result;
    }

    @RequestMapping(value="/classroom/{courseId}/{lessonId}/{segmentId}/done")
    public String done(
            @PathVariable final Integer courseId,
            @PathVariable final Integer lessonId,
            @PathVariable final Integer segmentId,
            final HttpServletRequest request
    ){
        // Event
        final SessionModel session = sessionProvider.getCurrentSession(request);
        academyBackend.done(session.getRoleId(), segmentId);

        // Next URL
        final List<LessonModel> lessons = loadLessons(courseId);
        final List<SegmentModel> segments = loadSegments(courseId, lessonId);
        final String nextUrl = getNextUrl(courseId, lessonId, segmentId, lessons, segments);

        LOGGER.info("Redirecting to {}", nextUrl);
        return "redirect:" + nextUrl;
    }

    @RequestMapping(value="/classroom/{courseId}/done")
    public String done (
            @PathVariable final Integer courseId,
            final Model model,
            final HttpServletRequest request
    ){
        final SessionModel session = sessionProvider.getCurrentSession(request);
        final CourseAttendanceDto attendance = academyBackend.findAttendance(session.getRoleId(), courseId).getAttendance();
        if (attendance.getAttendedSegmentCount() < attendance.getCourseSegmentCount()){
            return "redirect:/classroom/" + courseId;
        }

        final CourseModel course = getCourse(courseId);
        model.addAttribute("course", course);
        return "done";
    }


    //-- Private
    public SegmentModel openSegment(
            @PathVariable final Integer courseId,
            @PathVariable final Integer lessonId,
            @PathVariable final Integer segmentId,
            final Model model,
            final HttpServletRequest request
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
        if (video != null) {
            model.addAttribute("video", video);
        }

        // Quiz
        final QuizModel quiz = getQuiz(segment);
        if (quiz != null) {
            model.addAttribute("quiz", quiz);
        }

        // Attendance
        final SessionModel session = sessionProvider.getCurrentSession(request);
        try {
            final Integer studentId = session.getRoleId();
            final AttendanceResponse response = academyBackend.findAttendance(studentId, course.getId());
            updateAttendance(segments, response.getAttendance());
        } catch (Exception e){
            LOGGER.warn("Unable to load the course attendance", e);
        }

        // Next URL
        model.addAttribute("nextUrl", String.format("/classroom/%s/%s/%s/done", course.getId(), lesson.getId(), segment.getId()));
        return segment;
    }

    protected String getNextUrl(
            final Integer courseId,
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
            nextSegmentIndex = -1;
        }

        if (nextLessonIndex != -1){
            final Integer nextLessonId = lessons.get(nextLessonIndex).getId();
            final Integer nextSegmentId = nextSegmentIndex >= 0 ? segments.get(nextSegmentIndex).getId() : null;
            return urlProvider.getSegmentUrl(courseId, nextLessonId, nextSegmentId);
        }
        return "/classroom/" + courseId + "/done";
    }

    private void updateAttendance(final List<SegmentModel> segments, final CourseAttendanceDto attendance) {
        final List<Integer> attendedSegmentId = attendance.getSegments().stream()
                .map(s -> s.getSegmentId())
                .collect(Collectors.toList());
        for (final SegmentModel segment : segments){
            segment.setAttended(attendedSegmentId.contains(segment.getId()));
        }
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
        return getVideo(segment.getVideoId());
    }

    private VideoModel getVideo(final Integer videoId){
        if (videoId == null){
            return null;
        }

        final VideoResponse videoResponse = academyBackend.findVideoById(videoId);
        return academyMapper.toVideoModel(videoResponse.getVideo());
    }

    private QuizModel getQuiz(final SegmentModel segment){
        final Integer quizId = segment.getQuizId();
        if (quizId == null){
            return null;
        }

        final QuizResponse response = academyBackend.findQuizById(quizId);
        final QuizModel quiz =  academyMapper.toQuizModel(response.getQuiz());
        quiz.setChoices(
                response.getQuiz().getChoices().stream()
                    .map(s -> academyMapper.toQuizChoiceModel(s))
                    .collect(Collectors.toList())
        );
        return quiz;
    }
}
