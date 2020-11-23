/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.LivroDAO;
import br.cesjf.bibliotecalpwsd.model.Livro;
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
public class LivroListBean extends ProcessReport implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Livro livro;
    private List livros;
    private List livrosSelecionados;
    private List livrosFiltrados;
    private Integer id;

    //construtor
    public LivroListBean() {
        livros = new LivroDAO().buscarTodas();
        livro = new Livro();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        msgScreen(new LivroDAO().persistir(livro));
        livros = new LivroDAO().buscarTodas();
    }

    public void exclude(ActionEvent actionEvent) {
        for (Object a: livrosSelecionados){
            msgScreen(new LivroDAO().remover((Livro) a));
        }
        livros = new LivroDAO().buscarTodas();
    }
    
    public void novo(ActionEvent actionEvent) {
        livro = new Livro();
    }
    
    public void buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("Insira um ID");
        }
        livrosSelecionados.add(new LivroDAO().buscar(id));
    }

    //getters and setters
    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public List getLivros() {
        return livros;
    }

    public void setLivros(List livros) {
        this.livros = livros;
    }

    public List getLivrosSelecionados() {
        return livrosSelecionados;
    }

    public void setLivrosSelecionados(List livrosSelecionados) {
        this.livrosSelecionados = livrosSelecionados;
    }

    public List getLivrosFiltrados() {
        return livrosFiltrados;
    }

    public void setLivrosFiltrados(List livrosFiltrados) {
        this.livrosFiltrados = livrosFiltrados;
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