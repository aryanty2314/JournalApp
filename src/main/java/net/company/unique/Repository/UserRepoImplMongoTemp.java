package net.company.unique.Repository;

import net.company.unique.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepoImplMongoTemp
{

    private MongoTemplate mongoTemplate;

public UserRepoImplMongoTemp(MongoTemplate mongoTemplate)
{
    this.mongoTemplate = mongoTemplate;
}


public List<User> getall()
{
    Query query = new Query();
    query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
    query.addCriteria(Criteria.where("sentimentalAnalysis").is(true));
    List<User> userList = mongoTemplate.find(query, User.class);
    return userList;
}
}
