package pucrs.poo.estacionamento;

import java.util.List;

public class CadastroClientes {
    private List<Cliente> lista;

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
}