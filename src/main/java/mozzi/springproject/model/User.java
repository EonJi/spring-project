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

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    // 사용자입장에서 게시글을 가져올때 : OneToMany
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true) // 양방향 매핑
    private List<Board> boards = new ArrayList<>();

}
