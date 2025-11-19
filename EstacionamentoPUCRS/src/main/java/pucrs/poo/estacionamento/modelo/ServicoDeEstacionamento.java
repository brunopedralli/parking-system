package pucrs.poo.estacionamento.modelo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class ServicoDeEstacionamento {
    private static ServicoDeEstacionamento instance;
    private final int vagasTotais = 500;
    private int ocupacao;
    private Map<String, LocalDateTime> veiculosEstacionados;
    private CadastroClientes cadClientes;
    private RegistroHistorico regHistorico;

    public static ServicoDeEstacionamento getInstance() {
        if (instance == null) {
            instance = new ServicoDeEstacionamento();
        }

        return instance;
    }

    private ServicoDeEstacionamento() {
        this.cadClientes = CadastroClientes.getInstance();
        this.regHistorico = RegistroHistorico.getInstance();
        this.ocupacao = 0;
        this.veiculosEstacionados = this.carregaDeEntradasMap("entradas.dat");
    }

    public Map<String, LocalDateTime> getEstacionamento() {
        return veiculosEstacionados;
    }

    public int getOcupacao() {
        return this.ocupacao;
    }
    
    public boolean entrada(String placa, LocalDateTime horarioEntrada) {
        if (ocupacao >= vagasTotais) {
            Notification.show("Entrada recusada, pois o estacionamento já está lotado. Pedimos perdão pela inconveniência.", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);            
            return false;
        }

        Cliente cli = cadClientes.getPorPlaca(placa);
        if (cli instanceof Pucrs || cli instanceof Estudante) {
            Set<String> veiculos = cli.getVeiculos();
            for (String s : veiculos) {
                if (veiculosEstacionados.containsKey(s)) {
                    Notification.show("Entrada recusada, pois o cliente já possui outro veículo no estacionamento.", 3000, Notification.Position.TOP_CENTER)
                                .addThemeVariants(NotificationVariant.LUMO_ERROR); 
                    return false;
                }
            }
        }

        if (cli instanceof Estudante) {
            Estudante aluno = (Estudante) cli;
            int cred = aluno.getCreditos();
            if (cred < -15) {
                    Notification.show("Entrada recusada, por insuficiência de créditos.", 3000, Notification.Position.TOP_CENTER)
                                .addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return false;
            } else if (cred < 0) {
                Notification.show(String.format("Atenção! Você está com saldo devedor de: R$%.2f", (double) Math.abs(cred)), 3000, Notification.Position.TOP_CENTER)
                            .addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        }

        veiculosEstacionados.put(placa, horarioEntrada);
        ocupacao++;
        return true;
    }

    public boolean saida(String placa, LocalDateTime horarioSaida) {
        if (!this.veiculosEstacionados.containsKey(placa)) {
            Notification.show("Houve um erro no sistema. Pedimos perdão pela inconveniência", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR);
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
        regHistorico.add(placa, horarioEntrada, horarioSaida);
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
                        ocupacao++;
                    });
        } catch (java.io.IOException e) {
            throw new RuntimeException("Erro ao ler arquivo de entradas: " + 
                                                               e.getMessage(), e);
        }
        return entradas;
    }
}