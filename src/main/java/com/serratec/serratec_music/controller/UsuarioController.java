package com.serratec.serratec_music.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.serratec.serratec_music.domain.Usuario;
import com.serratec.serratec_music.repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuário", description = "Cadastro e gerenciamento de usuários da plataforma Serratec Music")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ====================== LISTAR TODOS ======================
    @GetMapping
    @Operation(
        summary = "Lista todos os usuários",
        description = "Retorna uma lista com todos os usuários cadastrados no sistema Serratec Music."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = Usuario.class), mediaType = "application/json"),
            description = "Lista retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Erro de autenticação"),
        @ApiResponse(responseCode = "403", description = "Acesso negado"),
        @ApiResponse(responseCode = "404", description = "Recurso não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    // ====================== BUSCAR POR ID ======================
    @GetMapping("/{id}")
    @Operation(
        summary = "Busca um usuário pelo ID",
        description = "Retorna os dados de um usuário específico a partir do seu identificador (ID)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = Usuario.class), mediaType = "application/json"),
            description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        return usuarioOpt.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ====================== INSERIR ======================
    @PostMapping
    @Operation(
        summary = "Cadastra um novo usuário",
        description = "Cria um novo usuário juntamente com seu perfil (OneToOne)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201",
            content = @Content(schema = @Schema(implementation = Usuario.class), mediaType = "application/json"),
            description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos enviados"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<Usuario> inserir(@Valid @RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    // ====================== ATUALIZAR ======================
    @PutMapping("/{id}")
    @Operation(
        summary = "Atualiza os dados de um usuário existente",
        description = "Permite alterar informações do usuário e do seu perfil associado."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            content = @Content(schema = @Schema(implementation = Usuario.class), mediaType = "application/json"),
            description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        Optional<Usuario> existente = usuarioRepository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        usuario.setId(id);
        usuario.getPerfil().setId(existente.get().getPerfil().getId());
        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    // ====================== DELETAR ======================
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Remove um usuário",
        description = "Exclui um usuário e seu perfil do banco de dados."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
