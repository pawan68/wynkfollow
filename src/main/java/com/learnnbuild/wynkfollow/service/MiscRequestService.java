package com.learnnbuild.wynkfollow.service;

import com.learnnbuild.wynkfollow.entities.Artist;
import com.learnnbuild.wynkfollow.entities.PlayList;
import com.learnnbuild.wynkfollow.entities.Song;
import com.learnnbuild.wynkfollow.entities.User;
import com.learnnbuild.wynkfollow.model.response.FollowerCountResponse;
import com.learnnbuild.wynkfollow.model.response.PopularArtistResponse;
import com.learnnbuild.wynkfollow.model.response.PopularSongResponse;
import com.learnnbuild.wynkfollow.util.WynkFollowUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MiscRequestService {

    public static final String MOST_POPULAR_ARTIST = "mostPopularArtist";
    public static final String MOST_POPULAR_SONG = "mostPopularSong";

    public static final Logger LOGGER = LoggerFactory.getLogger(MiscRequestService.class);

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private WynkFollowUtil wynkFollowUtil;

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<FollowerCountResponse> getFollowerCount(String artistUserName) {
        Artist artist = persistenceService.getArtist(artistUserName);
        if(artist == null)
            throw new RuntimeException("User does not exist!");
        int followerCount = artist.getFollowers().size();
        FollowerCountResponse followerCountResponse = new FollowerCountResponse(artist.userName, followerCount);
        return new ResponseEntity<>(followerCountResponse, HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<List<String>> getSongsInPlayList(String userName) {
        List<String> response = null;
        User user = persistenceService.getUser(userName);
        if (user == null)
            throw new RuntimeException("User does not exist!");
        PlayList playlist = user.getPlaylist();
        if(playlist == null || playlist.getSongsInPlayList() == null || playlist.getSongsInPlayList().size() == 0) {
            throw new RuntimeException("Playlist is empty!");
        }
        response = playlist.getSongsInPlayList().stream().map(Song::getSongName).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<PopularArtistResponse> getMostPopularArtist() {
        PopularArtistResponse response = null;
        Artist mostPopularArtist = wynkFollowUtil.getMostPopularArtist();
        if(mostPopularArtist != null) {
            return new ResponseEntity<>(new PopularArtistResponse(mostPopularArtist.getUserName()), HttpStatus.OK);
        }
        LOGGER.info("Most popular artist not present in the cache. Going to fetch it from db.");
        List<Artist> artistList = persistenceService.findAllArtists();
        if(CollectionUtils.isEmpty(artistList))
            throw new RuntimeException("Empty artist list!");
        mostPopularArtist = wynkFollowUtil.getMostPopularArtist(artistList);
        if (mostPopularArtist == null) {
            throw new RuntimeException("Most popular artist is not present.");
        }
        wynkFollowUtil.updateMostPopularArtist(mostPopularArtist);
        response = new PopularArtistResponse(mostPopularArtist.getUserName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<PopularSongResponse> getMostPopularSong() {
        PopularSongResponse response = null;
        Song mostPopularSong = null;
        mostPopularSong = wynkFollowUtil.getMostPopularSong();
        if(mostPopularSong != null)
            return new ResponseEntity<>(new PopularSongResponse(mostPopularSong.getSongName()), HttpStatus.OK);
        LOGGER.info("Most popular song not present in the cache. Going to fetch it from db.");
        List<Song> allSongs = persistenceService.findAllSongs();
        if(allSongs == null)
            throw new RuntimeException("There are no songs available in the database.");
        mostPopularSong = wynkFollowUtil.getMostPopularSong(allSongs);
        if (mostPopularSong == null) {
            throw new RuntimeException("Most popular song is not present.");
        }
        wynkFollowUtil.updateMostPopularSong(mostPopularSong);
        response = new PopularSongResponse(mostPopularSong.getSongName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
