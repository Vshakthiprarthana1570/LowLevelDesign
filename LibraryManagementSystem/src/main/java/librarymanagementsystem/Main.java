package librarymanagementsystem;


import librarymanagementsystem.strategy.StandardFineStrategy;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args)
    {

        System.out.println("=== INITIALIZING LIBRARY MANAGEMENT SYSTEM ===");
        LibraryManagementService library = LibraryManagementService.getInstance();

        // Injecting the Fine Calculator Strategy Policy
        library.setFineStrategy(new StandardFineStrategy());

        // -------------------------------------------------------------
        // REQUIREMENT 1: Book Management Setup
        // -------------------------------------------------------------
        System.out.println("\n--- Step 1: Registering Catalog Data ---");
        String cleanCodeIsbn = "978-0132350884";
        library.registerBook(cleanCodeIsbn, "Clean Code", "Robert C. Martin");

        // Add 2 unique physical copies of the same book item onto shelves
        library.addBook("BARCODE-CC-01", cleanCodeIsbn);
        library.addBook("BARCODE-CC-02", cleanCodeIsbn);
        System.out.println("Success: Registered metadata and 2 physical copies for 'Clean Code'.");

        // -------------------------------------------------------------
        // REQUIREMENT 2: Member Account Setup
        // -------------------------------------------------------------
        System.out.println("\n--- Step 2: Registering Members ---");
        var alice = library.registerMember("Alice", "alice@verizon.com");
        var bob = library.registerMember("Bob", "bob@verizon.com");
        var charlie = library.registerMember("Charlie", "charlie@verizon.com");

        System.out.println("Registered Account IDs:");
        System.out.println("-> " + alice.getName() + ": ID = " + alice.getMemberId());
        System.out.println("-> " + bob.getName() + ": ID = " + bob.getMemberId());
        System.out.println("-> " + charlie.getName() + ": ID = " + charlie.getMemberId());

        // -------------------------------------------------------------
        // REQUIREMENT 3: Borrowing System Simulation
        // -------------------------------------------------------------
        System.out.println("\n--- Step 3: Executing Concurrent Borrowing Actions ---");
        // Alice borrows Copy 1
        library.borrowBook("BARCODE-CC-01", alice.getMemberId());
        System.out.println("Execution Success: Alice successfully checked out Copy #1.");

        // Bob borrows Copy 2
        library.borrowBook("BARCODE-CC-02", bob.getMemberId());
        System.out.println("Execution Success: Bob successfully checked out Copy #2.");

        // Edge Case Test: Verify borrowing limit blocks (Simulating failure if limit is reached)
        try {
            System.out.println("\n[Guard Test] Attempting to borrow Copy 1 again while it's out...");
            library.borrowBook("BARCODE-CC-01", charlie.getMemberId());
        } catch (IllegalArgumentException e) {
            System.out.println("Caught Expected Safety Block: " + e.getMessage());
        }

        // -------------------------------------------------------------
        // REQUIREMENT 4: Reservations System (FIFO Queue)
        // -------------------------------------------------------------
        System.out.println("\n--- Step 4: Placing FIFO Book Reservations ---");
        // Since both copies are checked out, Charlie places a reservation
        library.reserveBook(cleanCodeIsbn, charlie.getMemberId());

        // -------------------------------------------------------------
        // REQUIREMENT 5 & OBSERVER: Returning Books & Triggering Notifications
        // -------------------------------------------------------------
        System.out.println("\n--- Step 5: Returning Books & Event Dispatching ---");
        // Alice returns her copy. This should auto-trip the queue and notify Charlie!
        library.returnBook("BARCODE-CC-01", alice.getMemberId());

        System.out.println("\n=== SIMULATION SYSTEM AUDIT COMPLETE ===");


    }
}