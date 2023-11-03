package com.karenrodrigues.projetospring.repository;

import com.karenrodrigues.projetospring.domain.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@Data
@Component
public class UsuarioRepositoryCustomImpl implements UsuarioRepositoryCustom {

    private EntityManager entityManager;

    @Autowired
    public UsuarioRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Usuario> getWithFilter(UsuarioFilterParam params) {

        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();

        CriteriaQuery<Usuario> query = criteriaBuilder.createQuery(Usuario.class);

        Root<Usuario> usuario = query.from(Usuario.class);

        List<Predicate> predicates = new ArrayList<>();

        if (params.getNome() != null) {
            predicates.add(criteriaBuilder.like(usuario.get("nome"), "%" + params.getNome() + "%"));
        }
        if (params.getProfissao() != null) {
            predicates.add(criteriaBuilder.like(usuario.get("profissao"), "%" + params.getProfissao() + "%"));
        }
        if (params.getIdade() != null) {
            predicates.add(criteriaBuilder.equal(usuario.get("idade"), params.getIdade()));
        }

        if (!predicates.isEmpty()) {
            query.where(predicates.stream().toArray(Predicate[]::new));
        }

        TypedQuery<Usuario> queryResult = this.entityManager.createQuery(query);

        return queryResult.getResultList();
    }
}
