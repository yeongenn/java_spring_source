package pack.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pack.dto.UserDto;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class User {

    @Id
    @Column(name="user_id")
    private String id;
    private String password, email;

    public static UserDto toDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .password(user.getPassword())
                .email(user.getEmail())
                .build();
    }

    public void changePassword(String password){
        this.password = password;
    }

    public void changeEmail(String email){
        this.email = email;
    }
}