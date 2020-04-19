package com.learnnbuild.wynkfollow.service;

import com.learnnbuild.wynkfollow.entities.Artist;
import com.learnnbuild.wynkfollow.entities.PlayList;
import com.learnnbuild.wynkfollow.entities.Song;
import com.learnnbuild.wynkfollow.entities.User;
import com.learnnbuild.wynkfollow.model.request.Publish;
import com.learnnbuild.wynkfollow.model.response.Response;
import com.learnnbuild.wynkfollow.util.WynkFollowUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PublishService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublishService.class);

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private WynkFollowUtil wynkFollowUtil;

    public ResponseEntity<Response> process(Publish request) {
        Response response = null;
        try {
            String songName = request.getSong();
            Set<PlayList> playListSet = new HashSet<>();
            List<String> artistUserNameList = request.getArtists();
            Song song = persistenceService.getOrCreateSong(songName);
            if(song == null)
                throw new Exception("Exception while creating song!");
            for (String artistUserName : artistUserNameList) {
                Artist artist = persistenceService.getArtist(artistUserName);
                if(artist == null) {
                    throw new Exception("Exception while creating artist!");
                }
                // publish the song
                artist.publishSong(song);
                song.addPublishedBy(artist);
                // now update playlist of every follower
                for (User follower : artist.getFollowers()) {
                    follower.addSongInPlayList(song);
                    playListSet.add(follower.getPlaylist());
                    persistenceService.saveUser(follower);
                }
                wynkFollowUtil.checkAndUpdateMostPopularSong(song);
                persistenceService.saveArtist(artist);
            }
            song.getPlayListsHavingCurrentSong().addAll(playListSet);
            persistenceService.saveSong(song);
            response = wynkFollowUtil.buildPulishResponse();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception occured while publishing a song! ", e);
        }
        response = new Response("failed", "invalid input parameter");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
