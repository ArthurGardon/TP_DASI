/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import metier.modele.Consultation;
import javax.persistence.TypedQuery;
import metier.modele.Client;
import metier.modele.Employe;

/**
 *
 * @author Arthur
 */
public class ConsultationDao {

    public void creer(Consultation consult) {
        JpaUtil.obtenirContextePersistance().persist(consult);
    }

    public void supprimer(Consultation consult) {
        JpaUtil.obtenirContextePersistance().remove(consult);
    }

    public Consultation modifier(Consultation consult) {
        return JpaUtil.obtenirContextePersistance().merge(consult);
    }

    public Consultation chercherParId(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Consultation.class, id);
    }

    public List<Consultation> chercherTous() {
        String s = "select e from Consultation e order by e.nom asc";
        TypedQuery<Consultation> query = JpaUtil.obtenirContextePersistance().createQuery(s, Consultation.class);
        return query.getResultList();
    }

    public List<Consultation> historiqueClient(Client c) throws NoResultException {
        String s = "select e from Consultation e where e.client = :client";
        TypedQuery<Consultation> query = JpaUtil.obtenirContextePersistance().createQuery(s, Consultation.class);
        return query.setParameter("client", c).getResultList();
    }

    public List<Object[]> topClient(int top) {
        //retourne une liste de Object[] avec [0] Client et [1] nmbre de consult
        String s = "select c.client, count(c) as sumCons from Consultation c "
                + "group by c.client order by sumCons desc";
        return JpaUtil.obtenirContextePersistance().createQuery(s, Object[].class).setMaxResults(top).getResultList();

    }

    public List<Object[]> topEmploye(int top) {
        //retourne une liste de Object[] avec [0] Employe et [1] nmbre de consult
        String s = "select c.employe, count(c) as sumCons from Consultation c "
                + "group by c.employe order by sumCons desc";
        return JpaUtil.obtenirContextePersistance().createQuery(s, Object[].class).setMaxResults(top).getResultList();
    }

    public List<Object[]> topMediumClient(Client cl, int nbMedium) {
        //retourne une liste de Object[] avec [0] medium et [1] nmbre de consult
        String s = "select c.medium, count(c) as sumCons from Consultation c ";
        if (cl != null) {
            s += "where c.client = :cl ";
        }

        s += "group by c.medium order by sumCons desc";
        TypedQuery<Object[]> res = JpaUtil.obtenirContextePersistance().createQuery(s, Object[].class).setMaxResults(nbMedium);
        
        if (cl != null) {
            res.setParameter("cl", cl);
        }
        return res.getResultList();
    }
    
    public List<Object[]> topMediumEmploye(Employe emp, int nbMedium) {
        //retourne une liste de Object[] avec [0] medium et [1] nmbre de consult
        String s = "select c.medium, count(c) as sumCons from Consultation c ";
        if (emp != null) {
            s += "where c.employe = :emp ";
        }
        s += "group by c.medium order by sumCons desc";
        TypedQuery<Object[]> res = JpaUtil.obtenirContextePersistance().createQuery(s, Object[].class).setMaxResults(nbMedium);
        
        if (emp != null) {
            res.setParameter("emp", emp);
        }
        return res.getResultList();
    }
}
