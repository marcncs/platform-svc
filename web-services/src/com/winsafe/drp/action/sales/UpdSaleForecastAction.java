package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSaleForecast;
import com.winsafe.drp.dao.AppSaleForecastDetail;
import com.winsafe.drp.dao.SaleForecast;
import com.winsafe.drp.dao.SaleForecastDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-8-8 下午06:21:19
 * www.winsafe.cn
 */
public class UpdSaleForecastAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);
		try {
			AppSaleForecast appSaleForecast = new AppSaleForecast();
			Integer sid = RequestTool.getInt(request, "id");
			SaleForecast saleForecast = appSaleForecast.findByID(sid);
			SaleForecast oldsaleForecast = (SaleForecast) BeanUtils.cloneBean(saleForecast);
			saleForecast.setObjsort(RequestTool.getInt(request, "objsort"));
			saleForecast.setCid(RequestTool.getString(request, "cid"));
			saleForecast.setCname(RequestTool.getString(request, "cname"));
			saleForecast.setForestartdate(RequestTool.getDate(request, "forestartdate"));
			saleForecast.setForeenddate(RequestTool.getDate(request, "foreenddate"));
			saleForecast.setMakedate(DateUtil.getCurrentDate());
			saleForecast.setMakedeptid(users.getMakedeptid());
			saleForecast.setMakeid(userid);
			saleForecast.setMakeorganid(users.getMakeorganid());
			
			
			
			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			int[] unitid = RequestTool.getInts(request, "unitid");
			double[] quantity = RequestTool.getDoubles(request, "quantity");
			double[] unitprice = RequestTool.getDoubles(request, "unitprice");
			double totalsum = 0.00;
			
			AppSaleForecastDetail appSaleForecastDetail = new AppSaleForecastDetail();
			SaleForecastDetail saleForecastDetai = null;
			SaleForecastDetail[] saleForecastDetais = new SaleForecastDetail[strproductid.length];
			appSaleForecastDetail.deleteBySID(sid);
			for(int i = 0; i < strproductid.length ; i++){
				saleForecastDetai = new SaleForecastDetail();
				
				Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"saleforecastdetail", 0, ""));
				saleForecastDetai.setId(id);
				saleForecastDetai.setSid(sid);
				saleForecastDetai.setProductid(strproductid[i]);
				saleForecastDetai.setProductname(strproductname[i]);
				saleForecastDetai.setSpecmode(strspecmode[i]);
				saleForecastDetai.setQuantity(quantity[i]);
				saleForecastDetai.setUnitid(unitid[i]);
				saleForecastDetai.setUnitprice(unitprice[i]);
				saleForecastDetai.setSubsum(saleForecastDetai.getUnitprice()*saleForecastDetai.getQuantity());
				saleForecastDetais[i] = saleForecastDetai;
				totalsum+= saleForecastDetai.getSubsum();
			}
			saleForecast.setTotalsum(totalsum);
			appSaleForecastDetail.save(saleForecastDetais);
			
			appSaleForecast.update(saleForecast);
			DBUserLog.addUserLog(userid, 4, "渠道管理>>修改销售预测  ,编号：" + sid,oldsaleForecast,saleForecast);
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("success");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}

}
