package com.winsafe.drp.action.assistant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcodeDetail;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.IdcodeDetail;
import com.winsafe.drp.dao.IdcodeDetailForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class IdcodeDetailAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String iid = request.getParameter("ID");
		UsersBean users = UserManager.getUser(request);
//		Long userid = users.getUserid();
		try {
			IdcodeDetailForm idf = null;
			AppIdcodeDetail ap = new AppIdcodeDetail();
			AppOrgan ao = new AppOrgan();
			AppWarehouse aw = new AppWarehouse();
			IdcodeDetail id = ap.getIdcodeDetailById(Long.valueOf(iid));
			idf = new IdcodeDetailForm();
			idf.setId(id.getId());
			idf.setProductid(id.getProductid());
			idf.setProductname(id.getProductname());
			idf.setSpecmode(id.getSpecmode());
			idf.setMakeuser(id.getMakeuser());
			idf.setMakedate(DateUtil.formatDateTime(id.getMakedate()));
			idf.setIdbilltype(id.getIdbilltype());
			idf.setIdbilltypename(Internation.getStringByKeyPosition(
					"IdBillType", request, id.getIdbilltype(),
					"global.sys.SystemResource"));
			idf.setBillid(id.getBillid());	
			idf.setIdcode(id.getIdcode());
			if ( id.getMakeorganid()!=null && !"".equals(id.getMakeorganid()) ){
				idf.setMakeorganidname(ao.getOrganByID(id.getMakeorganid()).getOrganname());
			}
			if ( id.getEquiporganid()!=null && !"".equals(id.getEquiporganid()) ){
				idf.setEquiporganidname(ao.getOrganByID(id.getEquiporganid()).getOrganname());
			}
			if ( id.getWarehouseid() !=null && id.getWarehouseid()>0 ){
//				idf.setWarehouseidname(aw.getWarehouseByID(id.getWarehouseid()).getWarehousename());
			}
			idf.setCname(id.getCname());
			idf.setCmobile(id.getCmobile());
			request.setAttribute("idf", idf);
//			DBUserLog.addUserLog(userid, "序号记录详情");// 日志
			return mapping.findForward("info");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();
	}

}
