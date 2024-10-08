package com.boxsurprise.enuns;

import lombok.Getter;

@Getter
public enum ErrorCode {
    EMAIL_JA_CADASTRADO("Email", "Email já está cadastrado"),
    CPF_JA_CADASTRADO("CPF", "CPF já está cadastrado"),
    ID_EMPRESA_NAO_EXISTE("ID de Empresa", "ID de Empresa não existe"),
    ID_PROJETO_NAO_EXISTE("ID de Projeto", "ID de Projeto não existe"),
    ID_FREELANCER_NAO_EXISTE("ID de Freelancer", "ID de Freelancer não existe"),
    PROPOSTA_JA_ACEITA("Proposta já foi aceita e não pode ser recusada", "Proposta já foi aceita e não pode ser recusada"),
    PROPOSTA_NAO_EXISTE("Proposta com id", "Proposta com id não existe"),
    PROJETO_JA_ASSOCIADO("Projeto já possui um freelancer associado", "Projeto já possui um freelancer associado"),
    CEP_INVALIDO("CEP inválido", "CEP inválido: "),
    CEP_NAO_ENCONTRADO("CEP não encontrado", "CEP não encontrado: "),
    CNPJ_INVALIDO("CNPJ inválido", "CNPJ inválido: "),
    CPF_INVALIDO("CPF inválido", "CPF inválido: "),
    EMAIL_INVALIDO("Email inválido", "Email inválido: "),
    NOME_INVALIDO("Nome inválido", "Nome inválido: "),
    SENHA_INVALIDA("Senha inválida", "Senha inválida: a senha deve ter pelo menos 8 caracteres, incluindo uma letra maiúscula, uma letra minúscula, um número e um caractere especial."),
    TELEFONE_INVALIDO("Número de telefone inválido", "Número de telefone inválido: "),
    LISTA_VAZIA("lista vazia", "Nenhum dado foi encontrado!"),
    OBJETO_VAZIO("objeto não encontrado", "Nenhum dado foi encontrado!"),
    DADO_INVALIDO("Dado invalido no Json", "Dado Inválido no Json"),
    ID_OBRIGATORIA("id invalido", "Voce deverá passar o id do cliente na requisição"),
    ESTRATEGIA_DUPLICADA("Estrategia Duplicada", "Estrategia duplicada para esse tipo de objeto: "),
    ESTRATEGIA_NAO_ENCONTRADA("Estrategia não encontrada", "Estrategia não encontrada para esse objeto: "),
    NAO_PODE_SER_NULO ("Os dados do objeto não pode ser nulo", "Os dados do objeto não pode ser nulo" ),
    TITULO_VAZIO("O campo 'titulo' é obrigatório e não pode estar vazio", "O campo 'titulo' é obrigatório e não pode estar vazio"),
    VALOR_OBRIGATORIO("O campo 'valor' é obrigatório e deve ser maior que zero", "O campo 'valor' é obrigatório e deve ser maior que zero"),
    TAMANHO_OBRIGATORIO("O campo 'tamanhoCaixa' é obrigatório","O campo 'tamanhoCaixa' é obrigatório"),
    TIPO_PRODUTO_OBRIGATORIO("O campo 'tipoProduto' é obrigatório","O campo 'tipoProduto' é obrigatório"),
    ID_PRODUTO_NULO("ID nulo", "O campo de ID não pode ser nulo ou vazio"),
    NENHUM_DADO_ENCONTRADO("nenhum dado encontrado", "Nenhum dado foi encontrado"),
    PESSOA_NAO_ENCONTRADA("Pessoa não encontrada", "Pessoa não encontrada"),
    ANALISE_INDISPONIVEL("Análise indisponivel", "Análise indisponivel verifique se a caixa é prédefinida ou se o valor é nulo"),
    ENDERECO_NAO_ENCONTRADO("Endereço não encontrado", "Endereço não encontrado"),
    ANALISE_NAO_ENCONTRADA("Análise não encontrada", "Análise não encontrada"),
    PEDIDO_NAO_ENCONTRADO("Pedido não encontrado", "Pedido não encontrado"),
    DESC_ROLE_SEM_PERMISSAO("sempermissão", "SEM PERMISSÃO"),
    DESC_BAD_REQUEST_HEADERS("loginincorreto","Efetue o login corretamente" ),
    DESC_TOKEN_INVALIDO("tokeninvalido", "Token inválido"),
    EMAIL_USUARIO_INVALIDO("Email invalido", "Email inválido" ),
    PRODUTO_NAO_ENCONTRADO("Produto não encontrado.", "Produto não encontrado."),
    SENHA_USUARIO_INCORRETA("Senha incorreta", "Senha incorreta" ),
    NENHUM_DADO_PESSOA_ENCONTRADO("nenhum dado foi encontrado", "pessoa não encontrada"),
    OUTRO_ERRO("Outro erro", "Erro desconhecido");

    private final String message;
    private final String customMessage;

    ErrorCode(String message, String customMessage) {
        this.message = message;
        this.customMessage = customMessage;
    }

    public static ErrorCode fromMessage(String message) {
        for (ErrorCode code : ErrorCode.values()) {
            if (message.contains(code.getMessage())) {
                return code;
            }
        }
        return OUTRO_ERRO;
    }
}