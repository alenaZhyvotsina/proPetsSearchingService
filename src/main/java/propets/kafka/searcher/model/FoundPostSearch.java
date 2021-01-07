package propets.kafka.searcher.model;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "founds")
public class FoundPostSearch {
	@Id
	String id;  
	String userLogin;  
	LocalDateTime datePost;
	
	String type;
	
	Set<String> tags;
	
	String phone;
	String email;

}
