package com.winsafe.drp.action.purchase;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductStruct;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.ICode;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductStruct;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.PYCode;

public class ImportProductAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AppFUnit afu = new AppFUnit();
		AppProduct app = new AppProduct();
		AppProductStruct aps = new AppProductStruct();
		AppICode appcs = new AppICode();
		AppOrganProduct appOrganProduct = new AppOrganProduct();
		AppMakeConf appmc = new AppMakeConf();
		
		UsersBean users = UserManager.getUser(request);
		
		//保存报错信息
		StringBuffer errMsg = new StringBuffer();
		int CCount =0,SCount=0;
		try {
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			FormFile idcodefile = (FormFile) mf.getIdcodefile();
			boolean bool = false;
			if (idcodefile != null && !idcodefile.equals("")) {
				if (idcodefile.getContentType() != null) {
					if (idcodefile.getFileName().indexOf("xls") >= 0) {
						bool = true;
					}
				}
			}
			if (bool) {
				Workbook wb = Workbook.getWorkbook(idcodefile.getInputStream());
				Sheet sheet = wb.getSheet(0);
				int row = sheet.getRows();
				
				Product p = null;
				for (int i = 1; i < row; i++) {
					SCount ++;
					try {
						//产品编号
						String proNCCode = sheet.getCell(0, i).getContents()==null ? null : sheet.getCell(0, i).getContents().trim();
						//判断产品编号是否为空
						if(StringUtil.isEmpty(proNCCode)){
							errMsg.append("第[" + (i+1) + "]行产品编号不能为空<br />");
							continue;
						}
						//产品品名
						String proName = sheet.getCell(1, i).getContents()==null ? null : sheet.getCell(1, i).getContents().trim();
						//判断产品品名是否为空
						if(StringUtil.isEmpty(proName)){
							errMsg.append("第[" + (i+1) + "]行产品品名不能为空<br />");
							continue;
						}
						//产品规格
						String proSpecmode = sheet.getCell(2, i).getContents()==null ? null : sheet.getCell(2, i).getContents().trim();
						//判断产品规格是否为空
						if(StringUtil.isEmpty(proSpecmode)){
							errMsg.append("第[" + (i+1) + "]行产品规格不能为空<br />");
							continue;
						}
						//产品中盒规格
						String proBoxSpecmode = sheet.getCell(3, i).getContents()==null ? null : sheet.getCell(3, i).getContents().trim();
						//判断产品中盒规格是否为空
						if(StringUtil.isEmpty(proNCCode)){
							errMsg.append("第[" + (i+1) + "]行产品中盒规格不能为空<br />");
							continue;
						}
						//产品类别
						String proSort = sheet.getCell(4, i).getContents()==null ? null : sheet.getCell(4, i).getContents().trim();
						//判断产品类别是否为空
						if(StringUtil.isEmpty(proSort)){
							errMsg.append("第[" + (i+1) + "]行产品类别不能为空<br />");
							continue;
						}
						//产品物流码前缀
						String proICode = sheet.getCell(5, i).getContents()==null ? null : sheet.getCell(5, i).getContents().trim();
						//判断产品物流码前缀是否为空
						if(StringUtil.isEmpty(proICode)){
							errMsg.append("第[" + (i+1) + "]行产品物流码前缀不能为空<br />");
							continue;
						}
						
						//判断产品编号是否存在
						Product productByNcode = app.findProductByNccode(proNCCode);
						if(productByNcode!=null){
							errMsg.append("第[" + (i+1) + "]行产品编号已经存在<br />");
							continue;
						}
						
						//判断产品品名是否存在
						Product productByName = app.findProductByproducrname(proName);
						if(productByName!=null){
							errMsg.append("第[" + (i+1) + "]行产品品名已经存在<br />");
							continue;
						}
						HibernateUtil.currentSession();
						p = new Product();
						p.setId(MakeCode.getExcIDByRandomTableName("product", 4, ""));
						p.setProductname(proName);
						p.setProductnameen(proName);
						p.setPycode(PYCode.getSampCode(p.getProductname()));
						ProductStruct ps = aps.getProductStructBySortName(proSort);
						if(ps != null){
							p.setPsid(ps.getStructcode());
						}else{
							p.setPsid("101");
						}
						p.setSpecmode(proSpecmode);
						p.setCountunit(1);
						p.setSunit(1);
						p.setWise(0);
						p.setIsidcode(1);
						p.setIsbatch(0);
						p.setUseflag(1);
						p.setIsunify(0);
						p.setMakeorganid(users.getMakeorganid());
						p.setMakedeptid(users.getMakedeptid());
						p.setMakeid(users.getUserid());
						p.setMakedate(DateUtil.getCurrentDate());
						p.setCost(0.00);
						p.setAbcsort(0);
						p.setNccode(proNCCode);
						p.setBoxquantity(DataFormat.strToDouble(proSpecmode));
						
						p.setScatterunitid(p.getCountunit());
						app.addProduct(p);
							
						//新增单位换算
						FUnit fu = new FUnit();
						fu.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("f_unit",0,"")));
						fu.setFunitid(p.getCountunit());
						fu.setProductid(p.getId());
						fu.setXquantity(1d);
						fu.setIsmain(1);
						afu.addFUnit(fu);
						
						fu = new FUnit();
						fu.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("f_unit",0,"")));
						fu.setFunitid(2);
						fu.setProductid(p.getId());
						fu.setXquantity(DataFormat.strToDouble(proSpecmode));
						fu.setIsmain(0);
						afu.addFUnit(fu);
						
						fu = new FUnit();
						fu.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("f_unit",0,"")));
						fu.setFunitid(4);
						fu.setProductid(p.getId());
						fu.setXquantity(DataFormat.strToDouble(proBoxSpecmode));
						fu.setIsmain(0);
						afu.addFUnit(fu);
						
						
						//新增物流码前缀
						ICode cu = new ICode();
						cu.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("i_code",0, "")));
						cu.setProductid(p.getId());
						cu.setLcode(proICode);
						appcs.addICode(cu);
						
						//添加上架机构
						appOrganProduct.addAllOrgan(p.getId());
						//更新make_conf
						appmc.updMakeConf("organ_product","organ_product");
						CCount++;
						//每行数据手动提交事务,防止事务事件过长
						HibernateUtil.commitTransaction();
					} catch (Exception e) {
						errMsg.append("第[" + (i+1) + "]行导入失败<br />");
						e.printStackTrace();
						HibernateUtil.rollbackTransaction();
					}
				}
				wb.close();
			} else {
				HibernateUtil.rollbackTransaction();
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			String resultMsg = "上传产品资料成功,本次总共添加 :"+(SCount)+"条! 成功:"+CCount+"条! 失败："+(SCount-CCount)+"条!";
			if (SCount-CCount > 0) {
				resultMsg = resultMsg
						+ "<br/>&nbsp;&nbsp;&nbsp;&nbsp;失败原因如下：<br/>"
						+ "<div >"
						+ 	errMsg.toString()
						+ "</div>";
			}
			
			request.setAttribute("result", resultMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			System.out.println("异常出现在第 " + CCount + "行");
			return new ActionForward("/sys/lockrecord2.jsp");

		}

	}
}
