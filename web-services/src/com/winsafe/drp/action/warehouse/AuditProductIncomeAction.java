package com.winsafe.drp.action.warehouse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppFUnit;
import com.winsafe.drp.dao.AppIdcode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductIncome;
import com.winsafe.drp.dao.AppProductIncomeDetail;
import com.winsafe.drp.dao.AppProductIncomeIdcode;
import com.winsafe.drp.dao.AppProductStockpile;
import com.winsafe.drp.dao.AppSystemResource;
import com.winsafe.drp.dao.Idcode;
import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.ProductIncomeDetail;
import com.winsafe.drp.dao.ProductIncomeIdcode;
import com.winsafe.drp.dao.ProductStockpile;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.drp.server.ProductCostService;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AuditProductIncomeAction extends BaseAction {
	private AppFUnit af = new AppFUnit();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		super.initdata(request);
		try {
			// 获取单据编号
			String piid = request.getParameter("PIID");
			// 获取type类型
			String type = request.getParameter("type");
			// 单据服务类
			AppProductIncome api = new AppProductIncome();
			// 单据条形码服务类
			AppProductIncomeIdcode apidcode = new AppProductIncomeIdcode();
			// 入库单详情dao
			AppProductIncomeDetail apid = new AppProductIncomeDetail();
			// 根据单据编号获取单据实体
			ProductIncome pi = api.getProductIncomeByID(piid);
			AppFUnit af = new AppFUnit();
			// 如果单据已复核，则提示相应信息
			if (pi.getIsaudit() == 1) {
				request.setAttribute("result", "databases.record.audit");
				return new ActionForward("/sys/lockrecordclose.jsp");
			}
			List<ProductIncomeDetail> pidetails = apid
					.getProductIncomeDetailByPbId(piid);

			double convertQuantity = 0.0;
			if (type.equals("1")) {
				// 显示确认页面

				for (ProductIncomeDetail pid : pidetails) {
					// 每箱多少kg
					convertQuantity = af.getXQuantity(pid.getProductid(), 1);
					pid.setConvertQuantity(convertQuantity);
					if (pid.getUnitid() == 18) {
						pid.setBoxQuantity(ArithDouble.div(pid.getQuantity(),
								convertQuantity));
					} else {
						pid.setBoxQuantity(pid.getQuantity());
					}
				}
				// 弹出实际数量确认页面
				request.setAttribute("piid", pi.getId());
				request.setAttribute("als", pidetails);
				return mapping.findForward("confirmQuantity");

			} else if (type.equals("2")) {
				// 获取所有指定单据编号的条形码
				List<ProductIncomeIdcode> idcodelist = apidcode
						.getProductIncomeIdcodeByPiid(piid, 1);
				HibernateUtil.currentSession(true);

				String confirmQuantity[] = request
						.getParameterValues("confirmquantity");
				for (int i = 0; i < pidetails.size(); i++) {
					pidetails.get(i).setConfirmQuantity(
							Double.valueOf(confirmQuantity[i]));
				}
				// 记录条码
				addIdcode(pi);
				// 设置单据已复核
				api.updIsAudit(piid, userid, 1);

				// List<ProductIncomeIdcode> idlist =
				// apidcode.getProductIncomeIdcodeByPiid(piid);

				// 修改库存量
				// 如果是自动生成的单据
				// if(pi.getIsAutoCreate()==1){ //国外粉
				// addProductStockpile(idlist, pi.getWarehouseid());
				// }else{
				// 手动新增的单据
				addProductStockpileByPIDetail(pi, pidetails, pi
						.getWarehouseid());
				// }

				HibernateUtil.commitTransaction();

				request.setAttribute("result", "databases.audit.success");
				DBUserLog
						.addUserLog(userid, 7, "入库>>产成品入库>>复核产成品入库,编号：" + piid);

				/*
				 * ResXmlBean resXmlBean=new ResXmlBean();
				 * resXmlBean.setCgeneralhid
				 * (api.getProductIncomeNccodeByID(piid)); resXmlBean
				 * .setState(ResourceBundle .getBundle(
				 * "com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
				 * .getString("xml_state_success")); resXmlBean
				 * .setDetail(ResourceBundle .getBundle(
				 * "com.winsafe.drp.util.fileListener.UFIDA.ImportDataFormat")
				 * .getString("success"));
				 * ImportSysData.creatResponseXml(resXmlBean);
				 */

				return mapping.findForward("audit");
			}

		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
			e.printStackTrace();
		} finally {
			HibernateUtil.closeSession();
		}
		return new ActionForward(mapping.getInput());
	}

	/**
	 * 增加产品库存量 Create Time: Oct 9, 2011 2:50:46 PM
	 * 
	 * @param idlist
	 *            跟单据关联的条码集合
	 * @param warehouseid
	 *            仓库Id
	 * @throws Exception
	 * @author dufazuo
	 */
	/*
	 * private void addProductStockpile(List<ProductIncomeIdcode> idlist, String
	 * warehouseid) throws Exception { AppProductStockpile aps = new
	 * AppProductStockpile(); AppProduct ap = new AppProduct(); ProductStockpile
	 * ps = null; for (ProductIncomeIdcode idcode : idlist) { ps = new
	 * ProductStockpile();
	 * 
	 * 
	 * 
	 * ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("product_stockpile"
	 * , 0, ""))); ps.setProductid(idcode.getProductid());
	 * 
	 * ps.setCountunit(ap.getProductByID(ps.getProductid()).getCountunit());
	 * ps.setBatch(idcode.getBatch());
	 * ps.setProducedate(idcode.getProducedate());
	 * ps.setValidate(idcode.getValidate()); ps.setWarehouseid(warehouseid);
	 * ps.setWarehousebit(idcode.getWarehousebit());
	 * ps.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
	 * //设置产品检验状态为合格 ps.setVerifyStatus(1);
	 * //新增库存记录（如果要新增的库存记录已经存在则不作新增操作，否则新增，注意，新增时先将库存量设置为零，因为下面的更新操作会加库存量）
	 * aps.addProductByPurchaseIncome2(ps); //更新库存量
	 * aps.inProductStockpileTotalQuantity
	 * (ps.getProductid(),idcode.getUnitid(),ps.getBatch(),
	 * af.getQuantity(idcode.getProductid(), idcode.getUnitid(),
	 * idcode.getQuantity()), ps.getWarehouseid(), idcode.getWarehousebit(),
	 * idcode.getPiid(), "产成品-入库"); //更新库存中产品的成本
	 * ProductCostService.updateCost(warehouseid, ps.getProductid(),
	 * ps.getBatch()); } }
	 */
	/**
	 * 依据入库单详情 增加库存量
	 * 
	 * @param idlist
	 * @param warehouseid
	 * @throws Exception
	 * @author wenping
	 * @CreateTime Jun 28, 2012 1:13:02 PM
	 */
	private void addProductStockpileByPIDetail(ProductIncome pi,
			List<ProductIncomeDetail> pidetails, String warehouseid)
			throws Exception {
		AppSystemResource appSR = new AppSystemResource();
		// //判断入库单复核时是否操作库存
		// if(!appSR.isTrue("systemParam", "productIncome")){
		// return;
		// }

		AppProductStockpile aps = new AppProductStockpile();
		AppProduct ap = new AppProduct();
		ProductStockpile ps = null;
		for (ProductIncomeDetail pid : pidetails) {
			ps = new ProductStockpile();

			ps.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName(
					"product_stockpile", 0, "")));
			ps.setProductid(pid.getProductid());
			ps
					.setCountunit(ap.getProductByID(ps.getProductid())
							.getCountunit());
			ps.setBatch(pid.getBatch());
			ps.setProducedate("");
			ps.setVad("");
			ps.setWarehouseid(warehouseid);
			ps.setWarehousebit("000");
			ps.setMakedate(DateUtil.StringToDatetime(DateUtil
					.getCurrentDateTime()));
			ps.setVerifyStatus(0);
			// 新增库存记录（如果要新增的库存记录已经存在则不作新增操作，否则新增，注意，新增时先将库存量设置为零，因为下面的更新操作会加库存量）
			aps.addProductByPurchaseIncome(ps);
			// 更新库存量
			aps.inProductStockpileTotalQuantity(ps.getProductid(), 1, ps
					.getBatch(), pid.getConfirmQuantity(), ps.getWarehouseid(),
					ps.getWarehousebit(), pi.getId(), "产成品-入库");
			// 更新库存中产品的成本
			ProductCostService.updateCost(warehouseid, ps.getProductid(), ps
					.getBatch());
		}
	}

	/**
	 * 记录单据的条码 Create Time: Oct 9, 2011 2:55:06 PM
	 * 
	 * @param pi
	 *            单据
	 * @param idcodelist
	 *            单据的条码
	 * @throws Exception
	 * @author dufazuo
	 */
	private void addIdcode(ProductIncome pi,
			List<ProductIncomeIdcode> idcodelist) throws Exception {
		AppIdcode appidcode = new AppIdcode();
		AppProduct ap = new AppProduct();
		Idcode ic = null;
		for (ProductIncomeIdcode wi : idcodelist) {
			ic = new Idcode();
			ic.setIdcode(wi.getIdcode());
			ic.setProductid(wi.getProductid());
			ic.setProductname(ap.getProductNameByID(ic.getProductid()));
			ic.setBatch(wi.getBatch());
			ic.setProducedate(wi.getProducedate());
			ic.setVad(wi.getVad());
			ic.setLcode(wi.getLcode());
			ic.setStartno(wi.getStartno());
			ic.setEndno(wi.getEndno());
			ic.setUnitid(wi.getUnitid());
			ic.setQuantity(wi.getQuantity());
			ic.setFquantity(wi.getPackquantity());
			ic.setPackquantity(wi.getPackquantity());
			ic.setIsuse(1);
			ic.setIsout(0);
			ic.setBillid(wi.getPiid());
			ic.setIdbilltype(2);
			ic.setMakeorganid(pi.getMakeorganid());
			ic.setWarehouseid(pi.getWarehouseid());
			ic.setWarehousebit(wi.getWarehousebit());
			ic.setProvideid("");
			ic.setProvidename("");
			ic.setBoxCode(wi.getBoxCode());
			ic.setCartonCode(wi.getCartonCode());
			ic.setPalletCode(wi.getPalletCode());
			ic.setMakedate(wi.getMakedate());
			ic.setNcLotNo(wi.getNcLotNo());
			// 记录条码（如果要记录的条形码已经存在则不作更新处理，否则新增一条记录）
			appidcode.addIdcode2(ic);
			// 更新（如果上面没有做新增操作，则更新数据库中相应的记录）
			appidcode.updIsUse(ic.getIdcode(), ic.getMakeorganid(), ic
					.getWarehouseid(), ic.getWarehousebit(), 1, 0);
		}
	}

	/**
	 * 记录单据的条码 Create Time: Oct 9, 2011 2:55:06 PM
	 * 
	 * @param pi
	 *            单据
	 * @param idcodelist
	 *            单据的条码
	 * @throws Exception
	 * @author dufazuo
	 */
	private void addIdcode(ProductIncome pi) throws Exception {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("insert into idcode(" +
				"idcode,productid,productname,batch,producedate,vad,lcode,startno,endno,unitid,quantity," +
				"fquantity,packquantity,isuse,isout,billid,idbilltype,makeorganid,WarehouseID,warehousebit," +
				"ProvideID,ProvideName, boxcode, cartonCode, palletCode , ncLotNo,MakeDate,PColumn)" +
				" select ");
		//idcode
		sqlBuffer.append("pii.idcode,");
		//productid
		sqlBuffer.append("pii.PRODUCTID,");
		//productname
		sqlBuffer.append("p.PRODUCTNAME,");
		//batch
		sqlBuffer.append("pii.BATCH,");
		//producedate
		sqlBuffer.append("pii.PRODUCEDATE,");
		//vad
		sqlBuffer.append("pii.vad,");
		//lcode
		sqlBuffer.append("pii.LCODE,");
		//startno
		sqlBuffer.append("pii.STARTNO,");
		//endno
		sqlBuffer.append("pii.ENDNO,");
		//unitid
		sqlBuffer.append("pii.UNITID,");
		//quantity
		sqlBuffer.append("pii.QUANTITY,");
		//fquantity
		sqlBuffer.append("pii.PACKQUANTITY,");
		//packquantity
		sqlBuffer.append("pii.PACKQUANTITY,");
		//isuse
		sqlBuffer.append("1,");
		//isout
		sqlBuffer.append("0,");
		//billid
		sqlBuffer.append("pii.PIID,");
		//idbilltype
		sqlBuffer.append("2,");
		//makeorganid
		sqlBuffer.append("pi.MAKEORGANID,");
		//warehouseid
		sqlBuffer.append("pi.WAREHOUSEID,");
		//warehousebit
		sqlBuffer.append("pii.WAREHOUSEBIT,");
		//provideid
		sqlBuffer.append("'',");
		//providename
		sqlBuffer.append("'',");
		//boxcode
		sqlBuffer.append("pii.BOXCODE,");
		//cartoncode
		sqlBuffer.append("pii.CARTONCODE,");
		//palletcode
		sqlBuffer.append("pii.PALLETCODE,");
		//nclotno
		sqlBuffer.append("pii.NCLOTNO,");
		//makedate
		sqlBuffer.append("sysdate,");
		//pcolumn
		sqlBuffer.append("to_char(sysdate,'MM')");
		sqlBuffer.append(" from product_income_idcode pii, product p ,product_income pi " +
				" where pii.ISIDCODE = 1  and  pii.PRODUCTID = p.id and pii.piid = pi.id and pii.piid = '" + pi.getId()+"'");
		System.out.println(sqlBuffer.toString());
		EntityManager.updateOrdelete(sqlBuffer.toString());
	}

}
