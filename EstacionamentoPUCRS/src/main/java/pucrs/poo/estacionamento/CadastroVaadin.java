package pucrs.poo.estacionamento;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
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
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Cadastro de Clientes")
@Route("cadastro-de-clientes")
public class CadastroVaadin extends VerticalLayout {
    private final CadastroClientes cadClientes;
    private final TextField nome;
    private final TextField cpf;
    private final TextField celular;
    private final IntegerField creditos;
    private final NumberField debitos;
    private final ComboBox<String> tipoUsuario;
    private final Checkbox aceitaTermos;
    private final Grid<Cliente> grid;

    public CadastroVaadin() {
        cadClientes = CadastroClientes.getInstance();
        nome = new TextField("Nome");
        cpf = new TextField("CPF");
        celular = new TextField("Celular");
        tipoUsuario = new ComboBox<>("Tipo de Usuário");
        tipoUsuario.setItems("Estudante", "Tecnopuc", "Funcionário PUCRS");
        creditos = new IntegerField("Créditos do Estudante");
        debitos = new NumberField("Débitos do Profissional");
        aceitaTermos = new Checkbox("Aceito os termos de serviço");
        grid = new Grid<>(Cliente.class);

        creditos.setVisible(false);
        debitos.setVisible(false);

        tipoUsuario.addValueChangeListener(event -> {
            String valor = event.getValue();

            if ("Estudante".equals(valor)) {
                creditos.setVisible(true);
                debitos.setVisible(false);
                debitos.clear();
            } else if ("Tecnopuc".equals(valor)) {
                debitos.setVisible(true);
                creditos.setVisible(false);
                creditos.clear();
            } else {
                creditos.setVisible(false);
                creditos.clear();
                debitos.setVisible(false);
                debitos.clear();
            }
        });

        setSpacing(true);
        setPadding(true);

        add(new H2("Cadastro de Clientes"));

        FormLayout formLayout = new FormLayout(nome, cpf, celular, tipoUsuario, creditos, debitos, aceitaTermos);

        Button salvarButton = new Button("Cadastrar", VaadinIcon.CHECK.create());
        salvarButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        salvarButton.addClickShortcut(Key.ENTER);
        salvarButton.addClickListener(click -> this.inserirFormulario());

        Button cancelarButton = new Button("Cancelar");
        Dialog dialogoCancelamento = criaDialogoDeCancelamento();
        cancelarButton.addClickListener(click -> dialogoCancelamento.open());

        HorizontalLayout botoesLayout = new HorizontalLayout(salvarButton, cancelarButton);

        grid.setItems(cadClientes.getLista());
        grid.setColumns("nome", "cpf", "celular", "tipo", "veiculos");

        add(formLayout, botoesLayout, new H2("Usuários Cadastrados"), grid);
        add(new Hr());
        
        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("hello"));
        add(backButton);
    }

    private void inserirFormulario() {
        if (aceitaTermos.getValue() == false) {
            Notification.show("Você precisa aceitar os termos de serviço.", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        } 

        if (cpf.getValue() == null || nome.getValue() == null || celular.getValue() == null || tipoUsuario.getValue() == null) {
            Notification.show("Preencha todos os campos do cadastro para prosseguir.", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
            return;
        }

        String tipo = tipoUsuario.getValue();
        Cliente c;
        
        if (tipo.equals("Estudante")) {
            int cred = (creditos.getValue() == null) ? 0 : creditos.getValue();
            c = new Estudante(cpf.getValue(),
                                nome.getValue(),
                                celular.getValue(),
                                cred);

        } 
        else if (tipo.equals("Tecnopuc")) {
            double deb = (debitos.getValue() == null) ? 0 : debitos.getValue();
            c = new Tecnopuc(cpf.getValue(),
                                nome.getValue(),
                                celular.getValue(),
                                deb);

        } 
        else {
            c = new Pucrs(cpf.getValue(),
                            nome.getValue(),
                            celular.getValue());
        }

        cadClientes.add(c);
            
        String mensagem = "Usuário " + c.getNome() + " salvo com sucesso!";
        Notification.show(mensagem, 3000, Notification.Position.BOTTOM_STRETCH);

        grid.getDataProvider().refreshAll();
        limparFormulario();
    }

    private void limparFormulario() {
        nome.clear();
        cpf.clear();
        celular.clear();
        tipoUsuario.clear();
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