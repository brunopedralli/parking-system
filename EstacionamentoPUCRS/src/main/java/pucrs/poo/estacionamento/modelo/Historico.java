package pucrs.poo.estacionamento.modelo;

import java.time.LocalDateTime;

public class Historico {
    private LocalDateTime dataEntrada;
    private LocalDateTime dataSaida;
    private String placa;
    private double custo;

    public Historico(String placa, LocalDateTime dataEntrada, LocalDateTime dataSaida, double custo) {
        this.placa = placa;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.custo = custo;
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

    public double getCusto() {
        return this.custo;
    }
}