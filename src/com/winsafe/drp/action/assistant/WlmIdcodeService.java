package com.winsafe.drp.action.assistant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.owasp.esapi.ESAPI;

import com.winsafe.drp.dao.AppFleeProduct;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOrganWithdraw;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppTakeTicket;
import com.winsafe.drp.dao.AppTakeTicketIdcode;
import com.winsafe.drp.dao.AppWlmIdcodeLog;
import com.winsafe.drp.dao.FleeProduct;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OrganWithdraw;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.dao.TakeTicketIdcode;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.dao.WlmIdcodeLog;
import com.winsafe.drp.util.Constants;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.DbUtil;
import com.winsafe.hbm.util.StringUtil;
import com.winsafe.sap.dao.AppCartonCode;
import com.winsafe.sap.dao.AppPrimaryCode;
import com.winsafe.sap.pojo.CartonCode;
import com.winsafe.sap.pojo.PrimaryCode;
import com.winsafe.sap.pojo.PrintJob;
import common.Logger;

public class WlmIdcodeService {
	private static Logger logger = Logger.getLogger(WlmIdcodeService.class);
	private AppWlmIdcodeLog awll = new AppWlmIdcodeLog();
	private AppProduct ap = new AppProduct();
	private Map<String, Object> resultMap = new HashMap<String, Object>();
	private UsersBean users = new UsersBean();
	private PrimaryCode pc = null;
	private AppPrimaryCode apc = new AppPrimaryCode();
	private CartonCode cc = new CartonCode();
	private AppCartonCode acc = new AppCartonCode();
	private Product p = new Product();
	private AppTakeTicketIdcode attid = new AppTakeTicketIdcode();
	private WlmIdcodeLog wll = new WlmIdcodeLog();
	private AppTakeTicket att = new AppTakeTicket();
	private List<TakeTicketIdcode> lttid = new ArrayList<TakeTicketIdcode>();
	private List<TakeTicket> ltt = new ArrayList<TakeTicket>();
	private List<TakeTicket> lttall = new ArrayList<TakeTicket>();
	private FleeProduct fp = new FleeProduct();
	private AppOrgan ao = new AppOrgan();
	private AppIdcode apIdcode = new AppIdcode();
	private Idcode idcode = null;
	// 是否增加记录
	private boolean addRecord = false;

	// idCode用来保存查询码
	// 用来保存箱码
	private String existString = "";
	// 用来保存小码
	private String pcString = "";
	// 用来保存暗码
	private String covertString = "";
	// 用来保存原二维mpin
	private String mpinString = "";

	public WlmIdcodeService() {
	}

	public WlmIdcodeService(boolean addRecord) {
		this.addRecord = addRecord;
	}

