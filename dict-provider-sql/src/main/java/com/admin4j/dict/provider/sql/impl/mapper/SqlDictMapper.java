package com.admin4j.dict.provider.sql.impl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author andanyang
 * @since 2022/7/24 13:37
 */
@Mapper
public interface SqlDictMapper {

    @Select("<script>SELECT ${labelFiled} as `label` from ${dictType} " +
            "where ${codeFiled} = #{code}" +
            " <if test=\"whereSql !=null and whereSql !=\'\' \"> AND ${whereSql} </if>  LIMIT 1</script>")
    String dictLabel(@Param("dictType") String dictType, @Param("code") Object code,
                     @Param("codeFiled") String codeFiled, @Param("labelFiled") String labelFiled,
                     @Param("whereSql") String whereSql);


    @Select("<script>select ${labelFiled} as `label`, ${codeFiled} as `code` from ${dictType} " +
            "where ${codeFiled} in" +
            " <foreach collection=\"codes\" item=\"code\" separator=\",\" open=\"(\" close=\")\">" +
            "        #{code}" +
            "    </foreach>" +
            " <if test=\"whereSql !=null and whereSql !=\'\' \"> AND ${whereSql} </if></script>")

    @ResultType(HashMap.class)
    ArrayList<HashMap<String, Object>> dictLabels(@Param("dictType") String dictType, @Param("codes") Collection<Object> codes,
                                                  @Param("codeFiled") String codeFiled, @Param("labelFiled") String labelFiled,
                                                  @Param("whereSql") String whereSql);

    @Select("<script>SELECT ${codeFiled} as `code` from ${dictType} " +
            "where ${labelFiled} = #{label}" +
            " <if test=\"whereSql !=null and whereSql !=\'\' \"> AND ${whereSql} </if>  LIMIT 1</script>")
    Object dictCode(@Param("dictType") String dictType, @Param("label") Object label,
                    @Param("codeFiled") String codeFiled, @Param("labelFiled") String labelFiled,
                    @Param("whereSql") String whereSql);
}
