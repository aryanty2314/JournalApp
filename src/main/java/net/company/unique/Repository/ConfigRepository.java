package net.company.unique.Repository;

import net.company.unique.Entity.configEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigRepository extends MongoRepository<configEntity,String>
{

}
