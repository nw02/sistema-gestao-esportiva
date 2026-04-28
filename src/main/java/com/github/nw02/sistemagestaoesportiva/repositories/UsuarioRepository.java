package com.github.nw02.sistemagestaoesportiva.repositories;

import com.github.nw02.sistemagestaoesportiva.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
