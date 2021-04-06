/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import metier.modele.Medium;
import javax.persistence.TypedQuery;
/**
 *
 * @author Arthur
 */
public class MediumDao {
    
    public void creer(Medium medium) {
        JpaUtil.obtenirContextePersistance().persist(medium);
    }

    public void supprimer(Medium medium) {
        JpaUtil.obtenirContextePersistance().remove(medium);
    }

    public Medium modifier(Medium medium) {
        return JpaUtil.obtenirContextePersistance().merge(medium);
    }

    public Medium chercherParId(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Medium.class, id);
    }

    public List<Medium> chercherTous() {
        String s = "select e from Medium e order by e.nom asc";
        TypedQuery<Medium> query = JpaUtil.obtenirContextePersistance().createQuery(s, Medium.class);
        return query.getResultList();
    }
}
