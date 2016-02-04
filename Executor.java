/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ncedu.tsarev.ctc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Алексей
 */
public class Executor {
    
    EventChooserImpl ec;
       
    public Executor(){
         ec = new EventChooserImpl();
    }
    
    public static void execute(String[] args){
       Executor e = new Executor();
        Scanner input = new Scanner(System.in);
        String userData = "-help";
        e.about();
        while (!"Esc".equals(userData)){                
                e.execute(userData);
                userData = input.nextLine();   
        }        
    }
    
    public void execute(String data){
        SaveHelper sh = new SaveHelper(ec);     
        LoadHelper lh = new LoadHelper();
        String delims = "([ ])+";
        String[] t = data.split(delims);
        ArrayList<String> tokens = new ArrayList<String>();
        for (String s: t){
            tokens.add(s);
        }       
        try {
            switch(tokens.get(0)){
                case "-a":  
                    if (tokens.size() != 6) throw new IOException();
                        ec.addEvent(tokens.get(1), tokens.get(2), tokens.get(3), tokens.get(4), tokens.get(5));
                        System.out.println("Ok. Enter next command:");
                    break;
                case "-s":
                    if (tokens.size() != 4) throw new IOException();
                        ec.selectTime(tokens.get(1), tokens.get(2), tokens.get(3));
                        System.out.println("Ok. Enter next command:");
                    break;
                case "-t":
                    if (tokens.size() != 2) throw new IOException();
                        ec.getBestTime(tokens.get(1));
                        System.out.println("Ok. Enter next command:");
                    break;
                case "-i":
                    if (tokens.size() != 2) throw new IOException();
                    ec.getEventInfo(tokens.get(1));
                    System.out.println("Ok. Enter next command:");
                    break;
                case "-save":                    
                    sh.save();
                    System.out.println("Ok. Enter next command:");
                    break;
                case "-load":
                    ec = (EventChooserImpl) lh.load();   
                case "Esc":                    
                    sh.save();
                    break;
                case "-help":
                    help();
                    break;
                default:
                    System.out.println("Wrong command! Correct your command, please.");
                    help();
                    break;
        } 
        } catch (IOException e) {
            System.out.println("Wrong number of arguments!");
        } catch (NullPointerException e2){
            System.out.println("You need more arguments");
        }
    }   
    
    /**
     * Description of allowable commands for execute method
     */
    public void help(){
        System.out.println("Input '-a eventName date startTime endTime duration' to add new event");
        System.out.println("Input '-s eventName startTime endTime' to select time for an event");
        System.out.println("Input '-t eventName' to show the best time for an event");
        System.out.println("Input '-i eventName' to show event info");
        System.out.println("Input '-save' to save all info");        
        System.out.println("Input '-load' to save all info");        
        System.out.println("NOTE: 'date' in format dd.mm.yyyy");
        System.out.println("NOTE: 'startTime','endtime' and 'duration' in format hh.mm (NOTE: min time interval - 30m)");  
        System.out.println("---------------------------------------------------------------------------");
    }
    
    public void about(){
        System.out.println("This is 2-nd version of event chooser.\nTo quit enter 'Esc'\n\n-------------------");
    }    
}
