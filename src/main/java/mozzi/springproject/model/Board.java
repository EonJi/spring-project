package mozzi.springproject.model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
//외부에서 변수 꺼내서 사용할 수 있도록 getter and setter
//lombok 패키지 깔아놨기 때문에 어노테이션 이용하기
public class Board {
    @Id // id가 pk라는 것을 알려주기 위한 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto-increment
    private Long id;
    @NotNull
    @Size(min=2, max=30, message = "제목은 2자 이상 30자 이하로 입력해주세요")
    private String title;
    private String content;
    // Annotation만으로 validation처리를 하면 편리하긴 하지만 자유도에 제약이 있다
}


