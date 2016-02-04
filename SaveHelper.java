/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ncedu.tsarev.ctc;

import java.io.File;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author Алексей
 */
public class SaveHelper {
    EventChooserImpl chooser;
    
    public SaveHelper(EventChooserImpl chooser){
        this.chooser = chooser;
    }
    
    public void save(){
        File xmlFile = new File( "EventChooserImpl.xml");
        try
            {
                Serializer serializer = new Persister();
                serializer.write(chooser, xmlFile);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }        
        }
}
