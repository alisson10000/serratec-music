package com.serratec.serratec_music.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.serratec.serratec_music.domain.Artista;
import com.serratec.serratec_music.domain.Musica;
import com.serratec.serratec_music.repository.ArtistaRepository;
import com.serratec.serratec_music.repository.MusicaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/musicas")
@Tag(name = "Música", description = "Cadastro e gerenciamento de músicas da plataforma Serratec Music")
public class MusicaController {

    @Autowired
    private MusicaRepository musicaRepository;

    @Autowired
    private ArtistaRepository artistaRepository;

    // ====================== LISTAR TODAS ======================
    @GetMapping
    @Operation(
        summary = "Lista todas as músicas",
        description = "Retorna uma lista com todas as músicas cadastradas no sistema Serratec Music."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = Musica.class), mediaType = "application/json"),
            description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public List<Musica> listar() {
        return musicaRepository.findAll();
    }

    // ====================== BUSCAR POR ID ======================
    @GetMapping("/{id}")
    @Operation(
        summary = "Busca uma música pelo ID",
        description = "Retorna os dados de uma música específica a partir do seu identificador (ID)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = Musica.class), mediaType = "application/json"),
            description = "Música encontrada"),
        @ApiResponse(responseCode = "404", description = "Música não encontrada")
    })
    public ResponseEntity<Musica> buscarPorId(@PathVariable Long id) {
        return musicaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ====================== INSERIR ======================
    @PostMapping
    @Operation(
        summary = "Cadastra uma nova música",
        description = "Cria uma nova música e associa aos artistas já cadastrados informando seus IDs."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            content = @Content(schema = @Schema(implementation = Musica.class), mediaType = "application/json"),
            description = "Música criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos enviados"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Musica> criar(@Valid @RequestBody Musica musica) {
        if (musica.getArtistas() != null) {
            musica.setArtistas(
                artistaRepository.findAllById(
                    musica.getArtistas().stream().map(Artista::getId).toList()
                )
            );
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(musicaRepository.save(musica));
    }

    // ====================== ATUALIZAR ======================
    @PutMapping("/{id}")
    @Operation(
        summary = "Atualiza uma música existente",
        description = "Permite alterar as informações de uma música e sua lista de artistas associados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = Musica.class), mediaType = "application/json"),
            description = "Música atualizada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Música não encontrada"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos enviados")
    })
    public ResponseEntity<Musica> atualizar(@PathVariable Long id, @Valid @RequestBody Musica musica) {
        return musicaRepository.findById(id)
                .map(m -> {
                    m.setTitulo(musica.getTitulo());
                    m.setMinutos(musica.getMinutos());
                    m.setGenero(musica.getGenero());

                    if (musica.getArtistas() != null) {
                        List<Artista> artistas = artistaRepository.findAllById(
                            musica.getArtistas().stream().map(Artista::getId).toList()
                        );
                        m.setArtistas(artistas);
                    }

                    musicaRepository.save(m);
                    return ResponseEntity.ok(m);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ====================== DELETAR ======================
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Remove uma música",
        description = "Exclui uma música e suas associações com artistas do banco de dados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Música deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Música não encontrada")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!musicaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        musicaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
