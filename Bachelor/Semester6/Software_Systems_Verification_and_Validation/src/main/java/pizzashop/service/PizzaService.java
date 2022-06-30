package pizzashop.service;

import pizzashop.model.MenuDataModel;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import java.util.List;

public class PizzaService {

    private MenuRepository menuRepo;
    private PaymentRepository payRepo;
    private static final int NR_TABLES = 8;

    public PizzaService(MenuRepository menuRepo, PaymentRepository payRepo){
        this.menuRepo=menuRepo;
        this.payRepo=payRepo;
    }

    public List<MenuDataModel> getMenuData(){return menuRepo.getMenu();}

    public List<Payment> getPayments(){return payRepo.getAll(); }

    public void addPayment(int table, PaymentType type, double amount){
        if (table < 1 || table > NR_TABLES) {
            throw new IllegalArgumentException();
        }
        if (amount <= 0 || amount > Double.MAX_VALUE) {
            throw new IllegalArgumentException();
        }

        Payment payment= new Payment(table, type, amount);
        payRepo.add(payment);
    }

    public void addPayment(Payment payment) throws Exception {
        int table = payment.getTableNumber();
        if(table < 1 || table > NR_TABLES)
            throw new Exception("Invalid table number");

        payRepo.add(payment);
    }

    public double getTotalAmount(PaymentType type){
        double total=0.0f;
        List<Payment> l=getPayments();
        if (l==null)
            return total;
        if(l.isEmpty())
            return total;
        for (Payment p:l) {
            if (p.getType().equals(type))
                total+=p.getAmount();
        }
        return total;
    }
}