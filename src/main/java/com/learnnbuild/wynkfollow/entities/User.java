package com.learnnbuild.wynkfollow.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "user")
@Entity
public class User extends Account implements Serializable {

    @OneToOne
    @JoinColumn(name = "playlist_id")
    private PlayList playlist;

    @ManyToMany
    private Set<Artist> artistsFollowed;

    public User() {
    }

    public User(String userName, PlayList playList) {
        this.artistsFollowed = new HashSet<>();
        this.password = "password_" + userName; // todo generate random key as password
        this.joiningDate = new Date(System.currentTimeMillis());
        this.userName = userName;
        this.playlist = playList;
    }

    public Set<Artist> getArtistsFollowed() {
        return artistsFollowed;
    }

    public void setArtistsFollowed(Set<Artist> artistsFollowed) {
        this.artistsFollowed = artistsFollowed;
    }

    public PlayList getPlaylist() {
        return playlist;
    }

    public void setPlaylist(PlayList playlist) {
        this.playlist = playlist;
    }

    public void follow(Artist artist) {
        this.artistsFollowed.add(artist);
    }

    public void unFollow(Artist artist) {
        this.artistsFollowed.remove(artist);
    }

    public void addSongInPlayList(Song song) {
        this.playlist.addSongInPlayList(song);

    }

    public void removeSongFromPlayList(Song song) {
        this.playlist.removeSongFromPlayList(song);
    }

    public void removeSongFromPlayList(Set<Song> songsToRemove) {
        Set<Song> songsInPlayList = this.playlist.getSongsInPlayList();
        songsInPlayList.removeAll(songsToRemove);
    }
}
