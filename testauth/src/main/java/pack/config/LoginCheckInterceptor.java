package pack.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import pack.service.JwtService;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod hm = (HandlerMethod) handler;
        LoginCheck loginCheck = hm.getMethodAnnotation(LoginCheck.class);

        // LoginCheck 어노테이션이 없으면 로그인 인증 필요 X -> 바로 컨트롤러로
        if(loginCheck == null){
            return true;
        }

        // LoginCheck 어노테이션이 있으면 로그인 했는지 체크 -> 토큰 있는지 체크
        String token = request.getHeader("Authorization");
        if(token == null || !token.startsWith("Bearer ")){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

//        // LoginCheck 어노테이션 && 토큰 존재
//        token = token.replace("Bearer ", "");
//        System.out.println("token : " + token);
//        String userId = "";
//
//        // 유효성 검사 생략
//        userId = jwtService.getUserFromToken(token);
//        System.out.println("userId : " + userId);
//        request.setAttribute("userId", userId);

        //
        return true;

    }
}