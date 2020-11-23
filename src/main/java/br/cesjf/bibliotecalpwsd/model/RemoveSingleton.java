/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.model;
import sun.security.jca.GetInstance;

/**
 *
 * @author diego
 */
public class RemoveSingleton {
    
    private static RemoveSingleton remover;
    
    public static RemoveSingleton GetInstance() {
    
        if(remover == null) {
            remover = new RemoveSingleton();
        }
        
        return remover;
    };
    
    public void removedBook() {
        
       System.out.println("Livro removido com sucesso.");
    
    };
}


