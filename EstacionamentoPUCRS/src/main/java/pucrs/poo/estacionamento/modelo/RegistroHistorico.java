package pucrs.poo.estacionamento.modelo;

import java.time.LocalDateTime;

public class RegistroHistorico {
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }
    public RegistroHistorico(LocalDateTime dataEntrada, LocalDateTime dataSaida) {
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
    }
}
