package com.boxsurprise.dao.impl;

import com.boxsurprise.dao.IClienteJdbcTemplateDao;
import com.boxsurprise.dtos.response.EnderecoResponseDto;
import com.boxsurprise.model.Endereco;
import com.boxsurprise.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ClienteJdbcTemplateImpl implements IClienteJdbcTemplateDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void salvarDadosCadastrais(Usuario usuario) {
        String sql = "CALL inserir_pessoa_usuario(?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                usuario.getPessoa().getNome(),
                usuario.getPessoa().getTelefone(),
                usuario.getPessoa().getCpf(),
                usuario.getPessoa().getDataNascimento(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getTipoUsuario().toString()
        );
    }

    @Override
    public Integer salvarEndereco(Integer pessoaId, Endereco endereco) {
        String sql = "SELECT inserir_endereco(?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.queryForObject(sql, Integer.class,
                pessoaId,
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep()
        );
    }

    @Override
    public List<EnderecoResponseDto> listarEnderecoPessoa(Integer pessoaId) {
        String sql = "SELECT * FROM public.listar_enderecos_pessoa(?)";

        return jdbcTemplate.query(
                sql,
                new Object[]{pessoaId},
                new BeanPropertyRowMapper<>(EnderecoResponseDto.class)
        );
    }


}
