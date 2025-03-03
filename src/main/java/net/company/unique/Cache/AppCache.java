package net.company.unique.Cache;

import jakarta.annotation.PostConstruct;
import net.company.unique.Entity.configEntity;
import net.company.unique.Repository.ConfigRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache
{
    private ConfigRepository configRepository;
    public AppCache(ConfigRepository configRepository)
    {
        this.configRepository = configRepository;
    }

  public Map<String,String> appCache = new HashMap<>();

@PostConstruct
public void init()
{
    List<configEntity> alllist = configRepository.findAll();
    for (configEntity config : alllist)
    {
        appCache.put(config.getKey(),config.getValue());
    }
}
}
