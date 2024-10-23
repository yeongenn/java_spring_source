package pack.service;

import jakarta.servlet.http.HttpSession;
import pack.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pack.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private HttpSession httpSession;

    public User findById(String id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("cannot find user using this id : " + id));

        return user;
    }

    public String login(String id, String password){
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("check your id"));

        if(!password.equals(user.getPassword())){
            throw new IllegalArgumentException("check your password");
        }

        // id 존재하고 id-password 일치하면 토큰 발행
        return jwtService.createToken(id);

    }

    public String getLoginUser(){
        String userId = (String) httpSession.getAttribute("user_id");
        if(userId == null) {
            throw new NoSuchElementException("no session");
        }

        return userId;
    }

}
