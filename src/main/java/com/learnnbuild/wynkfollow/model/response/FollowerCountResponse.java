package com.learnnbuild.wynkfollow.model.response;

public class FollowerCountResponse {
    private String artist;
    private Integer count;

    public FollowerCountResponse() {
    }

    public FollowerCountResponse(String artist, Integer count) {
        this.artist = artist;
        this.count = count;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
