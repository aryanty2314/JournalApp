package net.company.unique.Service;

import lombok.extern.slf4j.Slf4j;
import net.company.unique.Entity.JournalEntry;
import net.company.unique.Entity.User;
import net.company.unique.Repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalService
{
    private JournalRepository journalrepo;
    private UserService userService;
    @Autowired
public JournalService(JournalRepository journalrepo , UserService userService)
{
    this.journalrepo = journalrepo;
    this.userService = userService;

}
    @Transactional
    public void save(JournalEntry journalEntry, String username) {
        try {
            User user = userService.findByUsername(username);

            if (user == null) {  // 🛑 Avoid NullPointerException
                throw new RuntimeException("User not found: " + username);
            }

            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalrepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveNew(user);
        } catch (Exception e) {
            log.error("Error saving journal entry: {}", e.getMessage());  // ✅ Logs the real error
            throw new RuntimeException("Failed to save journal entry", e);
        }
    }

    public void save(JournalEntry journalEntry)
    {
        journalrepo.save(journalEntry);
    }
public List<JournalEntry> printAll()
{
    return journalrepo.findAll();
}

public Optional<JournalEntry> getById(ObjectId id)
{
    return journalrepo.findById(id);
}
@Transactional
public boolean deleteId(ObjectId id, String username)
{
    boolean removed = false;
    try {
        User user = userService.findByUsername(username);
        removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        if (removed)
        {
            userService.save(user);
            journalrepo.deleteById(id);
        }

    } catch (Exception e) {
        throw new RuntimeException(e);
    }
    return removed;
}

}
