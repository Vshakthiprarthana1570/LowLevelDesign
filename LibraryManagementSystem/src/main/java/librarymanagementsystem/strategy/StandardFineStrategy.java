package librarymanagementsystem.strategy;

import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class StandardFineStrategy implements FineStrategy
{
    private static final double FINE_PER_DAY = 2.0;

    public double calculateFee(LocalDate returnDate, LocalDate dueDate)
    {
        if(dueDate.isAfter(returnDate))
        {
            long duedays = ChronoUnit.DAYS.between(dueDate, returnDate);
            return duedays * FINE_PER_DAY;
        }
        return 0.0;
    }
}
