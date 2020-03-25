package com.winsafe.drp.action.finance;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;

public class ListProductStructAction
    extends BaseAction {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    Vector tree = null;
    super.initdata(request);
    try {
//      tree = ProductTreeItem.getProductTree("../purchase/listProductAction.do","OtherKey");
//      String treeHtmlCode = ProductTreeItem.genTreeHTMLCode(tree);
//      request.setAttribute("treeHtmlCode", treeHtmlCode);

    	//DBUserLog.addUserLog(userid,"查询存货结构"); 
      return mapping.findForward("success");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
