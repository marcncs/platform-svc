package com.winsafe.drp.action.ditch;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrganPriceii;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppOrganScan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseBit;
import com.winsafe.drp.dao.WarehouseVisit;
import com.winsafe.drp.server.OrganService;
import com.winsafe.drp.server.WarehouseService;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.BeanCopy;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;


public class ImportFirOrganAction extends BaseAction {
	/*
	 *  ** joey add for  firstOrgan *** 
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		IdcodeUploadForm mf = (IdcodeUploadForm) form;
		FormFile uploadfile = (FormFile) mf.getIdcodefile();
		int CCount =0;
		try{
			HibernateUtil.currentSession();    //启动同步
			boolean bool = false;
			if (uploadfile != null && !uploadfile.equals("")) {
				if (uploadfile.getContentType() != null) {
					if (uploadfile.getFileName().indexOf("xls") >= 0) 
						bool = true;
				}
			}
			if (bool) {
				Workbook wb = Workbook.getWorkbook(uploadfile.getInputStream());
				Sheet sheet = wb.getSheet(0);
				int row = sheet.getRows();
				boolean ckStr=checkExcelType(sheet);
				if(ckStr){
				}else{
					request.setAttribute("result", "此处为一级机构上传，请确认！");
					return new ActionForward("/sys/lockrecord2.jsp");
				}
				for(int i=1;i<row;i++){				
					UsersBean users = UserManager.getUser(request);
					Integer userid = users.getUserid();
					Warehouse w=null;

					OrganService ao = new OrganService();
					Organ co = ao.getOrganByID(sheet.getCell(0, i).getContents().trim());
					if (co != null) {
						request.setAttribute("result", "第<font color='red'>***"+i+"***</font>行"+co.getId() + "此机构编号已经存在，请重新录入!");
						HibernateUtil.rollbackTransaction();    //用于处理同步回滚
						return new ActionForward("/sys/lockrecord2.jsp");
					}
					String parentid ="00000002";

					Organ parentOrgan = ao.getOrganByID(parentid);
					String sysid = ao.getOcodeByParent(parentOrgan.getSysid());
					Organ dt = new Organ();
					if (parentid == null) {
						parentid = "0";
						dt.setRank(0);
					} else {
						dt.setRank(ao.getOrganByID(parentid).getRank() + 1);
					}
					//联系人名称
					String strname = sheet.getCell(3, i).getContents().trim();
					//仓库关联ID
					String id = sheet.getCell(0, i).getContents().trim();
					//联系人电话
					String strlinkmobile = sheet.getCell(4, i).getContents().trim();
					//机构地址
					String organadr = sheet.getCell(2, i).getContents().trim();
					//机构名称
					String organname= sheet.getCell(1, i).getContents().trim();
					if("".equals(strname)||"".equals(id)||"".equals(strlinkmobile)||"".equals(organadr)||"".equals(organname)){
						request.setAttribute("result", "第<font color='red'>***"+i+"***</font>行"+"内有空值！");
						HibernateUtil.rollbackTransaction();
						return new ActionForward("/sys/lockrecord2.jsp");
					}		
					
					dt.setId(MakeCode
							.getExcIDByRandomTableName("organ", 4, ""));
					dt.setSysid(sysid);
					dt.setOrganname(organname);
					dt.setOecode(sysid);
					dt.setOemail("");
					dt.setOfax("");
					dt.setOmobile("");
					dt.setOtel("");
					dt.setCity(0);
					dt.setProvince(0);
					dt.setAreas(0);
					dt.setPprompt(0);
					dt.setPrompt(0);
					dt.setRate(0);
					dt.setRepealid(0);
					dt.setLogo("");
					dt.setPaycondition("");
					dt.setParentid(parentid);
					dt.setOaddr(organadr);
			//****** 增加联系人表 ******
					AppOlinkMan appol = new AppOlinkMan();
					Olinkman linkman = null;
							linkman = new Olinkman();
							linkman.setId(Integer.valueOf(MakeCode
									.getExcIDByRandomTableName("olinkman", 0, "")));
							linkman.setCid(dt.getId());
							linkman.setName(strname);
							linkman.setSex(2);
							linkman.setMobile(strlinkmobile);
							// linkman.setTransit(vc.getTransit()[i]);
							linkman.setMakeorganid(users.getMakeorganid());
							linkman.setMakedeptid(users.getMakedeptid());
							linkman.setMakeid(userid);
							linkman.setMakedate(DateUtil.getCurrentDate());
					appol.addOlinkman(linkman);			
					AppOrganScan appos = new AppOrganScan();
					appos.insertOrganScan(dt.getId());													
					//默认增加机构为非撤销--RichieYu----20100526
					dt.setIsrepeal(0);
					ao.AddOrgan(dt);					
					try {
						 w = new Warehouse();
						Integer warehouseid = Integer.valueOf(MakeCode
								.getExcIDByRandomTableName("warehouse", 0, ""));
						w.setId(MakeCode.getFormatNums(warehouseid, 5));
						w.setNccode(id);
						w.setWarehousename(organadr);
						w.setDept(0);
						w.setUserid(0);
						w.setUsername(strname);
						w.setWarehousetel("");
						w.setWarehouseproperty(0);
						w.setProvince(0);
						w.setCity(0);
						w.setAreas(0);
						w.setWarehouseaddr(organadr);
						w.setMakeorganid(dt.getId());
						w.setMakedeptid(0);
						w.setUseflag(1);
						w.setIsautoreceive(0);
						w.setRemark("");

						WarehouseService aw = new WarehouseService();
						aw.addWarehouse(w);

						WarehouseBit whbit = new WarehouseBit();
						whbit.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
								"warehouse_bit", 0, "")));
						whbit.setWid(w.getId());
						whbit.setWbid("000");
						aw.addWarehouseBit(whbit);
					}catch(Exception e){
						request.setAttribute("result", "第<font color='red'>***"+i+"***</font>行"+dt.getId() + "在设置仓库和仓位过程中出错！");
						HibernateUtil.rollbackTransaction();    //用于处理同步回滚						
						return new ActionForward("/sys/lockrecord2.jsp");
					}
						
				//****** 许可部分 begin	******				
						String wid = w.getId();
					    try{
					      AppUsers au = new AppUsers();
					      List uls = au.getIDAndLoginNameByOID2(users.getMakeorganid());

					      AppWarehouseVisit awv = new AppWarehouseVisit();
					      List alrd = awv.getWarehouseVisitWID(wid);
					      ArrayList alls = new ArrayList();
					      for(int l=0;l<alrd.size();l++){
					        UsersBean alub = new UsersBean();
					        Object[] ob = (Object[])alrd.get(l);
					        alub.setUserid(Integer.valueOf(ob[2].toString()));
					        alub.setLoginname(au.getUsersByID(alub.getUserid()).getLoginname());
					        alub.setRealname(au.getUsersByID(alub.getUserid()).getRealname());
					        alls.add(alub);
					      }		  
					      request.setAttribute("alls",alls);
					      request.setAttribute("auls",uls);
					   
					      List<Users> allusers = new AppUsers().getAllUsers();
					      awv.delWarehouseVisitByWID(wid);
					      for (Users user : allusers) {
					    	  WarehouseVisit wv = new WarehouseVisit();
					    	  wv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse_visit",0,"")));
					    	  wv.setWid(wid);
					    	  wv.setUserid(user.getUserid());
					    	  awv.addWarehouseVisit(wv); 
					      }
					    }catch(Exception e){
					    	request.setAttribute("result", "第<font color='red'>***"+i+"***</font>行"+dt.getId() + "在许可过程中出错！");
							HibernateUtil.rollbackTransaction();    //用于处理同步回滚						
							return new ActionForward("/sys/lockrecord2.jsp");
					    }
			//****** 许可部分  end  ******		
			//****** 上架部分 begin ******  
					    try{
					     List<Product> products = new AppProduct().getProduct(null);
					     OrganService os = new OrganService();
							AppOrganProduct aop = new AppOrganProduct();
							AppOrganPriceii appprice = new AppOrganPriceii();
							OrganProduct op = new OrganProduct();
							for (Product product : products) {
								op.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
										"organ_product", 0, "")));
								op.setOrganid(dt.getId());
								op.setProductid(product.getId());
								aop.addOrganProductNoExist(op);
								appprice.delOrganPriceiiByOIDPID(dt.getId(), product.getId());
								appprice.copyProductPriceii(dt.getId(), product.getId(), os.getOrganByID(dt.getId()).getRate());
							}
								
					    }catch(Exception e){
					    	request.setAttribute("result", "第<font color='red'>***"+i+"***</font>行"+dt.getId() + "在上架过程中出错！");
							HibernateUtil.rollbackTransaction();    //用于处理同步回滚						
							return new ActionForward("/sys/lockrecord2.jsp");
					    }
			//****** 上架部分 end ******       
			   			CCount++;													
				}	
				request.setAttribute("result", "成功上传"+CCount+"条");
				HibernateUtil.commitTransaction();		 //同步提交
			}else {
				request.setAttribute("result", "格式有误！");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			return new ActionForward("/sys/lockrecord2.jsp");			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("result", "新增失败!");
			return new ActionForward("/sys/lockrecord2.jsp");
		}			
	}
	/*
	 * Param Sheet
	 * return boolean
	 * 
	 */
		private boolean checkExcelType(Sheet st) {
			String ckStr=st.getCell(0, 0).getContents().trim();
			if("机构编号".equals(ckStr))
				return true;
			return false;
		}

}

