package com.winsafe.drp.action.sales;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSampleBill;
import com.winsafe.drp.dao.AppSampleBillDetail;
import com.winsafe.drp.dao.SampleBill;
import com.winsafe.drp.dao.SampleBillDetail;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdSampleBillAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		initdata(request);

		try {
			String id = request.getParameter("ID");
			AppSampleBill aso = new AppSampleBill();
			SampleBill sb = aso.findByID(id);
			SampleBill oldsb = (SampleBill) BeanUtils.cloneBean(sb);
			String cid = request.getParameter("cid");
			if (cid == null || cid.equals("null") || cid.equals("")) {
				String result = "databases.upd.fail";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecord.jsp");
			}
			String linkman = request.getParameter("linkman");
			String tel = request.getParameter("tel");
			String postcode = request.getParameter("postcode");
			String receiveaddr = request.getParameter("receiveaddr");
			String makeuser = request.getParameter("makeuser");
			String crid = RequestTool.getString(request, "crid");
			Double exrate = RequestTool.getDouble(request, "exrate");
			String remark = request.getParameter("remark");
			double totalsum = 0.00;
			sb.setObjsort(Integer.valueOf(request.getParameter("objsort")));
			sb.setCid(cid);
			sb.setCname(request.getParameter("cname"));
			sb.setLinkman(linkman);
			sb.setTel(tel);
			sb.setPostcode(postcode);
			sb.setReceiveaddr(receiveaddr);
			sb.setMakeuser(makeuser);
			sb.setRemark(remark);
			sb.setShipmentdate(RequestTool.getDate(request, "shipmentdate"));
			sb.setEstimaterecycle(RequestTool.getDate(request,
					"estimaterecycle"));
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(sb.getCid() + "," + sb.getCname()+","+sb.getLinkman() + ","
					+ sb.getTel() + ",");

			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strunitid[] = request.getParameterValues("unitid");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			int quantity[] = RequestTool.getInts(request, "quantity");
			String productid;
			String productname, specmode;
			Integer unitid;

			AppSampleBillDetail asld = new AppSampleBillDetail();
			asld.deleteBySbid(id);

			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				unitid = Integer.valueOf(strunitid[i]);

				SampleBillDetail sod = new SampleBillDetail();
				sod.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"sample_bill_detail", 0, "")));
				sod.setSbid(id);
				sod.setProductid(productid);
				sod.setProductname(productname);
				sod.setSpecmode(specmode);
				sod.setUnitid(unitid);
				sod.setUnitprice(unitprice[i]);
				sod.setQuantity(quantity[i]);
				sod.setSubsum(sod.getUnitprice() * sod.getQuantity());
				totalsum += sod.getSubsum();

				asld.save(sod);
				keyscontent.append(sod.getProductcode());
			}
			sb.setKeyscontent(keyscontent.toString());
			sb.setTotalsum(totalsum);
			aso.update(sb);

			request.setAttribute("result", "databases.upd.success");

			DBUserLog.addUserLog(userid,5, "会员管理>>修改样品记录，编号："+id,oldsb,sb);

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
