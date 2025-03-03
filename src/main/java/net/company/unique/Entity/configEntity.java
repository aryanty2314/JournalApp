package net.company.unique.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_api")
@Data
@NoArgsConstructor
public class configEntity
{
private String key;
private String value;
}
