package pack.controller;

import org.springframework.web.bind.annotation.GetMapping;
import pack.config.CurrentUser;
import pack.config.LoginCheck;
import pack.dto.LoginResDto;
import pack.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pack.entity.User;
import pack.service.JwtService;
import pack.service.UserService;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping(value="/login", produces = "application/json; charset=utf8")
    public ResponseEntity<LoginResDto> userLogin(@RequestBody UserDto userDto){

        try {
            String token = userService.login(userDto.getId(), userDto.getPassword());

            return ResponseEntity.ok(new LoginResDto(token, null));
        } catch (IllegalArgumentException e){
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

    @LoginCheck
    @GetMapping("/mypage")
    public ResponseEntity<?> mypage(@CurrentUser String userId){
        try {
            UserDto userDto = User.toDto(userService.findById(userId));
            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
