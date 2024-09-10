package com.boxsurprise.dao;

import com.boxsurprise.dtos.response.AnaliseResponseDto;
import com.boxsurprise.dtos.response.ProdutoResponseDto;
import com.boxsurprise.dtos.request.RequestProdutoDto;
import com.boxsurprise.model.Produto;

import java.util.List;

public interface IProdutoJdbcTemplateDao {

    ProdutoResponseDto buscarProdutoPorId(Integer id);

    void cadastrarProduto(Produto produto);

    List<ProdutoResponseDto> listarProdutosCadastrados();

    void alterarProdutoCadastrado(Integer idProduto, RequestProdutoDto request);

}
