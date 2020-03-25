package com.winsafe.drp.action.purchase;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

/**
 * @author : jelli
 * @version : 2009-9-1 下午02:31:07 www.winsafe.cn
 */
public class TxtPutProductAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pid = request.getParameter("OtherKey");
		try {
			Map map = new HashMap(request.getParameterMap());
		      Map tmpMap = EntityManager.scatterMap(map);
		      String[] tablename={"Product"};
		      String whereSql = EntityManager.getTmpWhereSql(map, tablename);
		      StringBuffer buf = new StringBuffer();
		      if( pid !=null && pid.length()>0){
			    	buf.append("(");
				    buf.append("psid like '" + pid + "%')");
		            whereSql = whereSql + buf ; 

		      }
		      String blur = DbUtil.getOrBlur(map, tmpMap, "ID","ProductName","SpecMode","PYCode");
		      String timeCondition = DbUtil.getTimeCondition(map, tmpMap, "MakeDate");

		      whereSql = whereSql + blur + timeCondition;
		      whereSql = DbUtil.getWhereSql(whereSql); 
		      
			AppProduct app = new AppProduct();
			List<Product> list = app.findByWhereSql(whereSql);
			
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=Product.txt");
			response.setContentType("application/octet-stream");
			
			writeTxt(list, response.getOutputStream());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

	private void writeTxt(List<Product> olist, OutputStream os){
		BufferedOutputStream bos = null;
		try{
			bos = new BufferedOutputStream(os);			
			for ( Product o : olist ){
				bos.write(StringUtil.fillBack(o.getId(), Constants.TXT_DL_PRODUCTID, Constants.TXT_FILL_STRING).getBytes());
				bos.write(StringUtil.fillBack(o.getProductname(), Constants.TXT_DL_PRODUCTNAME, Constants.TXT_FILL_STRING).getBytes());
				bos.write(o.getIsbatch().toString().getBytes());
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
