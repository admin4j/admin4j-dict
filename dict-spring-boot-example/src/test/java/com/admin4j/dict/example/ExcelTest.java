package com.admin4j.dict.example;

import com.admin4j.dict.example.mapper.ConsumerMapper;
import com.admin4j.dict.example.model.Consumer;
import com.admin4j.framework.excel.ExcelUtils;
import com.admin4j.oss.MockMultipartFile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author andanyang
 * @since 2022/10/26 14:41
 */
@SpringBootTest
public class ExcelTest {

    @Autowired
    ConsumerMapper consumerMapper;

    @Test
    public void read() throws Exception {


        FileInputStream fileInputStream = new FileInputStream("./dict.xlsx");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("consumers", fileInputStream);
        List<Consumer> consumers = ExcelUtils.read(mockMultipartFile, Consumer.class);
        System.out.println("consumers = " + consumers);
    }

    @Test
    public void write() throws Exception {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH_mm_ss");
        File file = new File("target/" + LocalDateTime.now().format(dateTimeFormatter) + ".xlsx");
        // file.deleteOnExit();
        boolean newFile = file.createNewFile();
        System.out.println("newFile = " + newFile);
        System.out.println("getAbsolutePath = " + file.getAbsolutePath());

        FileOutputStream fileOutputStream = new FileOutputStream(file);

        List<Consumer> list = consumerMapper.list();
        ExcelUtils.write(fileOutputStream, "dict", list, Consumer.class, false);

        fileOutputStream.close();
    }
}
