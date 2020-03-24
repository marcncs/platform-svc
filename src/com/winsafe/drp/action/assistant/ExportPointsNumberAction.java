package com.winsafe.drp.action.assistant;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.AppPointNumber;
import com.winsafe.drp.dao.PointNumber;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.ProductIncomeDetail;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.util.DataFormat;

public class ExportPointsNumberAction extends BaseAction {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);

		try {

			String pointNumber = request.getParameter("pointNumber");
			String beginDate = request.getParameter("BeginDate");
			String endDate = request.getParameter("EndDate");
			
			String isactive = request.getParameter("isactive");
			String beginActiveDate = request.getParameter("beginActiveDate");
			String endActiveDate = request.getParameter("endActiveDate");
			
			//String sql ="select * from BdPointsNumber where  Points_Number is null";
			 String sql ="";
				
				if(!StringUtil.isEmpty(pointNumber) ){
					sql = "select * from BdPointsNumber where Points_Number='"+pointNumber+"'";
				}
				
				if(!StringUtil.isEmpty(beginDate) ){
					if(!StringUtil.isEmpty(sql)){
						sql+="  and AddDT>='"+beginDate+"'";
					}else{
						sql="select * from BdPointsNumber where AddDT>='"+beginDate+"'";
					}
				}
				
				if(!StringUtil.isEmpty(endDate) ){
					if(!StringUtil.isEmpty(sql)){
						sql+="  and AddDT<='"+endDate+"'";
					}else{
						sql="select * from BdPointsNumber where AddDT<='"+endDate+"'";
					}
				}
				
				
				if(!StringUtil.isEmpty(isactive) ){
					if(!StringUtil.isEmpty(sql)){
						sql+="  and IsActivated="+isactive;
					}else{
						sql = "select * from BdPointsNumber where IsActivated="+isactive;
					}
				}
				
				if(!StringUtil.isEmpty(beginActiveDate) ){
					if(!StringUtil.isEmpty(sql)){
						sql+="  and ActivatedDT>='"+beginActiveDate+"'";
					}else{
						sql="select * from BdPointsNumber where ActivatedDT>='"+beginActiveDate+"'";
					}
				}
				
				if(!StringUtil.isEmpty(endActiveDate) ){
					if(!StringUtil.isEmpty(sql)){
						sql+="  and ActivatedDT<='"+endActiveDate+" 23:59:59'";
					}else{
						sql="select * from BdPointsNumber where ActivatedDT<='"+endActiveDate+" 23:59:59'";
					}
				}
				
				
				if(StringUtil.isEmpty(sql)){
					sql ="select * from BdPointsNumber where  Points_Number is null";
				}

			List result = AppPointNumber.jdbcSqlserverQuery(request, sql);
			
			List<PointNumber> list = new ArrayList<PointNumber>();
			PointNumber pn = null;
			Map map = null;
			for (int i = 0; i < result.size(); i++) {
				map = (HashMap) result.get(i);

				pn = new PointNumber();
				pn.setPointsNumber((String) map.get("Points_Number"));

				String AddDT = (String) map.get("AddDT");
				Date date = Dateutil.formatDatetime(AddDT);
				pn.setAddDT(Dateutil.formatDate(date));

				list.add(pn);
				
//				if((i+1)%3000==0){
//					response.reset();
//					response.setHeader("content-disposition", "attachment; filename=PointNumber_"+Dateutil.getCurrenttimeStamp()+".txt");
//					response.setContentType("application/octet-stream");
//
//					writeTxt(list, response.getOutputStream());
//					
//					list.clear();
//				}
				
			}
			
			response.reset();
			response.setHeader("content-disposition", "attachment; filename=PointNumber_"+Dateutil.getCurrenttimeStamp()+".txt");
			response.setContentType("application/octet-stream");

			writeTxt(list, response.getOutputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void writeTxt(List<PointNumber> list, OutputStream os) {
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(os);
			
			bos.write(StringUtil.fillBack("积分（防伪）数据", 25, Constants.TXT_FILL_STRING).getBytes());

			bos.write(StringUtil.fillBack(" ", 3, Constants.TXT_FILL_STRING)
					.getBytes());
			
			bos.write(StringUtil.fillBack("     生成日期", 18, Constants.TXT_FILL_STRING)
					.getBytes());

			bos.write("\r\n".getBytes());
			
			for (int i = 0; i < list.size(); i++) {
				PointNumber pn = list.get(i);

				bos.write(StringUtil.fillBack(pn.getPointsNumber(), Constants.POINT_NUMBER, Constants.TXT_FILL_STRING).getBytes());

				bos.write(StringUtil.fillBack("，", 3, Constants.TXT_FILL_STRING)
						.getBytes());
				
				bos.write(StringUtil.fillBack(pn.getAddDT(), Constants.POINT_NUMBER_DATE, Constants.TXT_FILL_STRING)
						.getBytes());

				bos.write("\r\n".getBytes());

			}
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
