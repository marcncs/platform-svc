package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.AppMoveApplyDetail;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.MoveApplyDetail;
import com.winsafe.drp.dao.MoveApplyDetailForm;
import com.winsafe.drp.dao.MoveApplyForm;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.dao.AppUserCustomer;
import com.winsafe.drp.keyretailer.pojo.UserCustomer;
import com.winsafe.drp.metadata.UserType;

public class ToRatifyMoveApplyAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppUserCustomer appUserCustomer = new AppUserCustomer();
		String id = request.getParameter("AAID");
		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();
		
		super.initdata(request);try{
			AppMoveApply asm = new AppMoveApply();
			MoveApply sm = asm.getMoveApplyByID(id);

			if (sm.getIsaudit() == 0) { 
				String result = "databases.record.noauditnoratify";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			boolean flag=false;
			int isratify=sm.getIsratify();
			UserType ut=UserType.parseByValue(users.getUserType());
			if(ut!=null){
				int typeid=users.getUserType();
				if(typeid==2&&sm.getIsratify()==1){
					UserCustomer uout=appUserCustomer.getUserCustomer(userid+"", sm.getOutorganid());
					if(uout!=null)
					flag=true;
				}else if(typeid==5&&sm.getIsratify()==2){
					flag=true;
				}else if(typeid==2&&sm.getIsratify()==3){
					UserCustomer uin=appUserCustomer.getUserCustomer(userid+"", sm.getInorganid());
					if(uin!=null){
					flag=true;
					}
				}else if(typeid==6&&sm.getIsratify()==4){
					flag=true;
				}
			}
			if (flag) { 
				String result = "databases.record.approve";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}

//			if (!sm.getOutorganid().equals(users.getMakeorganid())) { 
//				String result = "databases.record.noorgan";
//				request.setAttribute("result", result);
//				return new ActionForward("/sys/lockrecordclose.jsp");
//			}

			MoveApplyForm smf = new MoveApplyForm();
			smf.setId(sm.getId());
			smf.setMovedate(sm.getMovedate());
			smf.setOutorganid(sm.getOutorganid());
			smf.setInwarehouseid(sm.getInwarehouseid());
			smf.setOlinkman(sm.getOlinkman());
			smf.setOtel(sm.getOtel());
			smf.setMakeorganid(sm.getMakeorganid());
			smf.setTotalsum(sm.getTotalsum());
			smf.setTransportmode(sm.getTransportmode());
			smf.setTransportaddr(sm.getTransportaddr());
			smf.setMovecause(sm.getMovecause());
			smf.setRemark(sm.getRemark());
			smf.setOutwarehouseid(sm.getOutwarehouseid());
			smf.setInorganid(sm.getInorganid());

			AppMoveApplyDetail asmd = new AppMoveApplyDetail();
			List smdls = asmd.getMoveApplyDetailByAmID(id);
			ArrayList als = new ArrayList();

			for (int i = 0; i < smdls.size(); i++) {
				MoveApplyDetailForm smdf = new MoveApplyDetailForm();
				MoveApplyDetail o = (MoveApplyDetail) smdls.get(i);
				smdf.setId(o.getId());
				smdf.setProductid(o.getProductid());
				smdf.setProductname(o.getProductname());
				smdf.setSpecmode(o.getSpecmode());
				smdf.setUnitid(o.getUnitid());
				smdf.setUnitprice(o.getUnitprice());
				smdf.setQuantity(o.getQuantity());
				smdf.setCanquantity(0d);
				smdf.setSubsum(o.getSubsum());
				als.add(smdf);
			}

			request.setAttribute("smf", smf);
			request.setAttribute("als", als);

			return mapping.findForward("toratify");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
}
