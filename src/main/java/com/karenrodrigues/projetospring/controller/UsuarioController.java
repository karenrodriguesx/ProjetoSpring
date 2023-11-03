package com.karenrodrigues.projetospring.controller;

import com.karenrodrigues.projetospring.domain.Usuario;
import com.karenrodrigues.projetospring.domain.UsuarioDTO;
import com.karenrodrigues.projetospring.repository.UsuarioFilterParam;
import com.karenrodrigues.projetospring.requests.UsuarioPostRequestBody;
import com.karenrodrigues.projetospring.requests.UsuarioPutRequestBody;
import com.karenrodrigues.projetospring.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("usuarios")
@Log4j2
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    @Operation(summary = "Exibe a lista de usuários")
    public ResponseEntity<List<Usuario>> listAllNonPageable() {
        return ResponseEntity.ok(usuarioService.listAllNonPageable());
    }

    @GetMapping(path = "/page")
    @Operation(summary = "Exibe a lista de usuários com paginação")
    public ResponseEntity<Page<Usuario>> list(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(usuarioService.listAll(pageable));
    }

    @GetMapping(path = "/profissao/{profissao}")
    @Operation(summary = "Busca as informações do usuario via jpql query, a partir da profissão")
    public ResponseEntity<List<UsuarioDTO>> findByProfissao(@PathVariable String profissao) {
        List<UsuarioDTO> usuarios = usuarioService.findUsuariosByProfissao(profissao);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Busca o usuário por Id")
    public ResponseEntity<Usuario> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "find/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> findByIdAuthenticationPrincipal(@PathVariable Integer id,
                                                                   @AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails);
        return ResponseEntity.ok(usuarioService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping(path = "/filter")
    @Operation(summary = "Pesquisa usuários através de filtros enviados via JSON")
    public ResponseEntity<List<Usuario>> getUsuariosFilter(@RequestBody UsuarioFilterParam params) {
        return ResponseEntity.ok(usuarioService.getAll(params));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Insere usuários no sistema")
    public ResponseEntity<Usuario> save(@RequestBody @Valid UsuarioPostRequestBody usuarioPostRequestBody) {
        return new ResponseEntity<>(usuarioService.save(usuarioPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Atualiza usuários")
    public ResponseEntity<Void> replace(@RequestBody UsuarioPutRequestBody usuarioPutRequestBody) {
        usuarioService.replace(usuarioPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "admin/{id}")
    @Operation(summary = "Exclui usuários pelo id")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        usuarioService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

