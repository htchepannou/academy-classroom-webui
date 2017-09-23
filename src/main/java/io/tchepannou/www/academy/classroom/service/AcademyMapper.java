package io.tchepannou.www.academy.classroom.service;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import io.tchepannou.www.academy.classroom.backend.academy.CourseDto;
import io.tchepannou.www.academy.classroom.backend.academy.LessonDto;
import io.tchepannou.www.academy.classroom.backend.academy.QuizChoiceDto;
import io.tchepannou.www.academy.classroom.backend.academy.QuizDto;
import io.tchepannou.www.academy.classroom.backend.academy.SegmentDto;
import io.tchepannou.www.academy.classroom.backend.academy.VideoDto;
import io.tchepannou.www.academy.classroom.backend.user.SessionDto;
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

        model.setDescription(markdown2Html(dto.getDescription()));
        model.setId(dto.getId());
        model.setLanguage(dto.getLanguage());
        model.setLevel(dto.getLevel());
        model.setPublishedDateTime(dto.getPublishedDateTime());
        model.setStatus(dto.getStatus());
        model.setSummary(dto.getSummary());
        model.setTitle(dto.getTitle());
        model.setUpdatedDateTime(dto.getUpdatedDateTime());

        return model;
    }

    public LessonModel toLessonModel(final LessonDto dto){
        final LessonModel model = new LessonModel();

        model.setId(dto.getId());
        model.setRank(dto.getRank());
        model.setTitle(dto.getTitle());
        return model;
    }

    public SegmentModel toSegmentModel(final SegmentDto dto){
        final SegmentModel model = new SegmentModel();

        model.setId(dto.getId());
        model.setDescription(markdown2Html(dto.getDescription()));
        model.setRank(dto.getRank());
        model.setSummary(dto.getSummary());
        model.setTitle(dto.getTitle());
        model.setType(dto.getType());
        model.setVideoId(dto.getVideoId());
        model.setQuizId(dto.getQuizId());
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
}
