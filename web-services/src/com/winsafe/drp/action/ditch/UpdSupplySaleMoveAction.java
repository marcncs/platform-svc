package com.winsafe.drp.action.ditch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.AppSupplySaleMoveDetail;
import com.winsafe.drp.dao.SupplySaleMove;
import com.winsafe.drp.dao.SupplySaleMoveDetail;
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
public class UpdSupplySaleMoveAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			
			SupplySaleMove ssm = new SupplySaleMove();
			AppSupplySaleMove appAMA = new AppSupplySaleMove();
			AppSupplySaleMoveDetail appAMAD = new AppSupplySaleMoveDetail();
			BeanCopy bc = new BeanCopy();
			
			bc.copy(ssm, request);

			
			SupplySaleMove newSsm = appAMA.getSupplySaleMoveByID(ssm.getId());
			
			
			SupplySaleMove oldssm = (SupplySaleMove) BeanUtils.cloneBean(newSsm);
			String amid = newSsm.getId();
			newSsm.setMovedate(ssm.getMovedate());
			newSsm.setOutwarehouseid(ssm.getOutwarehouseid());
			newSsm.setInorganid(ssm.getInorganid());
			newSsm.setInwarehouseid(ssm.getInwarehouseid());
			newSsm.setTotalsum(ssm.getTotalsum());
			newSsm.setPaymentmode(ssm.getPaymentmode());
			newSsm.setInvmsg(ssm.getInvmsg());
			newSsm.setTransportmode(ssm.getTransportmode());
			newSsm.setTransportaddr(ssm.getTransportaddr());
			newSsm.setTickettitle(ssm.getTickettitle());
			newSsm.setMovecause(ssm.getMovecause());
			newSsm.setRemark(ssm.getRemark());
			
			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strunitid[] = request.getParameterValues("unitid");
			String strpunitprice[] = request.getParameterValues("punitprice");
			String strsunitprice[] = request.getParameterValues("sunitprice");
			String strquantity[] = request.getParameterValues("quantity");
			appAMAD.deleteBySSMID(amid);
			Double totalsum = 0.00,stotalsum=0.00;
			SupplySaleMoveDetail ssmd = null;
			if (strproductid != null) {
				for (int i = 0; i < strproductid.length; i++) {
					Double punitprice = Double.valueOf(strpunitprice[i]);
					Double sunitprice = Double.valueOf(strsunitprice[i]);
					Double quantity = Double.valueOf(strquantity[i]);
					ssmd = new SupplySaleMoveDetail();
					Integer id = Integer.valueOf(MakeCode
							.getExcIDByRandomTableName(
									"supply_sale_move_detail", 0, ""));
					ssmd.setId(id);
					ssmd.setSsmid(amid);
					ssmd.setProductid(strproductid[i]);
					ssmd.setProductname(strproductname[i]);
					ssmd.setSpecmode(strspecmode[i]);
					ssmd.setUnitid(Integer.valueOf(strunitid[i]));
					ssmd.setPunitprice(punitprice);
					ssmd.setSunitprice(sunitprice);
					ssmd.setQuantity(quantity);
					ssmd.setTakequantity(0d);
					Double psubsum = punitprice*quantity;
					ssmd.setPsubsum(psubsum);
					totalsum+=psubsum;
					Double ssubsum = sunitprice*quantity;
					ssmd.setSsubsum(ssubsum);
					stotalsum+=ssubsum;
					appAMAD.save(ssmd);
				}
			}
			newSsm.setTotalsum(totalsum);
			newSsm.setStotalsum(stotalsum);
			newSsm.setKeyscontent(amid+","+newSsm.getMakeorganidname()+","+newSsm.getOlinkman()+","+newSsm.getOtel());
			appAMA.update(newSsm);
			DBUserLog.addUserLog(userid,4, "渠道管理>>修改代销申请,编号："+amid,oldssm,newSsm);
			request.setAttribute("result", "databases.upd.success");
			return mapping.findForward("success");
			
			}catch(Exception ex){
				ex.printStackTrace();
			}
		return super.execute(mapping, form, request, response);
	}
}
