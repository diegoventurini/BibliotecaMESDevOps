/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.LoginDAO;
import br.cesjf.bibliotecalpwsd.dao.UsuarioDAO;
import br.cesjf.bibliotecalpwsd.model.Usuario;
import com.github.adminfaces.template.session.AdminSession;
import org.omnifaces.util.Faces;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import com.github.adminfaces.template.config.AdminConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
/**
 *
 * @author dmeireles
 */
@Named
@SessionScoped
@Specializes
public class LoginBean extends AdminSession implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nome;
    private String usuario;
    private String senha;
    private String tipo;
    
    @Inject
    private AdminConfig adminConfig;
    
    @Override
    public boolean isLoggedIn() {

        return nome != null;
}

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void login() throws IOException {        
        if (LoginDAO.login(usuario, senha)) {
            Usuario u = UsuarioDAO.getInstance().buscar(usuario);
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("usuario", u);
            session.setAttribute("nome", u.getUsuario());
            nome = u.getNome();
            tipo = u.getTipo();
            setIsLoggedIn(true);
            Faces.getExternalContext().getFlash().setKeepMessages(true);
            Faces.redirect(adminConfig.getIndexPage());
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuário ou senha inválido!", "Por favor tente novamente!"));
            Faces.getExternalContext().getFlash().setKeepMessages(true);
            Faces.redirect(adminConfig.getLoginPage());
        }
    }
    
    public void logout() throws IOException {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
        nome = null;
        tipo = null;
        setIsLoggedIn(false);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Logout efetuado com sucesso!", ""));
        Faces.getExternalContext().getFlash().setKeepMessages(true);
        Faces.redirect(adminConfig.getLoginPage());
    }
    
    public boolean menu(String menu) {
        //1 - Aluno, 2 - Professor, 3 - Funcionário, 4 - Bibliotecário e 5 - Administrador
        switch(menu){
            case "Usuários":
                return tipo.equals("3")
                        || tipo.equals("4")
                        || tipo.equals("5");
            case "Exemplares":
                return tipo.equals("4")
                        || tipo.equals("5");
            case "Assuntos":
                return tipo.equals("4")
                        || tipo.equals("5");
            case "Livros":
                return tipo.equals("4")
                        || tipo.equals("5");
            case "Autores":
                return tipo.equals("4")
                        || tipo.equals("5");
            case "Editoras":
                return tipo.equals("4")
                        || tipo.equals("5");
            case "Cadastros":
                return tipo.equals("3")
                        || tipo.equals("4")
                        || tipo.equals("5");
            case "Relatórios":
                return tipo.equals("5");
            case "Empréstimos":
                return tipo.equals("4")
                        || tipo.equals("5");
            case "Dashboard":
                return tipo.equals("4")
                        || tipo.equals("5");
            case "Reservas":
                return tipo.equals("4")
                        || tipo.equals("5");
            default:
                return false;
        }
    }
    
}