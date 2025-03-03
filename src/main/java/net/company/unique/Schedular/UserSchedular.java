package net.company.unique.Schedular;

import net.company.unique.Cache.AppCache;
import net.company.unique.Entity.JournalEntry;
import net.company.unique.Entity.User;
import net.company.unique.Enum.Sentiment;
import net.company.unique.Repository.UserRepoImplMongoTemp;
import net.company.unique.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class UserSchedular
{
    @Autowired
    private UserRepoImplMongoTemp userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AppCache appCache;

   @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendEmail() {
        List<User> userList = userRepository.getall(); // Corrected method name
        for (User user : userList) {
            List<JournalEntry> journalEntries = user.getJournalEntries();

            // Filtering only last 7 days' entries
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))) // Uncommented
                    .map(JournalEntry::getSentiment)
                    .filter(Objects::nonNull) // To avoid null values
                    .collect(Collectors.toList());

            // Counting sentiment occurrences
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }

            // Finding the most frequent sentiment
            Sentiment mostFrequentCount = null;
            int max = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > max) {
                    max = entry.getValue();
                    mostFrequentCount = entry.getKey();
                }
            }

            // Sending the email if there is at least one sentiment
            if (mostFrequentCount != null) {
                emailService.sendEmail(user.getEmail(), "Your Weekly Sentiment Analysis Report", mostFrequentCount.toString());
            }
        }
    }

    @Scheduled(cron = " 0 0/10 * ? * *")
public void ClearAppCache()
{
    appCache.init();
}
}
