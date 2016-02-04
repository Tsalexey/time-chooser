/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ncedu.tsarev.ctc;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Алексей
 */
public class InputDataHelper {

    /**
     *  
     * @return 
     */
    public Pattern getNamePattern() {
        Pattern p = Pattern.compile("(\\S)+");
        return p;
    }

    /**
     * 
     * @return 
     */
    public Pattern getDatePattern() {
        Pattern p = Pattern.compile("(\\d){2}[.](\\d){2}[.](\\d){4}");
        return p;
    }    
    
    /**
     * 
     * @return 
     */
    public Pattern getTimePattern() {
        Pattern p = Pattern.compile("(\\d){2}[:](\\d){2}");
        return p;
    }    
    
    /**
     *  True if string satisfy pattern
     * @param inputString
     * @param pattern
     * @return
     * @throws IllegalArgumentException 
     */
    public boolean checkAccordance(String inputString, Pattern pattern) throws IllegalArgumentException {

        if ( inputString ==null && pattern == null){
            return true;
        }
        
        if ( inputString ==null || pattern == null){
            throw new IllegalArgumentException();
        }

        Matcher m = pattern.matcher( inputString );
        boolean b = m.matches();
        return b;
    }
    
    /**
     * Checks if name of event is valid
     * @param name
     * @return
     * @throws IOException 
     */
    public String checkName(String name) throws IOException{
        InputDataHelper checker = new InputDataHelper();
        try {
            if ( !checker.checkAccordance(name, checker.getNamePattern()) ){
                throw new IOException();
            }
        } catch (IOException e) {
             System.out.println("Bad name!");
             throw new IOException();
        }        
        return name;
    }

    /**
     * Checks if date is valid
     * @param date
     * @return
     * @throws IOException 
     */
    public Calendar checkDate(String date) throws IOException{
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");                
        Calendar newCalendar = Calendar.getInstance();
        try{
        Date parsed = df.parse(date);
        newCalendar.setTime(parsed);
        } catch (ParseException e) {
            System.out.println("Wrong date!");
            throw new IOException();
        }
        return newCalendar;
    }  
 
    /**
     * Check if time is valid
     * @param date
     * @param time
     * @return
     * @throws IOException 
     */
    public Calendar checkTime(String date, String time) throws IOException {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");                
        Calendar newCalendar = Calendar.getInstance();
        try{
        Date parsed = df.parse(date + " " + time);
        newCalendar.setTime(parsed); 
        if ( (newCalendar.get(Calendar.MINUTE) != 0) ) {
            if ( (newCalendar.get(Calendar.MINUTE) != 30) ) {
                System.out.println(newCalendar.get(Calendar.HOUR_OF_DAY) + " " + newCalendar.get(Calendar.MINUTE));
                throw new IOException();
            }
        }
      
        } catch (ParseException e) {
            System.out.println("Wrong time!" + time);
            throw new IOException();
        } catch (IOException e) {
            System.out.println("Wrong time step");
            throw new IOException();
        }
        return newCalendar;
    }   

    /**
     *  Checks if time is valid
     * @param time
     * @return
     * @throws IOException 
     */
    public Calendar checkTime(String time) throws IOException {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");                
        Calendar newCalendar = Calendar.getInstance();
        String date = "01.01.2001";
        try{
        Date parsed = df.parse(date + " " + time);
        newCalendar.setTime(parsed); 
        if ( (newCalendar.get(Calendar.MINUTE) != 0) ) {
            if ( (newCalendar.get(Calendar.MINUTE) != 30) ) {
                System.out.println(newCalendar.get(Calendar.HOUR_OF_DAY) + " " + newCalendar.get(Calendar.MINUTE));
                throw new IOException();
            }
        }
      
        } catch (ParseException e) {
            System.out.println("Wrong time!" + time);
            throw new IOException();
        } catch (IOException e) {
            System.out.println("Wrong time step");
            throw new IOException();
        }
        return newCalendar;
    }       
    
    /**
     * Checks if time range is valid
     * @param c1
     * @param c2
     * @param dur
     * @throws IOException 
     */
    public void checkTimeRange(Calendar c1, Calendar c2, Calendar dur) throws IOException{
        int h1, h2, m1, m2, dh, dm;
        h1 = c1.get(Calendar.HOUR_OF_DAY);
        h2 = c2.get(Calendar.HOUR_OF_DAY);
        m1 = c1.get(Calendar.MINUTE);
        m2 = c2.get(Calendar.MINUTE);
        dh = dur.get(Calendar.HOUR_OF_DAY);
        dm = dur.get(Calendar.MINUTE);
        try {
            if ( h1 > h2  || (h1 == h2 && m2 < m1) || ( ((h2-h1)*60 + (m2-m1) < dm + (dh*60)) )){                
                throw new IOException();
            }
        } catch (IOException e) {
            System.out.println("Wrong time range");
            throw new IOException();
        }        
    }    
    
    /**
     *  Sets correct hour and minutes for a time for event E
     * @param e
     * @param time
     * @return 
     */
    public Calendar setCorrectDate(Event e, Calendar time){
        Calendar correctTime = e.getDate();
        int correctHour = time.get(Calendar.HOUR_OF_DAY);
        int correctMinute = time.get(Calendar.MINUTE);
        correctTime.set(Calendar.HOUR_OF_DAY, correctHour);
        correctTime.set(Calendar.MINUTE, correctMinute);
        return correctTime;
    }
}
