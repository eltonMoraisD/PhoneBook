package com.example.phonebook.Utils;

import com.example.phonebook.model.ContatosUsuarios;

import java.util.List;

public interface IContatoDAO {
    boolean salvar(ContatosUsuarios contatosUsuarios);
    boolean atualizar(ContatosUsuarios contatosUsuarios);
    boolean deletar(ContatosUsuarios contatosUsuarios);
    boolean favorito(ContatosUsuarios contatosUsuarios);
    List<ContatosUsuarios> listar();

}
