package com.financeanalyser.service;


import java.util.List;
import java.util.Map;

import com.financeanalyser.model.Category;
import com.financeanalyser.model.Transaction;

public class Categoriser {
	
	private static final Map<Category, List<String>> KEYWORDS = Map.of(
        Category.GROCERIES, List.of(
                "tesco", "sainsbury's", "asda", "morrisons", "aldi", "lidl", "waitrose", "marks & spencer", "ocado", "iceland", "co-op", "farmfoods", "booths", "amazon fresh", "whole foods market", "spar", "nisa", "heron foods", "costco", "budgens",
                "costcutter", "sainsburys s/mkts" 
            ),

            Category.SHOPPING, List.of(
                    "amazon", "argos", "ebay", "etsy", "john lewis", "next", "primark", "marks & spencer", "argos", "currys", "ikea", "asos", "boohoo", "jd sports", "sports direct", "tk maxx", "selfridges", "harrods", "decathlon", "h&m",
                    "tiktok shop", "duchess & dressmaker" 
            ),

            Category.TRANSPORT, List.of(
                    "uber", "bolt", "free now", "national express", "megabus", "trainline", "national rail", "stagecoach", "arriva", "first bus", "go-ahead", "south western railway", "southeastern", "thameslink", "greater anglia", "avanti west coast", "crosscountry", "easyjet", "ryanair", "british airways"
            ),
            
            Category.BILLS, List.of("british gas", "octopus energy", "ovo energy", "eon next", "edf energy", "scottish power", "utilita", "utility warehouse", "shell energy", "thames water", "severn trent", "united utilities", "anglian water", "bt", "sky", "virgin media", "vodafone", "ee", "three", "o2"),
            
            Category.ENTERTAINMENT, List.of("netflix", "disney+", "amazon prime video", "apple tv+", "paramount+", "now", "bbc", "itv", "channel 4", "sky", "spotify", "youtube", "twitch", "cinema city", "odeon", "vue", "cineworld", "legoland", "merlin entertainments", "ticketmaster",
                    "bet365"
            ),
            
            Category.RESTAURANTS, List.of("mcdonald's", "kfc", "subway", "burger king", "domino's", "pizza hut", "nando's", "greggs", "pret a manger", "wagamama", "five guys", "starbucks", "costa coffee", "caffe nero", "itsu", "gourmet burger kitchen", "taco bell", "wendy's", "papa john's", "las iguanas",
                    "just eat", "deliveroo", "uber eats", "jd wetherspoon", "wetherspoon", "eastern oriental" // new
            ),
            
            Category.HEALTH, List.of("boots", "superdrug", "lloydspharmacy", "holland & barrett", "well pharmacy", "rowlands pharmacy", "superdrug opticians", "boots opticians", "specsavers", "vision express", "bupa", "nuffield health", "spires healthcare", "circle health group", "benenden health", "vitality", "aviva health", "axa health", "hca healthcare", "bmi healthcare",
                    "surrey sports park", "gym"
            ),
            
            Category.SUBSCRIPTIONS, List.of("netflix", "spotify", "disney+", "amazon prime", "apple music", "apple tv+", "youtube premium", "youtube music", "paramount+", "now", "sky", "audible", "kindle unlimited", "xbox game pass", "playstation plus", "nintendo switch online", "adobe creative cloud", "microsoft 365", "google one", "dropbox")
            
        );
	
	public static Category categorise(String description) {
		String lowered = description.toLowerCase();
		
		for (Map.Entry<Category, List<String>> entry : KEYWORDS.entrySet()) {
			for(String keyword: entry.getValue()) {
				if(lowered.contains(keyword)) {
					return entry.getKey();
				
				}
			}
		}
		
		return Category.OTHER;
		
	}

	public static void convertFromOtherToCategory(String number, Transaction transaction) {
		switch(number) {
		case "1":
				transaction.setCategory(Category.GROCERIES);
				break;
			case "2":
				transaction.setCategory(Category.SHOPPING);
				break;
			case "3":
				transaction.setCategory(Category.TRANSPORT);
				break;
			case "4":
				transaction.setCategory(Category.BILLS);
				break;
			case "5":
				transaction.setCategory(Category.ENTERTAINMENT);
				break;
			case "6":
				transaction.setCategory(Category.RESTAURANTS);
				break;
			case "7":
				transaction.setCategory(Category.HEALTH);
				break;
			case "8":
				transaction.setCategory(Category.SUBSCRIPTIONS);
				break;
			default:
				System.out.println("Invalid input, please try again.");
		}
	}
	

}
