package com.winsafe.drp.action.ditch;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppSupplySaleMove;
import com.winsafe.drp.dao.AppSupplySaleMoveDetail;
import com.winsafe.drp.dao.SupplySaleMove;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:52:29
 * www.winsafe.cn
 */
public class ToUpdSupplySaleMoveAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			String id = request.getParameter("ID");
			AppSupplySaleMove appAMA = new AppSupplySaleMove();
			SupplySaleMove ama = appAMA.getSupplySaleMoveByID(id);
			if(ama.getIsaudit() ==1){
				String result = "databases.record.lock";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			AppSupplySaleMoveDetail appAMAD = new AppSupplySaleMoveDetail();
			List listAmad = appAMAD.getSupplySaleMoveBySSMID(id);
			
			
			request.setAttribute("list", listAmad);
			
			request.setAttribute("ama", ama);

			return mapping.findForward("upd");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
