package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            JPALecture8_5(em);
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

    public static void JPALecture1To3(EntityManager em, EntityTransaction tx){
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

//          flush 확인
            Member member3 = new Member(300L, "A");
            em.persist(member3);

            em.flush();
            System.out.println("-------------------");

//          준영속 상태
            Member findMember4 = em.find(Member.class, 150L);
            findMember3.setName("AAA");

            em.detach(findMember4);
//          em.clear();
            System.out.println("====================");

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

    public static void JPALecture4_1(EntityManager em){
//      enum 타입 매핑 시 ORDINAL말고 STRING 써야 하는 이유 설명
//        Member member1= new Member();
//        member1.setId(1L);
//        member1.setName("A");
//        member1.setRoleType(RoleType.USER);
//
//        Member member2= new Member();
//        member2.setId(2L);
//        member2.setName("B");
//        member2.setRoleType(RoleType.ADMIN);
//
//        em.persist(member1);
//        em.persist(member2);
    }

    public static void JPALecture4_2(EntityManager em){
//      기본키 매핑 전략 확인
        Member member1=new Member();
        member1.setName("A");

        Member member2=new Member();
        member2.setName("B");

        Member member3=new Member();
        member3.setName("C");

        System.out.println("=================");
        em.persist(member1);
        System.out.println("member1.getId() = " + member1.getId());
        em.persist(member2);
        em.persist(member3);

        System.out.println("member1.getId() = " + member1.getId());
        System.out.println("member2.getId() = " + member2.getId());
        System.out.println("member3.getId() = " + member3.getId());

        System.out.println("=================");
    }

    public static void JPALecture5_1(EntityManager em){
//      DB 테이블대로 엔티티 설계 시 문제 확인
//        Team team=new Team();
//        team.setName("TeamA");
//
//        em.persist(team);
//
//        Member member=new Member();
//        member.setName("member1");
//        member.setTeamId(team.getId());
//
//        em.persist(member);
//
////      조회 시 문제
//        Member findMember = em.find(Member.class, member.getId());
//
//        Long findTeamId = findMember.getTeamId();
//        Team findTeam = em.find(Team.class, findTeamId);

    }

    public static void JPALecture5_2(EntityManager em) {
//      객체지향적으로 엔티티 설계 변경
        Team team=new Team();
        team.setName("TeamA");

        em.persist(team);

        Member member=new Member();
        member.setName("member1");
        member.setTeam(team);

        em.persist(member);

//      조회
        Member findMember = em.find(Member.class, member.getId());
        Team findTeam = findMember.getTeam();
        System.out.println("findTeam.getName() = " + findTeam.getName());
    }

    public static void JPALecture5_3(EntityManager em){
//      양방향 연관관계 확인
        Team team=new Team();
        team.setName("TeamA");
        em.persist(team);

        Member member=new Member();
        member.setName("member1");
//        member.setTeam(team);
//        member.changeTeam(team);
        em.persist(member);

//        team.getMembers().add(member);
        team.addMember(member);

        em.flush();
        em.clear();

        Member findMember = em.find(Member.class, member.getId());
        List<Member> members = findMember.getTeam().getMembers();

        for (Member m : members) {
            System.out.println("m.getName() = " + m.getName());
        }

        Team findTeam = em.find(Team.class, team.getId());
        List<Member> members1 = findTeam.getMembers();

        for (Member member1 : members1) {
            System.out.println("member1.getName() = " + member1.getName());
        }

    }

    public static void JPALecture7_1(EntityManager em){
//      상속관계 매핑 예제
        Movie movie=new Movie();
        movie.setName("aaaa");
        movie.setActor("bbbb");
        movie.setDirector("cccc");
        movie.setPrice(10000);

        em.persist(movie);

        em.flush();
        em.clear();

//        em.find(Movie.class, movie.getId());

        em.find(Item.class, movie.getId());

    }

    public static void JPALecture7_2(EntityManager em){
        Member member=new Member();
        member.setName("son");
        member.setCreateBy("kim");
        member.setCreatedDate(LocalDateTime.now());

        em.persist(member);

    }

    public static void JPALecture8_1(EntityManager em){
//      프록시 알아보기 위한 예제

        Member member=new Member();
        member.setName("kom");

        em.persist(member);

        em.flush();
        em.clear();

        em.find(Member.class, member.getId());
        System.out.println("member.getId() = " + member.getId());
        System.out.println("member.getName() = " + member.getName());

        em.flush();
        em.clear();

        Member referenceMember = em.getReference(Member.class, member.getId());
        System.out.println("referenceMember.getClass() = " + referenceMember.getClass());
        System.out.println("referenceMember.getId() = " + referenceMember.getId());
        System.out.println("referenceMember.getName() = " + referenceMember.getName());

        em.flush();
        em.clear();

//      Member만 출력하거나 Member, Team 함께 출력하는 경우 보기 위한 예제
        Member findMember = em.find(Member.class, 1L);
        printMemberAndTeam(findMember);
        printMember(findMember);
    }

    public static void JPALecture8_2(EntityManager em){
//      프록시 때문에 ==비교하면 안되는 이유 확인
        Member member1=new Member();
        member1.setName("kom");
        em.persist(member1);

        Member member2=new Member();
        member2.setName("kom");
        em.persist(member2);

        em.flush();
        em.clear();

        Member m1 = em.find(Member.class, member1.getId());
        Member m2 = em.getReference(Member.class, member2.getId());

        System.out.println("m1==m2: " + (m1==m2));
    }

    public static void JPALecture8_3(EntityManager em){
//      준영속 상태에서 프록시 초기화 안되는 것 확인
        Member member1=new Member();
        member1.setName("kom");
        em.persist(member1);

        em.flush();
        em.clear();

        Member refMember = em.getReference(Member.class, member1.getId());
        System.out.println("refMember.getClass() = " + refMember.getClass());

        em.detach(refMember);

        refMember.getName();
    }

    public static void JPALecture8_4(EntityManager em, EntityManagerFactory emf){
//      프록시 관련 유틸리티 함수
        Member member1=new Member();
        member1.setName("kom");
        em.persist(member1);

        em.flush();
        em.clear();

        Member refMember = em.getReference(Member.class, member1.getId());
        System.out.println("refMember.getClass() = " + refMember.getClass());
        System.out.println("isLoaded= "+emf.getPersistenceUnitUtil().isLoaded(refMember));
        Hibernate.initialize(refMember);    //강제 초기화
        System.out.println("isLoaded= "+emf.getPersistenceUnitUtil().isLoaded(refMember));
    }

    public static void JPALecture8_5(EntityManager em){
//      지연 로딩, 즉시 로딩 확인
        Team team=new Team();
        team.setName("teamA");
        em.persist(team);

        Member member1=new Member();
        member1.setName("kim");
        member1.setTeam(team);

        em.persist(member1);

        em.flush();
        em.clear();

        Member m = em.find(Member.class, member1.getId());
        System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass());

        System.out.println("===================");
        m.getTeam().getName();
        System.out.println("===================");
    }

    public static void JPALecture8_6(EntityManager em){
//      영속성 전이 예제

        Child child1 =new Child();
        Child child2 =new Child();

        Parent parent=new Parent();
        parent.addChild(child1);
        parent.addChild(child2);

        em.persist(parent);
        em.persist(child1);
        em.persist(child2);

        em.flush();
        em.clear();

//      고아 객체 예제
        Parent findParent = em.find(Parent.class, parent.getId());
        findParent.getChildList().remove(0);
    }

    private static void printMember(Member member) {
        System.out.println("member.getName() = " + member.getName());
    }

    private static void printMemberAndTeam(Member member) {
        String name = member.getName();
        System.out.println("name = " + name);

        Team team = member.getTeam();
        System.out.println("team.getName() = " + team.getName());
    }
}


