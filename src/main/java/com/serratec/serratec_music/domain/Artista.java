package com.serratec.serratec_music.domain;

import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "artista")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_artista")
    private Long id;

    @NotBlank(message = "O nome do artista é obrigatório.")
    @Size(max = 100, message = "O nome do artista deve ter no máximo 100 caracteres.")
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "A nacionalidade é obrigatória.")
    @Size(max = 50, message = "A nacionalidade deve ter no máximo 50 caracteres.")
    @Column(name = "nacionalidade", nullable = false, length = 50)
    private String nacionalidade;

    @ManyToMany(mappedBy = "artistas")
    private List<Musica> musicas;

    // ---------------- GETTERS E SETTERS ---------------- //
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getNacionalidade() {
        return nacionalidade;
    }
    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }
    public List<Musica> getMusicas() {
        return musicas;
    }
    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }
}
