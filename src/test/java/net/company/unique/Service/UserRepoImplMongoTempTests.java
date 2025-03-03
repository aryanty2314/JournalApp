package net.company.unique.Service;


import net.company.unique.Repository.UserRepoImplMongoTemp;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepoImplMongoTempTests
{

    @Autowired
   private UserRepoImplMongoTemp userR;

    @Test
    public void userFindTest()
    {
        Assertions.assertNotNull(userR.getall());
    }


}

