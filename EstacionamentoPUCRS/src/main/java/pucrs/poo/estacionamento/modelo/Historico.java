package pucrs.poo.estacionamento.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Historico {
     private String placa;
    private List<RegistroHistorico> registros;

    public Historico(String placa) {
        this.placa = placa;
        this.registros = new ArrayList<>();
    }

    public void adicionarEntrada(LocalDateTime dataHoraEntrada) {
        RegistroHistorico registro = new RegistroHistorico();
        registro.setDataEntrada(dataHoraEntrada);
        registros.add(registro);
    }

    public void adicionarSaida(LocalDateTime dataHoraSaida) {
        for (int i = registros.size() - 1; i >= 0; i--) {
            RegistroHistorico registro = registros.get(i);
            if (registro.getDataSaida() == null) {
                registro.setDataSaida(dataHoraSaida);
                break;
            }
        }
    }

    public String getPlaca() {
        return placa;
    }

    public List<RegistroHistorico> getRegistros() {
        return registros;
    }
}
