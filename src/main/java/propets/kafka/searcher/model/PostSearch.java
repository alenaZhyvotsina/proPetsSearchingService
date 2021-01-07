package propets.kafka.searcher.model;

import java.time.LocalDateTime;
import java.util.Set;

public class PostSearch {
	
	String id;  
	String userLogin;  
	LocalDateTime datePost;
	
	String type;
	
	Set<String> tags;
	
	String phone;
	String email;

}
