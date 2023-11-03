package com.karenrodrigues.projetospring.mapper;

import com.karenrodrigues.projetospring.domain.Usuario;
import com.karenrodrigues.projetospring.requests.UsuarioPostRequestBody;
import com.karenrodrigues.projetospring.requests.UsuarioPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UsuarioMapper {

    public static final UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    public abstract Usuario toUsuario(UsuarioPostRequestBody usuarioPostRequestBody);

    public abstract Usuario toUsuario(UsuarioPutRequestBody usuarioPutRequestBody);

}
