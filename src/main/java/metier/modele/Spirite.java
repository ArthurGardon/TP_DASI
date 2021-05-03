/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Entity;

/**
 *
 * @author Arthur
 */
@Entity
public class Spirite extends Medium {
    private String support;

    public Spirite() {
    }

    public Spirite(String denomination, String genre, String presentation, String support) {
        super(denomination, genre, presentation);
        this.support = support;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    @Override
    public String toString() {
        return "Spirite{" + "support=" + support + '}' + super.toString();
    }    
}
