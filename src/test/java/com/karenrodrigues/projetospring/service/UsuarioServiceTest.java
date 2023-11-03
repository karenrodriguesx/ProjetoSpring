package com.karenrodrigues.projetospring.service;

import com.karenrodrigues.projetospring.domain.Usuario;
import com.karenrodrigues.projetospring.domain.UsuarioDTO;
import com.karenrodrigues.projetospring.repository.UsuarioFilterParam;
import com.karenrodrigues.projetospring.repository.UsuarioRepository;
import com.karenrodrigues.projetospring.requests.UsuarioPostRequestBody;
import com.karenrodrigues.projetospring.requests.UsuarioPutRequestBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@AutoConfigureTestDatabase
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Listando todos os usuarios usando page")
    void listAllSucess() {
        Usuario usuario = criarUsuario(new UsuarioDTO(1, "Karen", 20, "Dev"));

        List<Usuario> usuarios = Collections.singletonList(usuario);

        Page<Usuario> mockedPage = new PageImpl<>(usuarios);

        when(usuarioRepository.findAll(any(Pageable.class))).thenReturn(mockedPage);

        Page<Usuario> result = usuarioService.listAll(Pageable.unpaged());

        assertEquals(mockedPage, result);
    }

    @Test
    @DisplayName("Listando todos os usuarios sem o page")
    void listAllNonPageableSucess() {
        Usuario usuario = criarUsuario(new UsuarioDTO(1, "Karen", 20, "Dev"));

        List<Usuario> mockedUsuarios = new ArrayList<>();
        mockedUsuarios.add(usuario);

        when(usuarioRepository.findAll()).thenReturn(mockedUsuarios);

        List<Usuario> result = usuarioService.listAllNonPageable();

        assertEquals(mockedUsuarios, result);
    }

    @Test
    @DisplayName("Buscando usuario por profissao com sucesso")
    void findUsuariosByProfissaoSucess() {
        String profissao = "Dev";
        new UsuarioDTO(1, "Karen", 20, profissao);

        List<UsuarioDTO> mockedUsuarios = new ArrayList<>();
        when(usuarioRepository.findUsuariosByProfissao(profissao)).thenReturn(mockedUsuarios);

        List<UsuarioDTO> result = usuarioService.findUsuariosByProfissao(profissao);

        assertEquals(mockedUsuarios, result);
    }

    @Test
    @DisplayName("Filtrando usuario por idade")
    void filterParamIdadeSucess() {
        UsuarioFilterParam filtro = new UsuarioFilterParam();
        filtro.setIdade(20);

        List<Usuario> usuariosFiltrados = new ArrayList<>();
        usuariosFiltrados.add(new Usuario(new UsuarioDTO(1, "Karen", 20, "Dev")));
        usuariosFiltrados.add(new Usuario(new UsuarioDTO(2, "Maria", 20, "Dev")));

        when(usuarioRepository.getWithFilter(filtro)).thenReturn(usuariosFiltrados);

        List<Usuario> result = usuarioService.getAll(filtro);

        assertEquals(usuariosFiltrados, result);
    }

    @Test
    @DisplayName("Filtrando usuario por profissao")
    void filterParamProfissaoSucess() {
        UsuarioFilterParam filtro = new UsuarioFilterParam();
        filtro.setIdade(20);

        List<Usuario> usuariosFiltrados = new ArrayList<>();
        usuariosFiltrados.add(new Usuario(new UsuarioDTO(1, "Karen", 20, "Dev")));
        usuariosFiltrados.add(new Usuario(new UsuarioDTO(2, "Maria", 20, "Dev")));

        when(usuarioRepository.getWithFilter(filtro)).thenReturn(usuariosFiltrados);

        List<Usuario> result = usuarioService.getAll(filtro);

        assertEquals(usuariosFiltrados, result);
    }

    @Test
    @DisplayName("Filtrando usuario por profissao e idade")
    void filterParamIdadeProfissaoSucess() {
        UsuarioFilterParam filtro = new UsuarioFilterParam();
        filtro.setIdade(20);
        filtro.setProfissao("Dev");

        List<Usuario> usuariosFiltrados = new ArrayList<>();
        usuariosFiltrados.add(new Usuario(new UsuarioDTO(1, "Karen", 20, "Dev")));
        usuariosFiltrados.add(new Usuario(new UsuarioDTO(2, "Maria", 20, "Recepção")));

        when(usuarioRepository.getWithFilter(filtro)).thenReturn(usuariosFiltrados);

        List<Usuario> result = usuarioService.getAll(filtro);

        assertEquals(usuariosFiltrados, result);
    }

    @Test
    @DisplayName("Salvando usuario")
    void save() {
        UsuarioDTO usuarioDTO = new UsuarioDTO(1, "Karen", 20, "Dev");
        Usuario usuarioSalvo = criarUsuario(usuarioDTO);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        Usuario result = usuarioService.save(new UsuarioPostRequestBody("Karen", "Dev", 20));

        assertEquals(usuarioSalvo, result);
    }

    @Test
    @DisplayName("Atualizando o usuario")
    void replace() {
        UsuarioPutRequestBody requestBody = new UsuarioPutRequestBody(1, "Alice", "Dev", 25);
        Usuario usuarioAtualizado = criarUsuario(new UsuarioDTO(1, "Alice", 25, "Tester"));

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(criarUsuario(new UsuarioDTO(1, "Alice", 25, "Tester"))));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioAtualizado);

        usuarioService.replace(requestBody);

        assertEquals(usuarioAtualizado, usuarioRepository.findById(1).get());

    }

    @Test
    @DisplayName("Excluindo o usuario")
    void delete() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(criarUsuario(new UsuarioDTO(1, "Karen", 20, "Dev"))));

        usuarioService.delete(1);

        assertEquals(0, usuarioRepository.findAll().size());

    }

    private Usuario criarUsuario(UsuarioDTO data) {
        return new Usuario(data);
    }
}