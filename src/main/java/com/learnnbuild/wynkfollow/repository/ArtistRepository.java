package com.learnnbuild.wynkfollow.repository;

import com.learnnbuild.wynkfollow.entities.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, String> {
    Optional<Artist> findByUserName(String userName);
}

