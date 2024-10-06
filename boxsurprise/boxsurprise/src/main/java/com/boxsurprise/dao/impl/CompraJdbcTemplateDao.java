package com.boxsurprise.dao.impl;

import com.boxsurprise.dao.ICompraJdbcTemplateDao;
import com.boxsurprise.dao.impl.mapper.PedidoItemRowMapper;
import com.boxsurprise.dtos.response.PedidoItemResponseDto;
import com.boxsurprise.dtos.response.PedidoResponseDto;
import com.boxsurprise.dtos.request.RequestCompraItemDto;
import com.boxsurprise.dtos.response.ProdutoResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompraJdbcTemplateDao implements ICompraJdbcTemplateDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void processarPedido(Integer idPessoa, RequestCompraItemDto requestCompraItemDto) {
        String sql = "CALL processar_pedido(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql,
                idPessoa,
                requestCompraItemDto.getIdEndereco(),
                requestCompraItemDto.getIdProduto(),
                requestCompraItemDto.getValorTamanhoCaixa(),
                requestCompraItemDto.getTipoProduto().toString(),
                requestCompraItemDto.getTamanhoCaixa().toString(),
                requestCompraItemDto.getRespostasBox(),
                requestCompraItemDto.getTema(),
                requestCompraItemDto.getQuantidade()
        );
    }

    @Override
    public PedidoResponseDto buscarPedido(Integer pedidoId) {
        // Busca as informações gerais do pedido
        String sqlPedido = "SELECT * FROM buscarInformacoesDoPedido(?)";
        PedidoResponseDto pedido;

        pedido = jdbcTemplate.queryForObject(sqlPedido, new Object[]{pedidoId}, new BeanPropertyRowMapper<>(PedidoResponseDto.class));

        // Busca os itens do pedido
        String sqlItens = "SELECT * FROM buscarItensDoPedido(?)";
        List<PedidoItemResponseDto> itensPedido = jdbcTemplate.query(sqlItens,
                new Object[]{pedidoId}, new PedidoItemRowMapper());

        // Se possível, já trazer o idProduto no PedidoItemResponseDto e evitar a segunda consulta
        for (PedidoItemResponseDto item : itensPedido) {
            String sqlProduto = "SELECT * FROM buscarDetalhesDoProduto(?)";
            ProdutoResponseDto produto = jdbcTemplate.queryForObject(sqlProduto,
                    new Object[]{item.getIdProduto()}, new BeanPropertyRowMapper<>(ProdutoResponseDto.class));

            item.setProduto(produto);  // Adiciona as informações detalhadas do produto ao item
        }

        // Define os itens do pedido no objeto principal
        pedido.setItensPedido(itensPedido);

        return pedido;
    }

    @Override
    public void finalizarCompra(Integer idPedido, String status) {
        String sql = "CALL finalizar_compra(?, ?)";
        jdbcTemplate.update(sql, idPedido, status);
    }
}
