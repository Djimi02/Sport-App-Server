package com.example.project.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UUIDRepositoryTest {

    @Autowired
    private UUIDRepository uuidRepository;

    @Test
    void testGetGroupTypeByUUID() {
        String uuid = "072acce5-8b14-49ba-a33a-28cf0c8c347e";
        String groupTypeByUUID = uuidRepository.getGroupTypeByUUID(uuid);
        System.out.println("group type = " + groupTypeByUUID);
    }
}
