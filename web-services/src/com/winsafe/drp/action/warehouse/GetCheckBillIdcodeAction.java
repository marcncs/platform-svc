package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppSuggestInspect;
import com.winsafe.drp.dao.AppTakeBill;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketDetail;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.SuggestInspect;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.util.Encrypt;

public class GetCheckBillIdcodeAction extends BaseAction {
	AppTakeTicketIdcode apptti = new AppTakeTicketIdcode();
	AppTakeTicket appTakeTicket = new AppTakeTicket();
	
	AppTakeTicketDetail appttd = new AppTakeTicketDetail();
	AppTakeBill apptb = new AppTakeBill();
	AppStockAlterMove appsam = new AppStockAlterMove();
	AppSuggestInspect appsi = new AppSuggestInspect();
	AppOrgan appo = new AppOrgan();
	private final String separator = ",";
	private final String separator_ln = "\r\n";
	private final String separator_sm = ";";

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 获取单号
		String billNo = request.getParameter("billNo");
		String returnStr = "";
		if (!StringUtil.isEmpty(billNo)) {
			try {
				String username = request.getParameter("username"); //登陆名
				String password = request.getParameter("password");  //密码
				String securityPassword = Encrypt.getSecret(password, 1);  //加密后的密码
				// 判断用户是否存在
				AppUsers appUsers = new AppUsers();
//				Users loginUsers = appUsers.getUsers(username);
				UsersBean loginUsers = appUsers.CheckUsersNamePassword(username, securityPassword);
				if(loginUsers==null){
					ResponseUtil.write(response,"用户名无效！");
				}
				
				// 查询单据
				TakeTicket tt = appTakeTicket.getTakeTicketById(billNo);
				// 管家婆单据号
				String suids = "";
				// 管家婆订单日期
				String sidate = "";
				// 客户信息
				Organ o = null;
				// 订单
				TakeBill tb = null;
				if (tt != null && (tt.getIsChecked() == null || tt.getIsChecked() != 2)) {
					if(tt.getIsPicked() == null || tt.getIsPicked() != 2){
						ResponseUtil.write(response, "单据" + billNo + "拣货未完成！");
						return null;
					}
					// 查询条码
					List<TakeTicketIdcode> idcodeList = apptti
							.getTakeTicketIdcodeByttid(billNo);
					// 查询详情
					List<TakeTicketDetail> detailList = appttd
							.getTakeTicketDetailByTtid(billNo);
					// 查询管家婆单号和日期
					String samid = tt.getBillno();
					StockAlterMove sam = appsam.getStockAlterMoveByID(samid);
					tb = apptb.getTakeBillByID(tt.getBillno());
					if (sam != null) {
						suids = sam.getNccode2();
						suids = StringUtil.isEmpty(suids)?"":suids.replace(",", "#");
						SuggestInspect si = appsi
								.getSiBySiid(suids.split("#")[0]);
						if(si != null && si.getMakeDate() != null){
							sidate = Dateutil.formatDateTime(si.getMakeDate());
						}
						// 客户信息
						
					} else {
						// 单据不存在，则输出=billNo+"单据不存在！"
						//ResponseUtil.write(response, "单据" + billNo + "不存在！");
					}
					String inwh = tt.getInwarehouseid();
					o = appo.getOrganByWarehouseid(inwh);
					//拼接输出字符串
					returnStr = getReturnBuffer(suids, sidate, o, tt, idcodeList, detailList, tb);

					
					//修改此单状态为验货中
					tt.setIsChecked(1);
					appTakeTicket.updTakeTicket(tt);
					
				} else {
					// 单据不存在，则输出=billNo+"单据不存在！"
					ResponseUtil.write(response, "单据" + billNo + "不存在或者单据已验货！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				ResponseUtil.write(response,"获取验货数据出现错误！");
			}
		}
		// 将结果输出到采集器
		ResponseUtil.write(response, returnStr);
		return null;
	}

	public String getReturnBuffer(String suids, String sidate, Organ o,
			TakeTicket tt, List<TakeTicketIdcode> idcodeList,
			List<TakeTicketDetail> detailList, TakeBill tb) throws Exception {
		StringBuffer buffer = new StringBuffer();
		/*单据*/
		//单据号： TT201404200005
		buffer.append(checkNull(tt.getId())+separator);
		//仓库编号：11133
		buffer.append(checkNull(tt.getWarehouseid())+separator);
		//客户编号：10000084
		buffer.append(checkNull(o.getId())+separator);
		//客户名称:测试经销商
		buffer.append(checkNull(o.getOrganname())+separator);
		//客户地址：湖北省武汉市东西湖区
		buffer.append(checkNull(o.getOaddr())+separator);
		//送货日期：2014-05-21
		buffer.append(checkNull(tb.getSenddate())+separator);
		//管家婆订单号
		buffer.append(checkNull(suids)+separator);
		//管家婆订单日期
		buffer.append(checkNull(sidate)+separator_ln);
		/*单据详情*/
		for (int i = 1; i <= detailList.size(); i++) {
			TakeTicketDetail ttd = detailList.get(i-1);
//			//单据号：TT201404200005
			buffer.append(ttd.getTtid()+separator);
			//产品编号: 010087
			buffer.append(ttd.getProductid()+separator);
			//产品名称: 弹力娇嫩新生水120ML
			buffer.append(ttd.getProductname()+separator);
			//产品总数量：300
			buffer.append(ttd.getQuantity()+separator);
			//产品规格： 120ML
			buffer.append(checkNull(ttd.getSpecmode())+(i!=detailList.size()?separator_sm:separator_sm+separator_ln));
		}
		
		/*单据码*/
		for (int i = 1; i <= idcodeList.size(); i++) {
			TakeTicketIdcode tti = idcodeList.get(i-1);
//			//单据号： TT201404200005
			buffer.append(tti.getTtid()+separator);
			//产品编号： 010087
			buffer.append(tti.getProductid()+separator);
			//码： H0004140422K03385001#0122013
			buffer.append(tti.getIdcode()+separator);
			//规格数量： 12
			buffer.append(tti.getPackquantity()+separator_sm);
		}
		return buffer.toString();
	}
	
	public Object checkNull(Object var) throws Exception{
		return var == null?"":var;
	}
}
