package com.boxsurprise.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PedidoResponseDto {
    private Integer idPedido;
    private String statusPedido;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataCompra;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataAtualizacao;
    private Integer quantidadePedido;
    private String nomeCliente;
    private String telefoneCliente;
    private String cpfCliente;
    private String ruaEndereco;
    private String numeroEndereco;
    private String complementoEndereco;
    private String cidadeEndereco;
    private String estadoEndereco;
    private String cepEndereco;
    private List<PedidoItemResponseDto> itensPedido;
}
