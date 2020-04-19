package com.learnnbuild.wynkfollow.model.response;

import java.io.Serializable;

public class PopularSongResponse implements Serializable {
    private String song;

    public PopularSongResponse() {
    }

    public PopularSongResponse(String song) {
        this.song = song;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }
}
