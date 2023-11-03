package com.karenrodrigues.projetospring.service;

import com.karenrodrigues.projetospring.domain.Usuario;
import com.karenrodrigues.projetospring.domain.UsuarioDTO;
import com.karenrodrigues.projetospring.exception.BadRequestException;
import com.karenrodrigues.projetospring.mapper.UsuarioMapper;
import com.karenrodrigues.projetospring.repository.UsuarioFilterParam;
import com.karenrodrigues.projetospring.repository.UsuarioRepository;
import com.karenrodrigues.projetospring.requests.UsuarioPostRequestBody;
import com.karenrodrigues.projetospring.requests.UsuarioPutRequestBody;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Page<Usuario> listAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public List<Usuario> listAllNonPageable() {
        return usuarioRepository.findAll();
    }

    public List<UsuarioDTO> findUsuariosByProfissao(String profissao) {
        return usuarioRepository.findUsuariosByProfissao(profissao);
    }

    public Usuario findByIdOrThrowBadRequestException(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Usuario não encontrado"));
    }

    public List<Usuario> getAll(UsuarioFilterParam params) {
        return usuarioRepository.getWithFilter(params);
    }

    @Transactional
    public Usuario save(UsuarioPostRequestBody usuarioPostRequestBody) {
        return usuarioRepository.save(UsuarioMapper.INSTANCE.toUsuario(usuarioPostRequestBody));
    }

    public void replace(UsuarioPutRequestBody usuarioPutRequestBody) {
        Usuario usuario = UsuarioMapper.INSTANCE.toUsuario(usuarioPutRequestBody);
        usuarioRepository.save(usuario);
    }

    public void delete(Integer id) {
        usuarioRepository.delete(findByIdOrThrowBadRequestException(id));
    }

}
