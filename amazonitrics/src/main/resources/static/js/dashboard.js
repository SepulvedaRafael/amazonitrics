document.addEventListener('DOMContentLoaded', () => {
    const themeToggle = document.getElementById('themeToggle');
    const html = document.documentElement;

    function applyTheme(theme) {
        html.dataset.theme = theme;
        themeToggle.innerText = theme === 'dark' ? 'Tema Claro' : 'Tema Escuro';
        localStorage.setItem('theme', theme);

        if (window.meuGrafico) {
            const chartTextColor = theme === 'dark' ? '#ffffff' : '#000000';
            window.meuGrafico.options.scales.x.ticks.color = chartTextColor;
            window.meuGrafico.options.scales.y.ticks.color = chartTextColor;
            window.meuGrafico.options.plugins.legend.labels.color = chartTextColor;
            window.meuGrafico.update();
        }
    }

    const savedTheme = localStorage.getItem('theme') || 'light';
    applyTheme(savedTheme);

    themeToggle.addEventListener('click', () => {
        const currentTheme = html.dataset.theme;
        applyTheme(currentTheme === 'light' ? 'dark' : 'light');
    });

    const accordions = document.querySelectorAll('.accordion-item');
    accordions.forEach(accordion => {
        accordion.addEventListener('toggle', (e) => {
            if (accordion.open) {
                accordions.forEach(other => {
                    if (other !== accordion) {
                        other.open = false;
                    }
                });
            }
        });
    });

    const tipoDadosSelect = document.getElementById('tipoDados');
    const tamanhoInput = document.getElementById('tamanho');

    function toggleTamanhoInput() {
        if (tipoDadosSelect.value.startsWith('DADOS_EXTERNOS')) {
            tamanhoInput.disabled = true;
            tamanhoInput.style.backgroundColor = 'var(--border-color)';
        } else {
            tamanhoInput.readOnly = false;
            tamanhoInput.style.backgroundColor = 'var(--bg-color)';
        }
    }

    tipoDadosSelect.addEventListener('change', toggleTamanhoInput);
    toggleTamanhoInput();

    if (typeof benchmarkData !== 'undefined' && benchmarkData.length > 0) {
        criarGrafico(benchmarkData);
    } else {
        console.log("Nenhum dado de benchmark para exibir.");
    }
});

window.meuGrafico = null;

function criarGrafico(dados) {
    const ctx = document.getElementById('graficoBenchmark').getContext('2d');

    const currentTheme = document.documentElement.dataset.theme;
    const chartTextColor = currentTheme === 'dark' ? '#ffffff' : '#000000';

    const labels = dados.map(res => res.nomeAlgoritmo);
    const data = dados.map(res => res.tempoMs);

    if (window.meuGrafico) {
        window.meuGrafico.destroy();
    }

    window.meuGrafico = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Tempo de Execução (ms)',
                data: data,
                backgroundColor: 'rgba(64, 145, 108, 0.6)',
                borderColor: 'rgba(45, 106, 79, 1)',
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Tempo (ms)',
                        color: chartTextColor
                    },
                    ticks: {
                        color: chartTextColor
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Algoritmo',
                        color: chartTextColor
                    },
                    ticks: {
                        color: chartTextColor
                    }
                }
            },
            plugins: {
                legend: {
                    labels: {
                        color: chartTextColor
                    }
                }
            }
        }
    });
}