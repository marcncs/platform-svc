package com.winsafe.hbm.filters;

import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.RedisUtil;

import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserCounterListener
  implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener
{
  private final transient Log log = LogFactory.getLog(UserCounterListener.class);
  private transient ServletContext servletContext;
  private int counter;
  private Set users;

  public synchronized void contextInitialized(ServletContextEvent sce)
  {
    this.servletContext = sce.getServletContext();
    this.servletContext.setAttribute("userCounter", Integer.toString(this.counter));
  }

  public synchronized void contextDestroyed(ServletContextEvent event) {
    this.servletContext = null;
    this.users = null;
    this.counter = 0;
    RedisUtil.closePool();
  }

  synchronized void incrementUserCounter() {
    this.counter = 
      Integer.parseInt((String)this.servletContext.getAttribute("userCounter"));
    this.counter += 1;
    this.servletContext.setAttribute("userCounter", Integer.toString(this.counter));

    if (this.log.isDebugEnabled())
      this.log.debug("User Count: " + this.counter);
  }

  synchronized void decrementUserCounter()
  {
    int counter = 
      Integer.parseInt((String)this.servletContext.getAttribute("userCounter"));
    --counter;

    if (counter < 0) {
      counter = 0;
    }
    this.servletContext.setAttribute("userCounter", Integer.toString(counter));

    if (this.log.isDebugEnabled())
      this.log.debug("User Count: " + counter);
  }

  synchronized void addUsername(Object user)
  {
    this.users = ((Set)this.servletContext.getAttribute("userNames"));

    if (this.users == null) {
      this.users = new HashSet();
    }

    if ((this.log.isDebugEnabled()) && 
      (this.users.contains(user))) {
      this.log.debug("User already logged in, adding anyway...");
    }

    this.users.add(user);
    this.servletContext.setAttribute("userNames", this.users);
    incrementUserCounter();
  }

  synchronized void removeUsername(Object user) {
    this.users = ((Set)this.servletContext.getAttribute("userNames"));

    if (this.users != null) {
      this.users.remove(user);
    }

    this.servletContext.setAttribute("userNames", this.users);
    decrementUserCounter();
  }

  public void attributeAdded(HttpSessionBindingEvent event)
  {
    if (event.getName().equals("users")) {
      UsersBean ub = (UsersBean)event.getValue();
      addUsername(ub.getLoginname());
    }
  }

  public void attributeRemoved(HttpSessionBindingEvent event)
  {
  }

  public void sessionCreated(HttpSessionEvent event)
  {
  }

  public void sessionDestroyed(HttpSessionEvent event)
  {
    UsersBean ub = (UsersBean)event.getSession().getAttribute("users");
    if (ub != null)
      removeUsername(ub.getLoginname());
  }

  public void attributeReplaced(HttpSessionBindingEvent arg0)
  {
  }
}
