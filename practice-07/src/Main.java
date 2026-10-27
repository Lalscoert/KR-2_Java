
import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        OrderService service = new OrderService();

        System.out.println("=== ЗАКАЗ ОБОРУДОВАНИЯ ===");
        System.out.print("Введите имя сотрудника: ");

        EquipmentOrder order;

        try {
            order = service.createOrder(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
            return;
        }

        while (true) {

            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Добавить товар");
            System.out.println("2. Удалить товар");
            System.out.println("3. Показать заказ");
            System.out.println("4. Подтвердить заказ");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            if (choice.equals("0")) {
                System.out.println("Завершение программы");
                break;
            }

            try {
                switch (choice) {

                    case "1":
                        System.out.print("ID товара: ");
                        String id = scanner.nextLine();

                        System.out.print("Название товара: ");
                        String name = scanner.nextLine();

                        System.out.print("Цена: ");
                        BigDecimal price = new BigDecimal(
                            scanner.nextLine().trim()
                        );

                        System.out.print("Количество: ");
                        int quantity = Integer.parseInt(
                            scanner.nextLine().trim()
                        );

                        Product product = new Product(id, name, price);

                        service.addItem(order, product, quantity);

                        System.out.println("Товар добавлен!");
                        break;

                    case "2":
                        System.out.print("Введите ID товара: ");
                        String removeId = scanner.nextLine();

                        service.removeItem(order, removeId);

                        System.out.println("Товар удалён!");
                        break;

                    case "3":
                        System.out.println("\n=== СОСТАВ ЗАКАЗА ===");

                        for (OrderItem item : order.getItems()) {
                            System.out.println(
                                item.getProduct().getName()
                                + " | Количество: " + item.getQuantity()
                                + " | Стоимость: " + item.getSubtotal()
                            );
                        }

                        System.out.println("Итого: " + service.total(order));
                        System.out.println(
                            "Подтверждён: " + order.isConfirmed()
                        );
                        break;

                    case "4":
                        service.confirm(order);
                        System.out.println("Заказ подтверждён!");
                        break;

                    default:
                        System.out.println("Неизвестная команда");
                }

            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
