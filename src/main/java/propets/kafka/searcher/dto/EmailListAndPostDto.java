package propets.kafka.searcher.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class EmailListAndPostDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1478262352632554297L;
	/**
	 * 
	 */
	
	List<String> emails;
	String postId;

}
