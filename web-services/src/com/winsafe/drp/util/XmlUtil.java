package com.winsafe.drp.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

public class XmlUtil {
	
	private static Logger logger = Logger.getLogger(XmlUtil.class);
	
	public static <T> T xmlToObject(Class<T> clazz, InputStream in) {
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T)unmarshaller.unmarshal(in);
		} catch (JAXBException e) {
			logger.error("xml转成对像时发生异常：", e);
		}
		return t;
	}
	
	public static <T> void objectToXml(Class<T> clazz, Object obj, OutputStream os) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(obj, os);
		} catch (JAXBException e) {
			logger.error("对像转成xml时发生异常：", e);
		}
	}
	
	public static <T> String objectToXml(Class<T> clazz, Object obj) {
		try {
			StringWriter sw = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(clazz);
			Marshaller marshaller = context.createMarshaller();
//			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(obj, sw);
			return sw.toString();
		} catch (JAXBException e) {
			logger.error("对像转成xml时发生异常：", e);
		}
		return "";
	}
	
	public static <T> String objectToXml2(Class<T> clazz, Object obj) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
//			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			marshaller.marshal(obj, os);
			return os.toString("UTF-8");
		} catch (Exception e) {
			logger.error("对像转成xml时发生异常：", e);
		} 
		return "";
	}
	
	public static <T> T xmlToObject(Class<T> clazz, Reader reader) {
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T)unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			logger.error("xml转成对像时发生异常：", e);
		}
		return t;
	}

}
