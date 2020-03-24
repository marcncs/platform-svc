package com.winsafe.drp.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale; 
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.DateFormat;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.users.ListOrganAction;
import com.winsafe.drp.dao.AppLeftMenu;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author jelli
 * @version 2009-8-10 下午12:37:11 www.winsafe.cn
 */

public class BaseAction extends Action {
	private static Logger logger = Logger.getLogger(BaseAction.class);
	protected UsersBean users = null;
	protected Integer userid;
	private HttpServletRequest baserequest = null;
	protected Map map = null;
	protected Map tmpMap = null;
	protected String menuType = "";

	protected WritableCellFormat wcfN = null;
	protected WritableCellFormat QWCF = null;
	protected WritableCellFormat QWCF2 = null;
	protected WritableCellFormat wcfFC = null;
	protected WritableCellFormat wchT = null;
	protected WritableCellFormat wcfDF = null;
	protected WritableCellFormat seachT = null;
	protected WritableCellFormat aec = null;
	
	//国际化对象
	protected Locale locale;
	protected ResourceBundle resBundle;
	protected String localeLanguage;
	

	protected void initdata(HttpServletRequest request) {
		try {
			this.baserequest = request;
			users = UserManager.getUser(request);
			userid = users.getUserid();
			setValues();
			setFormat();
			setMenuValue();
			//初始化国际化对象
			locale = getCurUserLocale(request);
			resBundle = ResourceBundle.getBundle(
					"global.app.ApplicationResource", locale);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void initdata(HttpServletRequest request,String requestType) {
		try {
			this.baserequest = request;
			this.menuType = requestType;
			users = UserManager.getUser(request);
			userid = users.getUserid();
			setValues();
			setFormat();
			setMenuValue();
			//初始化国际化对象
			locale = getCurUserLocale(request);
			resBundle = ResourceBundle.getBundle(
					"global.app.ApplicationResource", locale);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void initdataNoUser(HttpServletRequest request) {
		try {
			//初始化国际化对象
			locale = getCurUserLocale(request);
			localeLanguage = locale.getLanguage().toString();
			resBundle = ResourceBundle.getBundle(
					"global.app.ApplicationResource", locale);
			if (localeLanguage != null) {
				request.getSession().setAttribute("localeLanguage", this.localeLanguage.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setFormat() throws WriteException {
		NumberFormat nf = new NumberFormat("#,##0.00");
		wcfN = new WritableCellFormat(nf);

		NumberFormat QNF = new NumberFormat("#,##0.00");
		QWCF = new WritableCellFormat(QNF);
		
		NumberFormat QNF2 = new NumberFormat("#,##0");
		QWCF2 = new WritableCellFormat(QNF2);

		WritableFont wfct = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);
		wchT = new WritableCellFormat(wfct);
		wchT.setAlignment(Alignment.CENTRE);
		
		WritableFont wfc = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		wcfFC = new WritableCellFormat(wfc);
		wcfFC.setBackground(Colour.GREY_25_PERCENT);
		wcfFC.setAlignment(jxl.format.Alignment.CENTRE);
		wcfFC.setWrap(true);
		
		WritableFont wfcs = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);
		seachT= new WritableCellFormat(wfcs);

		
		DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
		wcfDF = new WritableCellFormat(df);
		
		aec = new WritableCellFormat(wfc);
		aec.setWrap(true);
		aec.setAlignment(Alignment.CENTRE);
		aec.setVerticalAlignment(VerticalAlignment.CENTRE);
	}

	private void setValues() {
		Map parmmap = baserequest.getParameterMap();
		Set keyset = parmmap.keySet();
		try {
			for (Iterator it = keyset.iterator(); it.hasNext();) {
				Object keyobj = it.next();
				Object valueobj = parmmap.get(keyobj);
				if (valueobj instanceof Object[]) {
					valueobj = ((Object[]) valueobj)[0];
				}
				if ("button".equals(keyobj) || "topage".equals(keyobj)
						|| "submit.x".equalsIgnoreCase(keyobj.toString())
						|| "submit.y".equalsIgnoreCase(keyobj.toString())) {
					continue;
				}
				baserequest.setAttribute(keyobj.toString(), valueobj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		baserequest.setAttribute("porganid", users.getMakeorganid());
		baserequest.setAttribute("porganname", users.getMakeorganname());
		baserequest.setAttribute("pusername", users.getRealname());
		baserequest.setAttribute("ptime", DateUtil.getCurrentDateTime());
		
		
	}
	private void setMenuValue(){
		String menusTrace ="";
		if(!StringUtil.isEmpty(menuType)){
			menusTrace = AppLeftMenu.getOpreateHtmlTarace(baserequest.getServletPath().concat("?type="+menuType));
		}else{
			menusTrace = AppLeftMenu.getOpreateHtmlTarace(baserequest.getServletPath());
		}
		if(!StringUtil.isEmpty(menusTrace)){
			String[] objs = menusTrace.split(Constants.MENUS_HTML_SEPARATE);
			menusTrace = "";
			int length = objs.length;
			if(length > 1){
				for(int i=0 ; i<length-1 ; i++){
					menusTrace +=  Constants.MENUS_HTML_SEPARATE + objs[i];
				}
				String operateName = objs[length-1];
				String menu = objs[length-2];
				baserequest.setAttribute("menusTrace", menusTrace.substring(Constants.MENUS_HTML_SEPARATE.length()));
				baserequest.setAttribute("menu", menu);
				baserequest.setAttribute("operateName", operateName);
			}
		}
	}

	protected String getOrVisitOrgan(String makeorgan) {
		if (users.getVisitorgan() != null && users.getVisitorgan().length() > 0) {
			return " or instr(" + makeorgan + ",'" + users.getVisitorgan()
					+ "')>=1 ";
		}
		return "";
	}
	
	protected String getAndVisitOrgan(String makeorgan) {
		if (users.getVisitorgan() != null && users.getVisitorgan().length() > 0) {
			return " and instr(" + makeorgan + ",'" + users.getVisitorgan()
					+ "')>=1 ";
		}
		return "";
	}
	
	protected String getOrVisitOrganInstr(String makeorgan) {
		if (users.getVisitorgan() != null && users.getVisitorgan().length() > 0) {
			return " or instr('" + users.getVisitorgan()
					+ "'," + makeorgan + ")>=1 ";
		}
		return "";
	}

	protected String getVisitOrgan(String makeorgan) {
		if (users.getVisitorgan() != null && users.getVisitorgan().length() > 0) {
			return " instr(" + makeorgan + ",'" + users.getVisitorgan()
					+ "')>=1 ";
		}
		return "";
	}
	protected String getVisitOrganInstr(String makeorgan) {
		if (users.getVisitorgan() != null && users.getVisitorgan().length() > 0) {
			return "  instr('" + users.getVisitorgan()
					+ "'," + makeorgan + ")>=1 ";
		}
		return "";
	}


	@SuppressWarnings("unchecked")
	protected String getWhereSql2(String[] tablename) throws Exception {
		map = new HashMap(baserequest.getParameterMap());
		tmpMap = EntityManager.scatterMap(map);
		String whereSql = EntityManager.getTmpWhereSql(map, tablename);
		return whereSql;
	}

	@SuppressWarnings("unchecked")
	protected String getWhereSql(String[] tablename) throws Exception {
		map = new HashMap(baserequest.getParameterMap());

		String BeginDate = baserequest.getParameter("BeginDate");
		String EndDate = baserequest.getParameter("EndDate");
		if (BeginDate == null) {
			BeginDate = DateUtil.getMonthFirstDay();
			map.put("BeginDate", BeginDate);
		}
		if (EndDate == null) {
			EndDate = DateUtil.getMonthLastDay();
			map.put("EndDate", EndDate);
		}
		tmpMap = EntityManager.scatterMap(map);
		String whereSql = EntityManager.getTmpWhereSql(map, tablename);

		return whereSql;
	}

	protected String getTimeCondition(String timeCondition) {
		return DbUtil.getTimeCondition(map, tmpMap, timeCondition);

	}

	protected String getPriceCondition(String priceCondition) {
		return DbUtil.getTimeCondition(map, tmpMap, priceCondition);
	}

	protected String getKeyWordCondition(String... fields) {
		return DbUtil.getOrBlur(map, tmpMap, fields);
	}
	
	protected int getInt(String fields) {
		String parameter = baserequest.getParameter(fields);
		if ( parameter == null || parameter.equals("") ){
			return -1;
		}
		try{
			return Integer.valueOf(parameter);
		}catch ( Exception e ){
			return -1;
		}
	}
	
	public static Locale getCurUserLocale(HttpServletRequest pRequest) {
		HttpSession session = pRequest.getSession();
		Locale tmpLocale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
		return tmpLocale;
	}
}
