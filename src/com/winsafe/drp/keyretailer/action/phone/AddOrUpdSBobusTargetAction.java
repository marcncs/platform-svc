package com.winsafe.drp.keyretailer.action.phone;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppUsers;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.keyretailer.dao.AppSBonusAccount;
import com.winsafe.drp.keyretailer.dao.AppSBonusConfig;
import com.winsafe.drp.keyretailer.dao.AppSBonusSetting;
import com.winsafe.drp.keyretailer.dao.AppSBonusTarget;
import com.winsafe.drp.keyretailer.pojo.SBonusAccount;
import com.winsafe.drp.keyretailer.pojo.SBonusConfig;
import com.winsafe.drp.keyretailer.pojo.SBonusSetting;
import com.winsafe.drp.keyretailer.pojo.SBonusTarget;
import com.winsafe.drp.keyretailer.service.SBonusService;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.util.ArithDouble;
import com.winsafe.drp.util.Constants;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.hbm.entity.HibernateUtil;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.StringUtil;


public class AddOrUpdSBobusTargetAction extends BaseAction {
	private Logger logger = Logger.getLogger(AddOrUpdSBobusTargetAction.class);
	
	private AppUsers appUsers = new AppUsers();
	
	private AppSBonusTarget ast = new AppSBonusTarget();
	private AppSBonusAccount asa = new AppSBonusAccount();
	private AppOrgan ao = new AppOrgan();
	private AppSBonusSetting ass = new AppSBonusSetting();
	private AppProduct appProduct = new AppProduct();
	private AppSBonusConfig appSBonusConfig = new AppSBonusConfig();
	
	Map<String, String> unitMap = null;

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String year = request.getParameter("year"); 
			SBonusConfig sbc = appSBonusConfig.getPeriodConfig(year, DateUtil.getCurrentDateString(), 4);
			if(sbc == null) {
				ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "设置失败,目前不在积分目标设置周期内");
				return null;
			}
			
			String username = request.getParameter("Username"); //登陆名
			String organId = request.getParameter("organId"); 
			String productNamesStr = request.getParameter("productName").substring(1); 
			String specsStr = request.getParameter("spec").substring(1); 
			String amountsStr = request.getParameter("amount").substring(1); 
			
			String[] productNames = productNamesStr.split(","); 
			String[] specs = specsStr.split(","); 
			String[] amounts = amountsStr.split(","); 
			StringBuffer errMsg = new StringBuffer();
			
			// 判断用户是否存在
			UsersBean loginUsers = appUsers.getUsersBeanByLoginname(username);
			
			for(int i = 0; i < productNames.length; i++) {
				if(StringUtil.isEmpty(amounts[i]) 
						|| "0".equals(amounts[i])) {
					continue;
				}
				SBonusTarget sbt = ast.getSBonusTarget(loginUsers.getMakeorganid(), organId,productNames[i], specs[i], year);
				Organ o = ao.getOrganByID(organId);
				if(sbt == null) { //新增
					SBonusTarget localSbt = ast.getSBonusTarget(organId, productNames[i], specs[i], year);
					if(localSbt != null) {
						errMsg.append("机构"+o.getOrganname()+"对应产品"+sbt.getProductName()+"规格"+sbt.getSpec()+"的目标设置失败,其他机构已设置过这个产品\r\n");
						continue;
					}
					SBonusAccount  sba= asa.getSBonusAccountByOrganId(organId);
					Product product = appProduct.getProductByNameAndSpec(productNames[i], specs[i]);
					if (sba==null) {
						errMsg.append("机构"+o.getOrganname()+"的目标设置失败,该进货机构不在积分范围内\r\n");
						continue;
					}
					sbt = new SBonusTarget();
					sbt.setAccountId(sba.getAccountId());
					sbt.setModifiedDate(new Date());
					sbt.setFromOrganId(loginUsers.getMakeorganid());
					sbt.setToOrganId(organId);
					sbt.setYear(Integer.parseInt(year));
					sbt.setProductName(productNames[i]);
					sbt.setSpec(specs[i]);
					sbt.setTargetAmount(Double.parseDouble(amounts[i]));
					double bonusPoint = 0;
					if (o!=null) {
						SBonusSetting sbs =  ass.getSBonusSetting(sbt.getProductName(), sbt.getSpec(), o.getOrganModel().equals(DealerType.BKD.getValue())?DealerType.BKD.getValue():DealerType.BKR.getValue());
						if (sbs!=null) {
							bonusPoint = ArithDouble.mul(sbt.getTargetAmount(), sbs.getBonusPoint());
						} else {
							errMsg.append("机构"+o.getOrganname()+"对应产品"+sbt.getProductName()+"规格"+sbt.getSpec()+"的目标设置失败,产品对应的积分设置信息未找到\r\n");
							continue;
						}
					}
					sbt.setBonusPoint(bonusPoint);
					sbt.setCountUnit(product.getCountunit());
					sbt.setIsSupported(0);
					ast.addSBonusTarget(sbt);
				} else { //更新
					/*if(!loginUsers.getMakeorganid().equals(sbt.getFromOrganId())) {
						errMsg.append("机构"+o.getOrganname()+"对应产品"+sbt.getProductName()+"规格"+sbt.getSpec()+"的目标设置失败,其他机构已设置过这个产品\r\n");
						continue;
					}*/
					sbt.setTargetAmount(Double.parseDouble(amounts[i]));
					double bonusPoint = 0;
					if (o!=null) {
						SBonusSetting sbs =  ass.getSBonusSetting(sbt.getProductName(), sbt.getSpec(), o.getOrganModel().equals(DealerType.BKD.getValue())?DealerType.BKD.getValue():DealerType.BKR.getValue());
						if (sbs!=null) {
							bonusPoint = ArithDouble.mul(sbt.getTargetAmount(), sbs.getBonusPoint());
						} else {
							errMsg.append("机构"+o.getOrganname()+"对应产品"+sbt.getProductName()+"规格"+sbt.getSpec()+"的目标设置失败,产品对应的积分设置信息未找到\r\n");
							continue;
						}
					}
					sbt.setBonusPoint(bonusPoint);
					ast.updSBonusTarget(sbt);
				}
			}
			
			// 如果要下载的信息不为空，则进行下载操作
			ResponseUtil.writeJsonMsg(response, errMsg.length() > 0 ? Constants.CODE_ERROR :Constants.CODE_SUCCESS, errMsg.length() > 0 ? errMsg.toString() : Constants.CODE_SUCCESS_MSG,
					year+" "+organId+" "+productNamesStr+" "+specsStr+" "+amountsStr,loginUsers.getUserid(),"APP","下级客户目标设置",true);
		} catch (Exception e) {
			logger.error("", e);
			HibernateUtil.rollbackTransaction();
			ResponseUtil.writeJsonMsg(response, Constants.CODE_ERROR, "系统异常");
		}
		return null;
	}
}
