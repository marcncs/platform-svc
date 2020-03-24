package com.winsafe.drp.action.ditch;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AlterMoveApply;
import com.winsafe.drp.dao.AlterMoveApplyDetail;
import com.winsafe.drp.dao.AlterMoveApplyDetailForm;
import com.winsafe.drp.dao.AppAlterMoveApply;
import com.winsafe.drp.dao.AppAlterMoveApplyDetail;
import com.winsafe.hbm.util.HtmlSelect;

/**
 * @author : jerry
 * @version : 2009-8-24 下午02:52:29
 * www.winsafe.cn
 */
public class ToUpdAlterMoveApplyAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			String id = request.getParameter("ID");
			AppAlterMoveApply appAMA = new AppAlterMoveApply();
			AlterMoveApply ama = appAMA.getAlterMoveApplyByID(id);
			if(ama.getIsaudit() ==1){
				String result = "databases.record.isapprovenooperator";
				request.setAttribute("result", result);
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			
			AppAlterMoveApplyDetail appAMAD = new AppAlterMoveApplyDetail();
			List listAmad = appAMAD.getAlterMoveApplyDetailByAmID(id);
			List list = new ArrayList();
			for(int i = 0 ; i< listAmad.size() ; i++){
				
				AlterMoveApplyDetailForm amadf = new AlterMoveApplyDetailForm();
				AlterMoveApplyDetail amad = (AlterMoveApplyDetail) listAmad.get(i);
				
				amadf.setId(amad.getId());
				amadf.setProductid(amad.getProductid());
				amadf.setProductname(amad.getProductname());
				amadf.setSpecmode(amad.getSpecmode());
				amadf.setUnitid(amad.getUnitid());
				amadf.setUnitidname(HtmlSelect.getResourceName(request,
						"CountUnit", amad.getUnitid()));
				amadf.setQuantity(amad.getQuantity());
				amadf.setSubsum(amad.getSubsum());
				amadf.setUnitprice(amad.getUnitprice());
				list.add(amadf);
			}
			
			request.setAttribute("list", list);
			
			request.setAttribute("ama", ama);
		
			return mapping.findForward("upd");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return super.execute(mapping, form, request, response);
	}
}
