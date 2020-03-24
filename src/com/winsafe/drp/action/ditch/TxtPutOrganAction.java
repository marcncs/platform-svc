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
import com.winsafe.drp.dao.AppOrgan; 
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;

/**
 * @author : jerry
 * @version : 2009-8-22 下午02:31:07 www.winsafe.cn
 */
public class TxtPutOrganAction extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);try{

			UsersBean users = UserManager.getUser(request);
			String Condition = "";
			if(DbUtil.isDealer(users)) {
				Condition = " o.parentid ='"+users.getMakeorganid()+"' ";
			} else { 
				Condition = DbUtil.getWhereCondition(users,"o");
			}
			Map map=new HashMap(request.getParameterMap());
	        Map tmpMap = EntityManager.scatterMap(map);
	        String[] tablename={"Organ"};
	        String whereSql = EntityManager.getTmpWhereSql(map, tablename); 

	        String blur = DbUtil.getOrBlur(map, tmpMap, "ID","OrganName","OMobile","OECode"); 
	        whereSql = whereSql + blur +Condition ; 
	        whereSql = DbUtil.getWhereSql(whereSql); 
	        
			AppOrgan app = new AppOrgan();
			List<Map<String,String>> list = app.getOrganListByWhere(whereSql);
						
			response.reset();
			response.setHeader("content-disposition",
					"attachment; filename=Organ.txt");
			response.setContentType("application/octet-stream;charset=UTF-8");
			
			writeTxt(list, response.getOutputStream());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}
	
	private void writeTxt(List<Map<String, String>> list, OutputStream os){
		BufferedOutputStream bos = null;
		try{
			bos = new BufferedOutputStream(os);			
			for ( Map<String, String> o : list ){
				bos.write(StringUtil.fillBack(o.get("id"), Constants.TXT_DL_ORGAN, Constants.TXT_FILL_STRING).getBytes());
				bos.write(StringUtil.fillBack(o.get("oecode"), Constants.TXT_DL_NCCODE, Constants.TXT_FILL_STRING).getBytes());
				bos.write(StringUtil.fillBack(o.get("organname"), Constants.TXT_DL_ORGANNAME, Constants.TXT_FILL_STRING).getBytes());
				bos.write("\r\n".getBytes());
				
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
