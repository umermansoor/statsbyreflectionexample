/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.statsbyreflectionexample;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * An example class which measures something.
 *
 * @author umermansoor
 */
public class SomethingToMeasure implements Runnable {

    private AtomicInteger numberOfRequests = new AtomicInteger(0);

    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            // do some work... 
            try {
                Thread.sleep(200); // ... let's just pretend that we're doing work
            } catch (InterruptedException ie) {
                Thread.interrupted();
            }

            numberOfRequests.incrementAndGet();
        }
    }

    public int getNumberOfRequests() {
        return numberOfRequests.intValue();
    }

    public void registerManagementElements() {
        try {
            ManagementStore.Element mo = ManagementStore.newManagementObject();
            mo.setMethod(this.getClass().getMethod("getNumberOfRequests", null));
            mo.setObject(this);
            ManagementStore.addManagementObject("counters", "numrequests", mo);
        } catch (NoSuchMethodException e) {
            System.out.println("unable to create management object");
            //logger.error("unable to create management object");
        }
    }
}
