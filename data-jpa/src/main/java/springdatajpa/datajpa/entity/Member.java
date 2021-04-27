package springdatajpa.datajpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter // Entity에는 가급적 @Setter 사용하지 x
public class Member {
    @Id @GeneratedValue
    private Long id;
    private String username;
}
