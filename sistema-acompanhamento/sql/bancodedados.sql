
CREATE DATABASE IF NOT EXISTS sistemaneurodivergente;
USE sistemaneurodivergente;

CREATE TABLE pessoa (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(60) NOT NULL,
    telefone VARCHAR(20),
    CPF CHAR(14) UNIQUE,
    ativo TINYINT DEFAULT 1
);

CREATE TABLE turma (
    turmaId INT AUTO_INCREMENT PRIMARY KEY,
    nomeTurma VARCHAR(100) NOT NULL,
    anoEscolarTurma INT,
    turnoTurma VARCHAR(30),
    anoLetivoTurma INT
);

CREATE TABLE responsavel_legal (
    responsavelLegal INT PRIMARY KEY,
    parentescoLegal VARCHAR(50),
    FOREIGN KEY (responsavelLegal) REFERENCES pessoa(id)
);

CREATE TABLE aluno (
    alunoId INT AUTO_INCREMENT PRIMARY KEY,
    turmaId INT,
    responsavelId INT,
    nomeAluno VARCHAR(150) NOT NULL,
    dataNascimentoAluno DATE,
    matriculaAluno VARCHAR(50),
    anoEscolarAluno VARCHAR(30),
    statusAluno VARCHAR(30),

    FOREIGN KEY (turmaId) REFERENCES turma(turmaId),
    FOREIGN KEY (responsavelId)
        REFERENCES responsavel_legal(responsavelLegal)
);

CREATE TABLE disciplina (
    disciplinaId INT AUTO_INCREMENT PRIMARY KEY,
    nomeDisciplina VARCHAR(100),
    descricaoDisciplina TEXT
);
CREATE TABLE avaliacao (
    avaliacaoId INT AUTO_INCREMENT PRIMARY KEY,
    alunoId INT,
    disciplinaId INT,
    notaAvaliacao DOUBLE,
    dataAvaliacao DATE,
    tipoAvaliacao VARCHAR(50),
    observacaoAvaliacao TEXT,

    FOREIGN KEY (alunoId) REFERENCES aluno(alunoId),
    FOREIGN KEY (disciplinaId) REFERENCES disciplina(disciplinaId)
);

CREATE TABLE neurodivergencia (
    neurodivergenciaId INT AUTO_INCREMENT PRIMARY KEY,
    alunoId INT,
    tipoNeurodivergencia VARCHAR(100),
    descricaoNeuro TEXT,
    observacoesNeuro TEXT,
    dataRegistroNeuro DATE,

    FOREIGN KEY (alunoId) REFERENCES aluno(alunoId)
);

CREATE TABLE documento (
    documentoId INT AUTO_INCREMENT PRIMARY KEY,
    alunoId INT,
    nomeArquivo VARCHAR(255),
    tipoDocumento VARCHAR(100),
    dataUpload DATETIME,
    versao VARCHAR(20),
    caminhoArquivo VARCHAR(500),

    FOREIGN KEY (alunoId) REFERENCES aluno(alunoId)
);

CREATE TABLE acompanhamento (
    acompanhamentoId INT AUTO_INCREMENT PRIMARY KEY,
    alunoId INT,
    dataInicioAcompanhamento DATE,
    dataFimAcompanhamento DATE,
    objetivoAcompanhamento TEXT,
    observacaoAcompanhamento TEXT,
    statusAcompanhamento VARCHAR(50),

    FOREIGN KEY (alunoId) REFERENCES aluno(alunoId)
);

CREATE TABLE notificacao (
    notificacaoId INT AUTO_INCREMENT PRIMARY KEY,
    pessoaId INT,
    tituloNotificacao VARCHAR(150),
    mensagemNotificacao TEXT,
    dataEnvioNotificacao DATETIME,
    recebidaNotificacao TINYINT DEFAULT 0,
    tipoNotificacao VARCHAR(50),

    FOREIGN KEY (pessoaId) REFERENCES pessoa(id)
);

CREATE TABLE mensagem (
    mensagemId INT AUTO_INCREMENT PRIMARY KEY,
    remetenteId INT,
    destinatarioId INT,
    conteudoMensagem TEXT,
    dataEnvioMensagem DATETIME,
    lidaMensagem TINYINT DEFAULT 0,

    FOREIGN KEY (remetenteId) REFERENCES pessoa(id),
    FOREIGN KEY (destinatarioId) REFERENCES pessoa(id)
);

