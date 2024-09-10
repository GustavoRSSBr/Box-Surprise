package com.boxsurprise.dao.impl;

import com.boxsurprise.dao.IAdmJdbcTemplateDao;
import com.boxsurprise.dtos.PedidoPessoaResponseDto;
import com.boxsurprise.dtos.RequestStatusItemDto;
import com.boxsurprise.dtos.RequestStatusPedidoDto;
import com.boxsurprise.dtos.response.AnaliseResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdmJdbcTemplateImpl implements IAdmJdbcTemplateDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void salvarAnalise(Integer idProduto, String resposta) {
        String sql = "CALL salvar_analise(?, ?)";
        jdbcTemplate.update(sql, idProduto, resposta);
    }

    @Override
    public AnaliseResponseDto buscarAnalise(Integer idProduto) {
        String sql = "SELECT * FROM buscar_analise_por_produto(?)";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{idProduto},
                new BeanPropertyRowMapper<>(AnaliseResponseDto.class)
        );
    }


    @Override
    public List<PedidoPessoaResponseDto> listarPedidos() {
        String sql = "SELECT * FROM listar_pedidos_nao_finalizados()";

        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(PedidoPessoaResponseDto.class)
        );
}

    @Override
    public void mudarStatusPedidoItem(RequestStatusItemDto request) {
        String sql = "CALL atualizar_status_pedido_item(?, ?)";
        jdbcTemplate.update(sql, request.getIdPedidoItem(), request.getNovoStatus().name());
    }


    @Override
    public void mudarStatusPedido(RequestStatusPedidoDto request) {
        String sql = "CALL atualizar_status_pedido(?, ?)";
        jdbcTemplate.update(sql, request.getIdPedido(), request.getNovoStatus().name());
    }

}
