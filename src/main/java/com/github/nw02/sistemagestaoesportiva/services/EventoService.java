package com.github.nw02.sistemagestaoesportiva.services;

import com.github.nw02.sistemagestaoesportiva.exceptions.RecursoNaoEncontrado;
import com.github.nw02.sistemagestaoesportiva.models.Endereco;
import com.github.nw02.sistemagestaoesportiva.models.Evento;
import com.github.nw02.sistemagestaoesportiva.repositories.EventoRepository;
import com.github.nw02.sistemagestaoesportiva.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventoService {
    private EventoRepository eventoRepository;
    private UsuarioRepository usuarioRepository;

    public EventoService(EventoRepository eventoRepository, UsuarioRepository usuarioRepository) {
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Evento criarEvento(Evento evento){
        if(!usuarioRepository.existsById(evento.getId()))
            throw new RecursoNaoEncontrado("Esse usuario nao existe!");
        return eventoRepository.save(evento);
    }

    @Transactional
    public Evento alterarEvento(Evento eventoDadosNovos) {
        Evento eventoExistente = eventoRepository.findById(eventoDadosNovos.getId())
                .orElseThrow(() -> new RecursoNaoEncontrado("Evento não encontrado!"));

        if (eventoDadosNovos.getNome() != null) eventoExistente.setNome(eventoDadosNovos.getNome());
        if (eventoDadosNovos.getDataHorario() != null) eventoExistente.setDataHorario(eventoDadosNovos.getDataHorario());

        if (eventoDadosNovos.getEndereco() != null) {
            Endereco enderecoAtual = eventoExistente.getEndereco();
            Endereco enderecoNovosDados = eventoDadosNovos.getEndereco();

            if (enderecoAtual != null) {
                if (enderecoNovosDados.getCep() != null) enderecoAtual.setCep(enderecoNovosDados.getCep());
                if (enderecoNovosDados.getNumero() != null) enderecoAtual.setNumero(enderecoNovosDados.getNumero());
                if (enderecoNovosDados.getCidade() != null) enderecoAtual.setCidade(enderecoNovosDados.getCidade());
                if (enderecoNovosDados.getEstado() != null) enderecoAtual.setEstado(enderecoNovosDados.getEstado());
            } else {
                eventoExistente.setEndereco(enderecoNovosDados);
            }
        }
        return eventoRepository.save(eventoExistente);
    }

    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }

    public void deletarEvento(Long id){
        if(id == null) throw new IllegalArgumentException("Id não pode ser nulo");
        if(!eventoRepository.existsById(id)) throw new RecursoNaoEncontrado("Não existe esse usuario");

        eventoRepository.deleteById(id);
    }

}
