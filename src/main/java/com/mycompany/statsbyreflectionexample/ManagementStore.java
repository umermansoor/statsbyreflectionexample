package com.mycompany.statsbyreflectionexample;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Maintains a Map of key value pairs that define the set of management 
 * objects available, grouped together by group name. Groups make it easy to 
 * iterate over certain sections without iterating through the entire Map.
 * 
 * Each key is associated with a {@link Element} which uses reflection 
 * to invoke a method on a target object to retrieve a runtime value.  
 * 
 * @threadsafe
 * @author Umer Mansoor
 */
public class ManagementStore
{
    /**
     * The type of elements held in {@link ManagementStore}.
     */
    public static class Element
    {
        private Object object;
        private Method method;

        /**
         * Get the value of object
         * @return the value of object
         */
        public Object getObject()
        {
            return object;
        }

        /**
         * Set the value of object
         * @param object new value of object
         */
        public void setObject(Object object)
        {
            this.object = object;
        }

        /**
         * Get the value of method
         * @return the value of method
         */
        public Method getMethod()
        {
            return method;
        }

        /**
         * Set the value of method
         * @param method new value of method
         */
        public void setMethod(Method method)
        {
            this.method = method;
        }
    }

    static ConcurrentHashMap<String, ConcurrentHashMap<String, ManagementStore.Element>> 
            managementData = new 
            ConcurrentHashMap<String,ConcurrentHashMap<String, ManagementStore.Element>>();

    /**
     * Returns a new, empty {@link Element}
     */
    public static ManagementStore.Element newManagementObject()
    {
        return new ManagementStore.Element();
    }

    /**
     * Adds new {@link Element} to the map.
     * 
     * @param group Group for this Element
     * @param key
     * @param value 
     */
    public static void addManagementObject(String group, String key, 
            ManagementStore.Element value)
    {
        if(!managementData.containsKey(group))
        {
            managementData.put(group,new ConcurrentHashMap<String, ManagementStore.Element>());
        }
        ConcurrentHashMap<String, ManagementStore.Element> groupManagementObjects = 
                managementData.get(group);
        groupManagementObjects.put(key, value);
    }

    /**
     * Returns an enumeration of all keys for the specified group or {@code null} 
     * if this map contains no mapping for the group.
     */
    public static Enumeration<String> keysForGroup(String group)
    {
       ConcurrentHashMap<String, ManagementStore.Element> groupManagementObjects = 
               managementData.get(group);
       
       if(groupManagementObjects == null) {
           return null;
       }
 
       return groupManagementObjects.keys();
    }

    /**
     * Returns an enumeration of all keys in the map.
     */
    public static Enumeration<String> groups()
    {
        return managementData.keys();
    }
    
    /**
     * Returns {@link Element} to which the specified key is mapped, in
     * the specified group.
     * 
     * @throws NullPointerException if the specified key is null
     */
    public static Element getManagementObject(String group, String key)
    {
        return managementData.get(group).get(key);
    }
}