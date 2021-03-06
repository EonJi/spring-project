package mozzi.springproject.controller;

import mozzi.springproject.model.Board;
import mozzi.springproject.repository.BoardRepository;
import mozzi.springproject.service.BoardService;
import mozzi.springproject.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board") // board라는 경로 지정해줌
public class BoardController {

    @Autowired // dependency injection
    private BoardRepository boardRepository; //interface 추가

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable, @RequestParam(required = false, defaultValue="") String searchText){
//        Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = 1;
        int endPage = boards.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
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
    public String boardSubmit(@Valid Board board, BindingResult bindingResult, Authentication authentication){
        boardValidator.validate(board, bindingResult);
        if(bindingResult.hasErrors()){
            return "board/form";
        }
        // Authentication a = SecurityContextHolder.getContext().getAuthentication();
        // 서비스 클래스나 컨트롤러 외에 스프링에서 관리해주는 클래스에서 인증정보 가지고 오고 싶은 경우
        String username = authentication.getName(); // Authentication을 컨트롤러 파라메터에 담아서 getName()해도 됨 (사용자 정보를 가져온다)
        boardService.save(username, board);
//        boardRepository.save(board);
        return "redirect:/board/list";
    }


}
