package com.winsafe.drp.action.ditch.productreturn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppOrganWithdrawDetail;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.OrganWithdrawDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:12:00
 * www.winsafe.cn
 */
public class DoUpdProductReturnAction extends BaseAction{

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		
		OrganWithdraw ss = new OrganWithdraw();
		AppOrganWithdraw appAMA = new AppOrganWithdraw();
		AppOrganWithdrawDetail appAMAD = new AppOrganWithdrawDetail();
		BeanCopy bc = new BeanCopy();
		
		bc.copy(ss, request);
		OrganWithdraw ow= appAMA.getOrganWithdrawByID(ss.getId());
		OrganWithdraw oldow = (OrganWithdraw) BeanUtils.cloneBean(ow);
		ow.setPorganid(ss.getPorganid());
		ow.setPorganname(ss.getPorganname());
		ow.setPlinkman(ss.getPlinkman());
		ow.setTel(ss.getTel());
		
		ow.setWarehouseid(ss.getWarehouseid());
		ow.setWithdrawcause(ss.getWithdrawcause());
		
		
		String pwid = ow.getId();
		
		String strproductid[] = request.getParameterValues("productid");
		String strproductname[] = request.getParameterValues("productname");
		String strspecmode[] = request.getParameterValues("specmode");
		int unitid[] = RequestTool.getInts(request, "unitid");
		String strbatch[] = request.getParameterValues("batch");
		double unitprice[] = RequestTool.getDoubles(request, "unitprice");
		double quantity[] = RequestTool.getDoubles(request, "quantity");
		
		Double totalsum =0.00;
		OrganWithdrawDetail ssd = null;
		appAMAD.deleteByPIID(pwid);
		if (strproductid != null) {
			for (int i = 0; i < strproductid.length; i++) {				
				ssd = new OrganWithdrawDetail();
				Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"organ_withdraw_detail", 0, ""));
				ssd.setId(id);
				ssd.setOwid(pwid);
				ssd.setProductid(strproductid[i]);
				ssd.setProductname(strproductname[i]);
				ssd.setSpecmode(strspecmode[i]);
				ssd.setUnitid(Integer.valueOf(unitid[i]));
				ssd.setBatch(strbatch[i]);
				ssd.setUnitprice(unitprice[i]);
				ssd.setQuantity(quantity[i]);
				ssd.setRatifyquantity(0d);
				ssd.setTakequantity(0d);				
				ssd.setSubsum(ssd.getUnitprice()*ssd.getQuantity());
				appAMAD.save(ssd);
				totalsum +=ssd.getSubsum();
			}
		}
		ow.setKeyscontent(ow.getId()+","+ow.getPorganid()+","+
				ow.getPorganname()+","+ow.getPlinkman()+","+ow.getTel());
		ow.setTotalsum(totalsum);
		appAMA.update(ow);
		DBUserLog.addUserLog(userid,4, "渠道管理>>修改渠道退货,编号："+pwid,oldow,ow);
		request.setAttribute("result", "databases.upd.success");
		return mapping.findForward("success");
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}

