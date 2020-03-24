package com.winsafe.drp.action.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ListProductStructAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    try {
//    	Vector tree = ProductTreeItem.getProductTree("../purchase/listProductAction.do","OtherKey");
//      String treeHtmlCode = ProductTreeItem.genTreeHTMLCode(tree);
//      request.setAttribute("treeHtmlCode", treeHtmlCode);
//    	UsersBean users = UserManager.getUser(request);
//    	int userid = users.getUserid();
//    	DBUserLog.addUserLog(userid,"查询存货结构");
      return mapping.findForward("list");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
