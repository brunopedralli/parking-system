package pucrs.poo.estacionamento;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ServicoDeEstacionamento {
    private final int vagasTotais = 500;
    private int ocupacao;
    private Map<String, LocalDateTime> veiculosEstacionados;
    private CadastroClientes cadClientes;

    public ServicoDeEstacionamento(CadastroClientes cadClientes) {
        this.cadClientes = cadClientes;
        this.veiculosEstacionados = new HashMap<>();
        this.ocupacao = 0;
    }
    
    public void entrada(String placa, LocalDateTime horarioEntrada) {
        if (this.ocupacao >= 500) {
            System.out.print("Entrada recusada, pois o estacionamento já está lotado. Pedimos perdão pela inconveniência.");
            return;
        }

        Cliente cli = cadClientes.getPorPlaca(placa);
        if (cli instanceof Pucrs || cli instanceof Estudante) {
            Set<String> veiculos = cli.getVeiculos();
            for (String s : veiculos) {
                if (veiculosEstacionados.containsKey(s)) {
                    System.out.print("Entrada recusada, pois o cliente já possui outro veículo no estacionamento.");
                    return;
                }
            }
        }

        if (cli instanceof Estudante) {
            Estudante aluno = (Estudante) cli;
            int cred = aluno.getCreditos();
            if (cred < 0) {
                if (cred < -15) {
                    System.out.print("Entrada recusada, por insuficiência de créditos.");
                    return;
                } else {
                    System.out.printf("Atenção! Você está com saldo devedor de: R$%.2f", (double) Math.abs(cred));
                }
            }
        }

        this.veiculosEstacionados.put(placa, horarioEntrada);
        this.ocupacao++;
    }

    public boolean saida(String placa, LocalDateTime horarioSaida) {
        return false;
    }
}