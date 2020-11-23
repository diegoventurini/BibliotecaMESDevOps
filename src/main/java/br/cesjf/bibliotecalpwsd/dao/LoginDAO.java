/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.dao;

import br.cesjf.bibliotecalpwsd.model.Usuario;
import br.cesjf.bibliotecalpwsd.util.PersistenceUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dmeireles
 */
public class LoginDAO implements Serializable{

    public static LoginDAO loginDAO;

    public static LoginDAO getInstance() {
        if (loginDAO == null) {
            loginDAO = new LoginDAO();
        }
        return loginDAO;
    }
    
    public static Boolean login(String usuario, String senha) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usuario AND u.senha = :senha");
        query.setParameter("usuario", usuario);
        query.setParameter("senha", senha);
        List<Usuario> usuarios = query.getResultList();
        if (usuarios != null && usuarios.size() > 0) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Login efetuado com sucesso!");
            return true;
        }
        Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Usuário ou senha inválidos!");
        return false;
    }
    
}