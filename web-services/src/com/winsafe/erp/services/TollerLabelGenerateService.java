package com.winsafe.erp.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;  

import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.PlantConfig; 
import com.winsafe.erp.dao.AppCartonSeq;
import com.winsafe.erp.metadata.CartonSeqStatus;
import com.winsafe.getNumber.GetFWNumber;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.pojo.ApplyQrCode;
import com.winsafe.sap.pojo.CartonSeq;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.util.FileUploadUtil;

public class TollerLabelGenerateService {
	
	private AppProduct appProduct = new AppProduct();
	private AppCartonSeq appCartonSeq = new AppCartonSeq();
	private AppFUnit appFunit = new AppFUnit();
	private ApplyQrCode aqc;
	private String prefix;
	//追溯网址
	private String traceUrl = "";
	//箱序号长度
	private final int cartonSeqLength = 9;
	//随机数长度
	private final int randomLength = 8;
	//小码序号长度
	private final int primaryCodeSeqLength = 3;

	public TollerLabelGenerateService(ApplyQrCode aqc) {
		this.aqc = aqc;
	}
	
	private void init(Product product) throws Exception {
		//追溯网址
		traceUrl = PlantConfig.getConfig().getProperty("traceUrl")+"/";
		//条码前缀:登记类别+农药登记证后6位+生产类型+产品规格码+内部生产类型
		prefix = product.getRegCertType()+product.getRegCertCodeFixed()+product.getProduceType()+product.getSpecCode()+product.getInnerProduceType();
		
	}

	public void generateQrCode() throws Exception {
		Date currentDate = DateUtil.getCurrentDate();
		//产品信息
		Product product = appProduct.getProductByID(aqc.getProductId());
		//初始化
		init(product);
		//包装比例
		double packingRatio = appFunit.getXQuantity(product.getId(), Constants.DEFAULT_UNIT_ID);
		//获取箱序号
		Long cSeqLong = getNextCartonSeq(product.getId());
		//箱数量
		int totalQuantity = aqc.getQuantity()+aqc.getQuantity()*aqc.getRedundance()/100;
		//创建码文件
		File codeFile = createCodeFile(product);
		
		OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(codeFile), "UTF-8");
		BufferedWriter bw = new BufferedWriter(fw);
		try {
			while(totalQuantity > 0) {
				List<Object> codeList = new ArrayList<Object>();
				//生成箱序号
				CartonSeq cartonSeq = new CartonSeq();
				cartonSeq.setApplyId(aqc.getId().intValue());
				cartonSeq.setMakeDate(currentDate);
				cartonSeq.setProductId(product.getId());
				cartonSeq.setSeq(Constants.ZERO_PREFIX[cartonSeqLength-cSeqLong.toString().length()]+cSeqLong);
				cartonSeq.setStatus(CartonSeqStatus.NOT_USED.getValue());
				codeList.add(cartonSeq);
				for(int i=1;i<=packingRatio;) {
					PrimaryCode primaryCode = new PrimaryCode();
					primaryCode.setCodeLength(Constants.PRIMARY_CODE_III_II);
					primaryCode.setCreateDate(currentDate);
					primaryCode.setIsUsed(YesOrNo.NO.getValue());
					primaryCode.setSeq(Constants.ZERO_PREFIX[primaryCodeSeqLength-String.valueOf(i).length()]+i);
					primaryCode.setPrimaryCode(prefix+cartonSeq.getSeq()+primaryCode.getSeq()+GetFWNumber.getFWNumber(randomLength));
					primaryCode.setCartonCode(cartonSeq.getProductId()+cartonSeq.getSeq());
					codeList.add(primaryCode);
					i++;
				}
				//写入数据库
				appCartonSeq.addBatch(codeList);
				//写入文件
				for(Object obj : codeList) {
					if(obj instanceof PrimaryCode) {
						PrimaryCode primaryCode = (PrimaryCode)obj;
						StringBuffer sb = new StringBuffer();
						//产品名称 登记证持有人信息 追溯网址 单元识别码
						sb.append(traceUrl).append(primaryCode.getPrimaryCode()).append(",");
						sb.append(cartonSeq.getSeq()).append(",");
						sb.append(primaryCode.getSeq());
						//若需要暗码,则生成5列
						if(aqc.getNeedCovertCode() != null
								&& aqc.getNeedCovertCode() == YesOrNo.YES.getValue()) {
							sb.append(",").append(cartonSeq.getSeq()).append(",");
							sb.append(primaryCode.getSeq());
						}
						sb.append("\r\n");
						bw.write(sb.toString());
					}
				}
				cSeqLong++;
				totalQuantity--;
			}
		} finally {
			bw.close();
		}
	}

	private Long getNextCartonSeq(String productId) throws Exception {
		String cSeq = appCartonSeq.getMaxSeqByProductId(productId);
		Long cSeqLong = 1l;
		if(!StringUtil.isEmpty(cSeq)) {
			cSeqLong = Long.valueOf(cSeq);
			cSeqLong++;
		}
		return cSeqLong;
	}

	private File createCodeFile(Product product) throws Exception {
		String processOrder = "";
		if(aqc!=null && !StringUtil.isEmpty(aqc.getPono())) {
			processOrder = aqc.getPono();
		}
		String filePath = FileUploadUtil.getPrimayCodeFilePath()
			+ DateUtil.formatDate(DateUtil.getCurrentDate(), "yyyyMM")
			+ "/";
		String fileName = aqc.getId().toString()
			+ "_" + product.getProductname()+" "
			+ product.getSpecmode()+"_"
			+ "_" + product.getProductnameen()+" "
			+ product.getPackSizeNameEn()+"-"+ processOrder +".txt";
		aqc.setFilePath(filePath + fileName);
		return FileUploadUtil.createEmptyFile(filePath, fileName);
	}
}
