package com.serratec.serratec_music.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serratec.serratec_music.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
