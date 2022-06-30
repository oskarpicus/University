package pizzashop.repository;

import org.junit.jupiter.api.*;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private static String filename =  "data/payments.txt";
    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
    }

    @AfterEach
    void tearDown() {
        paymentRepository.clearAll();
    }

    @Test
    @Tag(value = "test_01")
    @DisplayName(value = "Check add() method")
    @Order(value = 1)
    void TC1() {
        int tableNo = 6;
        PaymentType paymentType = PaymentType.Cash;
        float amount = 45.9F;
        Payment payment = new Payment(tableNo, paymentType, amount);
        paymentRepository.add(payment);

        assertEquals(paymentRepository.getAll().size(), this.readPaymentsFromFile().size());
        assertTrue(paymentRepository.getAll().contains(payment));
        assertTrue(readPaymentsFromFile().stream().anyMatch(x-> {
            return x.getType().equals(payment.getType()) && x.getTableNumber() == payment.getTableNumber() && x.getAmount() == payment.getAmount();
        }));
    }

    @Test
    @Tag(value = "test_02")
    @DisplayName(value = "Check clearAll() method")
    @Order(value = 2)
    void TC2() {
        int tableNo = 1;
        PaymentType paymentType = PaymentType.Cash;
        float amount = 0F;
        Payment payment = new Payment(tableNo, paymentType, amount);
        paymentRepository.add(payment);

        assertTrue(this.readPaymentsFromFile().size()!=0);
        paymentRepository.clearAll();
        assertTrue(this.readPaymentsFromFile().size()==0);

    }
    private ArrayList<Payment> readPaymentsFromFile(){
        List<Payment> payments = new ArrayList<Payment>();
        ClassLoader classLoader = PaymentRepository.class.getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while((line=br.readLine())!=null){
                Payment item=null;
                StringTokenizer st=new StringTokenizer(line, ",");
                int tableNumber = Integer.parseInt(st.nextToken());
                String type = st.nextToken();
                double amount = Double.parseDouble(st.nextToken());
                item = new Payment(tableNumber, PaymentType.valueOf(type), amount);
                payments.add(item);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (ArrayList<Payment>) payments;
    }
}