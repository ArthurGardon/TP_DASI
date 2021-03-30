/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import metier.modele.Employe;
import dao.JpaUtil;
import dao.EmployeDao;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Arthur
 */
public class EmployeService {
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
