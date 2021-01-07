package propets.kafka.searcher.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import propets.kafka.searcher.model.FoundPostSearch;

public interface FoundsRepository extends MongoRepository<FoundPostSearch, String> {

}
