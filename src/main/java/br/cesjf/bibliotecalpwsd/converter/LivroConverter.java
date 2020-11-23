package br.cesjf.bibliotecalpwsd.converter;

import br.cesjf.bibliotecalpwsd.dao.LivroDAO;
import br.cesjf.bibliotecalpwsd.model.Livro;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Livro.class, value = "livroConverter")
public class LivroConverter implements Converter, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
        if (id != null && !id.isEmpty()) {
            return (Livro) new LivroDAO().buscar(Integer.valueOf(id));
        }
        return id;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
        if (objeto != null) {
            Livro livro = (Livro) objeto;
            return livro.getId() != null && livro.getId() > 0 ? livro.getId().toString() : null;
        }
        return null;
    }
}