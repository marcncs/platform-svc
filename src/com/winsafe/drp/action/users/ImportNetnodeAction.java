package com.winsafe.drp.action.users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.winsafe.drp.dao.AppCountryArea;
import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganProduct;
import com.winsafe.drp.dao.AppOrganScan;
import com.winsafe.drp.dao.AppUserVisit;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.AppWarehouseVisit;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.UserVisit;
import com.winsafe.drp.dao.Warehouse;
import com.winsafe.drp.dao.WarehouseBit;
import com.winsafe.drp.dao.WarehouseVisit;
import com.winsafe.drp.server.OrganService;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.MakeCode;

public class ImportNetnodeAction extends Action{
	/**全部是数字*/
	private static final Pattern NUM_MOBILE = Pattern.compile("\\d+");
	
	/**数字和-*/
	private static final Pattern NUM_TEL = Pattern.compile("\\d+-\\d+");
	
	/**数字/-组合*/
	private static final Pattern NUM_MIDLINE_SOLIDUSLINE = Pattern.compile("\\d+-\\d+/\\d+|\\d+/\\d+-\\d+");
	
	/**机构*/
	private Organ o = null;
	
	/**联系人*/
	private Olinkman ol1 = null;
	
	/**是否有联系人信息*/
	private boolean initLinkManFlg = false;


	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int CCount =0,ECount=0;
		String resultMsg = "";
		StringBuffer errorMsg = new StringBuffer();
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
				OrganService app = new OrganService();
				AppOrganScan appos = new AppOrganScan();
				AppOrgan appo = new AppOrgan();
				AppMakeConf appm = new AppMakeConf();
				AppCountryArea appCountry = new AppCountryArea();
				AppOlinkMan appLinkMan = new AppOlinkMan();
				//MakeConf mc = appm.getMakeConfByID("organ");
				Map<String,Integer> provinceMap = appCountry.getCountryAreaMap(0);
				Map<String,Integer> cityMap = appCountry.getCountryAreaMap(1);
				
