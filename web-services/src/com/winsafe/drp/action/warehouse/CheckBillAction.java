package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.entity.HibernateUtil;

public class CheckBillAction  extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String returnCode = "0";
		String returnMsg = "";
		AppTakeTicket appTakeTicket = new AppTakeTicket();
		//获取单号
		String billNo = request.getParameter("billNo"); 
		//获取用户名
		String username = request.getParameter("username"); 
		//类型  1:正常验货 0:取消检货验货状态
		String type = request.getParameter("type"); 
		//操作方式  1:修改状态  0:查看状态
		String op = request.getParameter("op"); 
		
		//根据用户名称查询用户信息
		AppUsers au = new AppUsers();
		Users users = au.getUsers(username);
		if(users == null){
			initdata(request);
			users = au.getUsersByid(userid);
		}
		try {
			if(!StringUtil.isEmpty(billNo)){
				TakeTicket tt = appTakeTicket.getTakeTicketById(billNo);
				
				//将单据修改为检货中未验货
				if("0".equals(type)){
					String msg = checkState(tt);
					if(msg == null){
//						if(tt.getIsChecked() == null || tt.getIsChecked() != 2){
//							msg = "该单据未验货!";
//						}
					}
					if(msg != null){
						returnCode = "1";
						returnMsg = msg;
					}else {
						if("1".equals(op)){
							tt.setIsChecked(0);
							tt.setCheckedId(null); 
							tt.setCheckedDate(null);
							tt.setIsPicked(2); //0:未检货  1:检货中 2:已检货
						}
					}
				}
				//更改单据状态为已验货
				if("1".equals(type)){
					String msg = checkState(tt);
					if(msg == null){
						if(tt.getIsChecked() != null && tt.getIsChecked() == 2){
							msg = "该单据已验货!";
						}
					}
					if(msg != null){
						returnCode = "1";
						returnMsg = msg;
					}else {
						if("1".equals(op)){
							tt.setIsChecked(2); //0:未验货  1:验货中 2:已验货
							tt.setCheckedId(users.getUserid()); //验货人
							tt.setCheckedDate(Dateutil.getCurrentDate()); //验货时间
						}
					}
				}
				
			}
			return null;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			returnCode="1";
			returnMsg = e.getMessage();
		}finally{
			//将结果输出到采集器
			ResponseUtil.writeReturnMsg(response, returnCode, returnMsg);
		}
		
		return null;
	}
	/**
	 * 检查单据的状态
	 * Create Time 2014-7-22 上午09:30:15 
	 * @param tt
	 * @return
	 * @author lipeng
	 */
	private String checkState(TakeTicket tt){
		if(tt == null){
			return "该单据不存在!";
		}
		if(tt.getIsaudit() == 1 ){
			return "该单据已复核!";
		}
		if(tt.getIsPicked() == null || tt.getIsPicked() != 2){
			return "该单据未拣货!";
		}
		return null;
		
	}
	
}
