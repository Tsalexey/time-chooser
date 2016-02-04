/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ncedu.tsarev.ctc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.simpleframework.xml.Attribute;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

/**
 *
 * @author Алексей
 */

@Root(name="EventChooserImpl")
public class EventChooserImpl implements EventChooserInterface{
    @Attribute (name = "eventsCount")
    private int eventsCount;
    @ElementList(name = "eventList", inline=true, required=false)
    private List<Event> eventList;
    @ElementMap(name = "eventVoters", entry="property", value="value", attribute=true, inline=true, required=false)
    private Map<Integer, VoteWrapper> eventVoters; 
    
    /**
     * Constructor
     */
    public EventChooserImpl(){
        eventsCount = 0;
        eventList = new ArrayList<Event>();
        eventVoters = new HashMap<Integer, VoteWrapper>();
    }
    
    public EventChooserImpl( int eventsCount,  List<Event> eventList, Map<Integer, VoteWrapper> eventVoters ){
        this.eventsCount = eventsCount;
        this.eventList = eventList;
        this.eventVoters = eventVoters;        
    }
    /**
     * Adds event to list
     * @param eventName
     * @param date
     * @param minTime
     * @param maxTime
     * @param duration 
     */
    @Override
    public void addEvent(String eventName, String date, String minTime, String maxTime, String duration){
        Calendar correctDate, correctMinTime, correctMaxTime, correctDuration;
        String correctName;
        InputDataHelper checker = new InputDataHelper();
        try {
            correctName = checker.checkName(eventName);
            correctDate = checker.checkDate(date);
            correctMinTime = checker.checkTime(date, minTime);
            correctMaxTime = checker.checkTime(date, maxTime);
            correctDuration = checker.checkTime(date, duration);
            checker.checkTimeRange(correctMinTime, correctMaxTime, correctDuration);
            
            Event e = new Event(eventsCount, correctName, correctDate, correctMinTime, correctMaxTime, correctDuration);
            ArrayList<Vote> voters = new ArrayList<Vote>();
            VoteWrapper vw = new VoteWrapper(voters);
            eventVoters.put(eventsCount, vw);
            eventList.add(e);
            eventsCount++; 
        } catch (IOException e) {
            System.out.println("Correct error and try again!");
        }
    }

    /**
     *  Sets convenient event's time for an user
     * @param eventName
     * @param startTime
     * @param endTime 
     */
    @Override
    public void selectTime(String eventName, String startTime, String endTime) {
        
        Calendar correctMinTime = null, correctMaxTime = null;
        String correctName = "";
        
        InputDataHelper checker = new InputDataHelper();
        try {
            correctName = checker.checkName(eventName);
            correctMinTime = checker.checkTime(startTime);
            correctMaxTime = checker.checkTime(endTime);            
        } catch (IOException e) {
            System.out.println("Correct error and try again!");
        }
        
        int eventId = selectEvent(correctName);
        
        ArrayList<Vote> voters;
        VoteWrapper vw = eventVoters.get(eventId);
        voters = vw.getData();
        
        Calendar tempMinTime, tempMaxTime;
        tempMinTime = (Calendar)(checker.setCorrectDate(eventList.get(eventId), correctMinTime)).clone();
        tempMaxTime = (Calendar)(checker.setCorrectDate(eventList.get(eventId), correctMaxTime)).clone();
        
        Vote v = new Vote(tempMinTime, tempMaxTime);
        voters.add(v);
        vw.setData(voters);
        eventVoters.put(eventId, vw);
    }
    
    /**
     * returns best time for an event
     * @param eventName
     * @return 
     */
    private Calendar getBestTime(int id){
        Calendar bestTime = null;
        bestTime = getBestTimeForAll(id);
        try {
            if ( bestTime == null ){
                bestTime = getBestTimeForMajority(id);
            }
        } catch( IOException e){
            System.out.println("There is no best time for anyone!");
        }
        return bestTime;
    }
    
    @Override
    public Calendar getBestTime(String eventName) {
        String correctName = "";
        
        InputDataHelper checker = new InputDataHelper();
        try {
            correctName = checker.checkName(eventName);
        } catch (IOException e) {
            System.out.println("Correct error and try again!");
        }     
        
        int eventId = selectEvent(correctName);
        Calendar bestTime = null;
        bestTime = getBestTimeForAll(eventId);
        try {
            if ( bestTime == null ){
                bestTime = getBestTimeForMajority(eventId);
            }
        } catch( IOException e){
            System.out.println("There is no best time for anyone!");
        }
        return bestTime;
    }

