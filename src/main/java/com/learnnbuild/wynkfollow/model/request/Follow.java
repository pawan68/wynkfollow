package com.learnnbuild.wynkfollow.model.request;

import java.util.List;

public class Follow {
    private String user;
    private List<String> artist;

    public Follow() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getArtist() {
        return artist;
    }

    public void setArtist(List<String> artist) {
        this.artist = artist;
    }
}

