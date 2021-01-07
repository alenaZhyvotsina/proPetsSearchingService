package propets.kafka.searcher.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import propets.kafka.searcher.model.LostPostSearch;

public interface LostsRepository extends MongoRepository<LostPostSearch, String> {

}
