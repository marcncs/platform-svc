package com.winsafe.drp.action.assistant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.message.MessageElement;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;

import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.ws.DataBaseWebService;
import com.winsafe.drp.ws.DataBaseWebServiceLocator;
import com.winsafe.drp.ws.DataBaseWebServiceSoap_PortType;
import com.winsafe.drp.ws.Fwnumber;
import com.winsafe.drp.ws.GetDataSetResponseGetDataSetResult;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-10-24 下午02:54:07 www.winsafe.cn
 */
public class FindFWNReport {

	public List<Fwnumber> getFWNReport(String whereSql) {
		List<Fwnumber> list = new ArrayList<Fwnumber>();
		try {
			DataBaseWebService db = new DataBaseWebServiceLocator();
			DataBaseWebServiceSoap_PortType sp = db.getDataBaseWebServiceSoap();
			GetDataSetResponseGetDataSetResult result = sp.getDataSet(whereSql);
			if (result != null) {
				MessageElement[] me = result.get_any();
				Fwnumber fb = null;
				for (int i = 0; i < me.length; i++) {
					DOMReader xmlReader = new DOMReader();
					org.dom4j.Document doc = xmlReader.read(me[i].getAsDocument());
					Element root = doc.getRootElement();
					if (!root.getName().equals("diffgram")) {
						continue;
					}
					List<Element> listE = doc.selectNodes("//dataset");
					Iterator<Element> iter = listE.iterator();
					while (iter.hasNext()) {
						fb = new Fwnumber();
						Element element = (Element) iter.next();
						fb.setPronumber(element.elementText("PRO_Number"));
						fb.setFinddt(element.elementText("FindDT"));
						fb.setTelnumber(element.elementText("TelNumber"));
						fb.setArea(element.elementText("Area"));
						String findtype = element.elementText("FindType");

						if (findtype.equals("0")) {
							findtype = "固定电话";
						} else if (findtype.equals("1")) {
							findtype = "手机查询";
						} else {
							findtype = "网络查询";
						}
						fb.setFindtype(findtype);
						fb.setCompflag(element.elementText("CompFlag"));
						fb.setChktrue(element.elementText("ChkTrue"));
						list.add(fb);
					}
					

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	public String getWhereSql(HttpServletRequest request) throws Exception {
		// 客户名称 编号
		String customer = RequestTool.getString(request, "customer");
		// 是否包括历史查询
		int ishistory = RequestTool.getInt(request, "ishistory");

		// 查询次数
		int qcount = RequestTool.getInt(request, "qcount");
		// 查询电话
		String qphone = RequestTool.getString(request, "qphone");
		// 查询开始时间
		String qstartdate = RequestTool.getString(request, "qstartdate");
		// 查询结束时间
		String qenddate = RequestTool.getString(request, "qenddate");

		String strSql = "";

		if (ishistory == 1) {
			// 按查询电话查询

			if (!qphone.equals("")) {
				strSql = "select  PRO_Number, FindDT, TelNumber, FindType, CompFlag, ChkTrue,Area from View_QueryByCustomer"
						+ " where  TelNumber="
						+ "'"
						+ qphone
						+ "'"
						+ " and PRO_Number in (";

			} else {

				strSql = "select  PRO_Number, FindDT, TelNumber, FindType, CompFlag, ChkTrue,Area from View_QueryByCustomer"
						+ " where PRO_Number in (";
			}

			strSql = strSql
					+ "select  distinct PRO_Number from View_QueryByCustomer"
					+ " where  PRO_Number in (";

			strSql = strSql
					+ "select  distinct PRO_Number from View_QueryByCustomer"
					+ " where FindDT>="
					+ "'"
					+ DateUtil.formatDate(Dateutil.StringToDate(qstartdate),
							"yyyyMMdd")
					+ "000000'"
					+ " and FindDT<="
					+ "'"
					+ DateUtil.formatDate(Dateutil.StringToDate(qenddate),
							"yyyyMMdd") + "235959'";
			// 按查询电话查询
			if (!qphone.equals("")) {
				strSql = strSql + "  and  TelNumber=" + "'" + qphone + "'";
			}

			if (!customer.equals(""))// 分客户查询
			{

				strSql = strSql + " and left(PRO_Number,4) in (" + customer
						+ ") ) ";
				// 按查询电话查询
				if (!qphone.equals("")) {
					strSql = strSql + "  and  TelNumber=" + "'" + qphone + "'";
				}
				strSql = strSql + "group by PRO_Number having count(*)>="
						+ qcount + ") order by FindDT desc";

			} else {

				strSql = strSql + " ) ";
				// 按查询电话查询
				if (!qphone.equals("")) {
					strSql = strSql + "  and  TelNumber=" + "'" + qphone + "'";
				}
				strSql = strSql + " group by PRO_Number having count(*)>="
						+ qcount + " ) order by  FindDT desc";
			}

		} else {
			strSql = "select  PRO_Number, FindDT, TelNumber, FindType, CompFlag, ChkTrue,Area from View_QueryByCustomer"
					+ " where FindDT>="
					+ "'"
					+ DateUtil.formatDate(Dateutil.StringToDate(qstartdate),
							"yyyyMMdd")
					+ "000000'"
					+ " and FindDT<="
					+ "'"
					+ DateUtil.formatDate(Dateutil.StringToDate(qenddate),
							"yyyyMMdd") + "235959'";
			// 按查询电话查询
			if (!qphone.equals("")) {
				strSql = strSql + "  and  TelNumber=" + "'" + qphone + "'";
			}
			strSql = strSql + " and PRO_Number in (";

			strSql = strSql + "select  PRO_Number from View_QueryByCustomer";

			if (!customer.equals(""))// 分客户查询
			{
				strSql = strSql
						+ "  where FindDT>="
						+ "'"
						+ DateUtil.formatDate(
								Dateutil.StringToDate(qstartdate), "yyyyMMdd")
						+ "000000'"
						+ " and FindDT<="
						+ "'"
						+ DateUtil.formatDate(Dateutil.StringToDate(qenddate),
								"yyyyMMdd") + "235959'"
						+ " and  left(PRO_Number,4) in (" + customer + ")";
				if (!qphone.equals("")) {
					strSql = strSql + "  and  TelNumber=" + "'" + qphone + "'";
				}
				strSql = strSql + " group by PRO_Number having count(*)>="
						+ qcount + " ) order by  FindDT desc";
			} else {
				strSql = strSql
						+ "  where FindDT>="
						+ "'"
						+ DateUtil.formatDate(
								Dateutil.StringToDate(qstartdate), "yyyyMMdd")
						+ "000000'"
						+ " and FindDT<="
						+ "'"
						+ DateUtil.formatDate(Dateutil.StringToDate(qenddate),
								"yyyyMMdd") + "235959'";
				// 按查询电话查询
				if (!qphone.equals("")) {
					strSql = strSql + "  and  TelNumber=" + "'" + qphone + "'";
				}
				strSql = strSql + "  group by PRO_Number having count(*)>="
						+ qcount + " ) order by  FindDT desc";
			}

		}

		return strSql;

	}

	public List<Fwnumber> getFWNReportPage(List<Fwnumber> items, int pageSize,
			int currentPage) {
		int startIndex = (currentPage - 1) * pageSize;
		int offset = items.size() - startIndex;

		int pageCount = offset > pageSize ? pageSize : offset;

		List<Fwnumber> pageList = new ArrayList<Fwnumber>();
		for (int i = startIndex; i < startIndex + pageCount; i++) {
			pageList.add(items.get(i));
		}
		return pageList;
	}

}
