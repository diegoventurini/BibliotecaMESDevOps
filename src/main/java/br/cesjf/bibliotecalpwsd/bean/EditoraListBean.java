/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.EditoraDAO;
import br.cesjf.bibliotecalpwsd.model.Editora;
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
public class EditoraListBean extends ProcessReport implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Editora editora;
    private List editoras;
    private List editorasSelecionados;
    private List editorasFiltrados;
    private Integer id;

    //construtor
    public EditoraListBean() {
        editoras = new EditoraDAO().buscarTodas();
        editora = new Editora();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        msgScreen(new EditoraDAO().persistir(editora));
        editoras = new EditoraDAO().buscarTodas();
    }

    public void exclude(ActionEvent actionEvent) {
        for (Object a: editorasSelecionados){
            msgScreen(new EditoraDAO().remover((Editora) a));
        }
        editoras = new EditoraDAO().buscarTodas();
    }
    
    public void novo(ActionEvent actionEvent) {
        editora = new Editora();
    }
    
    public void buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("Insira um ID");
        }
        editorasSelecionados.add(new EditoraDAO().buscar(id));
    }

    //getters and setters
    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public List getEditoras() {
        return editoras;
    }

    public void setEditoras(List editoras) {
        this.editoras = editoras;
    }

    public List getEditorasSelecionados() {
        return editorasSelecionados;
    }

    public void setEditorasSelecionados(List editorasSelecionados) {
        this.editorasSelecionados = editorasSelecionados;
    }

    public List getEditorasFiltrados() {
        return editorasFiltrados;
    }

    public void setEditorasFiltrados(List editorasFiltrados) {
        this.editorasFiltrados = editorasFiltrados;
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