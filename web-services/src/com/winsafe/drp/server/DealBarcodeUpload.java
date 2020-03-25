package com.winsafe.drp.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppBarcodeInventory;
import com.winsafe.drp.dao.AppBarcodeInventoryDetail;
import com.winsafe.drp.dao.AppBarcodeInventoryIdcode;
import com.winsafe.drp.dao.AppBarcodeUpload;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.BarcodeInventory;
import com.winsafe.drp.dao.BarcodeInventoryDetail;
import com.winsafe.drp.dao.BarcodeInventoryIdcode;
import com.winsafe.drp.dao.ErrorBean;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UploadBarcodeBean;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.UploadErrorMsg;
import com.winsafe.drp.util.UploadIdcodeUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.MakeCode;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrintJob;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.PrintJob;

/**
 * 条码上传处理
 * 
 * @Title: DealBarcodeUpload.java
 * @version:
 */
public class DealBarcodeUpload {

	private static Logger logger = Logger.getLogger(DealBarcodeUpload.class);

	private String filepath;
	private String fileaddress;
	private int iuid;
	private String failAddress;
	private String username;
	// 用于保存箱码
	private String ccString;

	private AppBarcodeUpload abu = new AppBarcodeUpload();
	private BarcodeUploadRuleService crs = new BarcodeUploadRuleService();
	private AppIdcode ai = new AppIdcode();
	private AppProduct ap = new AppProduct();
	private AppUsers au = new AppUsers();
	private AppCartonCode acc = new AppCartonCode();
	private AppPrintJob apj = new AppPrintJob();
	private AppIdcode aIdcode = new AppIdcode();
	private AppBarcodeInventory abi = new AppBarcodeInventory();
	private AppBarcodeInventoryDetail abid = new AppBarcodeInventoryDetail();
	private AppBarcodeInventoryIdcode abii = new AppBarcodeInventoryIdcode();
	private AppFUnit af = new AppFUnit();

	private OrganService os = new OrganService();
	private Product p = new Product();
	private PrintJob pj = new PrintJob();
	private Idcode idcode = new Idcode();
	// 存放idcode
	private Map mappMap = new HashMap();
	// 存放detail
	private Map mapMap = new HashMap();
	// 用于判断是否重复扫描
	private Map existMap = new HashMap();

	private int totalNum = 0;
	private int failNum = 0;
	private String makeorganid = null;
	private Integer userid = null;
	// 条码集合
	private Set<String> idcodes = new HashSet<String>();
	// 物流码前缀集合
	private Set<String> pids = new HashSet<String>();
	// 仓库集合
	private Set<String> whids = new HashSet<String>();

	/**
	 * 存放错误信息
	 */
	private Map<Integer, ErrorBean> errorMap = new TreeMap<Integer, ErrorBean>();

	public DealBarcodeUpload() {
	}

	public DealBarcodeUpload(String filepath, String fileaddress, int iuid, String username) {
		this.filepath = filepath;
		this.fileaddress = fileaddress;
		this.iuid = iuid;
		this.failAddress = fileaddress.substring(0, fileaddress.lastIndexOf(".")) + "_fail.txt";
		this.username = username;
	}

	public void deal() throws Exception {
		try {
			// 获取用户信息
			getUserInfo(this.username);
			UploadIdcodeUtil.getWVAuthority(userid);
			UploadIdcodeUtil.getRUWAuthority(userid);

			List<UploadBarcodeBean> idcodeBeanList = new ArrayList<UploadBarcodeBean>();
			// 处理上传的文件
			dealTxtFile(idcodeBeanList);
			// 缓存相关数据
			// cacheData();
			// 对上传后的所有条码进行分类整理
			// Map<String,Map<String,List<UploadIdcodeBean>>> resultMap =
			// sortUploadIdcodeBean(idcodeBeanList);
			// 处理分类后的map
			// dealResultMap(resultMap);
			// 封装错误信息
		} catch (Exception e) {
			generateErrorInfo(errorMap);
			abu.updNum(iuid, -1, 0, totalNum, failAddress);
			HibernateUtil.commitTransaction();
			throw e;
		}
		generateErrorInfo(errorMap);
	}

