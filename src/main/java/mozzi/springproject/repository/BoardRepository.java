package mozzi.springproject.repository;

import mozzi.springproject.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    //extends JpaRepository<모델클래스, PK>

    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);

}
