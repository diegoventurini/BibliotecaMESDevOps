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

@Named
@ViewScoped
public class AutorListBean extends CommandListBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Autor autor;
    private List autores;
    private List autoresSelecionados;
    private List autoresFiltrados;

    @Override
    public String toString() {
        return "AutorListBean{" + "autor=" + autor + ", autores=" + autores + ", autoresSelecionados=" + autoresSelecionados + ", autoresFiltrados=" + autoresFiltrados + '}';
    }
   
    //construtor
    public AutorListBean() {
        autores = new AutorDAO().buscarTodas();
        autor = new Autor();
    }
    
    @Override
    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        msgScreen(new AutorDAO().persistir(autor));
        autores = new AutorDAO().buscarTodas();
    }
    @Override
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

    
    public void msgScreen(String msg) {
        if(msg.contains("Não")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }
    
}