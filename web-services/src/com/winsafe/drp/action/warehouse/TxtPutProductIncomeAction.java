package com.winsafe.drp.action.warehouse;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.ProductIncomeDetail;
import com.winsafe.drp.dao.ScannerWarehouse;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketDetail;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.common.util.StringUtil;

public class TxtPutProductIncomeAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			//获取采集器编号
			String scanner=request.getParameter("scanner");
			//采集器下载
			if(!StringUtil.isEmpty(scanner)){
				return download2scanner(mapping,form,request,response);
			}

			String Condition = " (pi.makeid=" + userid + " "+ getOrVisitOrgan("pi.makeorganid")
					+ ") and pi.id=pid.piid and pid.productid=p.id and p.isidcode=1 ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "ProductIncome" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" IncomeDate");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppProductIncomeDetail api = new AppProductIncomeDetail();
			List pils = api.getProductIncomeDetail(whereSql);
			
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=ProductIncome.txt");
			response.setContentType("application/octet-stream");
			
			writeTxt(pils, response.getOutputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void writeTxt(List olist, OutputStream os){
		BufferedOutputStream bos = null;
		AppICode appic = new AppICode();
		try{
			bos = new BufferedOutputStream(os);			
			for (int i=0; i<olist.size(); i++ ){
				Object[] o = (Object[])olist.get(i);
				ProductIncome pi = (ProductIncome)o[0];
				ProductIncomeDetail d = (ProductIncomeDetail)o[1];
				
				bos.write(StringUtil.fillBack(pi.getId(), Constants.TXT_DL_BILLNO, Constants.TXT_FILL_STRING).getBytes());
				
				bos.write(StringUtil.fillBack(pi.getMakeorganid(), Constants.TXT_DL_ORGANNAME, Constants.TXT_FILL_STRING).getBytes());
				
				bos.write(StringUtil.fillBack(appic.getLCodeByPid(d.getProductid()), Constants.TXT_DL_PRODUCTID, Constants.TXT_FILL_STRING).getBytes());
				
				bos.write(StringUtil.fillBack("", Constants.TXT_DL_BATCH, Constants.TXT_FILL_STRING).getBytes());
				
				bos.write(StringUtil.fillBack(DataFormat.douFormatStrInt(d.getQuantity()), Constants.TXT_DL_QUANTITY,Constants.TXT_FILL_STRING).getBytes());
				bos.write("\r\n".getBytes());
				
			}
			bos.flush();
			bos.close();
		}catch ( Exception e ){
			e.printStackTrace();
		}finally{
			if ( bos != null ){
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	/**
	 * 采集器下载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward download2scanner(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//获取采集器编号
		String scanner=request.getParameter("scanner");
		
		Map<String,Object> map = new HashMap<String,Object>();
		StringBuilder hql = new StringBuilder();
		hql.append("from ScannerWarehouse where scannerid=:scannerid");
		map.put("scannerid", scanner);
		List<ScannerWarehouse> list = EntityManager.getAllByHql(hql.toString(),map);
		
		//判断采集器是否存在
		if(list!=null && list.size()>0){
			//存在
			
			StringBuilder inSql = new StringBuilder();
			for(ScannerWarehouse sw:list){
				inSql.append("'"+sw.getWarehouseid()+"',");
			}
			if(inSql.length()>0){
				inSql.deleteCharAt(inSql.length()-1);
			}
			
			ScannerWarehouse sw = list.get(0);
			map = new HashMap<String,Object>();
			hql = new StringBuilder();
			hql.append("from ProductIncome as pi ,ProductIncomeDetail as pid ");
			hql.append("where pi.id=pid.piid ");
			hql.append("and (pi.isaudit is null or pi.isaudit=0)");
			hql.append("and pi.warehouseid in("+inSql.toString()+") order by pi.id desc");
			List outlist = EntityManager.getAllByHql(hql.toString(),map);

			writeTxt2scanner(outlist,response.getOutputStream());
		}else{
			//不存在
			
			PrintWriter writer = response.getWriter();
			writer.write("fail\r\n");
			writer.write("无效的采集器编号！");
		}
		
		return null;
	}
	
	/**
	 * 输出数据到采集器
	 * @param list
	 * @param os
	 */
	private void writeTxt2scanner(List list, OutputStream os){
		BufferedOutputStream bos = null;
		try{
			bos = new BufferedOutputStream(os);			

			AppICode appic = new AppICode();
			for (int i=0; i<list.size(); i++ ){
				Object[] o = (Object[])list.get(i);
				ProductIncome tt = (ProductIncome)o[0];
				ProductIncomeDetail ttd = (ProductIncomeDetail)o[1];
				
				bos.write(StringUtil.fillBack(tt.getId(), Constants.TXT_DL_BILLNO, Constants.TXT_FILL_STRING).getBytes());
				
				bos.write(StringUtil.fillBack(tt.getMakeorganid(), Constants.TXT_DL_ORGANNAME, Constants.TXT_FILL_STRING).getBytes());
				
				bos.write(StringUtil.fillBack(appic.getLCodeByPid(ttd.getProductid()), Constants.TXT_DL_PRODUCTID, Constants.TXT_FILL_STRING).getBytes());
				
				bos.write(StringUtil.fillBack("", Constants.TXT_DL_BATCH, Constants.TXT_FILL_STRING).getBytes());
				
				bos.write(StringUtil.fillBack(DataFormat.douFormatStrInt(ttd.getQuantity()), Constants.TXT_DL_QUANTITY, Constants.TXT_FILL_STRING).getBytes());
				bos.write("\r\n".getBytes());
				
			}
			
			bos.flush();
			bos.close();
		}catch ( Exception e ){
			e.printStackTrace();
		}finally{
			if ( bos != null ){
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}
