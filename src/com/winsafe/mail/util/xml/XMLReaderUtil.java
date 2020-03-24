package com.winsafe.mail.util.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
 

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader; 

public class XMLReaderUtil {
	private static Log logger = LogFactory.getLog(XMLReaderUtil.class);

	public static List getPropertyFilesList(String property_files_name) {
		List list = new ArrayList();
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(property_files_name);
			Element rootElement = document.getRootElement();
			for (Iterator i = rootElement.elementIterator("file"); i.hasNext();) {
				Element element = (Element) i.next();
				list.add(element.attributeValue("name"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static Map getPropertiesByFileName(String fileName,String path ) {
		Map map = new HashMap();
		SAXReader reader = new SAXReader();
		FileInputStream fis=null;
		try {
			logger.debug(fileName);
			 fis =new FileInputStream(path);
			Document document = reader.read(fis);
			fis.close();
			Element rootElement = document.getRootElement();
			for (Iterator i = rootElement.elementIterator("prop"); i.hasNext();) {
				Element element = (Element) i.next();
				String key = element.attributeValue("key");
				String value = element.attributeValue("value");
				map.put(key, value);
			}
		} catch (Exception e) {
			try {
				if(fis!=null)
					fis.close();
			} catch (IOException e1) {
					e1.printStackTrace();
				}
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * @author Kevin
	 * @param fileName
	 * @param context
	 * @return
	 */
	public static Map getPropertiesByFileName2(String fileName,String path) {
		Map map = new HashMap();
		SAXReader reader = new SAXReader();
		try {
			logger.debug(fileName);
			   
			Document document = reader.read(new FileInputStream(path));
			Element rootElement = document.getRootElement();
			for (Iterator i = rootElement.elementIterator("prop"); i.hasNext();) {
				Element element = (Element) i.next();
				String key = element.attributeValue("key");
				String value = element.attributeValue("value");
				map.put(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static void main(String[] args) {
		ClassLoader loader = XMLReaderUtil.class.getClassLoader();
		System.out.println(loader.getResource("PF.xml").getPath());
	}
}
