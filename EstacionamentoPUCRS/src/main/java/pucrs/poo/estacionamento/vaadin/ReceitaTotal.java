package pucrs.poo.estacionamento.vaadin;

import java.util.List;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import pucrs.poo.estacionamento.modelo.Historico;
import pucrs.poo.estacionamento.modelo.RegistroHistorico;

@PageTitle("Receita Total")
@Route("financeiro/receita-total")
public class ReceitaTotal extends VerticalLayout {
    private final RegistroHistorico regHistorico;
    private final ComboBox<String> mes;
    private final ComboBox<Integer> ano;
    private final Button buscarButton;
    private final Button cancelarButton;
    private final Grid<Historico> grid;

    public ReceitaTotal() {
        regHistorico = RegistroHistorico.getInstance();

        mes = new ComboBox<>("Mês");
        mes.setItems("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");

        ano = new ComboBox<>("Ano");
        ano.setItems(2021, 2022, 2023, 2024, 2025);

        grid = new Grid<>(Historico.class);

        setSpacing(true);
        setPadding(true);

        add(new H2("Receita Total"));

        FormLayout formLayout = new FormLayout(mes, ano);

        buscarButton = new Button("Buscar", VaadinIcon.CHECK.create());
        buscarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buscarButton.addClickShortcut(Key.ENTER);
        buscarButton.addClickListener(click -> this.gerarReceita());

        cancelarButton = new Button("Cancelar");
        Dialog dialogoCancelamento = criaDialogoDeCancelamento();
        cancelarButton.addClickListener(click -> dialogoCancelamento.open());

        HorizontalLayout botoesLayout = new HorizontalLayout(buscarButton, cancelarButton);

        grid.setItems(regHistorico.getRegistros());
        grid.setColumns("placa", "dataEntrada", "dataSaida");

        add(formLayout, botoesLayout, new H2("Registros do Sistema"), grid);
        add(new Hr());

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("financeiro"));
        add(backButton);

        habilitarFormulario(true);
    }

    private void gerarReceita() {
        boolean semMes = mes.getValue() == null || mes.getValue().isEmpty();
        boolean semAno = ano.getValue() == null;

        if (semMes || semAno) {
            Notification.show("Selecione um período válido", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR); 
            return;
        }

        int m;
        switch (mes.getValue()) {
            case "Janeiro":
                m = 1;
                break;
            case "Fevereiro":
                m = 2;
                break;
            case "Março":
                m = 3;
                break;
            case "Abril":
                m = 4;
                break;
            case "Maio":
                m = 5;
                break;
            case "Junho":
                m = 6;
                break;
            case "Julho":
                m = 7;
                break;
            case "Agosto":
                m = 8;
                break;
            case "Setembro":
                m = 9;
                break;
            case "Outubro":
                m = 10;
                break;
            case "Novembro":
                m = 11;
                break;
            case "Dezembro":
                m = 12;
                break;
            default:
                m = 0;
        }

        int y = ano.getValue();

        List<Historico> lista = regHistorico.getRegistros().stream()
                                                .filter((Historico h) -> h.getDataEntrada().getMonthValue() == m)
                                                .filter((Historico h) -> h.getDataEntrada().getYear() == y)
                                                .toList();

        grid.setItems(lista);
        double total = lista.stream()
                            .mapToDouble((Historico h) -> h.getCusto())
                            .sum();

        String mensagem = "A receita do estacionamento no período foi de: R$" + String.format("%.2f", total);
        Notification.show(mensagem, 3000, Notification.Position.BOTTOM_STRETCH)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        grid.getDataProvider().refreshAll();
        habilitarFormulario(true);
    }

    private void habilitarFormulario(boolean opcao) {
        mes.setEnabled(opcao);
        ano.setEnabled(opcao);
        buscarButton.setEnabled(opcao);
        cancelarButton.setEnabled(opcao);
    }

    private void limparFormulario() {
        grid.asSingleSelect().clear();
        grid.setItems(regHistorico.getRegistros());
        mes.clear();
        ano.clear();
        mes.focus();
    }

    private Dialog criaDialogoDeCancelamento() {
        Dialog dialogo = new Dialog();
        dialogo.setHeaderTitle("Confirmar cancelamento");
        dialogo.add(new Paragraph("Você tem certeza que deseja cancelar e limpar o formulário?"));

        Button confirmarCancelamento = new Button("Sim, cancelar", e -> {
            limparFormulario();
            dialogo.close();
        });

        confirmarCancelamento.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        Button fecharDialogo = new Button("Não", e -> dialogo.close());
        dialogo.getFooter().add(fecharDialogo, confirmarCancelamento);
        return dialogo;
    }
}