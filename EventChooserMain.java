/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ncedu.tsarev.ctc;

import java.util.Scanner;

/**
 *
 * @author Алексей
 */
public class EventChooserMain {
    
    /**
     * Receives users command until 'Esc' entered
     * @param args 
     */
    public static void main(String[] args){
        Executor e = new Executor();
        Scanner input = new Scanner(System.in);
        String userData = "-help";
        e.about();
        while (!"Esc".equals(userData)){                
                e.execute(userData);
                userData = input.nextLine();                
        }
    }
    
        // -a dr 15.03.2016 9:00 12:00 1:00
        // -s dr 9:00 11:30

}
