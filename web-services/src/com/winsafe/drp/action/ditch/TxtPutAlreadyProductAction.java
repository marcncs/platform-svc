package com.winsafe.drp.action.ditch;

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
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.HtmlSelect;
import com.winsafe.hbm.util.StringUtil;

/**
 * @author : jelli
 * @version : 2009-9-1 下午02:31:07 www.winsafe.cn
 */
public class TxtPutAlreadyProductAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{
			UsersBean users = UserManager.getUser(request);

			String oid = users.getMakeorganid();

			String condition = " op.organid='" + oid+ "' and p.id=op.productid  ";
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Product" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String leftblur = DbUtil.getBlurLeft(map, tmpMap, "PSID");
			String blur = DbUtil.getOrBlur(map, tmpMap, "p.ID", "p.ProductName","p.SpecMode","p.PYCode");
			whereSql = whereSql + leftblur + blur + condition;
			whereSql = DbUtil.getWhereSql(whereSql);
		      
			AppOrganProduct aop = new AppOrganProduct();
			
			List list = aop.getOrganProductAlready(whereSql);
			
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=Product.txt");
			response.setContentType("application/octet-stream");
			response.setCharacterEncoding("UTF-8");
			writeTxt(list, response.getOutputStream(),request);
		} catch (Exception ex) {
			WfLogger.error("", ex);
		}

		return null;
	}

	private void writeTxt(List olist, OutputStream os,HttpServletRequest request){
		BufferedOutputStream bos = null;
		AppICode appic = new AppICode();
		try{
			bos = new BufferedOutputStream(os);			
			for ( int i=0; i<olist.size(); i++ ){
				Map m = (Map)olist.get(i);
				bos.write(StringUtil.fillBack(StringUtil.removeNull((String)m.get("productid")), Constants.TXT_DL_PRODUCTID, Constants.TXT_FILL_STRING).getBytes("UTF-8"));
				bos.write(StringUtil.fillBack(StringUtil.removeNull((String)m.get("productname")), Constants.TXT_DL_PRODUCTNAME, Constants.TXT_FILL_STRING).getBytes("UTF-8"));
				bos.write(StringUtil.fillBack(StringUtil.removeNull((String)m.get("specmode")), Constants.TXT_DL_PRODUCTNAME, Constants.TXT_FILL_STRING).getBytes("UTF-8"));				
				bos.write(StringUtil.fillBack(StringUtil.removeNull((String)m.get("packsizename")), Constants.TXT_DL_ORGANNAME, Constants.TXT_FILL_STRING).getBytes("UTF-8"));				
				Integer countunit = (Integer)m.get("countunit");
				String unitName = HtmlSelect.getResourceName(request,"CountUnit", countunit);
				bos.write(StringUtil.fillBack(unitName, 15, Constants.TXT_FILL_STRING).getBytes("UTF-8"));				
				bos.write("\r\n".getBytes("UTF-8"));
				
			}
			bos.flush();
			bos.close();
		}catch ( Exception e ){
			e.printStackTrace();
		}finally{
			if ( bos != null ){
				try{
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
