package com.github.nw02.sistemagestaoesportiva.services;

import com.github.nw02.sistemagestaoesportiva.exceptions.RecursoNaoEncontrado;
import com.github.nw02.sistemagestaoesportiva.models.Usuario;
import com.github.nw02.sistemagestaoesportiva.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Usuario criarUsuario (Usuario usuario){
        validarNovoCadastro(usuario);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizarUsuario (Usuario usuario){
        Usuario usuarioNovo = usuarioRepository.findById(usuario.getId()).
                orElseThrow(() -> new RecursoNaoEncontrado("Usuario nao encontrado!"));

        if(usuario.getEmail() != null) usuarioNovo.setEmail(usuario.getEmail());
        if(usuario.getSenha() != null) usuarioNovo.setSenha(usuario.getSenha());
        if(usuario.getNome() != null) usuarioNovo.setNome(usuario.getNome());

        validarAlteracao(usuarioNovo);

        return usuarioRepository.save(usuarioNovo);
    }

    public void deletarUsuario (Long id){
        if(id == null){
            throw new IllegalArgumentException("Id nao pode ser nulo");
        }
        if(!usuarioRepository.existsById(id)){
            throw new RecursoNaoEncontrado("Esse usuario nao existe");
        }
        usuarioRepository.deleteById(id);
    }

    public List mostrarUsuarios(){
        return usuarioRepository.findAll();
    }


    private void validarNovoCadastro(Usuario usuario){
         if(usuarioRepository.existsByNomeOrEmail(usuario.getNome(), usuario.getEmail())){
             throw new IllegalArgumentException("Nome ou Email já cadastrados");
         };
    }

    public void validarAlteracao(Usuario usuario){
        if(usuarioRepository.existsByEmailAndIdNot(usuario.getEmail(), usuario.getId()) ||
                usuarioRepository.existsByNomeAndIdNot(usuario.getNome(), usuario.getId())){
            throw new IllegalArgumentException("Nome ou email já existentes");
        }
    }
}
