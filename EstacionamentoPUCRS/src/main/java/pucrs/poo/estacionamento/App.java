package pucrs.poo.estacionamento;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    public static Map<String, LocalDateTime> carregaDeEntradasMap(String nomeArquivo) {
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

    public static List<Cliente> carregaClientesLista(String nomeArquivo, 
                                                 CadastroClientes cadastro) {
        List<Cliente> clientes = new LinkedList<>();
        try {
            java.nio.file.Files.lines(java.nio.file.Paths.get(nomeArquivo))
                    .filter(linha -> !linha.trim().isEmpty())
                    .forEach(linha -> {
                        String[] partes = linha.split(",");
                        String cpf = partes[0];
                        String nome = partes[1];
                        String celular = partes[2];
                        Cliente cliente;
                        if (partes[4].equals("Estudante")) {
                            int credito = Integer.parseInt(partes[3]);
                            cliente = new Estudante(cpf, nome, celular, credito);
                            for (int i = 5; i < partes.length; i++) {
                                cliente.cadastraVeiculo(partes[i]);
                            }
                        } else if (partes[4].equals("Tecnopuc")) {
                            int debito = Integer.parseInt(partes[3]);
                            cliente = new Tecnopuc(cpf, nome, celular, debito);
                            for (int i = 5; i < partes.length; i++) {
                                cliente.cadastraVeiculo(partes[i]);
                            }
                        } else if (partes[3].equals("Pucrs")) {
                            cliente = new Pucrs(cpf, nome, celular);
                            for (int i = 4; i < partes.length; i++) {
                                cliente.cadastraVeiculo(partes[i]);
                            }
                        } else {
                            throw new IllegalArgumentException("Tipo de cliente desconhecido: " + partes[4]);
                        }
                        clientes.add(cliente);
                    });
        } catch (java.io.IOException | IllegalArgumentException e) {
            throw new RuntimeException("Erro ao ler arquivo de clientes: " + 
                                                               e.getMessage(), e);
        }
        return clientes;
    }
}