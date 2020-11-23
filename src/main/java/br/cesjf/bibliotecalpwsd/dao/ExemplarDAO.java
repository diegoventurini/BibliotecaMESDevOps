/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.dao;

import br.cesjf.bibliotecalpwsd.model.Exemplar;
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
public class ExemplarDAO implements Serializable {
    
    public static ExemplarDAO exemplarDAO;

    public static ExemplarDAO getInstance() {
        if (exemplarDAO == null) {
            exemplarDAO = new ExemplarDAO();
        }
        return exemplarDAO;
    }
    
    public Exemplar buscar(int id) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT e FROM Exemplar e WHERE e.id = :id");
            query.setParameter("id", id);
            Exemplar exemplar = (Exemplar) query.getSingleResult();
            if(exemplar != null && exemplar.getId() > 0) {
                return exemplar;
            } else {
                Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados exemplares!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados exemplares!", e.getMessage());
            return null;
        }
    }
    
    public Exemplar buscar(Exemplar ed){
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT e FROM Exemplar e WHERE e.id = :id");
            query.setParameter("id", ed.getId());
            Exemplar exemplar = (Exemplar) query.getSingleResult();
            if(exemplar != null && exemplar.getId() > 0) {
                return exemplar;
            } else {
                Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontrados exemplares!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados exemplares!", e.getMessage());
            return null;
        }
    }

    public List<Exemplar> buscarTodas() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT e FROM Exemplar e");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontrados exemplares!", e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public String remover(Exemplar exemplar) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            exemplar = em.merge(exemplar);
            em.remove(exemplar);
            em.getTransaction().commit();
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Exemplar removido com sucesso!");
            return "Exemplar " + exemplar.getId() + " removido com sucesso!";
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível remover o exemplar!", e.getMessage());
            return "Não foi possível remover o exemplar " + exemplar.getId() + "!";
        }
    }

    public String persistir(Exemplar exemplar) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            exemplar = em.merge(exemplar);
            em.getTransaction().commit();
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Exemplar salvo com sucesso!");
            return "Exemplar " + exemplar.getId() + " salvo com sucesso!";
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível salvar o exemplar!", e.getMessage());
            return "Não foi possível salvar o exemplar " + exemplar.getId() + "!";
        }
    }

    public String removeAll() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Exemplar");
            query.executeUpdate();
            em.getTransaction().commit();
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Todos os exemplares foram deletados!");
            return "Todos os exemplares foram deletados!";
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível deletar todos os exemplares!", e.getMessage());
            return "Não foi possível deletar todos os exemplares!";
        }
    }
    
}
