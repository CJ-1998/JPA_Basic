package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

//            비영속
            Member member = new Member();
            member.setId(100L);
            member.setName("helloA");

//            영속
            System.out.println("===BEFORE===");
            em.persist(member);
            System.out.println("===AFTER===");

            Member findMember1 = em.find(Member.class, 100L);
            System.out.println("findMember.getId() = " + findMember1.getId());
            System.out.println("findMember.getName() = " + findMember1.getName());

            Member findMember2 = em.find(Member.class, 100L);
            System.out.println("result= "+ (findMember1==findMember2));

//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJPA");
//
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.getName() = " + member.getName());
//            }

//            em.remove(findMember);

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
