
import java.util.Scanner;

public class Main {

    private static final int WORK_DAYS = 5;
    private static final int MAX_HOURS = 16;
    private static final int DAILY_NORM = 8;

    private static final int WEEKLY_NORM = 20;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== УЧЁТ РАБОЧЕГО ВРЕМЕНИ ===");
        System.out.println("Введите часы за 5 дней через ;");
        System.out.print("Часы: ");

        String input = scanner.nextLine();

        try {
            int[] hours = parseHours(input);

            int total = calculateTotal(hours);
            double average = calculateAverage(hours);
            int overtimeDays = countOvertimeDays(hours);
            int weeklyOvertime = Math.max(0, total - WEEKLY_NORM);

            System.out.println("\n=== РЕЗУЛЬТАТ ===");
            System.out.println("Всего часов: " + total);
            System.out.println("Среднее за день: " + average);
            System.out.println("Дней с переработкой: " + overtimeDays);
            System.out.println("Недельная переработка: " + weeklyOvertime);

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }


    public static int[] parseHours(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Строка пустая");
        }

        String[] parts = input.split(";", -1);

        if (parts.length != WORK_DAYS) {
            throw new IllegalArgumentException(
                "Ожидалось 5 значений, получено: " + parts.length
            );
        }

        int[] hours = new int[WORK_DAYS];

        for (int i = 0; i < WORK_DAYS; i++) {
            try {
                hours[i] = Integer.parseInt(parts[i].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                    "Некорректное число на позиции " + (i + 1)
                );
            }

            if (hours[i] < 0 || hours[i] > MAX_HOURS) {
                throw new IllegalArgumentException(
                    "Недопустимое количество часов на позиции " + (i + 1)
                );
            }
        }

        return hours;
    }


    public static int calculateTotal(int[] hours) {
        int total = 0;

        for (int i = 0; i < hours.length; i++) {
            total += hours[i];
        }

        return total;
    }


    public static double calculateAverage(int[] hours) {
        return (double) calculateTotal(hours) / hours.length;
    }


    public static int countOvertimeDays(int[] hours) {
        int count = 0;

        for (int i = 0; i < hours.length; i++) {
            if (hours[i] > DAILY_NORM) {
                count++;
            }
        }

        return count;
    }
}
