package com.winsafe.drp.action.machin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.dao.AppCustomerMatchOrder;
import com.winsafe.drp.dao.AppSuggestInspect;
import com.winsafe.drp.dao.AppSuggestInspectDetail;
import com.winsafe.drp.dao.CustomerMatchOrder;
import com.winsafe.drp.dao.SuggestInspect;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.drp.util.DBUserLog;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

/**
 * 合并单号
 * 
 * @author Andy.liu
 * 
 */
public class MergeSuggestInspectAction extends Action {
	AppSuggestInspect asi = new AppSuggestInspect();
	AppSuggestInspectDetail asid = new AppSuggestInspectDetail();
	AppCustomerMatchOrder acmo = new AppCustomerMatchOrder();
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String ids = request.getParameter("ids");
		String type = request.getParameter("type");
		try {
			// type=1 是选择合并，type = 0是全部合并
			if ("1".equals(type)) {
				if (ids.indexOf("on") > 1) {
					ids = ids.substring(0, ids.length() - 3);
				}
//				// 一张单据无法合并
//				if (ids.lastIndexOf(",") == -1) {
//					request.setAttribute("result",
//							"databases.merge.insufficient");
//					return mapping.findForward("success");
//				}
				/*
				 * 首先获取ids下的所有单据 遍历集合，判断客户、目标仓库、制单时间是否一致，一致则将ID放入集合保存
				 */
				// 如果合并的单据包含拣货建议单，则删除该拣货建议单，并修改其下的单据状态为未合并
				String sql = " where si.id in (" + ids + ") ";
				List<Long> longs = new ArrayList<Long>();
				List<SuggestInspect> checkList = asi.getSiByIds(sql);
				for (SuggestInspect si : checkList) {
					if("2000001".equals(si.getTypeId())){
						String whereSql = " where si.mergeId = " + si.getId();
						List<SuggestInspect> list = asi.getSiByIds(whereSql);
						for (SuggestInspect suggestInspect : list) {
							suggestInspect.setIsMerge(0);
							suggestInspect.setMergeId(null);
							asi.updateSi(suggestInspect);   //修改状态
							longs.add(suggestInspect.getId());
						}
						//删除合并单详情
						asid.deleteBySiid(si.getSiid());
						asi.deleteSi(si);    //删除合并单
					}else{
						longs.add(si.getId());
					}
				}
				//将longs拼接成SQL语句的In
				ids = getsiid(longs);
				String whereSql = " where si.id in ("
						+ ids
						+ ") and si.isMerge = 0 and si.isOut = 0  and isRemove = 0";
				List<SuggestInspect> list = asi.getSiByIds(whereSql);
				// 合并方法，
				mergeSuggestInspect(list);
			} else {
				// 全部合并之前，删除未出库的合并单,并修改所有未出库单据的状态为未合并，
				asi.updateSiByUnMerge("");  //修改所有合并单据状态为未合并
				asid.deleteAllMergeDetail(); //删除所有未出库合并单的详情
				asi.deleteAllMergeSi();     //删除所有未出库的合并单
				String whereSql = " where si.isMerge = 0 and si.isOut = 0  and isRemove = 0";
				List<SuggestInspect> list = asi.getSiByIds(whereSql);
//				if (list.size() < 2) {
//					request.setAttribute("result",
//							"databases.merge.insufficient");
//					return mapping.findForward("success");
//				}
				mergeSuggestInspect(list);
			}

			request.setAttribute("result", "databases.merge.success");
			UsersBean users = UserManager.getUser(request);
			Integer userid = users.getUserid();
			DBUserLog.addUserLog(userid, 11, "合并拣货建议单");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "databases.merge.fail");
		}
		return mapping.findForward("success");
	}

	/**
	 * 合并规则
	 * @param list
	 * @throws Exception
	 */
	public void mergeSuggestInspect(List<SuggestInspect> list) throws Exception {
		Map<String, List<Long[]>> map = new HashMap<String, List<Long[]>>();
		String code = MakeCode.getExcIDByRandomTableName("suggestinspect", 2, "SI");
		List<SuggestInspect> siList = new ArrayList<SuggestInspect>();
		for (SuggestInspect si : list) {
			//合并原则为：发货仓库和时间一致
			String key = si.getDisWareHouseName()
					+ DateUtil.formatDate(si.getMakeDate());
			if (map.containsKey(key)) {
				List<Long[]> ids = map.get(key);
				Long[] longs = ids.get(0);
				ids.add(new Long[] { si.getId(), longs[1] });
			} else {
				map.put(key, new ArrayList<Long[]>());
				SuggestInspect resi = createSi(si);
				map.get(key).add(new Long[] { si.getId(), resi.getId() });
				siList.add(resi);
			}
			
		}
		//修改单据的状态为已合并
		for (String key : map.keySet()) {
			List<Long[]> ids = map.get(key);
			for (Long[] longs : ids) {
				asi.updateSiByMerge(longs[0], longs[1]);
			}
		}
		//合并后的单据编号   SI201404100001_1/2
		//按照客户优先级进行排序
		int j = 1;
		List<Map.Entry<String, SuggestInspect>> mappingList = orderSi(siList);
		for (Map.Entry<String, SuggestInspect> entry : mappingList) {
			SuggestInspect si = entry.getValue();
			si.setSiid(code+"_"+(j)+"/"+siList.size());
			asi.updateSi(si);//修改单据编号
			//计算产品总数，插入详情表
			asid.addMergeDetailBySiid(si.getId(), si.getSiid());
			j++;
		}

	}

	/**
	 * 生成拣货建议单（合并后的单据）
	 * @param si
	 * @return
	 * @throws Exception
	 */
	public SuggestInspect createSi(SuggestInspect si) throws Exception {
		SuggestInspect createsi = new SuggestInspect();
		createsi.setTypeId("2000001");
		createsi.setTypeName("拣货建议单");
		createsi.setSiid("");
		createsi.setDisWareHouseName(si.getDisWareHouseName());
		createsi.setCustomerCode(si.getCustomerCode());
		createsi.setSouWareHouseName(si.getSouWareHouseName());
		createsi.setMakeName("系统管理员");
		createsi.setMakeDate(si.getMakeDate());
		createsi.setIsMerge(0);
		createsi.setIsPost(si.getIsPost());
		createsi.setIsRemove(0);
		createsi.setIsOut(0);
		createsi.setCreateDate(DateUtil.getCurrentDate());
		asi.addSuggestInspect(createsi);
		return asi.getSiByOther(createsi.getCustomerCode(),
				createsi.getDisWareHouseName(), createsi.getMakeDate());
	}

	/**
	 * 拼接id
	 * @param longs
	 * @return
	 * @throws Exception
	 */
	public String getsiid(List<Long> longs) throws Exception {
		String ids = "";
		for (Long long1 : longs) {
			ids+=long1.toString()+",";
		}
		return ids.substring(0, ids.length()-1);
	}
	
	/**
	 * 根据客户配货顺序表排序
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<Map.Entry<String, SuggestInspect>> orderSi(List<SuggestInspect> list) throws Exception{
		Map<String, SuggestInspect> map = new LinkedHashMap<String, SuggestInspect>();
		int j = 1;
		for (SuggestInspect si : list) {
			//获取客户配货顺序
			CustomerMatchOrder cmo = acmo.getCmoBySiName(si.getDisWareHouseName());
			map.put(cmo.getMatchorder()+","+j, si);
			j++;
		}
		//对集合排序
		List<Map.Entry<String, SuggestInspect>> mappingList = new ArrayList<Map.Entry<String, SuggestInspect>>(map.entrySet());
		Collections.sort(mappingList,new Comparator<Map.Entry<String, SuggestInspect>>() {
			@Override
			public int compare(Map.Entry<String, SuggestInspect> o1,
					Map.Entry<String, SuggestInspect> o2) {
				String o1key = o1.getKey();
				String o2key = o2.getKey();
				Integer int1key = Integer.valueOf(o1key.substring(0, o1key.indexOf(",")));
				Integer int2key = Integer.valueOf(o2key.substring(0, o2key.indexOf(",")));
				return int1key.compareTo(int2key);
			}
		});
		return mappingList;
		
	}
	
}
