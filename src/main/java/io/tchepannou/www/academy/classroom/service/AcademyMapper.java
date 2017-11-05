package io.tchepannou.www.academy.classroom.service;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import io.tchepannou.academy.client.dto.CourseDto;
import io.tchepannou.academy.client.dto.LessonDto;
import io.tchepannou.academy.client.dto.QuizChoiceDto;
import io.tchepannou.academy.client.dto.QuizDto;
import io.tchepannou.academy.client.dto.SegmentDto;
import io.tchepannou.academy.client.dto.VideoDto;
import io.tchepannou.academy.user.client.dto.SessionDto;
import io.tchepannou.www.academy.classroom.model.CourseModel;
import io.tchepannou.www.academy.classroom.model.LessonModel;
import io.tchepannou.www.academy.classroom.model.QuizChoiceModel;
import io.tchepannou.www.academy.classroom.model.QuizModel;
import io.tchepannou.www.academy.classroom.model.SegmentModel;
import io.tchepannou.www.academy.classroom.model.SessionModel;
import io.tchepannou.www.academy.classroom.model.VideoModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.stream.Collectors;

@Component
public class AcademyMapper {
    @Autowired
    private Parser markdownParser;

    @Autowired
    private HtmlRenderer markdownHtmlRenderer;

    public SessionModel toSessionModel (final SessionDto dto){
        final SessionModel model = new SessionModel();
        model.setId(dto.getId());
        model.setAccessToken(dto.getAccessToken());
        model.setAccountId(dto.getAccountId());
        model.setExpiryDateTime(dto.getExpiryDateTime());
        model.setRoleId(dto.getRoleId());
        return model;
    }

    public CourseModel toCourseModel(final CourseDto dto){
        final CourseModel model = new CourseModel();
        final int durationSeconds = computeDurationSeconds(dto);

        model.setDescription(markdown2Html(dto.getDescription()));
        model.setId(dto.getId());
        model.setLanguage(dto.getLanguage());
        model.setLevel(dto.getLevel());
        model.setPublishedDateTime(dto.getPublishedDateTime());
        model.setStatus(dto.getStatus());
        model.setSummary(dto.getSummary());
        model.setTitle(dto.getTitle());
        model.setUpdatedDateTime(dto.getUpdatedDateTime());
        model.setDuration(formatHHMM(durationSeconds));

        if (dto.getLessons() != null){
            model.setLessons(
                    dto.getLessons().stream()
                        .map(l -> toLessonModel(l))
                        .collect(Collectors.toList())
            );
        }

        if (dto.getInstructors() != null){
            model.setInstructorRoleIds(
                    dto.getInstructors().stream()
                        .map(i -> i.getRoleId())
                        .collect(Collectors.toList())
            );
        }
        return model;
    }

    public LessonModel toLessonModel(final LessonDto dto){
        final LessonModel model = new LessonModel();

        model.setId(dto.getId());
        model.setRank(dto.getRank());
        model.setTitle(dto.getTitle());

        if (dto.getSegments() != null){
            model.setSegments(
                    dto.getSegments().stream()
                        .map(s -> toSegmentModel(s))
                        .collect(Collectors.toList())
            );
        }
        return model;
    }

    public SegmentModel toSegmentModel(final SegmentDto dto){
        final SegmentModel model = new SegmentModel();
        final Integer durationSecond = dto.getDurationSecond();

        model.setId(dto.getId());
        model.setDescription(markdown2Html(dto.getDescription()));
        model.setRank(dto.getRank());
        model.setSummary(dto.getSummary());
        model.setTitle(dto.getTitle());
        model.setType(dto.getType());
        model.setVideoId(dto.getVideoId());
        model.setQuizId(dto.getQuizId());
        model.setDurationSecond(durationSecond);
        model.setDuration(formatMMSS(durationSecond));

        return model;
    }

    public VideoModel toVideoModel(final VideoDto dto){
        final VideoModel model = new VideoModel();
        model.setDurationSecond(dto.getDurationSecond());
        model.setId(dto.getId());
        model.setType(dto.getType());
        model.setVideoId(dto.getVideoId());

        if ("youtube".equals(dto.getType())) {
            final String embedUrl = String.format("https://www.youtube.com/embed/%s", dto.getVideoId());
            model.setEmbedUrl(embedUrl);
        }
        return model;
    }

    public QuizModel toQuizModel(final QuizDto dto){
        final QuizModel model = new QuizModel();
        model.setAnswer(dto.getAnswer());
        model.setDescription(markdown2Html(dto.getDescription()));
        model.setId(dto.getId());
        model.setQuestion(dto.getQuestion());
        model.setType(dto.getType());
        model.setSuccessMessage(dto.getSuccessMessage());
        model.setFailureMessage(dto.getFailureMessage());
        return model;
    }

    public QuizChoiceModel toQuizChoiceModel(final QuizChoiceDto dto){
        final QuizChoiceModel model = new QuizChoiceModel();
        model.setId(dto.getId());
        model.setAnswer(dto.isAnswer());
        model.setText(dto.getText());
        return model;
    }

    private String markdown2Html(final String text){
        if (StringUtils.isEmpty(text)){
            return null;
        }

        final Node document = markdownParser.parse(text);
        return markdownHtmlRenderer.render(document);
    }

    private String formatMMSS(Integer durationSecond){
        if (durationSecond != null) {
            final NumberFormat fmt = new DecimalFormat("00");

            final int minute = durationSecond / 60;
            final int seconds = durationSecond % 60;
            return fmt.format(minute) + ':' + fmt.format(seconds);
        }
        return null;
    }

    private String formatHHMM(Integer durationSecond){
        if (durationSecond != null) {
            final NumberFormat fmt = new DecimalFormat("00");

            final int hour = durationSecond / 3600;
            final int minute = (int)(.5 + (float)(durationSecond % 3600) / (float)60);
            return hour > 0
                    ? String.format("%sh %smin", fmt.format(hour), fmt.format(minute))
                    : String.format("%s min", fmt.format(minute));
        }
        return null;
    }

    private int computeDurationSeconds(final CourseDto dto){
        int durationSeconds = 0;
        for (final LessonDto lesson : dto.getLessons()){
            for (final SegmentDto segment : lesson.getSegments()){
                if (segment.getDurationSecond() != null) {
                    durationSeconds += segment.getDurationSecond();
                }
            }
        }
        return durationSeconds;
    }

}
