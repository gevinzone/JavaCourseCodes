package com.gevinzone.homework0801;

import com.gevinzone.homework0801.utils.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class Homework0801ApplicationTests {

    @Autowired
    IdGenerator idGenerator;

    @Test
    void contextLoads() {
    }

    @Test
    void checkIdValue(){
        log.info(String.valueOf(idGenerator.nextId()));
    }


}
