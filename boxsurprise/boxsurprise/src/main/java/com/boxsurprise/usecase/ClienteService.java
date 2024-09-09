package com.boxsurprise.usecase;


import com.boxsurprise.dao.impl.ClienteJdbcTemplateImpl;
import com.boxsurprise.dtos.request.RequestCadastroDto;
import com.boxsurprise.dtos.request.RequestCadastroEnderecoDto;
import com.boxsurprise.dtos.response.EnderecoResponseDto;
import com.boxsurprise.dtos.response.EnderecoViaCepResponseDto;
import com.boxsurprise.enuns.ErrorCode;
import com.boxsurprise.enuns.TipoUsuario;
import com.boxsurprise.exceptions.NegocioException;
import com.boxsurprise.gateway.CepService;
import com.boxsurprise.model.Endereco;
import com.boxsurprise.model.Pessoa;
import com.boxsurprise.model.Usuario;
import com.boxsurprise.validador.Validador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    ClienteJdbcTemplateImpl serviceDao;

    @Autowired
    CepService cepService;

    @Autowired
    Validador validador;

    public void salvarDadosCadastrais(RequestCadastroDto request) {

        validador.validar(request);

        Pessoa pessoa = Pessoa.builder()
                .nome(request.getNome())
                .telefone(request.getTelefone())
                .cpf(request.getCpf())
                .dataNascimento(request.getDataNascimento())
                .build();

        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .senha(request.getSenha())
                .tipoUsuario(TipoUsuario.CLIENTE)
                .pessoa(pessoa)
                .build();


        serviceDao.salvarDadosCadastrais(usuario);
    }

    public Integer cadastrarEndereco(Integer idCliente, RequestCadastroEnderecoDto request) {
        EnderecoViaCepResponseDto enderecoApi = cepService.buscaEnderecoPor(request.getCep());

        if (idCliente == null) {
            throw new NegocioException(ErrorCode.ID_PRODUTO_NULO.getCustomMessage());
        }

        Endereco endereco = Endereco.builder()
                .cep(enderecoApi.getCep())
                .numero(request.getNumero())
                .complemento(request.getComplemento())
                .rua(enderecoApi.getLogradouro())
                .cidade(enderecoApi.getLocalidade())
                .estado(enderecoApi.getUf())
                .build();

        return serviceDao.salvarEndereco(idCliente, endereco);
    }


    public List<EnderecoResponseDto> listarEnderecoPessoa(Integer pessoaId) {
        List<EnderecoResponseDto> enderecos = serviceDao.listarEnderecoPessoa(pessoaId);

        if (enderecos == null || enderecos.isEmpty()){
            throw new NegocioException(ErrorCode.NENHUM_DADO_ENCONTRADO.getCustomMessage());
        }

        return enderecos;
    }

}
