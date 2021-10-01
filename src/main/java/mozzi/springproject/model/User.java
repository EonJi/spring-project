package mozzi.springproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    // 사용자입장에서 게시글을 가져올때 : OneToMany
//    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true) // 양방향 매핑
    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
    // EAGER : 사용자 정보랑 board정보 같이 가져옴, LAZY(기본) : 사용자 정보 사용할 때 board정보 조회 가져옴
    // OneToOne, ManyToOne : 기본값 EAGER
    // OneToMany, ManyToMany : 기본값 LAZY
//    @JsonIgnore
    private List<Board> boards = new ArrayList<>(); // 사용자 조회시에 board클래스에 대한 데이터

}
