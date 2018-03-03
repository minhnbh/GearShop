/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.category;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author MinhNBHSE61805
 */
public class CategoryDAO implements Serializable {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("GearShopPU");
    EntityManager em = emf.createEntityManager();

    public void persist(Object object) {
        try {
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public int addCategory(Category category) {
        if (category != null) {
            persist(category);
        } else {
            return 0;
        }
        return 1;
    }
}
