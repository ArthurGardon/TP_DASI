/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Arthur
 */
@Entity
public class Consultation implements Serializable {
    
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date dateDebut;
    @Temporal(javax.persistence.TemporalType.TIME)
    private Date dateFin;
    private String commentaire;
    @ManyToOne
    private Employe employe;
    @ManyToOne
    private Medium medium;
    @ManyToOne
    private Client client;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Consultation()
    {
    }
    
    public Consultation(Date dateDebut, Date dateFin, String commentaire, Employe employe, Medium medium, Client client) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.commentaire = commentaire;
        this.employe = employe;
        this.medium = medium;
        this.client = client;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Consultation{" + "dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", commentaire=" + commentaire + ", employe=" + employe + ", medium=" + medium + ", client=" + client + ", id=" + id + '}';
    }
    
    
    
}
