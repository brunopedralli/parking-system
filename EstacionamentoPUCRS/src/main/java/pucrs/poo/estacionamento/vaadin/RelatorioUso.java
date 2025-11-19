package pucrs.poo.estacionamento.vaadin;

import java.time.LocalDateTime;
import java.util.List;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import pucrs.poo.estacionamento.modelo.CadastroClientes;
import pucrs.poo.estacionamento.modelo.Historico;
import pucrs.poo.estacionamento.modelo.RegistroHistorico;

@PageTitle("Relatório de Uso")
@Route("gerencial/relatorio-de-uso")
public class RelatorioUso extends VerticalLayout {
    private final CadastroClientes cadClientes;
    private final RegistroHistorico regHistorico;
    private final TextField usuario;
    private final DateTimePicker dataInicial;
    private final DateTimePicker dataFinal;
    private final Button buscarButton;
    private final Button cancelarButton;
    private final Grid<Historico> grid;

    public RelatorioUso() {
        cadClientes = CadastroClientes.getInstance();
        regHistorico = RegistroHistorico.getInstance();

        usuario = new TextField("CPF");
        dataInicial = new DateTimePicker("Data inicial");
        dataFinal = new DateTimePicker("Data final");

        grid = new Grid<>(Historico.class);

        setSpacing(true);
        setPadding(true);

        add(new H2("Relatório de Uso por Usuário"));

        FormLayout formLayout = new FormLayout(usuario, dataInicial, dataFinal);

        buscarButton = new Button("Buscar", VaadinIcon.CHECK.create());
        buscarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buscarButton.addClickShortcut(Key.ENTER);
        buscarButton.addClickListener(click -> this.gerarRelatorio());

        cancelarButton = new Button("Cancelar");
        Dialog dialogoCancelamento = criaDialogoDeCancelamento();
        cancelarButton.addClickListener(click -> dialogoCancelamento.open());

        HorizontalLayout botoesLayout = new HorizontalLayout(buscarButton, cancelarButton);

        grid.setItems(regHistorico.getRegistros());
        grid.setColumns("placa", "dataEntrada", "dataSaida");

        add(formLayout, botoesLayout, new H2("Registros do Sistema"), grid);
        add(new Hr());

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("gerencial"));
        add(backButton);

        habilitarFormulario(true);
    }

    private void gerarRelatorio() {
        LocalDateTime dataI = dataInicial.getValue();
        LocalDateTime dataF = dataFinal.getValue();
        String pessoa = usuario.getValue();

        if (dataI == null || dataF == null) {
            Notification.show("Selecione um intervalo de datas válido", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR); 
            return;
        }

        if (dataI.isAfter(dataF)) {
            Notification.show("Selecione um intervalo de datas válido", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR); 
            return;
        }

        if (pessoa == null || pessoa.isEmpty()) {
            Notification.show("Insira um CPF válido", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR); 
            return;
        }

        List<Historico> lista = regHistorico.getRegistros().stream()
                                                .filter((Historico h) -> cadClientes.getPorCpf(pessoa).getVeiculos().contains(h.getPlaca()))
                                                .filter((Historico h) -> h.getDataEntrada().isAfter(dataI))
                                                .filter((Historico h) -> h.getDataSaida().isBefore(dataF))
                                                .toList();

        grid.setItems(lista);
        int total = lista.size();

        String mensagem = "Foram encontrados " + total + " registros nesse período";
        Notification.show(mensagem, 3000, Notification.Position.BOTTOM_STRETCH);

        grid.getDataProvider().refreshAll();
        habilitarFormulario(true);
    }

    private void habilitarFormulario(boolean opcao) {
        usuario.setEnabled(opcao);
        dataInicial.setEnabled(opcao);
        dataFinal.setEnabled(opcao);
        buscarButton.setEnabled(opcao);
        cancelarButton.setEnabled(opcao);
    }

    private void limparFormulario() {
        grid.asSingleSelect().clear();
        grid.setItems(regHistorico.getRegistros());
        usuario.clear();
        dataInicial.clear();
        dataFinal.clear();
        usuario.focus();
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