    /**
     * Provides all information about an event
     * @param eventName 
     */
    @Override
    public void getEventInfo(String eventName) {
        String correctName;
        InputDataHelper checker = new InputDataHelper();
        try {
            correctName = checker.checkName(eventName);  
            int eventId = selectEvent(correctName);
            boolean noOne = true;
            for (Event e: eventList) {

                if ( eventId == (e.getId())){
                    noOne = false;
                    System.out.println(". . . . . . . . . . . . . . . . .");
                    System.out.println("Event id: " + e.getId());
                    System.out.println("Event name: " + e.getEventName());
                    System.out.println("Date: " + e.dateToString(e.getDate()) );
                    System.out.println("Start time: " + e.timeToString(e.getStartTime()) );
                    System.out.println("End time: " + e.timeToString(e.getEndTime()) );
                    System.out.println("Duration: " + e.timeToString(e.getDuration()) );
                    ArrayList<Vote> voters;
                    VoteWrapper vw = eventVoters.get(e.getId());
                    voters = vw.getData();
                    int i = 1;
                    for(Vote v: voters){
                        System.out.println("Vote: " + i + ", start time: " + e.timeToString(v.getStartTime()) + ", end time: " + e.timeToString(v.getEndTime()));
                        i++;
                    }
                    System.out.println("Best time for event: " + e.timeToString(getBestTime(e.getId())));
                    System.out.println("");
                }
            }
            if (noOne) System.out.println("There is no one event with '" + eventName + "' name");
        } catch (IOException e) {
            System.out.println("Correct error and try again!");
        }
    }
 
    /**
     * Finds best time for all participants
     * @param id
     * @return 
     */
    private Calendar getBestTimeForAll(int id){
        Calendar result = null;
        Event e = eventList.get(id);
        List<Vote> voters;
        VoteWrapper vw = eventVoters.get(id);
        voters = vw.getData();
        
        // transform all to min
        int duration = (e.getDuration().get(Calendar.MINUTE)) + 60*(e.getDuration().get(Calendar.HOUR_OF_DAY));
        int startEventTime = (e.getStartTime().get(Calendar.MINUTE)) + 60*(e.getStartTime().get(Calendar.HOUR_OF_DAY));
        int endEventTime = (e.getEndTime().get(Calendar.MINUTE)) + 60*(e.getEndTime().get(Calendar.HOUR_OF_DAY));
        
        int voterStartTime, voterEndTime;        
        for ( Vote v: voters){
            voterStartTime = (v.getStartTime().get(Calendar.MINUTE)) + 60*(v.getStartTime().get(Calendar.HOUR_OF_DAY));
            voterEndTime = (v.getEndTime().get(Calendar.MINUTE)) + 60*(v.getEndTime().get(Calendar.HOUR_OF_DAY)); 
            
            //System.out.println(startEventTime + ":" + endEventTime + " " + duration + " " + voterStartTime + ":" + voterEndTime);
            if ( startEventTime <= voterEndTime && voterStartTime <= endEventTime && (voterEndTime-voterStartTime) >= duration && (min(endEventTime, voterEndTime)-max(startEventTime, voterStartTime)) >= duration ){
                //System.out.println("Overlapping: " + startEventTime + ":" + endEventTime + " and " + voterStartTime + ":" + voterEndTime);
                startEventTime = max(startEventTime, voterStartTime);
                endEventTime = min(endEventTime, voterEndTime);
            } else {
                System.out.println("There is no best time for all");
                return result;
            }
        }
        
        result = (Calendar)e.getDate().clone();
        int hour = startEventTime/60;
        int min =  startEventTime%60;
        result.set(Calendar.HOUR_OF_DAY, hour);
        result.set(Calendar.MINUTE, min);        
        
        //System.out.println("Best time: " + e.timeToString(result));
        return result;
    }
    
    /**
     * return max of of a and b
     * @param a
     * @param b
     * @return 
     */
    private int max( int a,  int b){
        if (a>=b) return a;
        return b;              
    }
    
    /**
     * returns min of a and b
     * @param a
     * @param b
     * @return 
     */
    private int min (int a, int b){
        if (a<=b) return a;
        return b;
    }
    
