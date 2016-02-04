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
public class LoadHelper {
    EventChooserImpl e;
    public EventChooserInterface load(){

        File xmlFile = new File("EventChooserImpl.xml");
        
         if (xmlFile.exists())
        {
            try
            {
                Serializer serializer = new Persister();
                e = serializer.read(EventChooserImpl.class, xmlFile);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
         
        return e;
    }
}