	/**
	 * 处理上传文件
	 * 
	 * @return
	 * @throws Exception 
	 */
	private Set<String> dealTxtFile(List<UploadBarcodeBean> idcodeBeanList) throws Exception {
		// 条码文件
		File file = new File(filepath + fileaddress);
		BufferedReader br = null;
		int errorLineNumber = 0;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
			String uploadidcode;
			errorLineNumber++;
			int lineNo = 1;
			// 通过循环得到该TXT文档的所有内容
			while ((uploadidcode = br.readLine()) != null) {
				// 首先去掉采集器上传的条码的bom
				uploadidcode = dealuploadIdcode(uploadidcode);
				// 获取条码上传对象
				JSONObject uploadJsonObj = JSONObject.fromString(uploadidcode);
				// 从json中获取对应的属性
				UploadBarcodeBean bean = crs.getNoBillSamUploadIdcode(uploadJsonObj);
				// 设置行号
				bean.setFileLineNo(lineNo++);
				// 扫描类型为D 代表已删除条码
				if ("D".equals(bean.getScanType())) {
					continue;
				}
				// 记录上传总数量
				totalNum++;
				// 获取盘点单
				BarcodeInventory bi = abi.getBarcodeInventoryByID(bean.getBillNo());
				// TODO:对码进行预处理
				idcode = pretreatIdcode(bean.getIdcode());
				// 条码判断
				if (bi.getIsaudit() == 1) {
					addErrorInfo(bean.getFileLineNo(), UploadErrorMsg.getError(
							UploadErrorMsg.E00307, bean.getIdcode()));
					continue;
				}
				if (idcode == null) {
					addErrorInfo(bean.getFileLineNo(), UploadErrorMsg.getError(
							UploadErrorMsg.E00302, bean.getIdcode()));
					continue;
				}
				if (StringUtil.isEmpty(idcode.getIdcode())) {
					addErrorInfo(bean.getFileLineNo(), UploadErrorMsg.getError(
							UploadErrorMsg.E00301, bean.getIdcode()));
					continue;
				}
				// 用于判断该单号中是否已经存在该条码的标志
				boolean codeExistFlag = abii.isExist(bean.getBillNo(), idcode.getIdcode());
				if (existMap.containsKey((bean.getBillNo() + idcode.getIdcode())) || codeExistFlag) {
					// 该单号存在该箱码说明重复扫描
					addErrorInfo(bean.getFileLineNo(), UploadErrorMsg.getError(
							UploadErrorMsg.E00303, bean.getIdcode()));
					continue;
				}
				if (pj == null || pj.getPrintJobId() == null) {
					addErrorInfo(bean.getFileLineNo(), UploadErrorMsg.getError(
							UploadErrorMsg.E00304, bean.getIdcode()));
					continue;
				}
				if (!idcode.getProductid().equals(pj.getProductId())) {
					addErrorInfo(bean.getFileLineNo(), UploadErrorMsg.getError(
							UploadErrorMsg.E00305, bean.getIdcode()));
					continue;
				}
				if (!idcode.getBatch().equals(pj.getBatchNumber())) {
					addErrorInfo(bean.getFileLineNo(), UploadErrorMsg.getError(
							UploadErrorMsg.E00306, bean.getIdcode()));
					continue;
				}
				Product product = ap.getProductByID(idcode.getProductid());
				if(product == null || product.getUseflag() == 0) {
					addErrorInfo(bean.getFileLineNo(), UploadErrorMsg.getError(
							UploadErrorMsg.E00308, bean.getIdcode(), product != null ? product.getProductname(): ""));
					continue;
				}

				if (Constants.SCAN_TYPE_BI.equals(bean.getScanType())) {
					dealBarcodeInventoryDetail(bean);
					// addBarcodeInventoryIdcode(bean);
					dealBarcodeInventoryIdcode(bean);
					// 保存有效条码
					idcodes.add(bean.getIdcode());
					// 保存入库仓库
					whids.add(bean.getInwarehouseid());
					// 保存出库仓库
					whids.add(bean.getOutwarehouseid());
					// 保存产品物流码前缀
					pids.add(bean.getLcode());
				}
				// 保存到beanList中
				idcodeBeanList.add(bean);
			}
			addBarcodeInventoryDetail(mapMap);
			addBarcodeInventoryIdcode(mappMap);
			return idcodes;
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			addErrorInfo(errorLineNumber, "文件处理失败,系统异常:"+e.getMessage());
			logger.error("", e);
			throw e;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
	}

