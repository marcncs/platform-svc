package com.winsafe.drp.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.winsafe.drp.dao.ProductIncome;
import com.winsafe.drp.dao.ReceiveIncome;
import com.winsafe.drp.dao.SampleBill;
import com.winsafe.drp.dao.StockAlterMove;
import com.winsafe.drp.dao.StockMove;
import com.winsafe.drp.dao.SupplySaleMove;
import com.winsafe.drp.dao.TakeBill;
import com.winsafe.drp.dao.TakeTicket;
import com.winsafe.drp.entity.EntityManager;
import com.winsafe.hbm.util.Pager;

public class RuleUtil {

    /**
     * 主要功能：根据仓库权限过滤列表
     * 
     * @param request
     * @param billList
     *                单据列表
     * @param userId
     *                登录人
     * @return 过滤后的列表
     */
    public static List filterBillByWHRule(HttpServletRequest request,
	    List billList, Integer userId) {
	List returnList = new ArrayList();
	for (Object object : billList) {
	    // 采购出库单据
	    if (object instanceof TakeBill) {

		TakeBill takeBill = (TakeBill) object;
		// 检货出库的处理判断
		takeBill = getTakeBillByRule(takeBill, userId);
		if (takeBill != null) {
		    returnList.add(takeBill);
		}
	   
	    // 产成品入库
	    } else if (object instanceof ProductIncome) {
		 ProductIncome productIncome = (ProductIncome)object;
		 //产成品入库的处理判断
		 productIncome = getProductIncomeByRule(productIncome, userId);
		 if(productIncome != null){
		     returnList.add(productIncome);
		 }
            //订购入库
	    } else if (object instanceof StockAlterMove){
		StockAlterMove stockAlterMove = (StockAlterMove)object;
		stockAlterMove = getStockAlterMoveByRule(stockAlterMove, userId);
		if(stockAlterMove != null){
		    returnList.add(stockAlterMove);
		}
	    //转仓签收
	    } else if (object instanceof StockMove){
		StockMove stockMove = (StockMove)object;
		stockMove = getStockMoveByRule(stockMove, userId);
		if(stockMove != null){
		    returnList.add(stockMove);
		}
	    //代销签收
	    }else if (object instanceof SupplySaleMove){
		SupplySaleMove supplySaleMove = (SupplySaleMove)object;
		supplySaleMove = getSupplySaleMoveByRule(supplySaleMove, userId);
		if(supplySaleMove != null){
		    returnList.add(supplySaleMove);
		}
	    //样品出库
	    } else if (object instanceof SampleBill){
		SampleBill sampleBill = (SampleBill)object;
		sampleBill = getSampleBillByRule(sampleBill, userId);
		if(sampleBill!= null){
		    returnList.add(sampleBill);
		}
           
	    }else if(object instanceof  ReceiveIncome){
	    	//经销商签收入库
	    	ReceiveIncome receiveIncome = (ReceiveIncome)object;
			 //产成品入库的处理判断
	    	receiveIncome = getReceiveIncomeByRule(receiveIncome, userId);
			 if(receiveIncome != null){
			     returnList.add(receiveIncome);
			 }
			 // 其他单据(可扩展)
	    }else if (1 == 1){
		
	    }
	}
	//手动设置分页 
	returnList = Pager.initPagerManually(request, returnList);
	return returnList;
    }

    /**
     * 主要功能：判断单据是否是用户仓库权限内可见
     * @param takeBill 检货出库
     * @param userId 登陆人ID
     * @return null:不可见;
     */
    private static TakeBill getTakeBillByRule(TakeBill takeBill, Integer userId) {
    	String hql=null;
    	//如果退货 对出库仓库有可见权限
    	if(takeBill.getId().startsWith("OW")){
    		 hql = " from TakeTicket where billno='"
    			+ takeBill.getId()
    			+ "' and warehouseid in (select wv.wid from  WarehouseVisit as wv where wv.userid="+userId+")";
    	}
    	//正常出库  对出库仓库操作权限
    	else{
    		 hql = " from TakeTicket where billno='"
    			+ takeBill.getId()
    			+ "' and warehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
    			+ userId + " and activeFlag = 1)";
    	}
	
	//得到可见的检货小票
	List<TakeTicket> takeTicketList = EntityManager.getAllByHql(hql);
	//如果存在可见小票,则能显示此检货出库单
	if (takeTicketList.size() > 0) {
	    return takeBill;
	} else {
	    return null;
	}

    }
    
