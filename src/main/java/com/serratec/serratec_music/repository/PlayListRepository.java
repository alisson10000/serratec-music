package com.serratec.serratec_music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serratec.serratec_music.domain.PlayList;

public interface PlayListRepository extends JpaRepository<PlayList, Long> {

}
