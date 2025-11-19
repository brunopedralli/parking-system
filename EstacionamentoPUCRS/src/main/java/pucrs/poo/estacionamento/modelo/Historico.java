package pucrs.poo.estacionamento.modelo;

import java.time.LocalDateTime;

public class Historico {
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private String placa;

    public Historico(String placa, LocalDateTime dataEntrada, LocalDateTime dataSaida) {
        this.placa = placa;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
    }

    public LocalDateTime getDataEntrada() {
        return this.dataEntrada;
    }

    public LocalDateTime getDataSaida() {
        return this.dataSaida;
    }

    public String getPlaca() {
        return this.placa;
    }
}