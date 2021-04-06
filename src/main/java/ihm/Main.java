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
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        init();
        testerAuth();
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

    
    static void init()
    {
        Service service = new Service();
        try
        {
        service.initBD();
        }
        catch(ParseException e){
            Logger.getAnonymousLogger().log(Level.INFO, "Erreur parse date");
        }
    }
    
    static void testerRechercheClient()
    {
        Service service = new Service();
        
        Client a = service.trouverClient(1L);
        System.out.println(a);
    }
    
}