    /**
     * Finds majority of participants and best time for them
     * @param id
     * @return
     * @throws IOException 
     */
    private Calendar getBestTimeForMajority(int id) throws IOException{
        Calendar result = null;
        Event e = eventList.get(id);
        List<Vote> voters;
        VoteWrapper vw = eventVoters.get(id);
        voters = vw.getData();
        
        // transform all to min
        int duration = (e.getDuration().get(Calendar.MINUTE)) + 60*(e.getDuration().get(Calendar.HOUR_OF_DAY));
        int eventStartTime = (e.getStartTime().get(Calendar.MINUTE)) + 60*(e.getStartTime().get(Calendar.HOUR_OF_DAY));
        int eventEndTime = (e.getEndTime().get(Calendar.MINUTE)) + 60*(e.getEndTime().get(Calendar.HOUR_OF_DAY)); 
        
        // get all possible event's start time for that day
        List<Integer> timePeriods = new ArrayList<Integer>();
        int minPeriod = 30;

        for (int i = eventStartTime; i + minPeriod <= eventEndTime; i = i + duration){
            timePeriods.add(i);
        }
        
        // number of voters who can be at event 'i'
        List<Integer> votersCountForTimePeriod = new ArrayList<Integer>();
        
        int voterStartTime, voterEndTime, votersNumber = 0;

        for ( int eventStart: timePeriods){
            votersCountForTimePeriod.add(votersNumber, 0);
            for ( Vote v: voters){
                voterStartTime = (v.getStartTime().get(Calendar.MINUTE)) + 60*(v.getStartTime().get(Calendar.HOUR_OF_DAY));
                voterEndTime = (v.getEndTime().get(Calendar.MINUTE)) + 60*(v.getEndTime().get(Calendar.HOUR_OF_DAY));
                if ( eventStart >= voterStartTime && (eventStart+duration) <= voterEndTime && (voterEndTime-voterStartTime) >= duration ){
                    int temp = votersCountForTimePeriod.get(votersNumber);
                    votersCountForTimePeriod.add(votersNumber, temp+1);
                }
            }
            votersNumber++;
        }
                
        //find max of votersCount
        int max = votersCountForTimePeriod.get(0), numberOfMax = 0;
        for (int i = 0; i < votersCountForTimePeriod.size(); i++ ){
            if ( max < votersCountForTimePeriod.get(i)){
                max = votersCountForTimePeriod.get(i);
                numberOfMax = i;
            }
        }
        
        result = (Calendar)e.getDate().clone();
        int hour = timePeriods.get(numberOfMax) / 60;
        int min =  timePeriods.get(numberOfMax) % 60;
        result.set(Calendar.HOUR_OF_DAY, hour);
        result.set(Calendar.MINUTE, min);        
        
        if (max != 0) {
            System.out.println("Majority: " + max + " persons.");
            return result;
        } else {
            System.out.println("There is no best time for majority.");    
            result  = null;
        }
        return result;
    }
    
    /**
     *  Returns id of unique Event by Name
     * @param name
     * @return 
     */
    private int selectEvent(String name){
        List<Event> eventListByName = getEventListByName(name);
        
        int choosenId = -1;
        if ( eventListByName.size() > 1 ){
            System.out.println("There is " + eventListByName.size() + "some events with similiar name.");            
            Scanner input = new Scanner(System.in);
            List<Integer> idList = getIdList(eventListByName);                         
            try{
                while (!idList.contains(choosenId)) {
                    eventListByName.forEach(e->System.out.println("id: " + e.getId() + ", name: " + e.getEventName() + ", date: " + e.dateToString(e.getDate())));
                    System.out.println("Choose correctly event id!");
                    choosenId = Integer.parseInt(input.nextLine());
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong event id. It must be an integer!");
            } catch (NoSuchElementException  e) {
                System.out.println("No such element exception");
            } 
            
        } else {
            try{
                if (eventListByName.size() == 0){
                    throw new IndexOutOfBoundsException();
                }else{
                    Event e = eventListByName.get(0);
                    choosenId = e.getId();
                }
            } catch(IndexOutOfBoundsException exp){
                System.out.println("No event with such name!");
            }
        }
       
        
        return choosenId;
    }
    
    /**
     * Returns sublist by name
     * @param name
     * @return 
     */
    private List getEventListByName(String name){
        List<Event> eventListByName = new ArrayList<Event>();
        boolean noOne = true;
        for (Event e: eventList) {
            if ( name.equals(e.getEventName())){
                noOne = false; 
                eventListByName.add(e);
            }
        }
        //if (noOne) System.out.println("There is no one event with '" + name + "' name");
                     
        return eventListByName;
    }   
    
    /**
     * Returns list of event's id for an List<Event>
     * @param list
     * @return 
     */
    private List getIdList(List<Event> list){
        List<Integer> idList = new ArrayList<Integer>();
        for(Event e: list){
            idList.add(e.getId());
        }
        return idList;
    }

}
