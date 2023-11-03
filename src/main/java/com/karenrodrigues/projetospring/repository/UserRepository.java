package com.karenrodrigues.projetospring.repository;

import com.karenrodrigues.projetospring.domain.ProjetoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<ProjetoUser, Integer> {

        ProjetoUser findByUsername(String username);
}
