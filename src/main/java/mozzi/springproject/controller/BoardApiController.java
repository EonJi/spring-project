package mozzi.springproject.controller;
import mozzi.springproject.model.Board;
import mozzi.springproject.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
class BoardApiController {

    @Autowired
    private BoardRepository repository;

    // 게시글 검색
    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false, defaultValue="") String title, @RequestParam(required = false, defaultValue="") String content) {
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            return repository.findAll(); // 둘다 빈값이면 전체 데이터 조회
        } else{
            return repository.findByTitleOrContent(title, content);
        }
    }

    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {
        return repository.findById(id)
                .map(board -> { // 새로운 값 세팅
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}