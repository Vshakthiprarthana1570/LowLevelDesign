package librarymanagementsystem.models;

import librarymanagementsystem.observer.Member;
import librarymanagementsystem.observer.MemberObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class LibraryItem
{
    private final String id;
    private final String title;
    private List<MemberObserver> observers = new ArrayList<>();

    public LibraryItem(String id, String title)
    {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public abstract int getDefaultLendingDays();
}
