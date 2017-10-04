package io.tchepannou.www.academy.classroom.backend.user;

import io.tchepannou.www.academy.classroom.backend.BaseResponse;

public class PersonResponse extends BaseResponse {
    private PersonDto person;

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(final PersonDto person) {
        this.person = person;
    }
}
