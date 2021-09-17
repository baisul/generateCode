package com.yl.generate_code.controller;

import com.yl.generate_code.model.ResultModel;
import com.yl.generate_code.model.TableClass;
import com.yl.generate_code.service.GenerateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class GenerateCodeController {
    @Autowired
    private GenerateCodeService generateCodeService;

    @PostMapping("/generateCode")
    public ResultModel generateCode(@RequestBody List<TableClass> list, HttpServletRequest request) {
        return generateCodeService.generateCode(list,request.getServletContext().getRealPath("/"));
    }
}
