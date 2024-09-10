package com.boxsurprise.dtos;

import com.boxsurprise.enuns.StatusPedido;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestStatusPedidoDto {
    private Integer idPedido;           // ID do pedido
    private StatusPedido novoStatus;    // Enum para o novo status do pedido
}

