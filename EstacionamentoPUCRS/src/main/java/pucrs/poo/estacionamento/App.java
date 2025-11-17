package pucrs.poo.estacionamento;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        CadastroClientes cadCli = CadastroClientes.getInstance();

        
        //SpringApplication.run(App.class, args);
    }
}