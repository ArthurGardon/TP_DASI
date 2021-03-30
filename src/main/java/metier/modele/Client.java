package metier.modele;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;


/**
 *
 * @author Arthur
 */

@Entity
public class Client extends User implements Serializable {

    //SimpleDateFormat?
    private Date birthDate;
    
    public Client(String nom, String prenom, String mail, String motDePasse, String phoneNumber, Date birthDate) {
        super(nom, prenom, mail, motDePasse, phoneNumber);
        this.birthDate = birthDate;
    }
    
    public Client()
    {}

    @Override
    public String toString() {
        return super.toString() + ", Client{" + "birthDate=" + birthDate + '}';
    }
    
    

}
