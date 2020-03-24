package com.winsafe.hbm.entity;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

//import com.winsafe.hbm.entity.HibernateUtil;

public class HibernateUtil {

  private static SessionFactory sessionFactory = null;

  public static final ThreadLocal session = new ThreadLocal();

  private static Logger logger = Logger.getLogger(HibernateUtil.class);

  public static final ThreadLocal transactionThread = new ThreadLocal();

  public static Session getAloneSessionBySessionFactory() throws
      HibernateException {
    if (sessionFactory == null)
    {
      logger.error("sessionFactory is null! Initial is error");
      return null;
    }
    return sessionFactory.openSession();
  }

  public static Session currentSession() throws HibernateException {
    if (sessionFactory == null) {

      if (getSessionFactory() == false) {
        throw new HibernateException(
            "Exception geting SessionFactory from JNDI ");
      }
    }
    Session s = (Session) session.get();
    // Open a new Session, if this Thread has none yet
    if (s == null) {
      s = sessionFactory.openSession();
      session.set(s);
    }
    return s;
  }

  public static Session currentSession(boolean isCommit) throws
      HibernateException {
    if (sessionFactory == null) {

      if (getSessionFactory() == false) {
        throw new HibernateException(
            "Exception geting SessionFactory from JNDI ");
      }
    }
    Session s = (Session) session.get();
    if (s == null) {
      s = sessionFactory.openSession();
      session.set(s);
    }
    if (isCommit) {
      currentTransaction();
    }
    return s;
  }

  public static void commitTransaction() throws HibernateException {
    Transaction tx = (Transaction) transactionThread.get();
    transactionThread.set(null);
    if (tx != null) {

      tx.commit();
    }
    Session s = currentSession();
    if (s != null) {
    	s.flush();
        s.clear();
    }	
  }

  public static void rollbackTransaction() throws HibernateException {
    Transaction tx = (Transaction) transactionThread.get();
    transactionThread.set(null);
    if (tx != null) {
      tx.rollback();
    }
    Session s = currentSession();
    if (s != null) {
        s.clear();
    }
  }

  public static Transaction currentTransaction() throws HibernateException {
    Transaction tx = (Transaction) transactionThread.get();
    if (tx == null) {
      tx = currentSession().beginTransaction();
      transactionThread.set(tx);
    }
    return tx;
  }

  public static void closeSession() {
    try {
      Session s = (Session) session.get();
      session.set(null);
      if (s != null) {
        s.close();
	
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static boolean getSystemSessionFactory() {
    try {

      Context inttex = new InitialContext();
      sessionFactory = (SessionFactory) inttex
          .lookup("HibernateSessionFactory");
    }
    catch (NamingException e) {
      return false;
    }
    return true;
  }

  private static boolean getSessionFactory() {
    try {
      sessionFactory = new Configuration().configure()
          .buildSessionFactory();

    }
    catch (HibernateException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
