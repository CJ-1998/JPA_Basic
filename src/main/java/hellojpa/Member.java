package hellojpa;

import javax.persistence.*;
import java.util.*;

@Entity
//@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR", sequenceName = "MEMBER_SEQ", initialValue = 1, allocationSize = 1) //sequence 전략 사용시
//@TableGenerator(name = "MEMBER_SEQ_GENERATOR", table = "MY_SEQUENCES", pkColumnValue = "MEMBER_SEQ", allocationSize = 1) //table 전략 사용시
public class Member extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne(fetch=FetchType.LAZY)
//    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();

    @Embedded
    private Period workPeriod;
    @Embedded
    private Address homeAddress;

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "city", column = @Column(name = "WORK_CITY")),
                    @AttributeOverride(name = "street", column=@Column(name = "WORK_STREET")),
                    @AttributeOverride(name = "zipcode", column=@Column(name = "WORK_ZIPCODE"))
            }
    )
    private Address workAddress;

    @ElementCollection
    @CollectionTable(name="FAVORITE_FOOD" , joinColumns=@JoinColumn(name="MEMBER_ID"))
    @Column(name="FOOD_NAME")
    private Set<String> favoriteFoods=new HashSet<>();

    @ElementCollection
    @CollectionTable(name="ADDRESS" , joinColumns=@JoinColumn(name="MEMBER_ID"))
    private List<Address> addressHistory=new ArrayList<>();

    //값 타입 컬렉션의 대안으로 엔티티로 매핑하는 것
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory2=new ArrayList<>();

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

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<Address> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<Address> addressHistory) {
        this.addressHistory = addressHistory;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

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