package pucrs.poo.estacionamento;

import java.time.Duration;
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
        this.veiculosEstacionados = this.carregaDeEntradasMap("entradas.dat");
        this.ocupacao = 0;
    }
    
    public void entrada(String placa, LocalDateTime horarioEntrada) {
        if (ocupacao >= vagasTotais) {
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
            if (cred < -15) {
                    System.out.print("Entrada recusada, por insuficiência de créditos.");
                    return;
            } else if (cred < 0) {
                System.out.printf("Atenção! Você está com saldo devedor de: R$%.2f", (double) Math.abs(cred));
            }
        }

        veiculosEstacionados.put(placa, horarioEntrada);
        ocupacao++;
    }

    public boolean saida(String placa, LocalDateTime horarioSaida) {
        if (!this.veiculosEstacionados.containsKey(placa)) {
            System.out.print("Houve um erro no sistema. Pedimos perdão pela inconveniência.");
            return false;
        }

        LocalDateTime horarioEntrada = veiculosEstacionados.get(placa);
        Duration dt = Duration.between(horarioEntrada, horarioSaida);
        long minutosTotais = dt.toMinutes();

        if (minutosTotais > 15) {
            Cliente cli = cadClientes.getPorPlaca(placa);
            cli.calculaCusto(horarioEntrada, horarioSaida);        
        }

        veiculosEstacionados.remove(placa);
        ocupacao--;

        return true;
    }

    private Map<String, LocalDateTime> carregaDeEntradasMap(String nomeArquivo) {
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