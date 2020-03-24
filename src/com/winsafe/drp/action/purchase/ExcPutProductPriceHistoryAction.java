package com.winsafe.drp.action.purchase;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sun.org.apache.commons.beanutils.BeanUtils;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductPriceHistory;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.ProductPriceHistory;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

/**
 * @author : jerry
 * @version : 2009-8-22 下午02:31:07 www.winsafe.cn
 */
public class ExcPutProductPriceHistoryAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		initdata(request);
		try {
			String condition = " ph.unitId=br.tagsubkey and br.tagname='CountUnit'";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ProductPriceHistory" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getOrBlur(map, tmpMap, "productId", "productName",
					"memo");
			String timeCondition = getStartAndEndTimeCondition(map, tmpMap,
					"startTime","endTime");

			whereSql = whereSql + blur + timeCondition + condition;
			
			whereSql = DbUtil.getWhereSql(whereSql);
			AppProductPriceHistory abr = new AppProductPriceHistory();
			AppProduct ap = new AppProduct();
			AppUsers au = new AppUsers();

			List apls = abr.getProductPriceHistoryList(whereSql);
			/*List alpl = new ArrayList();
			for (int i = 0; i < apls.size(); i++) {
				ProductPriceHistory pph = null;
				BaseResource br = null;
				Object[] o = (Object[]) apls.get(i);
				pph = (ProductPriceHistory) BeanUtils.cloneBean(o[0]);
				br = (BaseResource) o[1];
				pph.setUnitName(br.getTagsubvalue());
				alpl.add(pph);
			}*/
			
			
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 2,"系统设置>>导出产品价格");
			if (apls.size() > Constants.EXECL_MAXCOUNT) {
				request.setAttribute("result", "当前记录数超过"
						+ Constants.EXECL_MAXCOUNT + "条，请重新查询！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ProductPriceHistory.xls");
			response.setContentType("application/msexcel");
			writeXls(apls, os, request);
			os.flush();
			os.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	public void writeXls(List list, OutputStream os,
			HttpServletRequest request) throws Exception,
			RowsExceededException, WriteException {
		WritableWorkbook workbook = Workbook.createWorkbook(os);
		
		int snum = 1;
		snum = list.size() / 50000;
		if (list.size() % 50000 >= 0) {
			snum++;
		}
		WritableSheet[] sheets = new WritableSheet[snum];
		for (int j = 0; j < snum; j++) {
			sheets[j] = workbook.createSheet("sheet" + j, j);
			int currentnum = (j + 1) * 50000;
			if (currentnum >= list.size()) {
				currentnum = list.size();
			}
			int start = j * 50000;
			
			sheets[j].mergeCells(0, start, 6, start);
			sheets[j].addCell(new Label(0, start, "产品价格资料",wchT));
			sheets[j].addCell(new Label(0, start+1, "产品名称:",seachT));
			sheets[j].addCell(new Label(1, start+1, request.getParameter("productName")));
			sheets[j].addCell(new Label(2, start+1, "日期:", seachT));
			sheets[j].addCell(new Label(3, start+1, request.getParameter("BeginDate")));	
			sheets[j].addCell(new Label(4, start+1, "关键字:", seachT));
			sheets[j].addCell(new Label(5, start+1, request.getParameter("KeyWord")));		
			
			sheets[j].addCell(new Label(0, start+2, "导出机构:", seachT));
			sheets[j].addCell(new Label(1, start+2, request.getAttribute("porganname").toString()));
			sheets[j].addCell(new Label(2, start+2, "导出人:", seachT));
			sheets[j].addCell(new Label(3, start+2, request.getAttribute("pusername").toString()));
			sheets[j].addCell(new Label(4, start+2, "导出时间:", seachT));
			sheets[j].addCell(new Label(5, start+2, DateUtil.getCurrentDateTime()));

			sheets[j].addCell(new Label(0, start+3, "产品编号",wcfFC));
			sheets[j].addCell(new Label(1, start+3, "产品名",wcfFC));
			sheets[j].addCell(new Label(2, start+3, "单位",wcfFC));
			sheets[j].addCell(new Label(3, start+3, "价格",wcfFC));
			sheets[j].addCell(new Label(4, start+3, "开始时间",wcfFC));
			sheets[j].addCell(new Label(5, start+3, "结束时间",wcfFC));
			sheets[j].addCell(new Label(6, start+3, "备注",wcfFC));


			for (int i = start; i < currentnum; i++) {
				int row = i - start + 4;
				Object[] oo = (Object[]) list.get(i);
				ProductPriceHistory p = (ProductPriceHistory) oo[0];
				BaseResource br = (BaseResource) oo[1];

				sheets[j].addCell(new Label(0, row, p.getProductId()));
				sheets[j].addCell(new Label(1, row, p.getProductName()));
				sheets[j].addCell(new Label(2, row, br.getTagsubvalue()));
				sheets[j].addCell(new Label(3, row, String.valueOf(p.getUnitPrice())));
				sheets[j].addCell(new Label(4, row, DateUtil.formatDate(p.getStartTime())));
				sheets[j].addCell(new Label(5, row, DateUtil.formatDate(p.getEndTime())));
				sheets[j].addCell(new Label(6, row, p.getMemo()));

			}
		}
		workbook.write();
		workbook.close();
		os.close();
	}
	public String setNull(Object obj){
		return obj==null?"":obj.toString();
	}
	
	
	//创建日期条件
	  public String getStartAndEndTimeCondition(Map map, Map tmpMap, String starttime, String endtime) {
		    StringBuffer buf = new StringBuffer();
		    if (map.containsKey("BeginDate")) {
		        String timeField = (String) tmpMap.get("BeginDate");
		        if (timeField != null && !timeField.equals("")) {

		          buf.append(starttime + "<='" + timeField + "'");
		          buf.append(" and ");
		          
		          buf.append("'" + timeField + "'"  + "<=" + endtime);
		          buf.append(" and ");
		        }
		      }
		      
		    return buf.toString().toLowerCase();
		  }
}
