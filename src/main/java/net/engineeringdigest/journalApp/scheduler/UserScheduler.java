package net.engineeringdigest.journalApp.scheduler;

import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;
import net.engineeringdigest.journalApp.entity.*;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.enums.Sentiment;
import org.springframework.scheduling.annotation.Scheduled;
import net.engineeringdigest.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import net.engineeringdigest.journalApp.repository.UserRepository;

@Component
public class UserScheduler {

    @Autowired
    private AppCache appCache;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepositry;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepositry.getUserForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }
            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                if (mostFrequentSentiment == Sentiment.HAPPY) {
                    emailService.sendEmail(user.getEmail(),
                            "Keep Your Happiness Alive – Discover Our Premium Journals!",
                            "Dear " + user.getUsername()
                                    + ",\n\nWe noticed that your past week has been filled with happiness! 😊 That’s amazing, and we want to help you capture and cherish those joyful moments forever.\n\nIntroducing our Premium Gratitude Journals, designed to boost positivity, mindfulness, and happiness in your life. Start your mornings by jotting down happy thoughts, or use it to reflect on the best moments of your day.\n\n✨ Special Offer: Get 15% off on your first journal purchase!\n\nStay happy and keep journaling!");
                } else if (mostFrequentSentiment == Sentiment.SAD) {
                    emailService.sendEmail(user.getEmail(),
                            "A Journal Can Be Your Safe Space – Let’s Support Your Journey",
                            "Dear " + user.getUsername()
                                    + ",\n\nWe understand that the past week may have been challenging. You’re not alone. Writing can be a powerful way to process emotions, find clarity, and heal.\n\nOur specially designed Self-Care & Reflection Journals provide a safe space for you to express your feelings, release stress, and find peace through words.\n\n🖊️ Your thoughts matter. Your emotions matter. And we’re here to help.\n\n✨ Exclusive Offer: Get 20% off on our Emotional Wellness Journals!\n\nTake care, and remember—we’re always here to support you.");
                } else if (mostFrequentSentiment == Sentiment.ANGRY) {
                    emailService.sendEmail(user.getEmail(),
                            "Transform Anger into Productivity – Try Expressive Journaling",
                            "Dear " + user.getUsername()
                                    + ",\n\nWe all experience frustration and anger—it’s a natural part of life. But did you know that writing can be a powerful tool to turn those emotions into clarity and focus?\n\nOur Guided Anger Management Journals help you:\n✔️ Identify triggers and patterns\n✔️ Release frustration in a healthy way\n✔️ Shift your mindset toward solutions\n\n✨ Special Offer: Get 10% off on our Expressive Journaling Collection!\n\nWriting isn’t just an outlet—it’s a path to self-mastery. Let’s start this journey together.");
                } else {
                    emailService.sendEmail(user.getEmail(),
                            "Calm Your Mind with Guided Journaling – Special Offer Inside!",
                            "Dear " + user.getUsername()
                                    + ",\n\nFeeling overwhelmed? You’re not alone. Writing down your thoughts can be a simple yet powerful way to reduce anxiety and bring a sense of calm to your mind.\n\nOur Mindfulness & Anxiety-Relief Journals are designed to help you:\n✅ Organize overwhelming thoughts\n✅ Build healthy habits for relaxation\n✅ Find peace through guided reflections\n\n✨ Exclusive Deal: Get 20% off your first journal to start your journey toward inner peace.\n\nTake a deep breath. You’ve got this. And we’ve got your back.");
                }
            }
        }
    }

    @Scheduled(cron = "0 0/10 * ? * *")
    public void clearAppCache() {
        appCache.loadConfig();
    }
}
