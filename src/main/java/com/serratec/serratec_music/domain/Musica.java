package com.serratec.serratec_music.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.serratec.serratec_music.enums.GeneroMusical;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "musica")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_musica")
    private Long id;

    @NotBlank(message = "O título da música é obrigatório.")
    @Size(max = 100, message = "O título deve ter no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String titulo;

    @NotNull(message = "O tempo de duração é obrigatório.")
    @Positive(message = "A duração deve ser um número positivo.")
    @Column(nullable = false)
    private Integer minutos;

    @NotNull(message = "O gênero musical é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GeneroMusical genero;

    // ManyToMany com Artista
    @ManyToMany
    @JoinTable(
        name = "musica_artista",
        joinColumns = @JoinColumn(name = "musica_id"),
        inverseJoinColumns = @JoinColumn(name = "artista_id")
    )
    @JsonIgnoreProperties("musicas")
    private List<Artista> artistas = new ArrayList<>();

    // ManyToMany com Playlist (lado inverso)
    @ManyToMany(mappedBy = "musicas")
    @JsonIgnore
    private List<PlayList> playlists = new ArrayList<>();

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getMinutos() { return minutos; }
    public void setMinutos(Integer minutos) { this.minutos = minutos; }

    public GeneroMusical getGenero() { return genero; }
    public void setGenero(GeneroMusical genero) { this.genero = genero; }

    public List<Artista> getArtistas() { return artistas; }
    public void setArtistas(List<Artista> artistas) { this.artistas = artistas; }

    public List<PlayList> getPlaylists() { return playlists; }
    public void setPlaylists(List<PlayList> playlists) { this.playlists = playlists; }
}
