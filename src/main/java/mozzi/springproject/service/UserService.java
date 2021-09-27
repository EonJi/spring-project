package mozzi.springproject.service;

import mozzi.springproject.model.Role;
import mozzi.springproject.model.User;
import mozzi.springproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        Role role = new Role();
        role.setId(1l);
        user.getRoles().add(role);
        return userRepository.save(user);
    }
//    public User save(User user){
//        String encodedPassword = passwordEncoder.encode(user.getPassword()); //사용자가 전달한 비밀번호를 encoder를 이용해서 encode하면 암호화됨
//        user.setPassword(encodedPassword);
//        user.setEnabled(true);
//        Role role = new Role();
//        role.setId(1L);
//        user.getRoles().add(role);//Roles에 어떤 권한을 줄건지 저장 가능
//        return userRepository.save(user);
//    }

}
