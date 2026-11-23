-- =====================================================================
-- Banco do Sistema de Acompanhamento (sistemaneurodivergente)
-- Estrutura completa, igual à do banco em uso + tabelas novas:
--   diretor  (dados do Diretor, ligado a pessoa)
--   reuniao  (agenda de reuniões de todos os perfis)
-- Usa CREATE TABLE IF NOT EXISTS: pode rodar em um banco que já existe
-- sem apagar nada. (O sistema também cria diretor/reuniao sozinho na
-- primeira conexão, caso o script não seja executado.)
-- =====================================================================

CREATE DATABASE IF NOT EXISTS sistemaneurodivergente
  DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sistemaneurodivergente;

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS `acompanhamento` (
  `acompanhamentoId` int NOT NULL AUTO_INCREMENT,
  `alunoId` int DEFAULT NULL,
  `dataInicioAcompanhamento` date DEFAULT NULL,
  `dataFimAcompanhamento` date DEFAULT NULL,
  `objetivoAcompanhamento` text COLLATE utf8mb4_unicode_ci,
  `observacaoAcompanhamento` text COLLATE utf8mb4_unicode_ci,
  `statusAcompanhamento` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`acompanhamentoId`),
  KEY `alunoId` (`alunoId`),
  CONSTRAINT `acompanhamento_ibfk_1` FOREIGN KEY (`alunoId`) REFERENCES `aluno` (`alunoId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `adaptacao_metodologica` (
  `adaptacaoMetodologiaId` int NOT NULL AUTO_INCREMENT,
  `alunoId` int DEFAULT NULL,
  `professorId` int DEFAULT NULL,
  `orientadorId` int DEFAULT NULL,
  `adaptacaoTipo` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `descricaoAdaptacao` text COLLATE utf8mb4_unicode_ci,
  `recursoAdaptacao` text COLLATE utf8mb4_unicode_ci,
  `nivelAdaptacao` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `metodologia` text COLLATE utf8mb4_unicode_ci,
  `estrategiaMetodologia` text COLLATE utf8mb4_unicode_ci,
  `dataRegistroAdapt` date DEFAULT NULL,
  `resultado` text COLLATE utf8mb4_unicode_ci,
  `status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`adaptacaoMetodologiaId`),
  KEY `alunoId` (`alunoId`),
  KEY `professorId` (`professorId`),
  KEY `orientadorId` (`orientadorId`),
  CONSTRAINT `adaptacao_metodologica_ibfk_1` FOREIGN KEY (`alunoId`) REFERENCES `aluno` (`alunoId`),
  CONSTRAINT `adaptacao_metodologica_ibfk_2` FOREIGN KEY (`professorId`) REFERENCES `professor` (`professorId`),
  CONSTRAINT `adaptacao_metodologica_ibfk_3` FOREIGN KEY (`orientadorId`) REFERENCES `orientador_psicopedagogo` (`orientadorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `aluno` (
  `alunoId` int NOT NULL AUTO_INCREMENT,
  `turmaId` int DEFAULT NULL,
  `responsavelId` int DEFAULT NULL,
  `nomeAluno` varchar(150) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dataNascimentoAluno` date DEFAULT NULL,
  `matriculaAluno` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `anoEscolarAluno` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `statusAluno` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`alunoId`),
  KEY `turmaId` (`turmaId`),
  KEY `responsavelId` (`responsavelId`),
  CONSTRAINT `aluno_ibfk_1` FOREIGN KEY (`turmaId`) REFERENCES `turma` (`turmaId`),
  CONSTRAINT `aluno_ibfk_2` FOREIGN KEY (`responsavelId`) REFERENCES `responsavel_legal` (`responsavelLegal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `apontamento` (
  `id` int NOT NULL AUTO_INCREMENT,
  `data_inicio` date DEFAULT NULL,
  `nome_professor` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `motivo` text COLLATE utf8mb4_unicode_ci,
  `id_pessoa` int DEFAULT NULL,
  `id_professor` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_pessoa` (`id_pessoa`),
  KEY `id_professor` (`id_professor`),
  CONSTRAINT `apontamento_ibfk_1` FOREIGN KEY (`id_pessoa`) REFERENCES `pessoa` (`id`),
  CONSTRAINT `apontamento_ibfk_2` FOREIGN KEY (`id_professor`) REFERENCES `professor` (`professorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `auditoria` (
  `auditoriaId` int NOT NULL AUTO_INCREMENT,
  `pessoaId` int DEFAULT NULL,
  `dataHoraAuditoria` datetime DEFAULT NULL,
  `acaoAuditoria` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `entidadeId` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `descricaoId` text COLLATE utf8mb4_unicode_ci,
  `enderecoIp` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`auditoriaId`),
  KEY `pessoaId` (`pessoaId`),
  CONSTRAINT `auditoria_ibfk_1` FOREIGN KEY (`pessoaId`) REFERENCES `pessoa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `avaliacao` (
  `avaliacaoId` int NOT NULL AUTO_INCREMENT,
  `alunoId` int DEFAULT NULL,
  `disciplinaId` int DEFAULT NULL,
  `notaAvaliacao` double DEFAULT NULL,
  `dataAvaliacao` date DEFAULT NULL,
  `tipoAvaliacao` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `observacaoAvaliacao` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`avaliacaoId`),
  KEY `alunoId` (`alunoId`),
  KEY `disciplinaId` (`disciplinaId`),
  CONSTRAINT `avaliacao_ibfk_1` FOREIGN KEY (`alunoId`) REFERENCES `aluno` (`alunoId`),
  CONSTRAINT `avaliacao_ibfk_2` FOREIGN KEY (`disciplinaId`) REFERENCES `disciplina` (`disciplinaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `controle_acesso` (
  `acessoId` int NOT NULL AUTO_INCREMENT,
  `pessoaID` int DEFAULT NULL,
  `permissaoAcesso` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `recursoAcesso` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `autorizadoAcesso` tinyint DEFAULT '0',
  PRIMARY KEY (`acessoId`),
  KEY `pessoaID` (`pessoaID`),
  CONSTRAINT `controle_acesso_ibfk_1` FOREIGN KEY (`pessoaID`) REFERENCES `pessoa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `disciplina` (
  `disciplinaId` int NOT NULL AUTO_INCREMENT,
  `nomeDisciplina` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `descricaoDisciplina` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`disciplinaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `documento` (
  `documentoId` int NOT NULL AUTO_INCREMENT,
  `alunoId` int DEFAULT NULL,
  `nomeArquivo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tipoDocumento` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dataUpload` datetime DEFAULT NULL,
  `versao` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `caminhoArquivo` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`documentoId`),
  KEY `alunoId` (`alunoId`),
  CONSTRAINT `documento_ibfk_1` FOREIGN KEY (`alunoId`) REFERENCES `aluno` (`alunoId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `entrevista` (
  `id` int NOT NULL AUTO_INCREMENT,
  `aluno_id` int DEFAULT NULL,
  `psicologo_id` int DEFAULT NULL,
  `motivo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `agendada` tinyint(1) DEFAULT '0',
  `data` date DEFAULT NULL,
  `observacoes` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `aluno_id` (`aluno_id`),
  KEY `psicologo_id` (`psicologo_id`),
  CONSTRAINT `entrevista_ibfk_1` FOREIGN KEY (`aluno_id`) REFERENCES `aluno` (`alunoId`),
  CONSTRAINT `entrevista_ibfk_2` FOREIGN KEY (`psicologo_id`) REFERENCES `psicologo` (`pessoaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `intervencao` (
  `intervencaoId` int NOT NULL AUTO_INCREMENT,
  `acompanhamentoId` int DEFAULT NULL,
  `orientadorId` int DEFAULT NULL,
  PRIMARY KEY (`intervencaoId`),
  KEY `acompanhamentoId` (`acompanhamentoId`),
  KEY `orientadorId` (`orientadorId`),
  CONSTRAINT `intervencao_ibfk_1` FOREIGN KEY (`acompanhamentoId`) REFERENCES `acompanhamento` (`acompanhamentoId`),
  CONSTRAINT `intervencao_ibfk_2` FOREIGN KEY (`orientadorId`) REFERENCES `orientador_psicopedagogo` (`orientadorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `mensagem` (
  `mensagemId` int NOT NULL AUTO_INCREMENT,
  `remetenteId` int DEFAULT NULL,
  `destinatarioId` int DEFAULT NULL,
  `conteudoMensagem` text COLLATE utf8mb4_unicode_ci,
  `dataEnvioMensagem` datetime DEFAULT NULL,
  `lidaMensagem` tinyint DEFAULT '0',
  PRIMARY KEY (`mensagemId`),
  KEY `remetenteId` (`remetenteId`),
  KEY `destinatarioId` (`destinatarioId`),
  CONSTRAINT `mensagem_ibfk_1` FOREIGN KEY (`remetenteId`) REFERENCES `pessoa` (`id`),
  CONSTRAINT `mensagem_ibfk_2` FOREIGN KEY (`destinatarioId`) REFERENCES `pessoa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `neurodivergencia` (
  `neurodivergenciaId` int NOT NULL AUTO_INCREMENT,
  `alunoId` int DEFAULT NULL,
  `tipoNeurodivergencia` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `descricaoNeuro` text COLLATE utf8mb4_unicode_ci,
  `observacoesNeuro` text COLLATE utf8mb4_unicode_ci,
  `dataRegistroNeuro` date DEFAULT NULL,
  PRIMARY KEY (`neurodivergenciaId`),
  KEY `alunoId` (`alunoId`),
  CONSTRAINT `neurodivergencia_ibfk_1` FOREIGN KEY (`alunoId`) REFERENCES `aluno` (`alunoId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `notificacao` (
  `notificacaoId` int NOT NULL AUTO_INCREMENT,
  `pessoaId` int DEFAULT NULL,
  `tituloNotificacao` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mensagemNotificacao` text COLLATE utf8mb4_unicode_ci,
  `dataEnvioNotificacao` datetime DEFAULT NULL,
  `recebidaNotificacao` tinyint DEFAULT '0',
  `tipoNotificacao` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`notificacaoId`),
  KEY `pessoaId` (`pessoaId`),
  CONSTRAINT `notificacao_ibfk_1` FOREIGN KEY (`pessoaId`) REFERENCES `pessoa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `ocorrencia_pedagogica` (
  `ocorrenciaId` int NOT NULL AUTO_INCREMENT,
  `alunoId` int DEFAULT NULL,
  `professorId` int DEFAULT NULL,
  `dataOcorrenciaPedagogica` datetime DEFAULT NULL,
  `tipoOcorrencia` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `descricaoOcorrencia` text COLLATE utf8mb4_unicode_ci,
  `providenciaOcorrencia` text COLLATE utf8mb4_unicode_ci,
  `statusOcorrencia` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ocorrenciaId`),
  KEY `alunoId` (`alunoId`),
  KEY `professorId` (`professorId`),
  CONSTRAINT `ocorrencia_pedagogica_ibfk_1` FOREIGN KEY (`alunoId`) REFERENCES `aluno` (`alunoId`),
  CONSTRAINT `ocorrencia_pedagogica_ibfk_2` FOREIGN KEY (`professorId`) REFERENCES `professor` (`professorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `orientador_psicopedagogo` (
  `orientadorId` int NOT NULL,
  PRIMARY KEY (`orientadorId`),
  CONSTRAINT `orientador_psicopedagogo_ibfk_1` FOREIGN KEY (`orientadorId`) REFERENCES `pessoa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `pedagogo` (
  `pessoa` int NOT NULL,
  `especialidade` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`pessoa`),
  CONSTRAINT `pedagogo_ibfk_1` FOREIGN KEY (`pessoa`) REFERENCES `pessoa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `pessoa` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `senha` varchar(60) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telefone` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CPF` char(14) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ativo` tinyint DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `CPF` (`CPF`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `procedimento` (
  `procedimentoId` int NOT NULL AUTO_INCREMENT,
  `acompanhamentoId` int DEFAULT NULL,
  `orientadorId` int DEFAULT NULL,
  `dataProcedimento` date DEFAULT NULL,
  `descricaoProcedimento` text COLLATE utf8mb4_unicode_ci,
  `estrategiaProcedimento` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `resultadoProcedimento` text COLLATE utf8mb4_unicode_ci,
  `observacoesProcedimento` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`procedimentoId`),
  KEY `acompanhamentoId` (`acompanhamentoId`),
  KEY `orientadorId` (`orientadorId`),
  CONSTRAINT `procedimento_ibfk_1` FOREIGN KEY (`acompanhamentoId`) REFERENCES `acompanhamento` (`acompanhamentoId`),
  CONSTRAINT `procedimento_ibfk_2` FOREIGN KEY (`orientadorId`) REFERENCES `orientador_psicopedagogo` (`orientadorId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `professor` (
  `professorId` int NOT NULL,
  `matriculaProfessor` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `especialidade` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`professorId`),
  CONSTRAINT `professor_ibfk_1` FOREIGN KEY (`professorId`) REFERENCES `pessoa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `psicologo` (
  `pessoaId` int NOT NULL,
  `especialidade` text COLLATE utf8mb4_unicode_ci,
  `alunoId` int DEFAULT NULL,
  `reuniao` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`pessoaId`),
  KEY `alunoId` (`alunoId`),
  CONSTRAINT `psicologo_ibfk_1` FOREIGN KEY (`pessoaId`) REFERENCES `pessoa` (`id`),
  CONSTRAINT `psicologo_ibfk_2` FOREIGN KEY (`alunoId`) REFERENCES `aluno` (`alunoId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `relatorio` (
  `relatorioId` int NOT NULL AUTO_INCREMENT,
  `orientadorId` int DEFAULT NULL,
  `alunoId` int DEFAULT NULL,
  `dataGeracao` datetime DEFAULT NULL,
  `tipo` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `periodoInicialRel` date DEFAULT NULL,
  `periodoFinalRel` date DEFAULT NULL,
  `conteudo` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`relatorioId`),
  KEY `orientadorId` (`orientadorId`),
  KEY `alunoId` (`alunoId`),
  CONSTRAINT `relatorio_ibfk_1` FOREIGN KEY (`orientadorId`) REFERENCES `orientador_psicopedagogo` (`orientadorId`),
  CONSTRAINT `relatorio_ibfk_2` FOREIGN KEY (`alunoId`) REFERENCES `aluno` (`alunoId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `responsavel_legal` (
  `responsavelLegal` int NOT NULL,
  `parentescoLegal` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`responsavelLegal`),
  CONSTRAINT `responsavel_legal_ibfk_1` FOREIGN KEY (`responsavelLegal`) REFERENCES `pessoa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
CREATE TABLE IF NOT EXISTS `turma` (
  `turmaId` int NOT NULL AUTO_INCREMENT,
  `nomeTurma` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `anoEscolarTurma` int DEFAULT NULL,
  `turnoTurma` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `anoLetivoTurma` int DEFAULT NULL,
  PRIMARY KEY (`turmaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- Tabelas novas
-- ---------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `diretor` (
  `diretorId` int NOT NULL,
  `cargo` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `escola` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `formacao` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`diretorId`),
  CONSTRAINT `diretor_ibfk_1` FOREIGN KEY (`diretorId`) REFERENCES `pessoa` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `reuniao` (
  `reuniaoId` int NOT NULL AUTO_INCREMENT,
  `solicitanteTipo` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `solicitanteId` int DEFAULT NULL,
  `publicoAlvo` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `alunoId` int DEFAULT NULL,
  `acompanhamentoId` int DEFAULT NULL,
  `dataReuniao` date NOT NULL,
  `motivo` text COLLATE utf8mb4_unicode_ci,
  `statusReuniao` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT 'Agendada',
  `dataRegistro` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`reuniaoId`),
  KEY `alunoId` (`alunoId`),
  KEY `acompanhamentoId` (`acompanhamentoId`),
  CONSTRAINT `reuniao_ibfk_1` FOREIGN KEY (`alunoId`) REFERENCES `aluno` (`alunoId`) ON DELETE SET NULL,
  CONSTRAINT `reuniao_ibfk_2` FOREIGN KEY (`acompanhamentoId`) REFERENCES `acompanhamento` (`acompanhamentoId`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
