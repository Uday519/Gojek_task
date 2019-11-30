package com.uday.gojek_task.models;

public class GithubBuiltBy {
    public String username;
    public String avatar;
    public String href;

    public GithubBuiltBy(String username, String avatar, String href) {
        this.username = username;
        this.avatar = avatar;
        this.href = href;
    }

    public GithubBuiltBy() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
