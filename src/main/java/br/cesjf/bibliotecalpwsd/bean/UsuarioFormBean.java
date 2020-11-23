/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.UsuarioDAO;
import br.cesjf.bibliotecalpwsd.model.Usuario;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
public class UsuarioFormBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Usuario usuario;
    private int id;
    Map<String, String> tipos;

    //construtor
    public UsuarioFormBean() {
        //1 - Aluno, 2 - Professor, 3 - Funcionário, 4 - Bibliotecário e 5 - Administrador
        tipos = new HashMap<String, String>();
        tipos.put("Aluno", "1");
        tipos.put("Professor", "2");
        tipos.put("Funcionário", "3");
        tipos.put("Bibliotecário", "4");
        tipos.put("Administrador", "5");
    }
    
    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (id > 0) {
            usuario = new UsuarioDAO().buscar(id);
        } else {
            usuario = new Usuario();
        }
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        msgScreen(new UsuarioDAO().persistir(usuario));
    }
    
    public void exclude(ActionEvent actionEvent) {
        msgScreen(new UsuarioDAO().remover(usuario));
    }

    //getters and setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void clear() {
        usuario = new Usuario();
    }
    
    public boolean isNew() {
        return usuario == null || usuario.getId() == null || usuario.getId() == 0;
    }
    
    public void msgScreen(String msg) {
        if(msg.contains("Não")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }

    public Map<String, String> getTipos() {
        return tipos;
    }

    public void setTipos(Map<String, String> tipos) {
        this.tipos = tipos;
    }

}