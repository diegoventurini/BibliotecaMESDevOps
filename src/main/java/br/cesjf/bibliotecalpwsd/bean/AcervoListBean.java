/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.LivroDAO;
import br.cesjf.bibliotecalpwsd.model.Livro;
import com.github.adminfaces.template.exception.BusinessException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class AcervoListBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private List<Livro> livros;
    private Integer id;
    private String titulo;

    //construtor
    public AcervoListBean() {
        livros = new LivroDAO().buscarTodas();
    }

    //getters and setters
    public List getLivros() {
        return livros;
    }

    public void setLivros(List livros) {
        this.livros = livros;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public void buscarPorId(Integer id) {
        if (id == null) {
            throw new BusinessException("Insira um ID");
        }
        livros = new ArrayList<>();
        Livro l = new LivroDAO().buscar(id);
        if(l != null){
            livros.add(l);
        }
        if(livros.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Não foram encontrados livros"));
            livros = new LivroDAO().buscarTodas();
        }
        this.id = null;
        this.titulo = null;
    }
    
    public void buscarPorTitulo(String titulo) {
        if (titulo.equals("") || titulo == null) {
            throw new BusinessException("Insira um Título");
        }
        livros = new LivroDAO().buscar(titulo);
        
        if(livros.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Não foram encontrados livros"));
            livros = new LivroDAO().buscarTodas();
        }
        this.id = null;
        this.titulo = null;
    }
    
    public void limpar() {
        livros = new LivroDAO().buscar(titulo);
        this.id = null;
        this.titulo = null;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    
    
}