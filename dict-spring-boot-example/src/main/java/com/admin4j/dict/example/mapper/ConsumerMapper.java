package com.admin4j.dict.example.mapper;

import com.admin4j.dict.example.model.Consumer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author andanyang
 * @since 2022/10/26 10:14
 */
@Mapper
public interface ConsumerMapper {
    List<Consumer> list();
}
