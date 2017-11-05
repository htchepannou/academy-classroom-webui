package io.tchepannou.www.academy.classroom.service;

import io.tchepannou.academy.user.client.dto.PersonDto;
import io.tchepannou.www.academy.classroom.model.PersonModel;
import org.springframework.stereotype.Controller;

@Controller
public class PersonMapper {
    public PersonModel toPersonModel(final PersonDto dto){
        final PersonModel model = new PersonModel();
        model.setId(dto.getId());
        model.setBiography(dto.getBiography());
        model.setEmail(dto.getEmail());
        model.setFirstName(dto.getFirstName());
        model.setLastName(dto.getLastName());
        model.setLanguage(dto.getLanguage());
        model.setPictureUrl(dto.getPictureUrl());
        model.setWebsiteUrl(dto.getWebsiteUrl());
        model.setTitle(dto.getTitle());
        return model;
    }}
