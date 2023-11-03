package com.karenrodrigues.projetospring.repository;

import com.karenrodrigues.projetospring.domain.Usuario;

import java.util.List;

public interface UsuarioRepositoryCustom {
    List<Usuario> getWithFilter (UsuarioFilterParam params);
}
