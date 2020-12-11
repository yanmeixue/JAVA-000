package pers.ryan.xa;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pers.ryan.xa.service.XAOrderService;

import java.io.IOException;
import java.sql.SQLException;

public class XAOrderServiceTest {

    private XAOrderService orderService;

    @BeforeEach
    public void setUp() throws IOException, SQLException {
        orderService = new XAOrderService("application.yml");
        orderService.init();
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        // orderService.cleanup();
    }

    @Test
    public void assertInsertSuccess() throws SQLException {
        orderService.insert();
        Assertions.assertEquals(orderService.selectAll(), 20);
    }

    @Test
    public void assertInsertFailed() throws SQLException {
        try {
            orderService.insertFailed();
        } catch (final Exception ignore) {
        }
        Assertions.assertEquals(orderService.selectAll(), 0);
    }
}
