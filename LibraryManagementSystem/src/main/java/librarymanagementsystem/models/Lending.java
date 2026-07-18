package librarymanagementsystem.models;

import java.time.LocalDate;
import java.util.UUID;

public class Lending
{
    private final String lendingId;
    private final BookInstance bookInstance;
    private final String memberId;
    private final LocalDate creationDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    public Lending(BookInstance bookInstance, String memberId, int lendingDays)
    {
        this.lendingId = UUID.randomUUID().toString();
        this.bookInstance = bookInstance;
        this.memberId = memberId;
        this.creationDate = LocalDate.now();
        this.dueDate = creationDate.plusDays(lendingDays);
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getLendingId() {
        return lendingId;
    }

    public BookInstance getBookInstance() {
        return bookInstance;
    }

    public String getMemberId() {
        return memberId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
