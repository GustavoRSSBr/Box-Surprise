package com.boxsurprise.dao;

import com.boxsurprise.dtos.response.PedidoPessoaResponseDto;
import com.boxsurprise.dtos.response.PessoaResponseDto;
import com.boxsurprise.dtos.response.EnderecoResponseDto;
import com.boxsurprise.model.Endereco;
import com.boxsurprise.model.Usuario;

import java.util.List;

public interface IClienteJdbcTemplateDao {


    void salvarDadosCadastrais(Usuario usuario);

    Integer salvarEndereco(Integer pessoaId, Endereco endereco);


    List<EnderecoResponseDto> listarEnderecoPessoa(Integer pessoaId);


    List<PedidoPessoaResponseDto> listarPedidoPessoa(Integer pessoaId);

    PessoaResponseDto buscarPessoaPorEmail(String email);

    boolean existeUsuario(String email);

    Usuario obterPorEmail(String email);

    Integer buscarIdPessoaPorEmail(String email);
}
