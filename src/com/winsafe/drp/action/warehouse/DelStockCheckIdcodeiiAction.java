package com.winsafe.drp.action.warehouse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppStockCheck;
import com.winsafe.drp.dao.AppStockCheckDetail;
import com.winsafe.drp.dao.AppStockCheckIdcode;
import com.winsafe.drp.dao.StockCheck;
import com.winsafe.drp.dao.StockCheckIdcode;
import com.winsafe.drp.util.DBUserLog;

public class DelStockCheckIdcodeiiAction extends BaseAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try{

			
			String billno = request.getParameter("billno");
			
			AppStockCheck asc = new AppStockCheck();
			StockCheck sc = asc.getStockCheckByID(billno);
			if(sc.getIsaudit()==1){
				request.setAttribute("result", "该单据以复核,不能删除!");
				return new ActionForward("/sys/lockrecord2.jsp");
				
			}
			
			String scid = request.getParameter("ID");
			String[] ids = scid.split(",");
			AppStockCheckIdcode appsci = new AppStockCheckIdcode();
			AppStockCheckDetail appttd = new AppStockCheckDetail();
			for(int i = 0 ; i < ids.length ;i++){
				Long id = Long.valueOf(ids[i]);
				
				StockCheckIdcode scIdcode = appsci.getStockCheckIdcodeById(id);
				if ( !scIdcode.getIdcode().equals("") ){
					Double Cquantity = scIdcode.getCquantity();
					scIdcode.setCidcode("");
					scIdcode.setCquantity(0.0);
					appsci.updStockCheckIdcode(scIdcode);
					
					appttd.updCheckQuantity(billno, scIdcode.getWarehousebit(), scIdcode.getProductid(),
							scIdcode.getBatch(), -Cquantity);
				}else{
					StockCheckIdcode parentIdcode = appsci.getIdcodeByWLM(scIdcode.getStartno(), scIdcode.getEndno(), billno);				
					if ( parentIdcode != null ){		
						
						parentIdcode.setCquantity(parentIdcode.getCquantity()-scIdcode.getCquantity());
						appsci.updStockCheckIdcode(parentIdcode);
					}
					appttd.updCheckQuantity(billno, scIdcode.getWarehousebit(), scIdcode.getProductid(),
							scIdcode.getBatch(), -scIdcode.getCquantity());
					appsci.delStockCheckIdcodeById(id);
				}
				
			}
			DBUserLog.addUserLog(userid, 7, "仓库管理>>库存盘点>>删除条码详情");
			request.setAttribute("result", "databases.del.success");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}

}
