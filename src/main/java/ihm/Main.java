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
import metier.service.ClientService;
import metier.service.EmployeService;


/**
 *
 * @author Arthur
 */
class Main {
    public static void main(String[] args)
    {
        JpaUtil.init();
        testerInscription();
        JpaUtil.destroy();
    }
    
     static void testerAuth()
    {
        ClientService service = new ClientService();
        System.out.println(service.authentifierClient("a@g.com", "123"));
    }
    
    static void testerListe()
    {
        ClientService service = new ClientService();
        System.out.println(service.listerClients());
    }

    
    static void testerInscription()
    {
        ClientService serviceC = new ClientService();
        EmployeService serviceE = new EmployeService();
        Client a = new Client("G", "G", "a@g.com", "123", "0707070707", new Date(10, 10, 2010));
        Client b = new Client("B", "G", "b@g.com", "123", "0808080808", new Date(01,01,1998));
        Employe c = new Employe("R", "T", "r@t.com", "234", "050505203", "M");
        serviceC.inscrireClient(b);
        serviceC.inscrireClient(a);
        serviceE.ajouterEmploye(c);
    }
    
    static void testerRechercheClient()
    {
        ClientService service = new ClientService();
        
        Client a = service.trouverClient(1L);
        System.out.println(a);
    }
    
}
