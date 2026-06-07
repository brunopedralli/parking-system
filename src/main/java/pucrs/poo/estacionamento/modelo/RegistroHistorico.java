package pucrs.poo.estacionamento.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class RegistroHistorico {
    private List<Historico> registros;
    private static RegistroHistorico instance;

    public static RegistroHistorico getInstance() {
        if (instance == null) {
            instance = new RegistroHistorico();
        }

        return instance;
    }
    
    private RegistroHistorico() {
        this.registros = this.carregaHistoricoLista("TicketsLog.dat");
    }

    public List<Historico> getRegistros() {
        return this.registros;
    }

    public void add(String placa, LocalDateTime dataEntrada, LocalDateTime dataSaida, double custo) {
        Historico h = new Historico(placa, dataEntrada, dataSaida, custo);
        this.registros.add(h);
    }

    private List<Historico> carregaHistoricoLista(String nomeArquivo) {
        List<Historico> historicos = new LinkedList<>();
        try {
            java.nio.file.Files.lines(java.nio.file.Paths.get(nomeArquivo))
                    .filter(linha -> !linha.trim().isEmpty())
                    .forEach(linha -> {
                        String[] partes = linha.split(",");
                        String placa = partes[0];
                        String data = partes[1];
                        String horaEntrada = partes[2];
                        String horaSaida = partes[3];
                        Double custo = Double.valueOf(partes[5]);

                        // Combina data e hora e converte para LocalDateTime
                        String dataHoraEntrada = data + " " + horaEntrada;
                        String dataHoraSaida = data + " " + horaSaida;

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
                        LocalDateTime horarioEntrada = LocalDateTime.parse(dataHoraEntrada, formatter);
                        LocalDateTime horarioSaida = LocalDateTime.parse(dataHoraSaida, formatter);

                        // Registra o historico
                        Historico h = new Historico(placa, horarioEntrada, horarioSaida, custo);
                        historicos.add(h);
                    });
        } catch (java.io.IOException e) {
            throw new RuntimeException("Erro ao ler arquivo de históricos: " + e.getMessage(), e);
        }
        return historicos;
    }
}