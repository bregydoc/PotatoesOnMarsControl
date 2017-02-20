package com.freerider.chimpcode.potatoonmars.Mars;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by root on 2/18/17.
 */

public class DateCycle {


    private Date initDate;
    private Calendar calendar;

    public DateCycle() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-5:00"));
        this.calendar = c;
        this.initDate = c.getTime();

    }

    public Calendar getCurrentCalendar() {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT-5:00"));
    }

    public Date getCurrentDate() throws Exception {
        String url = "https://time.is/Unix_time_now";
        Document doc = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
        String[] tags = new String[] {
                "div[id=time_section]",
                "div[id=clock0_bg]"
        };
        Elements elements= doc.select(tags[0]);
        for (int i = 0; i <tags.length; i++) {
            elements = elements.select(tags[i]);
        }
        long time = Long.parseLong(elements.text() + "000");

        return  new Date(time);
    }

}
