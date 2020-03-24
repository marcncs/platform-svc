package com.winsafe.drp.service;

import java.util.List;

import com.winsafe.drp.dao.AppMoveApply;
import com.winsafe.drp.dao.MoveApply;
import com.winsafe.drp.metadata.StockMoveConfirmStatus;
import com.winsafe.hbm.util.StringUtil;

public class MoveApplyServices { 
	
	private AppMoveApply appMoveApply = new AppMoveApply();
	
	public String getApprovers(MoveApply ma) {
		if(StockMoveConfirmStatus.NOT_AUDITED.getValue().equals(ma.getIsratify())) {
			return getApproversByOrganId(ma.getOutorganid());
		} else if(StockMoveConfirmStatus.OUT_ASM_APPROVED.getValue().equals(ma.getIsratify())) {
			return getApproversByRoleName("转仓审批一");
		} else if(StockMoveConfirmStatus.CHANNEL_MANAGER_APPROVED.getValue().equals(ma.getIsratify())) {
			return getApproversByOrganId(ma.getInorganid());
		} else if(StockMoveConfirmStatus.IN_ASM_APPROVED.getValue().equals(ma.getIsratify())) {
			return getApproversByRoleName("转仓审批二");
		}
		return "";
	}

	private String getApproversByRoleName(String roleName) {
		List<Object[]> userNames = appMoveApply.getApproversByRoleName(roleName);
		return getNameString(userNames);
	}

	private String getApproversByOrganId(String organId) {
		List<Object[]> userNames = appMoveApply.getApproversByOrganId(organId);
		return getNameString(userNames);
	}

	private String getNameString(List<Object[]> userNames) {
		if(userNames.size() > 0) {
			StringBuffer names = new StringBuffer();
			for(Object[] name : userNames) {
				if(!StringUtil.isEmpty((String)name[0])) {
					names.append(","+name[0]);
				} else {
					names.append(","+name[1]);
				}
				
			}
			return names.substring(1);
		}
		return "";
	}
}
