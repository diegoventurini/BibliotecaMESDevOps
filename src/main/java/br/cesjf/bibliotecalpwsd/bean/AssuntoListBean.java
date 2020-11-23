/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.AssuntoDAO;
import br.cesjf.bibliotecalpwsd.model.Assunto;
import br.cesjf.bibliotecalpwsd.util.ProcessReport;
import com.github.adminfaces.template.exception.BusinessException;
import java.io.Serializable;
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
public class AssuntoListBean extends ProcessReport implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Assunto assunto;
    private List assuntos;
    private List assuntosSelecionados;
    private List assuntosFiltrados;
    private Integer id;

    //construtor
    public AssuntoListBean() {
        assuntos = new AssuntoDAO().buscarTodas();
        assunto = new Assunto();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        msgScreen(new AssuntoDAO().persistir(assunto));
        assuntos = new AssuntoDAO().buscarTodas();
    }

    public void exclude(ActionEvent actionEvent) {
        for (Object a: assuntosSelecionados){
            msgScreen(new AssuntoDAO().remover((Assunto) a));
        }
        assuntos = new AssuntoDAO().buscarTodas();
    }
    
    public void novo(ActionEvent actionEvent) {
        assunto = new Assunto();
    }
    
    public void buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("Insira um ID");
        }
        assuntosSelecionados.add(new AssuntoDAO().buscar(id));
    }

    //getters and setters
    public Assunto getAssunto() {
        return assunto;
    }

    public void setAssunto(Assunto assunto) {
        this.assunto = assunto;
    }

    public List getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List assuntos) {
        this.assuntos = assuntos;
    }

    public List getAssuntosSelecionados() {
        return assuntosSelecionados;
    }

    public void setAssuntosSelecionados(List assuntosSelecionados) {
        this.assuntosSelecionados = assuntosSelecionados;
    }

    public List getAssuntosFiltrados() {
        return assuntosFiltrados;
    }

    public void setAssuntosFiltrados(List assuntosFiltrados) {
        this.assuntosFiltrados = assuntosFiltrados;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public void msgScreen(String msg) {
        if(msg.contains("Não")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }
    
}