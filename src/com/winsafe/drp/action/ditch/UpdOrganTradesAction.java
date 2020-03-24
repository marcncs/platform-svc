package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrganTrades;
import com.winsafe.drp.dao.AppOrganTradesDetail;
import com.winsafe.drp.dao.OrganTrades;
import com.winsafe.drp.dao.OrganTradesDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

/**
 * @author : jerry
 * @version : 2009-9-3 上午10:33:19
 * www.winsafe.cn
 */
public class UpdOrganTradesAction extends BaseAction  {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			
			OrganTrades ot = new OrganTrades();
			AppOrganTrades appAMA = new AppOrganTrades();
			AppOrganTradesDetail appAMAD = new AppOrganTradesDetail();
			BeanCopy bc = new BeanCopy();
			
			bc.copy(ot, request);
			OrganTrades ow= appAMA.getOrganTradesByID(ot.getId());
			OrganTrades oldow = (OrganTrades) BeanUtils.cloneBean(ow);
			ow.setPorganid(ot.getPorganid());
			ow.setPorganname(ot.getPorganname());
			ow.setPlinkman(ot.getPlinkman());
			ow.setRlinkman(ot.getRlinkman());
			ow.setTransportaddr(ot.getTransportaddr());
			ow.setRtransportaddr(ot.getRtransportaddr());
			ow.setTel(ot.getTel());
			ow.setRtel(ot.getRtel());
			ow.setOutwarehouseid(ot.getOutwarehouseid());
			ow.setWithdrawcause(ot.getWithdrawcause());
			
			
			String pwid = ow.getId();
			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			int unitid[] = RequestTool.getInts(request, "unitid");
			String strbatch[] = request.getParameterValues("batch");
			double unitprice[] = RequestTool.getDoubles(request, "unitprice");
			double quantity[] = RequestTool.getDoubles(request, "quantity");
			OrganTradesDetail otd = null;
			Double totalsum =0.00;
			appAMAD.deleteByPIID(pwid);
			if (strproductid != null) {
				for (int i = 0; i < strproductid.length; i++) {
					otd = new OrganTradesDetail();
					Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"organ_withdraw_detail", 0, ""));
					otd.setId(id);
					otd.setOtid(pwid);
					otd.setProductid(strproductid[i]);
					otd.setProductname(strproductname[i]);
					otd.setSpecmode(strspecmode[i]);
					otd.setUnitid(unitid[i]);
					otd.setBatch(strbatch[i]);
					otd.setUnitprice(0d);
					otd.setQuantity(quantity[i]);
					otd.setCanquantity(0d);		
					otd.setTakequantity(0d);
					otd.setSubsum(otd.getUnitprice()*otd.getQuantity());
					appAMAD.save(otd);
					totalsum +=otd.getSubsum();
				}
			}
			ow.setTotalsum(totalsum);
			appAMA.update(ow);
			DBUserLog.addUserLog(userid,4, "渠道管理>>修改渠道换货,编号："+pwid,oldow,ow);
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("success");
			
			}catch(Exception ex){
				ex.printStackTrace();
			}
		return super.execute(mapping, form, request, response);
	}
}
