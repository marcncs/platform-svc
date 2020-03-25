package com.winsafe.drp.language;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InternationalAction extends Action{

	private Log log = LogFactory.getFactory().getInstance(this.getClass().getName());

    public ActionForward execute(ActionMapping mapping,
                 ActionForm form,
                 HttpServletRequest request,
                 HttpServletResponse response)
    throws Exception {

    HttpSession session = request.getSession();
    Locale locale = getLocale(request);

    String language = null;
    String country = null;
    String page = null;

    try {
        language = request.getParameter("language");
//            country = (String)
//              PropertyUtils.getSimpleProperty(form, "country");
         //   page = (String)
          //    PropertyUtils.getSimpleProperty(form, "page");
        } catch (Exception e) {
           log.error(e.getMessage(), e);
        }
//System.out.println("language===="+language+"country=="+country+"page="+page);
         if (language != null && language.length() > 0) {
           locale = new java.util.Locale(language, "");
         }

        session.setAttribute(Globals.LOCALE_KEY, locale);

        return mapping.findForward("international");

    }
}
