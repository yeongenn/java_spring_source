package pack.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.*;
import pack.config.AuthenticationContextHolder;
import pack.config.CurrentUser;
import pack.config.LoginCheck;
import pack.dto.LoginResDto;
import pack.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pack.entity.User;
import pack.repository.UserRepository;
import pack.service.JwtService;
import pack.service.UserService;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/login", produces = "application/json; charset=utf8")
    public ResponseEntity<LoginResDto> userLogin(@RequestBody UserDto userDto) {

        try {
            String token = userService.login(userDto.getId(), userDto.getPassword());

            return ResponseEntity.ok(new LoginResDto(token, null));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LoginResDto(null, e.getMessage()));
        }

    }

//    @LoginCheck
//    @GetMapping("/mypage")
//    public ResponseEntity<?> mypage(HttpServletRequest request){
//        String userId = request.getAttribute("userId").toString();
//
//        try {
//            UserDto userDto = User.toDto(userService.findById(userId));
//            return ResponseEntity.ok(userDto);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

//    @LoginCheck
//    @GetMapping("/mypage")
//    public ResponseEntity<?> mypage(@CurrentUser String userId){
//        try {
//            UserDto userDto = User.toDto(userService.findById(userId));
//            return ResponseEntity.ok(userDto);
//        } catch (IllegalArgumentException e){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

    @LoginCheck
    @GetMapping("/mypage")
    public ResponseEntity<?> mypage() {
        try {
            // AuthenticationContextHolder에 User 전체를 저장했을 때
//            final UserDto userDto = User.toDto(AuthenticationContextHolder.getContext());

            // AuthenticationContextHolder에 userId만 저장
            final UserDto userDto = User.toDto(userService.findById(AuthenticationContextHolder.getContext()));
            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @LoginCheck
    @PutMapping("/mypage")
    public ResponseEntity<?> updateMyInfo(@RequestBody UserDto userDto) {
        // JWT에 페이로드 필드에 user 정보 어디까지 저장하느냐에 따라 로직 달라짐
        // 현재는 JWT에 사용자 Id만 O

        try {
            // AuthenticationContextHolder에 User 전체를 저장했을 때
//            UserDto updatedUser = User.toDto(userService.updateMyInfo(AuthenticationContextHolder.getContext().getId(), userDto));

            // AuthenticationContextHolder에 userId만 저장
            UserDto updatedUser = User.toDto(userService.updateMyInfo(AuthenticationContextHolder.getContext(), userDto));
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
