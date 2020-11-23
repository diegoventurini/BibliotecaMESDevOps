/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.dao;

import br.cesjf.bibliotecalpwsd.model.Editora;
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
public class EditoraDAO implements Serializable {
    
    public static EditoraDAO editoraDAO;

    public static EditoraDAO getInstance() {
        if (editoraDAO == null) {
            editoraDAO = new EditoraDAO();
        }
        return editoraDAO;
    }
    
    public Editora buscar(int id) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT e FROM Editora e WHERE e.id = :id");
            query.setParameter("id", id);
            Editora editora = (Editora) query.getSingleResult();
            if(editora != null && editora.getId() > 0) {
                return editora;
            } else {
                Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontradas editoras!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontradas editoras!", e.getMessage());
            return null;
        }
    }
    
    public Editora buscar(Editora ed){
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT e FROM Editora e WHERE e.id = :id");
            query.setParameter("id", ed.getId());
            Editora editora = (Editora) query.getSingleResult();
            if(editora != null && editora.getId() > 0) {
                return editora;
            } else {
                Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Não foram encontradas editoras!");
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontradas editoras!", e.getMessage());
            return null;
        }
    }

    public List<Editora> buscarTodas() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            Query query = em.createQuery("SELECT e FROM Editora e");
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foram encontradas editoras!", e.getMessage());
            return new ArrayList<>();
        }
    }
    
    public String remover(Editora editora) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            editora = em.merge(editora);
            em.remove(editora);
            em.getTransaction().commit();
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Editora removida com sucesso!");
            return "Editora " + editora.getNome() + " removida com sucesso!";
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível remover a editora!", e.getMessage());
            return "Não foi possível remover a editora " + editora.getNome() + "!";
        }
    }

    public String persistir(Editora editora) {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            editora = em.merge(editora);
            em.getTransaction().commit();
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Editora salva com sucesso!");
            return "Editora " + editora.getNome() + " salva com sucesso!";
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível salvar oaeditora!", e.getMessage());
            if(e.getMessage().contains("ConstraintViolationException")){
                return "Não foi possível salvar a editora " + editora.getNome() + ", pois o nome deve ser único";
            }
            return "Não foi possível salvar a editora " + editora.getNome() + "!";
        }
    }

    public String removeAll() {
        try {
            EntityManager em = PersistenceUtil.getEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Editora");
            query.executeUpdate();
            em.getTransaction().commit();
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.INFO, "Todas as editoras foram deletadas!");
            return "Todos os editoras foram deletados!";
        } catch (Exception e) {
            Logger.getLogger (PersistenceUtil.class.getName()).log(Level.WARNING, "Não foi possível deletar todas as editoras!", e.getMessage());
            return "Não foi possível deletar todos os editoras!";
        }
    }
    
}
