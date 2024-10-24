package pack.config;

import pack.entity.User;

public class AuthenticationContextHolder {
    private static final ThreadLocal<String> context = new ThreadLocal<>();
    private AuthenticationContextHolder(){

    }

//    public static User getContext(){
//        return context.get();
//    }
//
//    public static void setContext(User user){
//        context.set(user);
//    }

    public static String getContext(){
        return context.get();
    }

    public static void setContext(String id){
        context.set(id);
    }

    public static void clear(){
        context.remove();
    }
}
