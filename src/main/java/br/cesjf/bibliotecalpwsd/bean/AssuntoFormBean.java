/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.AssuntoDAO;
import br.cesjf.bibliotecalpwsd.model.Assunto;
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
public class AssuntoFormBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Assunto assunto;
    private int id;

    //construtor
    public AssuntoFormBean() {
    }
    
    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (id > 0) {
            assunto = AssuntoDAO.getInstance().buscar(id);
        } else {
            assunto = new Assunto();
        }
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        msgScreen(new AssuntoDAO().persistir(assunto));
    }
    
    public void exclude(ActionEvent actionEvent) {
        msgScreen(new AssuntoDAO().remover(assunto));
    }

    //getters and setters
    public Assunto getAssunto() {
        return assunto;
    }

    public void setAssunto(Assunto assunto) {
        this.assunto = assunto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void clear() {
        assunto = new Assunto();
    }
    
    public boolean isNew() {
        return assunto == null || assunto.getId() == null || assunto.getId() == 0;
    }
    
    public void msgScreen(String msg) {
        if(msg.contains("Não")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }

}