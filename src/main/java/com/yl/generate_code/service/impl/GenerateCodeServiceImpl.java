package com.yl.generate_code.service.impl;

import com.google.common.base.CaseFormat;
import com.yl.generate_code.model.ColumnClass;
import com.yl.generate_code.model.ResultModel;
import com.yl.generate_code.model.TableClass;
import com.yl.generate_code.service.GenerateCodeService;
import com.yl.generate_code.utils.DBUtils;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GenerateCodeServiceImpl implements GenerateCodeService {

    Configuration cfg = null;
    {
        cfg = new Configuration(Configuration.VERSION_2_3_31);
        cfg.setTemplateLoader(new ClassTemplateLoader(GenerateCodeServiceImpl.class,"/templates"));
        cfg.setDefaultEncoding("utf-8");

    }

    @Override
    public ResultModel generateCode(List<TableClass> list, String realpath) {
        try {
            Template modelTemplate = cfg.getTemplate("Model.java.ftl");
            Template serviceTemplate = cfg.getTemplate("Service.java.ftl");
            Template serviceImplTemplate = cfg.getTemplate("ServiceImpl.java.ftl");
            Template mapperTemplate = cfg.getTemplate("Mapper.java.ftl");
            Template mapperXmlTemplate = cfg.getTemplate("Mapper.xml.java.ftl");
            Template controllerTemplate = cfg.getTemplate("Controller.java.ftl");
            Connection connection = DBUtils.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            for (TableClass tableClass : list) {
                //根据表名获取该表的所有字段
                ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableClass.getTableName(), null);
                //获取该表的所有主键
                ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null, tableClass.getTableName());
                List<ColumnClass> columnClasses = new ArrayList<>();
                while (columns.next()) {
                    //获取字段名
                    String column_name = columns.getString("COLUMN_NAME");
                    //获取字段类型
                    String type_name = columns.getString("TYPE_NAME");
                    //获取字段注释
                    String remark = columns.getString("REMARKS");
                    ColumnClass columnClass = new ColumnClass();
                    columnClass.setColumnName(column_name);
                    columnClass.setType(type_name);
                    columnClass.setRemark(remark);
                    columnClass.setPropertyName(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,column_name));
                    //指标挪到第一
                    primaryKeys.first();
                    while (primaryKeys.next()) {
                        String primaryKey = primaryKeys.getString("COLUMN_NAME");
                        if (column_name.equals(primaryKey)) {
                            columnClass.setPrimary(true);
                        }
                    }
                    columnClasses.add(columnClass);
                }
                tableClass.setColumns(columnClasses);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                tableClass.setCreateDate(sdf.format(new Date()));
                String path = realpath + "/" + tableClass.getPacketName().replace(".","/");
                generate(modelTemplate,tableClass,path+"/model/",1);
                generate(serviceTemplate,tableClass,path+"/service/",1);
                generate(serviceImplTemplate,tableClass,path+"/service/impl",1);
                generate(mapperTemplate,tableClass,path+"/mapper/",1);
                generate(mapperXmlTemplate,tableClass,path+"/mapper/",2);
                generate(controllerTemplate,tableClass,path+"/controller/",1);
            }
            return ResultModel.success("代码已生成",realpath);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultModel.fail("代码生成失败");
        }
    }

    private void generate(Template template,TableClass tableClass,String path,Integer flag) throws IOException, TemplateException {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName;
        if (flag == 1) {
            fileName = path + "/" + tableClass.getModelName() + template.getName().replace(".ftl","").replace("Model","");
        } else {
            fileName = path + "/" + tableClass.getModelName() + template.getName().replace(".ftl","").replace(".java","");
        }
        FileOutputStream fos = new FileOutputStream(fileName);
        OutputStreamWriter  out = new OutputStreamWriter(fos);
        template.process(tableClass,out);
        fos.close();
        out.close();
    }
}
