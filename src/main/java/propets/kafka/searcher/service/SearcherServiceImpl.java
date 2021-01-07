package propets.kafka.searcher.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import propets.kafka.searcher.dao.FoundsRepository;
import propets.kafka.searcher.dao.LostsRepository;
import propets.kafka.searcher.dto.EmailListAndPostDto;
import propets.kafka.searcher.dto.PostSearchDto;
import propets.kafka.searcher.model.FoundPostSearch;
import propets.kafka.searcher.model.LostPostSearch;

@Service
public class SearcherServiceImpl implements SearcherService {

	@Autowired
	LostsRepository lostsRepository;

	@Autowired
	FoundsRepository foundsRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	EmailNotifGenerator emailNotifGenerator;

	@Override
	public List<String> getEmailList(PostSearchDto postSearchDto) {
		
		List<String> emails = new ArrayList<>();
				
		if (postSearchDto.isTypePost()) {
			FoundPostSearch foundPost = new FoundPostSearch(postSearchDto.getId(), postSearchDto.getUserLogin(),
					postSearchDto.getDatePost(), postSearchDto.getType(), 
					postSearchDto.getTags(),
					postSearchDto.getPhone(), postSearchDto.getEmail());

			foundsRepository.save(foundPost);
			
		} else {
			LostPostSearch lostPost = new LostPostSearch(postSearchDto.getId(), postSearchDto.getUserLogin(),
					postSearchDto.getDatePost(), postSearchDto.getType(), 
					postSearchDto.getTags(),
					postSearchDto.getPhone(), postSearchDto.getEmail());

			lostsRepository.save(lostPost);

		}
		
		emails = findPreviousPosts(postSearchDto);
		
		try {
			System.out.println("send to method kafka: " + postSearchDto.getId());			
			EmailListAndPostDto emailListAndPostDto = 
					new EmailListAndPostDto(emails, postSearchDto.getId());
			
			emailNotifGenerator.sendToEmailNotification(emailListAndPostDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return emails;
	}
	
	private List<String> findPreviousPosts(PostSearchDto postSearchDto) {
		Set<String> emails = new HashSet<>();
				
		Query query = new Query();
		
		query.addCriteria(Criteria.where("type").is(postSearchDto.getType()));
				
		Set<String> tags = postSearchDto.getTags();
		if(!tags.isEmpty()) {
			query.addCriteria(Criteria.where("tags").in(tags));
		}
		
		System.out.println(query.toString());
				
		if(postSearchDto.isTypePost()) {
			List<LostPostSearch> posts = new ArrayList<>();
			posts = mongoTemplate.find(query, LostPostSearch.class);
			
			emails = posts.stream()
						.filter(p -> ifMatchTags(tags, p.getTags()))
						.map(p -> p.getEmail())
						.collect(Collectors.toSet());
			
		} else {
			List<FoundPostSearch> posts = new ArrayList<>();
			posts = mongoTemplate.find(query, FoundPostSearch.class);
			
			emails = posts.stream()
					.filter(p -> ifMatchTags(tags, p.getTags()))
					.map(p -> p.getEmail())
					.collect(Collectors.toSet());
		}
				
		return new ArrayList<String>(emails);
	}

	private boolean ifMatchTags(Set<String> tags, Set<String> baseTags) {
		int sizeBaseTags = baseTags.size();
		int countMatch = 0;
		
		for(String tag : baseTags) {
			if(tags.contains(tag)) {
				countMatch++;
			}
		}
		
		return (countMatch/(sizeBaseTags * 1.) ) >= 0.6 ? true : false;
	}

}
