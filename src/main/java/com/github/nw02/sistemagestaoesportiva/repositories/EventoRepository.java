package com.github.nw02.sistemagestaoesportiva.repositories;

import com.github.nw02.sistemagestaoesportiva.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}
