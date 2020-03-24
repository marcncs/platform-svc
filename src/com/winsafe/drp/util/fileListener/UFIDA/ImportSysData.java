package com.winsafe.drp.util.fileListener.UFIDA;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.winsafe.drp.dao.AppOlinkMan;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppOtherIncome;
import com.winsafe.drp.dao.AppOtherIncomeDetail;
import com.winsafe.drp.dao.AppPlinkman;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.AppPurchaseIncome;
import com.winsafe.drp.dao.AppPurchaseIncomeDetail;
import com.winsafe.drp.dao.AppStockAlterMove;
import com.winsafe.drp.dao.AppStockAlterMoveDetail;
import com.winsafe.drp.dao.AppStockMove;
import com.winsafe.drp.dao.AppStockMoveDetail;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.AppWarehouse;
import com.winsafe.drp.dao.Olinkman;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.OtherIncome;
import com.winsafe.drp.dao.OtherIncomeDetail;
import com.winsafe.drp.dao.Plinkman;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.ProductIncomeDetail;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.PurchaseIncome;
import com.winsafe.drp.dao.PurchaseIncomeDetail;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockAlterMoveDetail;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.StockMoveDetail;
import com.winsafe.drp.dao.Users;
import com.winsafe.drp.dao.Warehouse;

import com.winsafe.drp.util.Dateutil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.MakeCode;

public class ImportSysData {

	private static String fileName;
	

	// 日志
	private static final Logger log = Logger.getLogger(ImportSysData.class);

