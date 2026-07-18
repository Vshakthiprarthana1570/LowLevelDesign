package librarymanagementsystem.observer;

import librarymanagementsystem.enums.AccountStatus;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Member implements MemberObserver
{

    private final String memberId;
    private final String name;
    private final String email;
    private AccountStatus accountStatus;
    private AtomicInteger activeBorowedCount;

    public Member(String name,String email)
    {
        this.memberId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.accountStatus = AccountStatus.ACTIVE;
        this.activeBorowedCount = new AtomicInteger(0);
    }


    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public AtomicInteger getActiveBorowedCount() {
        return activeBorowedCount;
    }

    public void setActiveBorowedCount(AtomicInteger activeBorowedCount) {
        this.activeBorowedCount = activeBorowedCount;
    }

    @Override
    public void update(String message)
    {
        System.out.println("Notification sent to [" + email + "]: " + message);
    }
}
