package com.learnnbuild.wynkfollow.controller;

import com.learnnbuild.wynkfollow.model.request.Follow;
import com.learnnbuild.wynkfollow.model.request.Publish;
import com.learnnbuild.wynkfollow.model.request.UnFollow;
import com.learnnbuild.wynkfollow.model.response.FollowerCountResponse;
import com.learnnbuild.wynkfollow.model.response.PopularArtistResponse;
import com.learnnbuild.wynkfollow.model.response.PopularSongResponse;
import com.learnnbuild.wynkfollow.model.response.Response;
import com.learnnbuild.wynkfollow.service.FollowService;
import com.learnnbuild.wynkfollow.service.MiscRequestService;
import com.learnnbuild.wynkfollow.service.PublishService;
import com.learnnbuild.wynkfollow.service.UnFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WynkFollowController {

    @Autowired
    private FollowService followService;

    @Autowired
    private UnFollowService unFollowService;

    @Autowired
    private PublishService publishService;

    @Autowired
    private MiscRequestService miscRequestService;

    @RequestMapping("/wynk/follow")
    public ResponseEntity<Response> follow(@RequestBody Follow request) {
        return followService.process(request);
    }

    @RequestMapping("/wynk/unfollow")
    public ResponseEntity<Response> unFollow(@RequestBody UnFollow request) {
        return unFollowService.process(request);
    }

    @RequestMapping("/wynk/publish")
    public ResponseEntity<Response> publish(@RequestBody Publish request) {
        return publishService.process(request);
    }

    @RequestMapping("/wynk/playlist")
    public ResponseEntity<List<String>> getSongsInPlayList(String user) {
        return miscRequestService.getSongsInPlayList(user);
    }

    @RequestMapping("/wynk/follow/count")
    public ResponseEntity<FollowerCountResponse> getFollowerCount(@RequestParam(name = "artist") String artist) {
        return miscRequestService.getFollowerCount(artist);
    }

    @RequestMapping("/wynk/popular/song")
    public ResponseEntity<PopularSongResponse> getMostPopularSong() {
        return miscRequestService.getMostPopularSong();
    }

    @RequestMapping("/wynk/popular/artist")
    public ResponseEntity<PopularArtistResponse> getMostPopularArtist() {
        return miscRequestService.getMostPopularArtist();
    }


}
