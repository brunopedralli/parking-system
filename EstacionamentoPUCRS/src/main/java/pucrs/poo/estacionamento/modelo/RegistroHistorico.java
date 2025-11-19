package pucrs.poo.estacionamento.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        this.registros = new ArrayList<>();
    }

    public void add(String placa, LocalDateTime dataEntrada, LocalDateTime dataSaida, double custo) {
        Historico h = new Historico(placa, dataEntrada, dataSaida, custo);
        this.registros.add(h);
    }

    public List<Historico> getRegistros() {
        return this.registros;
    }
}