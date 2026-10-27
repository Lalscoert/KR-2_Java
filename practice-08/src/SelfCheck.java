
import java.time.LocalDate;

public class SelfCheck {

    private static int passed = 0;
    private static int failed = 0;

    private static final TripService service = new TripService();

    private static final LocalDate START =
        LocalDate.of(2026, 11, 1);

    private static final LocalDate END =
        LocalDate.of(2026, 11, 5);

    public static void main(String[] args) {

        BusinessTrip trip = createTrip();

        check("Начальный статус DRAFT",
            trip.getStatus() == TripStatus.DRAFT
        );

        expectState("Нельзя одобрить черновик",
            () -> service.approve(trip)
        );

        service.submit(trip);

        check("Отправка заявки",
            trip.getStatus() == TripStatus.SUBMITTED
        );

        expectState("Повторная отправка запрещена",
            () -> service.submit(trip)
        );

        service.approve(trip);

        check("Одобрение заявки",
            trip.getStatus() == TripStatus.APPROVED
        );

        expectState("Завершение в день окончания запрещено",
            () -> service.complete(trip, END)
        );

        service.complete(trip, END.plusDays(1));

        check("Завершение командировки",
            trip.getStatus() == TripStatus.COMPLETED
        );

        expectState("Нельзя отменить завершённую",
            () -> service.cancel(trip)
        );

        BusinessTrip rejected = createTrip();
        service.submit(rejected);

        expectArgument("Причина отклонения короче 10 символов",
            () -> service.reject(rejected, "123456789")
        );

        service.reject(rejected, "Not enough budget");

        check("Отклонение заявки",
            rejected.getStatus() == TripStatus.REJECTED
        );

        check("Причина отклонения сохранена",
            rejected.getRejectionReason().equals("Not enough budget")
        );

        expectState("Нельзя одобрить отклонённую",
            () -> service.approve(rejected)
        );

        BusinessTrip draftCancelled = createTrip();
        service.cancel(draftCancelled);

        check("Отмена черновика",
            draftCancelled.getStatus() == TripStatus.CANCELLED
        );

        expectState("Повторная отмена запрещена",
            () -> service.cancel(draftCancelled)
        );

        BusinessTrip submittedCancelled = createTrip();
        service.submit(submittedCancelled);
        service.cancel(submittedCancelled);

        check("Отмена отправленной заявки",
            submittedCancelled.getStatus() == TripStatus.CANCELLED
        );

        BusinessTrip approvedCancelled = createTrip();
        service.submit(approvedCancelled);
        service.approve(approvedCancelled);
        service.cancel(approvedCancelled);

        check("Отмена одобренной заявки",
            approvedCancelled.getStatus() == TripStatus.CANCELLED
        );

        BusinessTrip incomplete = service.createTrip(
            "", "Moscow", "Meeting", START, END
        );

        expectArgument("Пустое имя сотрудника",
            () -> service.submit(incomplete)
        );

        BusinessTrip wrongDates = service.createTrip(
            "Anna", "Moscow", "Meeting", END, START
        );

        expectArgument("Неправильный порядок дат",
            () -> service.submit(wrongDates)
        );

        BusinessTrip exactly20 = service.createTrip(
            "Anna", "Moscow", "Meeting",
            START, START.plusDays(19)
        );

        service.submit(exactly20);

        check("20 дней допустимо",
            exactly20.getStatus() == TripStatus.SUBMITTED
        );

        BusinessTrip over20 = service.createTrip(
            "Anna", "Moscow", "Meeting",
            START, START.plusDays(20)
        );

        expectArgument("21 день запрещён",
            () -> service.submit(over20)
        );

        BusinessTrip noToday = createTrip();
        service.submit(noToday);
        service.approve(noToday);

        expectArgument("Отсутствует текущая дата",
            () -> service.complete(noToday, null)
        );

        System.out.println("\n======================");
        System.out.println("Успешных проверок: " + passed);
        System.out.println("Ошибок: " + failed);

        if (failed > 0) {
            System.exit(1);
        }
    }

    private static BusinessTrip createTrip() {
        return service.createTrip(
            "Anna", "Moscow", "Meeting", START, END
        );
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
