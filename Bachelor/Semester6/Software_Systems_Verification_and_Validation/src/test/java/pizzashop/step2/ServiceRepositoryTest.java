package pizzashop.step2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import pizzashop.model.Payment;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServiceRepositoryTest {

    private PaymentRepository paymentRepository;
    private PizzaService pizzaService;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        pizzaService = new PizzaService(null, paymentRepository);
    }

    @AfterEach
    void tearDown() {
        paymentRepository.clearAll();
    }

    @Test
    @Order(1)
    void testAdd() throws Exception {
        Payment p1 = Mockito.mock(Payment.class);
        Mockito.when(p1.getTableNumber()).thenReturn(1);
        Mockito.when(p1.toString()).thenReturn("1,Cash,3.0");
        int size = pizzaService.getPayments().size();

        pizzaService.addPayment(p1);

        assertEquals(size + 1, pizzaService.getPayments().size());
        assertEquals(pizzaService.getPayments().get(size), p1);
        Mockito.verify(p1, Mockito.times(1)).getTableNumber();
        assertEquals(pizzaService.getPayments().get(size).getTableNumber(), 1);
        Mockito.verify(p1, Mockito.times(2)).getTableNumber();
    }

    @Test
    @Order(2)
    void testGetAll() throws Exception {
        Payment p1 = Mockito.mock(Payment.class);
        Payment p2 = Mockito.mock(Payment.class);
        Payment p3 = Mockito.mock(Payment.class);

        Mockito.when(p1.getTableNumber()).thenReturn(1);
        Mockito.when(p2.getTableNumber()).thenReturn(2);
        Mockito.when(p3.getTableNumber()).thenReturn(3);

        Mockito.when(p1.toString()).thenReturn("1,Card,19.2");
        Mockito.when(p2.toString()).thenReturn("2,Cash,200.3");
        Mockito.when(p3.toString()).thenReturn("3,Card,123.4");

        pizzaService.addPayment(p1);
        pizzaService.addPayment(p1);
        pizzaService.addPayment(p2);
        pizzaService.addPayment(p2);
        pizzaService.addPayment(p3);
        pizzaService.addPayment(p3);

        assertEquals(6, pizzaService.getPayments().size());
        assertEquals(pizzaService.getPayments().get(0), p1);
        assertEquals(pizzaService.getPayments().get(1), p1);
        assertEquals(pizzaService.getPayments().get(0).getTableNumber(), 1);
        assertEquals(pizzaService.getPayments().get(1).getTableNumber(), 1);
        assertEquals(pizzaService.getPayments().get(2), p2);
        assertEquals(pizzaService.getPayments().get(3), p2);
        assertEquals(pizzaService.getPayments().get(2).getTableNumber(), 2);
        assertEquals(pizzaService.getPayments().get(3).getTableNumber(), 2);
        assertEquals(pizzaService.getPayments().get(4), p3);
        assertEquals(pizzaService.getPayments().get(5), p3);
        assertEquals(pizzaService.getPayments().get(4).getTableNumber(), 3);
        assertEquals(pizzaService.getPayments().get(5).getTableNumber(), 3);
        Mockito.verify(p1, Mockito.times(4)).getTableNumber();
        Mockito.verify(p2, Mockito.times(4)).getTableNumber();
        Mockito.verify(p3, Mockito.times(4)).getTableNumber();
    }
}