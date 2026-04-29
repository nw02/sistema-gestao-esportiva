package com.github.nw02.sistemagestaoesportiva.repositories;

import com.github.nw02.sistemagestaoesportiva.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByNomeOrEmail(String nome, String email);

    boolean existsByNomeAndIdNot(String nome, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);
}
