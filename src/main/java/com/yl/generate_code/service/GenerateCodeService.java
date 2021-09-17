package com.yl.generate_code.service;

import com.yl.generate_code.model.ResultModel;
import com.yl.generate_code.model.TableClass;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface GenerateCodeService {
    ResultModel generateCode(List<TableClass> list, String realpath);
}