				for (int i = 1; i < row; i++) {
					try {
						//判断名称是否为空
						String oName = sheet.getCell(1, i).getContents();
						if(StringUtil.isEmpty(oName)){
							errorMsg.append("行号"+(i+1)+"："+"网点名称为空!");
							errorMsg.append("<br/>");
							ECount++;
							continue;
						}
						//地址
						String oaddr = sheet.getCell(6, i).getContents();
						if(StringUtil.isEmpty(oaddr)){
							errorMsg.append("行号"+(i+1)+"："+"地址为空!");
							errorMsg.append("<br/>");
							ECount++;
							continue;
						}
						//如果内部编号为空
						String oecode=sheet.getCell(0, i).getContents();
						
						if(StringUtil.isEmpty(oecode)){
							errorMsg.append("行号"+(i+1)+"："+"网点编号为空!");
							errorMsg.append("<br/>");
							ECount++;
							continue;
						}
						//如果机构已经存在，该条记录不做处理
						Organ tempOrgan=appo.getByOecode(oecode);
						if(tempOrgan!=null){
							errorMsg.append("行号"+(i+1)+"："+"网点信息已经存在!");
							errorMsg.append("<br/>");
							ECount++;
							continue;
						}
						
						String parentOecode = sheet.getCell(5, i).getContents();
						
						if ( null == parentOecode || "".equals(parentOecode) || "0".equals(parentOecode) ){
							errorMsg.append("行号"+(i+1)+"："+"经销商编号为空!");
							errorMsg.append("<br/>");
							ECount++;
							continue;
						}
						Organ parentOrgan = appo.getByOecode(parentOecode);
						if ( parentOrgan == null ){
							errorMsg.append("行号"+(i+1)+"："+"经销商编号不存在!");
							errorMsg.append("<br/>");
							ECount++;
							continue;
						}
						String sysid = app.getOcodeByParent(parentOrgan.getSysid());
						
						//机构实例
						o = new Organ();
						
						String id = "";
 
						id = MakeCode.getExcIDByRandomTableName("organ", 4, "");

						//编号
						o.setId(id);
						
						//系统编号
						o.setSysid(sysid);
						//大区
						String bigRegion = sheet.getCell(2, i).getContents();
						o.setBigRegion(bigRegion);
						//机构类型
						o.setOrganType(0); //0为网点
						
						//取得地区
						/*String areaName = sheet.getCell(3, i).getContents();
						CountryArea area = null; 
						if(null != areaName && !"".equals(areaName)){
							area = appCountry.getCountryAreaByName( areaName.trim() ); 
						} 
						if( null != area){
						    o.setProvince(area.getId());
						}else{
							o.setProvince(0);
						}*/
						//省
						String province = sheet.getCell(3, i).getContents();
						Integer provinceId = provinceMap.get(province);
						if(provinceId == null){ 
							provinceId = appCountry.getIdByName(province, 0);
						}
						o.setProvince(provinceId==null?0:provinceId);
						//城市
						String city = sheet.getCell(4, i).getContents();
						Integer cityId = provinceMap.get(city);
						if(cityId == null){
							cityId = appCountry.getIdByName(city,1);
						}
						o.setCity(cityId== null?0:cityId);
						
						o.setAreas(0);
						
						//内部编号
						o.setOecode(oecode);
						
						/*String organName = sheet.getCell(3, i).getContents();
						if(null != organName && !"".equals(organName)){
							Organ tempOrgan=appo.getOrganByOrganName(organName);
							if(tempOrgan!=null){
								errorMsg.append("行号"+(i+1)+"："+"机构信息已经存在!");
								errorMsg.append("<br/>");
								ECount++;
								continue;
							}
							//机构名称,仓库名称默认相同+默认仓库
							o.setOrganname(organName);
						}else{
							errorMsg.append("行号"+(i+1)+"："+"机构名称为空!");
							errorMsg.append("<br/>");
							ECount++;
							continue;
						}*/
						
						//联系人和电话有些有的需要手动拆分开,按*号分隔开
						//initAddNameTel( sheet.getCell(4, i).getContents() );
						//名称
						o.setOrganname(oName);

						
						o.setOaddr(oaddr);
						o.setRate(0);
						o.setPrompt(0);
						o.setPprompt(0);
						o.setIsrepeal(0);
 
						o.setParentid(parentOrgan.getId());
						o.setRank(parentOrgan.getRank() + 1);
						o.setRepealid(0);
						o.setLogo("");
						
						String olName = sheet.getCell(7, i).getContents();
						String olTel = sheet.getCell(8, i).getContents();
						//联系人不为空时,导入联系人及电话
						if(!StringUtil.isEmpty(olName) || !StringUtil.isEmpty(olTel)){
							initLinkManFlg = true;
						}
						//导入联系人
						if( initLinkManFlg ){
							//联系人实例
							ol1 = new Olinkman();
							
							ol1.setId(Integer.valueOf(MakeCode
											.getExcIDByRandomTableName("olinkman", 0, "")));
							//姓名
							ol1.setName(olName);
							//电话
							ol1.setOfficetel(olTel);
							//设置联系人性别(默认:男)TODO 模板中读取
							ol1.setSex(2);
							 
							//设置从属机构
							ol1.setCid(id);
							appLinkMan.addOlinkman(ol1);
						}
						
						//默认仓库
						Warehouse warehouse = new Warehouse();
						warehouse.setId(MakeCode
								.getExcIDByRandomTableName("warehouse", 1, ""));
						//仓库名默认与机构名相同 + "默认仓库"
						warehouse.setWarehousename( o.getOrganname() + "默认仓库" );
						//仓库位置
						warehouse.setWarehouseaddr( o.getOaddr() );
						//仓库联系人
						warehouse.setUsername( ol1.getName() );
						//仓库联系人id
						warehouse.setUserid( ol1.getId() );
						//相关的组织关联
						warehouse.setMakeorganid( o.getId() );
						//默认非自动签收仓库
						warehouse.setIsautoreceive( 0 );
						
						//设置可用
						warehouse.setUseflag(1);
						AppWarehouse appWarehouse = new AppWarehouse();
						//插入数据库
						appWarehouse.addWarehouse(warehouse);
						
						//默认仓库的库位
						WarehouseBit bit = new WarehouseBit();
						bit.setId(Integer.valueOf(MakeCode
								.getExcIDByRandomTableName("warehouse_bit", 1, "")));
						bit.setWid( warehouse.getId() );
						//默认库位000
						bit.setWbid("000");
						//插入数据库
						appWarehouse.addWarehouseBit(bit);
						
						// 添加用户许可的仓库
						AppWarehouseVisit awv = new AppWarehouseVisit();
						awv.delWarehouseVisitByWID(warehouse.getId());
						AppUserVisit auv = new AppUserVisit();
						List<UserVisit> uservisitList = new ArrayList<UserVisit>();
						if(null != parentOrgan){
							uservisitList = auv.getUserVisitByOrganList(parentOrgan.getId());
						}else{
							ECount++;
							continue;
						}
						if (null != uservisitList && uservisitList.size() > 0) {
							for (UserVisit user : uservisitList) {
								WarehouseVisit wv = new WarehouseVisit();
								wv.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName("warehouse_visit", 0, "")));
								wv.setWid(warehouse.getId());
								wv.setUserid(user.getUserid());
								awv.addWarehouseVisit(wv);
							}
						}
						// 给ALL用户添加所有上架产品
						/*AppOrganProduct aop = new AppOrganProduct();
						List<Product> productList = new AppProduct().getProductAll();
						for (Product product : productList) {
							OrganProduct op = new OrganProduct();
							op.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("organ_product", 0, "")));
							op.setOrganid(id);
							op.setProductid(product.getId());
							aop.addOrganProductNoExist(op);
						}*/
						//机构上架所有产品
						AppOrganProduct aop = new AppOrganProduct();
						aop.addAllProduct(o.getId());
						appo.AddOrgan(o);
						//appos.insertOrganScan(o.getId());
						CCount++;
						HibernateUtil.commitTransaction();
					} catch (Exception e) {
						ECount++;
						errorMsg.append("行号"+(i+1)+"："+"导入失败!");
						errorMsg.append("<br/>");
						HibernateUtil.rollbackTransaction();
						continue;
					}
				}
				wb.close();

			} else {

				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}

			resultMsg = "上传机构资料成功,本次总共添加 :"+(CCount+ECount)+"条! 成功:"+CCount+"条! 失败："+ECount+"条!";
			if (ECount > 0) {
				resultMsg = resultMsg
						+ "<br/>&nbsp;&nbsp;&nbsp;&nbsp;失败原因如下：<br/>"
						+ "<div >"
						+ 	errorMsg.toString()
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
	
	/**
	 * 根据提供的字符串信息,分解得到机构地址电话等信息
	 * Create Time 2011-10-8 下午12:43:08 
	 * @param o
	 *        机构对象
	 * @param target
	 *        等待分解的目标字符串
	 * @return
	 * @throws Exception
	 * @author zhangfuming
	 */
	private void initAddNameTel(String target) throws Exception{
		initLinkManFlg = false;
		
		//平湖市当湖街道城北粮管所*顾卫忠*0573-5023985/13857389771
		//金华市书香名邸5幢1单元C12*13957172830/0579-82343903*钱清芬
		//安徽省合肥市合白路九公里处光太物流中心大仓*侯桂芳*13355518500/0551-5607392/0551-5523806

		List<String> allInfoList = new ArrayList<String>(5);
		
		//现根据*分解
		String[] array1 = target.split("\\*");
		allInfoList.addAll( Arrays.asList( array1 ));
		
		String[] array2 = null;
		for(String str : array1){
			if( isNumMidlineSolidusline(str) ){
				array2 = str.split("/");
				allInfoList.remove(str);
				allInfoList.addAll(Arrays.asList( array2 ));
				break;
			}
		}
		//System.out.println( allInfoList );
		for(int i = 0 ; i < allInfoList.size() ; i++){
			if(null == allInfoList.get(i) || "".equals(allInfoList.get(i))){
				continue;
			}
			if(i == 0){
				o.setOaddr( allInfoList.get(i) );
			}else {
				//如果是手机号码,联系人手机
				if(this.isMobileNum( allInfoList.get(i) )){
					ol1.setMobile( allInfoList.get(i) );
					o.setOmobile( allInfoList.get(i) );
				}
				//如果是电话号码,即机构电话号码
				else if( this.isTelNum( allInfoList.get(i) )){
					o.setOtel( allInfoList.get(i) );
				}
				//否则就是用户名
				else {
					ol1.setName( allInfoList.get(i) );
					initLinkManFlg = true;
				}
			}
		}
	}
	
	/**
	 * 字符串是手机号码
	 * Create Time 2011-10-8 下午01:17:52 
	 * @param str
	 * @return
	 * @throws Exception
	 * @author zhangfuming
	 */
	private boolean isMobileNum(String str)throws Exception{
		Matcher m = NUM_MOBILE.matcher(str);
		return m.matches();
	}
	
	/**
	 * 字符串是电话号码(根据有字符串有-来判断)
	 * Create Time 2011-10-8 下午01:18:58 
	 * @param str
	 * @return
	 * @throws Exception
	 * @author zhangfuming
	 */
	private boolean isTelNum(String str)throws Exception{
		Matcher m = NUM_TEL.matcher(str);
		return m.matches();
	}
	
	/**
	 * 判断是电话号码的组合(手机和座机)
	 * Create Time 2011-10-8 下午01:38:40 
	 * @param str
	 * @return
	 * @throws Exception
	 * @author zhangfuming
	 */
	private static boolean isNumMidlineSolidusline(String str)throws Exception{
		Matcher m = NUM_MIDLINE_SOLIDUSLINE.matcher(str);
		return m.matches();
	}
	
	public static void main(String args[])throws Exception{
/*		System.out.println( isMobileNum("5345345345") );
		System.out.println( isMobileNum("534534-5345") );
		
		System.out.println( isTelNum("53453-45345") );
		System.out.println( isTelNum("5345345345") );
		
		System.out.println( isNumMidlineSolidusline("5345/345-345") );
		System.out.println( isNumMidlineSolidusline("5345345345") );*/
		
		//initAddNameTel("金华市书香名邸5幢1单元C12*13957172830/0579-82343903*钱清芬") ;
	}
}
