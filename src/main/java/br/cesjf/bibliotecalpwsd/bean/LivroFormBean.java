/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.dao.AssuntoDAO;
import br.cesjf.bibliotecalpwsd.dao.AutorDAO;
import br.cesjf.bibliotecalpwsd.dao.EditoraDAO;
import br.cesjf.bibliotecalpwsd.dao.LivroDAO;
import br.cesjf.bibliotecalpwsd.model.Editora;
import br.cesjf.bibliotecalpwsd.model.Livro;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
import org.omnifaces.util.Faces;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class LivroFormBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Livro livro;
    private List assuntos;
    private List autores;
    private List<Editora> editoras;
    private int id;
    private UploadedFile uploadedFile;
    private final String diretorio;

    //construtor
    public LivroFormBean() {
        assuntos = new AssuntoDAO().buscarTodas();
        autores = new AutorDAO().buscarTodas();
        editoras = new EditoraDAO().buscarTodas();
        
        ExternalContext e = FacesContext.getCurrentInstance().getExternalContext();
        diretorio = e.getRealPath("resources\\arquivos");
    }
    
    public void init() {
        if(Faces.isAjaxRequest()){
           return;
        }
        if (id > 0) {
            livro = new LivroDAO().buscar(id);
        } else {
            livro = new Livro();
        }
    }

    //Métodos dos botões 
    public void record(ActionEvent actionEvent) {
        upload();
        msgScreen(new LivroDAO().persistir(livro));
    }
    
    public void exclude(ActionEvent actionEvent) {
       delete(1);
       delete(2);
       msgScreen(new LivroDAO().remover(livro));
    }

    //getters and setters
    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List getAssuntos() {
        Collections.sort(assuntos);
        return assuntos;
    }

    public void setAssuntos(List assuntos) {
        this.assuntos = assuntos;
    }

    public List getAutores() {
        Collections.sort(autores);
        return autores;
    }

    public void setAutores(List autores) {
        this.autores = autores;
    }

    public List getEditoras() {
        Collections.sort(editoras);
        return editoras;
    }

    public void setEditoras(List editoras) {
        this.editoras = editoras;
    }
    
    public String getCapa() {
        return diretorio + "\\" + livro.getArquivo();
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
        upload();
    }
    
    public void clear() {
        livro = new Livro();
    }
    
    public boolean isNew() {
        return livro == null || livro.getId() == null || livro.getId() == 0;
    }
    
    public void msgScreen(String msg) {
        if(msg.contains("Não")){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", msg));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação", msg));
        }
    }
    
    public void upload() {
        
        if(uploadedFile != null) {
            
            File dir = new File(diretorio);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            try {
                String name = new Timestamp(System.currentTimeMillis()).toString();
                name = name.replace("-", "").replace(".", "").replace(":", "").replace(" ", "");
                name = name + uploadedFile.getFileName();
                File file = new File(dir, name);
                OutputStream out = new FileOutputStream(file);
                out.write(uploadedFile.getContents());
                out.close();
                msgScreen("O arquivo " + uploadedFile.getFileName() + " foi salvo!");
                if(uploadedFile.getFileName().toUpperCase().contains(".PDF")){
                    livro.setArquivo(name);
                }else{
                    livro.setCapa(name);
                }
                uploadedFile = null;
            } catch(IOException e) {
               msgScreen("Não foi possível salvar o arquivo " + uploadedFile.getFileName() + "!" + e);
            }
        }
    }
    
    public void delete(int i) {
        
        File file;
        if(i == 1 && livro.getCapa() != null) {
            file = new File(diretorio + "\\" + livro.getCapa());
            file.delete();
        } else if(i == 2 && livro.getArquivo() != null) {
            file = new File(diretorio + "\\" + livro.getArquivo());
            file.delete();
        }
        
        msgScreen("Arquivo apagado com sucesso");
        
        if(i == 1) {
            livro.setCapa(null);
        } else {
            livro.setArquivo(null);
        }
        
        new LivroDAO().persistir(livro);
        
    }
    
}