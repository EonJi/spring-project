package mozzi.springproject.controller;

import mozzi.springproject.model.Board;
import mozzi.springproject.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board") // board라는 경로 지정해줌
public class BoardController {

    @Autowired // dependency injection
    private BoardRepository boardRepository; //interface 추가

    @GetMapping("/list")
    public String list(Model model){
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        //모델에 담긴 데이터들은 타임리프에서 사용가능하니까 boards라는 키값에 boards를 준다
        return "board/list";
    }
    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id == null){
            model.addAttribute("board", new Board());
        } else {
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String boardSubmit(@ModelAttribute Board board){
        boardRepository.save(board);
        return "redirect:/board/list";
    }


}
