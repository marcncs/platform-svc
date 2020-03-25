package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppReceivable;
import com.winsafe.drp.dao.AppReceivableObject;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.Receivable;
import com.winsafe.drp.dao.ReceivableObject;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class TallyStockAlterMoveAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

		UsersBean users = UserManager.getUser(request);
//	    Long userid = users.getUserid();

		super.initdata(request);try{
			String omid = request.getParameter("OMID");
			AppStockAlterMove asam = new AppStockAlterMove(); 
			AppStockAlterMoveDetail asamd = new AppStockAlterMoveDetail();
			StockAlterMove sam = asam.getStockAlterMoveByID(omid);

			
			List ls = asamd.getStockAlterMoveDetailBySamID(omid);


			AppReceivableObject appro = new AppReceivableObject();
			ReceivableObject ro = new ReceivableObject();
			ro.setOid(sam.getReceiveorganid());
			ro.setObjectsort(0);
			ro.setPayer(sam.getReceiveorganidname());
			ro.setMakeorganid(users.getMakeorganid());
			ro.setMakedeptid(users.getMakedeptid());
//			ro.setMakeid(userid);
			ro.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			ro.setKeyscontent(+sam.getReceiveid()+","+sam.getReceiveorganidname());
			appro.addReceivableObjectIsNoExist(ro);

			
			AppReceivable ar = new AppReceivable();
			Receivable r = new Receivable();
			r.setId(MakeCode.getExcIDByRandomTableName("receivable",
							2, ""));
			r.setRoid(sam.getReceiveorganid());
			r.setReceivablesum(sam.getTotalsum());
			r.setPaymentmode(sam.getPaymentmode());
			r.setBillno(omid);
			r.setReceivabledescribe(omid + "机构调拨生成应收款");
			r.setAlreadysum(0.00d);
			r.setIsclose(0);
			r.setMakeorganid(users.getMakeorganid());
			r.setMakedeptid(users.getMakedeptid());
//			r.setMakeid(userid);
			r.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			ar.addReceivable(r);
			
//			asam.updIsTally(omid, userid,1);
//			
//		      request.setAttribute("result", "databases.audit.success");
//		      DBUserLog.addUserLog(userid,"复核库存调拨"); 
			return mapping.findForward("tally");
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}

}
