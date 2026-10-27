
import java.math.BigDecimal;

public final class OrderService {

    public EquipmentOrder createOrder(String employee) {
        return new EquipmentOrder(employee);
    }

    public void addItem(
            EquipmentOrder order,
            Product product,
            int quantity) {

        order.addProduct(product, quantity);
    }

    public void removeItem(EquipmentOrder order, String productId) {
        order.removeProduct(productId);
    }

    public void confirm(EquipmentOrder order) {
        order.confirm();
    }

    public BigDecimal total(EquipmentOrder order) {
        return order.calculateTotal();
    }
}
