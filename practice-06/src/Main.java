
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ApprovalService service = new ApprovalService();

        try {
            System.out.println("=== СОГЛАСОВАНИЕ ЗАЯВОК ===");

            System.out.print("Введите сумму заявки: ");
            long amount = Long.parseLong(scanner.nextLine());

            System.out.print("Срочная заявка? (true/false): ");
            boolean urgent = readBoolean(scanner.nextLine());

            System.out.print("Уровень риска (LOW/MEDIUM/HIGH): ");
            RiskLevel risk = RiskLevel.valueOf(
                scanner.nextLine().trim().toUpperCase()
            );

            System.out.print("Есть персональные данные? (true/false): ");
            boolean personalData = readBoolean(scanner.nextLine());

            ApprovalRoute result = service.route(
                amount, urgent, risk, personalData
            );

            System.out.println("\n=== РЕЗУЛЬТАТ ===");
            System.out.println("Маршрут согласования: " + result);

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }

    private static boolean readBoolean(String value) {
        if (value.trim().equalsIgnoreCase("true")) {
            return true;
        }
        if (value.trim().equalsIgnoreCase("false")) {
            return false;
        }
        throw new IllegalArgumentException(
            "Необходимо ввести true или false"
        );
    }
}


enum RiskLevel {
    LOW,
    MEDIUM,
    HIGH
}


enum ApprovalRoute {
    SECURITY_REVIEW,
    DIRECTOR_REVIEW,
    FINANCE_REVIEW,
    FAST_TRACK,
    STANDARD
}


class ApprovalService {

    private static final long FINANCE_LIMIT = 500000;

    private static final long FAST_TRACK_LIMIT = 20000;

    public ApprovalRoute route(
            long amount,
            boolean urgent,
            RiskLevel risk,
            boolean personalData) {

        if (amount < 0) {
            throw new IllegalArgumentException(
                "Сумма не может быть отрицательной"
            );
        }

        if (risk == null) {
            throw new IllegalArgumentException(
                "Уровень риска обязателен"
            );
        }

        if (personalData) {
            return ApprovalRoute.SECURITY_REVIEW;
        }

        if (risk == RiskLevel.HIGH) {
            return ApprovalRoute.DIRECTOR_REVIEW;
        }

        if (amount >= FINANCE_LIMIT) {
            return ApprovalRoute.FINANCE_REVIEW;
        }

        if (urgent && amount < FAST_TRACK_LIMIT) {
            return ApprovalRoute.FAST_TRACK;
        }

        return ApprovalRoute.STANDARD;
    }
}
