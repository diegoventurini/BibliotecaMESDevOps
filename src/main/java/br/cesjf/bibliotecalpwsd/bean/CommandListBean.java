/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import javax.faces.event.ActionEvent;

/**
 *
 * @author diego
 */
public abstract class CommandListBean {
    protected Long id;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public abstract void record(ActionEvent actionEvent);
    
    public abstract void exclude(ActionEvent actionEvent);
    
    public void addMensagem() {
        System.out.println("Livro adicionado com sucesso!");
    }
    
    public void deleteMensagem() {
        System.out.println("Livro deletado com sucesso!");
        
    }
}
