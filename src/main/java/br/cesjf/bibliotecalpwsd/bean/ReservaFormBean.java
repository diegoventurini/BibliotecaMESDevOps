/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.EmprestimoDAO;
import br.cesjf.bibliotecalpwsd.dao.ExemplarDAO;
import br.cesjf.bibliotecalpwsd.dao.LivroDAO;
import br.cesjf.bibliotecalpwsd.dao.ReservaDAO;
import br.cesjf.bibliotecalpwsd.dao.UsuarioDAO;
import br.cesjf.bibliotecalpwsd.model.Emprestimo;
import br.cesjf.bibliotecalpwsd.model.Exemplar;
import br.cesjf.bibliotecalpwsd.model.Livro;
import br.cesjf.bibliotecalpwsd.model.Reserva;
import br.cesjf.bibliotecalpwsd.model.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
import org.omnifaces.util.Faces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class ReservaFormBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Reserva reserva;
    private int id;
    private List<Exemplar> exemplaresPermitidos;
    private List<Usuario> usuariosPermitidos;
    private List<Livro> livros;
    private Livro livro;
    private List<Usuario> usuarios;

    //construtor
    public ReservaFormBean() {
    }
    
    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (id > 0) {
            reserva = new ReservaDAO().buscar(id);
        } else {
            reserva = new Reserva();
        }
        livros = new LivroDAO().buscarTodas();
        usuarios = new UsuarioDAO().buscarTodas();
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        reserva.calculaDevolucaoPrevista();
        msgScreen(new ReservaDAO().persistir(reserva));
    }
    
    public void exclude(ActionEvent actionEvent) {
        msgScreen(new ReservaDAO().remover(reserva));
    }

    //getters and setters
    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void clear() {
        reserva = new Reserva();
    }
    
    public boolean isNew() {
        return reserva == null || reserva.getId() == null || reserva.getId() == 0;
    }
    
    private void usuariosPermitidos() {
        usuariosPermitidos = new ArrayList<>();
        for(Usuario u: usuarios) {
            List<Emprestimo> emp = new ArrayList<>();
            for(Emprestimo e: u.getEmprestimoList()) {
                if(e.getDataDevolucao() == null) {
                    emp.add(e);
                }
            }
            if(u.getTipoTexto().equals("Aluno") && emp.size() < 3) {
                usuariosPermitidos.add(u);
            } else if(!u.getTipoTexto().equals("Aluno") && emp.size() < 5) {
                usuariosPermitidos.add(u);
            }
        }
    }
    
    public void verificaUsuario(SelectEvent event) {
        usuariosPermitidos();
        if(!usuariosPermitidos.contains(reserva.getIdUsuario())) {
            msgScreen("Não permitido. Usuário com alguma pendência.");
        }
    }
    
    public List<Exemplar> getExemplaresPermitidos() {
        return exemplaresPermitidos;
    }

    public void setExemplaresPermitidos(List<Exemplar> exemplaresPermitidos) {
        this.exemplaresPermitidos = exemplaresPermitidos;
    }

    public List<Usuario> getUsuariosPermitidos() {
        usuariosPermitidos();
        return usuariosPermitidos;
    }

    public void setUsuariosPermitidos(List<Usuario> usuariosPermitidos) {
        this.usuariosPermitidos = usuariosPermitidos;
    }
    
    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
    public void calcularExemplaresPermitidos(SelectEvent event) {
        List<Exemplar> exemplares = new ExemplarDAO().buscarTodas();
        List<Reserva> reservas = new ReservaDAO().buscarTodas();
        exemplaresPermitidos = new ArrayList<>();
        Date dataReserva = reserva.getDataReserva();
        if(dataReserva != null){
            if(livro == null) {
                for(Exemplar e: exemplares) {
                    if(e.getCircular()) {
                        exemplaresPermitidos.add(e);
                    }
                }
            } else {
                for(Exemplar e: exemplares) {
                    if(e.getIdLivro().getId() == livro.getId() && e.getCircular()) {
                        exemplaresPermitidos.add(e);
                    }
                }
            }
            List<Exemplar> lista = new ArrayList<>();
            lista.addAll(exemplaresPermitidos);
            
            for(Exemplar e: lista) {
                for(Emprestimo emp: new EmprestimoDAO().buscarTodas()) {
                    if(emp.getIdExemplar().getId() == e.getId()) {
                        if(emp.getDataDevolucao() == null && emp.getDataEmprestimo().compareTo(dataReserva) <= 0 && emp.getDataDevolucaoPrevista().compareTo(dataReserva) >= 0) {
                            exemplaresPermitidos.remove(e);
                            continue;
                        }
                        if(emp.getDataDevolucao() != null && emp.getDataEmprestimo().compareTo(dataReserva) <= 0 && emp.getDataDevolucao().compareTo(dataReserva) >= 0) {
                            exemplaresPermitidos.remove(e);
                            continue;
                        }
                    }
                }
                for(Reserva r: new ReservaDAO().buscarTodas()) {
                    if(r.getIdExemplar().getId() == e.getId()){
                        if(!r.getCancelada() && r.getDataReserva().compareTo(dataReserva) <= 0 && r.getDataDevolucaoPrevista().compareTo(dataReserva) >= 0) {
                            exemplaresPermitidos.remove(e);
                        }
                    }
                }
            }
        }
        if(reserva.getIdExemplar() != null) {
            exemplaresPermitidos.add(reserva.getIdExemplar());
        }
    }
    
    public void msgScreen(String msg) {
        if(msg.contains("Não")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }

}