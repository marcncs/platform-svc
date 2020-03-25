package com.winsafe.drp.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import com.winsafe.hbm.util.StringUtil;
import org.apache.log4j.Logger;
import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.erp.dao.AppPrepareCode;
import com.winsafe.erp.dao.AppProductPlan;
import com.winsafe.erp.dao.AppUnitInfo;
import com.winsafe.erp.pojo.ProductPlan;
import com.winsafe.erp.pojo.UnitInfo;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.metadata.CartonSeqStatus;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.PrintJob;
 
public class TollerApproveListener {
	// 日志
	private static Logger logger = Logger.getLogger(TollerApproveListener.class);
	// 同步锁
	private static Object lock = new Object();
	private static boolean isRunning = false;

	private AppProductPlan abu = new AppProductPlan();
	private AppProduct appp = new AppProduct();
	private AppPrintJob appPrintJob = new AppPrintJob();
	private Product product  ;
	private Integer pjid  ;
	private AppUnitInfo aui = new AppUnitInfo();
	private PrintJob pj = new PrintJob();
	private ProductPlan plan;
	private TreeSet<CartonCode> cartonCodes = new TreeSet<CartonCode>();
	private String plantCode;
	private String plantName;
	private AppPrimaryCode appPrimaryCode = new AppPrimaryCode();
	
	public void run() {
		if (!isRunning) {
			synchronized (lock) {
				try {
					isRunning = true;
					// 入口
					autoTollerApprove();
				} catch (Exception e) {
					logger.error("", e);
				} finally {
					isRunning = false;
				}
			}
		}
	}
	public void autoTollerApprove() throws Exception {
		long id = 0 ;
		try {
			// logger.info("----已审批Toller工厂生产计划生成打印任务监听--开始---");
			String sql = "from ProductPlan where  (temp is null or  temp ='重新处理') and approvalFlag=1  order by PROdate asc";
			plan = (ProductPlan) EntityManager.findReadOnly(sql);
			if (plan != null) {
				// 更新处理状态
				id = plan.getId();
				AppOrgan apo = new AppOrgan();
				Organ o = apo.getOrganByID(plan.getOrganId());
				if(null == o || StringUtil.isEmpty(o.getOecode())){
					throw new Exception("查无工厂ID"+o.getId()+"对应的企业编号，请确认后重新处理!");
				}else{
					plantCode = o.getOecode();
					plantName = o.getOrganname();
				}
				pjid = Integer.parseInt(MakeCode.getExcIDByRandomTableName("print_job", 0, ""));
				abu.updIsDealSetPJId(id,"处理中",pjid);
				HibernateUtil.commitTransaction();
				String result = dealMakePrintJob(); //生成打印任务
				abu.updIsDealMsg(id,"已生成",result);
				HibernateUtil.commitTransaction();
			}
			// logger.info("----条码上传监听任务 --结束---");
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			abu.updIsDealMsg(id,"处理错误",e.toString());
			logger.error("条码上传监听任务 ", e);
			HibernateUtil.commitTransaction();
		} finally {
			HibernateUtil.closeSession();
		}
	}

