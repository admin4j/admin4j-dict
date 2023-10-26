package com.admin4j.dict.example.json.controller;

import com.admin4j.dict.anno.DictCache;
import com.admin4j.dict.example.mapper.ConsumerMapper;
import com.admin4j.dict.example.model.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author andanyang
 * @since 2022/10/26 9:34
 */
@RestController
@RequestMapping("json")
public class JSONController {

    /**
     * 这里只做测试，省去service层
     */
    @Autowired
    private ConsumerMapper consumerMapper;
    // @Autowired
    // ObjectMapper objectMapper;
    // BeanPropertyWriter

    @GetMapping
    @DictCache(Consumer.class)
    public List<Consumer> consumers() {
        // objectMapper.registerModule()
        List<Consumer> consumers = consumerMapper.list();
        return consumers;
    }
}
