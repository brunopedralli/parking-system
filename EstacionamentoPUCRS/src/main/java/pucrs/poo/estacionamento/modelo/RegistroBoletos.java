package pucrs.poo.estacionamento.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegistroBoletos {
    private List<Boleto> boletos;
    private static RegistroBoletos instance;

    public static RegistroBoletos getInstance() {
        if (instance == null) {
            instance = new RegistroBoletos();
        }

        return instance;
    }

    private RegistroBoletos() {
        this.boletos = new ArrayList<>();
    }

    public List<Boleto> getBoletos() {
        return boletos;
    }

    public List<Boleto> gerarBoletosParaMesAno(int mes, int ano) {
        List<Boleto> gerados = new ArrayList<>();
        for (Cliente c : CadastroClientes.getInstance().getLista()) {
            if (c instanceof Tecnopuc) {
                Tecnopuc e = (Tecnopuc) c;
                boolean existe = boletos.stream().anyMatch(b -> b.getClienteCpf().equals(e.getCpf()) && b.getMes() == mes && b.getAno() == ano);
                if (!existe) {
                    Boleto b = new Boleto(e.getCpf(), e.getNome(), mes, ano, e.getDebitos());
                    boletos.add(b);
                    gerados.add(b);
                }
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
