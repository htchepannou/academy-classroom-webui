package io.tchepannou.www.academy.classroom.controller;

import io.tchepannou.academy.client.video.VideoResponse;
import io.tchepannou.www.academy.classroom.backend.VideoBackend;
import io.tchepannou.www.academy.classroom.model.VideoModel;
import io.tchepannou.www.academy.classroom.service.AcademyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class VideoController {
    @Autowired
    private VideoBackend videoBackend;

    @Autowired
    private AcademyMapper academyMapper;

    @RequestMapping(value="/video/{videoId}")
    public String getVideo(
            @PathVariable final Integer videoId,
            final Model model
    ){

        final VideoResponse videoResponse = videoBackend.findVideoById(videoId);
        final VideoModel video = academyMapper.toVideoModel(videoResponse.getVideo());
        model.addAttribute("video", video);
        return "video";
    }

}
