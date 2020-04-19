package com.learnnbuild.wynkfollow.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@Table(name = "artist")
@Entity
public class Artist extends Account implements Serializable {

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "artists_publish_songs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private Set<Song> publishedSongs;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "followers_of_artist",
            joinColumns = @JoinColumn(name = "artist.user_id"),
            inverseJoinColumns = @JoinColumn(name = "user.user_id"))
    private Set<User> followers;

    public Artist() {
    }

    public Set<Song> getPublishedSongs() {
        return publishedSongs;
    }

    public void setPublishedSongs(Set<Song> publishedSongs) {
        this.publishedSongs = publishedSongs;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Artist(String userName) {
        this.userName = userName;
        this.password = "artist_password_" + userName; // todo generate random key as password
        this.joiningDate = new Date(System.currentTimeMillis());
        this.publishedSongs = new HashSet<>();
        this.followers = new HashSet<>();
    }

    public String getUserName() {
        return this.userName;
    }

    public void publishSong(Song song) {
        this.publishedSongs.add(song);
    }

    public void addFollower(User user) {
        this.followers.add(user);
    }

    public void removeFollower(User user) {
        this.followers.remove(user);
    }

}
