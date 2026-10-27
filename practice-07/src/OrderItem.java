
import java.math.BigDecimal;

public final class OrderItem {

    private final Product product;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Товар обязателен");
        }

        if (quantity < 1 || quantity > 100) {
            throw new IllegalArgumentException(
                "Количество должно быть от 1 до 100"
            );
        }

        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getSubtotal() {
        return product.getPrice().multiply(
            BigDecimal.valueOf(quantity)
        );
    }
}
