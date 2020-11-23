/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.AutorDAO;
import br.cesjf.bibliotecalpwsd.model.Autor;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
import org.omnifaces.util.Faces;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class AutorFormBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Autor autor;
    private int id;

    //construtor
    public AutorFormBean() {
    }
    
    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (id > 0) {
            autor = new AutorDAO().buscar(id);
        } else {
            autor = new Autor();
        }
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        msgScreen(new AutorDAO().persistir(autor));
    }
    
    public void exclude(ActionEvent actionEvent) {
        msgScreen(new AutorDAO().remover(autor));
    }

    //getters and setters
    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void clear() {
        autor = new Autor();
    }
    
    public boolean isNew() {
        return autor == null || autor.getId() == null || autor.getId() == 0;
    }
    
    public void msgScreen(String msg) {
        if(msg.contains("Não")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }

}