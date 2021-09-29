package mozzi.springproject.service;

import mozzi.springproject.model.Board;
import mozzi.springproject.model.User;
import mozzi.springproject.repository.BoardRepository;
import mozzi.springproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    public Board save(String username, Board board){
        User user = userRepository.findByUsername(username); // user정보를 가져온다 (username으로 검색)
        board.setUser(user); //board안에 user에 세팅해준다
        return boardRepository.save(board);
    }
}
