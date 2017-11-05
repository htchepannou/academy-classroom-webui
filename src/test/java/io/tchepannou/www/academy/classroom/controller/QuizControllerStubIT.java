package io.tchepannou.www.academy.classroom.controller;

import io.tchepannou.www.academy.classroom.model.QuizModel;
import io.tchepannou.www.academy.classroom.model.QuizValidationResultModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = {"stub"})
public class QuizControllerStubIT extends ControllerStubSupport{
    @Autowired
    private QuizController controller;


    @Test
    public void shouldReturnQuiz() throws Exception {
        // WHEN
        final ModelAndView model = controller.getQuiz(10111);

        // THEN
        assertThat(model.getViewName()).isEqualTo("quiz");

        final QuizModel quiz = (QuizModel)model.getModel().get("quiz");
        assertThat(quiz.getId()).isEqualTo(10111);
        assertThat(quiz.getType()).isEqualTo("multichoice");
        assertThat(quiz.getQuestion()).isEqualTo("Who might be a potential end user of documentation?");
        assertThat(quiz.getDescription()).isEqualTo("<p>This is the description</p>\n");
        assertThat(quiz.getSuccessMessage()).isEqualTo("Awesome");
        assertThat(quiz.getFailureMessage()).isEqualTo("Looser");

        assertThat(quiz.getChoices()).hasSize(3);
        assertThat(quiz.getChoices().get(0).getId()).isEqualTo(101031);
        assertThat(quiz.getChoices().get(0).getText()).isEqualTo("You");
        assertThat(quiz.getChoices().get(1).getId()).isEqualTo(101032);
        assertThat(quiz.getChoices().get(1).getText()).isEqualTo("Your coworkers");
        assertThat(quiz.getChoices().get(2).getId()).isEqualTo(101033);
        assertThat(quiz.getChoices().get(2).getText()).isEqualTo("Your users");
    }

    @Test
    public void shouldAnswerFalseAnswer() throws Exception {
        // Given
        when (request.getParameterValues(anyString())).thenReturn(new String[] {"foo"});

        // When
        final QuizValidationResultModel result = controller.answer(10111, request);

        // Then
        assertThat(result.isValid()).isFalse();
        assertThat(result.getMessage()).isEqualTo("Looser");
    }


    @Test
    public void shouldAnswerTrueAnswer() throws Exception {
        // Given
        when (request.getParameterValues(anyString())).thenReturn(new String[] {"101032", "101033"});

        // When
        final QuizValidationResultModel result = controller.answer(10112, request);

        // Then
        assertThat(result.isValid()).isTrue();
        assertThat(result.getMessage()).isEqualTo("You are a champion");
    }
}
