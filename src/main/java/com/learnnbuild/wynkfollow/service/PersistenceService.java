package com.learnnbuild.wynkfollow.service;

import com.learnnbuild.wynkfollow.entities.*;
import com.learnnbuild.wynkfollow.repository.ArtistRepository;
import com.learnnbuild.wynkfollow.repository.PlayListRepository;
import com.learnnbuild.wynkfollow.repository.SongRepository;
import com.learnnbuild.wynkfollow.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersistenceService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlayListRepository playListRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceService.class);

    public User getOrCreateUser(String userName) {
        User user = null;
        try {
            user = userRepository.findByUserName(userName).orElse(null);
           // user = userRepository.findById(2).orElse(null);
            if (user == null) {
                // first create a playlist for the user
                PlayList playList = playListRepository.save(new PlayList());
                user = userRepository.save(new User(userName, playList));// todo check if person is getting saved or not
            }
        } catch (Exception e) {
            LOGGER.error("exception occured while getting or saving user. ", e);
        }
        return user;
    }

    public User getUser(String userName) {
        User user = null;
        try {
            user = userRepository.findByUserName(userName).orElse(null);
            if (user == null) {
                LOGGER.info("User {} does not exits.", userName);
            }
        } catch (Exception e) {
            LOGGER.error("Exception while getting the user. ", e);
        }
        return user;
    }

    public Artist getOrCreateArtist(String userName) {
        Artist artist = null;
        try {
            artist = artistRepository.findByUserName(userName).orElse(null);
            if (artist == null) {
                artist = artistRepository.save(new Artist(userName));// todo check if person is getting saved or not
            }
        } catch (Exception e) {
            LOGGER.error("exception occured while getting or saving artist. ", e);
        }
        return artist;
    }

//    public UserFollowArtist getOrCreateUserFollowArtist(User user, Artist artist) {
//        UserFollowArtist userFollowArtist = null;
//        try {
//            userFollowArtist = userFollowArtistRepository.findByUserIdAndArtistId(user.userId, artist.userId).orElse(null);
//            if (userFollowArtist == null) {
//                userFollowArtist = userFollowArtistRepository.save(new UserFollowArtist(user, artist, new Date(System.currentTimeMillis())));
//            }
//        } catch (Exception e) {
//            LOGGER.error("exception occured while getting or saving userfollowartist", e);
//        }
//        return userFollowArtist;
//    }

    public Artist getArtist(String userName) {
        Artist artist = null;
        try {
            artist = artistRepository.findByUserName(userName).orElse(null);
            if (artist == null) {
                LOGGER.info("Artist {} does not exits.", userName);
            }
        } catch (Exception e) {
            LOGGER.error("Exception while getting the artist. ", e);
        }
        return artist;
    }

    public Song getSong(String songName) {
        Song song = null;
        try {
            song = songRepository.findBySongName(songName).orElse(null);
            if (song == null) {
                LOGGER.info("Song {} does not exits.", songName);
            }
        } catch (Exception e) {
            LOGGER.error("Exception while getting the artist. ", e);
        }
        return song;
    }

    public Song getOrCreateSong(String songName) {
        Song song = null;
        try {
            song = songRepository.findBySongName(songName).orElse(null);
            if (song == null) {
                LOGGER.info("Song {} does not exits, creating it now!", songName);
                song = songRepository.save(new Song(songName));
            }
        } catch (Exception e) {
            LOGGER.error("Exception while getting the artist. ", e);
        }
        return song;
    }

    public List<Artist> findAllArtists() {
        List<Artist> allArtists = null;
        try {
            allArtists = artistRepository.findAll();
        } catch (Exception e) {
            LOGGER.error("Error occured while fetching all artists!", e);
        }
        return allArtists;
    }

    public List<Song> findAllSongs() {
        List<Song> allSongs = null;
        try {
            allSongs = songRepository.findAll();
        } catch (Exception e) {
            LOGGER.error("Error occured while fetching all songs!", e);
        }
        return allSongs;
    }

    public void saveUser(User user) {
        try {
            playListRepository.save(user.getPlaylist());
            userRepository.save(user);
        } catch (Exception e) {
            LOGGER.error("Error while saving user to db!", e);
        }
    }

    public void saveArtist(Artist artist) {
        try {
            artistRepository.save(artist);
        } catch (Exception e) {
            LOGGER.error("Error while saving artist to db!", e);
        }
    }

    public void saveSong(Song song) {
        try {
            songRepository.save(song);
        } catch (Exception e) {
            LOGGER.error("Error while saving song to db!", e);
        }
    }
}
