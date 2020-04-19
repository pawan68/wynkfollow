package com.learnnbuild.wynkfollow.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Table(name = "playlist")
@Entity
public class PlayList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "playlist_id")
    private Integer playListId;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(
            name = "playlist_songs",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private Set<Song> songsInPlayList;

    public PlayList() {
        this.songsInPlayList = new HashSet<>();
    }


    public Integer getPlayListId() {
        return playListId;
    }

    public void setPlayListId(Integer playListId) {
        this.playListId = playListId;
    }

    public Set<Song> getSongsInPlayList() {
        return songsInPlayList;
    }

    public void setSongsInPlayList(Set<Song> songsInPlayList) {
        this.songsInPlayList = songsInPlayList;
    }

    public void addSongInPlayList(Song song) {
        songsInPlayList.add(song);
    }

    public void removeSongFromPlayList(Song song) {
        songsInPlayList.remove(song);
    }
}
