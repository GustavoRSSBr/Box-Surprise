package com.boxsurprise.dao.impl.mapper;

import com.boxsurprise.dtos.response.PedidoItemResponseDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PedidoItemRowMapper implements RowMapper<PedidoItemResponseDto> {
    public PedidoItemResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return PedidoItemResponseDto.builder()
                .idPedidoItem(rs.getInt("id_pedido_item"))
                .idProduto(rs.getInt("id_produto"))
                .produtoTitulo(rs.getString("produto_titulo"))
                .produtoDescricao(rs.getString("produto_descricao"))
                .produtoValor(rs.getDouble("produto_valor"))
                .produtoQuantidade(rs.getInt("produto_quantidade"))
                .build();
    }

}
