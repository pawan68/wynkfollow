package com.learnnbuild.wynkfollow.repository;

import com.learnnbuild.wynkfollow.entities.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findBySongName(String songName);
}
