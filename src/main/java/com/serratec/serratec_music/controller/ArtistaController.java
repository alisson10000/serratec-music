package com.serratec.serratec_music.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.serratec.serratec_music.domain.Artista;
import com.serratec.serratec_music.repository.ArtistaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/artistas")
@Tag(name = "Artista", description = "Cadastro e gerenciamento de artistas da plataforma Serratec Music")
public class ArtistaController {

    @Autowired
    private ArtistaRepository artistaRepository;

    // ====================== LISTAR TODOS ======================
    @GetMapping
    @Operation(
        summary = "Lista todos os artistas",
        description = "Retorna uma lista com todos os artistas cadastrados no sistema Serratec Music."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = Artista.class), mediaType = "application/json"),
            description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public List<Artista> listar() {
        return artistaRepository.findAll();
    }

    // ====================== BUSCAR POR ID ======================
    @GetMapping("/{id}")
    @Operation(
        summary = "Busca um artista pelo ID",
        description = "Retorna os dados de um artista específico a partir do seu identificador (ID)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = Artista.class), mediaType = "application/json"),
            description = "Artista encontrado"),
        @ApiResponse(responseCode = "404", description = "Artista não encontrado")
    })
    public ResponseEntity<Artista> buscarPorId(@PathVariable Long id) {
        return artistaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ====================== INSERIR ======================
    @PostMapping
    @Operation(
        summary = "Cadastra um novo artista",
        description = "Cria um novo artista no sistema Serratec Music."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            content = @Content(schema = @Schema(implementation = Artista.class), mediaType = "application/json"),
            description = "Artista criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos enviados"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Artista> criar(@Valid @RequestBody Artista artista) {
        Artista novo = artistaRepository.save(artista);
        return ResponseEntity.status(HttpStatus.CREATED).body(novo);
    }

    // ====================== ATUALIZAR ======================
    @PutMapping("/{id}")
    @Operation(
        summary = "Atualiza os dados de um artista existente",
        description = "Permite alterar informações de um artista já cadastrado no sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = Artista.class), mediaType = "application/json"),
            description = "Artista atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Artista não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos enviados")
    })
    public ResponseEntity<Artista> atualizar(@PathVariable Long id, @Valid @RequestBody Artista artista) {
        if (!artistaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        artista.setId(id);
        Artista atualizado = artistaRepository.save(artista);
        return ResponseEntity.ok(atualizado);
    }

    // ====================== DELETAR ======================
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Remove um artista",
        description = "Exclui um artista do banco de dados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Artista deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Artista não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!artistaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        artistaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
