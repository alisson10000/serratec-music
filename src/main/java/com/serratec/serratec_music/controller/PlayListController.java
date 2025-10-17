package com.serratec.serratec_music.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.serratec.serratec_music.domain.Musica;
import com.serratec.serratec_music.domain.PlayList;
import com.serratec.serratec_music.domain.Usuario;
import com.serratec.serratec_music.repository.MusicaRepository;
import com.serratec.serratec_music.repository.PlayListRepository;
import com.serratec.serratec_music.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/playlists")
@Tag(name = "Playlist", description = "Gerenciamento de playlists dos usuários na plataforma Serratec Music")
public class PlayListController {

    @Autowired
    private PlayListRepository playListRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    // ====================== GET - LISTAR TODAS ======================
    @GetMapping
    @Operation(
        summary = "Lista todas as playlists",
        description = "Retorna uma lista com todas as playlists cadastradas no sistema Serratec Music."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Lista de playlists retornada com sucesso",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PlayList.class))),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<PlayList>> listar() {
        List<PlayList> playlists = playListRepository.findAll();
        return ResponseEntity.ok(playlists);
    }

    // ====================== GET - BUSCAR POR ID ======================
    @GetMapping("/{id}")
    @Operation(
        summary = "Busca uma playlist pelo ID",
        description = "Retorna os dados de uma playlist específica a partir do seu identificador (ID)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Playlist encontrada",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PlayList.class))),
        @ApiResponse(responseCode = "404", description = "Playlist não encontrada")
    })
    public ResponseEntity<PlayList> buscarPorId(@PathVariable Long id) {
        PlayList playlist = playListRepository.findById(id).orElse(null);
        if (playlist == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(playlist);
    }

    // ====================== POST - CRIAR PLAYLIST ======================
    @PostMapping
    @Operation(
        summary = "Cadastra uma nova playlist",
        description = "Cria uma nova playlist associando-a a um usuário já existente e opcionalmente vinculando músicas."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            description = "Playlist criada com sucesso",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PlayList.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário inexistente"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<?> criar(@Valid @RequestBody PlayList playlist) {
        // Valida se o usuário existe
        if (playlist.getUsuario() == null || playlist.getUsuario().getId() == null) {
            return ResponseEntity.badRequest().body("É necessário informar o ID do usuário.");
        }

        Usuario usuario = usuarioRepository.findById(playlist.getUsuario().getId()).orElse(null);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuário informado não existe.");
        }

        // Associa o usuário à playlist
        playlist.setUsuario(usuario);

        // Se vier com músicas, carrega do banco
        if (playlist.getMusicas() != null && !playlist.getMusicas().isEmpty()) {
            List<Long> ids = playlist.getMusicas().stream().map(Musica::getId).toList();
            List<Musica> musicas = musicaRepository.findAllById(ids);
            playlist.setMusicas(musicas);
        }

        PlayList nova = playListRepository.save(playlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(nova);
    }

    // ====================== PUT - ATUALIZAR PLAYLIST ======================
    @PutMapping("/{id}")
    @Operation(
        summary = "Atualiza uma playlist existente",
        description = "Permite alterar nome, descrição e músicas associadas de uma playlist."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Playlist atualizada com sucesso",
            content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = PlayList.class))),
        @ApiResponse(responseCode = "404", description = "Playlist não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<?> atualizar(@PathVariable Long id, @Valid @RequestBody PlayList playlistAtualizada) {
        PlayList existente = playListRepository.findById(id).orElse(null);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        // Atualiza campos básicos
        existente.setNome(playlistAtualizada.getNome());
        existente.setDescricao(playlistAtualizada.getDescricao());

        // Atualiza músicas (se vierem)
        if (playlistAtualizada.getMusicas() != null) {
            List<Long> ids = playlistAtualizada.getMusicas().stream().map(Musica::getId).toList();
            List<Musica> musicas = musicaRepository.findAllById(ids);
            existente.setMusicas(musicas);
        }

        playListRepository.save(existente);
        return ResponseEntity.ok(existente);
    }

    // ====================== DELETE - REMOVER PLAYLIST ======================
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Remove uma playlist",
        description = "Exclui permanentemente uma playlist do banco de dados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Playlist deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Playlist não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!playListRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        playListRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
