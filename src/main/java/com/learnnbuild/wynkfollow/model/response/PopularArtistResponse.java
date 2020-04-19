package com.learnnbuild.wynkfollow.model.response;

public class PopularArtistResponse {
    private String artist;

    public PopularArtistResponse(String artist) {
        this.artist = artist;
    }

    public PopularArtistResponse() {
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
