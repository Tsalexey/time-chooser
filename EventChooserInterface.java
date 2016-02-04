/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ncedu.tsarev.ctc;

import java.util.Calendar;

/**
 *
 * @author Алексей
 */
public interface EventChooserInterface {
    
    /**
     *  Make new Event 
     * @param eventName
     * @param date
     * @param minTime
     * @param maxTime
     * @param duration 
     */
    public void addEvent(String eventName, String date, String minTime, String maxTime, String duration);
    
    /**
     * Define convenient time for a person 
     * @param eventName
     * @param startTime
     * @param endTime
     */
    public void selectTime(String eventName, String startTime, String endTime);
    
    /**
     * Return the most convenient time for an event
     * @param eventName
     * @return 
     */
    public Calendar getBestTime(String eventName);
    
    /**
     * Shows event's info
     * @param eventName
     * @param name 
     */
    public void getEventInfo(String eventName);
    
}