	private String dealMakePrintJob() throws Exception {
		String returnString="";
		try {
			product = appp.getProductByID(plan.getProductId());
			//新增 printjob
			addPrintJob(plan);
			//根据 toller工厂ID  ，产品 获取托盘信息
			AppUnitInfo appUnitInfo = new AppUnitInfo();
			UnitInfo unitInfo = appUnitInfo.getUnitInfoByOidAndPid(plan.getOrganId(),plan.getProductId());
			if(unitInfo==null){
				throw new Exception("该工厂未配置产品的托盘信息，请配置后重新处理!");
			}
			
			long covertcode = 0l;
			//检查是否有重复暗码
			if(unitInfo.getNeedCovertCode() != null && unitInfo.getNeedCovertCode() == 1) {
				if(StringUtil.isEmpty(plan.getCodeFrom()) || StringUtil.isEmpty(plan.getCodeTo())) {
					throw new Exception("未设置暗码");
				} 
				if(appPrimaryCode.isCovertCodeAlreadyExists(plan.getCodeFrom(), plan.getCodeTo())) {
					throw new Exception("存在重复暗码");
				}
				covertcode = Long.valueOf(plan.getCodeFrom());
			}
			//托对应箱数量
			Integer t_b = unitInfo.getUnitCount();
			//余
			Integer yu = plan.getBoxnum()%t_b;
			//托数量
			Integer t = plan.getBoxnum()/t_b;
			if(yu.intValue()!=0){
				t = t+1;
			}
			//总码数
			Integer total  = plan.getBoxnum() +t;
			//获取本次需要的条码
			AppPrepareCode apppre = new AppPrepareCode();
//			String whereSql = " where rownum <= " +total+" and isuse=0 and tcode is null  and organid = '"+plan.getOrganId()+"'  order by code ";
//			List<PrepareCode>  listcode = apppre.getPrepareCodeByWhere(whereSql);
			String whereSql = " where isuse=0 and tcode is null  and organid = '"+plan.getOrganId()+"'  order by code ";
			List<Map>   listcode = apppre.getPrepareCodeByWhereOrder(whereSql,total);
			if(listcode.size()<total){
				throw new Exception("该工厂没有足够的SAP码数据，请导入SAP码数据后重新处理!");
			}
			//生成托箱码
			String tcode = "";//存放托码
			int pseq = 0;//打印顺序
			int cseq = 0;//托内顺序
			int coutin = t_b ;//托内箱数
			//用于更新 PrepareCode 表（箱托关系）
			Map<String,StringBuffer> tbmap = new HashMap<String,StringBuffer>();
			StringBuffer sb = new StringBuffer();
			//1托的码
			int tuo=t_b+1;
			
			Date currentDate = DateUtil.getCurrentDate();
			for(int i=0;i<listcode.size();i++){
				
				if((i+1)%(t_b+1)==0||(i+1)==total){//存放map 并清除buffer
					tbmap.put(tcode, sb);
					sb = new StringBuffer();
					continue;
				}
				
				if(i%(t_b+1)==0){
					tuo = i+coutin+1;
					//清零托内顺序
					cseq=0;
					//该情况获取托码
					if(tuo>=listcode.size()){
						tcode = (String)listcode.get(listcode.size()-1).get("code");
					}else{
						tcode =(String) listcode.get(tuo-1).get("code");
					}
					if(i/(t_b+1)>=t-1&&yu!=0){
						coutin = yu;
					}
				}
				{
					String code = (String)listcode.get(i).get("code");
					CartonCode cartonCode = new CartonCode();
					
					//打印顺序
					pseq =pseq+1;
					//托内顺序
					cseq = cseq +1;
					cartonCode.setPrintSeq(pseq);
					cartonCode.setCartonSeq(cseq);
					cartonCode.setCountInPallet(coutin);
					
					StringBuffer outPinCode = new StringBuffer();
					if(StringUtil.isEmpty(pj.getGTINNumber())) {
						outPinCode.append("01").append(pj.getMaterialCode());
					} else {
						outPinCode.append("01").append(pj.getGTINNumber());
					}
					outPinCode.append("10").append(pj.getBatchNumber());
					outPinCode.append("11").append(pj.getProductionDate());
					//outPinCode.append(pj.getCartonSSCC());
					outPinCode.append(code);
					
					cartonCode.setPrintJobId(pjid);
					cartonCode.setOutPinCode(outPinCode.toString().trim());
					cartonCode.setCartonCode(code.trim());
					cartonCode.setPalletCode(tcode.trim());
					cartonCode.setCreateDate(Dateutil.getCurrentDate());
					cartonCode.setMaterialCode(product.getmCode());
					cartonCode.setPrimaryCodeStatus(0);
					cartonCode.setCreateDate(Dateutil.getCurrentDate());
					cartonCode.setProductID(product.getId());
					cartonCodes.add(cartonCode);
					
					if(unitInfo.getNeedCovertCode() != null && unitInfo.getNeedCovertCode() == 1) {
						String covertCode = String.valueOf(covertcode++);
						PrimaryCode primaryCode = new PrimaryCode();
						primaryCode.setCartonCode(cartonCode.getCartonCode());
						primaryCode.setCodeLength(20);
						primaryCode.setCovertCode(Constants.ZERO_PREFIX[11-covertCode.length()]+covertCode);
						primaryCode.setCreateDate(currentDate);
						primaryCode.setIsUsed(1);
						primaryCode.setPrimaryCode(cartonCode.getCartonCode());
						primaryCode.setPrintJobId(cartonCode.getPrintJobId());
						appPrimaryCode.addPrimaryCode(primaryCode);
					}
					
					//用于更新 PrepareCode 表（箱托关系）
					sb.append(",'"+code+"'");

				}

			}
			AppCartonCode appCartonCode = new AppCartonCode();
			appCartonCode.addCartonCodes(cartonCodes);
			apppre.updatePrepareCode(tbmap,1,plan.getId());

		} catch (Exception e) {
			returnString = e.toString();
			throw e;
		}
		return returnString;
	}
	
