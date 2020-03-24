package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.entity.HibernateUtil;
 
public class PickBillAction  extends BaseAction{
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String returnCode = "0";
		String returnMsg = "";
		
		//获取单号
		String billNo = request.getParameter("billNo"); 
		//获取用户名
		String username = request.getParameter("username"); 
		//获取操作类型  2:检货完成  1:检货中
		String type = request.getParameter("type"); 
		//根据用户名称查询用户信息
		AppUsers au = new AppUsers();
		Users users = au.getUsers(username);
		if(users == null){
			initdata(request);
			users = au.getUsersByid(userid);
		}
		try {
			//查询单据
			AppTakeTicket appTakeTicket = new AppTakeTicket();
			TakeTicket tt = appTakeTicket.getTakeTicketById(billNo);
			
			//检货完成
			if("2".equals(type)){
				String msg = checkState(tt);
				if(msg == null){
					if(tt.getIsChecked() != null && tt.getIsChecked() == 2){
						msg = "该单据已拣货!";
					}
				}
				if(msg != null){
					returnCode = "1";
					returnMsg = msg;
				}else {
					//更改单据状态为已检货
					tt.setIsPicked(2);  //0:未检货  1:检货中 2:已检货
					tt.setPickedId(users.getUserid());  //检货人
					tt.setPickedDate(Dateutil.getCurrentDate());  //检货时间
				}
			}
			//检货中
			if("1".equals(type)){
				String msg = checkState(tt);
				if(msg == null){
					if(tt.getIsPicked() == null || tt.getIsPicked() != 2){
						msg = "该单据未拣货!";
					}
				}
				if(msg != null){
					returnCode = "1";
					returnMsg = msg;
				}else {
					//更改单据状态为检货中
					tt.setIsPicked(0);  //0:未检货  1:检货中 2:已检货
					tt.setPickedId(null);  //检货人
					tt.setPickedDate(null);  //检货时间
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			HibernateUtil.rollbackTransaction();
			returnCode="1";
			returnMsg = e.getMessage();
		}finally{
			//将结果输出
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
		if(tt.getIsChecked() != null && tt.getIsChecked()>0){
			return "该单据为验货状态!";
		}
		return null;
		
	}
}
