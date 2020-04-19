package com.learnnbuild.wynkfollow.service;

import com.learnnbuild.wynkfollow.entities.*;
import com.learnnbuild.wynkfollow.model.request.Follow;
import com.learnnbuild.wynkfollow.model.response.Response;
import com.learnnbuild.wynkfollow.util.WynkFollowUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class FollowService {

    @Autowired
    private WynkFollowUtil wynkFollowUtil;

    @Autowired
    private PersistenceService persistenceService;

    private static final Logger LOGGER = LoggerFactory.getLogger(FollowService.class);

    public ResponseEntity<Response> process(Follow request) {
        Response response = null;
        try {
            List<String> artistUserNameList = request.getArtist();
            String userName = request.getUser();
            User user = persistenceService.getOrCreateUser(userName);

            if(user == null)
                throw new Exception("Exception while creating user!");

            for (String artistUserName : artistUserNameList) {
                Artist artist = persistenceService.getOrCreateArtist(artistUserName);
                if(artist == null) {
                   throw new Exception("Exception while creating artist!");
                }
                // follow the artist
                user.follow(artist);
                // add user as follower of artist
                artist.addFollower(user);
                wynkFollowUtil.checkAndUpdateMostPopularArtist(artist);
                // now add songs of artist in user's playlist
                addSongsOfArtistInUserPlaylist(artist, user);
                persistenceService.saveArtist(artist);
            }
            persistenceService.saveUser(user);
            response = wynkFollowUtil.buildFollowResponse(userName, artistUserNameList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception occured in follow service. ", e);
        }
        response = new Response("failed", "invalid input parameter");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private void addSongsOfArtistInUserPlaylist(Artist artist, User user) {
        Set<Song> publishedSongs = artist.getPublishedSongs();
        for (Song song : publishedSongs) {
            user.addSongInPlayList(song);
            song.getPlayListsHavingCurrentSong().add(user.getPlaylist());
            wynkFollowUtil.checkAndUpdateMostPopularSong(song);
        }
    }
}
