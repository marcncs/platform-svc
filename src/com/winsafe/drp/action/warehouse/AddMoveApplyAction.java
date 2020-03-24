package com.winsafe.drp.action.warehouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.AppMoveApplyDetail;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.dao.MoveApplyDetail;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.metadata.StockMoveConfirmStatus;
import com.winsafe.drp.util.AuditMailUtil;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.drp.util.DataValidate;
import com.winsafe.drp.util.JProperties;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddMoveApplyAction extends BaseAction {
	private static Logger logger = Logger.getLogger(AddMoveApplyAction.class);
	private AppMoveApply asm = new AppMoveApply();
	private AppMoveApplyDetail asmd = new AppMoveApplyDetail();
/*
 * 如果出库机构和入库机构相同则为机构内转仓，，反之则为机构间转仓
 * 
 * 机构内转仓，如果是不需要审批，则直接生成转仓单，反之，则需要机构的区域经理，和转二的用户进行审批
 * 机构间转仓 ，依然走以前的流程，asm1，-》站一用户审批——》入库asm2s审批-》站二用户审批
 */
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UsersBean users = UserManager.getUser(request);
		Integer userid = users.getUserid();

		super.initdata(request);
		try {
			Properties pro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
			String moveApplyOrganId = pro.getProperty("moveApplyOrganId")!=null?pro.getProperty("moveApplyOrganId"):"";
			String outorganid = request.getParameter("outorganid");
			String receiveorganid = request.getParameter("receiveorganid");
			String moveType = request.getParameter("moveType");
			Integer moveTypeInteger = null;
			if(outorganid.equals(receiveorganid)
					&& moveApplyOrganId.indexOf(outorganid)==-1){//机构内转仓，转仓类型必须为空，不然影响统计, moveApplyOrganId  除外
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
			
			AppOrgan ao = new AppOrgan();
			MoveApply sm = new MoveApply();
			// String smid = MakeCode.getExcIDByRandomTableName("move_apply", 2,
			// "MA");
			String smid = MakeCode.getExcIDByRandomTableName("stock_move", 2, "SM");
			sm.setId(smid);
			// 机构间转仓日期
			sm.setMovedate(DateUtil.StringToDate(request.getParameter("movedate")));
			// 调出机构
			sm.setOutorganid(outorganid);
			// 调出仓库
			sm.setOutwarehouseid(request.getParameter("outwarehouseid"));
			// 调入机构
			sm.setInorganid(receiveorganid);
			// 调入仓库
			sm.setInwarehouseid(request.getParameter("inwarehouseid"));
			// 联系人
			sm.setOlinkman(request.getParameter("olinkman"));
			// 联系电话
			sm.setOtel(request.getParameter("otel"));
			// 收货地址
			sm.setTransportaddr(request.getParameter("transportaddr"));
			sm.setMakeorganidname(ao.getOrganByID(users.getMakeorganid()).getOrganname());
			// 机构间转仓原因
			sm.setMovecause(request.getParameter("movecause"));
			sm.setRemark(request.getParameter("remark"));
			sm.setIsratify(0);
			sm.setIsblankout(0);
			sm.setMakeorganid(users.getMakeorganid());
			sm.setMakedeptid(users.getMakedeptid());
			sm.setMakeid(userid);
			sm.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
			sm.setIstrans(0);
			sm.setMoveType(moveTypeInteger);

			String strproductid[] = request.getParameterValues("productid");
			String strproductname[] = request.getParameterValues("productname");
			String strspecmode[] = request.getParameterValues("specmode");
			String strunitid[] = request.getParameterValues("unitid");
			// String strbatch[] = request.getParameterValues("batch");
			// String strunitprice[] = request.getParameterValues("unitprice");
			String strquantity[] = request.getParameterValues("quantity");
			// String strsubsum[] = request.getParameterValues("subsum");

			Integer unitid;
			Double quantity;
			String productid, productname, specmode;
			StringBuffer keyscontent = new StringBuffer();
			keyscontent.append(sm.getId()).append(",");

			for (int i = 0; i < strproductid.length; i++) {
				productid = strproductid[i];
				productname = strproductname[i];
				specmode = strspecmode[i];
				unitid = Integer.valueOf(strunitid[i]);
				if (DataValidate.IsDouble(strquantity[i])) {
					quantity = Double.valueOf(strquantity[i]);
				} else {
					quantity = Double.valueOf(0.00);
				}

				MoveApplyDetail smd = new MoveApplyDetail();
				smd.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("move_apply_detail", 0, "")));
				smd.setMaid(smid);
				smd.setProductid(productid);
				smd.setProductname(productname);
				smd.setSpecmode(specmode);
				smd.setUnitid(unitid);
				smd.setUnitprice(0d);
				smd.setQuantity(quantity);
				smd.setCanquantity(0d);
				smd.setAlreadyquantity(0d);
				smd.setSubsum(0d);

				asmd.addMoveApplyDetail(smd);

			}

			sm.setTotalsum(0d);
			sm.setKeyscontent(keyscontent.toString());

			sm.setIsaudit(1);
			sm.setAuditid(userid);
			sm.setAuditdate(DateUtil.getCurrentDate());

			asm.addMoveApply(sm);

			boolean isAutoAudit = false;
			//先判断是否是机构内转仓
			if(sm.getOutorganid().equals(sm.getInorganid())){//机构一致，则为机构内转仓
				AppOrgan appOrgan = new AppOrgan();
				Organ outO = appOrgan.getOrganByID(sm.getOutorganid());
				if(outO != null){
					if(outO.getIsNeedApprove() == null || outO.getIsNeedApprove() != 1){//不需要审批的机构，直接生成转仓单据
						isAutoAudit = true;
					}
				}
			}else{//机构不一致，则为机构间转仓
				if (sm.getMoveType() == 3) {// 转仓类型是平台交易
					AppWarehouse appWarehouse = new AppWarehouse();
					Warehouse inWarehouse = appWarehouse.getWarehouseByID(sm.getInwarehouseid());
					Warehouse outWarehouse = appWarehouse.getWarehouseByID(sm.getOutwarehouseid());
					if (inWarehouse != null && outWarehouse != null) {
						if(inWarehouse.getWarehouseproperty() == 4 || outWarehouse.getWarehouseproperty() == 4){//转入仓库或转出仓库任意一个的仓库属性是平台仓
							isAutoAudit = true;
						}
					}
				}
			}
			// 机构间转仓申请自动审核
			autoRatify(sm, users, isAutoAudit);

			request.setAttribute("result", "databases.add.success");

			DBUserLog.addUserLog(userid, "销售管理", "销售管理>>机构间转仓申请，新增编号的：" + smid + "的机构间转仓申请单");
			return mapping.findForward("success");
		} catch (Exception e) {
			logger.error("",e);
			e.printStackTrace();
		}

		return new ActionForward(mapping.getInput());
	}

	public void autoRatify(MoveApply moveApply, UsersBean usersBean ,boolean isAutoAudit) throws Exception {

		// 机构间转仓申请自动审核
		Properties sysPro = JProperties.loadProperties("system.properties", JProperties.BY_CLASSLOADER);
		if (sysPro.getProperty("autoRatifyMoveApply").equals("1") || isAutoAudit) {

			// 该list用于机构间转仓申请自动审核
			List<MoveApplyDetail> listDetail = asmd.getMoveApplyDetailByAmID(moveApply.getId());
			List<MoveApplyDetail> listDetails = new ArrayList<MoveApplyDetail>();

			for (int i = 0; i < listDetail.size(); i++) {
				MoveApplyDetail moveApplyDetail = listDetail.get(i);
				moveApplyDetail.setCanquantity(moveApplyDetail.getQuantity());
				listDetails.add(moveApplyDetail);
			}

			OrganMoveService organMoveService = new OrganMoveService();

			organMoveService.addStockMove(moveApply, listDetails, usersBean);

			moveApply.setIsratify(StockMoveConfirmStatus.CHANNEL_APPROVED.getValue());
			moveApply.setRatifyid(userid);
			moveApply.setRatifydate(DateUtil.getCurrentDate());
			asm.updMoveApply(moveApply);
		} else {//走审批流程 
			AuditMailUtil.addNotificationMail(moveApply);
		}
	}

}