CREATE TABLE auditoria (
    auditoriaId INT AUTO_INCREMENT PRIMARY KEY,
    pessoaId INT,
    dataHoraAuditoria DATETIME,
    acaoAuditoria VARCHAR(100),
    entidadeId VARCHAR(100),
    descricaoId TEXT,
    enderecoIp VARCHAR(45),

    FOREIGN KEY (pessoaId) REFERENCES pessoa(id)
);

CREATE TABLE controle_acesso (
    acessoId INT AUTO_INCREMENT PRIMARY KEY,
    pessoaID INT,
    permissaoAcesso VARCHAR(100),
    recursoAcesso VARCHAR(100),
    autorizadoAcesso TINYINT DEFAULT 0,

    FOREIGN KEY (pessoaID) REFERENCES pessoa(id)
);

CREATE TABLE professor (
    professorId INT PRIMARY KEY,
    matriculaProfessor VARCHAR(50),
    especialidade VARCHAR(100),

    FOREIGN KEY (professorId) REFERENCES pessoa(id)
);

CREATE TABLE psicologo (
    pessoaId INT PRIMARY KEY,
    especialidade TEXT,
    alunoId INT,
    reuniao TEXT,

    FOREIGN KEY (pessoaId) REFERENCES pessoa(id),
    FOREIGN KEY (alunoId) REFERENCES aluno(alunoId)
);

CREATE TABLE pedagogo (
    pessoa INT PRIMARY KEY,

    FOREIGN KEY (pessoa) REFERENCES pessoa(id)
);

CREATE TABLE orientador_psicopedagogo (
    orientadorId INT PRIMARY KEY,

    FOREIGN KEY (orientadorId) REFERENCES pessoa(id)
);

CREATE TABLE procedimento (
    procedimentoId INT AUTO_INCREMENT PRIMARY KEY,
    acompanhamentoId INT,
    orientadorId INT,
    dataProcedimento DATE,
    descricaoProcedimento TEXT,
    estrategiaProcedimento VARCHAR(255),
    resultadoProcedimento TEXT,
    observacoesProcedimento TEXT,

    FOREIGN KEY (acompanhamentoId)
        REFERENCES acompanhamento(acompanhamentoId),

    FOREIGN KEY (orientadorId)
        REFERENCES orientador_psicopedagogo(orientadorId)
);

CREATE TABLE ocorrencia_pedagogica (
    ocorrenciaId INT AUTO_INCREMENT PRIMARY KEY,
    alunoId INT,
    professorId INT,
    dataOcorrenciaPedagogica DATETIME,
    tipoOcorrencia VARCHAR(100),
    descricaoOcorrencia TEXT,
    providenciaOcorrencia TEXT,
    statusOcorrencia VARCHAR(50),

    FOREIGN KEY (alunoId) REFERENCES aluno(alunoId),
    FOREIGN KEY (professorId) REFERENCES professor(professorId)
);

CREATE TABLE adaptacao_metodologica (
    adaptacaoMetodologiaId INT AUTO_INCREMENT PRIMARY KEY,
    alunoId INT,
    professorId INT,
    orientadorId INT,
    adaptacaoTipo VARCHAR(100),
    descricaoAdaptacao TEXT,
    recursoAdaptacao TEXT,
    nivelAdaptacao VARCHAR(50),
    metodologia TEXT,
    estrategiaMetodologia TEXT,
    dataRegistroAdapt DATE,
    resultado TEXT,
    status VARCHAR(50),

    FOREIGN KEY (alunoId) REFERENCES aluno(alunoId),
    FOREIGN KEY (professorId) REFERENCES professor(professorId),
    FOREIGN KEY (orientadorId)
        REFERENCES orientador_psicopedagogo(orientadorId)
);

CREATE TABLE relatorio (
    relatorioId INT AUTO_INCREMENT PRIMARY KEY,
    orientadorId INT,
    alunoId INT,
    dataGeracao DATETIME,
    tipo VARCHAR(50),
    periodoInicialRel DATE,
    periodoFinalRel DATE,
    conteudo TEXT,

    FOREIGN KEY (orientadorId)
        REFERENCES orientador_psicopedagogo(orientadorId),

    FOREIGN KEY (alunoId) REFERENCES aluno(alunoId)
);

CREATE TABLE intervencao (
    intervencaoId INT AUTO_INCREMENT PRIMARY KEY,
    acompanhamentoId INT,
    orientadorId INT,

    FOREIGN KEY (acompanhamentoId)
        REFERENCES acompanhamento(acompanhamentoId),

    FOREIGN KEY (orientadorId)
        REFERENCES orientador_psicopedagogo(orientadorId)
);
