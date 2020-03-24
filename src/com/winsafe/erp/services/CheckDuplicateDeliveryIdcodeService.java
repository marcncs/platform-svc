package com.winsafe.erp.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.DuplicateDeliveryIdcodeReportForm;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.drp.util.MapUtil;
import com.winsafe.erp.dao.AppDuplicateDeliveryIdcode;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.hbm.util.pager2.PageQuery;
import com.winsafe.mail.dao.AppBatchCompleteMail;
import com.winsafe.mail.metadata.MailType;
import com.winsafe.mail.pojo.BatchCompleteMail;
import com.winsafe.mail.smtp.base.SMTPMailFactory;

public class CheckDuplicateDeliveryIdcodeService {

	private AppBaseResource appBr = new AppBaseResource();
	private AppDuplicateDeliveryIdcode appDdi = new AppDuplicateDeliveryIdcode();
	private AppBatchCompleteMail abcm = new AppBatchCompleteMail();
	private String TAG_NAME = "dupDeliveryIdcodeTime";
	
	public void deal() throws Exception {
		String startTime = getStartTime(TAG_NAME);
		String endTime = getEndTime();
		//获取该时间段内发出的条码
		appDdi.getDeliveryIdcode(startTime, endTime);
		//删除有退货给TD记录的条码
		appDdi.delReturnIdcode(); 
		//获取重复发货的条码记录
		List<Map<String,String>> dataList = appDdi.getDuplicateDeliveryIdcode();
		if(dataList != null && dataList.size() > 0) {
			//增加通知邮件
			addMail(dataList);
			//添加到日志表
			appDdi.addDuplicateDeliveryIdcode();
		}
		//更新收货日期
		appDdi.updReceiveDate();
		//更新结束日期
		updateEndTime(TAG_NAME, endTime);
	}
	
	private void addMail(List<Map<String, String>> dataList) {
		BatchCompleteMail mail = new BatchCompleteMail();
		Properties mailPro = SMTPMailFactory.getSMTPMailFactory();
		mail.setMailSender(mailPro.getProperty("Bayer_smtp_from"));
		mail.setMailFrom(mailPro.getProperty("Bayer_smtp_from"));
		mail.setMailTo(mailPro.getProperty("dup_idcode_mailTo"));//发送给谁
		mail.setMailSubject(mailPro.getProperty("dup_idcode_mailSubject"));//邮件标题
		mail.setCreateDate(DateUtil.getCurrentDate());
		mail.setMailType(MailType.DUPLICATE_DELIVERY_IDCODE.getDbValue());
		mail.setMailBody(getMaolBody(dataList));
		
		abcm.add(mail);
	}

