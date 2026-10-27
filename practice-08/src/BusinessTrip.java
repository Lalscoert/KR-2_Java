
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class BusinessTrip {

    // Рабочая трактовка варианта №3
    private static final long MAX_DURATION_DAYS = 20;

    private final String employee;
    private final String destination;
    private final String purpose;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private TripStatus status = TripStatus.DRAFT;
    private String rejectionReason;

    public BusinessTrip(
            String employee,
            String destination,
            String purpose,
            LocalDate startDate,
            LocalDate endDate) {

        this.employee = employee;
        this.destination = destination;
        this.purpose = purpose;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Отправка заявки
    public void submit() {

        if (status != TripStatus.DRAFT) {
            throw new IllegalStateException(
                "Отправить можно только черновик"
            );
        }

        if (employee == null || employee.isBlank()
                || destination == null || destination.isBlank()
                || purpose == null || purpose.isBlank()
                || startDate == null || endDate == null) {

            throw new IllegalArgumentException(
                "Не заполнены обязательные поля"
            );
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(
                "Дата окончания раньше даты начала"
            );
        }

        long duration = ChronoUnit.DAYS.between(
            startDate, endDate
        ) + 1;

        if (duration > MAX_DURATION_DAYS) {
            throw new IllegalArgumentException(
                "Командировка не может быть дольше 20 дней"
            );
        }

        status = TripStatus.SUBMITTED;
    }

    // Одобрение заявки
    public void approve() {

        if (status != TripStatus.SUBMITTED) {
            throw new IllegalStateException(
                "Одобрить можно только отправленную заявку"
            );
        }

        status = TripStatus.APPROVED;
    }

    // Отклонение заявки
    public void reject(String reason) {

        if (status != TripStatus.SUBMITTED) {
            throw new IllegalStateException(
                "Отклонить можно только отправленную заявку"
            );
        }

        if (reason == null || reason.trim().length() < 10) {
            throw new IllegalArgumentException(
                "Причина отклонения должна содержать минимум 10 символов"
            );
        }

        rejectionReason = reason.trim();
        status = TripStatus.REJECTED;
    }

    // Отмена заявки
    public void cancel() {

        if (status != TripStatus.DRAFT
                && status != TripStatus.SUBMITTED
                && status != TripStatus.APPROVED) {

            throw new IllegalStateException(
                "Отмена невозможна из состояния " + status
            );
        }

        status = TripStatus.CANCELLED;
    }

    // Завершение командировки
    public void complete(LocalDate today) {

        if (status != TripStatus.APPROVED) {
            throw new IllegalStateException(
                "Завершить можно только одобренную командировку"
            );
        }

        if (today == null) {
            throw new IllegalArgumentException(
                "Текущая дата обязательна"
            );
        }

        if (!today.isAfter(endDate)) {
            throw new IllegalStateException(
                "Дата окончания командировки ещё не прошла"
            );
        }

        status = TripStatus.COMPLETED;
    }

    public TripStatus getStatus() {
        return status;
    }

    public String getEmployee() {
        return employee;
    }

    public String getDestination() {
        return destination;
    }

    public String getPurpose() {
        return purpose;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }
}
