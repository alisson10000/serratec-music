package com.serratec.serratec_music.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

    @Value("${serratecmusic.openapi.dev-url}")
    private String devUrl;

    @Value("${serratecmusic.openapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI serratecMusicOpenAPI() {
        //  Servidor de Desenvolvimento
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Servidor de desenvolvimento da API Serratec Music");

        //  Servidor de Produção
        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Servidor de produção da API Serratec Music");

        //  Contato
        Contact contact = new Contact();
        contact.setName("Alisson Lima de Souza");
        contact.setEmail("alisson.lima.souza@gmail.com");
        contact.setUrl("https://github.com/alisson10000");

        //  Licença
        License mitLicense = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        //  Informações Gerais da API
        Info info = new Info()
                .title("Serratec Music API")
                .version("1.0.0")
                .description("API RESTful desenvolvida como trabalho prático individual do curso Serratec FullStack. "
                        + "A aplicação gerencia usuários, perfis, artistas, músicas e playlists, aplicando os conceitos de JPA, validação e documentação com Swagger/OpenAPI.")
                .contact(contact)
                .license(mitLicense)
                .termsOfService("https://github.com/alisson10000/serratec-music");

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer));
    }
}
