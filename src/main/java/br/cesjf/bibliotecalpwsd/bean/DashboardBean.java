/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.AssuntoDAO;
import br.cesjf.bibliotecalpwsd.dao.EmprestimoDAO;
import br.cesjf.bibliotecalpwsd.dao.ReservaDAO;
import br.cesjf.bibliotecalpwsd.model.Assunto;
import br.cesjf.bibliotecalpwsd.model.Emprestimo;
import br.cesjf.bibliotecalpwsd.model.Reserva;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class DashboardBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private List<Reserva> reservas;
    private BarChartModel livrosReservados;
    private List<Emprestimo> emprestimos;
    private BarChartModel livrosEmprestados;
    private List<Assunto> assuntos;
    private BarChartModel livrosReservadosCategoria;
    private BarChartModel livrosEmprestadosCategoria;
    private BarChartModel livrosReservadosEmprestados;
    

    //construtor
    public DashboardBean() {
        reservas = new ReservaDAO().buscarTodas();
        emprestimos = new EmprestimoDAO().buscarTodas();
        assuntos = new AssuntoDAO().buscarTodas();
    }
    
    @PostConstruct
    public void init() {
        criarLivrosReservados();
        criarLivrosEmprestados();
        criarReservadosCategoria();
        criarEmprestadosCategoria();
        criarLivrosReservadosEmprestados();
    }
    
    public void criarLivrosReservados() {
        livrosReservados = new BarChartModel();
        ChartData data = new ChartData();
         
        BarChartDataSet barDataSet = new BarChartDataSet();
        
        barDataSet.setLabel("Quantidade de Livros Reservados por Mês ");
        
        Calendar data1 = Calendar.getInstance();
        data1.setTime(new Date());
        data1.set(Calendar.DAY_OF_MONTH, 1);
        data1.set(Calendar.MONTH, data1.get(Calendar.MONTH)-3);
        data1.set(Calendar.HOUR, 0);
        data1.set(Calendar.MINUTE, 0);
        data1.set(Calendar.SECOND, 0);
        data1.set(Calendar.MILLISECOND, 0);
        
        Calendar data2 = Calendar.getInstance();
        data2.setTime(new Date());
        data2.set(Calendar.DAY_OF_MONTH, 1);
        data2.set(Calendar.MONTH, data2.get(Calendar.MONTH)-2);
        data2.set(Calendar.HOUR, 0);
        data2.set(Calendar.MINUTE, 0);
        data2.set(Calendar.SECOND, 0);
        data2.set(Calendar.MILLISECOND, 0);
        
        Calendar data3 = Calendar.getInstance();
        data3.setTime(new Date());
        data3.set(Calendar.DAY_OF_MONTH, 1);
        data3.set(Calendar.MONTH, data3.get(Calendar.MONTH)-1);
        data3.set(Calendar.HOUR, 0);
        data3.set(Calendar.MINUTE, 0);
        data3.set(Calendar.SECOND, 0);
        data3.set(Calendar.MILLISECOND, 0);
        
        Calendar data4 = Calendar.getInstance();
        data4.setTime(new Date());
        data4.set(Calendar.DAY_OF_MONTH, 1);
        data4.set(Calendar.HOUR, 0);
        data4.set(Calendar.MINUTE, 0);
        data4.set(Calendar.SECOND, 0);
        data4.set(Calendar.MILLISECOND, 0);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        
        int mes1 = 0;
        int mes2 = 0;
        int mes3 = 0;
        
        for(Reserva r: reservas){
            Calendar reserva = Calendar.getInstance();
            reserva.setTime(r.getDataReserva());
            if((reserva.equals(data1) || reserva.after(data1)) && reserva.before(data2)) {
                mes1++;
            } else if((reserva.equals(data2) || reserva.after(data2)) && reserva.before(data3)) {
                mes2++;
            } else if((reserva.equals(data3) || reserva.after(data3)) && reserva.before(data4)) {
                mes3++;
            }
        }
         
        List<Number> values = new ArrayList<>();
        values.add(mes1);
        values.add(mes2);
        values.add(mes3);
        barDataSet.setData(values);
         
        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        barDataSet.setBackgroundColor(bgColor);
         
        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
         
        data.addChartDataSet(barDataSet);
         
        List<String> labels = new ArrayList<>();
        labels.add(mes(data1.get(Calendar.MONTH)));
        labels.add(mes(data2.get(Calendar.MONTH)));
        labels.add(mes(data3.get(Calendar.MONTH)));
        data.setLabels(labels);
        livrosReservados.setData(data);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
         
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Gráfico de Livros Reservados nos 3 Últimos Meses");
        options.setTitle(title);
 
        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
 
        livrosReservados.setOptions(options);
    }
    
    public void criarLivrosEmprestados() {
        livrosEmprestados = new BarChartModel();
        ChartData data = new ChartData();
         
        BarChartDataSet barDataSet = new BarChartDataSet();
        
        barDataSet.setLabel("Quantidade de Livros Emprestados por Mês ");
        
        Calendar data1 = Calendar.getInstance();
        data1.setTime(new Date());
        data1.set(Calendar.DAY_OF_MONTH, 1);
        data1.set(Calendar.MONTH, data1.get(Calendar.MONTH)-3);
        data1.set(Calendar.HOUR, 0);
        data1.set(Calendar.MINUTE, 0);
        data1.set(Calendar.SECOND, 0);
        data1.set(Calendar.MILLISECOND, 0);
        
        Calendar data2 = Calendar.getInstance();
        data2.setTime(new Date());
        data2.set(Calendar.DAY_OF_MONTH, 1);
        data2.set(Calendar.MONTH, data2.get(Calendar.MONTH)-2);
        data2.set(Calendar.HOUR, 0);
        data2.set(Calendar.MINUTE, 0);
        data2.set(Calendar.SECOND, 0);
        data2.set(Calendar.MILLISECOND, 0);
        
        Calendar data3 = Calendar.getInstance();
        data3.setTime(new Date());
        data3.set(Calendar.DAY_OF_MONTH, 1);
        data3.set(Calendar.MONTH, data3.get(Calendar.MONTH)-1);
        data3.set(Calendar.HOUR, 0);
        data3.set(Calendar.MINUTE, 0);
        data3.set(Calendar.SECOND, 0);
        data3.set(Calendar.MILLISECOND, 0);
        
        Calendar data4 = Calendar.getInstance();
        data4.setTime(new Date());
        data4.set(Calendar.DAY_OF_MONTH, 1);
        data4.set(Calendar.HOUR, 0);
        data4.set(Calendar.MINUTE, 0);
        data4.set(Calendar.SECOND, 0);
        data4.set(Calendar.MILLISECOND, 0);
        
        int mes1 = 0;
        int mes2 = 0;
        int mes3 = 0;
        
        for(Emprestimo e: emprestimos){
            Calendar emprestimo = Calendar.getInstance();
            emprestimo.setTime(e.getDataEmprestimo());
            if((emprestimo.equals(data1) || emprestimo.after(data1)) && emprestimo.before(data2)) {
                mes1++;
            } else if((emprestimo.equals(data2) || emprestimo.after(data2)) && emprestimo.before(data3)) {
                mes2++;
            } else if((emprestimo.equals(data3) || emprestimo.after(data3)) && emprestimo.before(data4)) {
                mes3++;
            }
        }
         
        List<Number> values = new ArrayList<>();
        values.add(mes1);
        values.add(mes2);
        values.add(mes3);
        barDataSet.setData(values);
         
        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        bgColor.add("rgba(255, 205, 86, 0.2)");
        barDataSet.setBackgroundColor(bgColor);
         
        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        borderColor.add("rgb(255, 205, 86)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
         
        data.addChartDataSet(barDataSet);
         
        List<String> labels = new ArrayList<>();
        labels.add(mes(data1.get(Calendar.MONTH)));
        labels.add(mes(data2.get(Calendar.MONTH)));
        labels.add(mes(data3.get(Calendar.MONTH)));
        data.setLabels(labels);
        livrosEmprestados.setData(data);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
         
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Gráfico de Livros Emprestados nos 3 Últimos Meses");
        options.setTitle(title);
 
        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
 
        livrosEmprestados.setOptions(options);
    }
    
    public void criarReservadosCategoria() {
        livrosReservadosCategoria = new BarChartModel();
        ChartData data = new ChartData();
         
        BarChartDataSet barDataSet = new BarChartDataSet();
        
        barDataSet.setLabel("Quantidade de Livros Reservados por Categoria ");
        
        Calendar data1 = Calendar.getInstance();
        data1.setTime(new Date());
        data1.set(Calendar.DAY_OF_MONTH, 1);
        data1.set(Calendar.MONTH, data1.get(Calendar.MONTH)-3);
        data1.set(Calendar.HOUR, 0);
        data1.set(Calendar.MINUTE, 0);
        data1.set(Calendar.SECOND, 0);
        data1.set(Calendar.MILLISECOND, 0);
        
        Calendar data2 = Calendar.getInstance();
        data2.setTime(new Date());
        data2.set(Calendar.DAY_OF_MONTH, 1);
        data2.set(Calendar.MONTH, data2.get(Calendar.MONTH)-2);
        data2.set(Calendar.HOUR, 0);
        data2.set(Calendar.MINUTE, 0);
        data2.set(Calendar.SECOND, 0);
        data2.set(Calendar.MILLISECOND, 0);
        
        Calendar data3 = Calendar.getInstance();
        data3.setTime(new Date());
        data3.set(Calendar.DAY_OF_MONTH, 1);
        data3.set(Calendar.MONTH, data3.get(Calendar.MONTH)-1);
        data3.set(Calendar.HOUR, 0);
        data3.set(Calendar.MINUTE, 0);
        data3.set(Calendar.SECOND, 0);
        data3.set(Calendar.MILLISECOND, 0);
        
        Calendar data4 = Calendar.getInstance();
        data4.setTime(new Date());
        data4.set(Calendar.DAY_OF_MONTH, 1);
        data4.set(Calendar.HOUR, 0);
        data4.set(Calendar.MINUTE, 0);
        data4.set(Calendar.SECOND, 0);
        data4.set(Calendar.MILLISECOND, 0);
        
        Map<String, Integer> aux = new HashMap<String, Integer>();
        
        for(Assunto a: assuntos) {
            aux.put(a.getAssunto(), 0);
        }
        aux = aux.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldValue, newValue) -> oldValue, LinkedHashMap::new));
        
        int mes1 = 0;
        int mes2 = 0;
        int mes3 = 0;
        
        for(Reserva r: reservas){
            Calendar reserva = Calendar.getInstance();
            reserva.setTime(r.getDataReserva());
            if((reserva.equals(data1) || reserva.after(data1)) && reserva.before(data4)) {
                for(Assunto a: r.getIdExemplar().getIdLivro().getAssuntoList()) {
                    aux.computeIfPresent(a.getAssunto(), (k, v) -> v + 1);
                }
            }
        }
         
        List<Number> values = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: aux.entrySet()) {
            values.add(entry.getValue());
        }
        barDataSet.setData(values);
        
        int auxValue = 0;
        List<String> bgColor = new ArrayList<>();
        List<String> borderColor = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: aux.entrySet()) {
            auxValue++;
            if(auxValue % 2 == 0) {
                bgColor.add("rgba(255, 99, 132, 0.2)");
                borderColor.add("rgb(255, 99, 132)");
            } else {
                bgColor.add("rgba(255, 159, 64, 0.2)");
                borderColor.add("rgb(255, 159, 64)");
            }
        }
        barDataSet.setBackgroundColor(bgColor);
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
         
        data.addChartDataSet(barDataSet);
         
        List<String> labels = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: aux.entrySet()) {
            labels.add(entry.getKey());
        }
        data.setLabels(labels);
        livrosReservadosCategoria.setData(data);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
         
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Gráfico de Livros Reservados por Categoria nos 3 Últimos Meses");
        options.setTitle(title);
 
        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
 
        livrosReservadosCategoria.setOptions(options);
    }
    
    public void criarEmprestadosCategoria() {
        livrosEmprestadosCategoria = new BarChartModel();
        ChartData data = new ChartData();
         
        BarChartDataSet barDataSet = new BarChartDataSet();
        
        barDataSet.setLabel("Quantidade de Livros Emprestados por Categoria ");
        
        Calendar data1 = Calendar.getInstance();
        data1.setTime(new Date());
        data1.set(Calendar.DAY_OF_MONTH, 1);
        data1.set(Calendar.MONTH, data1.get(Calendar.MONTH)-3);
        data1.set(Calendar.HOUR, 0);
        data1.set(Calendar.MINUTE, 0);
        data1.set(Calendar.SECOND, 0);
        data1.set(Calendar.MILLISECOND, 0);
        
        Calendar data2 = Calendar.getInstance();
        data2.setTime(new Date());
        data2.set(Calendar.DAY_OF_MONTH, 1);
        data2.set(Calendar.MONTH, data2.get(Calendar.MONTH)-2);
        data2.set(Calendar.HOUR, 0);
        data2.set(Calendar.MINUTE, 0);
        data2.set(Calendar.SECOND, 0);
        data2.set(Calendar.MILLISECOND, 0);
        
        Calendar data3 = Calendar.getInstance();
        data3.setTime(new Date());
        data3.set(Calendar.DAY_OF_MONTH, 1);
        data3.set(Calendar.MONTH, data3.get(Calendar.MONTH)-1);
        data3.set(Calendar.HOUR, 0);
        data3.set(Calendar.MINUTE, 0);
        data3.set(Calendar.SECOND, 0);
        data3.set(Calendar.MILLISECOND, 0);
        
        Calendar data4 = Calendar.getInstance();
        data4.setTime(new Date());
        data4.set(Calendar.DAY_OF_MONTH, 1);
        data4.set(Calendar.HOUR, 0);
        data4.set(Calendar.MINUTE, 0);
        data4.set(Calendar.SECOND, 0);
        data4.set(Calendar.MILLISECOND, 0);
        
        Map<String, Integer> aux = new HashMap<String, Integer>();
        
        for(Assunto a: assuntos) {
            aux.put(a.getAssunto(), 0);
        }
        aux = aux.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldValue, newValue) -> oldValue, LinkedHashMap::new));
        
        int mes1 = 0;
        int mes2 = 0;
        int mes3 = 0;
        
        for(Emprestimo e: emprestimos){
            Calendar emprestimo = Calendar.getInstance();
            emprestimo.setTime(e.getDataEmprestimo());
            if((emprestimo.equals(data1) || emprestimo.after(data1)) && emprestimo.before(data4)) {
                for(Assunto a: e.getIdExemplar().getIdLivro().getAssuntoList()) {
                    aux.computeIfPresent(a.getAssunto(), (k, v) -> v + 1);
                }
            }
        }
         
        List<Number> values = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: aux.entrySet()) {
            values.add(entry.getValue());
        }
        barDataSet.setData(values);
        
        int auxValue = 0;
        List<String> bgColor = new ArrayList<>();
        List<String> borderColor = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: aux.entrySet()) {
            auxValue++;
            if(auxValue % 2 == 0) {
                bgColor.add("rgba(255, 99, 132, 0.2)");
                borderColor.add("rgb(255, 99, 132)");
            } else {
                bgColor.add("rgba(255, 159, 64, 0.2)");
                borderColor.add("rgb(255, 159, 64)");
            }
        }
        barDataSet.setBackgroundColor(bgColor);
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
         
        data.addChartDataSet(barDataSet);
         
        List<String> labels = new ArrayList<>();
        for (Map.Entry<String, Integer> entry: aux.entrySet()) {
            labels.add(entry.getKey());
        }
        data.setLabels(labels);
        livrosEmprestadosCategoria.setData(data);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
         
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Gráfico de Livros Emprestados por Categoria nos 3 Últimos Meses");
        options.setTitle(title);
 
        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
 
        livrosEmprestadosCategoria.setOptions(options);
    }
    
    public void criarLivrosReservadosEmprestados() {
        livrosReservadosEmprestados = new BarChartModel();
        ChartData data = new ChartData();
         
        BarChartDataSet barDataSet = new BarChartDataSet();
        
        barDataSet.setLabel("Quantidade de Livros Reservados e Emprestados no Último Mês");
        
        Calendar data1 = Calendar.getInstance();
        data1.setTime(new Date());
        data1.set(Calendar.DAY_OF_MONTH, 1);
        data1.set(Calendar.MONTH, data1.get(Calendar.MONTH)-3);
        data1.set(Calendar.HOUR, 0);
        data1.set(Calendar.MINUTE, 0);
        data1.set(Calendar.SECOND, 0);
        data1.set(Calendar.MILLISECOND, 0);
        
        Calendar data2 = Calendar.getInstance();
        data2.setTime(new Date());
        data2.set(Calendar.DAY_OF_MONTH, 1);
        data2.set(Calendar.MONTH, data2.get(Calendar.MONTH)-2);
        data2.set(Calendar.HOUR, 0);
        data2.set(Calendar.MINUTE, 0);
        data2.set(Calendar.SECOND, 0);
        data2.set(Calendar.MILLISECOND, 0);
        
        Calendar data3 = Calendar.getInstance();
        data3.setTime(new Date());
        data3.set(Calendar.DAY_OF_MONTH, 1);
        data3.set(Calendar.MONTH, data3.get(Calendar.MONTH)-1);
        data3.set(Calendar.HOUR, 0);
        data3.set(Calendar.MINUTE, 0);
        data3.set(Calendar.SECOND, 0);
        data3.set(Calendar.MILLISECOND, 0);
        
        Calendar data4 = Calendar.getInstance();
        data4.setTime(new Date());
        data4.set(Calendar.DAY_OF_MONTH, 1);
        data4.set(Calendar.HOUR, 0);
        data4.set(Calendar.MINUTE, 0);
        data4.set(Calendar.SECOND, 0);
        data4.set(Calendar.MILLISECOND, 0);
        
        int emp = 0;
        int res = 0;
        
        for(Reserva r: reservas){
            Calendar reserva = Calendar.getInstance();
            reserva.setTime(r.getDataReserva());
            if((reserva.equals(data3) || reserva.after(data3)) && reserva.before(data4)) {
                res++;
            }
        }
        
        for(Emprestimo e: emprestimos){
            Calendar emprestimo = Calendar.getInstance();
            emprestimo.setTime(e.getDataEmprestimo());
            if((emprestimo.equals(data3) || emprestimo.after(data3)) && emprestimo.before(data4)) {
                emp++;
            }
        }
         
        List<Number> values = new ArrayList<>();
        values.add(res);
        values.add(emp);
        barDataSet.setData(values);
         
        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgba(255, 99, 132, 0.2)");
        bgColor.add("rgba(255, 159, 64, 0.2)");
        barDataSet.setBackgroundColor(bgColor);
         
        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(255, 99, 132)");
        borderColor.add("rgb(255, 159, 64)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);
         
        data.addChartDataSet(barDataSet);
         
        List<String> labels = new ArrayList<>();
        labels.add("Reservados");
        labels.add("Emprestados");
        data.setLabels(labels);
        livrosReservadosEmprestados.setData(data);
         
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
         
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Gráfico de Livros Reservados e Empresrados no Último Mês");
        options.setTitle(title);
 
        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);
 
        livrosReservadosEmprestados.setOptions(options);
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public BarChartModel getLivrosReservados() {
        return livrosReservados;
    }

    public void setLivrosReservados(BarChartModel livrosReservados) {
        this.livrosReservados = livrosReservados;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }

    public BarChartModel getLivrosEmprestados() {
        return livrosEmprestados;
    }

    public void setLivrosEmprestados(BarChartModel livrosEmprestados) {
        this.livrosEmprestados = livrosEmprestados;
    }

    public List<Assunto> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List<Assunto> assuntos) {
        this.assuntos = assuntos;
    }

    public BarChartModel getLivrosReservadosCategoria() {
        return livrosReservadosCategoria;
    }

    public void setLivrosReservadosCategoria(BarChartModel livrosReservadosCategoria) {
        this.livrosReservadosCategoria = livrosReservadosCategoria;
    }

    public BarChartModel getLivrosEmprestadosCategoria() {
        return livrosEmprestadosCategoria;
    }

    public void setLivrosEmprestadosCategoria(BarChartModel livrosEmprestadosCategoria) {
        this.livrosEmprestadosCategoria = livrosEmprestadosCategoria;
    }

    public BarChartModel getLivrosReservadosEmprestados() {
        return livrosReservadosEmprestados;
    }

    public void setLivrosReservadosEmprestados(BarChartModel livrosReservadosEmprestados) {
        this.livrosReservadosEmprestados = livrosReservadosEmprestados;
    }
    
    public String mes(int m) {
        switch(m) {
            case 0:
                return "Janeiro";
            case 1:
                return "Fevereiro";
            case 2:
                return "Março";
            case 3:
                return "Abril";
            case 4:
                return "Maio";
            case 5:
                return "Junho";
            case 6:
                return "Julho";
            case 7: 
                return "Agosto";
            case 8:
                return "Setembro";
            case 9:
                return "Outubro";
            case 10:
                return "Novembro";
            case 11:
                return "Dezembro";
            default:
                return "";
        }
    }
    
}