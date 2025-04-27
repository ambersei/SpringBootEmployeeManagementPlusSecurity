package ca.sheridancollege.houyue.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
	Long userId; 
	@NonNull 
	String email;
	@NonNull
	String encryptedPassword; 
	@NonNull 
	Boolean enable; 

}