	/**
	 * 处理barcode_inventory_detail
	 */
	private void dealBarcodeInventoryDetail(UploadBarcodeBean bean) {
		BarcodeInventoryDetail bid = new BarcodeInventoryDetail();
		// 根据条码查找产品
		if (idcode.getProductid() != null) {
			bid.setOsid(bean.getBillNo());
			bid.setProductid(idcode.getProductid());
			// 批次判断
			if (!StringUtil.isEmpty(bean.getBatch())) {
				bid.setBatch(bean.getBatch());
			} else if (!StringUtil.isEmpty(idcode.getBatch())) {
				bid.setBatch(idcode.getBatch());
			} else {
				bid.setBatch(Constants.NO_BATCH);
			}
			bid.setQuantity(Double.valueOf(bean.getQuantity()));
			if (mapMap.get(bid.getOsid() + "_" + bid.getProductid() + "_" + bid.getBatch()) != null) {
				mapMap.put(bid.getOsid() + "_" + bid.getProductid() + "_" + bid.getBatch(),
						Double.valueOf(mapMap
								.get(
										bid.getOsid() + "_" + bid.getProductid() + "_"
												+ bid.getBatch()).toString())
								+ bid.getQuantity());
			} else {
				mapMap.put(bid.getOsid() + "_" + bid.getProductid() + "_" + bid.getBatch(), bid
						.getQuantity());
			}
		}
	}

	/**
	 * 处理barcode_inventory_idcode
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	private void dealBarcodeInventoryIdcode(UploadBarcodeBean bean) throws NumberFormatException, Exception {
		BarcodeInventoryIdcode bii = new BarcodeInventoryIdcode();
		// 判断上传上来的条码是否在码库中
		bii.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("barcode_inventory_idcode",
				0, "")));
		bii.setOsid(bean.getBillNo());
		if (idcode.getProductid() != null) {
			bii.setProductid(idcode.getProductid());
		}
		// 批次判断
		if (!StringUtil.isEmpty(bean.getBatch())) {
			bii.setBatch_(bean.getBatch());
		} else if (!StringUtil.isEmpty(idcode.getBatch())) {
			bii.setBatch_(idcode.getBatch());
		} else {
			bii.setBatch_(Constants.NO_BATCH);
		}
		bii.setQuantity(Double.valueOf(bean.getQuantity()));
		bii.setIdcode(idcode.getIdcode());
		existMap.put(bii.getOsid() + bii.getIdcode(), "");
		if (mappMap.get(bii.getOsid() + "_" + bii.getProductid() + "_" + bii.getBatch_() + "_"
				+ bii.getIdcode()) != null) {
			mappMap.put(bii.getOsid() + "_" + bii.getProductid() + "_" + bii.getBatch_() + "_"
					+ bii.getIdcode(), Double.valueOf(mappMap.get(
					bii.getOsid() + "_" + bii.getProductid() + "_" + bii.getBatch_() + "_"
							+ bii.getIdcode()).toString())
					+ bii.getQuantity());
		} else {
			mappMap.put(bii.getOsid() + "_" + bii.getProductid() + "_" + bii.getBatch_() + "_"
					+ bii.getIdcode(), bii.getQuantity());
		}
	}

	/**
	 *保存 barcode_inventory_detail
	 * @throws Exception 
	 */
	private void addBarcodeInventoryDetail(Map mapbarcodedetail) throws Exception {
		Iterator itmap = mapbarcodedetail.entrySet().iterator();
		while (itmap.hasNext()) {

			BarcodeInventoryDetail bid = new BarcodeInventoryDetail();
			Map.Entry itnextMap = (Entry) itmap.next();
			String[] key = itnextMap.getKey().toString().split("_");
			String val = itnextMap.getValue().toString();
			bid.setId(Integer.valueOf(MakeCode.getExcIDByRandomTableName(
					"barcode_inventory_detail", 0, "")));
			bid.setOsid(key[0]);
			bid.setProductid(key[1]);
			p = ap.getProductByID(key[1]);
			bid.setProductname(p.getProductname());
			bid.setSpecmode(p.getSpecmode());
			bid.setUnitid(Constants.DEFAULT_UNIT_ID);
			bid.setBatch(key[2]);
			bid.setQuantity(Double.valueOf(val));
			abid.addBarcodeInventoryDetail(bid);
		}
	}

