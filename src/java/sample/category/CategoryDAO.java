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
public class CategoryDAO implements Serializable{
    
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ReviewJavaWebPU");
    static EntityManager em = emf.createEntityManager();
    
    public static int addCategory(Category category) {
        em.getTransaction().begin();
        em.persist(category);
        em.getTransaction().commit();
        em.flush();
        return category.getCategoryID();
    }
}
