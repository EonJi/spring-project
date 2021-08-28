package mozzi.springproject.model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
//외부에서 변수 꺼내서 사용할 수 있도록 getter and setter
//lombok 패키지 깔아놨기 때문에 어노테이션 이용하기
public class Board {
    @Id // id가 pk라는 것을 알려주기 위한 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-increment
    private Long id;
    private String title;
    private String content;
}


