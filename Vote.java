/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ncedu.tsarev.ctc;

import java.util.Calendar;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *
 * @author Алексей
 */
@Root
public class Vote {
    @Element (name = "startTime")
    private Calendar startTime;
    @Element (name = "endTime")
    private Calendar endTime;

    public Vote(){
        this.startTime = null;
        this.endTime = null;
    }
    /**
     * Constructor
     * @param startTime
     * @param endTime 
     */
    public Vote( Calendar startTime,  Calendar endTime){
        this.startTime = startTime;
        this.endTime = endTime;
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
     * @return the endtime
     */
    public Calendar getEndTime() {
        return endTime;
    }

    /**
     * @param endtime the endtime to set
     */
    public void setEndTime(Calendar endtime) {
        this.endTime = endtime;
    }
}
