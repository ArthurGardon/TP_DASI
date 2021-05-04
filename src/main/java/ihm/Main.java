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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import metier.modele.Astrologue;
import metier.modele.Cartomancien;
import metier.modele.Consultation;
import metier.modele.Employe;
import metier.modele.Medium;
import metier.modele.Spirite;
import metier.service.Service;

/**
 *
 * @author Arthur
 */
class Main {

    static Service service = new Service();

    public static void main(String[] args) {
        JpaUtil.init();
        init();
        JpaUtil.destroy();
    }

    static void testerAuth(Client c) {
        System.out.println(service.authentifierClient(c.getMail(), c.getMotDePasse()));
    }

    static void testerListes(Client c) {
        System.out.println(service.listerClients());
        System.out.println(service.listerMediums());
        System.out.println(service.getHistoriqueConsultations(c));
    }

    static void testerConsultFlow(Client c, Medium m, Employe e, Date dateDebut, Date dateFin) {
        Consultation consult = service.demanderConsultation(c, m);
        if (consult != null) {
            service.accepterConsultation(consult, dateDebut);
            service.validerConsultation(consult, dateFin, "ok");
        }
    }

    static void testerStats(Client c, Employe e) {
        List<Object[]> listTopClient = service.statTopClient(2);
        List<Object[]> listTopEmp = service.statTopEmploye(4);
        List<Object[]> listMedInc = service.statMediumIncarne(e, 3);
        List<Object[]> listMedFav1 = service.statMediumFav(null, 2);
        List<Object[]> listMedFav2 = service.statMediumFav(c, 2);

        System.out.println("listTopClient");
        for (var item : listTopClient) {
            System.out.println(item[0] + " nb de consultations " + item[1]);
        }
        System.out.println("listTopEmp");
        for (var item : listTopEmp) {
            System.out.println(item[0] + " nb de consultations " + item[1]);
        }
        System.out.println("listMedInc");
        for (var item : listMedInc) {
            System.out.println(item[0] + " nb de consultations " + item[1]);
        }
        System.out.println("listMedFav1");
        for (var item : listMedFav1) {
            System.out.println(item[0] + " nb de consultations " + item[1]);
        }
        System.out.println("listMedFav2");
        for (var item : listMedFav2) {
            System.out.println(item[0] + " nb de consultations " + item[1]);
        }
    }

    static void init() {
        try {
            service.initBD();
        } catch (ParseException e) {
            Logger.getAnonymousLogger().log(Level.INFO, "Erreur parse date");
        }
    }
}
