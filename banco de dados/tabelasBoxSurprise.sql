-- Criação da tabela 'pessoa'
CREATE TABLE pessoa (
    idPessoa SERIAL,
    nome VARCHAR NOT NULL,
    telefone VARCHAR NOT NULL,
    cpf VARCHAR NOT NULL UNIQUE,
    dataNascimento VARCHAR NOT NULL
);

-- Criação da tabela 'endereco'
CREATE TABLE endereco (
    idEndereco SERIAL,
    pessoaId INTEGER NOT NULL,
    rua VARCHAR NOT NULL,
    numero VARCHAR NOT NULL,
    complemento VARCHAR,
    cidade VARCHAR NOT NULL,
    estado CHAR(2) NOT NULL,
    cep CHAR(9) NOT NULL
);

-- Criação da tabela 'pedido'
CREATE TABLE pedido (
    idPedido SERIAL,
    pessoaId INTEGER NOT NULL,
    enderecoId INTEGER NOT NULL,
    statusPedido VARCHAR,
    quantidade INTEGER NOT NULL,
    dataCriacao VARCHAR NOT NULL,
    dataCompra TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- Nova coluna: Data da compra
    dataAtualizacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP  -- Nova coluna: Data de atualização
);

-- Criação da tabela 'usuario'
CREATE TABLE usuario (
    idUsuario SERIAL,
    email VARCHAR NOT NULL UNIQUE,
    senha VARCHAR NOT NULL,
    pessoaId INTEGER,
    tipoUsuario VARCHAR NOT NULL
);

-- Criação da tabela 'produto'
CREATE TABLE produto (
    idProduto SERIAL,
    titulo VARCHAR NOT NULL,
    descricao TEXT,
    valor DECIMAL(10, 2) NOT NULL,
    tipoProduto VARCHAR NOT NULL,
    tamanhoCaixa VARCHAR NOT NULL,
    respostasBox TEXT,
    tema VARCHAR
);

-- Criação da tabela de junção 'pedidoItem'
CREATE TABLE pedidoItem (
    idPedidoItem SERIAL,
    pedidoId INTEGER NOT NULL,
    produtoId INTEGER NOT NULL,
    quantidade INTEGER NOT NULL,
    statusPedidoItem VARCHAR(50) NOT NULL DEFAULT 'PENDENTE'  -- Nova coluna: Status do item no pedido
);

-- Criação da tabela 'analise'
CREATE TABLE analise (
    idAnalise SERIAL,
    idProduto INTEGER NOT NULL,
    analise TEXT NOT NULL
);

-- Definição das chaves primárias
ALTER TABLE pessoa ADD PRIMARY KEY (idPessoa);
ALTER TABLE endereco ADD PRIMARY KEY (idEndereco);
ALTER TABLE pedido ADD PRIMARY KEY (idPedido);
ALTER TABLE usuario ADD PRIMARY KEY (idUsuario);
ALTER TABLE produto ADD PRIMARY KEY (idProduto);
ALTER TABLE pedidoItem ADD PRIMARY KEY (idPedidoItem);
ALTER TABLE analise ADD PRIMARY KEY (idAnalise);

-- Definição das chaves estrangeiras
ALTER TABLE endereco ADD CONSTRAINT fkPessoaEndereco FOREIGN KEY (pessoaId) REFERENCES pessoa(idPessoa);
ALTER TABLE pedido ADD CONSTRAINT fkPessoaPedido FOREIGN KEY (pessoaId) REFERENCES pessoa(idPessoa);
ALTER TABLE pedido ADD CONSTRAINT fkEnderecoPedido FOREIGN KEY (enderecoId) REFERENCES endereco(idEndereco);
ALTER TABLE usuario ADD CONSTRAINT fkPessoaUsuario FOREIGN KEY (pessoaId) REFERENCES pessoa(idPessoa);
ALTER TABLE pedidoItem ADD CONSTRAINT fkPedido FOREIGN KEY (pedidoId) REFERENCES pedido(idPedido);
ALTER TABLE pedidoItem ADD CONSTRAINT fkProduto FOREIGN KEY (produtoId) REFERENCES produto(idProduto);
ALTER TABLE analise ADD CONSTRAINT fkProdutoAnalise FOREIGN KEY (idProduto) REFERENCES produto(idProduto);
