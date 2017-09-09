package io.tchepannou.www.academy.classroom.backend.academy.dto;

import java.util.ArrayList;
import java.util.List;

public class LessonListResponse extends BaseResponse{
    private List<LessonDto> lessons;


    public void add(LessonDto lesson){
        if (lessons == null){
            lessons = new ArrayList<>();
        }
        lessons.add(lesson);
    }

    public int getSize (){
        return lessons != null ? lessons.size() : 0;
    }
    public List<LessonDto> getLessons() {
        return lessons;
    }

    public void setLessons(final List<LessonDto> lessons) {
        this.lessons = lessons;
    }
}
