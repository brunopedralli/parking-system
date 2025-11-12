package pucrs.poo.estacionamento;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    public static Map<String, LocalDateTime> carregaDeEntradasMap(String nomeArquivo) {
        Map<String,LocalDateTime> entradas = new HashMap<>();
        try {
            java.nio.file.Files.lines(java.nio.file.Paths.get(nomeArquivo))
                    .filter(linha -> !linha.trim().isEmpty())
                    .forEach(linha -> {
                        String[] partes = linha.split(",");
                        String placa = partes[0];
                        // String data = partes[1];
                        // String hora = partes[2];

                        // Combina data e hora e converte para LocalDateTime
                        // String dataHora = data + " " + hora;
                        // DateTimeFormatter formatter = 
                                    // DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime horarioEntrada = 
                                    LocalDateTime.now();

                        // Registra a entrada no estacionamento
                        entradas.put(placa, horarioEntrada);
                    });
        } catch (java.io.IOException e) {
            throw new RuntimeException("Erro ao ler arquivo de entradas: " + 
                                                               e.getMessage(), e);
        }
        return entradas;
    }
}