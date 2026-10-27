
public class SelfCheck {

    private static final ApprovalService service = new ApprovalService();

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        check("Персональные данные", 100, false,
                RiskLevel.LOW, true,
                ApprovalRoute.SECURITY_REVIEW);

        check("Приоритет безопасности", 600000, true,
                RiskLevel.HIGH, true,
                ApprovalRoute.SECURITY_REVIEW);

        check("Высокий риск", 600000, false,
                RiskLevel.HIGH, false,
                ApprovalRoute.DIRECTOR_REVIEW);

        check("Сумма 499999", 499999, false,
                RiskLevel.LOW, false,
                ApprovalRoute.STANDARD);

        check("Сумма 500000", 500000, false,
                RiskLevel.MEDIUM, false,
                ApprovalRoute.FINANCE_REVIEW);

        check("Сумма 500001", 500001, false,
                RiskLevel.LOW, false,
                ApprovalRoute.FINANCE_REVIEW);

        check("Срочная заявка 19999", 19999, true,
                RiskLevel.LOW, false,
                ApprovalRoute.FAST_TRACK);

        check("Срочная заявка 20000", 20000, true,
                RiskLevel.LOW, false,
                ApprovalRoute.STANDARD);

        check("Срочная заявка 499999", 499999, true,
                RiskLevel.MEDIUM, false,
                ApprovalRoute.STANDARD);

        check("Обычная заявка", 0, false,
                RiskLevel.LOW, false,
                ApprovalRoute.STANDARD);

        check("Высокий риск и срочность", 100, true,
                RiskLevel.HIGH, false,
                ApprovalRoute.DIRECTOR_REVIEW);

        checkError("Отрицательная сумма", -1,
                false, RiskLevel.LOW, false);

        checkError("Отсутствует риск", 100,
                false, null, false);

        System.out.println("\n========================");
        System.out.println("Успешных проверок: " + passed);
        System.out.println("Ошибок: " + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void check(
            String name,
            long amount,
            boolean urgent,
            RiskLevel risk,
            boolean personal,
            ApprovalRoute expected) {

        ApprovalRoute actual = service.route(
                amount, urgent, risk, personal
        );

        boolean success = actual == expected;

        if (success) {
            passed++;
            System.out.println("[PASS] " + name);
        } else {
            failed++;
            System.out.println("[FAIL] " + name);
        }
    }

    private static void checkError(
            String name,
            long amount,
            boolean urgent,
            RiskLevel risk,
            boolean personal) {

        try {
            service.route(amount, urgent, risk, personal);
            failed++;
            System.out.println("[FAIL] " + name);

        } catch (IllegalArgumentException e) {
            passed++;
            System.out.println("[PASS] " + name);
        }
    }
}
