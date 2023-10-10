package hellojpa.jpql;

import javax.persistence.*;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member1 member1=new Member1();
            member1.setUsername("kim");
            member1.setAge(10);
            em.persist(member1);

            TypedQuery<Member1> query1 = em.createQuery("SELECT m FROM Member1 m", Member1.class);
            Query query2 = em.createQuery("SELECT m.username, m.age from Member1 m");

            TypedQuery<Member1> query3 = em.createQuery("SELECT m FROM Member1 m where m.username = :username", Member1.class);
            query3.setParameter("username","kim");
            Member1 singleResult = query3.getSingleResult();
            System.out.println("singleResult.getUsername() = " + singleResult.getUsername());

            tx.commit();
        }
        catch(Exception e){
            tx.rollback();
        }
        finally {
            em.close();
        }

        emf.close();
    }
}
