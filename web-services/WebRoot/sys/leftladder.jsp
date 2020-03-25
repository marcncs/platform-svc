<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
.style1 {color: #00009C}
-->
</style></head>
<script language=javascript>

		var old_item;
		
		function open_menu(item_name)
		{
				
			var item = document.all(item_name);

			if(item)
			{

				if (old_item)
					old_item.style.display = "none";
				
				item.style.display = "";
				old_item = item;
			}
	
		}

		function menuOver(src)
		{

		}
		
		function menuOut(src)
		{
			
		}
</script>

<body bgcolor="#F4F2F0" onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">

<script language="javascript">
function ShowContent(obj){
document.all.main.src=obj;
}
</script>
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert("<bean:message key='sys.nouserightkey'/>");}}document.onmousedown=click
</SCRIPT>
<div align="center">
  <table width="100%" border="0" cellpadding="0" cellspacing="0" style="width:117px;border:1px inset">

    <tr> 
      <td  align="center" background="../images/CN/mnu_item_back.jpg" style="height:24px" onClick="open_menu('desk')" onMouseOver="menuOver(this)" onMouseOut="menuOut(this)"><A href="#"><bean:message key="sys.left.MyDesk"/></a></td>
    </tr>
    <tr id="desk" style="display:none"> 
      <td> <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('listcalendaraction.do');"><bean:message key="sys.left.Calendar"/></a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../self/listAfficheAction.do');"><bean:message key="sys.left.Affiche"/></a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../self/listAffichePublisthAction.do');">公告发布</a></td>
          </tr>
          <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('listTaskAction.do');"><bean:message key="sys.left.Task"/></a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../self/receiptTaskAction.do');"><bean:message key="sys.left.ReceiptTask"/></a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('listWorkReportAction.do');"><bean:message key="sys.left.WorkReport"/></a></td>
          </tr>
          <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../self/listPhoneBookSortAction.do');"><bean:message key="sys.left.PhoneBook"/></a></td>
          </tr>
		  <!--<tr> 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../self/waitApproveAction.do');"><bean:message key="sys.left.WaitApprove"/></a></td>
          </tr>
		  <tr> 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../self/toFlowControlAction.do');">产品计划</a></td>
          </tr>-->
        </table></td>
    </tr>



    <tr> 
      <td  align="center" background="../images/CN/mnu_item_back.jpg" style="height:24px" onClick="open_menu('sale')" onMouseOver="menuOver(this)" onMouseOut="menuOut(this)"><A href="#"><bean:message key="sys.left.SaleOffice"/></a></td>
    </tr>
    <tr id="sale" style="display:none"> 
      <td> <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
	  	  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../sales/waitApproveAction.do');">待审阅提交信息</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../sales/listCustomerAction.do');"><bean:message key="sys.left.Customer"/></a></td>
          </tr>
          <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../sales/listSaleOrderAction.do');">销售订单</a></td>
          </tr>
		   <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../sales/listWithdrawAction.do');">销售退货</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../sales/listOutLayAction.do');">销售费用</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../sales/listRegieAction.do');">专卖店资料</a></td>
          </tr>

        </table></td>
    </tr>

<!--

	<tr> 
      <td  align="center" background="../images/CN/mnu_item_back.jpg" style="height:24px" onClick="open_menu('service')" onMouseOver="menuOver(this)" onMouseOut="menuOut(this)"><A href="#"><bean:message key="sys.left.Service"/></a></td>
    </tr>
    <tr id="service" style="display:none"> 
      <td> <table width="100%" height="125" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
	<tr> 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../service/listChangeBillAction.do');"><bean:message key="sys.left.ChangeBill"/></a></td>
          </tr>
          <tr> 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../service/listWithdrawAction.do');"><bean:message key="sys.left.Withdraw"/></a></td>
          </tr>
		  <tr> 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../service/listMaintainLogAction.do');"><bean:message key="sys.left.MaintainLog"/></a></td>
          </tr>
		  <tr> 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../service/listClientServiceAction.do');"><bean:message key="sys.left.ClientService"/></a></td>
          </tr>
		  <tr> 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../service/listOutLayAction.do');"><bean:message key="sys.left.Outlay"/></a></td>
          </tr>
        </table></td>
    </tr>
