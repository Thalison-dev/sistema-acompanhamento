USE sistemaneurodivergente;

-- Remove tabelas dependentes para permitir recriação da chave estrangeira
DROP TABLE IF EXISTS intervencao;
DROP TABLE IF EXISTS procedimento;
DROP TABLE IF EXISTS acompanhamento;

-- Tabela acompanhamento ajustada conforme o DAO
CREATE TABLE acompanhamento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dataInicio DATE NOT NULL,
    dataFim DATE,
    objetivo TEXT,
    status VARCHAR(50)
);

-- Recriação das tabelas com a chave estrangeira atualizada
CREATE TABLE procedimento (
    procedimentoId INT AUTO_INCREMENT PRIMARY KEY,
    acompanhamentoId INT,
    orientadorId INT,
    dataProcedimento DATE,
    descricaoProcedimento TEXT,
    estrategiaProcedimento VARCHAR(255),
    resultadoProcedimento TEXT,
    observacoesProcedimento TEXT,

    FOREIGN KEY (acompanhamentoId) REFERENCES acompanhamento(id),
    FOREIGN KEY (orientadorId) REFERENCES orientador_psicopedagogo(orientadorId)
);

CREATE TABLE intervencao (
    intervencaoId INT AUTO_INCREMENT PRIMARY KEY,
    acompanhamentoId INT,
    orientadorId INT,

    FOREIGN KEY (acompanhamentoId) REFERENCES acompanhamento(id),
    FOREIGN KEY (orientadorId) REFERENCES orientador_psicopedagogo(orientadorId)
);