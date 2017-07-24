package com.youu.winter.annotations;

import java.util.Date;

import org.joda.time.DateTime;

@Bean
public class User {

	
	public static void main(String[] args) {
		DateTime now = DateTime.now().withTimeAtStartOfDay();
		Date tomorrow = now.plusDays(1).toDate();
        Date yesterday = now.minusDays(1).toDate();
        Date theDayBeforeYesterday = now.minusDays(2).toDate();
        
        System.out.println(now.equals(DateTime.now().withTimeAtStartOfDay()));
		System.out.println(DateTime.now().getSecondOfDay() > 12 * 60 * 60
				? 24 * 60 * 60 - DateTime.now().getSecondOfDay() : 12 * 60 * 60);
        System.out.println(yesterday);
        System.out.println(theDayBeforeYesterday);
	}
}
