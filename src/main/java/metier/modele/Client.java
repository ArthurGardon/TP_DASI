package metier.modele;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import util.AstroNet;


/**
 *
 * @author Arthur
 */

@Entity
public class Client extends User implements Serializable {

    //SimpleDateFormat?
    private Date birthDate;
    private String adressePostale;
    @OneToOne(cascade = CascadeType.PERSIST)
    private ProfilAstral profilA;
    
    public Client(String nom, String prenom, String mail, String motDePasse, String phoneNumber, Date birthDate, String adressePostale) {
        super(nom, prenom, mail, motDePasse, phoneNumber);
        this.birthDate = birthDate;
        this.adressePostale = adressePostale;     
    }
    
    public Client()
    {}

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAdressePostale() {
        return adressePostale;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }

    public ProfilAstral getProfilA() {
        return profilA;
    }

    public void setProfilA(ProfilAstral profilA) {
        this.profilA = profilA;
    }

    @Override
    public String toString() {
        return super.toString() + ", Client{" + "birthDate=" + birthDate + '}' + profilA.toString();
    }
    
    

}
