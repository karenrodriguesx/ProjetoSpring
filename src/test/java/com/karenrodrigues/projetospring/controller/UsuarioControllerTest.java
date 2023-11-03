package com.karenrodrigues.projetospring.controller;

import com.karenrodrigues.projetospring.domain.Usuario;
import com.karenrodrigues.projetospring.domain.UsuarioDTO;
import com.karenrodrigues.projetospring.repository.UsuarioFilterParam;
import com.karenrodrigues.projetospring.requests.UsuarioPostRequestBody;
import com.karenrodrigues.projetospring.requests.UsuarioPutRequestBody;
import com.karenrodrigues.projetospring.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@AutoConfigureTestDatabase
class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Lista os usuarios sem paginacao")
    void listAllNonPageableSucess() {
        when(usuarioService.listAllNonPageable()).thenReturn(List.of(new Usuario(1, "Karen", "Dev", 20)));

        ResponseEntity<List<Usuario>> response = usuarioController.listAllNonPageable();

        assertEquals(1, response.getBody().size());
        assertEquals("Karen", response.getBody().get(0).getNome());
    }

    @Test
    @DisplayName("Lista os usuarios com paginacao")
    void listSucess() {
        // Mocking
        Page<Usuario> page = mock(Page.class);
        when(usuarioService.listAll(any(Pageable.class))).thenReturn(page);

        // Test
        ResponseEntity<Page<Usuario>> response = usuarioController.list(mock(Pageable.class));

        // Verify
        assertEquals(page, response.getBody());
    }

    @Test
    @DisplayName("Busca o usuario por profissao")
    void findByProfissao() {
        // Mocking
        when(usuarioService.findUsuariosByProfissao("Dev")).thenReturn(List.of(new UsuarioDTO(1, "Karen", 20, "Dev")));

        // Test
        ResponseEntity<List<UsuarioDTO>> response = usuarioController.findByProfissao("Dev");

        // Verify
        assertEquals(1, response.getBody().size());
        assertEquals("Karen", response.getBody().get(0).getNome());
    }

    @Test
    @DisplayName("Busca o usuario por id")
    void findById() {
        // Mocking
        when(usuarioService.findByIdOrThrowBadRequestException(1)).thenReturn(new Usuario(1, "Karen", "Dev", 20));

        // Test
        ResponseEntity<Usuario> response = usuarioController.findById(1);

        // Verify
        assertEquals("Karen", response.getBody().getNome());
    }

    @Test
    @DisplayName("Busca o usuario por id para realizar a autenticacao")
    void findByIdAuthenticationPrincipal() {
        // Mocking
        when(usuarioService.findByIdOrThrowBadRequestException(1)).thenReturn(new Usuario(1, "Karen", "Dev", 20));
        UserDetails userDetails = mock(UserDetails.class);

        // Test
        ResponseEntity<Usuario> response = usuarioController.findByIdAuthenticationPrincipal(1, userDetails);

        // Verify
        assertEquals("Karen", response.getBody().getNome());
    }

    @Test
    @DisplayName("Filtra os usuarios")
    void getUsuariosFilter() {
        // Mocking
        UsuarioFilterParam params = new UsuarioFilterParam();
        when(usuarioService.getAll(params)).thenReturn(List.of(new Usuario(1, "Karen", "Dev", 20)));

        // Test
        ResponseEntity<List<Usuario>> response = usuarioController.getUsuariosFilter(params);

        // Verify
        assertEquals(1, response.getBody().size());
        assertEquals("Karen", response.getBody().get(0).getNome());
    }

    @Test
    @DisplayName("Salva usuarios")
    void save() {
        // Mocking
        UsuarioPostRequestBody requestBody = new UsuarioPostRequestBody("Karen", "Dev", 20);
        when(usuarioService.save(requestBody)).thenReturn(new Usuario(1, "Karen", "Dev", 20));

        // Test
        ResponseEntity<Usuario> response = usuarioController.save(requestBody);

        // Verify
        assertEquals("Karen", response.getBody().getNome());
    }

    @Test
    @DisplayName("Atualiza usuarios")
    void replace() {
        // Mocking
        UsuarioPutRequestBody requestBody = new UsuarioPutRequestBody(1, "Karen", "Dev", 20);

        // Test
        ResponseEntity<Void> response = usuarioController.replace(requestBody);

        // Verify
        verify(usuarioService).replace(requestBody);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Deleta os usuarios por id")
    void delete() {
        // Mocking
        doNothing().when(usuarioService).delete(1);

        // Test
        ResponseEntity<Void> response = usuarioController.delete(1);

        // Verify
        verify(usuarioService).delete(1);
        assertEquals(204, response.getStatusCodeValue());
    }
}