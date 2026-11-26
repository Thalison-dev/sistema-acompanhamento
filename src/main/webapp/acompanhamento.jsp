
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Acompanhamento</title>

    <style>
        /* CONFIGURAÇÕES GERAIS */

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: Arial, Helvetica, sans-serif;
            background-color: #ffffff;
            min-height: 100vh;
        }

        /* CABEÇALHO FIXO */

        .cabecalho {
            position: fixed;
            top: 22px;
            left: 31px;
            right: 31px;
            height: 40px;

            background: linear-gradient(
                to right,
                #568f84,
                #142039
            );

            display: flex;
            justify-content: flex-end;
            align-items: center;

            padding: 0 15px;
            z-index: 1000;
        }

        /* BOTÃO DE APLICAÇÕES */

        .menu-aplicacoes {
            position: absolute;
            left: 0;
            top: 0;

            width: 55px;
            height: 54px;

            background: linear-gradient(
                135deg,
                #31549a,
                #5b80c9
            );

            border-radius: 4px;

            display: flex;
            justify-content: center;
            align-items: center;

            color: white;
            font-size: 25px;
            letter-spacing: 1px;
        }

        /* ÍCONE DO USUÁRIO */

        .perfil {
            width: 22px;
            height: 22px;

            border: 1.5px solid white;
            border-radius: 50%;

            display: flex;
            justify-content: center;
            align-items: center;

            color: white;
            font-size: 14px;
        }

        /* ÁREA PRINCIPAL */

        main {
            margin: 62px 31px 0;
            min-height: 297px;

            background-color: #cecece;

            display: flex;
            flex-direction: column;
            align-items: center;

            padding: 10px 20px 20px;
        }

        /* TÍTULO */

        h1 {
            font-size: 21px;
            font-weight: normal;
            color: #080808;

            margin-bottom: 10px;
        }

        /* PAINEL VERDE */

        .painel-acompanhamentos {
            width: 100%;
            max-width: 420px;
            height: 233px;

            background: linear-gradient(
                145deg,
                #389b70,
                #385d56
            );

            border-radius: 6px;

            padding: 20px 17px;

            display: flex;
            flex-direction: column;
        }

        /* CABEÇALHO DAS COLUNAS */

        .cabecalho-lista {
            display: grid;

            grid-template-columns:
                1.2fr 1fr 1fr 1fr 0.65fr;

            gap: 5px;

            margin-bottom: 5px;
            flex-shrink: 0;
        }

        .coluna-titulo {
            background-color: #304c94;
            color: white;

            font-size: 10px;
            text-align: center;

            padding: 7px 2px;

            border-radius: 5px;
            white-space: nowrap;
        }

        /* ÁREA COM ROLAGEM */

        .lista-acompanhamentos {
            flex: 1;
            min-height: 0;

            overflow-y: scroll;
            overflow-x: hidden;

            padding-right: 5px;
        }

        /* PERSONALIZAÇÃO DA BARRA DE ROLAGEM */

        .lista-acompanhamentos::-webkit-scrollbar {
            width: 8px;
        }

        .lista-acompanhamentos::-webkit-scrollbar-track {
            background: transparent;
        }

        .lista-acompanhamentos::-webkit-scrollbar-thumb {
            background-color: #ffffff;
            border-radius: 10px;
        }

        .lista-acompanhamentos {
            scrollbar-width: thin;
            scrollbar-color: #ffffff transparent;
        }

        /* LINHAS DOS ACOMPANHAMENTOS */

        .acompanhamento {
            display: grid;

            grid-template-columns:
                1.2fr 1fr 1fr 1fr 0.65fr;

            gap: 5px;

            margin-bottom: 5px;
            min-height: 91px;
        }

        /* CÉLULAS */

        .celula {
            background-color: rgba(255, 255, 255, 0.52);

            border-radius: 6px;

            display: flex;
            justify-content: center;
            align-items: center;

            text-align: center;

            padding: 5px;

            font-size: 10px;
            color: #17201e;

            overflow-wrap: anywhere;
        }

        /* DATA */

        .data {
            font-size: 7px;
        }

        /* RESPONSIVIDADE */

        @media (max-width: 500px) {

            main {
                margin-left: 10px;
                margin-right: 10px;
                padding-left: 8px;
                padding-right: 8px;
            }

            .cabecalho {
                left: 10px;
                right: 10px;
            }

            .painel-acompanhamentos {
                padding: 15px 8px;
            }

            .coluna-titulo {
                font-size: 8px;
            }

            .celula {
                font-size: 8px;
            }

            .data {
                font-size: 6px;
            }
        }

    </style>
</head>

<body>

    <!-- CABEÇALHO -->

    <header class="cabecalho">

        <div class="menu-aplicacoes">
            ⠿
        </div>

        <div class="perfil">
            ♙
        </div>

    </header>


    <!-- CONTEÚDO PRINCIPAL -->

    <main>

        <h1>Acompanhamento</h1>


        <!-- PAINEL DE ACOMPANHAMENTOS -->

        <section class="painel-acompanhamentos">


            <!-- TÍTULOS DAS COLUNAS -->

            <div class="cabecalho-lista">

                <div class="coluna-titulo">
                    Descrição
                </div>

                <div class="coluna-titulo">
                    Aluno & turma
                </div>

                <div class="coluna-titulo">
                    Descrição
                </div>

                <div class="coluna-titulo">
                    Descrição
                </div>

                <div class="coluna-titulo">
                    Data
                </div>

            </div>


            <!-- LISTA COM ROLAGEM -->

            <div class="lista-acompanhamentos">


                <!-- ACOMPANHAMENTO 1 -->

                <div class="acompanhamento">

                    <div class="celula">
                        Demonstrou participação ativa nas atividades propostas.
                    </div>

                    <div class="celula">
                        Aluno 01
                        <br>
                        Turma A
                    </div>

                    <div class="celula">
                        Boa interação com os colegas.
                    </div>

                    <div class="celula">
                        Desenvolvimento positivo.
                    </div>

                    <div class="celula data">
                        20/11/2026
                    </div>

                </div>


                <!-- ACOMPANHAMENTO 2 -->

                <div class="acompanhamento">

                    <div class="celula">
                        Apresentou evolução na realização das tarefas.
                    </div>

                    <div class="celula">
                        Aluno 02
                        <br>
                        Turma B
                    </div>

                    <div class="celula">
                        Necessitou de auxílio em algumas atividades.
                    </div>

                    <div class="celula">
                        Acompanhamento contínuo.
                    </div>

                    <div class="celula data">
                        22/11/2026
                    </div>

                </div>


                <!-- ACOMPANHAMENTO 3 -->

                <div class="acompanhamento">

                    <div class="celula">
                        Um pequeno passo também é uma grande conquista.
                    </div>

                    <div class="celula">
                        Aluno 03
                        <br>
                        Turma C
                    </div>

                    <div class="celula">
                        Cada aluno tem seu próprio ritmo.
                    </div>

                    <div class="celula">
                        Continue evoluindo!
                    </div>

                    <div class="celula data">
                        25/11/2026
                    </div>

                </div>


            </div>

        </section>

    </main>

</body>

</html>

                                                                                                       