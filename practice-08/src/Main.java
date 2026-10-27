
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        TripService service = new TripService();

        System.out.println("=== СИСТЕМА КОМАНДИРОВОК ===");

        try {
            System.out.print("Имя сотрудника: ");
            String employee = scanner.nextLine();

            System.out.print("Город: ");
            String destination = scanner.nextLine();

            System.out.print("Цель командировки: ");
            String purpose = scanner.nextLine();

            System.out.print("Дата начала (yyyy-MM-dd): ");
            LocalDate start = LocalDate.parse(
                scanner.nextLine().trim()
            );

            System.out.print("Дата окончания (yyyy-MM-dd): ");
            LocalDate end = LocalDate.parse(
                scanner.nextLine().trim()
            );

            BusinessTrip trip = service.createTrip(
                employee, destination, purpose, start, end
            );

            while (true) {

                System.out.println("\n=== МЕНЮ ===");
                System.out.println("Текущий статус: " + trip.getStatus());
                System.out.println("1. Отправить заявку");
                System.out.println("2. Одобрить заявку");
                System.out.println("3. Отклонить заявку");
                System.out.println("4. Отменить заявку");
                System.out.println("5. Завершить командировку");
                System.out.println("0. Выход");
                System.out.print("Выберите действие: ");

                String choice = scanner.nextLine().trim();

                if (choice.equals("0")) {
                    System.out.println("Завершение программы");
                    break;
                }

                try {
                    switch (choice) {

                        case "1":
                            service.submit(trip);
                            System.out.println("Заявка отправлена!");
                            break;

                        case "2":
                            service.approve(trip);
                            System.out.println("Заявка одобрена!");
                            break;

                        case "3":
                            System.out.print("Причина отклонения: ");
                            String reason = scanner.nextLine();

                            service.reject(trip, reason);
                            System.out.println("Заявка отклонена!");
                            break;

                        case "4":
                            service.cancel(trip);
                            System.out.println("Заявка отменена!");
                            break;

                        case "5":
                            System.out.print(
                                "Дата проверки (yyyy-MM-dd): "
                            );

                            LocalDate today = LocalDate.parse(
                                scanner.nextLine().trim()
                            );

                            service.complete(trip, today);
                            System.out.println("Командировка завершена!");
                            break;

                        default:
                            System.out.println("Неизвестная команда");
                    }

                } catch (IllegalArgumentException | IllegalStateException
                         | DateTimeParseException e) {

                    System.out.println("Ошибка: " + e.getMessage());
                }
            }

        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.out.println("Ошибка ввода: " + e.getMessage());
        }

        scanner.close();
    }
}
