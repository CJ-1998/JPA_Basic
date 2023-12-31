package hellojpa.jpql;

import javax.persistence.*;

@Entity
@NamedQuery(
        name = "Member1.findByUsername",
        query="select m from Member1 m where m.username = :username")
public class Member1 {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TEAM_ID")
    private Team1 team1;

    @Enumerated(EnumType.STRING)
    private MemberType1 type1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team1 getTeam1() {
        return team1;
    }

    public void setTeam1(Team1 team1) {
        this.team1 = team1;
    }

    public MemberType1 getType1() {
        return type1;
    }

    public void setType1(MemberType1 type1) {
        this.type1 = type1;
    }

    public void changeTeam(Team1 team1){
        this.team1=team1;
        team1.getMembers1().add(this);
    }

    @Override
    public String toString() {
        return "Member1{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
