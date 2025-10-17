package com.serratec.serratec_music.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "playlist")
public class PlayList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_playlist")
    private Long id;

    @NotBlank(message = "O nome da playlist é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    @Size(max = 300, message = "A descrição deve ter no máximo 300 caracteres.")
    @Column(length = 300)
    private String descricao;

    // ManyToOne com Usuário (cada playlist pertence a um usuário)
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnoreProperties("playlists")
    private Usuario usuario;

    // ManyToMany com Música (lado dono)
    @ManyToMany
    @JoinTable(
        name = "playlist_musica",
        joinColumns = @JoinColumn(name = "playlist_id"),
        inverseJoinColumns = @JoinColumn(name = "musica_id")
    )
    @JsonIgnoreProperties("playlists")
    private List<Musica> musicas = new ArrayList<>();

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public List<Musica> getMusicas() { return musicas; }
    public void setMusicas(List<Musica> musicas) { this.musicas = musicas; }
}
