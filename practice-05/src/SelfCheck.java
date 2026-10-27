
public class SelfCheck {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        int[] normal = Main.parseHours("8;8;9;10;7");

        check("Обычные данные: сумма",
                Main.calculateTotal(normal) == 42);

        check("Обычные данные: среднее",
                Main.calculateAverage(normal) == 8.4);

        check("Количество дней с переработкой",
                Main.countOvertimeDays(normal) == 2);

        int[] minimum = Main.parseHours("0;0;0;0;0");

        check("Нижняя граница: 0 часов",
                Main.calculateTotal(minimum) == 0);

        int[] maximum = Main.parseHours("16;16;16;16;16");

        check("Верхняя граница: 16 часов",
                Main.calculateTotal(maximum) == 80);

        int[] exactNorm = Main.parseHours("8;8;8;8;8");

        check("Ровно 8 часов — не переработка",
                Main.countOvertimeDays(exactNorm) == 0);

        expectError("Пустая строка", "",
                "Строка пустая");

        expectError("Превышение 16 часов",
                "8;8;8;8;17", "позиции 5");

        expectError("Отрицательное значение",
                "8;-1;8;8;8", "позиции 2");

        expectError("Буква вместо числа",
                "8;8;abc;8;8", "позиции 3");

        expectError("Только четыре значения",
                "8;8;8;8", "Ожидалось 5");

        expectError("Пропущено пятое значение",
                "8;8;8;8;", "позиции 5");

        System.out.println("\n========================");
        System.out.println("Успешных проверок: " + passed);
        System.out.println("Ошибок: " + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void check(String name, boolean condition) {
        if (condition) {
            System.out.println("[PASS] " + name);
            passed++;
        } else {
            System.out.println("[FAIL] " + name);
            failed++;
        }
    }

    private static void expectError(
            String name, String input, String expectedMessage) {

        try {
            Main.parseHours(input);
            check(name, false);
        } catch (IllegalArgumentException e) {
            check(name, e.getMessage().contains(expectedMessage));
        }
    }
}
