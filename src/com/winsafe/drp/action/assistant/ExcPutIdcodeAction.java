package com.winsafe.drp.action.assistant;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;

public class ExcPutIdcodeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		try {
			UsersBean users = UserManager.getUser(request);
//			Long userid = users.getUserid();
			
			String Condition = "";
			if (users.getVisitorgan() != null
					&& users.getVisitorgan().length() > 0) {
				Condition = "  makeorganid in(" + users.getVisitorgan()
						+ ")";
			}
			Map map = new HashMap(request.getParameterMap());
			Map tmpMap = EntityManager.scatterMap(map);
			String[] tablename = { "Idcode" };
			String whereSql = EntityManager.getTmpWhereSql(map, tablename);

			String timeCondition = DbUtil.getTimeCondition(map, tmpMap,
					" MakeDate");
			String blur = DbUtil.getOrBlur(map, tmpMap, "ProductID","ProductName","BillID","ProvideName","IDCode");

			whereSql = whereSql + timeCondition +blur+Condition; 
			whereSql = DbUtil.getWhereSql(whereSql); 

			AppIdcode ap = new AppIdcode();
			AppOrgan ao = new AppOrgan();
			AppWarehouse aw = new AppWarehouse();
			List<Idcode> idlist = ap.searchIdcode(whereSql);
			
			String[] sc = request.getParameterValues("sc");
			String fname = "idcode";//excel文件名    
		    OutputStream os = response.getOutputStream();//取得输出流    
		    response.reset();//清空输出流    
		    response.setHeader("content-disposition", "attachment; filename=" + fname + ".xls");//设定输出文件头    
		    response.setContentType("application/msexcel");//定义输出类型    
		    writeXls(idlist, os, request, sc);
		    os.flush();
		    os.close();


		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	 public  void writeXls(List list, OutputStream os, HttpServletRequest request, String[] sc) throws Exception{		  
		 	AppOrgan ao = new AppOrgan();
			AppWarehouse aw = new AppWarehouse();
			WritableWorkbook workbook = jxl.Workbook.createWorkbook(os);
			Label lr;				
			int snum = 1;
			snum = list.size()/50000;
			if ( list.size()%50000 > 0 ){
				snum ++;
			}
			WritableSheet[] sheets = new WritableSheet[snum];
			for (int j=0; j<snum; j ++ ){
				sheets[j] = workbook.createSheet("sheet"+j, j);
				int currentnum = (j+1)*50000;
				if ( currentnum >= list.size() ){
					currentnum = list.size();
				}
				int start = j * 50000;	
				for ( int s=0; s<sc.length; s++ ){
					if ( "1".equals(sc[s]) ){
						sheets[j].addCell(new Label(s, start, "序号"));
					}else if ( "2".equals(sc[s]) ){
						sheets[j].addCell(new Label(s, start, "产品编号"));
					}else if ( "3".equals(sc[s]) ){
						sheets[j].addCell(new Label(s, start, "产品名称"));
					}else if ( "4".equals(sc[s]) ){
						sheets[j].addCell(new Label(s, start, "制单机构"));
					}else if ( "5".equals(sc[s]) ){
						sheets[j].addCell(new Label(s, start, "入库单号"));
					}else if ( "6".equals(sc[s]) ){
						sheets[j].addCell(new Label(s, start, "仓库"));
					}else if ( "7".equals(sc[s]) ){
						sheets[j].addCell(new Label(s, start, "供应商"));
					}else if ( "8".equals(sc[s]) ){
						sheets[j].addCell(new Label(s, start, "入库日期"));
					}				
				}				
				for (int i=start; i<currentnum; i++ ){
					int row = i - start + 1;
					Idcode c = (Idcode)list.get(i);					
					for ( int s=0; s<sc.length; s++ ){
						if ( "1".equals(sc[s]) ){
							lr = new Label(s, row, c.getIdcode());
							sheets[j].addCell(lr);
						}else if ( "2".equals(sc[s]) ){
							lr = new Label(s, row, c.getProductid());
							sheets[j].addCell(lr);
						}else if ( "3".equals(sc[s]) ){
							lr = new Label(s, row, c.getProductname());
							sheets[j].addCell(lr);
						}else if ( "4".equals(sc[s]) ){
							if ( c.getMakeorganid()!=null && !"".equals(c.getMakeorganid()) ){
								lr = new Label(s, row, ao.getOrganByID(c.getMakeorganid()).getOrganname());
							}else{
								lr = new Label(s, row, "");
							}							
							sheets[j].addCell(lr);
						}else if ( "5".equals(sc[s]) ){
							lr = new Label(s, row, c.getBillid());
							sheets[j].addCell(lr);
						}else if ( "6".equals(sc[s]) ){
//							if ( c.getWarehouseid() !=null && c.getWarehouseid()>0 ){
//								lr = new Label(s, row, aw.getWarehouseByID(c.getWarehouseid()).getWarehousename());
//							}else{
//								lr = new Label(s, row, "");
//							}							
//							sheets[j].addCell(lr);
						}else if ( "7".equals(sc[s]) ){
							lr = new Label(s, row, c.getProvidename()==null?"":c.getProvidename());
							sheets[j].addCell(lr);
						}else if ( "8".equals(sc[s]) ){
							lr = new Label(s, row, DateUtil.formatDateTime(c.getMakedate()));
							sheets[j].addCell(lr);
						}				
					}	            
				}
			}			
			workbook.write();
	        workbook.close();
	        os.close();
		}

}
