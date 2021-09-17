<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packetName}.mapper.${mapperName}">

    <resultMap id="BaseResultMap" type="${packetName}.model.${modelName}">
        <#list columns as column>
            <<#if column.primary??>id<#else>result</#if> column="${column.columnName}" property="${column.propertyName?uncap_first}" jdbcType="<#if column.type='INT'>INTEGER<#elseif column.type='DATETIME'>TIMESTAMP<#elseif column.type='text'>VARCHAR<#else>${column.type}</#if>"/>
        </#list>
    </resultMap>

    <select id="getAll${modelName}s" resultMap="BaseResultMap">
        select * from ${tableName};
    </select>
</mapper>
