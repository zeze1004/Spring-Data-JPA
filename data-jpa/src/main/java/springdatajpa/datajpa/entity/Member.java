package springdatajpa.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter                           // Entity에는 가급적 @Setter 사용하지 x
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"}) // 연관관계 필드(team)는 무한 출력이 될 수 있으니깐 ToString에서 제외
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)    // 실무에서는 무족권 지연로딩!
    @JoinColumn(name = "team_id")
    private Team team;
    public Member(String username) {
        this(username, 0);
    }

    public Member(String username, int age) {
        this(username, age, null);
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
