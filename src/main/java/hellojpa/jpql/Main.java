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
            JPALecture11_3(em);

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

    public static void JPALecture10_4(EntityManager em){
        //페이징

        for(int i=0;i<100;i++) {
            Member1 member1 = new Member1();
            member1.setUsername("member"+i);
            member1.setAge(i);
            em.persist(member1);
        }

        em.flush();
        em.clear();

        List<Member1> resultList = em.createQuery("select m from Member1 m order by m.age desc", Member1.class)
                .setFirstResult(1)
                .setMaxResults(10)
                .getResultList();

        System.out.println("resultList.size() = " + resultList.size());
        for (Member1 member11 : resultList) {
            System.out.println("member11 = " + member11);
        }
    }

    public static void JPALecture10_5(EntityManager em){
        //조인
        Team1 team1=new Team1();
        team1.setName("teamA");
        em.persist(team1);

        Member1 member1 = new Member1();
        member1.setUsername("member1");
        member1.setAge(10);
        member1.changeTeam(team1);
        em.persist(member1);

        em.flush();
        em.clear();

        String query="select m from Member1 m inner join m.team1 t";
        String query1="select m from Member1 m left outer join m.team1 t";
        String query2="select m from Member1 m, Team1 t where m.username=t.name";

        List<Member1> resultList = em.createQuery(query, Member1.class).getResultList();

    }

    public static void JPALecture10_6(EntityManager em){
        //jpql 타입 표현
        Team1 team1=new Team1();
        team1.setName("teamA");
        em.persist(team1);

        Member1 member1 = new Member1();
        member1.setUsername("member1");
        member1.setAge(10);
        member1.changeTeam(team1);
        member1.setType1(MemberType1.ADMIN);
        em.persist(member1);

        em.flush();
        em.clear();

        String query="select m.username, 'HELLO', TRUE from Member1 m";
        List<Object[]> result = em.createQuery(query).getResultList();

        for (Object[] objects : result) {
            System.out.println("objects[0] = " + objects[0]);
            System.out.println("objects[1] = " + objects[1]);
            System.out.println("objects[2] = " + objects[2]);
        }

        System.out.println("======================");

        String query1="select m.username, 'HELLO', TRUE from Member1 m where m.type1= hellojpa.jpql.MemberType1.ADMIN";
        List<Object[]> result1 = em.createQuery(query1).getResultList();

        for (Object[] objects : result1) {
            System.out.println("objects[0] = " + objects[0]);
            System.out.println("objects[1] = " + objects[1]);
            System.out.println("objects[2] = " + objects[2]);
        }

    }

    public static void JPALecture10_7(EntityManager em){
        //case식
        Team1 team1=new Team1();
        team1.setName("teamA");
        em.persist(team1);

        Member1 member1 = new Member1();
        member1.setUsername("관리자");
        member1.setAge(10);
        member1.changeTeam(team1);
        member1.setType1(MemberType1.ADMIN);
        em.persist(member1);

        em.flush();
        em.clear();

        //기본 case식
        String query="select "+
                        "case when m.age <= 10 then '학생요금' "+
                            "when m.age >= 60 then '경로요금' "+
                            "else '일반요금' "+
                        "end "+
                    "from Member1 m ";

        List<String> resultList = em.createQuery(query, String.class).getResultList();

        for (String s : resultList) {
            System.out.println("s = " + s);
        }

        //coalesce
        String query1="select coalesce(m.username,'이름 없는 회원') from Member1 m";

        List<String> resultList1 = em.createQuery(query1, String.class).getResultList();

        for (String s : resultList1) {
            System.out.println("s = " + s);
        }

        //nullif
        String query2="select nullif(m.username,'관리자') from Member1 m";

        List<String> resultList2 = em.createQuery(query2, String.class).getResultList();

        for (String s : resultList2) {
            System.out.println("s = " + s);
        }
    }

    public static void JPALecture11_1(EntityManager em) {
        //fetch join
        Team1 teamA = new Team1();
        teamA.setName("teamA");
        em.persist(teamA);

        Team1 teamB = new Team1();
        teamB.setName("teamB");
        em.persist(teamB);

        Member1 member1 = new Member1();
        member1.setUsername("회원1");
        member1.setAge(10);
        member1.setTeam1(teamA);
        em.persist(member1);

        Member1 member2 = new Member1();
        member2.setUsername("회원2");
        member2.setAge(10);
        member2.setTeam1(teamA);
        em.persist(member2);

        Member1 member3 = new Member1();
        member3.setUsername("회원3");
        member3.setAge(10);
        member3.setTeam1(teamB);
        em.persist(member3);

        em.flush();
        em.clear();

        String query="select m from Member1 m join fetch m.team1";

        List<Member1> resultList = em.createQuery(query, Member1.class).getResultList();

        for (Member1 member11 : resultList) {
            System.out.println("member11 = " + member11.getUsername()+", "+member11.getTeam1().getName());
        }

        String query1="select t from Team1 t join fetch t.members1";

        List<Team1> resultList1 = em.createQuery(query1, Team1.class).getResultList();

        for (Team1 team1 : resultList1) {
            System.out.println("team1. = " + team1.getName()+", "+team1.getMembers1().size());

            for(Member1 member11 : team1.getMembers1()){
                System.out.println("member11 = " + member11);
            }
        }

        //중복 제거 위해 distinct 사용
        String query2="select distinct t from Team1 t join fetch t.members1";

        List<Team1> resultList2 = em.createQuery(query2, Team1.class).getResultList();

        for (Team1 team1 : resultList2) {
            System.out.println("team1. = " + team1.getName()+", "+team1.getMembers1().size());

            for(Member1 member11 : team1.getMembers1()){
                System.out.println("member11 = " + member11);
            }
        }

        em.flush();
        em.clear();

        //일반 join
        String query3="select t from Team1 t join t.members1 m";

        List<Team1> resultList3 = em.createQuery(query3, Team1.class).getResultList();

        for (Team1 team1 : resultList3) {
            System.out.println("team1. = " + team1.getName()+", "+team1.getMembers1().size());

            for(Member1 member11 : team1.getMembers1()){
                System.out.println("member11 = " + member11);
            }
        }
    }

    public static void JPALecture11_2(EntityManager em) {
        //Named 쿼리
        Team1 teamA = new Team1();
        teamA.setName("teamA");
        em.persist(teamA);

        Team1 teamB = new Team1();
        teamB.setName("teamB");
        em.persist(teamB);

        Member1 member1 = new Member1();
        member1.setUsername("회원1");
        member1.setAge(10);
        member1.setTeam1(teamA);
        em.persist(member1);

        Member1 member2 = new Member1();
        member2.setUsername("회원2");
        member2.setAge(10);
        member2.setTeam1(teamA);
        em.persist(member2);

        Member1 member3 = new Member1();
        member3.setUsername("회원3");
        member3.setAge(10);
        member3.setTeam1(teamB);
        em.persist(member3);

        em.flush();
        em.clear();

        List<Member1> resultList = em.createNamedQuery("Member1.findByUsername", Member1.class)
                .setParameter("username", "회원1")
                .getResultList();

        for (Member1 member11 : resultList) {
            System.out.println("member11 = " + member11);
        }

    }

    public static void JPALecture11_3(EntityManager em) {
        //벌크 연산
        Team1 teamA = new Team1();
        teamA.setName("teamA");
        em.persist(teamA);

        Team1 teamB = new Team1();
        teamB.setName("teamB");
        em.persist(teamB);

        Member1 member1 = new Member1();
        member1.setUsername("회원1");
        member1.setAge(10);
        member1.setTeam1(teamA);
        em.persist(member1);

        Member1 member2 = new Member1();
        member2.setUsername("회원2");
        member2.setAge(10);
        member2.setTeam1(teamA);
        em.persist(member2);

        Member1 member3 = new Member1();
        member3.setUsername("회원3");
        member3.setAge(10);
        member3.setTeam1(teamB);
        em.persist(member3);

        em.flush();
        em.clear();

        int resultCount = em.createQuery("update Member1 m set m.age=20").executeUpdate();

        System.out.println("resultCount = " + resultCount);
    }
}
