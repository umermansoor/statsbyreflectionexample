package com.mycompany.statsbyreflectionexample;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        // Start Measuring
        SomethingToMeasure o = new SomethingToMeasure();
        o.registerManagementElements();
        new Thread(o).start();
        
        ManagementStore.Element e = ManagementStore.getManagementObject("counters", 
                "numrequests");
        
        while(true) { //Show stats every 1 seconds
            try {
                Thread.sleep(1000);
                Object value = e.getMethod().invoke(e.getObject(), null);
                System.out.println("Value = " + value);
                
            } 
            catch (InterruptedException ignored) {}
            catch (Exception ignored) {}
        }
        
        
        
        
    }
}
