package propets.kafka.searcher.service;

import java.util.List;

import propets.kafka.searcher.dto.PostSearchDto;

public interface SearcherService {
	
	List<String> getEmailList(PostSearchDto postSearchDto);

}
