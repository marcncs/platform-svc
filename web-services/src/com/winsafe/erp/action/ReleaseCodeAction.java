package com.winsafe.erp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.erp.dao.AppPrepareCode;

public class ReleaseCodeAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(ReleaseCodeAction.class);
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		super.initdata(request);
		String type = request.getParameter("type");	
		if(StringUtil.isEmpty(type)){
			try{
				//释放
				AppPrepareCode asb = new AppPrepareCode();
				String ids = request.getParameter("ids");	
				String[] id = ids.split(",");			
				if ( id != null ){
					for (int i=0; i<id.length; i++ ){
						asb.releasePrepareCode((id[i]));
					}
					asb.releasePrepareCodeTcode(id[0]);
				}
				
				
				request.setAttribute("result", "databases.release.success");
//				request.setAttribute("close", 1);
//				
//				String productPlanId = request.getParameter("productPlanId");	
//				System.out.print("productPlanId+==="+productPlanId);
//				request.setAttribute("productPlanId", productPlanId);

				return mapping.findForward("success");
			} catch (Exception e) {
				logger.error("", e);
				throw e;
			}
		}else{
			try{
				//取消释放
				AppPrepareCode asb = new AppPrepareCode();
				String ids = request.getParameter("ids");	
				String[] id = ids.split(",");			
				if ( id != null ){
					for (int i=0; i<id.length; i++ ){
						asb.cancelReleasePrepareCode((id[i]));
					}
					asb.updateReleasePrepareCodeTcode((id[0]),0);
				}
				request.setAttribute("result", "databases.cancelrelease.success");
				return mapping.findForward("success");
			} catch (Exception e) {
				logger.error("", e);
				throw e;
			}
		}
	}

}
