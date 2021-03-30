/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.*;

/**
 *
 * @author Arthur
 */
@Entity
public class Employe extends User{
    boolean isAvailable = false;
    String genre;
    int nbConsultations = 0;

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getNbConsultations() {
        return nbConsultations;
    }

    public void setNbConsultations(int nbConsultations) {
        this.nbConsultations = nbConsultations;
    }

    public Employe(String nom, String prenom, String mail, String motDePasse, String phoneNumber, String genre) {
        super(nom, prenom, mail, motDePasse, phoneNumber);
        this.genre = genre;
    }

    public Employe() {
        
    }
}
