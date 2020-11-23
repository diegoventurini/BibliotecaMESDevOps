/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.dao;

import br.cesjf.bibliotecalpwsd.model.Emprestimo;
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
public class EmprestimoDAO implements Serializable {
    
    public static EmprestimoDAO emprestimoDAO;

    public static EmprestimoDAO getInstance() {
        if (emprestimoDAO == null) {
            emprestimoDAO = new EmprestimoDAO();
        }
        return emprestimoDAO;
    }
    
    public Emprestimo buscar(int id) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT e FROM Emprestimo e WHERE e.id = :id");
            query.setParameter("id", id);
            Emprestimo emprestimo = (Emprestimo) query.getSingleResult();
            if(emprestimo != null && emprestimo.getId() > 0) {
                return emprestimo;
            } else {
                Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados emprestimos!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados emprestimos!", e.getMessage());
            return null;
        }
    }
    
    public Emprestimo buscar(Emprestimo ed){
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT e FROM Emprestimo e WHERE e.id = :id");
            query.setParameter("id", ed.getId());
            Emprestimo emprestimo = (Emprestimo) query.getSingleResult();
            if(emprestimo != null && emprestimo.getId() > 0) {
                return emprestimo;
            } else {
                Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados emprestimos!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados emprestimos!", e.getMessage());
            return null;
        }
    }

    public List<Emprestimo> buscarTodas() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT e FROM Emprestimo e");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados emprestimos!", e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public String remover(Emprestimo emprestimo) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            emprestimo = em.merge(emprestimo);
            em.remove(emprestimo);
            em.getTransaction().commit();
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Emprestimo removido com sucesso!");
            return "Emprestimo " + emprestimo.getId() + " removido com sucesso!";
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível remover o emprestimo!", e.getMessage());
            return "Não foi possível remover o emprestimo " + emprestimo.getId() + "!";
        }
    }

    public String persistir(Emprestimo emprestimo) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            emprestimo = em.merge(emprestimo);
            em.getTransaction().commit();
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Emprestimo salvo com sucesso!");
            return "Emprestimo " + emprestimo.getId() + " salvo com sucesso!";
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível salvar o emprestimo!", e.getMessage());
            return "Não foi possível salvar o emprestimo " + emprestimo.getId() + "!";
        }
    }

    public String removeAll() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Emprestimo");
            query.executeUpdate();
            em.getTransaction().commit();
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Todos os emprestimos foram deletados!");
            return "Todos os emprestimos foram deletados!";
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível deletar todos os emprestimos!", e.getMessage());
            return "Não foi possível deletar todos os emprestimos!";
        }
    }
    
}
