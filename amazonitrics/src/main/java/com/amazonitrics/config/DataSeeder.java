package com.amazonitrics.config;

import com.amazonitrics.model.external.DesmatamentoDado;
import com.amazonitrics.repository.external.DesmatamentoDadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);
    private static final String CSV_FILE_PATH = "/data/dados_terrabrasilis.csv";

    @Autowired
    private DesmatamentoDadoRepository desmatamentoDadoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (desmatamentoDadoRepository.count() > 0) {
            log.info("Banco de dados externo já contém dados. Nenhuma ação necessária.");
            return;
        }

        log.info("Banco de dados externo vazio. Lendo dados do CSV em: {}", CSV_FILE_PATH);

        List<DesmatamentoDado> dadosDeAmostra = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream(CSV_FILE_PATH)) {

            if (is == null) {
                log.error("ERRO: Não foi possível encontrar o arquivo CSV em {}", CSV_FILE_PATH);
                return;
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                reader.readLine();

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        continue;
                    }

                    String[] data = line.split(",");

                    try {
                        String year = data[0].trim();
                        int month = Integer.parseInt(data[1].trim());
                        double area = Double.parseDouble(data[2].trim());
                        String uf = data[3].trim();
                        String className = data[4].trim();
                        int numPol = Integer.parseInt(data[5].trim());
                        dadosDeAmostra.add(new DesmatamentoDado(year, month, area, uf, className, numPol));
                    } catch (NumberFormatException e) {
                        log.warn("Falha ao processar a linha (ignorado): {} - Erro: {}", line, e.getMessage());
                    } catch (ArrayIndexOutOfBoundsException e) {
                        log.warn("Linha mal formada (ignorado): {}", line);
                    }
                }
            }

            if (!dadosDeAmostra.isEmpty()) {
                desmatamentoDadoRepository.saveAll(dadosDeAmostra);
                log.info("Banco de dados externo populado com {} registros do CSV.", dadosDeAmostra.size());
            }

        } catch (Exception e) {
            log.error("Falha ao ler ou popular dados do CSV.", e);
        }
    }
}