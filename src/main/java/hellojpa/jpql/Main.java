package hellojpa.jpql;

import javax.persistence.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            JPALecture10_2(em);

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

    public static void JPALecture10_2(EntityManager em){
        //jpql 알아보기
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
    }

    public static void JPALecture10_3(EntityManager em){
        //프로젝션
        Member1 member1=new Member1();
        member1.setUsername("kim");
        member1.setAge(10);
        em.persist(member1);

        em.flush();
        em.clear();

        //엔티티 프로젝션 1
        List<Member1> result = em.createQuery("SELECT m FROM Member1 m", Member1.class).getResultList();

        Member1 findMember = result.get(0);
        findMember.setAge(20);

        //엔티티 프로젝션 2
        em.createQuery("SELECT m.team FROM Member1 m", Team1.class).getResultList();

        //임베디드 타입 프로젝션
        em.createQuery("SELECT o.address FROM Order1 o", Address1.class).getResultList();

        //스칼라 타입 프로젝션
        em.createQuery("SELECT distinct m.username, m.age FROM Member1 m").getResultList();

        //스칼라 타입 프로젝션에서 여러 값 조회
        List<MemberDTO1> resultList = em.createQuery("SELECT new jpql.MemberDTO1(m.username, m.age) FROM Member1 m", MemberDTO1.class).getResultList();
        MemberDTO1 memberDTO1=resultList.get(0);
        System.out.println("memberDTO1.getUsername() = " + memberDTO1.getUsername());
        System.out.println("memberDTO1.getAge() = " + memberDTO1.getAge());
    }
}
