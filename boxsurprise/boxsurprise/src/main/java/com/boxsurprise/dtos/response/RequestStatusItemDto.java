package com.boxsurprise.dtos.response;

import com.boxsurprise.enuns.StatusPedidoItem;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestStatusItemDto {

    private Integer idPedidoItem;  // ID do item do pedido
    private StatusPedidoItem novoStatus;  // Enum para o novo status do item
}

