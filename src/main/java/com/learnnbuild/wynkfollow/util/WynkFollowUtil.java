package com.learnnbuild.wynkfollow.util;

import com.learnnbuild.wynkfollow.configuration.CollectionsBean;
import com.learnnbuild.wynkfollow.entities.Artist;
import com.learnnbuild.wynkfollow.entities.PlayList;
import com.learnnbuild.wynkfollow.entities.Song;
import com.learnnbuild.wynkfollow.entities.User;
import com.learnnbuild.wynkfollow.model.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.learnnbuild.wynkfollow.service.MiscRequestService.MOST_POPULAR_ARTIST;
import static com.learnnbuild.wynkfollow.service.MiscRequestService.MOST_POPULAR_SONG;

@Component
public class WynkFollowUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WynkFollowUtil.class);

    @Autowired
    private CollectionsBean collectionsBean;

    public Artist getMostPopularArtist() {
        Map<String, Object> popularityMap = collectionsBean.getPopularityMap();
        if(popularityMap != null) {
            Object o = popularityMap.get(MOST_POPULAR_ARTIST);
            if(o != null) {
                return (Artist) o;
            }
        }
        return null;
    }

    public void updateMostPopularArtist(Artist artist) {
        Map<String, Object> popularityMap = collectionsBean.getPopularityMap();
        popularityMap.put(MOST_POPULAR_ARTIST, artist);
    }

    public Song getMostPopularSong() {
        Map<String, Object> popularityMap = collectionsBean.getPopularityMap();
        if(popularityMap != null) {
            Object o = popularityMap.get(MOST_POPULAR_SONG);
            if(o != null) {
                return (Song) o;
            }
        }
        return null;
    }

    public void updateMostPopularSong(Song song) {
        Map<String, Object> popularityMap = collectionsBean.getPopularityMap();
        popularityMap.put(MOST_POPULAR_SONG, song);
    }

    public Artist getMostPopularArtist(List<Artist> artistList) {
        int max = 0;
        Artist mostPopularArtist = null;
        for (Artist artist : artistList) {
            if (artist.getFollowers().size() > max) {
                max = artist.getFollowers().size();
                mostPopularArtist = artist;
            }
        }
        return mostPopularArtist;
    }

    public Response buildFollowResponse(String userName, List<String> artists) {
        StringBuilder message = new StringBuilder();
        message.append("user ").append(userName).append(" has started following ");
        for(int i=0; i<artists.size(); i++) {
            message.append(artists.get(i));
            if(i != artists.size()-1) {
                message.append(" and ");
            }
        }
        return new Response("ok", message.toString());
    }

    public Response buildUnFollowResponse(String userName, List<String> artists) {
        StringBuilder message = new StringBuilder();
        message.append(userName).append(" stopped following ");
        for(int i=0; i<artists.size(); i++) {
            message.append(artists.get(i));
            if(i != artists.size()-1) {
                message.append(" and ");
            }
        }
        return new Response("ok", message.toString());
    }

    public Response buildPulishResponse() {
        String message = "song published against artists";
        return new Response("ok", message);
    }

    public void checkAndUpdateMostPopularArtist(Artist artist) {
        Map<String, Object> popularityMap = collectionsBean.getPopularityMap();
        if(popularityMap != null) {
            Object o = popularityMap.get(MOST_POPULAR_ARTIST);
            if(o != null) {
                Artist artistFromMap = (Artist) o;
                if(artistFromMap.getFollowers().size() < artist.getFollowers().size()) {
                    popularityMap.put(MOST_POPULAR_ARTIST, artist);
                }
            } else {
                popularityMap.put(MOST_POPULAR_ARTIST, artist);
            }
        }
    }

    public void checkAndUpdateMostPopularSong(Song song) {
        Map<String, Object> popularityMap = collectionsBean.getPopularityMap();
        if(popularityMap != null) {
            Object o = popularityMap.get(MOST_POPULAR_SONG);
            if(o != null) {
                Song songFromMap = (Song) o;
                if(songFromMap.getPlayListsHavingCurrentSong().size() < song.getPlayListsHavingCurrentSong().size()) {
                    popularityMap.put(MOST_POPULAR_SONG, song);
                }
            } else {
                popularityMap.put(MOST_POPULAR_SONG, song);
            }
        }
    }

    public void checkAndRemoveMostPopularArtist(Artist artist) {
        Map<String, Object> popularityMap = collectionsBean.getPopularityMap();
        if(popularityMap != null) {
            Object o = popularityMap.get(MOST_POPULAR_ARTIST);
            if(o != null) {
                Artist artistFromMap = (Artist) o;
                if( artistFromMap.getUserName().equals(artist.getUserName()))
                    popularityMap.remove(MOST_POPULAR_ARTIST);
            }
        }
    }

    public void checkAndRemoveMostPopularSong(Song song) {
        Map<String, Object> popularityMap = collectionsBean.getPopularityMap();
        if(popularityMap != null) {
            Object o = popularityMap.get(MOST_POPULAR_SONG);
            if(o != null) {
                Song songFromMap = (Song) o;
                if( songFromMap.getSongName().equals(song.getSongName()))
                    popularityMap.remove(MOST_POPULAR_SONG);
            }
        }
    }

    public Song getMostPopularSong(List<Song> allSongs) {
        int max = 0;
        Song mostPopularSong = null;
        for (Song song : allSongs) {
            if(song.getPlayListsHavingCurrentSong().size() > max) {
                max = song.getPlayListsHavingCurrentSong().size();
                mostPopularSong = song;
            }
        }
        return mostPopularSong;
    }

    public void checkAndRemoveMostPopularSong(Set<Song> songsToRemove) {
        Map<String, Object> popularityMap = collectionsBean.getPopularityMap();
        if(popularityMap != null) {
            Object o = popularityMap.get(MOST_POPULAR_SONG);
            if(o != null) {
                Song songFromMap = (Song) o;
                for (Song song : songsToRemove) {
                    if(song.getSongName().equals(songFromMap.getSongName())) {
                        popularityMap.remove(MOST_POPULAR_SONG);
                        return;
                    }
                }
            }
        }
    }

    public void removePlaylistReferenceFromSongs(Set<Song> songsToRemove, User user) {
        for (Song song : songsToRemove) {
            Set<PlayList> playListsHavingCurrentSong = song.getPlayListsHavingCurrentSong();
            playListsHavingCurrentSong.remove(user.getPlaylist());
        }
    }
}
