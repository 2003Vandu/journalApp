package com.selfproject.journalAPP.Scheduler;

import com.selfproject.journalAPP.Cache.AppCache;
import com.selfproject.journalAPP.Enums.Sentiment;
import com.selfproject.journalAPP.Repository.UserRepositoryImpl;
import com.selfproject.journalAPP.entity.JournalEntry;
import com.selfproject.journalAPP.entity.User;
import com.selfproject.journalAPP.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class UserScheduler {
	
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepositoryImpl userRepositoryImpl;
	
	@Autowired
	private AppCache appCache;


//    @Autowired
//    private KafkaTemplate<String, SentimentData> kafkaTemplate;
	
	@Scheduled(cron ="0 0 9 * * SUN")
	public void featchuserandSend_SA_mail()
	{
		List<User> users = userRepositoryImpl.getUserForSA();
		
		for(User user :users)
		{
			List<JournalEntry> journalEntries = user.getJournalEntries();
			List<Sentiment>  sentiments = journalEntries
					                                      .stream()
					                                      .filter(x->x.getDate()
					                                        		            .isAfter(LocalDateTime.now().minus(7,ChronoUnit.DAYS)))
					                                                            .map(x->x.getSentiment())
					                                                            .collect(Collectors.toList());
			
			Map<Sentiment, Integer> sentementCounts = new HashMap<>();
			for(Sentiment sentiment : sentiments)
			{
				if(sentiment != null)
				{
					sentementCounts.put(sentiment, sentementCounts.getOrDefault(sentiment, 0)+1);
				}
			}
			Sentiment mostFreuentSentiment = null;
			int maxCount=0;
			
			for(Map.Entry<Sentiment, Integer> entry: sentementCounts.entrySet())
			{
				if(entry.getValue()>maxCount)
				{
					maxCount= entry.getValue();
					mostFreuentSentiment=entry.getKey();
				}
			}
			
			if(mostFreuentSentiment !=null)
			{
//               SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days"+mostFreuentSentiment).build();
//               //sending email via kafka decoupling
//               kafkaTemplate.send("journal-group",sentimentData.getEmail(),sentimentData);

				 // this will send mail to user 
				emailService.sendMail(user.getEmail(), "Sentement for last 7 Days", mostFreuentSentiment.toString());
			}
		}
	}

	@Scheduled(cron = "0 */10 * * * *")
	public void clearAppcache()
	{
		appCache.init();
	}
}
