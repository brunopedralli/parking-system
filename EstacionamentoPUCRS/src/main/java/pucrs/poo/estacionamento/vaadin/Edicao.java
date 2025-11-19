package pucrs.poo.estacionamento.vaadin;

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
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
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import pucrs.poo.estacionamento.modelo.CadastroClientes;
import pucrs.poo.estacionamento.modelo.Cliente;
import pucrs.poo.estacionamento.modelo.Estudante;

@PageTitle("Atualização de Clientes")
@Route("atualizacao-de-clientes")
public class Edicao extends VerticalLayout {
    private final CadastroClientes cadClientes;
    private final TextField nome;
    private final TextField cpf;
    private final TextField celular;
    private final ComboBox<String> veiculos;
    private final TextField novoVeiculo;
    private final Button salvarButton;
    private final Button cancelarButton;
    private final Grid<Cliente> grid;
    Cliente clienteSelecionado;

    public Edicao() {
        cadClientes = CadastroClientes.getInstance();

        nome = new TextField("Nome");
        nome.setReadOnly(true);

        cpf = new TextField("CPF");
        cpf.setReadOnly(true);

        celular = new TextField("Celular");
        celular.setReadOnly(true);

        veiculos = new ComboBox<>("Veículos");
        veiculos.setReadOnly(true);

        novoVeiculo = new TextField("Novo Veículo");
        novoVeiculo.setReadOnly(true);

        grid = new Grid<>(Cliente.class);

        setSpacing(true);
        setPadding(true);

        add(new H2("Atualização de Clientes"));

        FormLayout formLayout = new FormLayout(nome, cpf, celular, veiculos, novoVeiculo);

        salvarButton = new Button("Atualizar", VaadinIcon.CHECK.create());
        salvarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        salvarButton.addClickShortcut(Key.ENTER);
        salvarButton.addClickListener(click -> this.atualizarCliente());

        cancelarButton = new Button("Cancelar");
        Dialog dialogoCancelamento = criaDialogoDeCancelamento();
        cancelarButton.addClickListener(click -> dialogoCancelamento.open());

        HorizontalLayout botoesLayout = new HorizontalLayout(salvarButton, cancelarButton);

        grid.setItems(cadClientes.getLista());

        grid.setColumns("nome", "cpf", "celular", "tipo", "veiculos");
        grid.asSingleSelect().addValueChangeListener(event -> preparaEdicaoCliente(event));

        add(formLayout, botoesLayout, new H2("Clientes Cadastrados"), grid);
        add(new Hr());

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("financeiro"));
        add(backButton);

        habilitarFormulario(false);
    }

    private void atualizarCliente() {
        Cliente c = cadClientes.getPorCpf(cpf.getValue());

        String anterior = veiculos.getValue();
        String novo = novoVeiculo.getValue();

        boolean semAnterior = anterior == null || anterior.isEmpty();
        boolean semNovo = novo == null || novo.isEmpty();

        if (semAnterior && semNovo) {
            Notification.show("Você deve selecionar um veículo para atualizar", 3000, Notification.Position.TOP_CENTER)
                        .addThemeVariants(NotificationVariant.LUMO_ERROR); 
            return;
        }

        if (semAnterior) {
            c.cadastraVeiculo(novoVeiculo.getValue());
        } 
        else {
            c.setVeiculos(veiculos.getValue(), novoVeiculo.getValue());
        }

        Notification.show("Veículo atualizado com sucesso!", 3000, Notification.Position.BOTTOM_STRETCH);

        grid.getDataProvider().refreshAll();
        limparFormulario();
        habilitarFormulario(false);
    }

    private void preencherFormulario(Cliente c) {
        nome.setValue(c.getNome());
        cpf.setValue(c.getCpf());
        celular.setValue(c.getCelular());
        veiculos.setItems(c.getVeiculos());
        veiculos.setReadOnly(false);
        novoVeiculo.setReadOnly(false);
    }

    private void habilitarFormulario(boolean opcao) {
        veiculos.setEnabled(opcao);
        novoVeiculo.setEnabled(opcao);
        salvarButton.setEnabled(opcao);
        cancelarButton.setEnabled(opcao);
    }

    private void preparaEdicaoCliente(ComponentValueChangeEvent<Grid<Cliente>, Cliente> event) {
        clienteSelecionado = event.getValue();

        if (clienteSelecionado != null) {
            preencherFormulario(clienteSelecionado);
            habilitarFormulario(true);
        } 
        else {
            limparFormulario();
            habilitarFormulario(false);
        }
    }

    private void limparFormulario() {
        grid.asSingleSelect().clear();
        nome.clear();
        cpf.clear();
        celular.clear();
        veiculos.clear();
        novoVeiculo.clear();
        nome.focus();
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