package pack.config;

import pack.entity.User;

public class AuthenticationContextHolder {
    private static final ThreadLocal<User> context = new ThreadLocal<>();
    private AuthenticationContextHolder(){

    }

    public static User getContext(){
        return context.get();
    }

    public static void setContext(User user){
        context.set(user);
    }

    public static void clear(){
        context.remove();
    }
}
