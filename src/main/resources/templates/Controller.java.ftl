package ${packetName}.controller;

import ${packetName}.model.${modelName};
import ${packetName}.service.${serviceName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/${modelName?uncap_first}")
public class ${controllerName} {
    @Autowired
    private ${serviceName} ${serviceName?uncap_first};

    @GetMapping("/getAll${modelName}s")
    public List<${modelName}> getAll${modelName}s() {
        return ${serviceName?uncap_first}.getAll${modelName}s();
    }
}