package io.tchepannou.www.academy.classroom.controller;

import io.tchepannou.www.academy.classroom.model.PersonModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = {"stub"})
public class InstructorControllerStubIT extends ControllerStubSupport{
    @Autowired
    private InstructorController controller;


    @Test
    public void shouldReturnAnInstructor(){
        // When
        final ModelAndView model = controller.findByRoleIds("100");

        // Then
        assertThat(model.getViewName()).isEqualTo("instructors");

        final List<PersonModel> instructors = (List)model.getModel().get("instructors");
        assertThat(instructors).hasSize(1);

        final PersonModel instructor = instructors.get(0);
        assertThat(instructor.getId()).isEqualTo(100);
        assertThat(instructor.getFirstName()).isEqualTo("Ray");
        assertThat(instructor.getLastName()).isEqualTo("Sponsible");
        assertThat(instructor.getEmail()).isEqualTo("ray.sponsible@gmail.com");
        assertThat(instructor.getLanguage()).isEqualTo("en");
        assertThat(instructor.getPictureUrl()).isEqualTo("http://img.com/ray.sponsible");
        assertThat(instructor.getWebsiteUrl()).isEqualTo("https://www.facebook.com/ray.sponsible");
        assertThat(instructor.getBiography()).isEqualTo("Bio...");
        assertThat(instructor.getTitle()).isEqualTo("Joker");

    }

    @Test
    public void shouldReturnMultipleInstructors(){
        // When
        final ModelAndView model = controller.findByRoleIds("100,101");

        // Then
        assertThat(model.getViewName()).isEqualTo("instructors");

        final List<PersonModel> instructors = (List)model.getModel().get("instructors");
        assertThat(instructors).hasSize(2);
    }
}
