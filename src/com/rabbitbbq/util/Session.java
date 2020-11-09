/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rabbitbbq.util;

import com.rabbitbbq.model.Factory;
import com.rabbitbbq.model.Queue;
import static com.rabbitbbq.role.PathRole.SESSION_NODE;
import java.util.prefs.Preferences;

/**
 *
 * @author rizal
 */
public class Session {
    private Preferences p;
    
    public void saveFactory(Factory factory){
        p = Preferences.userRoot().node(SESSION_NODE);
        p.put("savedFactory", new Beautify().toString(factory));
    }
    public Factory getFactory(){
        p = Preferences.userRoot().node(SESSION_NODE);
        return (Factory) new Beautify().toClass(p.get("savedFactory", ""), Factory.class);
    }
    
    public void saveQueue(Queue queue){
        p = Preferences.userRoot().node(SESSION_NODE);
        p.put("savedQueue", new Beautify().toString(queue));
    }
    public Queue getQueue(){
        p = Preferences.userRoot().node(SESSION_NODE);
        return (Queue) new Beautify().toClass(p.get("savedQueue", ""), Queue.class);
    }
}
