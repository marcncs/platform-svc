package com.winsafe.drp.action.sales;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppCustomer;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppMemberGrade;
import com.winsafe.drp.dao.Customer;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.MakeConf;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.hbm.util.PYCode;

public class ImportMemberAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		int CCount =0,ECount=0;
		initdata(request);
		try {
			IdcodeUploadForm mf = (IdcodeUploadForm) form;
			AppMemberGrade amg = new AppMemberGrade(); 
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
				AppCustomer app = new AppCustomer();
				AppMakeConf appm = new AppMakeConf();
				MakeConf mc = appm.getMakeConfByID("customer");
				Customer c = null;
				for (int i = 1; i < row; i++) {

					c = new Customer();
					String cid = "";
					boolean isExist = true;
					if (mc.getRunmode() == 0) {
						cid = sheet.getCell(0, i).getContents();
						//判断编号是否存在
						Customer oldp = app.getCustomer(cid);
						if (oldp != null) {
							isExist = false;
						}
					} else {
						cid = MakeCode.getExcIDByRandomTableName("customer", 4,
								"");
					}
					
					c.setCid(cid);					
					c.setCname(sheet.getCell(1, i).getContents());
					c.setCpycode(PYCode.getSampCode(c.getCname()));
					c.setCardno(sheet.getCell(2, i).getContents());
					c.setParentid(sheet.getCell(3, i).getContents());
					c.setPwd("");	
					c.setMembersex(DataFormat.strToInt(sheet.getCell(4, i).getContents()));
					c.setMemberbirthday(DateUtil.StringToDate(sheet.getCell(5, i).getContents()));
					c.setMemberidcard(sheet.getCell(6, i).getContents());							
					c.setMembercompany(sheet.getCell(7, i).getContents());
					c.setMemberduty(sheet.getCell(8, i).getContents());
					c.setTickettitle(sheet.getCell(9, i).getContents());
					c.setPrompt(DataFormat.strToInt(sheet.getCell(10, i).getContents()));
					
					c.setCreditlock(Double.valueOf(sheet.getCell(11, i).getContents()));
					c.setRate(DataFormat.strToInt(sheet.getCell(12, i).getContents()));
					
					
					c.setPolicyid(amg.getMemberGradeByID(Integer.valueOf(c.getRate())).getPolicyid());
					c.setDiscount(Double.valueOf(sheet.getCell(13, i).getContents()));
					c.setTaxrate(Double.valueOf(sheet.getCell(14, i).getContents()));
					c.setSource(DataFormat.strToInt(sheet.getCell(15, i).getContents()));
					c.setTransportmode(0);
					c.setProvince(DataFormat.strToInt(sheet.getCell(16, i).getContents()));
					c.setCity(DataFormat.strToInt(sheet.getCell(17, i).getContents()));
					c.setAreas(DataFormat.strToInt(sheet.getCell(18, i).getContents()));					
					c.setCommaddr(sheet.getCell(19, i).getContents());
					c.setDetailaddr(sheet.getCell(20, i).getContents());
					
					c.setPostcode(sheet.getCell(21, i).getContents());
					c.setSendpostcode(sheet.getCell(22, i).getContents());
					c.setHomepage(sheet.getCell(23, i).getContents());
					c.setOfficetel(sheet.getCell(24, i).getContents());
					c.setMobile(sheet.getCell(25, i).getContents());
					
					//判断手机是否存在
					Customer oldp = app.getCustomerByMobile(c.getMobile());
					if (oldp != null) {
						isExist = false;
					}					
					c.setFax(sheet.getCell(26, i).getContents());
					c.setEmail(sheet.getCell(27, i).getContents());
					c.setTaxcode(sheet.getCell(28, i).getContents());
					c.setSpecializeid(0);
					c.setMakeorganid(users.getMakeorganid());
					c.setMakedeptid(users.getMakedeptid());
					c.setMakeid(users.getUserid());
					c.setMakedate(DateUtil.getCurrentDate());					
					c.setUpdid(0);
					c.setUpddate(null);
					c.setLastcontact(null);
					c.setNextcontact(null);
					c.setRemark(sheet.getCell(29, i).getContents());
					c.setIsactivate(DataFormat.strToInt(sheet.getCell(30, i).getContents()));
					c.setActivateid(0);
					c.setActivatedate(null);
					c.setIsreceivemsg(DataFormat.strToInt(sheet.getCell(31, i).getContents()));
					c.setIsdel(0);
					
					
					
					if (isExist) {
						app.addCustomer(c);
						CCount++;
					} else {
						//WriterTxt(request, p);
						ECount++;
					}
				}
				wb.close();

			} else {

				request.setAttribute("result", "上传文件失败，请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}

			request.setAttribute("result", "上传供应商成功,本次总共添加 :"+(CCount+ECount)+"条! 成功:"+CCount+"条! 失败："+ECount+"条!");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "上传文件失败，请重试");
			return new ActionForward("/sys/lockrecord2.jsp");

		}

	}

//	private void WriterTxt(HttpServletRequest request, Provider p) {
//		System.out.println("编号: " + p.getPid() + " 已经存在!");
//		String s = new String();
//		String s1 = new String();
//		try {
//			String path = request.getRealPath("/")+"error.txt";
//			File f = new File(path);
//			BufferedReader input = new BufferedReader(new FileReader(f));
//
//			while ((s = input.readLine()) != null) {
//				s1 += s + "\n";
//			}
//			System.out.println("文件内容：" + s1);
//			input.close();
//			s1 += "编号为："+p.getPid() +"  供应商名 为 ："+p.getPname()+" 已经存在\n" ;
//
//			BufferedWriter output = new BufferedWriter(new FileWriter(f));
//			output.write(s1);
//			output.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

}
