package mozzi.springproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board") // board라는 경로 지정해줌
public class BoardController {
    @GetMapping("/list")
    public String list(){
        return "board/list";
    }
}
