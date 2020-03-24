package com.winsafe.drp.action.purchase;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AdsumGoods;
import com.winsafe.drp.dao.AdsumGoodsDetailForm;
import com.winsafe.drp.dao.AdsumGoodsForm;
import com.winsafe.drp.dao.AppAdsumGoods;
import com.winsafe.drp.dao.AppAdsumGoodsDetail;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppDept;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.Internation;

public class ToUpdAdsumGoodsAction
    extends Action {

  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
    String id = request.getParameter("ID");
    UsersBean users = UserManager.getUser(request);
    Integer userid = users.getUserid();
    try {
      AppAdsumGoods aag = new AppAdsumGoods();
      AdsumGoods ag = new AdsumGoods();
      AppCustomer apv = new AppCustomer();
      AppUsers au = new AppUsers();
      AppDept ad = new AppDept();
      ag = aag.getAdsumGoodsByID(id);

      if (ag.getIsaudit() == 1) { 
        String result = "databases.record.lock";
        request.setAttribute("result", result);
        return mapping.findForward("lock");
      }

      AdsumGoodsForm pbf= new AdsumGoodsForm();
      pbf.setId(id);
      pbf.setPoid(ag.getPoid());
      pbf.setPid(ag.getPid());
      pbf.setPidname(apv.getCustomer(ag.getPid()).getCname());
      pbf.setPlinkman(ag.getPlinkman());
      pbf.setTel(ag.getTel());
      pbf.setObtaincode(ag.getObtaincode());
      pbf.setPurchasedept(ag.getPurchasedept());
      pbf.setPurchaseid(ag.getPurchaseid());
      pbf.setTotalsum(ag.getTotalsum());
      pbf.setReceivedate(DateUtil.formatDate(ag.getReceivedate()));
      pbf.setRemark(ag.getRemark());

      AppAdsumGoodsDetail apad = new AppAdsumGoodsDetail();
      List padls = apad.getAdsumGoodsDetailByPbID(id);
      ArrayList als = new ArrayList();
      AppProduct ap = new AppProduct();

      for(int i=0;i<padls.size();i++){
        AdsumGoodsDetailForm pbdf = new AdsumGoodsDetailForm();
        Object[] o = (Object[])padls.get(i);
        pbdf.setProductid(o[2].toString());
        pbdf.setProductname(o[3].toString());
        pbdf.setSpecmode(o[4].toString());
        pbdf.setUnitid(Integer.valueOf(o[5].toString()));
        pbdf.setUnitname(Internation.getStringByKeyPositionDB("CountUnit",           
            Integer.parseInt(o[5].toString())));
        pbdf.setUnitprice(Double.valueOf(o[6].toString()));
        pbdf.setQuantity(Double.valueOf(o[7].toString()));
        pbdf.setSubsum(Double.valueOf(o[8].toString()));
        als.add(pbdf);
      }
      
  	List aldept = ad.getDeptByOID(users.getMakeorganid());
     
      
      List auls = au.getIDAndLoginNameByOID2(users.getMakeorganid());
      
      request.setAttribute("auls", auls);
      request.setAttribute("aldept", aldept);
      request.setAttribute("pbf",pbf);
      request.setAttribute("als",als);

      return mapping.findForward("toupd");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return new ActionForward(mapping.getInput());
  }
}
