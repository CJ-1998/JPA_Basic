package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
//@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR", sequenceName = "MEMBER_SEQ", initialValue = 1, allocationSize = 1) //sequence 전략 사용시
//@TableGenerator(name = "MEMBER_SEQ_GENERATOR", table = "MY_SEQUENCES", pkColumnValue = "MEMBER_SEQ", allocationSize = 1) //table 전략 사용시
public class Member extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();

//    @Column(name = "TEAM_ID")
//    private Long teamId;



//    @GeneratedValue(strategy = GenerationType.IDENTITY)  //identity 전략 사용 시
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")  //sequence 전략 사용 시
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")  //table 전략 사용시
//    private Long id;

//    @Column(name = "name")
//    private String username;

//    private Integer age;
//
//    @Enumerated(EnumType.STRING)
//    private RoleType roleType;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
//
//    @Lob
//    private String description;

    public Member(){

    }

    public Member(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    //Getter, Setter …

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getName() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {this.team = team;}

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", team=" + team +
                '}';
    }

    //    public Long getTeamId() {
//        return teamId;
//    }
//
//    public void setTeamId(Long teamId) {
//        this.teamId = teamId;
//    }

//    public String getName() {return username;}
//
//    public void setName(String username) {this.username = username;}
//
//    public Integer getAge() {return age;}
//
//    public void setAge(Integer age) {this.age = age;}
//
//    public RoleType getRoleType() {return roleType;}
//
//    public void setRoleType(RoleType roleType) {this.roleType = roleType;}
//
//    public Date getCreatedDate() {return createdDate;}
//
//    public void setCreatedDate(Date createdDate) {this.createdDate = createdDate;}
//
//    public Date getLastModifiedDate() {return lastModifiedDate;}
//
//    public void setLastModifiedDate(Date lastModifiedDate) {this.lastModifiedDate = lastModifiedDate;}
//
//    public String getDescription() {return description;}
//
//    public void setDescription(String description) {this.description = description;}
}