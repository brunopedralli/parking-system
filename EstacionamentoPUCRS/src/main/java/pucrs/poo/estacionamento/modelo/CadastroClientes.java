package pucrs.poo.estacionamento.modelo;

import pucrs.poo.estacionamento.vaadin.*;
import java.util.LinkedList;
import java.util.List;

public class CadastroClientes {
    private static CadastroClientes instance;
    private List<Cliente> lista;

    public static CadastroClientes getInstance() {
        if (instance == null) {
            instance = new CadastroClientes();
        }

        return instance;
    }

    private CadastroClientes() {
        this.lista = carregaClientesLista("clientes.dat");
    }

    public void add(Cliente cli) {
        lista.add(cli);
    }

    public Cliente getPorCpf(String cpf) {
        for (Cliente c : lista) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }

        return null;
    }

    public Cliente getPorPlaca(String placa) {
        for (Cliente cli : lista) {
            if (cli.getVeiculos().contains(placa)) {
                return cli;
            }
        }
        
        return null;
    }

    public List<Cliente> getLista() {
        return this.lista;
    }

    private List<Cliente> carregaClientesLista(String nomeArquivo) {
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