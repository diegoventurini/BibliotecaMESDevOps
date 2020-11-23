/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.dao;

import br.cesjf.bibliotecalpwsd.model.Usuario;
import br.cesjf.bibliotecalpwsd.util.PersistenceUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author dmeireles
 */
public class UsuarioDAO implements Serializable {
    
    public static UsuarioDAO usuarioDAO;

    public static UsuarioDAO getInstance() {
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO();
        }
        return usuarioDAO;
    }
    
    public Usuario buscar(int id) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.id = :id");
            query.setParameter("id", id);
            Usuario usuario = (Usuario) query.getSingleResult();
            if(usuario != null && usuario.getId() > 0) {
                return usuario;
            } else {
                Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados usuarios!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados usuarios!", e.getMessage());
            return null;
        }
    }
    
    public Usuario buscar(Usuario u){
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.id = :id");
            query.setParameter("id", u.getId());
            Usuario usuario = (Usuario) query.getSingleResult();
            if(usuario != null && usuario.getId() > 0) {
                return usuario;
            } else {
                Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados usuarios!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados usuarios!", e.getMessage());
            return null;
        }
    }
    
    public Usuario buscar(String u){
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario = :usuario");
            query.setParameter("usuario", u);
            Usuario usuario = (Usuario) query.getSingleResult();
            if(usuario != null && usuario.getId() > 0) {
                return usuario;
            } else {
                Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados usuarios!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados usuarios!", e.getMessage());
            return null;
        }
    }

    public List<Usuario> buscarTodas() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT u FROM Usuario u");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados usuarios!", e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public String remover(Usuario usuario) {
        try {
            System.err.println("oi" + usuario.getId());
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            usuario = em.merge(usuario);
            em.remove(usuario);
            em.getTransaction().commit();
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Usuario removido com sucesso!");
            return "Usuario " + usuario.getNome() + " removido com sucesso!";
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível remover o usuario!", e.getMessage());
            return "Não foi possível remover o usuario " + usuario.getNome() + ", pois ele possui reservas ou empréstimos vinculados";
        }
    }

    public String persistir(Usuario usuario) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            usuario = em.merge(usuario);
            em.getTransaction().commit();
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Usuario salvo com sucesso!");
            return "Usuario " + usuario.getNome() + " salvo com sucesso!";
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível salvar o usuario!", e.getMessage());
            if(e.getMessage().contains("ConstraintViolationException")){
                return "Não foi possível salvar o usuário " + usuario.getNome() + ", pois o usuário deve ser único";
            }
            return "Não foi possível salvar o usuário " + usuario.getNome() + "!";
        }
    }

    public String removeAll() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Usuario");
            query.executeUpdate();
            em.getTransaction().commit();
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Todos os usuarios foram deletados!");
            return "Todos os usuarios foram deletados!";
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível deletar todos os usuarios!", e.getMessage());
            return "Não foi possível deletar todos os usuarios!";
        }
    }
    
}
