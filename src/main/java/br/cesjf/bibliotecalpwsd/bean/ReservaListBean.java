/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.ReservaDAO;
import br.cesjf.bibliotecalpwsd.model.Emprestimo;
import br.cesjf.bibliotecalpwsd.model.Reserva;
import br.cesjf.bibliotecalpwsd.util.ProcessReport;
import com.github.adminfaces.template.exception.BusinessException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class ReservaListBean extends ProcessReport implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Reserva reserva;
    private List<Reserva> reservas;
    private List reservasSelecionados;
    private List reservasFiltrados;
    private Integer id;

    //construtor
    public ReservaListBean() {
        reservas = new ReservaDAO().buscarTodas();
        reserva = new Reserva();
        Calendar c = Calendar.getInstance();
        for(Reserva r: reservas) {
            if(!r.getCancelada()) {
                if(r.getIdEmprestimo() == null) {
                    c.setTime(r.getDataReserva());
                    c.add(Calendar.DAY_OF_MONTH, 10);
                    if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                        c.add(Calendar.DAY_OF_MONTH, 2);
                    } else if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                        c.add(Calendar.DAY_OF_MONTH, 1);
                    }
                    if(c.getTime().compareTo(new Date()) < 0) {
                        r.setCancelada(Boolean.TRUE);
                        r.setObsCancelamento("Sistema");
                        new ReservaDAO().persistir(r);
                    }
                }
            }
        }
        reservas = new ReservaDAO().buscarTodas();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        msgScreen(new ReservaDAO().persistir(reserva));
        reservas = new ReservaDAO().buscarTodas();
    }

    public void exclude(ActionEvent actionEvent) {
        for (Object a: reservasSelecionados){
            msgScreen(new ReservaDAO().remover((Reserva) a));
        }
        reservas = new ReservaDAO().buscarTodas();
    }
    
    public void novo(ActionEvent actionEvent) {
        reserva = new Reserva();
    }
    
    public void buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("Insira um ID");
        }
        reservasSelecionados.add(new ReservaDAO().buscar(id));
    }

    //getters and setters
    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public List getReservas() {
        return reservas;
    }

    public void setReservas(List reservas) {
        this.reservas = reservas;
    }

    public List getReservasSelecionados() {
        return reservasSelecionados;
    }

    public void setReservasSelecionados(List reservasSelecionados) {
        this.reservasSelecionados = reservasSelecionados;
    }

    public List getReservasFiltrados() {
        return reservasFiltrados;
    }

    public void setReservasFiltrados(List reservasFiltrados) {
        this.reservasFiltrados = reservasFiltrados;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public Boolean getData(Date reserva) {
        if(reserva.compareTo(new Date()) <= 0) {
            return true;
        }
        return false;
    }
    
    public void geraEmprestimo(ActionEvent actionEvent) {
        Emprestimo emp = new Emprestimo();
        emp.setIdExemplar(reserva.getIdExemplar());
        emp.setIdUsuario(reserva.getIdUsuario());
        emp.setDataEmprestimo(new Date());
        emp.calculaDevolucaoPrevista();
        reserva.setIdEmprestimo(emp);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", new ReservaDAO().persistir(reserva)));
        reservas = new ReservaDAO().buscarTodas();
    }
    
    public void efetuaCancelamento() {
        reserva.setCancelada(Boolean.TRUE);
        reserva.setObsCancelamento("Usuário");
        new ReservaDAO().persistir(reserva);
        reservas = new ReservaDAO().buscarTodas();
        msgScreen("Reserva cancelada com sucesso!");
    }
    
    public void msgScreen(String msg) {
        if(msg.contains("Não")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }
    
}