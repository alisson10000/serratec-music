package com.serratec.serratec_music.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo nome é obrigatório.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "O campo e-mail é obrigatório.")
    @Email(message = "O e-mail informado não é válido.")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres.")
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    //  Relacionamento OneToOne — um usuário tem um perfil
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_perfil", referencedColumnName = "id")
    @Valid
    private Perfil perfil;

    //  Construtores
    public Usuario() {}

    public Usuario(Long id, String nome, String email, Perfil perfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.perfil = perfil;
    }

    //  Getters e Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }
}
