package za.ac.cput.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Tips;
import za.ac.cput.factory.TipsFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TipsServiceTest {

    private Tips tips1;
    private Tips tips2;
    private Tips tips3;

    @Autowired
    private TipsService service;

    @BeforeEach
    void setUp() {

        tips1 = TipsFactory.buildTips(1, new byte[]{1, 2, 3});
        tips2 = TipsFactory.buildTips(2, new byte[]{4, 5, 6});
        tips3 = TipsFactory.buildTips(3, new byte[]{7, 8, 9});
    }

    @Test
    void create() {
        Tips createdTips1 = service.create(tips1);
        assertNotNull(createdTips1);
        assertEquals(tips1.getId(), createdTips1.getId());
        System.out.println(createdTips1);
    }

    @Test
    void read() {
        Tips createdTips1 = service.create(tips1);
        Tips readTips = service.read(createdTips1.getId());
        assertNotNull(readTips);
        assertEquals(tips1.getId(), readTips.getId());
        System.out.println(readTips);
    }



    @Test
    @Order(4)
    void getall() {
        System.out.println(service.getall());
    }
}
