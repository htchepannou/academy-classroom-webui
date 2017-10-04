package io.tchepannou.www.academy.classroom.controller;

import io.tchepannou.www.academy.classroom.backend.academy.AcademyBackend;
import io.tchepannou.www.academy.classroom.backend.academy.CourseResponse;
import io.tchepannou.www.academy.classroom.backend.academy.InstructorDto;
import io.tchepannou.www.academy.classroom.backend.academy.QuizAnswerResponse;
import io.tchepannou.www.academy.classroom.backend.academy.QuizResponse;
import io.tchepannou.www.academy.classroom.backend.academy.StudentDto;
import io.tchepannou.www.academy.classroom.backend.academy.StudentResponse;
import io.tchepannou.www.academy.classroom.backend.academy.VideoResponse;
import io.tchepannou.www.academy.classroom.backend.user.PersonResponse;
import io.tchepannou.www.academy.classroom.backend.user.UserBackend;
import io.tchepannou.www.academy.classroom.model.BaseModel;
import io.tchepannou.www.academy.classroom.model.CourseModel;
import io.tchepannou.www.academy.classroom.model.LessonModel;
import io.tchepannou.www.academy.classroom.model.PersonModel;
import io.tchepannou.www.academy.classroom.model.QuizModel;
import io.tchepannou.www.academy.classroom.model.QuizValidationResultModel;
import io.tchepannou.www.academy.classroom.model.SegmentModel;
import io.tchepannou.www.academy.classroom.model.SessionModel;
import io.tchepannou.www.academy.classroom.model.VideoModel;
import io.tchepannou.www.academy.classroom.service.AcademyMapper;
import io.tchepannou.www.academy.classroom.service.SessionProvider;
import io.tchepannou.www.academy.classroom.service.UrlProvider;
import io.tchepannou.www.academy.classroom.service.UserMapper;
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
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ClassroomController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClassroomController.class);

    @Autowired
    private AcademyBackend academyBackend;

    @Autowired
    private UserBackend userBackend;

    @Autowired
    private AcademyMapper academyMapper;

    @Autowired
    private UserMapper userMapper;

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
        final CourseModel course = getCourse(courseId);
        final LessonModel lesson = course.getLesson(lessonId);
        final SegmentModel segment = lesson.getSegment(segmentId);

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
        final CourseModel course = getCourse(courseId);
        final List<LessonModel> lessons = course.getLessons();
        final List<SegmentModel> segments = course.getLesson(lessonId).getSegments();

        // Update studends
        try {
            final SessionModel session = sessionProvider.getCurrentSession(request);
            academyBackend.updateStudent(courseId, segmentId, session.getRoleId());
        } catch (Exception e){
            LOGGER.warn("Unable to update the student", e);
        }

        // Next URL
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

        try {
            final StudentDto student = academyBackend.findStudent(courseId, session.getRoleId()).getStudent();
            if (student.getAttendedSegmentCount() < student.getCourseSegmentCount()) {
                return "redirect:/classroom/" + courseId;
            }
        } catch (Exception e){
            LOGGER.warn("Unable to get the student", e);
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
        final LessonModel lesson = lessonId == null ? course.getLessons().get(0) : course.getLesson(lessonId);
        model.addAttribute("lesson", lesson);

        // Current Segment
        final SegmentModel segment = segmentId == null ? lesson.getSegments().get(0) : lesson.getSegment(segmentId);
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
            final StudentResponse response = academyBackend.findStudent(course.getId(), session.getRoleId());
            updateAttendance(course, response.getStudent());
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

    private void updateAttendance(final CourseModel course, final StudentDto studentDto) {
        final Set<Integer> segmentIds = studentDto.getSegments().stream()
                .map(s -> s.getSegmentId())
                .collect(Collectors.toSet());

        for (final LessonModel lesson : course.getLessons()){
            for (final SegmentModel segment : lesson.getSegments()){
                segment.setAttended(segmentIds.contains(segment.getId()));
            }
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
        /* course */
        final CourseResponse response = academyBackend.findCourseById(id);
        final CourseModel course = academyMapper.toCourseModel(response.getCourse());

        /* Instructors */
        List<InstructorDto> instructorDtos = response.getCourse().getInstructors();
        if (instructorDtos != null && instructorDtos.size() > 0){
            final PersonResponse personResponse = userBackend.findPersonByRole(instructorDtos.get(0).getRoleId());
            final PersonModel instructor = userMapper.toPersonModel(personResponse.getPerson());
            course.setInstructor(instructor);
        }

        return course;
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
