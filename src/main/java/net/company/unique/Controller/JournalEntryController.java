package net.company.unique.Controller;

import net.company.unique.Entity.JournalEntry;
import net.company.unique.Entity.User;
import net.company.unique.Service.JournalService;
import net.company.unique.Service.UserService;
import org.bson.types.ObjectId;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


import java.util.List;



@RestController
@RequestMapping("/journal")
public class JournalEntryController
{

    private JournalService journalService;

    private UserService userService;

    public JournalEntryController(UserService userService,JournalService journalService)
    {
        this.journalService = journalService;
        this.userService = userService;
    }


    @GetMapping
   public ResponseEntity<List<JournalEntry>> getAllJournalEntryByUser()
   {
       Authentication authorization = SecurityContextHolder.getContext().getAuthentication();
       String Username = authorization.getName();
       User user = userService.findByUsername(Username);

       List<JournalEntry> all = user.getJournalEntries();
       if (all !=null && !all.isEmpty())
       {
        return new ResponseEntity<>(all,HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }

   @GetMapping("id/{myId}")
   public ResponseEntity<JournalEntry> GetMyJournalEntryById(@PathVariable ObjectId myId)
   {
       Authentication authorization = SecurityContextHolder.getContext().getAuthentication();
       String Username = authorization.getName();
       User user = userService.findByUsername(Username);
       List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();
       if (!collect.isEmpty())
       {
           Optional<JournalEntry> journalEntry = journalService.getById(myId);
           if(journalEntry.isPresent())
           {
               return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
           }
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);

   }


   @PostMapping("/create")
   public ResponseEntity<JournalEntry> CreateEntry(@RequestBody JournalEntry myentry)
   {
       try {
           Authentication authorization = SecurityContextHolder.getContext().getAuthentication();
           if (authorization == null || !authorization.isAuthenticated()) {
               return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
           }
           String Username = authorization.getName();
           journalService.save(myentry,Username);
           return new ResponseEntity<>(myentry,HttpStatus.OK);
       }
       catch (Exception e)
       {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
   }

    @DeleteMapping("/{myId}")
    public ResponseEntity<Object> deleteEntry(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
        }

        String username = authentication.getName();
        boolean deleted = journalService.deleteId(myId, username);

        if (deleted) {
            return new ResponseEntity<>("Journal entry deleted", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("Journal entry not found or unauthorized", HttpStatus.NOT_FOUND);
        }
    }


  @PutMapping("id/{myId}")
   public ResponseEntity<JournalEntry> update(@RequestBody JournalEntry myEntry, @PathVariable ObjectId myId)
   {
       Authentication authorization = SecurityContextHolder.getContext().getAuthentication();
       String Username = authorization.getName();
       User user = userService.findByUsername(Username);
       List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();
       if (!collect.isEmpty())
       {
           Optional<JournalEntry> journalEntry = journalService.getById(myId);
           if(journalEntry.isPresent())
           {
               JournalEntry journal = journalEntry.get();
               journal.setTitle(myEntry.getTitle()!=null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : journal.getTitle());
               journal.setContent(myEntry.getContent()!=null && !myEntry.getTitle().equals("") ? myEntry.getContent() : journal.getContent());
               journalService.save(journal);
               return new ResponseEntity<>(journal,HttpStatus.OK);

           }
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);

   }
}
