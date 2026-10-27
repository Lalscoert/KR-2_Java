
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class EquipmentOrder {

    // Рабочая трактовка варианта №3
    private static final int MAX_MERGED_QUANTITY = 20;

    private final String employee;
    private final List<OrderItem> items = new ArrayList<>();
    private boolean confirmed = false;

    public EquipmentOrder(String employee) {
        if (employee == null || employee.isBlank()) {
            throw new IllegalArgumentException(
                "Имя сотрудника обязательно"
            );
        }

        this.employee = employee.trim();
    }

    public void addProduct(Product product, int quantity) {
        ensureEditable();

        OrderItem newItem = new OrderItem(product, quantity);

        for (int i = 0; i < items.size(); i++) {
            OrderItem oldItem = items.get(i);

            if (oldItem.getProduct().getId().equals(product.getId())) {

                if (!oldItem.getProduct().getName().equals(product.getName())
                    || oldItem.getProduct().getPrice()
                        .compareTo(product.getPrice()) != 0) {

                    throw new IllegalArgumentException(
                        "Конфликт данных товара с ID " + product.getId()
                    );
                }

                int newQuantity = oldItem.getQuantity() + quantity;

                if (newQuantity > MAX_MERGED_QUANTITY) {
                    throw new IllegalArgumentException(
                        "Общее количество не может превышать 20"
                    );
                }

                items.set(i, new OrderItem(product, newQuantity));
                return;
            }
        }

        items.add(newItem);
    }

    public void removeProduct(String productId) {
        ensureEditable();

        if (productId == null || productId.isBlank()) {
            throw new IllegalArgumentException("ID обязателен");
        }

        boolean removed = items.removeIf(
            item -> item.getProduct().getId().equals(productId)
        );

        if (!removed) {
            throw new IllegalArgumentException("Товар не найден");
        }
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : items) {
            total = total.add(item.getSubtotal());
        }

        return total;
    }

    public void confirm() {
        ensureEditable();

        if (items.isEmpty()) {
            throw new IllegalStateException(
                "Нельзя подтвердить пустой заказ"
            );
        }

        confirmed = true;
    }

    private void ensureEditable() {
        if (confirmed) {
            throw new IllegalStateException(
                "Подтверждённый заказ нельзя изменять"
            );
        }
    }

    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getEmployee() {
        return employee;
    }
}