	public Map<String, Object> execute(String idCode, UsersBean users) throws Exception {

		try {
			this.users = users;

			// 首先判断是否有输入
			// 判断字符串长度。 不满足条件，直接返回
			if (!StringUtil.isEmpty(idCode)) {
				// 将得到的码解析最终转换成箱码
				analyzingCode(idCode);
				// 根据箱码判断该箱码是否存在
				cc = acc.getByCartonCode(existString);
				if(cc == null) {
					idcode = apIdcode.getIdcodeByCode(existString);
					if(idcode != null) {
						cc = new CartonCode();
						cc.setCartonCode(existString);
					}
				}
				if (cc != null || pc != null) {
					// 返回箱码
					resultMap.put("existString", existString);
					// 返回小码
					resultMap.put("pcString", pcString);
					// 判断是否存在该码的物流信息
					// 小包装流向,和箱码流向合并
					lttid = attid.getTakeTicketIdcodeByIdCode(existString, pcString);
					
					// 显示码信息
					resultMap.put("cc", cc);
					resultMap.put("pc", pc);
					
					// 有无流向信息
					if (lttid.size() > 0) {
						// 显示产品信息
						Map<String, Object> productMap = ap.getProductInformation(cc, pc, idcode);
						if (productMap.get("product") != null) {
							p = (Product) productMap.get("product");
							resultMap.put("p", (Product) productMap.get("product"));
						}

						if (productMap.get("printJob") != null) {
							PrintJob pg = (PrintJob) productMap.get("printJob");
							resultMap.put("pg", (PrintJob) productMap.get("printJob"));
						}
						if(DbUtil.isDealer(users)) {
							ltt = att.getTakeTicketsById(existString, pcString, users.getUserid());
						} else {
							ltt = att.getTakeTicketsById(existString, pcString);
						}
						
						// 判断是否拥有查看此码的权限
						if (ltt.size() > 0) {
							//显示所有流向
//							ltt = att.getTakeTicketsByCode(existString, pcString);
							//显示流到查询机构之后的流向
//							ltt = deallogistics(ltt);
							lttall = ltt;
							// 获取发货日期和收货日期
							achieveDate(lttall);
							// 显示流向信息
							resultMap.put("lttall", lttall);
							// 跳转到显示界面模块
							resultMap.put("prompt", "r");
							resultMap.put("wlmessage", "logistics");
							if (!addRecord) {
								addWlmidcodeLog(wll, idCode, cc, p, fp, pc, Constants.WLM_QUERYSORT_III);
							}
						} else {
							// 提示没有权限

							// 在异地销售报表记录本次查询
							if (! addRecord) {
								logger.debug("在异地销售报表中增加一条记录");
								addFleeProducLog(existString, pcString, idCode, lttall, p);
							}
							resultMap.put("prompt", "r");
							resultMap.put("noRight", "noRight");
							if (!addRecord) {
								addWlmidcodeLog(wll, idCode, cc, p, fp, pc, Constants.WLM_QUERYSORT_II);
							}
						}
					} else {
						// 提示不存在该箱码,由于没有物流信息
						// 跳转到提示不存在该箱码界面模块
						Map<String, Object> productMap = ap.getProductInformation(cc, pc, idcode);
						if (productMap.get("product") != null) {
							p = (Product) productMap.get("product");
							resultMap.put("p", (Product) productMap.get("product"));
						}

						if (productMap.get("printJob") != null) {
							PrintJob pg = (PrintJob) productMap.get("printJob");
							resultMap.put("pg", (PrintJob) productMap.get("printJob"));
						}

						resultMap.put("prompt", "r");
						resultMap.put("noLogistic", "noLogistic");
						if (!addRecord) {
							addWlmidcodeLog(wll, idCode, cc, p, fp, pc, Constants.WLM_QUERYSORT_II);
						}
					}
				} else {
					// 不存在该查询码
					// 跳转到提示不存在该查询码界面模块
					resultMap.put("prompt", "nocode");
					if (!addRecord) {
						addWlmidcodeLog(wll, idCode, cc, p, fp, pc, Constants.WLM_QUERYSORT_I);
					}
				}

			} else {
				// 请输入要查询的查询码
				resultMap.put("prompt", "rightcode");
				if (!addRecord) {
					addWlmidcodeLog(wll, idCode, cc, p, fp, pc, Constants.WLM_QUERYSORT_I);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 判断码的位数是否复核要求
	 * 
	 * @author jason.huang
	 * @param idcode
	 * @return
	 */
	public boolean judgeCode(String idcode) {
		// 用来判断码位数是否正确
		boolean flag = false;
		switch (idcode.length()) {
		case Constants.CARTON_CODE_V_I:
			flag = true;
			break;
		case Constants.CARTON_CODE_V_II:
			flag = true;
			break;
		case Constants.PRIMARY_CODE_V_I:
			flag = true;
			break;
		case Constants.PRIMARY_CODE_V_II:
			flag = true;
			break;
		case Constants.PRIMARY_CODE_V_III:
			flag = true;
			break;
		case Constants.PRIMARY_CODE_V_IV:
			flag = true;
			break;
		case Constants.MPIN_CODE_V_II:
			flag = true;
			break;
		case Constants.COVERT_CODE_V_II:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		return flag;
	}

	public void analyzingCode(String idCode) {
		try {
			idCode = ESAPI.encoder().decodeForHTML(idCode);
			if(idCode.indexOf("/QR/") != -1) {
				idCode = idCode.substring(idCode.lastIndexOf("/") + 1);
				resultMap.put("wlmIdcode", idCode);
			} 
			switch (idCode.length()) {
			case Constants.CARTON_CODE_V_I:
				// 得到箱码
				existString = idCode;
				break;
			case Constants.CARTON_CODE_V_II:
				// 对二维码解析,得到箱码
				existString = idCode.substring(Constants.CARTON_BEGIN_INDEX);
				break;
			case Constants.PRIMARY_CODE_V_I:
				// 得到小码
				pcString = idCode;
				getCartonCodeByPrimaryCode();
				break;
			case Constants.PRIMARY_CODE_V_II:
				// 得到小码
				pcString = idCode; 
				getCartonCodeByPrimaryCode();
				break;
			case Constants.PRIMARY_CODE_III_II:
				// 得到小码
				pcString = idCode;
				getCartonCodeByPrimaryCode();
				break;
			case Constants.PRIMARY_CODE_V_III:
				// 对50位小包装二维码解析,得到小码
				pcString = idCode.substring(Constants.PRIMARY_BEGIN_INDEX);
				getCartonCodeByPrimaryCode();
				break;
			case Constants.PRIMARY_CODE_V_IV:
				// 对53位小包装二维码解析,得到小码
				pcString = idCode.substring(Constants.PRIMARY_BEGIN_INDEX);
				getCartonCodeByPrimaryCode();
				break;
			case Constants.MPIN_CODE_V_II:
				// 得到原mpin码
				mpinString = idCode;

				cc = acc.getByOutAndIn(mpinString);

				if (cc != null) {
					// 根据原mpin码得到箱码
					existString = cc.getCartonCode();
				}
				break;

			case Constants.COVERT_CODE_V_II:
				// 得到暗码
				covertString = idCode;
				getCartonCodeByCovertCode();
				break;
			case Constants.COVERT_CODE_X_I:
				// 得到暗码
				covertString = idCode;
				getCartonCodeByCovertCode();
				break;
			case Constants.PRIMARY_CODE_II_I:
				getCartonCodeByPrimarySuffix(idCode);
				break;
			default:
				existString = idCode;
				break;
			}
		} catch (Exception e) {
			logger.error("analyzingCode  error:", e);
		}
	}

	private void getCartonCodeByPrimarySuffix(String idCode) throws Exception {
		List<Map<String, String>> pcMapList = apc.getCartonCodeByVCode(idCode);
		if(pcMapList != null && pcMapList.size() > 0) {
			Map<String, String> pcMap = pcMapList.get(0);
			pc = new PrimaryCode();
			pc.setPrimaryCode(pcMap.get("primarycode"));
			pc.setCartonCode(pcMap.get("cartoncode"));
			pc.setCovertCode(pcMap.get("convertcode"));
			if(!StringUtil.isEmpty(pcMap.get("printjobid"))) {
				pc.setPrintJobId(Integer.valueOf(pcMap.get("printjobid")));
			}
			// 根据小码得到箱码
			pcString = pc.getPrimaryCode();
			existString = pc.getCartonCode();
			resultMap.put("pc", pc);
			resultMap.put("viewPc", "viewTrue");
			resultMap.put("primaryCode", pc.getPrimaryCode());
			resultMap.put("cartonCode", pc.getCartonCode());
			resultMap.put("covertCode", pc.getCovertCode());
		}
	}

	private void getCartonCodeByCovertCode() {
		// 根据暗码得到小码 
		pc = apc.getPrimaryCodeByCovertCode(covertString);
		// 判断该小码是否存在
		if (pc != null) {
			// 根据小码得到箱码
			existString = pc.getCartonCode();
			resultMap.put("pc", pc);
			resultMap.put("viewPc", "viewTrue");
			resultMap.put("primaryCode", pc.getPrimaryCode());
			resultMap.put("cartonCode", pc.getCartonCode());
			resultMap.put("covertCode", pc.getCovertCode());
		}
	}

	private void getCartonCodeByPrimaryCode() {
		pc = apc.getPrimaryCodeByPCode(pcString);
		// 判断该小码是否存在
		if (pc != null) {
			// 根据小码得到箱码
			existString = pc.getCartonCode();
			resultMap.put("pc", pc);
			resultMap.put("viewPc", "viewTrue");
			resultMap.put("primaryCode", pc.getPrimaryCode());
			resultMap.put("cartonCode", pc.getCartonCode());
			resultMap.put("covertCode", pc.getCovertCode());
		}
	}

	/**
	 * 获取发货日期和收货日期
	 * 
	 * @author jason.huang
	 * @param lttall
	 * @return
	 * @throws Exception
	 */
	public List<TakeTicket> achieveDate(List<TakeTicket> lttall) throws Exception {
		AppStockAlterMove asam = new AppStockAlterMove();
		AppOrganWithdraw aow = new AppOrganWithdraw();
		AppStockMove asm = new AppStockMove();

		logger.debug("获取发货日期和收货日期");
		for (TakeTicket takeTicket : lttall) {
			// 如果存在单据号
			if (!StringUtil.isEmpty(takeTicket.getBillno())) {
				// 根据单据号的类型获取收货日期和发货日期"
				String billtype = takeTicket.getBillno().substring(0, 2);
				if (billtype.equals("OM")) {
					// 从发货单中获取收货日期和发货日期
					StockAlterMove sam = asam.getStockAlterMoveByID(takeTicket.getBillno());
					if (sam != null) {
						takeTicket.setAuditdate(sam.getShipmentdate());
						takeTicket.setPickedDate(sam.getReceivedate());
					}

				} else if (billtype.equals("OW") || billtype.equals("PW")) {
					// 获取渠道退货或者工厂退回收货和发货日期
					OrganWithdraw ow = aow.getOrganWithdrawByID(takeTicket.getBillno());
					if (ow != null) {
						takeTicket.setAuditdate(ow.getAuditdate());
						takeTicket.setPickedDate(ow.getReceivedate());
					}

				} else if (billtype.equals("SM")) {
					// 从机构内转仓获取收货日期和发货日期
					StockMove sm = asm.getStockMoveByID(takeTicket.getBillno());
					if (sm != null) {
						takeTicket.setAuditdate(sm.getAuditdate());
						takeTicket.setPickedDate(sm.getReceivedate());
					}

				} else {
					logger.debug("单据号错误");
				}
			}
		}
		return lttall;
	}

	/**
	 * 增加码查询记录
	 * 
	 * @author jason.huang
	 * @param wll
	 * @param cartonCode
	 * @param cc
	 * @param p
	 * @param fp
	 * @param pc
	 * @throws Exception
	 */
	public void addWlmidcodeLog(WlmIdcodeLog wll, String cartonCode, CartonCode cc, Product p, FleeProduct fp, PrimaryCode pc, int querysort) throws Exception {
		// 先设置wlmidcodelog属性值，设置完成以后添加到wlmidcodelog表中
		if (!StringUtil.isEmpty(cartonCode)) {
			// 增加单据编号
			wll.setId(awll.getMaxWlmIdcodeLogId());
			// 增加制单人
			wll.setMakeid(users.getUserid());
			// 增加机构编码
			wll.setMakeorganid(users.getMakeorganid());
			// 增加查询码
			wll.setWlmidcode(cartonCode);
			// 当没权限查看时，通过另外一种途径获取产品信息
			if (p.getId() == null && fp.getProductid() != null) {
				p = ap.getProductByID(fp.getProductid());
			}
			// 增加产品编号
			wll.setProductid(p.getId());
			// 增加产品名
			wll.setProductname(p.getProductname());
			// 增加规格
			wll.setSpecmode(p.getSpecmode());
			// 增加箱码
			if (cc != null && cc.getCartonCode() != null) {
				wll.setCartoncode(cc.getCartonCode());
			} else if (pc != null && pc.getCartonCode() != null) {
				wll.setCartoncode(pc.getCartonCode());
			}
			// 增加日期
			wll.setMakedate(new Date());
			// 增加查询分类
			switch (querysort) {
			case Constants.WLM_QUERYSORT_I:
				wll.setQuerysort(Constants.WLM_QUERYSORT_I);
				break;
			case Constants.WLM_QUERYSORT_II:
				wll.setQuerysort(Constants.WLM_QUERYSORT_II);
				break;
			case Constants.WLM_QUERYSORT_III:
				wll.setQuerysort(Constants.WLM_QUERYSORT_III);
				break;
			}
			// 增加实际销售机构
			String pdString = getPd();
			wll.setOrganid(pdString);
			awll.addWlmIdcodeLog(wll);
		}
	}

	/**
	 * 增加异地销售记录
	 * 
	 * @author jason.huang
	 * @param existString
	 * @param pcString
	 * @param cartonCode
	 * @param f
	 * @param ltt
	 * @param p
	 */
	public void addFleeProducLog(String existString, String pcString, String cartonCode, List ltt, Product p) {

		try {
			TakeTicketIdcode ttid = new TakeTicketIdcode();
			Organ o = new Organ();
			Object object = new Object();
			Map mapObject = new HashMap();
			FleeProduct fp = new FleeProduct();
			fp.setMakedate(new Date());
			fp.setMakeid(users.getUserid());
			fp.setMakeorganid(users.getMakeorganid());
			fp.setQueryid(cartonCode);
			AppTakeTicketIdcode attid = new AppTakeTicketIdcode();
			AppTakeTicket att = new AppTakeTicket();
			AppFleeProduct afp = new AppFleeProduct();

			// 根据箱码找TT信息
			// ltt = att.getTakeTicketsInf(existString, pcString);
			//
			// object = ltt.get(0);
			// mapObject = (Map) object;
			// ttid =
			// attid.getTakeTicketIdcodeByttidAndidcode(mapObject.get("id").toString(),
			// existString);
			//
			// // 设置一级机构
			// logger.debug("设置一级机构");
			// fp.setFirstorgan(mapObject.get("oid").toString());
			// // 设置二级机构
			// if (mapObject.get("inoid") != null) {
			// o = ao.getOrganByID(mapObject.get("inoid").toString());
			// if (o != null) {
			// if (o.getRank() == Constants.ORGAN_THIRD_RANK) {
			// fp.setSecondorgan(mapObject.get("inoid").toString());
			// } else {
			// fp.setSecondorgan(mapObject.get("inoid").toString());
			// fp.setFirstorgan(mapObject.get("oid").toString());
			// }
			// }
			//
			// }
			Map<String, String> organMap = getFirstAndSecondOrgan();
			fp.setFirstorgan(organMap.get("firstOrgan").toString());
			fp.setSecondorgan(organMap.get("secondOrgan").toString());

			// 设置发送日期
			if (!StringUtil.isEmpty(organMap.get("auditDate"))) {
				
				fp.setDeliverdate(DateUtil.formatStrDate(organMap.get("auditDate").toString()));
			}

			// 设置产品信息
			if (p != null) {
				fp.setProductid(p.getId());
				fp.setProductname(p.getProductname());
				fp.setSpecmode(p.getSpecmode());
			}

			// 设置箱码
			if (!StringUtil.isEmpty(existString)) {
				fp.setCartoncode(existString);
			}
			fp.setIsdeal(Constants.YES);
			afp.addFleeProduct(fp);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	/**
	 * 得到实际销售的PD
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getPd() throws Exception {
		// 得到物流信息
		List<TakeTicket> wlList = new ArrayList<TakeTicket>();
		wlList = att.getTakeTicketsInf(existString, pcString);
		// 发机构
		Organ outOrgan = null;
		// 收机构
		Organ inOrgan = null;
		boolean outFlag = false;
		boolean inFlag = false;
		Map mapObject = new HashMap();
		// 保存PD经销商编号
		String pdString = "";
		for (int i = 0; i < wlList.size(); i++) {
			// 从后往前搜索记录
			Object object = wlList.get(i);
			mapObject = (Map) object;
			// 当发货机构或者收货机构为PD停止
			if (mapObject.get("oid") != null) {
				outOrgan = ao.getOrganByID(mapObject.get("oid").toString());
			}
			if (mapObject.get("inoid") != null) {
				inOrgan = ao.getOrganByID(mapObject.get("inoid").toString());
			}
			if ((inOrgan != null && inOrgan.getOrganType() == 2 && inOrgan.getOrganModel() == 1)) {
				inFlag = true;
				break;
			}
			if ((outOrgan != null && outOrgan.getOrganType() == 2 && outOrgan.getOrganModel() == 1)) {
				outFlag = true;
				break;
			}
		}
		if (inFlag) {
			pdString = mapObject.get("inoid").toString();
		} else if (outFlag) {
			pdString = mapObject.get("oid").toString();
		}
		return pdString;
	}

	/**
	 * 
	 * 获取一二级机构
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getFirstAndSecondOrgan() throws Exception {
		// 得到物流信息
		List<TakeTicket> wlList = new ArrayList<TakeTicket>();
		wlList = att.getTakeTicketsInf(existString, pcString);
		// 发机构
		Organ outOrgan = null;
		// 收机构
		Organ inOrgan = null;
		// 一级机构
		String firstOrgan = "";
		// 二级机构
		String secondOrgan = "";
		Map mapObject = new HashMap();
		// 保存一二级机构
		Map<String, String> organMap = new HashMap<String, String>();

		for (int i = wlList.size() - 1; i >= 0; i--) {
			// 从前往后搜索记录
			Object object = wlList.get(i);
			mapObject = (Map) object;
			// 当发货机构或者收货机构为工厂记一级机构，当发货机构或者收货机构为PD记二级机构
			if (mapObject.get("oid") != null) {
				outOrgan = ao.getOrganByID(mapObject.get("oid").toString());
			}
			if (mapObject.get("inoid") != null) {
				inOrgan = ao.getOrganByID(mapObject.get("inoid").toString());
			}
			if (outOrgan != null && outOrgan.getOrganType() == 1) {
				firstOrgan = outOrgan.getId();
			}
			if (inOrgan != null && inOrgan.getOrganType() == 1) {
				firstOrgan = inOrgan.getId();
			}
			if ((outOrgan != null && outOrgan.getOrganType() == 2 && outOrgan.getOrganModel() == 1)) {
				secondOrgan = outOrgan.getId();
			}
			if ((inOrgan != null && inOrgan.getOrganType() == 2 && inOrgan.getOrganModel() == 1)) {
				secondOrgan = inOrgan.getId();
			}
		}
		organMap.put("firstOrgan", firstOrgan);
		organMap.put("secondOrgan", secondOrgan);
		//获取发货日期
		if (mapObject.get("auditdate") == null) {
			organMap.put("auditDate","");
		}else {
			organMap.put("auditDate",mapObject.get("auditdate").toString());
		}
		return organMap;
	}
	
	/**
	 * 读取查询用户机构有关的流向，即只显示发往该用户所在机构记录以及该记录之后的记录
	 * @param ltt
	 * @return
	 */
	public List<TakeTicket> deallogistics(List<TakeTicket> ltt) {
		//从开始到结束查询查询机构第一次出现的流向。
		for (int i = ltt.size()-1; i>=0; i--) {
			//判断收货或者发货机构是否为自己管辖机构
			TakeTicket takeTicket = ltt.get(i);
			//判断收机构或者发机构是否为本查询机构
			if (takeTicket.getOid().equals(users.getMakeorganid())  || takeTicket.getInOid().equals(users.getMakeorganid())) {
				break;
			}else {
				ltt.remove(i);
			}
		}
		return ltt;
	}
}
