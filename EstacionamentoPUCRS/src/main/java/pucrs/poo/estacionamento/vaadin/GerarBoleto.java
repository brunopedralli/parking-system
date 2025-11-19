package pucrs.poo.estacionamento.vaadin;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import pucrs.poo.estacionamento.modelo.Boleto;
import pucrs.poo.estacionamento.modelo.RegistroBoletos;

@PageTitle("Gerar Boleto")
@Route("finaceiro/gerar-boleto")
public class GerarBoleto extends VerticalLayout {

    public GerarBoleto() {
        add(new Hr());

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate(""));

        ComboBox<Integer> mesCombo = new ComboBox<>("Mês");
        mesCombo.setItems(IntStream.rangeClosed(1, 12).boxed().collect(Collectors.toList()));
        mesCombo.setValue(LocalDateTime.now().getMonthValue());

        TextField anoField = new TextField("Ano");
        anoField.setValue(String.valueOf(Year.now().getValue()));

        Button gerarButton = new Button("Gerar Boletos");

        Grid<Boleto> grid = new Grid<>(Boleto.class, false);
        grid.addColumn(Boleto::getClienteNome).setHeader("Empresa");
        grid.addColumn(b -> String.format("%02d/%04d", b.getMes(), b.getAno())).setHeader("Mês/Ano");
        grid.addColumn(Boleto::getValor).setHeader("Valor");
        grid.addColumn(b -> b.isPago() ? "Sim" : "Não").setHeader("Pago");

        gerarButton.addClickListener(e -> {
            Integer mes = mesCombo.getValue();
            int ano = Year.now().getValue();
            try {
                if (mes == null) {
                    Notification.show("Selecione um mês", 3000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }
                ano = Integer.parseInt(anoField.getValue());
            } catch (NumberFormatException ex) {
                Notification.show("Ano inválido", 3000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }

            var gerados = RegistroBoletos.getInstance().gerarBoletosParaMesAno(mes, ano);
            if (gerados.isEmpty()) {
                Notification.show("Nenhum boleto novo gerado", 3000, Notification.Position.TOP_CENTER);
            } else {
                Notification.show(gerados.size() + " boletos gerados", 3000, Notification.Position.TOP_CENTER);
            }
            grid.setItems(RegistroBoletos.getInstance().getBoletos());
        });

        Button registrarPagamento = new Button("Registrar Pagamento");
        registrarPagamento.addClickListener(e -> {
            var opt = grid.getSelectionModel().getFirstSelectedItem();
            if (opt.isEmpty()) {
                Notification.show("Selecione um boleto na grade", 2500, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_ERROR);
                return;
            }
            Boleto b = opt.get();
            if (b.isPago()) {
                Notification.show("Boleto já está pago", 2500, Notification.Position.TOP_CENTER);
                return;
            }
            RegistroBoletos.getInstance().registrarPagamento(b, LocalDateTime.now());
            grid.getDataProvider().refreshItem(b);
            Notification.show("Pagamento registrado", 2500, Notification.Position.TOP_CENTER);
        });

        add(backButton, mesCombo, anoField, gerarButton, registrarPagamento, grid);
        setSizeFull();
    }

}