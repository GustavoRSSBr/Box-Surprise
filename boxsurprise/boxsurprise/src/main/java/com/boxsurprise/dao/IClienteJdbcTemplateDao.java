package com.boxsurprise.dao;

import com.boxsurprise.dtos.response.EnderecoResponseDto;
import com.boxsurprise.model.Endereco;
import com.boxsurprise.model.Usuario;

import java.util.List;

public interface IClienteJdbcTemplateDao {


    void salvarDadosCadastrais(Usuario usuario);

    Integer salvarEndereco(Integer pessoaId, Endereco endereco);


    List<EnderecoResponseDto> listarEnderecoPessoa(Integer pessoaId);
}
