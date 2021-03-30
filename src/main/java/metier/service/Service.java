/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.ClientDao;
import dao.EmployeDao;
import dao.JpaUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Client;
import metier.modele.Employe;
import metier.modele.ProfilAstral;
import util.AstroNet;

/**
 *
 * @author Arthur
 */
public class Service {

    public Client inscrireClient(Client client) {
        ClientDao clientDao = new ClientDao();
        //calcul profil astral
        try {
            AstroNet astro = new AstroNet();
            List<String> p = astro.getProfil(client.getNom(), client.getBirthDate());
            ProfilAstral profilClient = new ProfilAstral(p.get(0), p.get(1), p.get(2), p.get(3));
            client.setProfilA(profilClient);
        }
        catch(IOException e){
            Logger.getAnonymousLogger().log(Level.INFO, "erreur AstroNetAPI");
        }
        //persistence du client
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
    
    public Employe ajouterEmploye(Employe emp)
    {
        EmployeDao dao = new EmployeDao();
        try
        {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            dao.creer(emp);
            JpaUtil.validerTransaction();
            
            Logger.getAnonymousLogger().log(Level.INFO, "succès ajouterEmploye");
            Logger.getAnonymousLogger().log(Level.INFO, emp.toString());
        }
        catch (Exception ex)
        {
            Logger.getAnonymousLogger().log(Level.SEVERE, "ERREUR ajouterEmploye");
            Logger.getAnonymousLogger().log(Level.SEVERE, emp.toString());
            emp = null;
            JpaUtil.annulerTransaction();
        }
        finally
        {
            JpaUtil.fermerContextePersistance();
        }
        return emp;
    }
}