-->


    <tr> 
      <td  align="center" background="../images/CN/mnu_item_back.jpg" style="height:24px" onClick="open_menu('purchase')" onMouseOver="menuOver(this)" onMouseOut="menuOut(this)"><A href="#"><bean:message key="sys.left.ProductPurchase"/></a></td>
    </tr>
    <tr id="purchase" style="display:none"> 
      <td> <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
	  	  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../purchase/waitApproveAction.do');">待审阅提交信息</a></td>
          </tr>
	  	  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('listProductStructAction.do');">产品资料</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../purchase/listProvideAction.do');"><bean:message key="sys.left.Provide"/></a></td>
          </tr>
         <!-- <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../purchase/stockConstrueAction.do');"><bean:message key="sys.left.StockConstrue"/></a></td>
          </tr>-->
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../purchase/listPurchasePlanAction.do');">采购计划</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../purchase/listPurchaseInquireAllAction.do');">询价记录</a></td>
          </tr>
          <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../purchase/listPurchaseBillAllAction.do');"><bean:message key="sys.left.PurchaseBill"/></a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../purchase/listPurchaseIncomeAction.do');">采购入库</a></td>
          </tr>
        </table></td>
    </tr>



    <tr> 
      <td  align="center" background="../images/CN/mnu_item_back.jpg" style="height:24px" onClick="open_menu('warehouse')" onMouseOver="menuOver(this)" onMouseOut="menuOut(this)"><A href="#"><bean:message key="sys.left.WarehouseManage"/></a></td>
    </tr>
    <tr id="warehouse" style="display:none"> 
      <td> <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
	  	   <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../warehouse/waitApproveAction.do');">待审阅提交信息</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../warehouse/toSaleOrderControlAction.do');">待处理订单</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../warehouse/listShipmentBillAction.do');">销售出库单</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../warehouse/listStuffShipmentBillAction.do');">材料出库</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../warehouse/listOtherShipmentBillAction.do');">其它出库</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../purchase/listPurchaseIncomeAction.do');">采购入库</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../warehouse/listProductIncomeAction.do');">产成品入库</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../warehouse/listOtherIncomeAction.do');">其它入库</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../warehouse/stockStatAction.do');">库存统计</a></td>
          </tr>
		  <!--
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../warehouse/listStockAdjustAction.do');">调整</a></td>
          </tr>
		  -->
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../warehouse/listStockMoveAction.do');">调拨</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../warehouse/listStockWasteBookInitAction.do');">库存台账</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../warehouse/listStockCheckAction.do');">库存盘点</a></td>
          </tr>
        </table></td>
    </tr>



    <tr> 
      <td  align="center" background="../images/CN/mnu_item_back.jpg" style="height:24px" onClick="open_menu('finance')" onMouseOver="menuOver(this)" onMouseOut="menuOut(this)"><A href="#"><bean:message key="sys.left.FinanceManage"/></a></td>
    </tr>
    <tr id="finance" style="display:none"> 
      <td> <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
	  	   <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../finance/waitApproveAction.do');">待审阅提交信息</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../finance/listReceivableObjectAction.do');">应收款</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../finance/listIncomeLogAction.do');">收款</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../finance/listSettlementAction.do');">结算单</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../finance/listPayableObjectAction.do');">应付款</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../finance/listPaymentLogAction.do');">付款</a></td>
          </tr>
        </table></td>
    </tr>



    <tr> 
      <td  align="center" background="../images/CN/mnu_item_back.jpg" style="height:24px" onClick="open_menu('report')" onMouseOver="menuOver(this)" onMouseOut="menuOut(this)"><A href="#"><bean:message key="sys.left.ReportAnalyse"/></a></td>
    </tr>
    <tr id="report" style="display:none"> 
      <td> <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
          <tr >
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../report/toCustomerReportAction.do');"><bean:message key="sys.left.CustomerAnalyse"/></a></td>
          </tr>
          <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../report/customerSaleOrderOutlayAction.do');">客户调货情况</a></td>
          </tr>
          <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../report/perDaySaleReportAction.do');"><bean:message key="sys.left.PerDaySaleReport"/></a></td>
          </tr>
          <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../report/perDayShipmentReportAction.do');"><bean:message key="sys.left.PerDayShipmentReport"/></a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../report/perDayPurchaseReportAction.do');"><bean:message key="sys.left.PerDayPurchaseReport"/></a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../report/perDayIncomeReportAction.do');"><bean:message key="sys.left.PerDayIncomeReport"/></a></td>
          </tr>
        </table></td>
    </tr>


	<tr> 
      <td  align="center" background="../images/CN/mnu_item_back.jpg" style="height:24px" onClick="open_menu('assistant')" onMouseOver="menuOver(this)" onMouseOut="menuOut(this)"><A href="#"><bean:message key="sys.left.AssistantTools"/></a></td>
    </tr>
    <tr id="assistant" style="display:none"> 
      <td> <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
          <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../assistant/toSearchMobileAreaAction.do');"><bean:message key="sys.left.SearchMobileArea"/></a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../assistant/toSearchMobileAreaAction.do');">在线用户</a></td>
          </tr>
        </table></td>
    </tr>



    <tr> 
      <td  align="center" background="../images/CN/mnu_item_back.jpg" style="height:24px" onClick="open_menu('base')" onMouseOver="menuOver(this)" onMouseOut="menuOut(this)"><A href="#"><bean:message key="sys.left.SystemManager"/></a></td>
    </tr>
    <tr id="base" style="display:none"> 
      <td> <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#CCCCCC">
	      <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('toSetBasicAction.do');"><bean:message key="sys.left.SetBasic"/></a></td>
          </tr>
          <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../users/listUsersAction.do');"><bean:message key="sys.left.UserManager"/></a></td>
          </tr>
          <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../users/listRoleAction.do');"><bean:message key="sys.left.RoleManager"/></a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../users/toUpdSelfPwdAction.do');"><bean:message key="sys.left.UpdatePassword"/></a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../sys/listUserLogAction.do');">日志查询</a></td>
          </tr>
		  <tr > 
            <td align="center" bgcolor="#E1F2DB" onMouseOver="this.className='mnu02'" onMouseOut="this.className='mnu01'"><a href="javascript:ShowContent('../nopurview.do');"><bean:message key="sys.left.DataOutPut"/></a></td>
          </tr>
        </table></td>
    </tr>
    

  </table>
</div>
</body>
</html>
