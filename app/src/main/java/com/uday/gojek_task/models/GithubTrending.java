package com.uday.gojek_task.models;

import java.util.List;

public class GithubTrending {

    public String author;
    public String name;
    public String avatar;
    public String url;
    public String description;
    public String language;
    public String languageColor;
    public String stars;
    public String forks;
    public String currentPeriodStars;
    public List<GithubBuiltBy> builtBy;
    private boolean expanded;

    public GithubTrending(String author, String name, String avatar, String url, String description, String language, String languageColor, String stars, String forks, String currentPeriodStars, List<GithubBuiltBy> builtBy) {
        this.author = author;
        this.name = name;
        this.avatar = avatar;
        this.url = url;
        this.description = description;
        this.language = language;
        this.languageColor = languageColor;
        this.stars = stars;
        this.forks = forks;
        this.currentPeriodStars = currentPeriodStars;
        this.builtBy = builtBy;
        this.expanded  = false;
    }

    public GithubTrending() {
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public void setLanguageColor(String languageColor) {
        this.languageColor = languageColor;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getForks() {
        return forks;
    }

    public void setForks(String forks) {
        this.forks = forks;
    }

    public String getCurrentPeriodStars() {
        return currentPeriodStars;
    }

    public void setCurrentPeriodStars(String currentPeriodStars) {
        this.currentPeriodStars = currentPeriodStars;
    }

    public List<GithubBuiltBy> getBuiltBy() {
        return builtBy;
    }

    public void setBuiltBy(List<GithubBuiltBy> builtBy) {
        this.builtBy = builtBy;
    }
}
