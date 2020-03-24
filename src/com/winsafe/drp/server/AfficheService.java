package com.winsafe.drp.server;

import com.winsafe.drp.dao.Affiche;
import com.winsafe.drp.dao.AppAffiche;
import com.winsafe.drp.dao.AppOrgan;
import com.winsafe.drp.dao.Organ;
import com.winsafe.drp.keyretailer.metadata.AfficheType;
import com.winsafe.drp.keyretailer.util.BonusAfficheMsg;
import com.winsafe.drp.metadata.DealerType;
import com.winsafe.drp.metadata.OrganType;
import com.winsafe.drp.metadata.ValidateStatus;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AfficheService {
	
	private AppAffiche appAffiche = new AppAffiche(); 
	
	public void addCreateAuditRequestAffiche(Organ organ) throws Exception {
		addAuditRequestAffiche(organ, BonusAfficheMsg.AUDIT_CREATE_TITLE,BonusAfficheMsg.getError(BonusAfficheMsg.AUDIT_CREATE, organ.getOrganname()));
	}
	
	public void addUpdateAuditRequestAffiche(Organ organ) throws Exception {
		addAuditRequestAffiche(organ, BonusAfficheMsg.AUDIT_UPDATE_TITLE,BonusAfficheMsg.getError(BonusAfficheMsg.AUDIT_UPDATE, organ.getOrganname()));
	}
	
	public void addAuditRequestAffiche(Organ organ, String title, String content) throws Exception {
		if(OrganType.Dealer.getValue().equals(organ.getOrganType()) 
				&& !DealerType.PD.getValue().equals(organ.getOrganModel())) {
			Affiche affiche = addAffiche(AfficheType.AUDIT, title, content ,null, organ.getId());
			if(DealerType.BKD.getValue().equals(organ.getOrganModel())) {
				//按小区找到对应的ASM审批
				appAffiche.addBKDAfficheUsers(affiche.getId(), organ.getAreas());
			} else {
				if(appAffiche.isSSSRExists(organ.getAreas())) {//若按小区能找到SS/SR,由SS/SR审批
					appAffiche.addBKRAfficheUsers(affiche.getId(), organ.getAreas());
				} else if(appAffiche.isASMExists(organ.getAreas())){//若按小区能找到ASM,由ASM审批
					appAffiche.addBKDAfficheUsers(affiche.getId(), organ.getAreas());
                } else {//若上级机构是PD,由负责该PD的ASM审批
					AppOrgan appOrgan = new AppOrgan(); 
					Organ parentOrgan = appOrgan.getOrganByID(organ.getParentid());
					if(OrganType.Dealer.getValue().equals(parentOrgan.getOrganType())
							&& DealerType.PD.getValue().equals(parentOrgan.getOrganModel())) {
						appAffiche.addBKRAfficheUsersByPD(affiche.getId(), parentOrgan.getId());
					}
				}
			}
		}
	}
	
	public void addAuditResponseAffiche(Organ organ, String msg) throws Exception {
		if(OrganType.Dealer.getValue().equals(organ.getOrganType()) 
				&& !DealerType.PD.getValue().equals(organ.getOrganModel())) {
			ValidateStatus vs = ValidateStatus.parseByValue(organ.getValidatestatus());
			if(DealerType.BKD.getValue().equals(organ.getOrganModel())) {
				addAffiche(AfficheType.AUDIT, BonusAfficheMsg.AUDIT_RESPONSE_TITLE, BonusAfficheMsg.getError(BonusAfficheMsg.AUDIT_RESPONSE, organ.getOrganname(),vs.getName(),msg),organ.getId(),null);
			} else {
				addAffiche(AfficheType.AUDIT, BonusAfficheMsg.AUDIT_RESPONSE_TITLE, BonusAfficheMsg.getError(BonusAfficheMsg.AUDIT_RESPONSE, organ.getOrganname(),vs.getName(),msg),organ.getParentid(),null);
			}
		}
	}
	
	public Affiche addAffiche(AfficheType afficheType, String title, String content, String organId, String makeOrganId) throws Exception { 
		Integer id = Integer.valueOf(MakeCode.getExcIDByRandomTableName("affiche", 0, ""));
		Affiche affiche = new Affiche();
		affiche.setId(id);
		affiche.setAffichecontent(content);
		affiche.setAfficheorganid(organId);
		affiche.setAffichetitle(title);
		affiche.setAffichetype(afficheType.getValue());
		affiche.setMakeorganid(makeOrganId);
		affiche.setMakedate(DateUtil.getCurrentDate());
		affiche.setIsPublish("1");
		appAffiche.addAffiche(affiche);
		return affiche;
	}
}
