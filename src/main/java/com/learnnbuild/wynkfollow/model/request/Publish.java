package com.learnnbuild.wynkfollow.model.request;

import java.util.List;

public class Publish {
    private String song;
    private List<String> artists;

    public Publish() {
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public List<String> getArtists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }
}
