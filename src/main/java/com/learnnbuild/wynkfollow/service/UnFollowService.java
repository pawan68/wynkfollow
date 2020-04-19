package com.learnnbuild.wynkfollow.service;

import com.learnnbuild.wynkfollow.entities.Artist;
import com.learnnbuild.wynkfollow.entities.Song;
import com.learnnbuild.wynkfollow.entities.User;
import com.learnnbuild.wynkfollow.model.request.UnFollow;
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
public class UnFollowService {

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private WynkFollowUtil wynkFollowUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(UnFollowService.class);

    public ResponseEntity<Response> process(UnFollow request) {
        Response response = null;
        try {
            String userName = request.getUser();
            List<String> artistUserNames = request.getArtist();
            User user = persistenceService.getUser(userName);
            if(user == null) {
                throw new Exception("User does not exist! " + userName);
            }
//            Set<Song> publishedSongsByAllArtists = new HashSet<>(); // set for storing all songs published by artists
            for (String artistUserName : artistUserNames) {
                Artist artist = persistenceService.getArtist(artistUserName);
                if(artist == null) {
                    LOGGER.error("Artist " + artistUserName + " does not exist!");
                    continue;
                }
                // remove entry from table
                user.unFollow(artist);
                // remove this user as a follower from artist
                artist.removeFollower(user);

//                publishedSongsByAllArtists.addAll(artist.getPublishedSongs());
                // check if this is the most popular artist. If that is the case then remove it
                wynkFollowUtil.checkAndRemoveMostPopularArtist(artist);
                persistenceService.saveArtist(artist);
            }

            // update user's playlist
            Set<Song> songsToRemove = new HashSet<>();
            for (Song song : user.getPlaylist().getSongsInPlayList()) {
                Set<Artist> intersection = new HashSet<>(user.getArtistsFollowed());
                intersection.retainAll(song.getPublishedBy());
                // if there is no artist whom this user is following who has published this particular song
                if(intersection.size() == 0) {
                    songsToRemove.add(song);
                    //user.removeSongFromPlayList(song);
                }
            }
            user.removeSongFromPlayList(songsToRemove);
            wynkFollowUtil.removePlaylistReferenceFromSongs(songsToRemove, user);
            persistenceService.saveUser(user);
            wynkFollowUtil.checkAndRemoveMostPopularSong(songsToRemove);
            response = wynkFollowUtil.buildUnFollowResponse(userName, artistUserNames);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Exception occured in follow service. ", e);
        }
        response = new Response("failed", "invalid input parameter");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
