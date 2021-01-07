package propets.kafka.searcher.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import propets.kafka.searcher.dto.PostSearchDto;
import propets.kafka.searcher.service.SearcherService;

@RestController
@RequestMapping("searcher")
public class SearchConroller {
	
	@Autowired
	SearcherService searcherService;
	
	@PostMapping
	public List<String> getEmailList(@RequestBody PostSearchDto postSearchDto){
		return searcherService.getEmailList(postSearchDto);
	}

}
