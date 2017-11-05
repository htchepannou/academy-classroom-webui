package io.tchepannou.www.academy.classroom.controller;

import io.tchepannou.academy.user.client.person.PersonResponse;
import io.tchepannou.www.academy.classroom.backend.PersonBackend;
import io.tchepannou.www.academy.classroom.model.PersonModel;
import io.tchepannou.www.academy.classroom.service.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InstructorController {
    @Autowired
    private PersonBackend personBackend;

    @Autowired
    private PersonMapper personMapper;

    @RequestMapping(value="/instructors/{roleIds}")
    public ModelAndView findByRoleIds(@PathVariable String roleIds) {
        final List<PersonModel> instructors = new ArrayList<>();
        for (String roleId : roleIds.split(",")) {
            final PersonResponse personResponse = personBackend.findPersonByRole(Integer.parseInt(roleId));
            final PersonModel instructor = personMapper.toPersonModel(personResponse.getPerson());
            instructors.add(instructor);
        }

        ModelAndView model = new ModelAndView("instructors");
        model.addObject("instructors", instructors);
        return model;
    }
}
