package com.boxsurprise.dao.impl;

import com.boxsurprise.dao.IProdutoJdbcTemplateDao;
import com.boxsurprise.dtos.response.AnaliseResponseDto;
import com.boxsurprise.dtos.response.ProdutoResponseDto;
import com.boxsurprise.dtos.request.RequestProdutoDto;
import com.boxsurprise.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProdutoJdbcTemplateDaoImpl implements IProdutoJdbcTemplateDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public ProdutoResponseDto buscarProdutoPorId(Integer id) {
        String sql = "SELECT * FROM buscar_produto_por_id(?)";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                new BeanPropertyRowMapper<>(ProdutoResponseDto.class)
        );
    }

    @Override
    public void cadastrarProduto(Produto produto) {
        String sql = "CALL inserir_produto(?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                produto.getTitulo(),
                produto.getDescricao(),
                produto.getValor(),
                produto.getTipoProduto(),
                produto.getTamanhoCaixa(),
                produto.getRespostasBox(),
                produto.getTema()
        );
    }

    @Override
    public List<ProdutoResponseDto> listarProdutosCadastrados() {
        String sql = "SELECT * FROM listar_produtos_caixa_predefinida()";
        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(ProdutoResponseDto.class)
        );
    }

    @Override
    public void alterarProdutoCadastrado(Integer idProduto, RequestProdutoDto request) {
        String sql = "CALL atualizar_produto(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                idProduto,
                request.getTitulo(),
                request.getDescricao(),
                request.getValor(),
                request.getTipoProduto().name(),
                request.getTamanhoCaixa().name()
        );
    }


}
