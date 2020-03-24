package com.winsafe.drp.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CollectionUtils {
	
	public static Collection union(Collection collection, Collection collection1){
		return union(collection, collection1, "id", true);
	}
	
	public static Collection union(Collection collection, Collection collection1,String key,boolean isRepeat)
    {
        ArrayList arraylist = new ArrayList();
        Map resultMap = new HashMap();
        Map map = getCardinalityMap(collection,key);
        Map map1 = getCardinalityMap(collection1,key);
        HashSet hashset = new HashSet(collection);
        hashset.addAll(collection1);
        for(Iterator iterator = hashset.iterator(); iterator.hasNext();)
        {
            Object obj = iterator.next();
            int i = 0;
            Object keyVal = getObjVal(obj, key);
            for(int j = Math.max(getFreq(keyVal, map), getFreq(keyVal, map1)); i < j; i++){
            	arraylist.add(obj);
            	resultMap.put(keyVal, obj);
            }
        }
        if(isRepeat){
        	return resultMap.values();
        }else {
			return arraylist;
		}
    }
	public static Collection intersection(Collection collection, Collection collection1){
		return intersection(collection, collection1, "id", true);
	}
	
	public static Collection intersection(Collection collection, Collection collection1,String key,boolean isRepeat)
    {
        ArrayList arraylist = new ArrayList();
        Map resultMap = new HashMap();
        Map map = getCardinalityMap(collection,key);
        Map map1 = getCardinalityMap(collection1,key);
        HashSet hashset = new HashSet(collection);
        hashset.addAll(collection1);
        for(Iterator iterator = hashset.iterator(); iterator.hasNext();)
        {
            Object obj = iterator.next();
            Object keyVal = getObjVal(obj, key);
            int i = 0;
            for(int j = Math.min(getFreq(keyVal, map), getFreq(keyVal,map1)); i < j; i++){
            	arraylist.add(obj);
            	resultMap.put(keyVal, obj);
            }
        }
        if(isRepeat){
        	return resultMap.values();
        }else {
			return arraylist;
		}
    }
	
	public static Map getCardinalityMap(Collection collection,String key) 
    {
        HashMap hashmap = new HashMap();
        for(Iterator iterator = collection.iterator(); iterator.hasNext();)
        {
            Object obj = iterator.next();
            Object keyVal = getObjVal(obj, key);
            Integer integer = (Integer)hashmap.get(keyVal);
            if(null == integer)
                hashmap.put(keyVal, new Integer(1));
            else
                hashmap.put(keyVal, new Integer(integer.intValue() + 1));
        }

        return hashmap;
    }
	
	private static final int getFreq(Object keyVal,Map map)
    {
        try
        {
            return ((Integer)map.get(keyVal)).intValue();
        }
        catch(Exception e) { 
        	
        }
        return 0;
    }
	
	private static Object getObjVal(Object obj , String filedName){
		 Object keyVal = null;
			try {
				Field  field = obj.getClass().getDeclaredField(filedName);
				field.setAccessible(true);
				keyVal = field.get(obj);
			} catch (Exception e) {
			}
		return keyVal;
	}
	
	
}
