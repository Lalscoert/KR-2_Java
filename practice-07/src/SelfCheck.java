
import java.math.BigDecimal;

public class SelfCheck {

    private static int passed = 0;
    private static int failed = 0;

    private static final OrderService service = new OrderService();

    private static final Product mouse = new Product(
        "M1", "Mouse", new BigDecimal("25.50")
    );

    private static final Product keyboard = new Product(
        "K1", "Keyboard", new BigDecimal("100")
    );

    public static void main(String[] args) {

        EquipmentOrder order = service.createOrder("Anna");

        check("Новый заказ пуст", order.getItems().isEmpty());

        service.addItem(order, mouse, 2);

        check("Расчёт стоимости",
            order.calculateTotal().compareTo(
                new BigDecimal("51.00")
            ) == 0
        );

        service.addItem(order, mouse, 3);

        check("Объединение одинаковых товаров",
            order.getItems().size() == 1
            && order.getItems().get(0).getQuantity() == 5
        );

        service.addItem(order, keyboard, 1);

        check("Добавление другого товара",
            order.getItems().size() == 2
        );

        check("Общая стоимость",
            order.calculateTotal().compareTo(
                new BigDecimal("227.50")
            ) == 0
        );

        expectArgument("Нулевая цена",
            () -> new Product("A", "Test", BigDecimal.ZERO)
        );

        expectArgument("Пустой ID",
            () -> new Product("", "Test", BigDecimal.ONE)
        );

        expectArgument("Количество 0",
            () -> service.addItem(order, mouse, 0)
        );

        expectArgument("Количество 101",
            () -> service.addItem(order, mouse, 101)
        );

        expectArgument("Конфликт ID товара",
            () -> service.addItem(
                order,
                new Product("M1", "Other", BigDecimal.ONE),
                1
            )
        );

        expectUnsupported("Защита списка товаров",
            () -> order.getItems().clear()
        );

        service.removeItem(order, "K1");

        check("Удаление товара",
            order.getItems().size() == 1
        );

        expectArgument("Удаление неизвестного товара",
            () -> service.removeItem(order, "UNKNOWN")
        );

        EquipmentOrder second = service.createOrder("Boris");

        service.addItem(second, mouse, 19);
        service.addItem(second, mouse, 1);

        check("Объединение до 20",
            second.getItems().get(0).getQuantity() == 20
        );

        expectArgument("Превышение лимита 20",
            () -> service.addItem(second, mouse, 1)
        );

        expectState("Подтверждение пустого заказа",
            () -> service.createOrder("Test").confirm()
        );

        service.confirm(order);

        check("Подтверждение заказа",
            order.isConfirmed()
        );

        expectState("Запрет добавления после подтверждения",
            () -> service.addItem(order, mouse, 1)
        );

        expectState("Запрет удаления после подтверждения",
            () -> service.removeItem(order, "M1")
        );

        System.out.println("\n======================");
        System.out.println("Успешных проверок: " + passed);
        System.out.println("Ошибок: " + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void expectArgument(String name, Runnable action) {
        try {
            action.run();
            check(name, false);
        } catch (IllegalArgumentException e) {
            check(name, true);
        }
    }

    private static void expectState(String name, Runnable action) {
        try {
            action.run();
            check(name, false);
        } catch (IllegalStateException e) {
            check(name, true);
        }
    }

    private static void expectUnsupported(String name, Runnable action) {
        try {
            action.run();
            check(name, false);
        } catch (UnsupportedOperationException e) {
            check(name, true);
        }
    }

    private static void check(String name, boolean success) {
        if (success) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }
}
