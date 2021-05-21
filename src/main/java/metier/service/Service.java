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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
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

        //preparation du mail
        StringWriter corps = new StringWriter();
        PrintWriter mailWriter = new PrintWriter(corps);
        String sender = "contact@predict.if";
        String reciever = client.getMail();
        String sujet = "";

        try {
            //calcul profil astral
            AstroNet astro = new AstroNet();
            List<String> p = astro.getProfil(client.getNom(), client.getBirthDate());
            ProfilAstral profilClient = new ProfilAstral(p.get(0), p.get(1), p.get(2), p.get(3));
            client.setProfilA(profilClient);

            //persistence du client
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
            ex.printStackTrace();
            JpaUtil.annulerTransaction();

            client = null;

            //contenu du mail
            sujet = "Echec de l'inscription chez Predict'IF";
            mailWriter.println("Bonjour " + client.getPrenom() + ", votre inscription au service PREDICT’IF a malencontreusement échoué...\nMerci de recommencer ultérieurement.");
        } finally {
            JpaUtil.fermerContextePersistance();

            //envoi du mail
            Message.envoyerMail(sender, reciever, sujet, corps.toString());
        }
        return client;
    }

    //methode de test
    public Client trouverClient(Long id) {
        ClientDao clientDao = new ClientDao();
        Client client = new Client();
        try {
            JpaUtil.creerContextePersistance();
            client = clientDao.chercherParId(id);
            Logger.getAnonymousLogger().log(Level.INFO, "succès trouverClient");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.INFO, "erreur trouverClient");
            client = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return client;
    }
    //methode de test

    public Medium trouverMedium(Long id) {
        MediumDao dao = new MediumDao();
        Medium m = new Medium();
        try {
            JpaUtil.creerContextePersistance();
            m = dao.chercherParId(id);
            Logger.getAnonymousLogger().log(Level.INFO, "succès trouverMedium");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.INFO, "erreur trouverMedium");
            m = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return m;
    }
    //methode de test

    public Employe trouverEmploye(Long id) {
        EmployeDao dao = new EmployeDao();
        Employe e = new Employe();
        try {
            JpaUtil.creerContextePersistance();
            e = dao.chercherParId(id);
            Logger.getAnonymousLogger().log(Level.INFO, "succès trouverEmploye");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.INFO, "erreur trouverEmploye");
            e = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return e;
    }
    
     public Consultation trouverConsultation(Long id) {
        ConsultationDao dao = new ConsultationDao();
        Consultation c = new Consultation();
        try {
            JpaUtil.creerContextePersistance();
            c = dao.chercherParId(id);
            Logger.getAnonymousLogger().log(Level.INFO, "succès trouverConsultation");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.INFO, "erreur trouverConsultation");
            c = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return c;
    }

    //methode de test
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

    public List<Medium> listerMediums() {
        MediumDao dao = new MediumDao();
        List<Medium> liste;
        try {
            JpaUtil.creerContextePersistance();
            liste = dao.chercherTous();
            Logger.getAnonymousLogger().log(Level.INFO, "succès listerMediums");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "erreur listerMediums");
            ex.printStackTrace();
            liste = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return liste;
    }

    public Client authentifierClient(String mail, String motDePasse) {
        JpaUtil.creerContextePersistance();
        ClientDao dao = new ClientDao();
        Client c = null;
        Client client = null;
        try {
            c = dao.chercherMail(mail);
            if (c.getMotDePasse().equals(motDePasse)) {
                client = c;
            }
        } catch (NoResultException e) {
            c = null;
        }

        JpaUtil.fermerContextePersistance();
        return client;
    }

    public Employe authentifierEmploye(String mail, String motDePasse) {
        JpaUtil.creerContextePersistance();
        EmployeDao dao = new EmployeDao();
        Employe e = null;
        Employe emp = null;
        try {
            e = dao.chercherMail(mail);
            if (e.getMotDePasse().equals(motDePasse)) {
                emp = e;
            }
        } catch (NoResultException ex) {
            e = null;
        }

        JpaUtil.fermerContextePersistance();
        return emp;
    }

    //methode de test/init
    public Employe ajouterEmploye(Employe emp) {
        EmployeDao dao = new EmployeDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            dao.creer(emp);
            JpaUtil.validerTransaction();

            Logger.getAnonymousLogger().log(Level.INFO, "succès ajouterEmploye");
            Logger.getAnonymousLogger().log(Level.INFO, emp.toString());
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "ERREUR ajouterEmploye");
            Logger.getAnonymousLogger().log(Level.SEVERE, emp.toString());
            emp = null;
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return emp;
    }

    //methode de test/init
    public Medium ajouterMedium(Medium med) {
        MediumDao dao = new MediumDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            dao.creer(med);
            JpaUtil.validerTransaction();

            Logger.getAnonymousLogger().log(Level.INFO, "succès ajouterMedium");
            Logger.getAnonymousLogger().log(Level.INFO, med.toString());
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "ERREUR ajouterMedium");
            Logger.getAnonymousLogger().log(Level.SEVERE, med.toString());
            med = null;
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return med;
    }

    public void initBD() throws ParseException {
        //temp
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        //temp
        Client a = new Client("G", "G", "a@g.com", "123", "0707070707", new Date(10, 10, 2010), "5 rue");
        Client b = new Client("B", "G", "b@g.com", "123", "0808080808", new Date(01, 01, 1998), "lotr rue");
        Employe c = new Employe("R", "T", "r@t.com", "234", "050505203", "H");
        inscrireClient(b);
        inscrireClient(a);
        ajouterEmploye(c);

        Cartomancien cart = new Cartomancien("Mme Irma", "F", "Mes cartes répondront à toutes vos questions personnelles.");
        Spirite spir = new Spirite("Professeur Tran", "H", "Votre avenir est devant vous: regardons le ensemble!", "Marc de cafe, boule de cristal, oeilles de lapin");
        Astrologue astr = new Astrologue("Mr M", "H", "Avenir, avenir, que nous reserves tu?", "Institut des nouveaux Savoirs Astrologiques", "2010");
        ajouterMedium(cart);
        ajouterMedium(spir);
        ajouterMedium(astr);

        Consultation consult1 = new Consultation(sd.parse("01/04/2018 18:30:00"), sd.parse("01/04/2018 18:45:00"), "passable", c, cart, b);
        ajouterConsultation(consult1);
        Consultation consult2 = new Consultation(sd.parse("02/04/2018 18:30:00"), sd.parse("02/04/2018 18:45:00"), "ok", c, cart, b);
        ajouterConsultation(consult2);
        Consultation consult3 = new Consultation(sd.parse("03/04/2018 18:30:00"), sd.parse("03/04/2018 18:45:00"), "ok", c, astr, b);
        ajouterConsultation(consult2);
    }

    //methode outil pour persister/tester
    public Consultation ajouterConsultation(Consultation cons) {
        ConsultationDao dao = new ConsultationDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            dao.creer(cons);
            JpaUtil.validerTransaction();

            Logger.getAnonymousLogger().log(Level.INFO, "succès ajouterConsultation");
            Logger.getAnonymousLogger().log(Level.INFO, cons.toString());
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "ERREUR ajouterConsultation");
            Logger.getAnonymousLogger().log(Level.SEVERE, cons.toString());
            cons = null;
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return cons;
    }

    public Consultation demanderConsultation(Client client, Medium medium) {
        Consultation c = null;

        EmployeDao dao = new EmployeDao();
        JpaUtil.creerContextePersistance();
        List<Employe> employes = dao.chercherDispo();
        JpaUtil.fermerContextePersistance();

        Employe emp = null;
        for (Employe e : employes) {
            if (e.isIsAvailable() && e.getGenre().equals(medium.getGenre())) {
                emp = e;
                c = new Consultation(null, null, null, emp, medium, client);
                break;
            }
        }

        if (emp != null) {
            JpaUtil.creerContextePersistance();
            try {
                JpaUtil.ouvrirTransaction();
                emp.setIsAvailable(false);
                dao.modifier(emp);
                JpaUtil.validerTransaction();
            } catch (Exception e) {
                e.printStackTrace();
            }

            JpaUtil.fermerContextePersistance();

            ajouterConsultation(c);
            String message = "Bonjour " + emp.getPrenom() + ". Consultation requise pour "
                    + client.getPrenom() + " " + client.getNom() + ". Médium à incarner: " + medium.getDenomination();

            Message.envoyerNotification(emp.getPhoneNumber(), message);
        }

        return c;
    }

    public void accepterConsultation(Consultation consult, Date dateDebut) {
        try {
            ConsultationDao dao = new ConsultationDao();

            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            consult.setDateDebut(dateDebut);
            dao.modifier(consult);
            JpaUtil.validerTransaction();

            Client client = consult.getClient();
            String message = "Bonjour " + client.getPrenom() + ". J'ai bien recu votre demande consultation pour " + dateDebut
                    + " vous pouvez des a present me contacter au " + consult.getEmploye().getPhoneNumber() + "! A bientot, "
                    + consult.getMedium().getDenomination();
            Message.envoyerNotification(client.getPhoneNumber(), message);
            Logger.getAnonymousLogger().log(Level.INFO, "succès accepterConsultation");

        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
            Logger.getAnonymousLogger().log(Level.SEVERE, "ERREUR accepterConsult");

        } finally {
            JpaUtil.fermerContextePersistance();
        }

    }

    public void validerConsultation(Consultation consult, Date dateFin, String commentaire) {
        try {
            ConsultationDao consultDao = new ConsultationDao();
            EmployeDao employeDao = new EmployeDao();

            Employe emp = consult.getEmploye();
            emp.setIsAvailable(true);
            emp.addConsultation();

            consult.setDateFin(dateFin);
            consult.setCommentaire(commentaire);

            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            consultDao.modifier(consult);
            employeDao.modifier(emp);

            JpaUtil.validerTransaction();

            Logger.getAnonymousLogger().log(Level.INFO, "succès validerConsultation");
        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
            Logger.getAnonymousLogger().log(Level.SEVERE, "ERREUR validerConsult");

        } finally {
            JpaUtil.fermerContextePersistance();
        }
    }

    public List<String> demanderAide(Client c, int amour, int sante, int travail) {
        String couleur = c.getProfilA().getColor();
        String animal = c.getProfilA().getAnimal();
        List<String> res;
        try {
            AstroNet astro = new AstroNet();
            res = astro.getPredictions(couleur, animal, amour, sante, travail);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "erreur AstroNetAPI");
            res = null;
        }
        return res;
    }

    public List<Consultation> getHistoriqueConsultations(Client c) {
        List<Consultation> res;
        JpaUtil.creerContextePersistance();
        ConsultationDao dao = new ConsultationDao();
        try {
            res = dao.historiqueClient(c);
        } catch (NoResultException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "erreur getHistoriqueConsultations");
            e.printStackTrace();
            res = null;
        }
        return res;
    }

    public List<Object[]> statTopClient(int top) {
        //returns Object[] with [0] Client and [1] number of consults
        ConsultationDao dao = new ConsultationDao();
        List<Object[]> liste;
        try {
            JpaUtil.creerContextePersistance();
            liste = dao.topClient(top);
            Logger.getAnonymousLogger().log(Level.INFO, "succès statTopClient");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.INFO, "erreur statTopClient");
            ex.printStackTrace();
            liste = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return liste;
    }

    public List<Object[]> statTopEmploye(int top) {
        //returns Object[] with [0] Employe and [1] number of consults
        ConsultationDao dao = new ConsultationDao();
        List<Object[]> liste;
        try {
            JpaUtil.creerContextePersistance();
            liste = dao.topEmploye(top);
            Logger.getAnonymousLogger().log(Level.INFO, "succès statTopEmploye");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.INFO, "erreur statTopEmploye");
            ex.printStackTrace();
            liste = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return liste;
    }

    public List<Object[]> statMediumFav(Client c, int nbMedium) {
        //returns Object[] with [0] Medium and [1] number of consults
        //c = null renvoie le medium favori de tous les clients
        ConsultationDao dao = new ConsultationDao();
        List<Object[]> liste;
        try {
            JpaUtil.creerContextePersistance();
            liste = dao.topMediumClient(c, nbMedium);
            Logger.getAnonymousLogger().log(Level.INFO, "succès statMediumFav");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.INFO, "erreur statMediumFav");
            ex.printStackTrace();
            liste = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return liste;

    }

    public List<Object[]> statMediumIncarne(Employe emp, int nbMedium) {
        //returns Object[] with [0] Medium and [1] number of consults
        //emp = null renvoie le medium le plus incarne de tous
        ConsultationDao dao = new ConsultationDao();
        List<Object[]> liste;
        try {
            JpaUtil.creerContextePersistance();
            liste = dao.topMediumEmploye(emp, nbMedium);
            Logger.getAnonymousLogger().log(Level.INFO, "statMediumIncarne");
        } catch (Exception ex) {
            Logger.getAnonymousLogger().log(Level.INFO, "erreur statMediumIncarne");
            ex.printStackTrace();
            liste = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return liste;
    }

    //pas sur si nécessaire
    public ProfilAstral getProfilAstral(Client c) {
        return c.getProfilA();
    }
    
    public Consultation getConsultation (Employe e) {
        ConsultationDao dao = new ConsultationDao();
        Consultation c = new Consultation();
        try {
            JpaUtil.creerContextePersistance();
            c = dao.chercherParEmploye(e);
            Logger.getAnonymousLogger().log(Level.INFO, "succès getConsultation");
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getAnonymousLogger().log(Level.INFO, "erreur getConsultation");
            c = null;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return c;
    }
}
