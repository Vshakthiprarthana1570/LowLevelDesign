package librarymanagementsystem.strategy;

import java.time.LocalDate;

public interface FineStrategy
{
    double calculateFee(LocalDate returnDate,LocalDate dueDate);
}
