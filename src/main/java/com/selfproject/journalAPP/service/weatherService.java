package com.selfproject.journalAPP.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.selfproject.journalAPP.Cache.AppCache;
import com.selfproject.journalAPP.Constants.PlaceHolders;
import com.selfproject.journalAPP.api.response.WeatherResponse;

@Service
public class weatherService {

    private final EmailService emailService;

	@Value("${Weather.api.key}")
	private String APIKEY;
	
	//private static final String API="https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
	
	@Autowired
	private RestTemplate restTemplate; 
	
	@Autowired
	private AppCache appCache;
	
	@Autowired
	private RedisService redisService;

    weatherService(EmailService emailService) {
        this.emailService = emailService;
    }
	
	public WeatherResponse getWeather(String city)
	{
	  // this response is cached 
	   WeatherResponse CachedweatherResponse = redisService.get("Weather_of_"+city,WeatherResponse.class);
	   
	   if(CachedweatherResponse != null)
	   { 
		 return CachedweatherResponse;  
	   }else {
		   String finalAPI = appCache.appCache.get(AppCache.keys.weather_api.toString()).replace(PlaceHolders.CITY, city).replace(PlaceHolders.API_KEY,APIKEY );
		   
		   //The process of converting json response into java object or pojo plane java old object is called as Deserilication
		   ResponseEntity<WeatherResponse> resposne = restTemplate.exchange(finalAPI, HttpMethod.GET,null,WeatherResponse.class);
		   
		   // data ow weather we get is stored in response we store it hear in body  resposne.getBody();
		   WeatherResponse body = resposne.getBody();
		   
		   if(body !=null)
		   {
			   // hear we get key the body means data and ttl time to leave 300l stored in redis database 
			   redisService.set("Weather_of_"+city, body, 300l);
		   }
		   return body;
		   
	   }
	   
	   
	   
	   
	}
	
/*	
    As now i dont use post method to make any  internal api call 
    
    public WeatherResponse postWeather(String city)
	{
	   String finalAPI = API.replace("CITY", city).replace("API_KEY",APIKEY );
	   
	   String RequestBody= "{ "
	   		                    + "\"userName\":\"Ayush\", "
	   		                    + "\"password\":\"Ayush\" "
	   		             + "}";
	   
	   HttpHeaders httpHeaders = new HttpHeaders();
	   httpHeaders.set("Key", "Value");
	   User user = (User) User.builder().username("Vandesh").password("Vandesh").build();
	   
	   HttpEntity<User> httpEntity = new HttpEntity<>(user,httpHeaders);
	   
	   ResponseEntity<WeatherResponse> resposne = restTemplate.exchange(finalAPI, HttpMethod.POST,null,WeatherResponse.class);
	   WeatherResponse body = resposne.getBody();
	   
	   return body;
	}
	
	sk_d496668196a5ba766fb0efbba5390eb23da76b0d59d7ca83
	
	*/
}
