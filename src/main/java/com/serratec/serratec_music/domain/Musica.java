package com.serratec.serratec_music.domain;

import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


import com.serratec.serratec_music.enums.GeneroMusical;

@Entity
@Table(name = "musica")
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_musica")
    private Long id;

    @NotBlank(message = "O título da música é obrigatório.")
    @Size(max = 100, message = "O título da música deve ter no máximo 100 caracteres.")
    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @NotNull(message = "O tempo de duração é obrigatório.")
    @Positive(message = "A duração deve ser um número positivo.")
    @Column(name = "minutos", nullable = false)
    private Integer minutos;

    @NotNull(message = "O gênero musical é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false, length = 20)
    private GeneroMusical genero;

    @ManyToMany
    @JoinTable(
        name = "musica_artista",
        joinColumns = @JoinColumn(name = "id_musica"),
        inverseJoinColumns = @JoinColumn(name = "id_artista")
    )
     private List<Artista> artistas;

    // ---------------- GETTERS E SETTERS ---------------- //
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public Integer getMinutos() {
        return minutos;
    }
    public void setMinutos(Integer minutos) {
        this.minutos = minutos;
    }
    public GeneroMusical getGenero() {
        return genero;
    }
    public void setGenero(GeneroMusical genero) {
        this.genero = genero;
    }
    public List<Artista> getArtistas() {
        return artistas;
    }
    public void setArtistas(List<Artista> artistas) {
        this.artistas = artistas;
    }
}
