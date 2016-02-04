/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ncedu.tsarev.ctc;

import java.util.ArrayList;
import java.util.List;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * @author Алексей
 */
@Root
public class VoteWrapper {

    @ElementList(name="vote")
    private ArrayList<Vote> vote = new ArrayList<Vote>();
    
    public VoteWrapper(){
        this.vote = null;
    }
    
    public VoteWrapper( ArrayList<Vote> data){
        vote = data;
    }
        
    public ArrayList<Vote> getData() {
        return vote;
    }

    public void setData(ArrayList<Vote> data) {
        vote = data;
    }

}

