/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.bibliotecalpwsd.bean;

import br.cesjf.bibliotecalpwsd.util.Relatorio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.omnifaces.cdi.ViewScoped;
import javax.inject.Named;
/**
 *
 * @author dmeireles
 */
@Named
@ViewScoped
public class RelatorioBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String relatorio;
    
    private Map<String, String> tipos;
    
    private List<Date> range;

    public RelatorioBean() {
        tipos = new HashMap<String, String>();
        tipos.put("Relatório de Livros/Exemplares", "Relatorio_01");
        tipos.put("Relatório de Livros Emprestados", "Relatorio_02");
        tipos.put("Relatório de Livros Reservados", "Relatorio_03");
        tipos.put("Relatório de Usuários Cadastrados", "Relatorio_04");
        tipos.put("Relatório de Livros com Atraso na Devolução", "Relatorio_05");
        range = new ArrayList<Date>();
    }

    public String getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(String relatorio) {
        this.relatorio = relatorio;
    }

    public Map<String, String> getTipos() {
        return tipos;
    }

    public void setTipos(Map<String, String> tipos) {
        this.tipos = tipos;
    }
    
    public void gerarRelatorio() {
        Relatorio relatorio = new Relatorio();
        relatorio.getRelatorio(this.relatorio, null, null);
    }
    
    public void gerarRelatorioGerencial() {
        Relatorio relatorio = new Relatorio();
        relatorio.getRelatorio("RelatorioGerencial", range.get(0), range.get(1));
    }

    public List<Date> getRange() {
        return range;
    }

    public void setRange(List<Date> range) {
        this.range = range;
    }
    
}