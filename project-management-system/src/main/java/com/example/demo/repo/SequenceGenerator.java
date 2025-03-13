package com.example.demo.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Query.query;
import java.util.Objects;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.stereotype.Service;

import com.example.demo.model.Sequence;

@Service
public class SequenceGenerator {

	private MongoOperations mongoOperations;
	
	@Autowired
	public SequenceGenerator(MongoOperations mongoOperations) {
		this.mongoOperations=mongoOperations;
	}
	
	public long generateSequenceforProject()
	{
		Sequence counter=mongoOperations.findAndModify(query(where("name").is("projectStart")),
				new Update().inc("counter",1), options().returnNew(true).upsert(true),
				Sequence.class);
		return !Objects.isNull(counter)? counter.getCounter():1000;
	}

	public long generateSequenceforTestcase()
	{
		Sequence counter=mongoOperations.findAndModify(query(where("name").is("testcaseStart")),
				new Update().inc("counter",1), options().returnNew(true).upsert(true),
				Sequence.class);
		return !Objects.isNull(counter)? counter.getCounter():1000;
	}
	
	public long generateRoleSequence()
	{
		Sequence counter=mongoOperations.findAndModify(query(where("name").is("roleStart")),
				new Update().inc("counter",1), options().returnNew(true).upsert(true),
				Sequence.class);
		return !Objects.isNull(counter)? counter.getCounter():1000;
	}
	
	public long generateUserSequence()
	{
		Sequence counter=mongoOperations.findAndModify(query(where("name").is("userStart")),
				new Update().inc("counter",1), options().returnNew(true).upsert(true),
				Sequence.class);
		return !Objects.isNull(counter)? counter.getCounter():1000;
	}

	public long generateDefectSequence() {
		Sequence counter=mongoOperations.findAndModify(query(where("name").is("defectStart")),
				new Update().inc("counter",1), options().returnNew(true).upsert(true),
				Sequence.class);
		return !Objects.isNull(counter)? counter.getCounter():1000;
	}
	
}