package pucrs.poo.estacionamento.modelo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RegistroBoletos {
    private List<Boleto> boletos;
    private CadastroClientes cadClientes;
    private RegistroHistorico regHistorico;
    private static RegistroBoletos instance;

    public static RegistroBoletos getInstance() {
        if (instance == null) {
            instance = new RegistroBoletos();
        }

        return instance;
    }

    private RegistroBoletos() {
        this.boletos = new LinkedList<>();
        this.cadClientes = CadastroClientes.getInstance();
        this.regHistorico = RegistroHistorico.getInstance();
    }

    public List<Boleto> getBoletos() {
        return boletos;
    }

    public List<Boleto> gerarBoletosParaMesAno(int mes, int ano) {
        List<Boleto> gerados = new LinkedList<>();
        Map<Tecnopuc, Double> debitos = new HashMap<>();
        List<Historico> historicos = regHistorico.getRegistros();
        List<Historico> lista = historicos.stream()
                                            .filter((Historico h) -> cadClientes.getPorPlaca(h.getPlaca()) instanceof Tecnopuc)
                                            .filter((Historico h) -> h.getDataEntrada().getYear() == ano)
                                            .filter((Historico h) -> h.getDataEntrada().getMonthValue() == mes)
                                            .toList();

        for (Historico hist : lista) {
            String placa = hist.getPlaca();
            Tecnopuc cli = (Tecnopuc) cadClientes.getPorPlaca(placa);
            
            if (!debitos.containsKey(cli)) {
                debitos.put(cli, 0.0);
            }
            else {
                debitos.put(cli, debitos.get(cli) + hist.getCusto());
            }
        }

        for (Tecnopuc c : debitos.keySet()) {
            boolean existe = boletos.stream().anyMatch(b -> b.getClienteCpf().equals(c.getCpf()) && b.getMes() == mes && b.getAno() == ano);
                if (!existe) {
                    Boleto b = new Boleto(c.getCpf(), c.getNome(), mes, ano, debitos.get(c));
                    boletos.add(b);
                    gerados.add(b);
                }
        }
        
        return gerados;
    }

    public boolean registrarPagamento(Boleto boleto, LocalDateTime dataPagamento) {
        if (boleto == null) return false;
        boleto.registrarPagamento(dataPagamento);
        return true;
    }
}