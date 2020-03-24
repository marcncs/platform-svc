package com.winsafe.drp.action.finance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.AppReceivableDetail;
import com.winsafe.drp.dao.ReceivableDetail;
import com.winsafe.drp.dao.ReceivableDetailForm;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.Internation;
import com.winsafe.hbm.util.pager.SimplePageInfo;

public class SelectReceivableDetailAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int pagesize = 10;
		super.initdata(request);
		String rid = request.getParameter("rid");
		try {
			String Condition = " r.roid='"+rid+"' and r.id=rd.rid and rd.isclose=0 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			// String sql = "select * from provide ";
			String[] tablename = { "Receivable", "ReceivableDetail" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getBlur(map, tmpMap, "ProductName"); 
			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "SpecMode");
			whereSql = whereSql + blur + leftblur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			Object obj[] = (DbUtil.setPager(request, "Receivable as r, ReceivableDetail as rd",
					whereSql, pagesize));
			SimplePageInfo tmpPgInfo = (SimplePageInfo) obj[0]; 
			whereSql = (String) obj[1];

			AppReceivableDetail apo = new AppReceivableDetail();
			List pls = apo
					.getReceivableDetail(pagesize, whereSql, tmpPgInfo);
			ArrayList sls = new ArrayList();
			ReceivableDetail rd = null;
			for (int i = 0; i < pls.size(); i++) {
				rd = (ReceivableDetail) pls.get(i);
				ReceivableDetailForm pof = new ReceivableDetailForm();
				pof.setId(rd.getId());
				pof.setRid(rd.getRid());
				pof.setProductid(rd.getProductid());
				pof.setProductname(rd.getProductname());
				pof.setSpecmode(rd.getSpecmode());
				pof.setBatch(rd.getBatch());
				pof.setBillno(rd.getBillno());
				pof.setPaymentmode(rd.getPaymentmode());
				pof.setPaymentmodename(Internation.getStringByPayPositionDB(rd.getPaymentmode()));
				pof.setUnitid(rd.getUnitid());
				pof.setUnitidname(Internation.getStringByKeyPositionDB("CountUnit", Integer.parseInt(rd.getUnitid()
								.toString())));
				pof.setQuantity(rd.getQuantity()-rd.getAlreadyquantity());
				pof.setGoodsfund(rd.getGoodsfund()-rd.getAlreadysum());
				pof.setScot(rd.getScot());
				pof.setAlreadyquantity(rd.getAlreadyquantity());
				pof.setAlreadysum(rd.getAlreadysum());
				sls.add(pof);
			}

//			 产品结构
			AppProductStruct appProductStruct = new AppProductStruct();
			List uls = appProductStruct.getProductStructCanUse();

			request.setAttribute("uls", uls);

			request.setAttribute("sls", sls);
			return mapping.findForward("selectrd");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
