package com.learnnbuild.wynkfollow.repository;

import com.learnnbuild.wynkfollow.entities.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Long> {
}
