package pizzashop.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PizzaServiceTest {
    @Mock
    private PaymentRepository paymentRepositoryMock;
    @InjectMocks
    private PizzaService pizzaServiceMock;
    private PaymentRepository paymentRepository;
    private PizzaService pizzaService;

    @BeforeEach
    void setUp() {
        MenuRepository menuRepository = new MenuRepository();
        paymentRepository = new PaymentRepository();
        pizzaService = new PizzaService(menuRepository, paymentRepository);
    }

    @AfterEach
    void tearDown() {
        paymentRepository.clearAll();
    }

    @TestFactory
    @Tag(value = "invalidBVA")
    @DisplayName(value = "Add payment table = 0, amount = 0")
    @Order(value = 1)
    Iterable<DynamicTest> TC1_BVA() {
        int table = 0;
        PaymentType type = PaymentType.Card;
        double amount = 0;

        return Collections.singletonList(
                DynamicTest.dynamicTest("TC1_BVA", () -> assertThrows(IllegalArgumentException.class,
                        () -> pizzaService.addPayment(table, type, amount)))
        );
    }

    @Test
    @Tag(value = "invalidBVA")
    @DisplayName(value = "Add payment table = 9, amount = 0")
    @Order(value = 2)
    void TC2_BVA() {
        int table = 9;
        PaymentType type = PaymentType.Cash;
        double amount = 0;

        assertThrows(IllegalArgumentException.class, () -> pizzaService.addPayment(table, type, amount));
    }

    @Test
    @Tag(value = "validBVA")
    @DisplayName(value = "Add payment table = 8, amount = Double.MAX_VALUE")
    @Order(value = 3)
    @Timeout(value = 1000)
    void TC3_BVA() {
        int table = 8;
        double amount = Double.MAX_VALUE;
        PaymentType type = PaymentType.Card;

        assertDoesNotThrow(() -> pizzaService.addPayment(table, type, amount));

        assertTrue(checkPaymentWasAdded(table, type, amount));
    }

    @Test
    @Tag(value = "validBVA")
    @DisplayName(value = "Add payment table = 1, amount = 1")
    @Order(value = 4)
    @Timeout(value = 1000)
    void TC4_BVA() {
        int table = 1;
        double amount = 1;
        PaymentType type = PaymentType.Cash;

        assertDoesNotThrow(() -> pizzaService.addPayment(table, type, amount));

        assertTrue(checkPaymentWasAdded(table, type, amount));
    }

    @Test
    @Tag(value = "validECP")
    @DisplayName(value = "Add payment table = 3, type = Card")
    @Order(value = 5)
    @Timeout(value = 1000)
    void TC1_ECP() {
        int table = 3;
        double amount = 44;
        PaymentType type = PaymentType.Card;

        assertDoesNotThrow(() -> pizzaService.addPayment(table, type, amount));

        assertTrue(checkPaymentWasAdded(table, type, amount));
    }

    @Test
    @Tag(value = "validECP")
    @DisplayName(value = "Add payment table = 8, type = Cash")
    @Order(value = 6)
    @Timeout(value = 1000)
    void TC2_ECP() {
        int table = 8;
        double amount = 34;
        PaymentType type = PaymentType.Cash;

        assertDoesNotThrow(() -> pizzaService.addPayment(table, type, amount));

        assertTrue(checkPaymentWasAdded(table, type, amount));
    }

    @Test
    @Tag(value = "invalidECP")
    @DisplayName(value = "Add payment table = -1, type = Card")
    @Order(value = 7)
    void TC3_ECP() {
        int table = -1;
        PaymentType type = PaymentType.Card;
        double amount = 45;

        assertThrows(IllegalArgumentException.class, () -> pizzaService.addPayment(table, type, amount));
    }

    @Test
    @Tag(value = "invalidECP")
    @DisplayName(value = "Add payment table = 10, type = Cash")
    @Order(value = 8)
    void TC4_ECP() {
        int table = 10;
        PaymentType type = PaymentType.Cash;
        double amount = 45;

        assertThrows(IllegalArgumentException.class, () -> pizzaService.addPayment(table, type, amount));
    }

    @Test
    @Tag(value = "WBT")
    @DisplayName(value = "getTotalAmount, getPayments==null, type=Cash")
    @Order(value = 9)
    void TC01_WBT() {
        when(paymentRepositoryMock.getAll()).thenReturn(null);
        PaymentType type = PaymentType.Cash;
        assertEquals(0, pizzaServiceMock.getTotalAmount(type));
    }

    @Test
    @Tag(value = "WBT")
    @DisplayName(value = "getTotalAmount, getPayments==empty, type=Cash")
    @Order(value = 10)
    void TC02_WBT() {
        when(paymentRepositoryMock.getAll()).thenReturn(Collections.emptyList());
        PaymentType type = PaymentType.Cash;
        assertEquals(0, pizzaServiceMock.getTotalAmount(type));
    }

    @Test
    @Tag(value = "WBT")
    @DisplayName(value = "getTotalAmount, getPayments.size() == 1, type=Card")
    @Order(value = 11)
    void TC03_WBT() {
        when(paymentRepositoryMock.getAll()).thenReturn(List.of(
                new Payment(7, PaymentType.Card, 10))
        );
        PaymentType type = PaymentType.Card;
        assertEquals(10, pizzaServiceMock.getTotalAmount(type));
    }

    @Test
    @Tag(value = "WBT")
    @DisplayName(value = "getTotalAmount, getPayments.size() == 2, type=Card ")
    @Order(value = 12)
    void TC04_WBT() {
        when(paymentRepositoryMock.getAll()).thenReturn(List.of(
                new Payment(7, PaymentType.Card, 10),
                new Payment(4, PaymentType.Cash, 8))
        );
        PaymentType type = PaymentType.Card;
        assertEquals(10, pizzaServiceMock.getTotalAmount(type));
    }

    @Test
    @Tag(value = "WBT")
    @DisplayName(value = "getTotalAmount, getPayments.size() == 3, type=Card ")
    @Order(value = 13)
    void TC05_WBT() {
        when(paymentRepositoryMock.getAll()).thenReturn(List.of(
                new Payment(7, PaymentType.Card, 10),
                new Payment(4, PaymentType.Cash, 8),
                new Payment(5, PaymentType.Card, 7))
        );
        PaymentType type = PaymentType.Card;
        assertEquals(17, pizzaServiceMock.getTotalAmount(type));
    }

    @Test
    @Tag(value = "test_01_lab4")
    @DisplayName(value = "Check getPayments() method ")
    @Order(value = 14)
    void TC1() {
        List<Payment> payments = Arrays.asList(
                new Payment(7, PaymentType.Card, 10),
                new Payment(4, PaymentType.Cash, 8),
                new Payment(5, PaymentType.Card, 7));
        when(paymentRepositoryMock.getAll()).thenReturn(payments);

        assertTrue(pizzaServiceMock.getPayments().equals(payments));
    }

    @Test
    @Tag(value = "test_02_lab4")
    @DisplayName(value = "Check addPayment() method ")
    @Order(value = 14)
    void TC2() {
        int tableNo = 6;
        PaymentType paymentType = PaymentType.Cash;
        float amount = 45.9F;
        Payment payment = new Payment(tableNo, paymentType, amount);
        int initialSize = pizzaService.getPayments().size();
        System.out.println(initialSize);
        pizzaService.addPayment(tableNo, paymentType, amount);
        System.out.println(pizzaService.getPayments());
        assertTrue(initialSize == pizzaService.getPayments().size() - 1);
        assertTrue(pizzaService.getPayments().stream().anyMatch(
                x -> {
                    return x.getType().equals(paymentType) && x.getTableNumber() == tableNo && x.getAmount() == amount;
                }
        ));
    }

    private boolean checkPaymentWasAdded(int table, PaymentType type, double amount) {
        return paymentRepository.getAll()
                .stream()
                .anyMatch(payment -> payment.getAmount() == amount && payment.getType().equals(type) && payment.getTableNumber() == table);
    }
}