	/**
	 * 处理barcode_inventory_idcode
	 * @throws Exception 
	 */
	private void addBarcodeInventoryIdcode(Map mapbarcodeidcode) throws Exception {
		Iterator itmap = mapbarcodeidcode.entrySet().iterator();
		CartonCode abiiCartonCode = new CartonCode();
		PrintJob abiiPrintJob = new PrintJob();
		FUnit abbiFUnit = new FUnit();
		while (itmap.hasNext()) {
			Map.Entry itnextMap = (Entry) itmap.next();
			String[] key = itnextMap.getKey().toString().split("_");
			String val = itnextMap.getValue().toString();
			BarcodeInventoryIdcode bii = new BarcodeInventoryIdcode();
			bii.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"barcode_inventory_idcode", 0, "")));
			bii.setOsid(key[0]);
			bii.setProductid(key[1]);
			bii.setUnitid(Constants.DEFAULT_UNIT_ID);
			p = ap.getProductByID(key[1]);
			// 设置
			abiiCartonCode = acc.getByCartonCode(key[3]);
			if (abiiCartonCode != null) {
				abiiPrintJob = apj.getPrintJobByID(abiiCartonCode.getPrintJobId());
				if (abiiPrintJob != null) {
					abbiFUnit = af.getFUnit(abiiPrintJob.getProductId(), bii.getUnitid());
					if (abbiFUnit != null) {
						bii.setPackquantity(abbiFUnit.getXquantity());
					}
					bii.setProducedate(abiiPrintJob.getProductionDate());
				}
			}
			bii.setWarehousebit(Constants.WAREHOUSE_BIT_DEFAULT);
			bii.setIsidcode(1);

			bii.setBatch_(key[2]);
			bii.setIdcode(key[3]);
			bii.setQuantity(Double.valueOf(val));
			abii.addBarcodeInventoryIdcode(bii);
		}
	}

	/**
	 * 保存barcode_inventory_idcode
	 * @throws Exception 
	 */
	private void addBarcodeInventoryIdcode(UploadBarcodeBean bean) throws Exception {
		AppBarcodeInventoryIdcode abii = new AppBarcodeInventoryIdcode();
		BarcodeInventoryIdcode bii = new BarcodeInventoryIdcode();
		bii.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("barcode_inventory_idcode",
				0, "")));
		bii.setOsid(bean.getBillNo());
		bii.setProductid(idcode.getProductid());
		p = ap.getProductByID(idcode.getProductid());
		bii.setUnitid(Constants.DEFAULT_UNIT_ID);
		// 批次判断
		if (!StringUtil.isEmpty(bean.getBatch())) {
			bii.setBatch_(bean.getBatch());
		} else if (!StringUtil.isEmpty(idcode.getBatch())) {
			bii.setBatch_(idcode.getBatch());
		} else {
			bii.setBatch_(Constants.NO_BATCH);
		}
		bii.setQuantity(Double.valueOf(bean.getQuantity()));
		bii.setIdcode(bean.getIdcode());
		abii.addBarcodeInventoryIdcode(bii);
	}

	/**
	 * 封装错误信息
	 * 
	 * @param bean
	 * @param idcode
	 * @param msg
	 */
	private void addErrorInfo(Integer lineNo, String msg) {
		ErrorBean eb = new ErrorBean();
		eb.setInfo(msg);
		errorMap.put(lineNo, eb);
	}

	/**
	 * 封装错误信息
	 * 
	 * @param errorMap
	 */
	private ArrayList<String> generateErrorInfo(Map<Integer, ErrorBean> errorMap) {
		ArrayList<String> errorList = new ArrayList<String>();
		String destFile = filepath + failAddress;
		BufferedWriter out = null;
		try {
			if (!errorMap.isEmpty()) {
				out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(destFile, true),"utf-8"));

				for (Map.Entry<Integer, ErrorBean> entry : errorMap.entrySet()) {
					failNum++;

					String str = "第" + entry.getKey() + "行";
					ErrorBean bean = entry.getValue();
					if (bean.getIdcode() != null) {
						str += "  " + bean.getIdcode();
					}
					str += "[" + bean.getInfo() + "]";
					// 写入文件
					out.write(str);
					out.write("\r\n");
					// errorList.add(str);
				}
			}
			// errorList.add("成功 "+(totalNum-failNum)+" 条,失败"+failNum+"条");
			updLogNum();
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			if (out != null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
		return errorList;
	}

	/**
	 * 更新条码上传日志 成功失败数量
	 * 
	 * @author
	 * @CreateTime Jan 11, 2013 9:58:50 AM
	 */
	private void updLogNum() throws Exception {
		abu.updNum(iuid, 2, totalNum - failNum, failNum, failAddress);
	}

	/**
	 * 获取用户相关信息
	 * 
	 * @param username
	 * @throws Exception
	 * @author
	 * @CreateTime Jan 17, 2013 3:54:06 PM
	 */
	private void getUserInfo(String username) throws Exception {
		Users u = au.getUsers(username);
		if (u != null) {
			makeorganid = u.getMakeorganid();
			userid = u.getUserid();
			u.getMakedeptid();
			os.getOrganName(makeorganid);
		}
	}

	/**
	 * 判断并去掉采集器上传的条码的bom
	 */
	private String dealuploadIdcode(String uploadIdcode) {
		byte[] bom = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
		byte[] buffBytes;
		try {
			buffBytes = uploadIdcode.getBytes("utf-8");
			if (buffBytes[0] == bom[0] && buffBytes[1] == bom[1] && buffBytes[2] == bom[2]) {
				uploadIdcode = new String(buffBytes, 3, buffBytes.length - 3, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return uploadIdcode;
	}

	private Idcode pretreatIdcode(String idcode) throws Exception {
		// 判断码
		CartonCode cc = new CartonCode();
		if (idcode.length() == 20) {
			// 获得箱码
			this.ccString = idcode;

			// 根据箱码得到idcode
			this.idcode = aIdcode.getIdcodeByCode(idcode);
		} else if (idcode.length() == 24) {
			// 根据原mpin码得到箱码
			cc = acc.getByOutAndIn(idcode);
			if (cc != null) {
				// 获得箱码
				ccString = cc.getCartonCode();
				// 根据箱码得到idcode
				this.idcode = aIdcode.getIdcodeByCode(ccString);
			}
		} else if (idcode.length() == 58) {
			// 获得箱码
			ccString = idcode.substring(Constants.CARTON_BEGIN_INDEX);

			// 根据二维码得到箱码，再根据箱码得到idcode
			this.idcode = aIdcode.getIdcodeByCode(ccString);
		}

		// 根据箱码获取pj
		cc = acc.getByCartonCode(ccString);
		if (cc != null) {
			this.pj = apj.getPrintJobByID(cc.getPrintJobId());
		}

		// 对不在idcode表中的数据, (在cartoncode表中的数据),,需要在 IDcode表中插入一条记录
		if (this.idcode == null && cc != null) {
			Idcode addIdcode = new Idcode();
			FUnit fu = new FUnit();
			addIdcode.setIdcode(cc.getCartonCode());
			addIdcode.setProductid(cc.getProductID());
			if (pj != null) {
				addIdcode.setProductname(pj.getMaterialName());
				addIdcode.setBatch(pj.getBatchNumber());
				addIdcode.setProducedate(pj.getProductionDate());
				addIdcode.setUnitid(Constants.DEFAULT_UNIT_ID);
				addIdcode.setQuantity(1.0);
				fu = af.getFUnit(pj.getProductId(), addIdcode.getUnitid());
				if (fu != null) {
					addIdcode.setPackquantity(fu.getXquantity());
				}
			}
			addIdcode.setWarehousebit("000");
			addIdcode.setMakeorganid(makeorganid);
			addIdcode.setPcolumn(0);
			addIdcode.setCartonCode(cc.getCartonCode());
			// 按箱条码,对idcode表进行标志(标志当前仓库,并把NCLtono 记录盘库的单据号)
			ai.addIdcode2(addIdcode);
			return addIdcode;
		}
		return this.idcode;
	}

}
