package br.cesjf.bibliotecalpwsd.converter;

import br.cesjf.bibliotecalpwsd.dao.UsuarioDAO;
import br.cesjf.bibliotecalpwsd.model.Usuario;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Usuario.class, value = "usuarioConverter")
public class UsuarioConverter implements Converter, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String id) {
        if (id != null && !id.isEmpty()) {
            return (Usuario) new UsuarioDAO().buscar(Integer.valueOf(id));
        }
        return id;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object objeto) {
        if (objeto != null) {
            Usuario usuario = (Usuario) objeto;
            return usuario.getId() != null && usuario.getId() > 0 ? usuario.getId().toString() : null;
        }
        return null;
    }
}