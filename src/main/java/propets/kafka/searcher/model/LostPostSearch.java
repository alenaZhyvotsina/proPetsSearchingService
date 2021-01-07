package propets.kafka.searcher.model;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "losts")
public class LostPostSearch {
	@Id
	String id;  
	String userLogin;  
	LocalDateTime datePost;
	
	String type;
	//String sex;
	//String breed;	
	//String color;
	
	Set<String> tags;
	
	String phone;
	String email;

}
