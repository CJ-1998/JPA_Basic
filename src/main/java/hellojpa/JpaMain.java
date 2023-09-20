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

//          비영속 상태
            Member member = new Member();
            member.setId(200L);
            member.setName("helloA");

//          영속 상태
//          엔티티 생명 주기 확인
//          persist 했을 때 쿼리 날라가는 것이 아니라 커밋했을 때 날라가는 것 확인
            System.out.println("===BEFORE===");
            em.persist(member);
            System.out.println("===AFTER===");

//          1차 캐시 존재 확인
            Member findMember1 = em.find(Member.class, 100L);
            System.out.println("findMember.getId() = " + findMember1.getId());
            System.out.println("findMember.getName() = " + findMember1.getName());

            Member findMember2 = em.find(Member.class, 100L);

//          영속 엔티티 동일성 보장 확인
            System.out.println("result= "+ (findMember1==findMember2));

//          쓰기 지연 확인
            Member member1 = new Member(250L, "A");
            Member member2 = new Member(260L, "B");

            em.persist(member1);
            em.persist(member2);

            System.out.println("====================");

//          변경 감지 확인
            Member findMember3 = em.find(Member.class, 150L);
            findMember3.setName("zzz");

            tx.commit();

//            jpa 조회
//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJPA");
//
//            jpql 확인
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.getName() = " + member.getName());
//            }

//            em.remove(findMember);

//            tx.commit();
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
