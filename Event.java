/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ncedu.tsarev.ctc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Алексей
 */
@Root
public class Event {
    @Attribute (name = "id")
    private int id;
    @Element (name = "eventName")
    private String eventName;
    @Element (name = "date")
    private Calendar date;
    @Element (name = "startTime")
    private Calendar startTime;
    @Element (name = "endTime")
    private Calendar endTime;
    @Element (name = "duration")
    private Calendar duration;

    /**
     * Constructor
     * @param id
     * @param eventName
     * @param date
     * @param startTime
     * @param endTime
     * @param duration 
     */
    public Event(){
        id = 0;
        eventName = "";
        date = null;
        startTime = null;
        endTime = null;
        duration = null;
    }
    
    public Event(  int id,  String eventName,  Calendar date,  Calendar startTime,  Calendar endTime, Calendar duration){
        this.id = id;        
        this.eventName = eventName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * @param eventName the eventName to set
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * @return the startTime
     */
    public Calendar getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public Calendar getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the duration
     */
    public Calendar getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(Calendar duration) {
        this.duration = duration;
    }

    /**
     * @return the date
     */
    public Calendar getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Calendar date) {
        this.date = date;
    }
    
    public String dateToString(Calendar cal){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = formatter.format((cal).getTime());
        return formattedDate;
    }
    
    public String timeToString(Calendar cal){
        SimpleDateFormat formatter = new SimpleDateFormat("kk:mm");
        String formattedDate = formatter.format((cal).getTime());
        return formattedDate;
    }
    
}
