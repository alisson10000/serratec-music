package com.serratec.serratec_music.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "perfil")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo telefone é obrigatório.")
    @Pattern(
        regexp = "\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}",
        message = "O telefone deve estar no formato (XX)XXXXX-XXXX."
    )
    @Column(nullable = false, length = 20)
    private String telefone;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @Past(message = "A data de nascimento deve ser anterior à data atual.")
    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    // Lado inverso do relacionamento (não cria coluna nova)
    @JsonIgnore
    @OneToOne(mappedBy = "perfil")
    private Usuario usuario;

    //  Construtores
    public Perfil() {}

    public Perfil(Long id, String telefone, LocalDate dataNascimento) {
        this.id = id;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
    }

    // 🔹 Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
