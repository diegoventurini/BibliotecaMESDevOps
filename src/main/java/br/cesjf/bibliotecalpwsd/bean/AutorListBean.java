/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.AutorDAO;
import br.cesjf.bibliotecalpwsd.model.Autor;
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
public class AutorListBean extends ProcessReport implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Autor autor;
    private List autores;
    private List autoresSelecionados;
    private List autoresFiltrados;
    private Integer id;

    //construtor
    public AutorListBean() {
        autores = new AutorDAO().buscarTodas();
        autor = new Autor();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        msgScreen(new AutorDAO().persistir(autor));
        autores = new AutorDAO().buscarTodas();
    }

    public void exclude(ActionEvent actionEvent) {
        for (Object a: autoresSelecionados){
            msgScreen(new AutorDAO().remover((Autor) a));
        }
        autores = new AutorDAO().buscarTodas();
    }
    
    public void novo(ActionEvent actionEvent) {
        autor = new Autor();
    }
    
    public void buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("Insira um ID");
        }
        autoresSelecionados.add(new AutorDAO().buscar(id));
    }

    //getters and setters
    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List getAutores() {
        return autores;
    }

    public void setAutores(List autores) {
        this.autores = autores;
    }

    public List getAutoresSelecionados() {
        return autoresSelecionados;
    }

    public void setAutoresSelecionados(List autoresSelecionados) {
        this.autoresSelecionados = autoresSelecionados;
    }

    public List getAutoresFiltrados() {
        return autoresFiltrados;
    }

    public void setAutoresFiltrados(List autoresFiltrados) {
        this.autoresFiltrados = autoresFiltrados;
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