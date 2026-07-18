package librarymanagementsystem;

import librarymanagementsystem.enums.AccountStatus;
import librarymanagementsystem.enums.BookStatus;
import librarymanagementsystem.enums.ReservationStatus;
import librarymanagementsystem.models.*;
import librarymanagementsystem.observer.Member;
import librarymanagementsystem.strategy.FineStrategy;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LibraryManagementService
{
    private static LibraryManagementService instance;
    private static final int MAX_ALLOWED_LIMIT = 5;
    private FineStrategy fineStrategy;

    private final Map<String, Member> members = new ConcurrentHashMap<>();

    private final Map<String, LibraryItem> bookByIsbn = new ConcurrentHashMap<>();
    private final Map<String, BookInstance> bookInstanceByBarcode = new ConcurrentHashMap<>();
    private final Map<String, List<BookInstance>> bookInstancesByIsbn = new ConcurrentHashMap<>();
    private final Map<String, Lending> activeLendingsByBarcode = new ConcurrentHashMap<>();
    private final Map<String, Queue<Reservation>> activeReservationsByIsbn = new ConcurrentHashMap<>();


    public static synchronized LibraryManagementService getInstance()
    {

        if (instance == null)
        {
            instance = new LibraryManagementService();
        }

        return instance;
    }

    public void setFineStrategy(FineStrategy fineStrategy)
    {
        this.fineStrategy = fineStrategy;
    }

    public Member registerMember(String name,String email)
    {
        Member newMember = new Member(name,email);
        members.put(newMember.getMemberId(), newMember);
        return newMember;
    }

    public void registerBook(String isbn,String title,String author)
    {
        if(bookByIsbn.get(isbn) != null)
        {
            throw new IllegalArgumentException("Book with ISBN " + isbn + " already exists.");
        }
        Book book = new Book(isbn, title, author);
        bookByIsbn.put(isbn, book);
    }

    public void addBook(String barcode, String isbn)
    {
        if(bookByIsbn.get(isbn) == null)
        {
            throw new IllegalArgumentException("Book with ISBN " + isbn + " does not exist.");
        }
        BookInstance bookInstance = new BookInstance(barcode, bookByIsbn.get(isbn));
        bookInstanceByBarcode.put(barcode, bookInstance);
        bookInstancesByIsbn.computeIfAbsent(isbn, k-> new ArrayList<>()).add(bookInstance);

    }

    public synchronized void borrowBook(String barcode,String memberId)
    {
        BookInstance bookInstance = bookInstanceByBarcode.get(barcode);
        Member member = members.get(memberId);

        if(bookInstance == null || bookInstance.getBookStatus() != BookStatus.AVAILABLE)
        {
            throw new IllegalArgumentException("Book instance with barcode " + barcode + " is not available for borrowing.");
        }
        if(member == null || member.getAccountStatus() != AccountStatus.ACTIVE)
        {
            throw new IllegalArgumentException("Member with ID " + memberId + " is not active or does not exist.");
        }

        if(member.getActiveBorowedCount().get() >= MAX_ALLOWED_LIMIT)
        {
            throw  new IllegalArgumentException("Member with ID " + memberId + " has reached the maximum allowed limit of borrowed books.");
        }

        Lending newLending = new Lending(bookInstance, memberId, bookInstance.getBook().getDefaultLendingDays());
        activeLendingsByBarcode.put(barcode, newLending);
        bookInstance.setBookStatus(BookStatus.BORROWED);
        member.getActiveBorowedCount().incrementAndGet();
    }

    public synchronized void returnBook(String barcode, String memberId)
    {
        BookInstance bookInstance = bookInstanceByBarcode.get(barcode);
        Member member = members.get(memberId);

        if(bookInstance == null || bookInstance.getBookStatus() != BookStatus.BORROWED)
        {
            throw new IllegalArgumentException("Book instance with barcode " + barcode + " is not currently borrowed.");
        }
        if(member == null)
        {
            throw new IllegalArgumentException("Member with ID " + memberId + " does not exist.");
        }

        Lending lending = activeLendingsByBarcode.get(barcode);
        if(lending == null || !lending.getMemberId().equals(memberId))
        {
            throw new IllegalArgumentException("This book instance was not borrowed by member with ID " + memberId + ".");
        }

        lending.setReturnDate(LocalDate.now());
        double fee = fineStrategy.calculateFee(lending.getDueDate(), lending.getReturnDate());

        if(fee > 0)
        {
            System.out.println("Member with ID " + memberId + " has a fine of $" + fee + " for late return.");
        }

        Queue<Reservation> activeReservations = activeReservationsByIsbn.get(bookInstance.getBook().getId());

        if(activeReservations != null && !activeReservations.isEmpty())
        {
            Reservation nextReservation = activeReservations.poll();
            nextReservation.setReservationStatus(ReservationStatus.FULFILLED);
            bookInstance.setBookStatus(BookStatus.RESERVED);
            Member memberObserver = members.get(nextReservation.getMemberId());

            memberObserver.update("Your reserved book with ISBN " + nextReservation.getIsbn() + " is now available for pickup.");
        }
        else
        {
            bookInstance.setBookStatus(BookStatus.AVAILABLE);
        }
    }

    public synchronized void reserveBook(String id, String memberId)
    {
        LibraryItem bookItem = bookByIsbn.get(id);
        Member member = members.get(memberId);

        if(bookItem == null || member == null || member.getAccountStatus() != AccountStatus.ACTIVE)
        {
            throw new IllegalArgumentException("Book with ID " + id + " or member with ID " + memberId + " does not exist or is not active.");
        }

        List<BookInstance> bookInstances = bookInstancesByIsbn.getOrDefault(id, Collections.emptyList());

        boolean available = bookInstances.stream().
                         anyMatch(instance -> instance.getBookStatus() == BookStatus.AVAILABLE);

        if(available)
        {
            throw new IllegalArgumentException("Book with ID " + id + " is currently available. No need to reserve.");
        }

        Reservation reservation = new Reservation(id, memberId);
        activeReservationsByIsbn.computeIfAbsent(id, k -> new LinkedList<>()).add(reservation);
        member.update("Your reservation for book with ISBN " + id + " has been placed successfully.");
    }

}
