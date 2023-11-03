package com.karenrodrigues.projetospring.repository;

import com.karenrodrigues.projetospring.domain.Usuario;
import com.karenrodrigues.projetospring.domain.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>, UsuarioRepositoryCustom {
    @Query("SELECT new com.karenrodrigues.projetospring.domain.UsuarioDTO (u.id, u.nome,u.idade,u.profissao) FROM Usuario u WHERE u.profissao = :profissao ORDER BY u.nome")
    List<UsuarioDTO> findUsuariosByProfissao(@Param("profissao") String profissao);

}
