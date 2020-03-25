package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSupplySaleApply;
import com.winsafe.drp.dao.AppSupplySaleApplyDetail;
import com.winsafe.drp.dao.SupplySaleApply;
import com.winsafe.drp.dao.SupplySaleApplyDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.MakeCode;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:52:42
 * www.winsafe.cn
 */
public class UpdSupplySaleApplyAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			
			SupplySaleApply alterma = new SupplySaleApply();

			AppSupplySaleApply appAMA = new AppSupplySaleApply();
			AppSupplySaleApplyDetail appAMAD = new AppSupplySaleApplyDetail();
			BeanCopy bc = new BeanCopy();
			
			bc.copy(alterma, request);

			
			SupplySaleApply newAlterma = appAMA.getSupplySaleApplyByID(alterma.getId());
			
			
			SupplySaleApply oldalterma = (SupplySaleApply) BeanUtils.cloneBean(newAlterma);
			String amid = newAlterma.getId();
			newAlterma.setMovedate(alterma.getMovedate());
			newAlterma.setOutorganid(alterma.getOutorganid());
			newAlterma.setInorganid(alterma.getInorganid());
			newAlterma.setInwarehouseid(alterma.getInwarehouseid());
			newAlterma.setTotalsum(alterma.getTotalsum());
			newAlterma.setPaymentmode(alterma.getPaymentmode());
			newAlterma.setInvmsg(alterma.getInvmsg());
			newAlterma.setTransportmode(alterma.getTransportmode());
			newAlterma.setTransportaddr(alterma.getTransportaddr());
			newAlterma.setTickettitle(alterma.getTickettitle());
			newAlterma.setMovecause(alterma.getMovecause());
			newAlterma.setRemark(alterma.getRemark());
			
			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strunitid[] = request.getParameterValues("unitid");
			String strpunitprice[] = request.getParameterValues("punitprice");
			String strsunitprice[] = request.getParameterValues("sunitprice");
			String strquantity[] = request.getParameterValues("quantity");

			appAMAD.deleteBySSID(amid);
			Double totalsum = 0.00,stotalsum=0.00;
			SupplySaleApplyDetail ssad = null;
			if (strproductid != null) {
				for (int i = 0; i < strproductid.length; i++) {
					Double punitprice = Double.valueOf(strpunitprice[i]);
					Double sunitprice = Double.valueOf(strsunitprice[i]);
					Double quantity = Double.valueOf(strquantity[i]);
					ssad = new SupplySaleApplyDetail();
					Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName(
							"supply_sale_apply_detail", 0, ""));
					ssad.setId(id);
					ssad.setSsid(amid);
					ssad.setProductid(strproductid[i]);
					ssad.setProductname(strproductname[i]);
					ssad.setSpecmode(strspecmode[i]);
					ssad.setUnitid(Integer.valueOf(strunitid[i]));
					ssad.setPunitprice(punitprice);
					ssad.setSunitprice(sunitprice);
					ssad.setQuantity(quantity);
					ssad.setCanquantity(0d);
					ssad.setAlreadyquantity(0d);
					Double psubsum = punitprice*quantity;
					ssad.setPsubsum(psubsum);
					totalsum+=psubsum;
					Double ssubsum = sunitprice*quantity;
					ssad.setSsubsum(ssubsum);
					stotalsum+=ssubsum;
					appAMAD.save(ssad);
				}
			}
			newAlterma.setTotalsum(totalsum);
			newAlterma.setStotalsum(stotalsum);
			appAMA.update(newAlterma);
			DBUserLog.addUserLog(userid,4, "渠道管理>>修改代销申请,编号："+amid,oldalterma,newAlterma);
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("success");
			
			}catch(Exception ex){
				ex.printStackTrace();
			}
		return super.execute(mapping, form, request, response);
	}
}
