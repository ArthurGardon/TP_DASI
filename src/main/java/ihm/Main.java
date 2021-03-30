package ihm;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import metier.modele.Client;
import dao.JpaUtil;
import dao.ClientDao;
import java.util.Date;
import metier.modele.Employe;
import metier.service.Service;


/**
 *
 * @author Arthur
 */
class Main {
    public static void main(String[] args)
    {
        JpaUtil.init();
        testerInscription();
        testerListe();
        JpaUtil.destroy();
    }
    
     static void testerAuth()
    {
        Service service = new Service();
        System.out.println(service.authentifierClient("a@g.com", "123"));
    }
    
    static void testerListe()
    {
        Service service = new Service();
        System.out.println(service.listerClients());
    }

    
    static void testerInscription()
    {
        Service service = new Service();
        Client a = new Client("G", "G", "a@g.com", "123", "0707070707", new Date(10, 10, 2010), "5 rue");
        Client b = new Client("B", "G", "b@g.com", "123", "0808080808", new Date(01,01,1998),"lotr rue");
        Employe c = new Employe("R", "T", "r@t.com", "234", "050505203", "M");
        service.inscrireClient(b);
        service.inscrireClient(a);
        service.ajouterEmploye(c);
    }
    
    static void testerRechercheClient()
    {
        Service service = new Service();
        
        Client a = service.trouverClient(1L);
        System.out.println(a);
    }
    
}
