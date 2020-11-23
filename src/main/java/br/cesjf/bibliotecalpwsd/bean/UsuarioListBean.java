/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.UsuarioDAO;
import br.cesjf.bibliotecalpwsd.model.Usuario;
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
public class UsuarioListBean extends ProcessReport implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Usuario usuario;
    private List usuarios;
    private List usuariosSelecionados;
    private List usuariosFiltrados;
    private Integer id;

    //construtor
    public UsuarioListBean() {
        usuarios = new UsuarioDAO().buscarTodas();
        usuario = new Usuario();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        msgScreen(new UsuarioDAO().persistir(usuario));
        usuarios = new UsuarioDAO().buscarTodas();
    }

    public void exclude(ActionEvent actionEvent) {
        for (Object a: usuariosSelecionados){
            msgScreen(new UsuarioDAO().remover((Usuario) a));
        }
        usuarios = new UsuarioDAO().buscarTodas();
    }
    
    public void novo(ActionEvent actionEvent) {
        usuario = new Usuario();
    }
    
    public void buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("Insira um ID");
        }
        usuariosSelecionados.add(new UsuarioDAO().buscar(id));
    }

    //getters and setters
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List usuarios) {
        this.usuarios = usuarios;
    }

    public List getUsuariosSelecionados() {
        return usuariosSelecionados;
    }

    public void setUsuariosSelecionados(List usuariosSelecionados) {
        this.usuariosSelecionados = usuariosSelecionados;
    }

    public List getUsuariosFiltrados() {
        return usuariosFiltrados;
    }

    public void setUsuariosFiltrados(List usuariosFiltrados) {
        this.usuariosFiltrados = usuariosFiltrados;
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