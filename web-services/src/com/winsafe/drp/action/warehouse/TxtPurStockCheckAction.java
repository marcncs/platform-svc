package com.winsafe.drp.action.warehouse;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import com.winsafe.drp.dao.AppStockCheckDetail;
import com.winsafe.drp.dao.StockCheck;
import com.winsafe.drp.dao.StockCheckDetail;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class TxtPurStockCheckAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
	
			String Condition = " (sc.makeid=" + userid + " " + getOrVisitOrgan("sc.makeorganid")+ ") "+
			" and sc.id=scd.scid and sc.isbar=1 and scd.productid=p.id and p.isidcode=1 ";

			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "StockCheck" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + timeCondition + blur + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 


			AppStockCheckDetail api = new AppStockCheckDetail();
			List pils = api.getStockCheckDetail(whereSql);
			
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=StockCheck.txt");
			response.setContentType("application/octet-stream");
			
			writeTxt(pils, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ActionForward(mapping.getInput());
	}
	
	private void writeTxt(List olist, OutputStream os){
		BufferedOutputStream bos = null;
		AppICode appic = new AppICode();
		try{
			bos = new BufferedOutputStream(os);			
			for (int i=0; i<olist.size(); i++ ){
				Object[] o = (Object[])olist.get(i);
				StockCheck pi = (StockCheck)o[0];
				StockCheckDetail d = (StockCheckDetail)o[1];
				
				bos.write(StringUtil.fillBack(pi.getId(), Constants.TXT_DL_BILLNO, Constants.TXT_FILL_STRING).getBytes());
				
				bos.write(StringUtil.fillBack(pi.getMakeorganid(), Constants.TXT_DL_ORGAN, Constants.TXT_FILL_STRING).getBytes());
				
				bos.write(StringUtil.fillBack(appic.getLCodeByPid(d.getProductid()), Constants.TXT_DL_PRODUCTID, Constants.TXT_FILL_STRING).getBytes());
				
				bos.write(StringUtil.fillBack(d.getBatch(), Constants.TXT_DL_BATCH, Constants.TXT_FILL_STRING).getBytes());
				
				bos.write(StringUtil.fillBack(DataFormat.douFormatStrInt(d.getReckonquantity()), Constants.TXT_DL_QUANTITY,Constants.TXT_FILL_STRING).getBytes());
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
