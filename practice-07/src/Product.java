
import java.math.BigDecimal;

public final class Product {

    private final String id;
    private final String name;
    private final BigDecimal price;

    public Product(String id, String name, BigDecimal price) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID товара обязателен");
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Название обязательно");
        }

        if (price == null || price.signum() <= 0) {
            throw new IllegalArgumentException(
                "Цена должна быть положительной"
            );
        }

        this.id = id.trim();
        this.name = name.trim();
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
