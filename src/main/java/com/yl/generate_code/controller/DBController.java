package com.yl.generate_code.controller;

import com.google.common.base.CaseFormat;
import com.yl.generate_code.model.Db;
import com.yl.generate_code.model.ResultModel;
import com.yl.generate_code.model.TableClass;
import com.yl.generate_code.utils.DBUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class DBController {

    @PostMapping("/connect")
    public ResultModel connect(@RequestBody Db db) {
        Connection connection = DBUtils.init(db);
        if (connection == null) {
            return ResultModel.fail("数据库连接失败");
        } else {
            return ResultModel.success("数据库连接成功");
        }
    }

    @PostMapping("/config")
    public ResultModel config(@RequestBody Map<String,Object> map) {
        String packetName = (String)map.get("packetName");
        try {
            //获取数据库连接
            Connection connection = DBUtils.getConnection();
            //获取数据库元数据
            DatabaseMetaData metaData = connection.getMetaData();
            //获取数据库所有的表
            ResultSet rs = metaData.getTables(connection.getCatalog(), null, null, null);
            List<TableClass> list = new ArrayList<>();
            while (rs.next()) {
                TableClass tableClass = new TableClass();
                tableClass.setPacketName(packetName);
                //获取表名
                String tableName = rs.getString("TABLE_NAME");
                //数据库表下划线的字段转成驼峰，且首字母大写
                String modelName = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
                tableClass.setTableName(tableName);
                tableClass.setModelName(modelName);
                tableClass.setServiceName(modelName + "Service");
                tableClass.setMapperName(modelName + "Mapper");
                tableClass.setControllerName(modelName + "Controller");
                list.add(tableClass);
            }
            return ResultModel.success("数据库信息读取成功",list);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResultModel.fail("数据库信息读取失败");
        }
    }
}