	/**
	 * @param plan
	 * 新增 printjob
	 * @throws Exception
	 */
	private void addPrintJob(ProductPlan plan) throws Exception {
		//FUnit funit = materialWithFunit.get(product.getId());
		
		//获取标签类型
		UnitInfo  uinfo= aui.getUnitInfoByOidAndPid(plan.getOrganId(),plan.getProductId());
		if(uinfo!=null){
			pj.setLabelType(uinfo.getLabelType());
		}
		pj.setTransOrder("");
		pj.setGTINNumber("");
		pj.setProcessOrderNumber(plan.getPONO());
		pj.setProductionDate(Dateutil.formatDate(plan.getProDate(),"yyyyMMdd"));
		pj.setPackagingDate(Dateutil.formatDate(plan.getPackDate(),"yyyyMMdd"));
		pj.setBatchNumber(plan.getMbatch());
		if (product != null) {
			pj.setMaterialName(product.getProductname());
			pj.setProductId(product.getId());
			pj.setMaterialCode(product.getmCode());
			pj.setExpiryDate(GetExpiryDate(pj.getProductionDate(),product.getExpiryDays()));
			pj.setPackSize(product.getSpecmode());
		}
		pj.setIsDelete(YesOrNo.NO.getValue());
		pj.setPrintingStatus(0);//未打印
		pj.setPrintJobId(pjid);
		pj.setCreateUser(plan.getMakeId());
		pj.setPrimaryCodeStatus(-1);
		pj.setPlantCode(plantCode);
		pj.setPlantName(plantName);
		pj.setNumberOfCases(plan.getBoxnum());
		pj.setTotalNumber(plan.getBoxnum()*plan.getCopys());
		pj.setCreateDate(Dateutil.getCurrentDate()); 
		pj.setCartonSeqStatus(CartonSeqStatus.NO_DEED.getValue());
		
		String shipperSize = "";// 
		if((product.getBoxquantity() != null && product.getSunit()>0 && product.getBoxquantity() != 0d) ){
			AppFUnit appFUnit = new AppFUnit();
//			FUnit fUnit = appFUnit.getFUnit(product.getId(), product.getCountunit());
			Double xquantity = appFUnit.getQuantityByProductId(product.getId());
			if (xquantity!= null) {
				//计算
				Double getmu = product.getBoxquantity()*xquantity;
				BaseResource brByUnit = new AppBaseResource().getBaseResourceValue("CountUnit", product.getCountunit());
				shipperSize = getmu+brByUnit.getTagsubvalue();
			} 
		}
		pj.setShipperSize(shipperSize);
		
		if(!StringUtil.isEmpty(StringUtil.removeNull(plan.getPbatch()))) {
			String mbatch ="00"+ plan.getPbatch();
			pj.setMaterialBatchNo(mbatch.substring(mbatch.length()-2, mbatch.length()));
		} else {
			pj.setMaterialBatchNo("");
		}
		pj.setPrintingDate(DateUtil.getCurrentDate());
		preProcess(pj);
		appPrintJob.addPrintJob(pj);
		plan.setPrintJobId(pj.getPrintJobId());
		abu.updProductPlan(plan);
	}
	private void preProcess(PrintJob printJob) {
		//如果物料码超过8位，取最后八位
		String materialCode = printJob.getMaterialCode();
		if(!StringUtil.isEmpty(materialCode) && materialCode.length() > 8) {
			int length = materialCode.length();
			printJob.setMaterialCode(materialCode.substring(length - 8, length));
		}
		//如果GTIN码为空，则用物料码生成14位GTIN码（不包括前缀），位数不足用0补足
		if(StringUtil.isEmpty(printJob.getGTINNumber())) {
			printJob.setGTINNumber(Constants.ZERO_PREFIX[6] + printJob.getMaterialCode());
		}
		//如果批次不足10位，前面加0补足
		if(!StringUtil.isEmpty(printJob.getBatchNumber()) && printJob.getBatchNumber().length() < 10) {
			printJob.setBatchNumber(Constants.ZERO_PREFIX[10 - printJob.getBatchNumber().length()] + printJob.getBatchNumber());
		}
		
	}
	/**
	 * 根据生产日期和保质期得到过期日期
	 * @throws Exception
	 */
	private String GetExpiryDate(String proDate,Integer dats) {
		Date productionDate =  DateUtil.formatStrDate(proDate);
		if(dats != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(productionDate);
			calendar.add(Calendar.DATE, dats);
			Date expiryDate = calendar.getTime();
			return DateUtil.formatDate(expiryDate, "yyyyMMdd");
		}else{
			return "";
		}
	}
	
	public static void main(String[] args) {
		TollerApproveListener task = new TollerApproveListener();
		task.run(); 
	}

}
