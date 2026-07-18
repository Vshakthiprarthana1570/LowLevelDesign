package librarymanagementsystem.models;

import librarymanagementsystem.enums.BookStatus;

import java.util.UUID;

public class BookInstance
{
    private final String barcode;
    private final LibraryItem book;
    private BookStatus bookStatus;

    public BookInstance(String barcode,LibraryItem book)
    {
        this.barcode = barcode;
        this.book = book;
        this.bookStatus = BookStatus.AVAILABLE;
    }

    public String getBarcode() {
        return barcode;
    }

    public LibraryItem getBook() {
        return book;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }
}
