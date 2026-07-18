package librarymanagementsystem.models;

import librarymanagementsystem.enums.ReservationStatus;

import java.time.LocalDate;
import java.util.UUID;

public class Reservation
{
    private final String reservationId;
    private final String isbn;
    private final String memberId;
    private final LocalDate reservationDate;
    private ReservationStatus reservationStatus;

    public Reservation(String isbn,String memberId)
    {
        this.reservationId = UUID.randomUUID().toString();
        this.isbn = isbn;
        this.memberId = memberId;
        this.reservationDate = LocalDate.now();
        this.reservationStatus = ReservationStatus.PENDING;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getMemberId() {
        return memberId;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}
