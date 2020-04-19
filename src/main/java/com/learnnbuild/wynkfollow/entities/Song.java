package com.learnnbuild.wynkfollow.entities;

import com.learnnbuild.wynkfollow.model.Genre;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "song")
@Entity
public class Song implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "song_id")
    private Integer songId;

    @Column(name = "song_name")
    private String songName;

    @ManyToMany
    private Set<Artist> publishedBy;

    private Integer popularity;
    private Date publishedDate;
    private Double durationInSec;
    private Genre genre;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<PlayList> playListsHavingCurrentSong;

    public Song() {
    }

    public Song(List<Artist> publishedBy, Integer popularity, Date publishedDate) {
        this.publishedBy = new HashSet<>(publishedBy);
        this.popularity = popularity;
        this.publishedDate = publishedDate;
    }

    public Song(String songName) {
        this.songName = songName;
        this.publishedBy = new HashSet<>();
        this.publishedDate = new Date(System.currentTimeMillis());
        this.playListsHavingCurrentSong = new HashSet<>();
    }

    public Song(Integer songId, List<Artist> publishedBy, Integer popularity, Date publishedDate, Double durationInSec, Genre genre) {
        this.songId = songId;
        this.publishedBy = new HashSet<>(publishedBy);
        this.popularity = popularity;
        this.publishedDate = publishedDate;
        this.durationInSec = durationInSec;
        this.genre = genre;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public Set<Artist> getPublishedBy() {
        return publishedBy;
    }

    public void setPublishedBy(Set<Artist> publishedBy) {
        this.publishedBy = publishedBy;
    }

    public Set<PlayList> getPlayListsHavingCurrentSong() {
        return playListsHavingCurrentSong;
    }

    public void setPlayListsHavingCurrentSong(Set<PlayList> playListsHavingCurrentSong) {
        this.playListsHavingCurrentSong = playListsHavingCurrentSong;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Double getDurationInSec() {
        return durationInSec;
    }

    public void setDurationInSec(Double durationInSec) {
        this.durationInSec = durationInSec;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void addPublishedBy(Artist artist) {
        this.publishedBy.add(artist);
    }
}
