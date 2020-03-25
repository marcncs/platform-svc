package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductInterconvert;
import com.winsafe.drp.dao.AppProductInterconvertDetail;
import com.winsafe.drp.dao.ProductInterconvert;
import com.winsafe.drp.dao.ProductInterconvertDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdProductInterconvertAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try {
			String id = request.getParameter("ID");
			AppProductInterconvert asm = new AppProductInterconvert();
			ProductInterconvert sm = asm.getProductInterconvertByID(id);
			ProductInterconvert oldP = (ProductInterconvert)BeanUtils.cloneBean(sm);

			sm.setMovedate(RequestTool.getDate(request, "movedate"));
			sm.setOutwarehouseid(request.getParameter("outwarehouseid"));
			sm.setInwarehouseid(request.getParameter("inwarehouseid"));
			sm.setMovecause(request.getParameter("movecause"));
			sm.setRemark(request.getParameter("remark"));

			AppProductInterconvertDetail asmd = new AppProductInterconvertDetail();


			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request,"unitid");
			String batch[] = request.getParameterValues("batch");
			double quantity[] = RequestTool.getDoubles(request,"quantity");

			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(sm.getId()).append(",");
			asmd.delProductInterconvertDetailBySamid(id);

			for (int i = 0; i < productid.length; i++) {				
				ProductInterconvertDetail smd = new ProductInterconvertDetail();
				smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"stock_alter_move_detail", 0, "")));
				smd.setPiid(id);
				smd.setProductid(productid[i]);
				smd.setProductname(productname[i]);
				smd.setSpecmode(specmode[i]);
				smd.setUnitid(unitid[i]);
				smd.setBatch(batch[i]);
				smd.setQuantity(quantity[i]);
				asmd.addProductInterconvertDetail(smd);
			}

			sm.setKeyscontent(keyscontent.toString());
			asm.updstockAlterMove(sm);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid, 7,"修改商品互转,编号："+id,oldP,sm);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
