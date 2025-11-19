package pucrs.poo.estacionamento.vaadin;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import pucrs.poo.estacionamento.modelo.Cliente;
import pucrs.poo.estacionamento.modelo.ServicoDeEstacionamento;
import pucrs.poo.estacionamento.modelo.Tecnopuc;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@PageTitle("Total de Entradas")
@Route("gerencial/total-de-entradas")
public class TotalDeEntradas extends VerticalLayout {
    public TotalDeEntradas() {
    private final DatePicker dataInicio = new DatePicker("Data Inicial");
    private final DatePicker dataFim = new DatePicker("Data Final");
    private final Grid<ResumoTipoUsuario> grid = new Grid<>(ResumoTipoUsuario.class);

    public TotalDeEntradas() {
        add(new H1("Relatório de Entradas por Tipo de Usuário"));
        add(new Hr());

        Button filtrarButton = new Button("Gerar Relatório", e -> gerarRelatorio());
        HorizontalLayout filtros = new HorizontalLayout(dataInicio, dataFim, filtrarButton);
        filtros.setAlignItems(Alignment.END);
        add(filtros);

        // Configurar Grid
        grid.removeAllColumns();
        grid.addColumn(ResumoTipoUsuario::getTipoUsuario).setHeader("Tipo de Usuário");
        grid.addColumn(ResumoTipoUsuario::getTotalEntradas).setHeader("Total de Entradas");
        add(grid);

        Button backButton = new Button("Voltar", e -> UI.getCurrent().navigate("gerencial"));
        add(backButton);
    }

    private void gerarRelatorio() {
        LocalDate inicio = dataInicio.getValue();
        LocalDate fim = dataFim.getValue();

        if (inicio == null || fim == null) {
            Notification.show("Selecione o intervalo de datas.", 3000, Notification.Position.MIDDLE);
            return;
        }

        List<ServicoDeEstacionamento> servicos = Tecnopuc.getServicos();

        // Filtra os serviços dentro do intervalo de datas
        List<ServicoDeEstacionamento> filtrados = servicos.stream()
                .filter(s -> s.getDataEntrada().toLocalDate().isAfter(inicio.minusDays(1)) &&
                             s.getDataEntrada().toLocalDate().isBefore(fim.plusDays(1)))
                .collect(Collectors.toList());

        // Agrupa por tipo de usuário (usa getTipoUsuario() do Cliente)
        Map<String, Long> contagemPorTipo = filtrados.stream()
                .collect(Collectors.groupingBy(
                        s -> s.getCliente().getTipoUsuario(), // método que deve existir em Cliente
                        Collectors.counting()
                ));

        // Transforma em lista para exibir no Grid
        List<ResumoTipoUsuario> resumoList = contagemPorTipo.entrySet().stream()
                .map(e -> new ResumoTipoUsuario(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        grid.setItems(resumoList);

        if (resumoList.isEmpty()) {
            Notification.show("Nenhuma entrada encontrada no intervalo informado.", 3000, Notification.Position.MIDDLE);
        }
    }

    // Classe auxiliar para mostrar os dados no Grid
    public static class ResumoTipoUsuario {
        private String tipoUsuario;
        private long totalEntradas;

        public ResumoTipoUsuario(String tipoUsuario, long totalEntradas) {
            this.tipoUsuario = tipoUsuario;
            this.totalEntradas = totalEntradas;
        }

        public String getTipoUsuario() {
            return tipoUsuario;
        }

        public long getTotalEntradas() {
            return totalEntradas;
        }
    }
}
        add(new Hr());

        Button backButton = new Button("Voltar");
        backButton.addClickListener(e -> UI.getCurrent().navigate("gerencial"));
        add(backButton);
    }
}