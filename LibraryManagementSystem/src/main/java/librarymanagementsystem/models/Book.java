package librarymanagementsystem.models;

public class Book extends LibraryItem
{

    private  final String author;

    public Book(String isbn, String title, String author)
    {
        super(isbn, title);
        this.author = author;
    }


    public String getAuthor() {
        return author;
    }

    public int getDefaultLendingDays()
    {
        return 14; // Default lending period for books is 14 days
    }
}
