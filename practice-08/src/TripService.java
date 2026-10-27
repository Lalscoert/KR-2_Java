
import java.time.LocalDate;

public final class TripService {

    public BusinessTrip createTrip(
            String employee,
            String destination,
            String purpose,
            LocalDate start,
            LocalDate end) {

        return new BusinessTrip(
            employee, destination, purpose, start, end
        );
    }

    public void submit(BusinessTrip trip) {
        requireTrip(trip).submit();
    }

    public void approve(BusinessTrip trip) {
        requireTrip(trip).approve();
    }

    public void reject(BusinessTrip trip, String reason) {
        requireTrip(trip).reject(reason);
    }

    public void cancel(BusinessTrip trip) {
        requireTrip(trip).cancel();
    }

    public void complete(BusinessTrip trip, LocalDate today) {
        requireTrip(trip).complete(today);
    }

    private BusinessTrip requireTrip(BusinessTrip trip) {
        if (trip == null) {
            throw new IllegalArgumentException(
                "Заявка обязательна"
            );
        }

        return trip;
    }
}
