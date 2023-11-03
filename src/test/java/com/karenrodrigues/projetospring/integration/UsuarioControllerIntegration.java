package com.karenrodrigues.projetospring.integration;

import com.karenrodrigues.projetospring.domain.ProjetoUser;
import com.karenrodrigues.projetospring.domain.Usuario;
import com.karenrodrigues.projetospring.repository.UserRepository;
import com.karenrodrigues.projetospring.repository.UsuarioRepository;
import com.karenrodrigues.projetospring.requests.UsuarioPostRequestBody;
import com.karenrodrigues.projetospring.util.UsuarioCreator;
import com.karenrodrigues.projetospring.util.UsuarioPostRequestBodyCreator;
import com.karenrodrigues.projetospring.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //deleta todos os dados do banco apos cada teste
class UsuarioControllerIntegration {

    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UserRepository userRepository;

    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "testRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("admin", "admin123");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("retorna a lista de usuarios com o page")
    void list_ReturnsListOfUsersInsidePageObject_WhenSuccessful() {

        Usuario savedUsuario = usuarioRepository.save(UsuarioCreator.createUsuarioToBeSaved());

        ProjetoUser user = new ProjetoUser();
        user.setId(1);
        user.setPassword("{bcrypt}$2a$10$F7QFfu13l0M0nWDMyKaH6e1amtngcHE4XwTJyZtbVYTwZ.q9iGsmi");
        user.setUsername("admin");
        user.setAuthorities("ROLE_ADMIN, ROLE_USER");

        userRepository.save(user);

        String expectedNome = savedUsuario.getNome();
        String expectedProfissao = savedUsuario.getProfissao();
        Integer expectedIdade = savedUsuario.getIdade();

        PageableResponse<Usuario> usuarioPage = testRestTemplateRoleAdmin.exchange("/usuarios/page", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Usuario>>() {
                }).getBody();

        Assertions.assertThat(usuarioPage).isNotNull();

        Assertions.assertThat(usuarioPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(usuarioPage.toList().get(0).getNome()).isEqualTo(expectedNome);
        Assertions.assertThat(usuarioPage.toList().get(0).getProfissao()).isEqualTo(expectedProfissao);
        Assertions.assertThat(usuarioPage.toList().get(0).getIdade()).isEqualTo(expectedIdade);
    }

    @Test
    @DisplayName("lista de usuarios sem o page")
    void listNonPageable_ReturnsListOfUsersInsidePageObject_WhenSuccessful() {
        Usuario savedUsuario = usuarioRepository.save(UsuarioCreator.createUsuarioToBeSaved());

        String expectedNome = savedUsuario.getNome();
        String expectedProfissao = savedUsuario.getProfissao();
        Integer expectedIdade = savedUsuario.getIdade();

        ProjetoUser user = new ProjetoUser();
        user.setId(1);
        user.setPassword("{bcrypt}$2a$10$F7QFfu13l0M0nWDMyKaH6e1amtngcHE4XwTJyZtbVYTwZ.q9iGsmi");
        user.setUsername("admin");
        user.setAuthorities("ROLE_ADMIN, ROLE_USER");

        userRepository.save(user);

        List<Usuario> usuarios = testRestTemplateRoleAdmin.exchange("/usuarios", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Usuario>>() {
                }).getBody();

        Assertions.assertThat(usuarios)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(usuarios.get(0).getNome()).isEqualTo(expectedNome);
        Assertions.assertThat(usuarios.get(0).getProfissao()).isEqualTo(expectedProfissao);
        Assertions.assertThat(usuarios.get(0).getIdade()).isEqualTo(expectedIdade);
    }

    @Test
    @DisplayName("Deve retornar o id com sucesso")
    void findById_ReturnsUser_WhenSuccessful() {
        Usuario savedUsuario = usuarioRepository.save(UsuarioCreator.createUsuarioToBeSaved());

        ProjetoUser user = new ProjetoUser();
        user.setId(1);
        user.setPassword("{bcrypt}$2a$10$F7QFfu13l0M0nWDMyKaH6e1amtngcHE4XwTJyZtbVYTwZ.q9iGsmi");
        user.setUsername("admin");
        user.setAuthorities("ROLE_ADMIN, ROLE_USER");

        userRepository.save(user);

        Integer expectedId = savedUsuario.getId();

        Usuario usuario = testRestTemplateRoleAdmin.getForObject("/usuarios/{id}", Usuario.class, expectedId);

        Assertions.assertThat(usuario).isNotNull();

        Assertions.assertThat(usuario.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("deve retornar usuario criado")
    void save_ReturnsUsers_WhenSuccessful() {
        UsuarioPostRequestBody usuarioPostRequestBody = UsuarioPostRequestBodyCreator.createUsuarioPostRequestBody();

        ProjetoUser user = new ProjetoUser();
        user.setId(1);
        user.setPassword("{bcrypt}$2a$10$F7QFfu13l0M0nWDMyKaH6e1amtngcHE4XwTJyZtbVYTwZ.q9iGsmi");
        user.setUsername("admin");
        user.setAuthorities("ROLE_ADMIN, ROLE_USER");

        userRepository.save(user);

        ResponseEntity<Usuario> usuarioResponseEntity = testRestTemplateRoleAdmin.postForEntity("/usuarios", usuarioPostRequestBody, Usuario.class);

        Assertions.assertThat(usuarioResponseEntity).isNotNull();
        Assertions.assertThat(usuarioResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(usuarioResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(usuarioResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("deve atualizar o usuario")
    void replace_UpdatesUsuario_WhenSuccessful() {
        Usuario savedUsuario = usuarioRepository.save(UsuarioCreator.createUsuarioToBeSaved());

        ProjetoUser user = new ProjetoUser();
        user.setId(1);
        user.setPassword("{bcrypt}$2a$10$F7QFfu13l0M0nWDMyKaH6e1amtngcHE4XwTJyZtbVYTwZ.q9iGsmi");
        user.setUsername("admin");
        user.setAuthorities("ROLE_ADMIN, ROLE_USER");

        userRepository.save(user);

        savedUsuario.setNome("novo nome");

        ResponseEntity<Void> usuarioResponseEntity = testRestTemplateRoleAdmin.exchange("/usuarios",
                HttpMethod.PUT, new HttpEntity<>(savedUsuario), Void.class);

        Assertions.assertThat(usuarioResponseEntity).isNotNull();

        Assertions.assertThat(usuarioResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("remove o usuario com sucesso")
    void delete_RemovesUser_WhenSuccessful() {
        Usuario savedUsuario = usuarioRepository.save(UsuarioCreator.createUsuarioToBeSaved());

        ProjetoUser user = new ProjetoUser();
        user.setId(1);
        user.setPassword("{bcrypt}$2a$10$F7QFfu13l0M0nWDMyKaH6e1amtngcHE4XwTJyZtbVYTwZ.q9iGsmi");
        user.setUsername("admin");
        user.setAuthorities("ROLE_ADMIN, ROLE_USER");

        userRepository.save(user);

        ResponseEntity<Void> usuarioResponseEntity = testRestTemplateRoleAdmin.exchange("/usuarios/admin/{id}",
                HttpMethod.DELETE, null, Void.class, savedUsuario.getId());

        Assertions.assertThat(usuarioResponseEntity).isNotNull();

        Assertions.assertThat(usuarioResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
