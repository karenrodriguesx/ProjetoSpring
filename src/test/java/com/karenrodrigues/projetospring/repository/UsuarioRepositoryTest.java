package com.karenrodrigues.projetospring.repository;

import com.karenrodrigues.projetospring.domain.Usuario;
import com.karenrodrigues.projetospring.domain.UsuarioDTO;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UsuarioRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Retorna o usuario com sucesso")
    void findUsuariosByProfissao_Sucess() {
        String profissao = "Dev";
        UsuarioDTO data = new UsuarioDTO(1,"Karen", 20, profissao);
        criarUsuario(data);

        List<UsuarioDTO> result = this.usuarioRepository.findUsuariosByProfissao(profissao);

        // Verificação
        assertEquals(1, result.size()); // Verifique o tamanho da lista
        UsuarioDTO usuarioEncontrado = result.get(0);
        assertEquals(data.getId(), usuarioEncontrado.getId());
        assertEquals(data.getNome(), usuarioEncontrado.getNome()); // Verifique o nome
        assertEquals(data.getIdade(), usuarioEncontrado.getIdade()); // Verifique a idade
        assertEquals(data.getProfissao(), usuarioEncontrado.getProfissao()); // Verifique a profissão
    }

    @Test
    @DisplayName("Nao retorna o usuario, pois ele nao existe")
    void findUsuariosByProfissaoFailed() {
        String profissao = "Dev";

        List<UsuarioDTO> result = this.usuarioRepository.findUsuariosByProfissao(profissao);

        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Salva o usuario com sucesso")
    void savePersistUsuarioSucess() {
        UsuarioDTO data = new UsuarioDTO(1,"Karen", 20, "Dev");
        criarUsuario(data);
        Assertions.assertThat(data).isNotNull();
        Assertions.assertThat(data.getId()).isNotNull();
        Assertions.assertThat(data.getNome()).isEqualTo(data.getNome());
        Assertions.assertThat(data.getProfissao()).isEqualTo(data.getProfissao());
        Assertions.assertThat(data.getIdade()).isEqualTo(data.getIdade());
    }

    @Test
    @DisplayName("Atualiza o usuario com sucesso")
    void saveUpdateUsuarioSucess() {
        UsuarioDTO data = new UsuarioDTO(1,"Karen", 20, "Dev");
        criarUsuario(data);

        data.setNome("Maria");

        Usuario usuarioUpdated = criarUsuario(data);

        Assertions.assertThat(usuarioUpdated).isNotNull();
        Assertions.assertThat(usuarioUpdated.getId()).isNotNull();
        Assertions.assertThat(usuarioUpdated.getNome()).isEqualTo(data.getNome());
        Assertions.assertThat(usuarioUpdated.getProfissao()).isEqualTo(data.getProfissao());
        Assertions.assertThat(usuarioUpdated.getIdade()).isEqualTo(data.getIdade());
    }

    @Test
    @DisplayName("Deleta o usuario com sucesso")
    void deleteUsuarioSucess() {
        UsuarioDTO data = new UsuarioDTO(1,"Karen", 20, "Dev");
        criarUsuario(data);

        usuarioRepository.deleteById(data.getId());

        Optional<Usuario> usuarioOptional = this.usuarioRepository.findById(data.getId());

        Assertions.assertThat(usuarioOptional).isEmpty();
    }

    private Usuario criarUsuario (UsuarioDTO data) {
        Usuario newUser = new Usuario(data);
        this.entityManager.persist(newUser);
        return newUser;
    }

}