	// 采购入库 45
	public static String TYPE_IN_STOCK_UP = ResourceBundle.getBundle(
			"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("stock_up_inbound");
	// 产成品入库 46
	public static String TYPE_IN_PRODUCT = ResourceBundle.getBundle(
			"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("product_inbound");
	// 委外加工入库 47
	public static String TYPE_IN_OUTSOURCED = ResourceBundle.getBundle(
			"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("outsourced_inbound");
	// 其它入库 4A
	public static String TYPE_IN_OTHER = ResourceBundle.getBundle(
			"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("other_inbound");
	// 借入 49
	public static String TYPE_IN_BORROW = ResourceBundle.getBundle(
			"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("borrow_inbound");
	// 调拨入库 4E
	public static String TYPE_IN_TRANSFER = ResourceBundle.getBundle(
			"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("transfer_inbound");
	// 订购出库 4C
	public static String TYPE_OUT_SALE = ResourceBundle.getBundle(
			"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("sale_outbound");
	// 其它出库 4I
	public static String TYPE_OUT_OTHER = ResourceBundle.getBundle(
			"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("other_outbound");
	// 借出 4H
	public static String TYPE_OUT_BORROW = ResourceBundle.getBundle(
			"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("borrow_outbound");
	// 调拨出库 4Y
	public static String TYPE_OUT_TRANSFER = ResourceBundle.getBundle(
			"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
			.getString("transfer_outbound");

	public static void importData(Document document, String fileName) {
		HibernateUtil.currentTransaction();
		setFileName(fileName);
		// creatResponseXml("aaaaa","bbbbb","ccccc");
		// 获取文档的根元素，赋值给rootElement变量
		Element rootElement = document.getDocumentElement();
		// 得到单据类型
		String billType = rootElement.getAttribute("billtype");
		// 采购入库操作
		if (billType.equals(TYPE_IN_STOCK_UP)) {
			stockUpInbound(document);

		}
		if (billType.equals(TYPE_IN_PRODUCT)
				|| billType.equals(TYPE_IN_OUTSOURCED)) {
			productInbound(document, billType);
		}
		if (billType.equals(TYPE_IN_BORROW) || billType.equals(TYPE_IN_OTHER)
				|| billType.equals(TYPE_OUT_BORROW)
				|| billType.equals(TYPE_OUT_OTHER)) {
			// i用来标注是入库还是出库，0为入库、1为出库
			int i = 1;
			if (billType.equals(TYPE_IN_BORROW)
					|| billType.equals(TYPE_IN_OTHER)) {
				i = 0;
			}
			otherInbound(document, i);
		}
		if (billType.equals(TYPE_IN_TRANSFER)
				|| billType.equals(TYPE_OUT_TRANSFER)) {
			// i用来标注是入库还是出库，0为入库、1为出库
			int i = 1;
			if (billType.equals(TYPE_IN_TRANSFER)) {
				i = 0;
			}
			transferInbound(document, i);
		}
		if (billType.equals(TYPE_OUT_SALE)) {
			saleOutbound(document);
		}
		HibernateUtil.commitTransaction();
	}

	private static void initPurchaseIncome(Document document) {
		// 首先判断唯一性
		// 测试
		printNode(document, 0);
		Element rootElement = document.getDocumentElement();
		NodeList nodeList = rootElement.getChildNodes();
		PurchaseIncome purchaseIncome = new PurchaseIncome();
		PurchaseIncomeDetail purchaseIncomeDetail = new PurchaseIncomeDetail();
		purchaseIncomeDetail.setPiid(purchaseIncome.getId());

	}

	// 采购入库

	/**
	 * 采购入库
	 * 
	 * @param doucment用友处给出的XML
	 */
	public static void stockUpInbound(Document doucment) {
		log.info("------采购入库操作开始------");
		// 用友xml参数含义(目前未解析，暂用NULL)
		// 仓库id
		String cwarehouseid = null;
		// 出入库单表头id
		String cgeneralhid = null;
		// 供应商ID
		String cproviderid = null;
		// 制单人
		String psnname = null;
		// 制单时间
		String tmaketime = null;
		// 存货id
		String cinvbasid = null;
		// 计量单位
		String measdocname = null;
		// 实入数量
		String ninnum = null;
		// 单据状态
		String fbillflag = null;
		// 单据方式（新增、修改、删除）
		String opt_type = null;

		// 取到根节点
		Element rootElement = doucment.getDocumentElement();
		NodeList nodeList = rootElement.getChildNodes();

		// 取到ic_bill节点及取下级所有节点
		Node nodeBill = nodeList.item(1);
		NodeList nodeList2 = nodeBill.getChildNodes();

		// 取到head节点及取下级所有节点
		Node nodeHead = nodeList2.item(1);
		NodeList nodeList3 = nodeHead.getChildNodes();

		// 取到body节点及所有下级节点
		Node nodeBody = nodeList2.item(3);
		NodeList nodeList4 = nodeBody.getChildNodes();

		// 寻找出所需的节点并将内容赋值给需要的变量(Head内)
		// 寻找到单据操作方式
		opt_type = getTheNodeValue(nodeList3, "opt_type");
		// 寻找到出入库单表头id节点
		cgeneralhid = getTheNodeValue(nodeList3, "cgeneralhid");
		// 寻找到入货仓库ID节点
		cwarehouseid = getTheNodeValue(nodeList3, "cwarehouseid");
		// 寻找到制单时间的节点
		tmaketime = getTheNodeValue(nodeList3, "tmaketime");
		// 寻找制单人节点
		psnname = getTheNodeValue(nodeList3, "psnname");
		// 寻找制单时间节点
		tmaketime = getTheNodeValue(nodeList3, "tmaketime");
		// 寻找供应商ID的节点
		cproviderid = getTheNodeValue(nodeList3, "cproviderid");
		// 生成DAO类
		AppPurchaseIncome appPurchaseIncome = new AppPurchaseIncome();
		AppPurchaseIncomeDetail appPurchaseIncomeDetail = new AppPurchaseIncomeDetail();
		AppWarehouse appWareHouse = new AppWarehouse();
		AppUsers appUsers = new AppUsers();
		AppOrgan appOrgan = new AppOrgan();
		AppPlinkman appPlinkman = new AppPlinkman();
		AppProvider appProvider = new AppProvider();

		Warehouse warehouse1 = new Warehouse();
		Users tempuser = new Users();
		Organ temporgan1 = new Organ();
		Plinkman plinkman = new Plinkman();
		Provider provider = new Provider();

		// 用于保存XML属性内容的封装
		ResXmlBean resXmlBean = null;
		try {
			tempuser = appUsers.findByNCcode(psnname);
			warehouse1 = appWareHouse.findByNCcode(cwarehouseid, tempuser
					.getUserid());
			temporgan1 = appOrgan.getOrganByID(warehouse1.getMakeorganid());
			provider = appProvider.getProviderByNCcode(cproviderid, tempuser
					.getUserid());
			plinkman = appPlinkman.getPLinkmanByNCcodde(cproviderid, tempuser
					.getUserid());
		

		} catch (Exception e1) {
			e1.printStackTrace();
			resXmlBean = new ResXmlBean(cgeneralhid, ResourceBundle.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
					.getString("xml_state_fail"), e1.getMessage());
			SendXml sendXml=new SendXml(resXmlBean,fileName,10000);
			sendXml.start(false);
			return;
		}

		// 生成PurchaseIncome实体类并将XML中得到的属性赋值给生成的PurchaseIncome类实体
		PurchaseIncome purchaseIncome = new PurchaseIncome();
		// 设置purchase_income的主键ID
		String piid = null;
		try {
			piid = MakeCode.getExcIDByRandomTableName("purchase_income", 2,
					"PI");
			log.info("ID======" + piid);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("------取purchase_income的ID字段值------" + e.toString());

		}
		purchaseIncome.setId(piid);
		purchaseIncome.setWarehouseid(warehouse1.getId());
		purchaseIncome.setPoid(cgeneralhid);
		purchaseIncome.setNccode(cgeneralhid);
		purchaseIncome.setProvideid(provider.getPid());
		purchaseIncome.setProvidename(provider.getPname());
		purchaseIncome.setPlinkman(plinkman.getName());
		purchaseIncome.setTel(plinkman.getMobile());
		purchaseIncome.setPaymode(0);
		purchaseIncome.setPrompt(provider.getPrompt());
		purchaseIncome.setRemark("");
		purchaseIncome.setIstally(0);
		purchaseIncome.setTallyid(0);
		purchaseIncome.setMakedeptid(tempuser.getMakedeptid());
		purchaseIncome.setMakeorganid(temporgan1.getId());
		purchaseIncome.setIsaudit(0);
		purchaseIncome.setAuditid(0);
		purchaseIncome.setKeyscontent(piid + "," + provider.getPname() + ","
				+ plinkman.getMobile() + "," + plinkman.getName() + ","
				+cgeneralhid);
		purchaseIncome.setMakeid(Integer.valueOf(tempuser.getUserid()));
		Date makedate = Dateutil.StringToDatetime(tmaketime);
		purchaseIncome.setMakedate(makedate);
		

		// 用于保存采购的封装好的对象
		XmlPurchase purchase = new XmlPurchase(cgeneralhid, purchaseIncome,
				nodeList4, appPurchaseIncome, appPurchaseIncomeDetail,
				opt_type, tempuser);

		// 如果单据操作方式为3则做删除操作
		if ("3".equals(opt_type)) {
			try {

				ReplyInfo rep = new ReplyInfo();
				// 进行删除记录操作
				rep = appPurchaseIncome.updateblankoutByNCcode(cgeneralhid,
						psnname);
				// 若删除成功则返回一个成功的XML
				if (rep.getSaveFlag()) {
					resXmlBean = new ResXmlBean(
							cgeneralhid,
							ResourceBundle
									.getBundle(
											"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
									.getString("xml_state_success"),
							ResourceBundle
									.getBundle(
											"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
									.getString("success"));
				}
				// 若删除失败则返回一个失败的XML
				else {
					resXmlBean = new ResXmlBean(
							cgeneralhid,
							ResourceBundle
									.getBundle(
											"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
									.getString("xml_state_fail"), rep
									.getErrorInfo());
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("------删除------" + e.toString());
				resXmlBean = new ResXmlBean(
						cgeneralhid,
						ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_fail"), e.toString());

			}
			// 若執行刪除操作则方法执行到此全部结束

		}

		// 如果单据操作方式为3则做新增操作
		else if ("1".equals(opt_type) || "2".equals(opt_type)) {
			resXmlBean = savePurchase(purchase);
		}

		log.info("------采购入库操作结束------");
		SendXml sendXml=new SendXml(resXmlBean,fileName,10000);
		sendXml.start(false);
		

	}

	/**
	 * 保存采购新增单据及货物的方法
	 * 
	 * @param purchase用于处理解析XML并保存相关对象的封装对象
	 */
	public static ResXmlBean savePurchase(XmlPurchase purchase) {
		ResXmlBean resXmlBean = new ResXmlBean();
		// 判断PurchaseIncome记录是否存在，如不存在则做保存操作，如存在则返回一个xml
		PurchaseIncome purchaseIncome = new PurchaseIncome();
		try {
			purchaseIncome = purchase.getAppPurchaseIncome()
					.getPurchaseIncomeByNCcode(purchase.getNccode());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (purchaseIncome == null) {
			// ReplyInfo对象封装了保存了是否成功保存与明细内容
			ReplyInfo replyInfo = purchase.getAppPurchaseIncome()
					.savePurchaseIncome(purchase.getPurchaseIncome());

			// 如果保存PurchaseIncome记录成功则继续保存PurchaseIncomeDetail记录
			if (replyInfo.getSaveFlag()) {

				// flag2为保存前一条detail记录时是否成功
				boolean flag2 = true;
				// 寻找出所需的节点并将内容赋值给需要的变量(Body内)

				String detailResult = "";
				for (int i = 0; i < purchase.getNodeList().getLength(); i++) {
					// 如果前一条detail保存成功的话则继续保存下一条detail记录
					if (flag2) {

						Node entryTemp = purchase.getNodeList().item(i);
						
						// 寻找Body下的entry标签
						if ("entry".equals(entryTemp.getNodeName())) {
							// 生成PurchaseIncomeDetail实体类
							PurchaseIncomeDetail purchaseIncomeDetail = new PurchaseIncomeDetail();
							// 生成purchaseIncomeDetail主键
							try {
								purchaseIncomeDetail
										.setId(Integer
												.valueOf(MakeCode
														.getExcIDByRandomTableName(
																"purchase_income_detail",
																0, "")));
							} catch (Exception e) {
								e.printStackTrace();
								log
										.error("------取purchase_income_detail的ID字段值------"
												+ e.toString());
							}
							NodeList entries = entryTemp.getChildNodes();
							// 寻找存货ID节点
							String cinvbasid = getTheNodeValue(entries,
									"cinvbasid");
							// 寻找计量单位节点
							String measdocname = getTheNodeValue(entries,
									"measdocname");
							// 寻找实入数量节点
							String ninnum = getTheNodeValue(entries, "ninnum");
							// 寻找单据状态节点
							String fbillflag = getTheNodeValue(entries,
									"fbillflag");

							purchaseIncomeDetail.setPiid(purchase
									.getPurchaseIncome().getId());
							purchaseIncomeDetail
									.setNccode(purchase.getNccode());
							/**
							 * 通过已有的货物ID去取最小单位
							 */
							AppProduct appProduct = new AppProduct();
							Product temp = new Product();
							try {
								temp = appProduct.getByNCcode(cinvbasid,
										purchase.getUsers().getUserid());
							} catch (Exception e) {
								e.printStackTrace();
								resXmlBean = new ResXmlBean(
										purchase.getNccode(),
										ResourceBundle
												.getBundle(
														"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
												.getString("xml_state_fail"), e
												.toString());
								SendXml sendXml=new SendXml(resXmlBean,fileName,10000);
								sendXml.start(false);
							}
							purchaseIncomeDetail.setUnitprice(temp
									.getLeastsale());
							purchaseIncomeDetail.setCost(temp.getCost());
							purchaseIncomeDetail.setUnitid(temp.getSunit());
							purchaseIncomeDetail.setProductid(temp.getId());
							purchaseIncomeDetail.setProductname(temp
									.getProductname());
							purchaseIncomeDetail.setQuantity(Double
									.valueOf(Integer.valueOf(ninnum)));
							purchaseIncomeDetail.setFactquantity(0.0);
							purchaseIncomeDetail
									.setSpecmode(temp.getSpecmode());
							purchaseIncomeDetail.setMakedate(purchase
									.getPurchaseIncome().getMakedate());
							// 无法找到EXCEL中所描述的Status

							// 保存生成的PurchaseIncomeDetail实体类

							ReplyInfo replyInfo2 = purchase
									.getAppPurchaseIncome()
									.savePurchaseIncomeDetail(
											purchaseIncomeDetail);
							flag2 = replyInfo2.getSaveFlag();
							detailResult = replyInfo2.getErrorInfo();

						}

					} else {
						try {
							purchase.getAppPurchaseIncome()
									.delPurchaseIncomeByNCcode(
											purchase.getNccode());
							purchase.getAppPurchaseIncomeDetail()
									.delPurchaseIncomeDetailByNCcode(
											purchase.getNccode());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							log.error("------回滚删除时------" + e.toString());
						}
						// 增加detail时出错返回的xml
						resXmlBean.setCgeneralhid(purchase.getNccode());
						resXmlBean.setState("0");
						resXmlBean.setDetail(detailResult);
						break;
					}
				}
				resXmlBean.setCgeneralhid(purchase.getNccode());
				resXmlBean
						.setState(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_success"));
				resXmlBean
						.setDetail(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("success"));
			} else {
				// 增加单据时出错时返回的xml
				resXmlBean.setCgeneralhid(purchase.getNccode());
				resXmlBean
						.setState(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_fail"));
				resXmlBean.setDetail(replyInfo.getErrorInfo());

			}

		} else {
			// 已经有该条记录时的xml
			HibernateUtil.currentTransaction();
			try {
				purchase.getAppPurchaseIncome().delPurchaseIncomeByNCcode(
						purchase.getNccode());
				purchase.getAppPurchaseIncomeDetail()
						.delPurchaseIncomeDetailByNCcode(purchase.getNccode());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			HibernateUtil.commitTransaction();
			resXmlBean = savePurchase(purchase);

		}

		return resXmlBean;

	}

	// 产成品入库
	/**
	 * 产成品入库及 委外加工入库（以产成品入库为模板）
	 * 
	 * @param doucment用友处给出的XML
	 * @param billType判断Incomesort为0还是1
	 */
	public static void productInbound(Document doucment, String billType) {
		log.info("------产成品入库操作开始------");
		// 用友xml参数含义(目前未解析，暂用NULL)
		// 出入库单表头id
		String cgeneralhid = null;
		// 仓库id
		String cwarehouseid = null;
		// 制单人
		String psnname = null;
		// 制单时间
		String tmaketime = null;
		// 存货id
		String cinvbasid = null;
		// 计量单位
		String measdocname = null;
		// 实入数量
		String ninnum = null;
		// 单据状态
		String fbillflag = null;
		// 单据操作方式
		String opt_type = null;

		// 取到根节点
		Element rootElement = doucment.getDocumentElement();
		NodeList nodeList = rootElement.getChildNodes();

		// 取到ic_bill节点及取下级所有节点
		Node nodeBill = nodeList.item(1);
		NodeList nodeList2 = nodeBill.getChildNodes();

		// 取到head节点及取下级所有节点
		Node nodeHead = nodeList2.item(1);
		NodeList nodeList3 = nodeHead.getChildNodes();

		// 取到body节点及所有下级节点
		Node nodeBody = nodeList2.item(3);
		NodeList nodeList4 = nodeBody.getChildNodes();

		// 寻找出所需的节点并将内容赋值给需要的变量(Head内)
		// 寻找到单据操作方式
		opt_type = getTheNodeValue(nodeList3, "opt_type");
		// 寻找到出入库单表头id节点
		cgeneralhid = getTheNodeValue(nodeList3, "cgeneralhid");
		// 寻找到入货仓库ID节点
		cwarehouseid = getTheNodeValue(nodeList3, "cwarehouseid");
		// 寻找到制单时间的节点
		tmaketime = getTheNodeValue(nodeList3, "tmaketime");
		// 寻找制单人节点
		psnname = getTheNodeValue(nodeList3, "psnname");
		// 寻找制单时间节点
		tmaketime = getTheNodeValue(nodeList3, "tmaketime");

		AppProductIncome appProductIncome = new AppProductIncome();
		AppProductIncomeDetail appProductIncomeDetail = new AppProductIncomeDetail();
		AppWarehouse appWareHouse = new AppWarehouse();
		AppUsers appUsers = new AppUsers();
		AppOrgan appOrgan = new AppOrgan();
		// 生成ProductIncome实体类并将XML中得到的属性赋值给生成的ProductIncome类实体
		ProductIncome productIncome = new ProductIncome();

		// 用于保存XML属性内容的封装
		ResXmlBean resXmlBean = null;

		// 设置product_income的主键ID
		String piid = null;
		try {
			piid = MakeCode
					.getExcIDByRandomTableName("product_income", 2, "PE");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("------取product_income的ID字段值------" + e.toString());

		}
		Warehouse warehouse1 = new Warehouse();
		Organ temporgan1 = new Organ();
		Users tempuser = new Users();
		try {
			tempuser = appUsers.findByNCcode(psnname);
			warehouse1 = appWareHouse.findByNCcode(cwarehouseid, tempuser
					.getUserid());
			temporgan1 = appOrgan.getOrganByID(warehouse1.getMakeorganid());

		} catch (Exception e1) {
			e1.printStackTrace();
			resXmlBean = new ResXmlBean(cgeneralhid, ResourceBundle.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
					.getString("xml_state_fail"), e1.getMessage());
			SendXml sendXml=new SendXml(resXmlBean,fileName,10000);
			sendXml.start(false);
			return;
		}
		productIncome.setId(piid);
		productIncome.setWarehouseid(cwarehouseid);
		productIncome.setHandwordcode(cgeneralhid);
		productIncome.setIsaudit(0);
		productIncome.setIncomesort(0);
		productIncome.setRemark("");
		productIncome.setAuditid(0);
		productIncome.setMakeorganid(temporgan1.getId());
		productIncome.setMakedeptid(warehouse1.getMakedeptid());
		productIncome.setMakeid(tempuser.getUserid());
		productIncome.setNccode(cgeneralhid);
		productIncome.setKeyscontent(piid + "," + cgeneralhid+ ","
				+cgeneralhid);

		// 产成品为0,委外加工入库为1
		if ("46".equals(billType)) {
			productIncome.setIncomesort(0);
		}
		if ("47".equals(billType)) {
			productIncome.setIncomesort(1);
		}

		// 存在疑问
		Date makedate = Dateutil.StringToDatetime(tmaketime);
		productIncome.setIncomedate(makedate);
		productIncome.setMakedate(makedate);
		// 用于保存产品的封装好的对象
		XmlProduct product = new XmlProduct(cgeneralhid, productIncome,
				nodeList4, appProductIncome, appProductIncomeDetail, opt_type,
				tempuser);

		// 如果单据操作方式为3则做删除操作
		if ("3".equals(opt_type)) {
			try {

				ReplyInfo rep = new ReplyInfo();
				// 进行删除记录操作
				rep = appProductIncome.updateblankoutByNCcode(cgeneralhid,
						psnname);
				// 若删除成功则返回一个成功的XML
				if (rep.getSaveFlag()) {
					resXmlBean = new ResXmlBean(
							cgeneralhid,
							ResourceBundle
									.getBundle(
											"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
									.getString("xml_state_success"),
							ResourceBundle
									.getBundle(
											"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
									.getString("success"));
				}
				// 若删除失败则返回一个失败的XML
				else {
					resXmlBean = new ResXmlBean(
							cgeneralhid,
							ResourceBundle
									.getBundle(
											"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
									.getString("xml_state_fail"), rep
									.getErrorInfo());
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("------删除------" + e.toString());
				resXmlBean = new ResXmlBean(
						cgeneralhid,
						ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_fail"), e.toString());

			}
			// 若執行刪除操作则方法执行到此全部结束

		}

		// 如果单据操作方式为1则做新增操作
		else if ("1".equals(opt_type) || "2".equals(opt_type)) {
			resXmlBean = saveProduct(product);
			// appProductIncome.auditProductIncome(piid,
			// String.valueOf(tempuser.getUserid()));
		}

		log.info("------产成品入库操作结束------");
		SendXml sendXml=new SendXml(resXmlBean,fileName,10000);
		sendXml.start(false);
	}

	/**
	 * 保存产成品新增单据及货物的方法
	 * 
	 * @param purchase用于处理解析XML并保存相关对象的封装对象
	 * @return resXmlBean返回出构建回复XML的resXmlBean
	 */
	public static ResXmlBean saveProduct(XmlProduct product) {
		ResXmlBean resXmlBean = new ResXmlBean();
		// 判断productIncome记录是否存在，如不存在则做保存操作，如存在则返回一个xml
		ProductIncome tempPI = new ProductIncome();
		try {
			tempPI = product.getAppProductIncome().getProductIncomeByNCcode(
					product.getNccode());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (tempPI == null) {
			// ReplyInfo对象封装了保存了是否成功保存与明细内容
			ReplyInfo replyInfo = product.getAppProductIncome()
					.saveProductIncome(product.getProductIncome());

			// 如果保存productIncome记录成功则继续保存productIncomeDetail记录
			if (replyInfo.getSaveFlag()) {

				// flag2为保存前一条detail记录时是否成功
				boolean flag2 = true;
				// 寻找出所需的节点并将内容赋值给需要的变量(Body内)

				String detailResult = "";
				for (int i = 0; i < product.getNodeList().getLength(); i++) {
					// 如果前一条detail保存成功的话则继续保存下一条detail记录
					if (flag2) {

						Node entryTemp = product.getNodeList().item(i);
				
						// 寻找Body下的entry标签
						if ("entry".equals(entryTemp.getNodeName())) {
							// 生成productIncomeDetail实体类
							ProductIncomeDetail productIncomeDetail = new ProductIncomeDetail();
							// 生成productIncomeDetail主键
							try {
								productIncomeDetail
										.setId(Integer
												.valueOf(MakeCode
														.getExcIDByRandomTableName(
																"product_income_detail",
																0, "")));
							} catch (Exception e) {
								e.printStackTrace();
								log
										.error("------取product_income_detail的ID字段值------"
												+ e.toString());
							}
							NodeList entries = entryTemp.getChildNodes();
							// 寻找存货ID节点
							String cinvbasid = getTheNodeValue(entries,
									"cinvbasid");
							// 寻找计量单位节点
							String measdocname = getTheNodeValue(entries,
									"measdocname");
							// 寻找实入数量节点
							String ninnum = getTheNodeValue(entries, "ninnum");
							// 寻找单据状态节点
							String fbillflag = getTheNodeValue(entries,
									"fbillflag");
							productIncomeDetail.setNccode(product.getNccode());
							productIncomeDetail.setPiid(product
									.getProductIncome().getId());

							/**
							 * 通过已有的货物ID去取最小单位
							 */
							AppProduct appProduct = new AppProduct();
							Product temp = new Product();
							try {
								temp = appProduct.getByNCcode(cinvbasid,
										product.getUsers().getUserid());
							} catch (Exception e) {
								e.printStackTrace();
								resXmlBean = new ResXmlBean(
										product.getNccode(),
										ResourceBundle
												.getBundle(
														"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
												.getString("xml_state_fail"), e
												.toString());
								SendXml sendXml=new SendXml(resXmlBean,fileName,10000);
								sendXml.start(false);
							}
							productIncomeDetail.setUnitid(temp.getSunit());
							productIncomeDetail.setProductname(temp
									.getProductname());
							productIncomeDetail.setProductid(temp.getId());
							productIncomeDetail.setSpecmode(temp.getSpecmode());
							productIncomeDetail.setQuantity(Double
									.valueOf(Integer.valueOf(ninnum)));
							productIncomeDetail.setFactquantity(Double
									.valueOf(ninnum));
							productIncomeDetail.setMakedate(product
									.getProductIncome().getMakedate());
							productIncomeDetail.setCostprice(temp
									.getLeastsale());
							productIncomeDetail.setCostsum(temp.getCost());
							// 无法找到EXCEL中所描述的Status

							// 保存生成的productIncomeDetail实体类

							ReplyInfo replyInfo2 = product
									.getAppProductIncome()
									.saveProductIncomeDetail(
											productIncomeDetail);
							flag2 = replyInfo2.getSaveFlag();
							detailResult = replyInfo2.getErrorInfo();

						}
					} else {
						try {
							HibernateUtil.currentTransaction();
							product.getAppProductIncome()
									.delProductIncomeByNCcode(
											product.getNccode());
							product.getAppProductIncomeDetail()
									.delProductIncomeDetailByNCcode(
											product.getNccode());
							HibernateUtil.commitTransaction();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							log.error("------回滚删除时------" + e.toString());
						}
						// 增加detail时出错返回的xml
						resXmlBean.setCgeneralhid(product.getNccode());
						resXmlBean.setState("0");
						resXmlBean.setDetail(detailResult);
						break;
					}
				}

				resXmlBean.setCgeneralhid(product.getNccode());
				resXmlBean
						.setState(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_success"));
				resXmlBean
						.setDetail(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("success"));
			} else {
				// 增加单据时出错时返回的xml
				resXmlBean.setCgeneralhid(product.getNccode());
				resXmlBean
						.setState(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_fail"));
				resXmlBean.setDetail(replyInfo.getErrorInfo());

			}

		} else {
			// 已经有该条记录时的xml

			try {
				HibernateUtil.currentTransaction();
				product.getAppProductIncome().delProductIncomeByNCcode(
						product.getNccode());
				product.getAppProductIncomeDetail()
						.delProductIncomeDetailByNCcode(product.getNccode());
				HibernateUtil.commitTransaction();
				resXmlBean = saveProduct(product);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return resXmlBean;

	}

	// 借入入库及其他入库（以其他入库为模板）
	/**
	 * 借入入库 other_inbound
	 * 
	 * @param doucment用友处给出的XML
	 */
	public static void otherInbound(Document doucment, int i) {
		log.info("------其他入库操作开始------");
		// 用友xml参数含义(目前未解析，暂用NULL)
		// 出入库单表头id
		String cgeneralhid = null;
		// 仓库id
		String cwarehouseid = null;
		// 制单人
		String psnname = null;
		// 存货id
		String cinvbasid = null;
		// 计量单位
		String measdocname = null;
		// 批次号
		String vbatchcode = null;
		// 实入数量
		String ninnum = null;
		// 单据状态
		String fbillflag = null;
		// 单据方式（新增、修改、删除）
		String opt_type = null;

		// 取到根节点
		Element rootElement = doucment.getDocumentElement();
		NodeList nodeList = rootElement.getChildNodes();

		// 取到ic_bill节点及取下级所有节点
		Node nodeBill = nodeList.item(1);
		NodeList nodeList2 = nodeBill.getChildNodes();

		// 取到head节点及取下级所有节点
		Node nodeHead = nodeList2.item(1);
		NodeList nodeList3 = nodeHead.getChildNodes();

		// 取到body节点及所有下级节点
		Node nodeBody = nodeList2.item(3);
		NodeList nodeList4 = nodeBody.getChildNodes();

		// 寻找出所需的节点并将内容赋值给需要的变量(Head内)
		// 寻找到出入库单表头id节点
		cgeneralhid = getTheNodeValue(nodeList3, "cgeneralhid");
		// 寻找到入货仓库ID节点
		cwarehouseid = getTheNodeValue(nodeList3, "cwarehouseid");
		// 寻找制单人节点
		psnname = getTheNodeValue(nodeList3, "psnname");

		opt_type = getTheNodeValue(nodeList3, "opt_type");
		AppOtherIncome appOtherIncome = new AppOtherIncome();
		AppOtherIncomeDetail appOtherIncomeDetail = new AppOtherIncomeDetail();
		// 生成OtherIncome实体类并将XML中得到的属性赋值给生成的OtherIncome类实体
		OtherIncome otherIncome = new OtherIncome();
		// 设置otherIncome_income的主键ID
		String oiid = null;
		try {
			oiid = MakeCode.getExcIDByRandomTableName("other_income", 2, "PY");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("------取other_income的ID字段值------" + e.toString());
		}
		otherIncome.setId(oiid);
		otherIncome.setWarehouseid(cwarehouseid);
		// 因为是借入入库，故incomesort固定为3
		otherIncome.setIncomesort(3);
		otherIncome.setBillno(cgeneralhid);
		otherIncome.setMakeid(Integer.valueOf(psnname));

		// 用于保存其他的封装好的对象
		XmlOther other = new XmlOther(cgeneralhid, otherIncome, nodeList4,
				appOtherIncome, appOtherIncomeDetail, opt_type);
		// 用于保存XML属性内容的封装
		ResXmlBean resXmlBean = null;

		// 如果单据操作方式为3则做删除操作
		if ("3".equals(opt_type)) {

			// 判断是入库的删除还是出库的删除，i=0时为入库删除
			if (i == 0) {
				try {
					String result = "";
					ReplyInfo replyInfo = appOtherIncome
							.updateblankoutByNCcode(cgeneralhid, psnname);
					if (replyInfo.getSaveFlag()) {
						result = ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_success");
					} else {
						result = ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_fail");
					}
					resXmlBean = new ResXmlBean(cgeneralhid, result, replyInfo
							.getErrorInfo());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 出库的删除
			else {

			}

		}

		// 如果单据操作方式为3则做新增操作
		else if ("3".equals(opt_type) || "2".equals(opt_type)) {
			resXmlBean = saveOther(other);
		}

		log.info("------其他入库操作结束------");
		SendXml sendXml=new SendXml(resXmlBean,fileName,10000);
		sendXml.start(false);
	}

	/**
	 * 保存其他新增单据及货物的方法
	 * 
	 * @param other用于处理解析XML并保存相关对象的封装对象
	 * @return resXmlBean返回出构建回复XML的resXmlBean
	 */
	public static ResXmlBean saveOther(XmlOther other) {
		ResXmlBean resXmlBean = new ResXmlBean();
		// 判断otherIncome记录是否存在，如不存在则做保存操作，如存在则返回一个xml
		if (other.getAppOtherIncome().checkIsAudit(other.getNccode())) {
			// ReplyInfo对象封装了保存了是否成功保存与明细内容
			ReplyInfo replyInfo = other.getAppOtherIncome().saveOtherIncome(
					other.getOtherIncome());

			// 如果保存productIncome记录成功则继续保存otherIncomeDetail记录
			if (replyInfo.getSaveFlag()) {

				// flag2为保存前一条detail记录时是否成功
				boolean flag2 = true;
				// 寻找出所需的节点并将内容赋值给需要的变量(Body内)

				String detailResult = "";
				for (int i = 0; i < other.getNodeList().getLength(); i++) {
					// 如果前一条detail保存成功的话则继续保存下一条detail记录
					if (flag2) {

						Node entryTemp = other.getNodeList().item(i);
			
						// 寻找Body下的entry标签
						if ("entry".equals(entryTemp.getNodeName())) {
							// 生成otherIncomeDetail实体类
							OtherIncomeDetail otherIncomeDetail = new OtherIncomeDetail();
							// 生成OtherIncomeDetail主键
							try {
								otherIncomeDetail
										.setId(Integer
												.valueOf(MakeCode
														.getExcIDByRandomTableName(
																"other_income",
																2, "PY")));
							} catch (Exception e) {
								e.printStackTrace();
								log
										.error("------取other_income_detail的ID字段值------"
												+ e.toString());
							}

							NodeList entries = entryTemp.getChildNodes();
							// 寻找存货ID节点
							String cinvbasid = getTheNodeValue(entries,
									"cinvbasid");
							// 寻找计量单位节点
							String measdocname = getTheNodeValue(entries,
									"measdocname");
							// 寻找实入数量节点
							String ninnum = getTheNodeValue(entries, "ninnum");
							// 寻找单据状态节点
							String fbillflag = getTheNodeValue(entries,
									"fbillflag");
							// 寻找批次号节点
							String vbatchcode = getTheNodeValue(entries,
									"vbatchcode");
							otherIncomeDetail.setNccode(other.getNccode());
							otherIncomeDetail.setOiid(other.getOtherIncome()
									.getId());
							otherIncomeDetail.setProductid(cinvbasid);
							// 存在疑问，数据类型不匹配
							// productIncomeDetail.setUnitid(measdocname);

							/**
							 * 通过已有的货物ID去取最小单位
							 */
							AppProduct appProduct = new AppProduct();
							Product temp = new Product();
							try {
								temp = appProduct.getByNCcode(cinvbasid);
							} catch (Exception e) {
								e.printStackTrace();
							}
							otherIncomeDetail.setUnitid(temp.getSunit());

							otherIncomeDetail.setQuantity(Double
									.valueOf(Integer.valueOf(ninnum)));
							otherIncomeDetail.setBatch(vbatchcode);
							// 无法找到EXCEL中所描述的Status

							// 保存生成的otherIncomeDetail实体类

							ReplyInfo replyInfo2 = other.getAppOtherIncome()
									.saveOtherIncomeDetail(otherIncomeDetail);
							flag2 = replyInfo2.getSaveFlag();
							detailResult = replyInfo2.getErrorInfo();

						}
					} else {
						try {
							other.getAppOtherIncome().delOtherIncomeByNCcode(
									other.getNccode());
							other.getAppOtherIncomeDetail()
									.delOtherIncomeDetailByNCcode(
											other.getNccode());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							log.error("------回滚删除时------" + e.toString());
						}
						// 增加detail时出错返回的xml
						resXmlBean.setCgeneralhid(other.getNccode());
						resXmlBean.setState("0");
						resXmlBean.setDetail(detailResult);
						break;
					}
				}

			} else {
				// 增加单据时出错时返回的xml
				resXmlBean.setCgeneralhid(other.getNccode());
				resXmlBean
						.setState(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_fail"));
				resXmlBean.setDetail(replyInfo.getErrorInfo());

			}

		} else {
			// 已经有该条记录时的xml
			resXmlBean.setCgeneralhid(other.getNccode());
			resXmlBean.setState(ResourceBundle.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
					.getString("xml_state_fail"));
			resXmlBean.setDetail(ResourceBundle.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
					.getString("is_exist"));

		}

		return resXmlBean;

	}

	// 调拨入库
	/**
	 * 调拨入库 transfer_inbound
	 * 
	 * @param doucment用友处给出的XML
	 * @param i用来判断是出库单还是入库单1为出库0为入库
	 */
	public static void transferInbound(Document doucment, int i) {
		log.info("------调拨入库操作开始------");
		// 出入库单表头id
		String cgeneralhid = null;
		// 单据日期
		String dbilldate = null;
		// 其它仓库id
		String cotherwhid = null;
		// 仓库id
		String cwarehouseid = null;
		// 备注
		String vnote = null;
		// 制单人
		String psnname = null;
		// 发运地址
		String vdiliveraddress = null;
		// 存货id
		String cinvbasid = null;
		// 计量单位
		String measdocname = null;
		// 批次号
		String vbatchcode = null;
		// 实入数量
		String ninnum = null;
		// 单据状态
		String fbillflag = null;
		// 单据方式（新增、修改、删除）
		String opt_type = null;
		// 来源单据
		String csourcebillhid = null;

		// 取到根节点
		Element rootElement = doucment.getDocumentElement();
		NodeList nodeList = rootElement.getChildNodes();

		// 取到ic_bill节点及取下级所有节点
		Node nodeBill = nodeList.item(1);
		NodeList nodeList2 = nodeBill.getChildNodes();

		// 取到head节点及取下级所有节点
		Node nodeHead = nodeList2.item(1);
		NodeList nodeList3 = nodeHead.getChildNodes();

		// 取到body节点及所有下级节点
		Node nodeBody = nodeList2.item(3);
		NodeList nodeList4 = nodeBody.getChildNodes();

		// 寻找出所需的节点并将内容赋值给需要的变量(Head内)
		// 寻找到出入库单表头id节点
		cgeneralhid = getTheNodeValue(nodeList3, "cgeneralhid");
		// 寻找到单据日期节点
		dbilldate = getTheNodeValue(nodeList3, "dbilldate");
		// 寻找其他仓库ID节点
		cotherwhid = getTheNodeValue(nodeList3, "cotherwhid");
		// 寻找到仓库ID节点
		cwarehouseid = getTheNodeValue(nodeList3, "cwarehouseid");
		// 寻找备注节点
		vnote = getTheNodeValue(nodeList3, "vnote");
		// 寻找制单人节点
		psnname = getTheNodeValue(nodeList3, "psnname");
		// 寻找发运地址节点
		vdiliveraddress = getTheNodeValue(nodeList3, "vdiliveraddress");
		// 寻找来源单据编号
		csourcebillhid = getTheNodeValue(nodeList3, "csourcebillhid");

		opt_type = getTheNodeValue(nodeList3, "opt_type");

		AppStockMove appStockMove = new AppStockMove();
		AppStockMoveDetail appStockMoveDetail = new AppStockMoveDetail();
		AppWarehouse appWareHouse = new AppWarehouse();
		AppOlinkMan appo = new AppOlinkMan();
		AppUsers appUsers = new AppUsers();
		AppOrgan appOrgan = new AppOrgan();

		Warehouse warehouseFrom = new Warehouse();
		Warehouse warehouseTo = new Warehouse();
		Users tempuser = new Users();
		Organ temporganFrom = new Organ();
		Organ temporganTo = new Organ();
		Olinkman olinkman = new Olinkman();
		
		// 用于保存XML属性内容的封装
		ResXmlBean resXmlBean = null;
		try {
			tempuser = appUsers.findByNCcode(psnname);
			warehouseFrom = appWareHouse.findByNCcode(cwarehouseid, tempuser
					.getUserid());
			warehouseTo = appWareHouse.findByNCcode(cotherwhid, tempuser
					.getUserid());

			temporganFrom = appOrgan.getOrganByID(warehouseFrom
					.getMakeorganid());
			temporganTo = appOrgan.getOrganByID(warehouseTo.getMakeorganid());
			olinkman = appo.getMainLinkmanByCid(warehouseFrom.getMakeorganid());

		} catch (Exception e1) {
			HibernateUtil.commitTransaction();
			e1.printStackTrace();
			resXmlBean = new ResXmlBean(cgeneralhid, ResourceBundle.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
					.getString("xml_state_fail"), e1.getMessage());
			SendXml sendXml=new SendXml(resXmlBean,fileName,10000);
			sendXml.start(false);
			return;
		}

		StockMove stockMove = new StockMove();
		MakeCode mc = new MakeCode();
		// 设置Stock_move的主键ID
		String smid = null;
		try {
			smid = mc.getExcIDByRandomTableName("stock_move", 2, "SM");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("------取Stock_move的ID字段值------" + e.toString());
		}
		stockMove.setId(smid);
		stockMove.setOutwarehouseid(warehouseFrom.getId());
		stockMove.setInwarehouseid(warehouseTo.getId());
		stockMove.setTransportmode(1);
		stockMove.setOlinkman(olinkman.getName());
		stockMove.setOtel(olinkman.getMobile());
		stockMove.setTotalsum(0.00);
		stockMove.setMovecause("");
		stockMove.setTransportaddr(warehouseTo.getWarehouseaddr());
		stockMove.setRemark("");
		stockMove.setIsaudit(0);
		stockMove.setInorganid(temporganTo.getId());
		stockMove.setMakeorganid(tempuser.getMakeorganid());
		stockMove.setMakedeptid(tempuser.getMakedeptid());
		stockMove.setMakeid(tempuser.getUserid());
		stockMove.setIsshipment(0);
		stockMove.setIscomplete(0);
		stockMove.setIsblankout(0);
		stockMove.setKeyscontent(smid + " , "+ " , "
				+cgeneralhid);
		stockMove.setNccode(cgeneralhid);

		Date moveDate = Dateutil.StringToDate(dbilldate);
		stockMove.setMovedate(moveDate);
		stockMove.setMakedate(moveDate);
		// 用于保存借入的封装好的对象
		XmlStockMove xmlstockMove = new XmlStockMove(cgeneralhid, stockMove,
				nodeList4, appStockMove, appStockMoveDetail, opt_type, tempuser);
		

		// 如果单据操作方式为3则做删除操作
		if ("3".equals(opt_type)) {

			// 判断是入库的删除还是出库的删除，i=0时为入库删除

			try {
				String result = "";
				ReplyInfo replyInfo = appStockMove.updateblankoutByNCcode(
						cgeneralhid, psnname);
				if (replyInfo.getSaveFlag()) {
					result = ResourceBundle
							.getBundle(
									"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
							.getString("xml_state_success");
				} else {
					result = ResourceBundle
							.getBundle(
									"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
							.getString("xml_state_fail");
				}
				resXmlBean = new ResXmlBean(cgeneralhid, result, replyInfo
						.getErrorInfo());
			} catch (Exception e) {
				e.printStackTrace();
				resXmlBean = new ResXmlBean(
						cgeneralhid,
						ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_fail"), e.toString());
				SendXml sendXml=new SendXml(resXmlBean,fileName,10000);
				sendXml.start(false);
				return;
			}

		}

		// 如果单据操作方式为1则做新增操作
		else if ("1".equals(opt_type) || "2".equals(opt_type)) {
			// 若出库则需要数量复核
			if (1 == i) {
				resXmlBean = saveStockMove(xmlstockMove);
				AppStockMove.auditStockMove(smid, String.valueOf(tempuser
						.getUserid()));
			}
			// 若入库则更新表中的nccode_receive字段
			if (i == 0) {
				resXmlBean = completeStockMove(csourcebillhid, cgeneralhid);
			}
		}

		log.info("------调拨入库操作结束------");
		SendXml sendXml=new SendXml(resXmlBean,fileName,10000);
		sendXml.start(false);
	}

	/**
	 * 用来完成入库单据的录入
	 * 
	 * @param cgeneralhid
	 *            入库单号
	 * @param csourcebillhid
	 *            相对应的出库单号
	 * @return resXmlBean返回出构建回复XML的resXmlBean
	 */
	public static ResXmlBean completeStockMove(String csourcebillhid,
			String cgeneralhid) {
		ResXmlBean resXmlBean = new ResXmlBean();
		AppStockMove appStockMove = new AppStockMove();
		try {
			String result = appStockMove.completeStockMoveRec(csourcebillhid,
					cgeneralhid);
			if ("success".equals(result)) {
				resXmlBean.setCgeneralhid(cgeneralhid);
				resXmlBean
						.setState(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_success"));
				resXmlBean.setDetail(result);
			} else {
				resXmlBean.setCgeneralhid(cgeneralhid);
				resXmlBean
						.setState(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_fail"));
				resXmlBean.setDetail("没有该入库单相对应的出库单");
			}
		} catch (Exception e) {
			resXmlBean.setCgeneralhid(cgeneralhid);
			resXmlBean.setState(ResourceBundle.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
					.getString("xml_state_fail"));
			resXmlBean.setDetail(e.toString());
		}
		return resXmlBean;
	}

	/**
	 * 保存调拨入库新增单据及货物的方法
	 * 
	 * @param stockMove用于处理解析XML并保存相关对象的封装对象
	 * @return resXmlBean返回出构建回复XML的resXmlBean
	 */
	public static ResXmlBean saveStockMove(XmlStockMove stockMove) {
		ResXmlBean resXmlBean = new ResXmlBean();
		MakeCode mc = new MakeCode();
		StockMove tempstockMove = new StockMove();
		try {
			tempstockMove = stockMove.getAppStockMove().getStockMoveByNCcode(
					stockMove.getNccode());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 判断StockMove记录是否存在，如不存在则做保存操作，如存在则返回一个xml
		if (tempstockMove == null) {
			// ReplyInfo对象封装了保存了是否成功保存与明细内容
			ReplyInfo replyInfo = stockMove.getAppStockMove().saveStockMove(
					stockMove.getStockMove());

			// 如果保存StockMove记录成功则继续保存StockMoveDetail记录
			if (replyInfo.getSaveFlag()) {

				// flag2为保存前一条detail记录时是否成功
				boolean flag2 = true;
				// 寻找出所需的节点并将内容赋值给需要的变量(Body内)

				String detailResult = "";
				for (int i = 0; i < stockMove.getNodeList().getLength(); i++) {
					// 如果前一条detail保存成功的话则继续保存下一条detail记录
					if (flag2) {

						Node entryTemp = stockMove.getNodeList().item(i);
					
						// 寻找Body下的entry标签
						if ("entry".equals(entryTemp.getNodeName())) {
							// 生成StockMoveDetail实体类
							StockMoveDetail stockMoveDetail = new StockMoveDetail();
							// 生成StockMoveDetail主键
							try {
								stockMoveDetail.setId(Integer.valueOf(mc
										.getExcIDByRandomTableName(
												"stock_move_detail", 0, "")));
							} catch (Exception e) {
								e.printStackTrace();
								log
										.error("------取stock_move_detail的ID字段值------"
												+ e.toString());
							}

							NodeList entries = entryTemp.getChildNodes();
							// 寻找存货ID节点
							String cinvbasid = getTheNodeValue(entries,
									"cinvbasid");
							// 寻找计量单位节点
							String measdocname = getTheNodeValue(entries,
									"measdocname");
							// 寻找实入数量节点
							String ninnum = getTheNodeValue(entries, "ninnum");
							// 寻找单据状态节点
							String fbillflag = getTheNodeValue(entries,
									"fbillflag");
							// 寻找批次号节点
							String vbatchcode = getTheNodeValue(entries,
									"vbatchcode");
							stockMoveDetail.setSmid(stockMove.getStockMove()
									.getId());
							stockMoveDetail.setNccode(stockMove.getNccode());

							/**
							 * 通过已有的货物ID去取最小单位
							 */
							AppProduct appProduct = new AppProduct();
							Product temp = new Product();
							try {
								temp = appProduct.getByNCcode(cinvbasid,
										stockMove.getUsers().getUserid());
							} catch (Exception e) {
								e.printStackTrace();
								resXmlBean = new ResXmlBean(
										stockMove.getNccode(),
										ResourceBundle
												.getBundle(
														"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
												.getString("xml_state_fail"), e
												.toString());
								return resXmlBean;
							}
							stockMoveDetail.setUnitid(temp.getSunit());

							stockMoveDetail.setQuantity(Double.valueOf(Integer
									.valueOf(ninnum)));
							stockMoveDetail.setBatch(vbatchcode);
							stockMoveDetail.setProductid(temp.getId());
							stockMoveDetail.setProductname(temp
									.getProductname());
							stockMoveDetail.setSpecmode(temp.getSpecmode());
							stockMoveDetail.setTakequantity(0.00);
							stockMoveDetail.setCost(temp.getCost());

							// 无法找到EXCEL中所描述的Status

							// 保存生成的stockMoveDetail实体类

							ReplyInfo replyInfo2 = stockMove.getAppStockMove()
									.saveStockMoveDetail(stockMoveDetail);
							flag2 = replyInfo2.getSaveFlag();
							detailResult = replyInfo2.getErrorInfo();

						}
					} else {
						try {
							stockMove.getAppStockMove().delStockMoveByNCcode(
									stockMove.getNccode());
							stockMove.getAppStockMoveDetail()
									.delStockMoveDetailByNCcode(
											stockMove.getNccode());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							log.error("------回滚删除时------" + e.toString());
						}
						// 增加detail时出错返回的xml
						resXmlBean.setCgeneralhid(stockMove.getNccode());
						resXmlBean.setState("0");
						resXmlBean.setDetail(detailResult);
						break;
					}
				}

				resXmlBean.setCgeneralhid(stockMove.getNccode());
				resXmlBean
						.setState(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_success"));
				resXmlBean
						.setDetail(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("success"));

			} else {
				// 增加单据时出错时返回的xml
				resXmlBean.setCgeneralhid(stockMove.getNccode());
				resXmlBean
						.setState(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_fail"));
				resXmlBean.setDetail(replyInfo.getErrorInfo());

			}

		} else {
			if (1 == tempstockMove.getIsshipment()) {
				resXmlBean.setCgeneralhid(stockMove.getNccode());
				resXmlBean
						.setState(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_fail"));
				resXmlBean
						.setDetail(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("has_been_sent"));
				return resXmlBean;
			}

			// 已经有该条记录时的xml
			HibernateUtil.currentTransaction();
			try {
				stockMove.getAppStockMove().delStockMoveByNCcode(
						stockMove.getNccode());
				stockMove.getAppStockMoveDetail().delStockMoveDetailByNCcode(
						stockMove.getNccode());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			HibernateUtil.commitTransaction();
			resXmlBean = saveStockMove(stockMove);

		}

		return resXmlBean;

	}

	// 订购出库
	/**
	 * 订购出库 sale_outbound
	 * 
	 * @param doucment用友处给出的XML
	 */
	public static void saleOutbound(Document doucment) {
		log.info("------订购出库操作开始------");
		// 出入库单表头id
		String cgeneralhid = null;
		// 单据日期
		String dbilldate = null;
		// 其它仓库id
		String cotherwhid = null;
		// 仓库id
		String cwarehouseid = null;
		// 备注
		String vnote = null;
		// 制单人
		String psnname = null;
		// 发运地址
		String vdiliveraddress = null;
		// 存货id
		String cinvbasid = null;
		// 计量单位
		String measdocname = null;
		// 批次号
		String vbatchcode = null;
		// 实入数量
		String ninnum = null;
		// 单据状态
		String fbillflag = null;
		// 单据方式（新增、修改、删除）
		String opt_type = null;

		// 取到根节点
		Element rootElement = doucment.getDocumentElement();
		NodeList nodeList = rootElement.getChildNodes();

		// 取到ic_bill节点及取下级所有节点
		Node nodeBill = nodeList.item(1);
		NodeList nodeList2 = nodeBill.getChildNodes();

		// 取到head节点及取下级所有节点
		Node nodeHead = nodeList2.item(1);
		NodeList nodeList3 = nodeHead.getChildNodes();

		// 取到body节点及所有下级节点
		Node nodeBody = nodeList2.item(3);
		NodeList nodeList4 = nodeBody.getChildNodes();

		// 寻找出所需的节点并将内容赋值给需要的变量(Head内)
		// 寻找到出入库单表头id节点
		cgeneralhid = getTheNodeValue(nodeList3, "cgeneralhid");
		// 寻找到单据日期节点
		dbilldate = getTheNodeValue(nodeList3, "dbilldate");
		// 寻找其他仓库ID节点
		cotherwhid = getTheNodeValue(nodeList3, "cotherwhid");
		// 寻找到仓库ID节点
		cwarehouseid = getTheNodeValue(nodeList3, "cwarehouseid");
		// 寻找备注节点
		vnote = getTheNodeValue(nodeList3, "vnote");
		// 寻找制单人节点
		psnname = getTheNodeValue(nodeList3, "psnname");
		// 寻找发运地址节点
		vdiliveraddress = getTheNodeValue(nodeList3, " vdiliveraddress");
		opt_type = getTheNodeValue(nodeList3, "opt_type");

		AppStockAlterMove appStockAlterMove = new AppStockAlterMove();
		AppStockAlterMoveDetail appStockAlterMoveDetail = new AppStockAlterMoveDetail();
		StockAlterMove stockAlterMove = new StockAlterMove();
		AppWarehouse appWareHouse = new AppWarehouse();
		AppOlinkMan appo = new AppOlinkMan();
		AppUsers appUsers = new AppUsers();
		AppOrgan appOrgan = new AppOrgan();
		MakeCode mc = new MakeCode();
		// 设置Stock_alter_move的主键ID
		String smid = null;
		try {
			smid = MakeCode.getExcIDByRandomTableName("stock_alter_move", 2,
					"OM");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("------取Stock_alter_move的ID字段值------" + e.toString());
		}

		Warehouse warehouseFrom = new Warehouse();
		Warehouse warehouseTo = new Warehouse();
		Users tempuser = new Users();
		Organ temporganFrom = new Organ();
		Organ temporganTo = new Organ();
		Olinkman olinkman = new Olinkman();

		// 用于保存XML属性内容的封装
		ResXmlBean resXmlBean = null;
		try {
			tempuser = appUsers.findByNCcode(psnname);
			warehouseFrom = appWareHouse.findByNCcode(cwarehouseid, tempuser
					.getUserid());
			warehouseTo = appWareHouse.findByNCcode(cotherwhid, tempuser
					.getUserid());

			temporganFrom = appOrgan.getOrganByID(warehouseFrom
					.getMakeorganid());
			temporganTo = appOrgan.getOrganByID(warehouseTo.getMakeorganid());
			olinkman = appo.getMainLinkmanByCid(warehouseFrom.getMakeorganid());

		} catch (Exception e1) {
			e1.printStackTrace();
			resXmlBean = new ResXmlBean(cgeneralhid, ResourceBundle.getBundle(
					"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
					.getString("xml_state_fail"), e1.getMessage());
			SendXml sendXml=new SendXml(resXmlBean,fileName,10000);
			sendXml.start(false);
			return;
		}
		stockAlterMove.setId(smid);
		Date moveDate = Dateutil.StringToDate(dbilldate);
		stockAlterMove.setMovedate(moveDate);
		stockAlterMove.setMakedate(moveDate);
		stockAlterMove.setOutwarehouseid(warehouseFrom.getId());
		stockAlterMove.setInwarehouseid(warehouseTo.getId());
		stockAlterMove.setMakeid(tempuser.getUserid());
		stockAlterMove.setTransportaddr(warehouseFrom.getWarehouseaddr());
		stockAlterMove.setOlinkman(warehouseFrom.getUsername());
		stockAlterMove.setPaymentmode(0);
		stockAlterMove.setTransportmode(0);
		stockAlterMove.setInvmsg(0);
		stockAlterMove.setOtel(olinkman.getMobile());
		stockAlterMove.setIsblankout(0);
		stockAlterMove.setIsaudit(0);
		stockAlterMove.setIsmaketicket(0);
		stockAlterMove.setIsreceiveticket(0);
		stockAlterMove.setTickettitle("");
		stockAlterMove.setMovecause("");
		stockAlterMove.setRemark("");
		stockAlterMove.setMakeorganid(temporganFrom.getId());
		stockAlterMove.setMakedeptid(warehouseFrom.getDept());
		stockAlterMove.setMakeorganidname(temporganFrom.getOrganname());
		stockAlterMove.setIsshipment(0);
		stockAlterMove.setIstally(0);
		stockAlterMove.setReceiveorganid(temporganTo.getId());
		stockAlterMove.setReceiveorganidname(temporganTo.getOrganname());
		stockAlterMove.setReceivedeptid(warehouseTo.getDept());
		stockAlterMove.setIscomplete(0);
		stockAlterMove.setTakestatus(0);
		stockAlterMove.setNccode(cgeneralhid);
		stockAlterMove.setKeyscontent(smid + "," + warehouseFrom.getUsername()
				+ "," + olinkman.getMobile() + ","
				+ temporganFrom.getOrganname()+ ","
				+cgeneralhid);
		// 用于保存采购的封装好的对象
		XmlStockAlterMove xmlstockAlterMove = new XmlStockAlterMove(
				cgeneralhid, stockAlterMove, nodeList4, appStockAlterMove,
				appStockAlterMoveDetail, opt_type, tempuser);

		// 如果单据操作方式为3则做删除操作
		if ("3".equals(opt_type)) {

			ReplyInfo rep = new ReplyInfo();
			// 进行删除记录操作
			try {
				rep = appStockAlterMove.updateblankoutByNCcode(cgeneralhid,
						psnname);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 若删除成功则返回一个成功的XML
			if (rep.getSaveFlag()) {
				resXmlBean = new ResXmlBean(
						cgeneralhid,
						ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_success"),
						ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("success"));
			}
			// 若删除失败则返回一个失败的XML
			else {
				resXmlBean = new ResXmlBean(
						cgeneralhid,
						ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_fail"), rep
								.getErrorInfo());
			}
		}

		// 如果单据操作方式为1则做新增操作
		else if ("1".equals(opt_type) || "2".equals(opt_type)) {
			resXmlBean = new ResXmlBean();
			resXmlBean = saveStockAlterMove(xmlstockAlterMove);
			appStockAlterMove.auditStockAlterMove(smid, String.valueOf(tempuser
					.getUserid()));

		}

		log.info("------订购出库操作结束------");
		SendXml sendXml=new SendXml(resXmlBean,fileName,10000);
		sendXml.start(false);
	}

	/**
	 * 保存订购出库新增单据及货物的方法
	 * 
	 * @param stockAlterMove用于处理解析XML并保存相关对象的封装对象
	 * @return resXmlBean返回出构建回复XML的resXmlBean
	 */
	public static ResXmlBean saveStockAlterMove(XmlStockAlterMove stockAlterMove) {
		ResXmlBean resXmlBean = new ResXmlBean();
		MakeCode mc = new MakeCode();

		// 通过NCcode取数据库里对象
		StockAlterMove tempSAM = new StockAlterMove();
		try {
			tempSAM = stockAlterMove.getAppStockAlterMove()
					.getStockAlterMoveByNCcode(stockAlterMove.getNccode());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 判断Stockaltermove记录是否存在，如不存在则做保存操作，如存在则删除
		if (tempSAM == null) {
			// ReplyInfo对象封装了保存了是否成功保存与明细内容
			ReplyInfo replyInfo = stockAlterMove.getAppStockAlterMove()
					.saveStockAlterMove(stockAlterMove.getStockAlterMove());

			// 如果保存Stockaltermove记录成功则继续保存Stockaltermove记录
			if (replyInfo.getSaveFlag()) {

				// flag2为保存前一条detail记录时是否成功
				boolean flag2 = true;
				// 寻找出所需的节点并将内容赋值给需要的变量(Body内)

				String detailResult = "";
				for (int i = 0; i < stockAlterMove.getNodeList().getLength(); i++) {
					// 如果前一条detail保存成功的话则继续保存下一条detail记录
					if (flag2) {

						Node entryTemp = stockAlterMove.getNodeList().item(i);
					
						// 寻找Body下的entry标签
						if ("entry".equals(entryTemp.getNodeName())) {
							// 生成StockaltermoveDetail实体类
							StockAlterMoveDetail stockAlterMoveDetail = new StockAlterMoveDetail();
							// 生StockaltermoveDetail主键
							try {
								stockAlterMoveDetail
										.setId(Integer
												.valueOf(MakeCode
														.getExcIDByRandomTableName(
																"stock_alter_move_detail",
																0, "")));
							} catch (Exception e) {
								e.printStackTrace();
								log
										.error("------取stock_move_detail的ID字段值------"
												+ e.toString());
							}

							NodeList entries = entryTemp.getChildNodes();
							// 寻找存货ID节点
							String cinvbasid = getTheNodeValue(entries,
									"cinvbasid");
							// 寻找计量单位节点
							String measdocname = getTheNodeValue(entries,
									"measdocname");
							// 寻找实入数量节点
							String ninnum = getTheNodeValue(entries, "ninnum");
							// 寻找单据状态节点
							String fbillflag = getTheNodeValue(entries,
									"fbillflag");
							// 寻找批次号节点
							String vbatchcode = getTheNodeValue(entries,
									"vbatchcode");
							stockAlterMoveDetail.setSamid(stockAlterMove
									.getStockAlterMove().getId());
							stockAlterMoveDetail.setNccode(stockAlterMove
									.getNccode());

							// 存在疑问，数据类型不匹配
							// productIncomeDetail.setUnitid(measdocname);

							/**
							 * 通过已有的货物ID去取最小单位
							 */
							AppProduct appProduct = new AppProduct();
							Product temp = new Product();
							try {
								temp = appProduct.getByNCcode(cinvbasid,
										stockAlterMove.getUsers().getUserid());
							} catch (Exception e) {
								e.printStackTrace();
								resXmlBean = new ResXmlBean(
										stockAlterMove.getNccode(),
										ResourceBundle
												.getBundle(
														"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
												.getString("xml_state_fail"), e
												.toString());
								return resXmlBean;
							}
							stockAlterMoveDetail.setUnitid(temp.getSunit());

							stockAlterMoveDetail.setQuantity(Double
									.valueOf(Integer.valueOf(ninnum)));
							stockAlterMoveDetail.setBatch(vbatchcode);
							stockAlterMoveDetail.setProductid(temp.getId());
							stockAlterMoveDetail.setProductname(temp
									.getProductname());
							stockAlterMoveDetail
									.setSpecmode(temp.getSpecmode());
							stockAlterMoveDetail.setUnitprice(temp
									.getLeastsale());
							stockAlterMoveDetail.setTakequantity(0.0);
							stockAlterMoveDetail.setCost(temp.getCost());
							stockAlterMoveDetail.setNccode(stockAlterMove
									.getNccode());

							// 无法找到EXCEL中所描述的Status

							ReplyInfo replyInfo2 = stockAlterMove
									.getAppStockAlterMove()
									.saveStockAlterMoveDetail(
											stockAlterMoveDetail);
							flag2 = replyInfo2.getSaveFlag();
							detailResult = replyInfo2.getErrorInfo();

						}
					} else {
						try {
							stockAlterMove.getAppStockAlterMove()
									.delStockAlterMoveByNCcode(
											stockAlterMove.getNccode());
							stockAlterMove.getAppStockAlterMoveDetail()
									.delStockAlterMoveDetailByNCcode(
											stockAlterMove.getNccode());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							log.error("------回滚删除时------" + e.toString());
						}
						// 增加detail时出错返回的xml
						resXmlBean.setCgeneralhid(stockAlterMove.getNccode());
						resXmlBean.setState("0");
						resXmlBean.setDetail(detailResult);
						break;
					}
				}
				resXmlBean.setCgeneralhid(stockAlterMove.getNccode());
				resXmlBean
						.setState(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_success"));
				resXmlBean
						.setDetail(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("success"));
			} else {
				// 增加单据时出错时返回的xml
				resXmlBean.setCgeneralhid(stockAlterMove.getNccode());
				resXmlBean
						.setState(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_fail"));
				resXmlBean.setDetail(replyInfo.getErrorInfo());

			}

		} else {
			// 已经有该条记录时判断是否出库复核如没有则删除该记录
			if (1 == tempSAM.getIsshipment()) {
				resXmlBean.setCgeneralhid(stockAlterMove.getNccode());
				resXmlBean
						.setState(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("xml_state_fail"));
				resXmlBean
						.setDetail(ResourceBundle
								.getBundle(
										"com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
								.getString("has_been_sent"));
				return resXmlBean;
			}
			try {
				HibernateUtil.currentTransaction();
				stockAlterMove.getAppStockAlterMove()
						.delStockAlterMoveByNCcode(stockAlterMove.getNccode());
				stockAlterMove.getAppStockAlterMoveDetail()
						.delStockAlterMoveDetailByNCcode(
								stockAlterMove.getNccode());
				HibernateUtil.commitTransaction();
				resXmlBean = saveStockAlterMove(stockAlterMove);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return resXmlBean;

	}

	// 共通方法
	/**
	 * 转换字符串到DATE格式
	 * 
	 * @param style为需要转成的格式;
	 * @param strDate为需要转成的字符串;
	 */
	public static Date changeStrToDate(String style, String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(style);
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {

			e.printStackTrace();
			log.error("------日期转换------" + e.toString());
		}
		return date;
	}

	/**
	 * 寻找到和NAME匹配的node的value值
	 * 
	 * @param nodeList为需要遍历的node集合
	 * @param nodeName为需要寻找出的node的名字
	 * @return result为该node的value值
	 */
	public static String getTheNodeValue(NodeList nodeList, String nodeName) {
		String result = null;
		for (int j = 0; j < nodeList.getLength(); j++) {
			Node node = nodeList.item(j);
			if (nodeName.equals(node.getNodeName())) {
				if (node.hasChildNodes()) {
					result = node.getFirstChild().getNodeValue();
				}
			}

		}
		return result;
	}

	

	/**
	 * 打印 DOM 节点 输出格式为： nodeType(nodeName,nodeValue)
	 * "ATTRIBUTE"(attributeName=attributeValue) ...
	 * childNodeType[childNodeName,childNodeValue] ...
	 */
	public static void printNode(Node node, int count) {
		if (node != null) {
			String tmp = "";
			for (int i = 0; i < count; i++)
				tmp += "  ";
			// 获取node节点的节点类型，赋值给nodeType变量
			int nodeType = node.getNodeType();
			switch (nodeType) {
			case Node.ATTRIBUTE_NODE:
				tmp += "ATTRIBUTE";
				break;
			case Node.CDATA_SECTION_NODE:
				tmp += "CDATA_SECTION";
				break;
			case Node.COMMENT_NODE:
				tmp += "COMMENT";
				break;
			case Node.DOCUMENT_FRAGMENT_NODE:
				tmp += "DOCUMENT_FRAGMENT";
				break;
			case Node.DOCUMENT_NODE:
				tmp += "DOCUMENT";
				break;
			case Node.DOCUMENT_TYPE_NODE:
				tmp += "DOCUMENT_TYPE";
				break;
			case Node.ELEMENT_NODE:
				tmp += "ELEMENT";
				break;
			case Node.ENTITY_NODE:
				tmp += "ENTITY";
				break;
			case Node.ENTITY_REFERENCE_NODE:
				tmp += "ENTITY_REFERENCE";
				break;
			case Node.NOTATION_NODE:
				tmp += "NOTATION";
				break;
			case Node.PROCESSING_INSTRUCTION_NODE:
				tmp += "PROCESSING_INSTRUCTION";
				break;
			case Node.TEXT_NODE:
				tmp += "TEXT";
				break;
			default:
				return;// invalid node type.
			}

			System.out.println(tmp + " (" + node.getNodeName() + ","
					+ node.getNodeValue() + ")");
			/*
			 * node.getAttributes()方法返回 包含node节点的属性的 NamedNodeMap（如果它是 Element）
			 */
			NamedNodeMap attrs = node.getAttributes();
			if (attrs != null)
				for (int i = 0; i < attrs.getLength(); i++) {
					printNode(attrs.item(i), count + 1);
				}
			/*
			 * node.getChildNodes()方法返回 包含node节点的所有子节点的 NodeList。
			 */
			NodeList childNodes = node.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				printNode(childNodes.item(i), count + 1);
			}
		}
	}

	public static String getFileName() {
		return fileName;
	}

	public static void setFileName(String fileName) {
		ImportSysData.fileName = fileName;
	}
}
