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
import com.winsafe.drp.dao.AppSupplySaleMoveDetail;
import com.winsafe.drp.dao.SupplySaleMove;
import com.winsafe.drp.dao.SupplySaleMoveDetail;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

public class TxtPutSupplySaleMoveAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {

			String Condition = "ssa.inorganid = '"+users.getMakeorganid()+
			"' and ssa.id=ssad.ssmid and ssa.isshipment=1 and ssa.isblankout=0 and ssad.productid=p.id and p.isidcode=1 ";
	    	
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);

			String[] tablename = { "SupplySaleMove"};
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);
			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MoveDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "KeysContent");
			whereSql = whereSql + blur+ timeCondition + Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppSupplySaleMoveDetail api = new AppSupplySaleMoveDetail();
			List pils = api.getSupplySaleMoveDetail(whereSql);
			
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=SupplySaleMove.txt");
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
				SupplySaleMove pi = (SupplySaleMove)o[0];
				SupplySaleMoveDetail d = (SupplySaleMoveDetail)o[1];
				
				bos.write(StringUtil.fillBack(pi.getId(), Constants.TXT_DL_BILLNO, Constants.TXT_FILL_STRING).getBytes());
				
				bos.write(StringUtil.fillBack(pi.getInorganid(), Constants.TXT_DL_ORGAN, Constants.TXT_FILL_STRING).getBytes());
				
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
}
