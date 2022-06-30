package pizzashop.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Payment payment;
    private static int tableNumber = 5;
    private static PaymentType paymentType = PaymentType.Cash;
    private static float amount = 23.5F;
    @BeforeEach
    void setUp() {
        payment = new Payment(tableNumber, paymentType, amount);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Tag(value = "test_01")
    @DisplayName(value = "Check getTableNumber() method")
    @Order(value = 1)
    void TC1() {
        assertTrue(payment.getTableNumber() == tableNumber);
    }

    @Test
    @Tag(value = "test_02")
    @DisplayName(value = "Check setTableNumber() method")
    @Order(value = 2)
    void TC2() {
        payment.setTableNumber(10);
        assertTrue(payment.getTableNumber() == 10);
    }

    @Test
    @Tag(value = "test_03")
    @DisplayName(value = "Check getType() method")
    @Order(value = 3)
    void TC3() {
        assertTrue(payment.getType() == paymentType);
    }

}