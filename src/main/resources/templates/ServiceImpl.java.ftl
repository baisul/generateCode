package ${packetName}.service.impl;

import ${packetName}.model.${modelName};
import ${packetName}.service.${serviceName};
import ${packetName}.mapper.${mapperName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ${serviceName}Impl implements ${serviceName} {
    @Autowired
    private ${mapperName} ${mapperName?uncap_first};

    public List<${modelName}> getAll${modelName}s() {
        return ${mapperName?uncap_first}.getAll${modelName}s();
    }
}