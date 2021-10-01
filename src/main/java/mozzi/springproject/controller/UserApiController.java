package mozzi.springproject.controller;
import lombok.extern.slf4j.Slf4j;
import mozzi.springproject.model.Board;
import mozzi.springproject.model.User;
import mozzi.springproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
class UserApiController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/users")
    List<User> all() {
        List<User> users = repository.findAll();// get을 통해 데이터 조회
        log.debug("getBoards().size() 호출 전");
        log.debug("getBoards().size() : {}", users.get(0).getBoards().size()); // log찍어볼 수 있다
//        users.get(0).getBoards().size(); // 첫번째 users에 getBoards()를 호출하게 되면, size()와 같은 함수 넣어주면 데이터 호출(첫번째 users에 해당하는 그 보드에 대한 정보를 가져온다)
        log.debug("getBoards().size() 호출 후"); // log찍어볼 수 있다
        return users;
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    @GetMapping("/users/{id}") // id에 해당하는 사용자만 나온다
    User one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    } // 하나의 데이터만 가져온다

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return repository.findById(id)
                .map(user -> {
//                    user.setTitle(newUser.getTitle());
//                    user.setContent(newUser.getContent());
//                    user.setBoards(newUser.getBoards());
                    user.getBoards().clear();
                    user.getBoards().addAll(newUser.getBoards());
                    for(Board board : user.getBoards()){
                        board.setUser(user);
                    }
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}