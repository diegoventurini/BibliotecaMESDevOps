/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.converter;

import br.cesjf.bibliotecalpwsd.dao.EmprestimoDAO;
import br.cesjf.bibliotecalpwsd.model.Emprestimo;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Daniel Meireles
 */
@FacesConverter(forClass = Emprestimo.class, value = "emprestimoConverter")
public class EmprestimoConverter implements Converter, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
        if (id != null && !id.isEmpty()) {
            return (Emprestimo) new EmprestimoDAO().buscar(Integer.valueOf(id));
        }
        return id;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
        if (objeto != null) {
            Emprestimo emprestimo = (Emprestimo) objeto;
            return emprestimo.getId() != null && emprestimo.getId() > 0 ? emprestimo.getId().toString() : null;
        }
        return null;
    }
}