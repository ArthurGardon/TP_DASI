/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.ClientDao;
import dao.ConsultationDao;
import dao.EmployeDao;
import dao.JpaUtil;
import dao.MediumDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Astrologue;
import metier.modele.Cartomancien;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.modele.ProfilAstral;
import metier.modele.Spirite;
import util.AstroNet;
import util.Message;

/**
 *
 * @author Arthur
 */
public class Service {

    public Client inscrireClient(Client client) {
        ClientDao clientDao = new ClientDao();
        //calcul profil astral
        //TODO catch and cancel the whole function?
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
        StringWriter corps = new StringWriter();
        PrintWriter mailWriter = new PrintWriter(corps);
        String sender = "contact@predict.if";
        String reciever = client.getMail();
        String sujet= "";
        
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            clientDao.creer(client);
            
            JpaUtil.validerTransaction();
            
            Logger.getAnonymousLogger().log(Level.INFO, "succès ajouterClient");
            
            //contenu du mail
            sujet = "Bienvenue chez Predict'IF";
            mailWriter.println("Bonjour " + client.getPrenom() + ", nous vous confirmons votre inscription au service PREDICT’IF.\nRendez-vous vite sur notre site pour consulter votre profil astrologique et profiter des dons incroyables de nos mediums");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "erreur ajouterClient");
            System.out.println("Erreur");
            System.out.println(client.toString());
            JpaUtil.annulerTransaction();
            
            //contenu du mail
            sujet = "Echec de l'inscription chez Predict'IF";
            mailWriter.println("Bonjour " + client.getPrenom() + ", votre inscription au service PREDICT’IF a malencontreusement échoué...\nMerci de recommencer ultérieurement.");

            
            client = null;
        } finally {
            JpaUtil.fermerContextePersistance();
            
            //envoi du mail
            Message.envoyerMail(sender, reciever, sujet, corps.toString());
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
        List<Client> liste;
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
        JpaUtil.creerContextePersistance();
        ClientDao dao = new ClientDao();
        Client c = dao.chercherMail(mail);
        JpaUtil.fermerContextePersistance();
        
        Client client = null;
        if (c.getMotDePasse().equals(motDePasse))
        {
            client = c;
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
    
    public Medium ajouterMedium(Medium med)
    {
        MediumDao dao = new MediumDao();
        try
        {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            dao.creer(med);
            JpaUtil.validerTransaction();
            
            Logger.getAnonymousLogger().log(Level.INFO, "succès ajouterMedium");
            Logger.getAnonymousLogger().log(Level.INFO, med.toString());
        }
        catch (Exception ex)
        {
            Logger.getAnonymousLogger().log(Level.SEVERE, "ERREUR ajouterMedium");
            Logger.getAnonymousLogger().log(Level.SEVERE, med.toString());
            med = null;
            JpaUtil.annulerTransaction();
        }
        finally
        {
            JpaUtil.fermerContextePersistance();
        }
        return med;
    }
    
    public void initBD() throws ParseException
    {
        //temp
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        //temp
        Client a = new Client("G", "G", "a@g.com", "123", "0707070707", new Date(10, 10, 2010), "5 rue");
        Client b = new Client("B", "G", "b@g.com", "123", "0808080808", new Date(01,01,1998),"lotr rue");
        Employe c = new Employe("R", "T", "r@t.com", "234", "050505203", "M");
        inscrireClient(b);
        inscrireClient(a);
        ajouterEmploye(c);
        
        Cartomancien cart = new Cartomancien("Mme Irma", "F", "Mes cartes répondront à toutes vos questions personnelles.");
        Spirite spir = new Spirite("Professeur Tran", "H", "Votre avenir est devant vous: regardons le ensemble!", "Marc de cafe, boule de cristal, oeilles de lapin");
        Astrologue astr = new Astrologue("Mr M", "H", "Avenir, avenir, que nous reserves tu?", "Institut des nouveaux Savoirs Astrologiques", "2010");
        ajouterMedium(cart);
        ajouterMedium(spir);
        ajouterMedium(astr);
        
        Consultation conslut = new Consultation(sd.parse("01/04/2018 18:30:00"), sd.parse("01/04/2018 18:45:00"), "passable", c, cart, b);
        ajouterConsultation(conslut);
    }
    
    public Consultation ajouterConsultation(Consultation cons)
    {
        ConsultationDao dao = new ConsultationDao();
        try
        {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            dao.creer(cons);
            JpaUtil.validerTransaction();
            
            Logger.getAnonymousLogger().log(Level.INFO, "succès ajouterConsultation");
            Logger.getAnonymousLogger().log(Level.INFO, cons.toString());
        }
        catch (Exception ex)
        {
            Logger.getAnonymousLogger().log(Level.SEVERE, "ERREUR ajouterConsultation");
            Logger.getAnonymousLogger().log(Level.SEVERE, cons.toString());
            cons = null;
            JpaUtil.annulerTransaction();
        }
        finally
        {
            JpaUtil.fermerContextePersistance();
        }
        return cons;
    }
}
