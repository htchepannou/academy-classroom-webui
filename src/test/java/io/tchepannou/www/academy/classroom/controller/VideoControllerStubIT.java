package io.tchepannou.www.academy.classroom.controller;

import io.tchepannou.www.academy.classroom.model.VideoModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = {"stub"})
public class VideoControllerStubIT extends ControllerStubSupport{
    @Autowired
    private VideoController controller;


    @Test
    public void shouldReturnVideo() throws Exception {
        // WHEN
        final ModelAndView model = controller.getVideo(10101);

        // THEN
        assertThat(model.getViewName()).isEqualTo("video");

        final VideoModel video = (VideoModel)model.getModel().get("video");
        assertThat(video.getId()).isEqualTo(10101);
        assertThat(video.getVideoId()).isEqualTo("zYyRDFx3e28");
        assertThat(video.getDurationSecond()).isEqualTo(52);
        assertThat(video.getEmbedUrl()).isEqualTo("https://www.youtube.com/embed/zYyRDFx3e28");
    }

}
