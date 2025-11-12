package pucrs.poo.estacionamento;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ServicoDeEstacionamento {
    private final int vagasTotais = 500;
    private Map<String, LocalDateTime> veiculosEstacionados;
    private CadastroClientes CadastroClientes;

    public ServicoDeEstacionamento(CadastroClientes cadastro) {
        this.CadastroClientes = cadastro;
        this.veiculosEstacionados = new HashMap<>();
    }
    public void entrada(String placa, LocalDateTime horarioEntrada) {
        return;
    }

    public boolean saida(String placa, LocalDateTime horarioSaida) {
        return false;
    }
}