package com.boxsurprise.usecase;


import com.boxsurprise.config.SegurancaConfig;
import com.boxsurprise.dao.IClienteJdbcTemplateDao;
import com.boxsurprise.dtos.PedidoPessoaResponseDto;
import com.boxsurprise.dtos.PessoaResponseDto;
import com.boxsurprise.dtos.request.LoginRequestDTO;
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
    IClienteJdbcTemplateDao serviceDao;


    @Autowired
    CepService cepService;

    @Autowired
    Validador validador;

    @Autowired
    SegurancaConfig seguranca;

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
                .senha(seguranca.criptografarSenha(request.getSenha()))
                .tipoUsuario(TipoUsuario.CLIENTE)
                .pessoa(pessoa)
                .build();


        serviceDao.salvarDadosCadastrais(usuario);
    }


    public String autenticar(LoginRequestDTO loginRequestDTO) {
        if (!serviceDao.existeUsuario(loginRequestDTO.getEmail())) {
            throw new NegocioException(ErrorCode.EMAIL_USUARIO_INVALIDO.getCustomMessage());
        }

        Usuario usuario = serviceDao.obterPorEmail(loginRequestDTO.getEmail());

        if (seguranca.compararSenhaHash(loginRequestDTO.getSenha(), usuario.getSenha())) {
            return seguranca.gerarToken(usuario);
        }

        throw new NegocioException(ErrorCode.SENHA_USUARIO_INCORRETA.getCustomMessage());
    }

    public Integer cadastrarEndereco(String token, RequestCadastroEnderecoDto request) {
        EnderecoViaCepResponseDto enderecoApi = cepService.buscaEnderecoPor(request.getCep());
        String email = seguranca.buscarEmailToken(token);

        Integer idCliente = serviceDao.buscarIdPessoaPorEmail(email);

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


    public List<EnderecoResponseDto> listarEnderecoPessoa(String token) {

        String email = seguranca.buscarEmailToken(token);

        Integer pessoaId = serviceDao.buscarIdPessoaPorEmail(email);

        List<EnderecoResponseDto> enderecos = serviceDao.listarEnderecoPessoa(pessoaId);

        if (enderecos == null || enderecos.isEmpty()){
            throw new NegocioException(ErrorCode.NENHUM_DADO_ENCONTRADO.getCustomMessage());
        }

        return enderecos;
    }

    public List<PedidoPessoaResponseDto> listarPedidoPessoa(String token) {
        String email = seguranca.buscarEmailToken(token);

        Integer idPessoa = serviceDao.buscarIdPessoaPorEmail(email);

        List<PedidoPessoaResponseDto> pedidos = serviceDao.listarPedidoPessoa(idPessoa);

        if (pedidos == null || pedidos.isEmpty()){
            throw new NegocioException(ErrorCode.NENHUM_DADO_ENCONTRADO.getCustomMessage());
        }

        return pedidos;
    }


    public PessoaResponseDto buscarPessoa(String email) {


        return serviceDao.buscarPessoaPorEmail(email);
    }
}
