package pucrs.poo.estacionamento.modelo;

import java.time.LocalDateTime;

public class Boleto {
    private CadastroClientes cadClientes;
    private String clienteCpf;
    private String clienteNome;
    private int mes;
    private int ano;
    private double valor;
    private boolean pago;
    private LocalDateTime dataPagamento;

    public Boleto(String clienteCpf, String clienteNome, int mes, int ano, double valor) {
        this.clienteCpf = clienteCpf;
        this.clienteNome = clienteNome;
        this.mes = mes;
        this.ano = ano;
        this.valor = valor;
        this.pago = false;
        this.dataPagamento = null;
        cadClientes = CadastroClientes.getInstance();
    }

    public String getClienteCpf() {
        return clienteCpf;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public double getValor() {
        return valor;
    }

    public boolean isPago() {
        return pago;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void registrarPagamento(LocalDateTime dataPagamento) {
        Tecnopuc cli = (Tecnopuc) cadClientes.getPorCpf(this.clienteCpf);
        cli.abateDebito(this.valor);

        this.pago = true;
        this.dataPagamento = dataPagamento;
    }

    @Override
    public String toString() {
        return clienteNome + " - " + String.format("%02d/%04d", mes, ano) + " - R$" + String.format("%.2f", valor) + (pago ? " (Pago)" : " (Aberto)");
    }
}