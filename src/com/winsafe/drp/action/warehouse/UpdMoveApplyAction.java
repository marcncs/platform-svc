package com.winsafe.drp.action.warehouse;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.AppMoveApplyDetail;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.MoveApplyDetail;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.RequestTool;

public class UpdMoveApplyAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DataValidate dv = new DataValidate();

		super.initdata(request);try{
			Properties pro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
			String moveApplyOrganId = pro.getProperty("moveApplyOrganId")!=null?pro.getProperty("moveApplyOrganId"):"";
			String outorganid = request.getParameter("outorganid");
			String receiveorganid = request.getParameter("receiveorganid");
			String moveType = request.getParameter("moveType");
			Integer moveTypeInteger = null;
			if(outorganid.equals(receiveorganid)
					&& moveApplyOrganId.indexOf(outorganid)==-1){//机构内转仓，转仓类型必须为空，不然影响统计，浙江浙农金泰生物科技有限公司  除外
				if(!StringUtil.isEmpty(moveType)){
					request.setAttribute("result", "机构内转仓不需要选择转仓类型，请重新录入!");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
			}else{//机构间转仓，转仓类型必须要有值
				if(StringUtil.isEmpty(moveType)){
					request.setAttribute("result","机构间转仓需要选择转仓类型，请重新录入!");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
				moveTypeInteger = Integer.parseInt(moveType);
			}
			
			String id = request.getParameter("id");
			AppMoveApply asm = new AppMoveApply();
			MoveApply sm = asm.getMoveApplyByID(id);
			MoveApply oldsm = (MoveApply)BeanUtils.cloneBean(sm);

			String movedate = request.getParameter("movedate");
			sm.setMovedate(DateUtil.StringToDate(movedate));
			sm.setOutorganid(outorganid);
			sm.setOutwarehouseid(request.getParameter("outwarehouseid"));
			sm.setInorganid(receiveorganid);
			sm.setInwarehouseid(request.getParameter("inwarehouseid"));
			sm.setTransportmode(RequestTool.getInt(request,"transportmode"));
			sm.setOlinkman(request.getParameter("olinkman"));
			sm.setOtel(request.getParameter("otel"));
			sm.setTransportaddr(request.getParameter("transportaddr"));
			sm.setMovecause(request.getParameter("movecause"));
			sm.setRemark(request.getParameter("remark"));
			sm.setMoveType(moveTypeInteger);

			AppMoveApplyDetail asmd = new AppMoveApplyDetail();

			
			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strunitid[] = request.getParameterValues("unitid");
			// String strbatch[] = request.getParameterValues("batch");
		//	String strunitprice[] = request.getParameterValues("unitprice");
			String strquantity[] = request.getParameterValues("quantity");
			//String strsubsum[] = request.getParameterValues("subsum");
			String productid;
			Long unitid;
			Double  quantity;
			String productname, specmode;
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(sm.getId()).append(",");

			// AppStockAlterMoveDetail asmd = new AppStockAlterMoveDetail();
			asmd.delMoveApplyDetailByAmid(id);
			// AppStockAlterMove asb = new AppStockAlterMove();

			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				unitid = Long.valueOf(strunitid[i]);
				if (dv.IsDouble(strquantity[i])) {
					quantity = Double.valueOf(strquantity[i]);
				} else {
					quantity = Double.valueOf(0);
				}

				MoveApplyDetail smd = new MoveApplyDetail();
				smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
						"move_apply_detail", 0, "")));
				smd.setMaid(id);
				smd.setProductid(productid);
				smd.setProductname(productname);
				smd.setSpecmode(specmode);
				smd.setUnitid(unitid.intValue());
				smd.setUnitprice(0d);
				smd.setQuantity(quantity);
				smd.setCanquantity(0d);
				smd.setAlreadyquantity(0d);
				smd.setSubsum(0d);
				asmd.addMoveApplyDetail(smd);

			}

			sm.setTotalsum(0d);
			sm.setKeyscontent(keyscontent.toString());
			asm.updMoveApply(sm);

			request.setAttribute("result", "databases.upd.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(request, "编号：" + id,oldsm, sm);
			return mapping.findForward("updresult");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

		return new ActionForward(mapping.getInput());
	}
}
