// Arquivo principal de scripts do sistema

document.addEventListener('DOMContentLoaded', function () {

    // Confirmação para links/botões de exclusão: <a data-confirmar="Excluir este aluno?">
    document.querySelectorAll('[data-confirmar]').forEach(function (el) {
        el.addEventListener('click', function (evento) {
            if (!window.confirm(el.getAttribute('data-confirmar'))) {
                evento.preventDefault();
            }
        });
    });

    // Converte datas aaaa-mm-dd em dd/mm/aaaa nos elementos marcados com .data
    document.querySelectorAll('.data').forEach(function (el) {
        var texto = (el.textContent || '').trim();
        var partes = /^(\d{4})-(\d{2})-(\d{2})/.exec(texto);
        if (partes) {
            el.textContent = partes[3] + '/' + partes[2] + '/' + partes[1];
        }
    });

    // Some com as mensagens de sucesso depois de alguns segundos
    document.querySelectorAll('.alert-success[data-auto-fechar]').forEach(function (el) {
        setTimeout(function () { el.style.display = 'none'; }, 6000);
    });
});