	private String getMaolBody(List<Map<String, String>> dataList) {
		StringBuffer sb = new StringBuffer();
		sb.append("<span>发货单中发现如下重码信息：</span><br/>");
		sb.append("<table cellspacing=\"0\" border=\"1\" >");
		sb.append("<tr>");
		sb.append("<th>箱码号</th><th>产品物料号</th><th>产品名称</th><th>规格</th><th>批号</th><th>发货单号</th>");
		sb.append("<th>SAP单号</th><th>发货机构</th><th>发货仓库</th><th>发货日期</th><th>收货机构</th><th>收货仓库</th><th>收货日期</th>");
		sb.append("</tr>");
		for(Map<String, String> map : dataList) {
			sb.append("<tr>");
			sb.append("<td>").append(map.get("idcode")).append("</td>");
			sb.append("<td>").append(map.get("mcode")).append("</td>");
			sb.append("<td>").append(map.get("productname")).append("</td>");
			sb.append("<td>").append(map.get("specmode")).append("</td>");
			sb.append("<td>").append(map.get("batch")).append("</td>");
			sb.append("<td>").append(map.get("billno")).append("</td>");
			sb.append("<td>").append(StringUtil.removeNull(map.get("nccode"))).append("</td>");
			sb.append("<td>").append(map.get("organname")).append("</td>");
			sb.append("<td>").append(map.get("warehousename")).append("</td>");
			sb.append("<td>").append(map.get("auditdate")).append("</td>");
			sb.append("<td>").append(map.get("inorganname")).append("</td>");
			sb.append("<td>").append(map.get("inwarehousename")).append("</td>");
			sb.append("<td>").append(StringUtil.removeNull(map.get("receivedate"))).append("</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 获取起始时间
	 * @param tagName
	 * @return
	 * @throws Exception
	 */
	private String getStartTime(String tagName) throws Exception {
		BaseResource br = appBr.getBaseResourceValue(tagName, 1);
		if(br != null) {
			return br.getTagsubvalue();
		}
		return null;
	}
	
	private String getEndTime() {
		int delay = 0; 
		Calendar curTime = Calendar.getInstance();
		curTime.add(Calendar.MINUTE, -delay);
		return DateUtil.formatDateTime(curTime.getTime());
	}
	
	private void updateEndTime(String tagName, String endTime) throws Exception {
		BaseResource br = appBr.getBaseResourceValue(tagName, 1);
		br.setTagsubvalue(endTime);
		appBr.updBaseResource(br);
	}
	
	public static void main(String[] args) {
		
	}

	public List<DuplicateDeliveryIdcodeReportForm> queryReport(HttpServletRequest request, int pageSize,
			DuplicateDeliveryIdcodeReportForm queryForm, UsersBean users) throws Exception {
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		//对于结束日期增加一天
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			String endDateStr = queryForm.getEndDate();
			Date endDate = Dateutil.StringToDate(endDateStr);
			endDate = Dateutil.addDay2Date(1, endDate);
			queryForm.setEndDate(Dateutil.formatDate(endDate));
		}
		List<DuplicateDeliveryIdcodeReportForm> resultList = new ArrayList<DuplicateDeliveryIdcodeReportForm>();
		StringBuffer sql = new StringBuffer();
		
		sql.append(" \r\n select DDI.*,p.MCODE,p.SPECMODE,p.PRODUCTNAME,OUTO.organname outoname,INO.organname inoname,outw.warehousename outwname,inw.warehousename inwname from (");
		sql.append(" \r\n select IDCODE from DUPLICATE_DELIVERY_IDCODE where 1=1");
		if(!StringUtil.isEmpty(queryForm.getIdcode())){
			sql.append(" \r\n and idcode=?");
			param.put(UUID.randomUUID().toString(), queryForm.getIdcode()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getOutOid())){
			sql.append(" \r\n and outoid=?");
			param.put(UUID.randomUUID().toString(), queryForm.getOutOid()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getOutWid())){
			sql.append(" \r\n and outwid=?");
			param.put(UUID.randomUUID().toString(), queryForm.getOutWid()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getBeginDate())){
			sql.append(" \r\n and OUTDATE >=to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getBeginDate()); //开始时间条件
		}
		if(!StringUtil.isEmpty(queryForm.getEndDate())){
			sql.append(" \r\n and OUTDATE < to_date(?,'yyyy-MM-dd hh24:mi:ss') ");param.put(UUID.randomUUID().toString(), queryForm.getEndDate()); //结束时间条件
		}
		sql.append(" \r\n GROUP BY IDCODE");
		sql.append(" \r\n ) temp_t");
		sql.append(" \r\n join DUPLICATE_DELIVERY_IDCODE ddi on temp_t.idcode=ddi.idcode");
		sql.append(" \r\n join PRODUCT p on DDI.PRODUCTID=p.ID");
		sql.append(" \r\n join organ outo on ddi.outoid=outo.id");
		sql.append(" \r\n join organ ino on ddi.inoid=ino.id");
		sql.append(" \r\n join WAREHOUSE outw on ddi.outwid=outw.id");
		sql.append(" \r\n left join WAREHOUSE inw on ddi.inwid=inw.id ");
		List<Map> list =  new ArrayList<Map>();
		if(request == null && pageSize == 0){
			list =  EntityManager.jdbcquery(sql.toString(), param);
		}else {
			list =  PageQuery.jdbcSqlserverQuery(request, "idcode", sql.toString(), pageSize, param);
		}
		if(list != null && list.size()>0){
			for(Map map : list){
				DuplicateDeliveryIdcodeReportForm scForm = new DuplicateDeliveryIdcodeReportForm();
				//将Map中对应的值赋值给实例
				MapUtil.mapToObject(map, scForm);
				resultList.add(scForm);
			}
		}
		return resultList;
	}
	
	
}