    /**
     * 主要功能：根据仓库权限得到产成品对象
     * @param productIncome 产成品入库对象
     * @param userId 登陆人ID
     * @return null:无权限
     */
    private static ProductIncome getProductIncomeByRule(ProductIncome productIncome, Integer userId) {
	String hql = " from ProductIncome where id = '"+ productIncome.getId()+"' and warehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
		+ userId + " and activeFlag = 1)";

	List<ProductIncome> list = EntityManager.getAllByHql(hql);
	//如果有查询数据,则说明此条记录有仓库权限
	if(list.size() > 0){
	    return productIncome;
	}else{
	    return null;
	}
    }
    
    /**
     * 主要功能：根据仓库权限得到订购入库
     * @param stockAlterMove 订购入库对象
     * @param userId 登陆人ID
     * @return null:无权限
     */
    private static StockAlterMove getStockAlterMoveByRule(StockAlterMove stockAlterMove, Integer userId){
	String hql = " from StockAlterMove where id = '"+ stockAlterMove.getId()+"' and outwarehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
	+ userId + " and activeFlag = 1)";
         
        List<StockAlterMove> list = EntityManager.getAllByHql(hql);
        //如果有查询数据,则说明此条记录有仓库权限
        if(list.size() > 0){
            return stockAlterMove;
        }else{
            return null;
        }  
    }
    
    /**
     * 主要功能：根据仓库权限得到转仓签收对象
     * @param stockMove 转仓签收对象
     * @param userId 登陆人Id
     * @return null:无权限
     */
    private static StockMove getStockMoveByRule(StockMove stockMove, Integer userId){
	String hql = " from StockMove where id = '"+ stockMove.getId()+"' and inwarehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
	+ userId + " and activeFlag = 1)";
         
        List<StockMove> list = EntityManager.getAllByHql(hql);
        //如果有查询数据,则说明此条记录有仓库权限
        if(list.size() > 0){
            return stockMove;
        }else{
            return null;
        }
    }
    
  
    /**
     * 主要功能：根据仓库权限得到代销签收对象
     * @param supplySaleMove 代销对象
     * @param userId 登陆人
     * @return null:无权限
     */
    private static SupplySaleMove getSupplySaleMoveByRule(SupplySaleMove supplySaleMove, Integer userId){
	String hql = " from SupplySaleMove where id = '"+ supplySaleMove.getId()+"' and inwarehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
	+ userId + " and activeFlag = 1)";
         
        List<SupplySaleMove> list = EntityManager.getAllByHql(hql);
        //如果有查询数据,则说明此条记录有仓库权限
        if(list.size() > 0){
            return supplySaleMove;
        }else{
            return null;
        }  
    }
    
    /**
     * 主要功能：根据仓库权限得到样品仓库对象
     * @param sampleBill
     * @param userId
     * @return
     */
    private static SampleBill getSampleBillByRule(SampleBill sampleBill, Integer userId){
	String hql = " from SampleBill where id = '"+ sampleBill.getId()+"' and warehouseID in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
	+ userId + " and activeFlag = 1)";
         
        List<SampleBill> list = EntityManager.getAllByHql(hql);
        //如果有查询数据,则说明此条记录有仓库权限
        if(list.size() > 0){
            return sampleBill;
        }else{
            return null;
        }  
    }
    
    /**
     * 主要功能：根据仓库权限得到经销商签收入库对象
     * @param productIncome 产成品入库对象
     * @param userId 登陆人ID
     * @return null:无权限
     */
    private static ReceiveIncome getReceiveIncomeByRule(ReceiveIncome receiveIncome, Integer userId) {
    	String hql = " from ReceiveIncome where id = '"+ receiveIncome.getId()+"' and warehouseid in (select ruw.warehouseId from RuleUserWh ruw where ruw.userId = "
    		+ userId + " and activeFlag = 1)";

    	List<ReceiveIncome> list = EntityManager.getAllByHql(hql);
    	//如果有查询数据,则说明此条记录有仓库权限
    	if(list.size() > 0){
    	    return receiveIncome;
    	}else{
    	    return null;
    	}
        }
}
