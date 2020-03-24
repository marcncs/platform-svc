package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSampleBill;
import com.winsafe.drp.dao.AppSampleBillDetail;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.SampleBill;
import com.winsafe.drp.dao.SampleBillDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class AddSampleBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);

		try {
			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.add.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			// String cname=request.getParameter("cname");
			String linkman = request.getParameter("linkman");
			String tel = request.getParameter("tel");
			String postcode = request.getParameter("postcode");
			String receiveaddr = request.getParameter("receiveaddr");
			String makeuser = request.getParameter("makeuser");
                        String whsID  = request.getParameter("warehouseid");
                        String remark = request.getParameter("remark");
			double totalsum = 0.00;

			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strunitid[] = request.getParameterValues("unitid");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			String quantity[] = request.getParameterValues("quantity");
			String productid;
			String productname, specmode;
			Integer unitid;

			SampleBill sb = new SampleBill();
			String sbid = MakeCode.getExcIDByRandomTableName(
				"sample_bill", 2, "SB"); 
			sb.setId(sbid);
			sb.setCid(cid);
			sb.setObjsort(Integer.valueOf(request.getParameter("objsort")));
			sb.setCname(request.getParameter("cname"));
			sb.setLinkman(linkman);
			sb.setTel(tel);
			sb.setWarehouseID(whsID);
			sb.setPostcode(postcode);
			sb.setReceiveaddr(receiveaddr);
			sb.setShipmentdate(RequestTool.getDate(request, "shipmentdate"));
			sb.setEstimaterecycle(RequestTool.getDate(request,
					"estimaterecycle"));
			sb.setMakeuser(makeuser);
			
			sb.setRemark(remark);
			sb.setMakeid(userid);
			sb.setMakeorganid(users.getMakeorganid());
			sb.setMakedeptid(users.getMakedeptid());
			sb.setMakedate(DateUtil.getCurrentDate());
			sb.setIsaudit(0);
			sb.setAuditid(Integer.valueOf(0));
			sb.setIsrecycle(0);
			sb.setRecycleid(Integer.valueOf(0));
			sb.setMakedeptid(users.getMakedeptid());
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(sb.getCid() + "," + sb.getCname()+","+sb.getLinkman() + ","
					+ sb.getTel() + ",");
			AppSampleBill asl = new AppSampleBill();
			
			AppProductStockpile  aps = new AppProductStockpile();

			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				//减少库存量
				 aps.adjustProductStockpile(productid, "-",
						 Integer.valueOf(quantity[i]), whsID);		
				 
				specmode = strspecmode[i];
				unitid = Integer.valueOf(strunitid[i]);

				SampleBillDetail sod = new SampleBillDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"sample_bill_detail", 0, "")));
				sod.setSbid(sbid);
				sod.setProductid(productid);

				sod.setProductname(productname);
				sod.setSpecmode(specmode);
				sod.setUnitid(unitid);
				sod.setUnitprice(unitprice[i]);
				sod.setQuantity(Integer.valueOf(quantity[i]));
				sod.setSubsum(sod.getQuantity() * sod.getUnitprice());
				totalsum += sod.getSubsum();

				AppSampleBillDetail asld = new AppSampleBillDetail();
				asld.save(sod);
				keyscontent.append(sod.getProductcode());
			}
			
			sb.setTotalsum(totalsum);
			sb.setKeyscontent(keyscontent.toString());
			asl.save(sb);

			request.setAttribute("result", "databases.add.success");
			DBUserLog.addUserLog(userid,5, "会员管理>>新增样品记录,编号："+sbid);

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}
}
