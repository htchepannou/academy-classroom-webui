package io.tchepannou.www.academy.classroom.backend.user;

@SuppressWarnings("CPD-START")
public class PersonDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String language;
    private String pictureUrl;
    private String biography;
    private String websiteUrl;
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(final String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(final String biography) {
        this.biography = biography;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(final String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }
}
