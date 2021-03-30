/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.ClientDao;
import dao.JpaUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Client;

/**
 *
 * @author Arthur
 */
public class ClientService {

    public Client inscrireClient(Client client) {
        ClientDao clientDao = new ClientDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            clientDao.creer(client);
            
            JpaUtil.validerTransaction();
            
            Logger.getAnonymousLogger().log(Level.INFO, "succès ajouterClient");
            
            System.out.println("Client inscrit");
            System.out.println(client.toString());
            
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "erreur ajouterClient");
            ex.printStackTrace();
            System.out.println("Erreur");
            System.out.println(client.toString());
            JpaUtil.annulerTransaction();
            
            client = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return client;
    }

    public Client trouverClient(Long id) {

        ClientDao clientDao = new ClientDao();
        Client client = new Client();
        try {
            JpaUtil.creerContextePersistance();
            client = clientDao.chercherParId(id);
            Logger.getAnonymousLogger().log(Level.INFO, "succès trouverClient");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.INFO, "erreur trouverClient");
            System.out.println("Erreur");
            client = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return client;
    }

    public List<Client> listerClients() {
        ClientDao clientDao = new ClientDao();
        List<Client> liste = new ArrayList<Client>();
        try {
            JpaUtil.creerContextePersistance();
            liste = clientDao.chercherTous();
            Logger.getAnonymousLogger().log(Level.INFO, "succès listerClients");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "erreur listerClients");
            ex.printStackTrace();
            liste = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return liste;
    }
    
    public Client authentifierClient(String mail, String motDePasse)
    {
        List<Client> clients = listerClients();
        Client client = null;
        for (Client c : clients)
        {
            if (c.getMail() == mail && c.getMotDePasse() == motDePasse)
            {
                client = c;
            }
        }
        return client;
    }
}
