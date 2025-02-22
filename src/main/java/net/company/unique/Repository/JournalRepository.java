package net.company.unique.Repository;

import net.company.unique.Entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface JournalRepository extends MongoRepository<JournalEntry, ObjectId>
{
    // You can define custom query methods here if needed
}