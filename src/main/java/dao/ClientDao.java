/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import metier.modele.Client;
import javax.persistence.TypedQuery;
/**
 *
 * @author Arthur
 */
public class ClientDao {
    
    public void creer(Client client) {
        JpaUtil.obtenirContextePersistance().persist(client);
    }

    public void supprimer(Client client) {
        JpaUtil.obtenirContextePersistance().remove(client);
    }

    public Client modifier(Client client) {
        return JpaUtil.obtenirContextePersistance().merge(client);
    }

    public Client chercherParId(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Client.class, id);
    }

    public List<Client> chercherTous() {
        String s = "select e from Client e order by e.nom asc";
        TypedQuery<Client> query = JpaUtil.obtenirContextePersistance().createQuery(s, Client.class);
        return query.getResultList();
    }
}
