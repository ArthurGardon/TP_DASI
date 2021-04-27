/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import metier.modele.Consultation;
import javax.persistence.TypedQuery;
import metier.modele.Client;
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
    
    public List<Consultation> historiqueClient(Client c)
    {
        String s = "select e from Consultation e where e.client = :client";
        TypedQuery<Consultation> query = JpaUtil.obtenirContextePersistance().createQuery(s, Consultation.class);
        return query.setParameter("client", c).getResultList();
    }
}
