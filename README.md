# 🎵 Serratec Music API

## 🧑‍💻 Desenvolvedor
**Nome:** Alisson Lima de Souza  
**Curso:** Serratec - Residência em TIC - Back-end  
**Disciplina:** Programação Back-End API
**Professor:** Romulo 
**Data de Entrega:** 20/10/2025  

---

## 🚀 Descrição do Projeto

A **Serratec Music API** é uma aplicação RESTful desenvolvida em **Spring Boot** com **PostgreSQL**, que gerencia usuários, artistas, músicas e playlists.  
Ela representa o núcleo da plataforma **Serratec Music**, responsável por centralizar e gerenciar os dados da aplicação musical, incluindo relacionamentos entre usuários, perfis, músicas e playlists.

---

## 🧩 Estrutura e Arquitetura

O projeto segue uma arquitetura organizada em pacotes:

```
src/
 └── main/
      └── java/
           └── br.com.serratec.music/
                ├── controller/        # Controladores REST
                ├── domain/            # Entidades e Enums
                ├── repository/        # Interfaces JPA Repository
                ├── exception/         # Tratamento de exceções
                └── SerratecMusicApplication.java
```

---

## 🧱 Modelagem das Entidades

### 🧍 Usuario
- **id (Long)**
- **nome (String)**
- **email (String)**
- **perfil (OneToOne → Perfil)**
- **playlists (OneToMany → Playlist)**

### 📄 Perfil
- **id (Long)**
- **telefone (String)**
- **dataNascimento (LocalDate)**

### 🎤 Artista
- **id (Long)**
- **nome (String)**
- **nacionalidade (String)**
- **musicas (ManyToMany → Musica)**

### 🎶 Musica
- **id (Long)**
- **titulo (String)**
- **minutos (Integer)**
- **genero (Enum: GeneroMusical)**
- **artistas (ManyToMany → Artista)**
- **playlists (ManyToMany → Playlist)**

### 📀 Playlist
- **id (Long)**
- **nome (String)**
- **descricao (String)**
- **usuario (ManyToOne → Usuario)**
- **musicas (ManyToMany → Musica)**

---

## 🔗 Relacionamentos

| Relacionamento | Tipo | Descrição |
|----------------|------|-----------|
| Usuario ↔ Perfil | OneToOne | Cada usuário possui um único perfil. |
| Usuario ↔ Playlist | OneToMany | Um usuário pode criar várias playlists. |
| Musica ↔ Artista | ManyToMany | Uma música pode ter vários artistas (feats). |
| Playlist ↔ Musica | ManyToMany | Uma playlist pode conter várias músicas. |

---

## ⚙️ Endpoints Principais

### 🧍 Usuários (`/usuarios`)
- **GET** `/usuarios` → Lista todos os usuários  
- **GET** `/usuarios/{id}` → Busca usuário por ID  
- **POST** `/usuarios` → Cria um usuário **junto com o Perfil** (JSON aninhado)  
- **PUT** `/usuarios/{id}` → Atualiza um usuário  
- **DELETE** `/usuarios/{id}` → Remove um usuário  

#### Exemplo JSON (POST /usuarios)
```json
{
  "nome": "João Silva",
  "email": "joao@gmail.com",
  "perfil": {
    "telefone": "24988887777",
    "dataNascimento": "1995-06-12"
  }
}
```

---

### 🎤 Artistas (`/artistas`)
CRUD completo de artistas.

---

### 🎶 Músicas (`/musicas`)
CRUD completo de músicas.

#### Enum de gênero musical:
```java
public enum GeneroMusical {
    ROCK, POP, SAMBA, FUNK, SERTANEJO
}
```

---

### 📀 Playlists (`/playlists`)
- **POST /playlists** → Cria uma nova playlist associada a um usuário existente.  
- **PUT /playlists/{id}** → Atualiza músicas de uma playlist existente (adicionando/removendo).  
- **CRUD completo.**

#### Exemplo JSON (POST /playlists)
```json
{
  "nome": "Minhas Favoritas",
  "descricao": "As melhores músicas do dia",
  "usuarioId": 1
}
```

#### Exemplo JSON (PUT /playlists/{id})
```json
{
  "nome": "Minhas Favoritas Atualizada",
  "descricao": "Versão revisada",
  "musicasIds": [2, 5, 7]
}
```

---

## 🧰 Tecnologias Utilizadas

| Tecnologia | Finalidade |
|-------------|-------------|
| **Java 17** | Linguagem principal |
| **Spring Boot 3.x** | Framework para construção da API REST |
| **Spring Data JPA** | Persistência de dados |
| **PostgreSQL** | Banco de dados relacional |
| **Hibernate Validator** | Validação de dados (Bean Validation) |
| **Springdoc OpenAPI / Swagger UI** | Documentação automática da API |
| **Lombok** | Redução de boilerplate (getters/setters, construtores) |

---

## ✅ Validações Implementadas

- `@NotBlank`, `@Email`, `@Size`, `@NotNull` para garantir consistência dos dados.  
- `@Valid` aplicado em entidades e DTOs.  
- Tratamento global de exceções com `@ControllerAdvice`, retornando respostas padronizadas:

```json
{
  "status": 400,
  "erro": "Validação de campo",
  "mensagem": "O campo 'email' é obrigatório."
}
```

---

## 📘 Documentação Swagger

Após iniciar o projeto, acesse no navegador:

👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
ou  
👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

A documentação é gerada automaticamente via **Springdoc OpenAPI**, contendo:
- Descrições detalhadas dos endpoints (`@Operation`)
- Modelos de entidades (`@Schema`)

---

## 🗄️ Banco de Dados

### Exemplo de configuração (`application.properties`)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/serratec_music
spring.datasource.username=postgres
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

---

## ▶️ Como Executar o Projeto

1. **Clonar o repositório**
   ```bash
   https://github.com/alisson10000/serratec-music.git
   ```

2. **Configurar o banco de dados PostgreSQL**
   - Criar o banco `serratec_music`
   - Atualizar `application.properties` com usuário e senha corretos

3. **Executar o projeto**
   ```bash
   mvn spring-boot:run
   ```
   ou executar diretamente a classe `SerratecMusicApplication.java`

4. **Acessar a API**
   - Base URL: [http://localhost:8080](http://localhost:8080)
   - Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## 🧾 Licença

Projeto desenvolvido exclusivamente para fins acadêmicos no contexto do curso **Serratec - Residência em TIC - 2025**.

---

## 💡 Observação Final

O projeto aplica os principais conceitos vistos em aula:
- Arquitetura RESTful organizada em pacotes  
- Relacionamentos JPA (1:1, 1:N, N:N)  
- Validação de dados e tratamento global de exceções  
- Documentação automática com Swagger/OpenAPI  

> 🎓 *Desenvolvido com dedicação e aprendizado contínuo — Serratec Music: o som da sua evolução!*
