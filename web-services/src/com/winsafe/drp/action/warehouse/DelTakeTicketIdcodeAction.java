package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppUploadProduceReport;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UploadProduceReport;
import com.winsafe.drp.util.DBUserLog;
/**
 * 检货出库 条码删除
* @Title: DelTakeTicketIdcodeAction.java
* @author: wenping 
* @CreateTime: Jul 23, 2012 10:51:29 AM
* @version:
 */
public class DelTakeTicketIdcodeAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		try{

			AppTakeTicketIdcode asb = new AppTakeTicketIdcode();
			AppIdcode ai  =new AppIdcode();
			AppUploadProduceReport aupr = new AppUploadProduceReport();
			TakeTicketIdcode tti ;
			UploadProduceReport upr;
			Idcode ic ;
			String idcode;
			String ids = request.getParameter("ids");			
			String[] id = ids.split(",");			
			if ( id != null ){
				String idcodeid="";
				for (int i=0; i<id.length; i++ ){
					
					tti = asb.getTakeTicketIdcodeById(Long.valueOf(id[i]));
					idcode  = tti.getIdcode();
					if((idcode.substring(0, 1).equals("1")&&tti.getIssplit()==1) 
							|| (idcode.substring(0, 1).equals("3")&&tti.getIssplit()==1)){//国内散 拆
						upr  = aupr.getUploadProduceReportByUnitNo(idcode);
						//根据生产报表的箱条码找到IDCODE表中的箱条码
						ic = ai.getIdcodeById(upr.getBoxCode());
						//更新IDCODE箱条码中的状态
						ic.setPackquantity(ic.getPackquantity() + tti.getPackquantity());
						ic.setIsuse(1);
						ai.updIdcode(ic);
						//删除IDOCDE散条码
						ai.delIdcodeByid(tti.getIdcode());
					}else if(tti.getUnitid()==17){//托码
						//托中最后一条条码的后几位顺序码
						int lastidocde = Integer.parseInt(tti.getEndno().substring(4,13));
						//托中第一条条码的后几位顺序码
						int firstidocde = Integer.parseInt(tti.getStartno().substring(4,13));
						//条码前四位
						String idcodeprefix = tti.getEndno().substring(0,4);
						
						//托条码中的箱数量
						int n =lastidocde-firstidocde+1;
						for(int k =n; k>0;k--){
							idcodeid =idcodeprefix +String.format("%09d", lastidocde) ;
							lastidocde--;
							
							ic = ai.getIdcodeById(idcodeid);
							ic.setIsuse(1);
							
							ai.updIdcode(ic);
						}
					}else{
						ic = ai.getIdcodeById(idcode);
						if(ic!=null){
							ic.setIsuse(1);
							
							ai.updIdcode(ic);
						}
					}
					
					//删除TTIDOCDE条码
					asb.delTakeTicketIdcodeById(Long.valueOf(id[i]));
				}
			}


			request.setAttribute("result", "databases.del.success");
//			UsersBean users = UserManager.getUser(request);
			DBUserLog.addUserLog(request, "删除条码,条码:"
					+ ids);

			return mapping.findForward("success");
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

}
