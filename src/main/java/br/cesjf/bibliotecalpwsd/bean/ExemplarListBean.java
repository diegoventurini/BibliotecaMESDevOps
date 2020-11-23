/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.ExemplarDAO;
import br.cesjf.bibliotecalpwsd.model.Exemplar;
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
public class ExemplarListBean extends ProcessReport implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Exemplar exemplar;
    private List exemplares;
    private List exemplaresSelecionados;
    private List exemplaresFiltrados;
    private Integer id;

    //construtor
    public ExemplarListBean() {
        exemplares = new ExemplarDAO().buscarTodas();
        exemplar = new Exemplar();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        msgScreen(new ExemplarDAO().persistir(exemplar));
        exemplares = new ExemplarDAO().buscarTodas();
    }

    public void exclude(ActionEvent actionEvent) {
        for (Object a: exemplaresSelecionados){
            msgScreen(new ExemplarDAO().remover((Exemplar) a));
        }
        exemplares = new ExemplarDAO().buscarTodas();
    }
    
    public void novo(ActionEvent actionEvent) {
        exemplar = new Exemplar();
    }
    
    public void buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("Insira um ID");
        }
        exemplaresSelecionados.add(new ExemplarDAO().buscar(id));
    }

    //getters and setters
    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public List getExemplares() {
        return exemplares;
    }

    public void setExemplares(List exemplares) {
        this.exemplares = exemplares;
    }

    public List getExemplaresSelecionados() {
        return exemplaresSelecionados;
    }

    public void setExemplaresSelecionados(List exemplaresSelecionados) {
        this.exemplaresSelecionados = exemplaresSelecionados;
    }

    public List getExemplaresFiltrados() {
        return exemplaresFiltrados;
    }

    public void setExemplaresFiltrados(List exemplaresFiltrados) {
        this.exemplaresFiltrados = exemplaresFiltrados;
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