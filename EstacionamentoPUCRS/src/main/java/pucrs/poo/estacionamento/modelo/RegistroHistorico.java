package pucrs.poo.estacionamento.modelo;

import java.time.LocalDateTime;

public class RegistroHistorico {
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;

    public LocalDateTime getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(LocalDateTime dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDateTime dataSaida) {
        this.dataSaida = dataSaida;
    }
}
