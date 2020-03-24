package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppProductInterconvert;
import com.winsafe.drp.dao.AppProductInterconvertDetail;
import com.winsafe.drp.dao.ProductInterconvert;
import com.winsafe.drp.dao.ProductInterconvertDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddProductInterconvertAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			ProductInterconvert sm = new ProductInterconvert();
			String smid = MakeCode.getExcIDByRandomTableName(
					"product_interconvert", 2, "PC");
			sm.setId(smid);
			sm.setMovedate(RequestTool.getDate(request, "movedate"));
			sm.setOutwarehouseid(request.getParameter("outwarehouseid"));
			sm.setInwarehouseid(request.getParameter("inwarehouseid"));			
			sm.setMovecause(request.getParameter("movecause"));
			sm.setRemark(request.getParameter("remark"));

			sm.setIsaudit(0);
			sm.setAuditid(0);
			sm.setMakeorganid(users.getMakeorganid());
			sm.setMakedeptid(users.getMakedeptid());
			sm.setMakeid(userid);
			sm.setMakedate(DateUtil.getCurrentDate());
			sm.setIsshipment(0);
			sm.setShipmentid(0);
			sm.setIscomplete(0);
			sm.setReceiveid(0);
			sm.setReceivedate(null);
			sm.setIsblankout(0);
			sm.setTotalsum(0d);


			AppProductInterconvert asm = new AppProductInterconvert();
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(sm.getId()).append(",");

			
			String productid[] = request.getParameterValues("productid");
			String productname[] = request.getParameterValues("productname");
			String specmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			String batch[] = request.getParameterValues("batch");
			double quantity[] = RequestTool.getDoubles(request, "quantity");


			AppProductInterconvertDetail asmd = new AppProductInterconvertDetail();
			for (int i = 0; i < productid.length; i++) {
				ProductInterconvertDetail smd = new ProductInterconvertDetail();
				smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"product_interconvert_detail", 0, "")));
				smd.setPiid(smid);
				smd.setProductid(productid[i]);
				smd.setProductname(productname[i]);
				smd.setSpecmode(specmode[i]);
				smd.setUnitid(unitid[i]);
				smd.setBatch(batch[i]);
				smd.setQuantity(quantity[i]);
				asmd.addProductInterconvertDetail(smd);
			}

			sm.setKeyscontent(keyscontent.toString());
			asm.addProductInterconvert(sm);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, 7, "商品互转>>新增商品互转,编号：" + smid);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
