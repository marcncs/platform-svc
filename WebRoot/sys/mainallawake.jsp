<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<title></title>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
  .removableObj   
  {   
  height:25;   
  position:relative;   
  left:   1px;   
   border-left: #ffffff 1px solid;
   border-right: #999999 0px solid;
   border-bottom: #999999 0px solid;
  }
 .table-bar{
 
 }
 .table-bar td{ 
  	
	HEIGHT: 20px;
 }
</style>

</head>



<body style="overflow-y: auto;">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td >我的办公桌>>待办事项 </td>
      </tr>
    </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
        
		  <%--        
          <td width="33%" valign="top" >
          <c:if test="${fn:contains(title.self, 'block')}">
          <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" class="removableObj" style="display:${title.self}">
            <tr>
              <td>
              <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="title-back" >
                  <tr>
                    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1" /></td>
                    <td > 我的办公桌 </td>
                  </tr>
                </table>
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-bar" >
                    <tr style="display:${self.se1}">
                      <td width="4%" >&nbsp;</td>
                      <td width="96%">新公告(<a href="../self/listAffichePublisthAction.do"><span <c:if test="${affiche>0}" >class="text-red"</c:if>>${affiche}</span></a>)</td>
                    </tr>
                    <tr style="display:${self.se2}">
                      <td >&nbsp;</td>
                      <td>新任务(<a href="../sys/listTaskAction.do"><span <c:if test="${task>0}" >class="text-red"</c:if>>${task}</span></a>)</td>
                    </tr>
                     <tr style="display:${self.se3}">
                      <td >&nbsp;</td>
                      <td>服务预约(<a href="../self/listServiceAgreementAllAction.do?se.IsAffirm=0"><span <c:if test="${sg>0}" >class="text-red"</c:if>>${sg}</span></a>)</td>
                    </tr>
                    <tr style="display:${self.se4}">
                      <td >&nbsp;</td>
                      <td>待审阅工作报告(<a href="../sys/listWorkReportAction.do?ApproveStatus=0"><span <c:if test="${workreport>0}" >class="text-red"</c:if>>${workreport}</span></a>)</td>
                    </tr>
                </table>
                </td>
            </tr>
          </table>
          </c:if>
          <c:if test="${fn:contains(title.warehouse, 'block')}">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" class="removableObj" id="2" style="display:${title.warehouse}">
              <tr>
                <td>
                <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="title-back">
                    <tr>
                      <td width="10"><img src="../images/CN/spc.gif" width="10" height="1" /></td>
                      <td > 仓库管理</td>
                    </tr>
                  </table>
                   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-bar">
                      <tr style="display:${warehouse.wa1}">
                        <td >&nbsp;</td>
                        <td>待完成检货(<a href="../warehouse/listTakeBillAction.do?IsAudit=0"><span <c:if test="${takecount>0}" >class="text-red" </c:if>>${takecount}</span></a>)</td>
                      </tr>
                      <tr style="display:${warehouse.wa2}">
                        <td >&nbsp;</td>
                        <td>待复核采购入库单(<a href="../warehouse/listPurchaseIncomeAction.do?IsAudit=0"><span <c:if test="${purchaseincomeaudit>0}" >class="text-red" </c:if>>${purchaseincomeaudit}</span></a>)</td>
                      </tr>
                      <tr style="display:${warehouse.wa3}">
                        <td >&nbsp;</td>
                        <td>待复核产成品入库(<a href="../warehouse/listProductIncomeAction.do?IsAudit=0"><span <c:if test="${productincomecount>0}" >class="text-red" </c:if>>${productincomecount}</span></a>)</td>
                      </tr>
                      <tr style="display:${warehouse.wa4}">
                        <td >&nbsp;</td>
                        <td>待签收发货单(<a href="../warehouse/listStockAlterMoveReceiveAction.do?IsComplete=0"><span <c:if test="${stockaltermovecount>0}" >class="text-red" </c:if>>${stockaltermovecount}</span></a>)</td>
                      </tr>
                      <tr style="display:${warehouse.wa5}">
                        <td width="4%" >&nbsp;</td>
                        <td width="96%">待签收转仓(<a href="../warehouse/listStockMoveReceiveAction.do?IsComplete=0"><span <c:if test="${stockmovecount>0}" >class="text-red" </c:if>>${stockmovecount}</span></a>)</td>
                      </tr>
                      <tr style="display:${warehouse.wa6}">
                        <td >&nbsp;</td>
                        <td>待签收代销(<a href="../warehouse/listSupplySaleMoveReceiveAction.do?IsComplete=0"><span <c:if test="${ssmcount>0}" >class="text-red" </c:if>>${ssmcount}</span></a>)</td>
                      </tr>
                      <tr style="display:${warehouse.wa7}">
                        <td >&nbsp;</td>
                        <td>待签收产品互转(<a href="../warehouse/listProductInterconvertReceiveAction.do?IsComplete=0"><span <c:if test="${pircount>0}" >class="text-red" </c:if>>${pircount}</span></a>)</td>
                      </tr>
                      <tr style="display:${warehouse.wa8}">
                        <td >&nbsp;</td>
                        <td>待签收渠道退货(<a href="../warehouse/listOrganWithdrawReceiveAction.do?IsComplete=0"><span <c:if test="${owcount>0}" >class="text-red" </c:if>>${owcount}</span></a>)</td>
                      </tr>
                      <tr style="display:${warehouse.wa9}">
                        <td >&nbsp;</td>
                        <td>待签收渠道换货&lt;供方&gt;(<a href="../warehouse/listOrganTradesReceiveAction.do?PisReceive=0"><span <c:if test="${otcount>0}" >class="text-red" </c:if>>${otcount}</span></a>)</td>
                      </tr>
                      <tr style="display:${warehouse.wa10}">
                        <td >&nbsp;</td>
                        <td>待签收渠道换货&lt;换方&gt;(<a href="../warehouse/listOrganTradesTReceiveAction.do?isReceive=0"><span <c:if test="${otrcount>0}" >class="text-red" </c:if>>${otrcount}</span></a>)</td>
                      </tr>
                      <tr style="display:${warehouse.wa11}">
                        <td >&nbsp;</td>
                        <td>待复核条码盘点(<a href="../warehouse/listStockCheckBarAction.do?IsBar=1"><span <c:if test="${scbcount>0}" >class="text-red" </c:if>>${scbcount}</span></a>)</td>
                      </tr>                      
                      <tr style="display:${warehouse.wa12}">
                        <td >&nbsp;</td>
                        <td>待复核盘亏(<a href="../warehouse/listOtherShipmentBillAction.do?IsAudit=0"><span <c:if test="${osbcount>0}" >class="text-red" </c:if>>${osbcount}</span></a>)</td>
                      </tr>
                      <tr style="display:${warehouse.wa13}">
                        <td >&nbsp;</td>
                        <td>待复核盘盈(<a href="../warehouse/listOtherIncomeAction.do?IsAudit=0"><span <c:if test="${oicount>0}" >class="text-red" </c:if>>${oicount}</span></a>)</td>
                      </tr>
                      <tr style="display:${warehouse.wa14}">
                        <td >&nbsp;</td>
                        <td>待复核领用(<a href="../warehouse/listDrawShipmentBillAction.do?IsAudit=0"><span <c:if test="${dsbcount>0}" >class="text-red" </c:if>>${dsbcount}</span></a>)</td>
                      </tr>
                      <tr style="display:${warehouse.wa15}">
                        <td >&nbsp;</td>
                        <td>待复核报损(<a href="../warehouse/listHarmShipmentBillAction.do?IsAudit=0"><span <c:if test="${hsbcount>0}" >class="text-red" </c:if>>${hsbcount}</span></a>)</td>
                      </tr>
                      <tr style="display:${warehouse.wa16}">
                        <td >&nbsp;</td>
                        <td>待复核产品互转(<a href="../warehouse/listProductInterconvertAction.do?IsAudit=0"><span <c:if test="${picount>0}" >class="text-red" </c:if>>${picount}</span></a>)</td>
                      </tr>
                  </table></td>
              </tr>
            </table>
            </c:if>
		  </td>
		  --%>
		  
          <td width="20%" align="center" valign="top" >
<%--          
          <c:if test="${fn:contains(title.sale, 'block')}">
          <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" class="removableObj"    id="3" style="display:${title.sale}">
            <tr>
              <td>
              <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="title-back">
                <tr>
                  <td width="10"><img src="../images/CN/spc.gif" width="10" height="1" /></td>
                  <td > 零售管理 </td>
                </tr>
              </table>
                <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-bar">
                  <tr style="display:${sale.sa1}">
                    <td width="4%" >&nbsp;</td>
                    <td width="96%">待复核销售单(<a href="../sales/listSaleOrderAction.do?IsAudit=0&IsBlankOut=0"><span <c:if test="${socount>0}" >class="text-red" </c:if>>${socount}</span></a>)</td>
                  </tr>
                  <tr style="display:${sale.sa2}">
                    <td >&nbsp;</td>
                    <td>待复核销售订单(<a href="../sales/listSaleIndentAction.do?IsAudit=0"><span <c:if test="${sicount>0}" >class="text-red" </c:if>>${sicount}</span></a>)</td>
                  </tr>
                  <tr style="display:${sale.sa3}">
                    <td >&nbsp;</td>
                    <td>待复核销售发票(<a href="../sales/listSaleInvoiceAction.do?IsAudit=0"><span <c:if test="${sicount2>0}" >class="text-red" </c:if>>${sicount2}</span></a>)</td>
                  </tr>
                  <tr style="display:${sale.sa4}">
                    <td >&nbsp;</td>
                    <td>待复核销售退货(<a href="../aftersale/listWithdrawAction.do?IsAudit=0"><span <c:if test="${swcount>0}" >class="text-red" </c:if>>${swcount}</span></a>)</td>
                  </tr>
                  <tr style="display:${sale.sa5}">
                    <td >&nbsp;</td>
                    <td>待复核换货(<a href="../aftersale/listSaleTradesAction.do?IsAudit=0"><span <c:if test="${stcount>0}" >class="text-red" </c:if>>${stcount}</span></a>)</td>
                  </tr>
                  <tr style="display:${sale.sa6}">
                    <td >&nbsp;</td>
                    <td>待发货换货(<a href="../aftersale/listSaleTradesAction.do?IsAudit=1&IsEndcase=0"><span <c:if test="${stcount2>0}" >class="text-red" </c:if>>${stcount2}</span></a>)</td>
                  </tr>
                  <tr style="display:${sale.sa7}">
                    <td >&nbsp;</td>
                    <td>待复核积分换购(<a href="../sales/listIntegralOrderAction.do?IsAudit=0"><span <c:if test="${iocount>0}" >class="text-red" </c:if>>${iocount}</span></a>)</td>
                  </tr>
                </table></td>
            </tr>
          </table>
          </c:if>
          --%>
          
           <c:if test="${fn:contains(title.ditch, 'block')}">
            <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" class="removableObj" id="4" style="display:${title.ditch}">
              <tr>
                <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="title-back">
                  <tr>
                    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1" /></td>
                    <td>发货流程 </td>
                  </tr>
                </table>
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-bar">
                  	<tr>
                      <td width="3%" >
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					创建发货单
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%"></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td width="3%" >
                      <table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					复核发货单
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%" style="display:${ditch.di3}">待复核(<a href="../warehouse/listStockAlterMoveAction.do?IsAudit=0"><span <c:if test="${samcount>0}" >class="text-red" </c:if>>${samcount}</span></a>)</td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td width="3%" >
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					采集器下载单据
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%"></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td width="3%" >
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					扫码
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%"></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td width="3%" >
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					上传条码
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%"></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					扫描单据确认
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td style="display:${ditch.di15}">待出库(<a href="../warehouse/listTakeBillAction.do?IsAudit=0&BSort=1"><span <c:if test="${samttcount>0}" >class="text-red" </c:if>>${samttcount}</span></a>)</td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td >
                      	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					发货单签收
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td style="display:${ditch.di16}">待签收(<a href="../warehouse/listStockAlterMoveReceiveAction.do?IsComplete=0"><span <c:if test="${samreceivecount>0}" >class="text-red" </c:if>>${samreceivecount}</span></a>)</td>
                    </tr>
                  </table></td>
              </tr>
          </table>
          </c:if>
          </td>
          
          
          
          <td width="40%" align="center" valign="top" >
          <c:if test="${fn:contains(title.ditch, 'block')}">
          <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" class="removableObj" id="4" style="display:${title.ditch}">
              <tr>
                <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="title-back">
                  <tr>
                    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1" /></td>
                    <td>转仓流程 </td>
                  </tr>
                </table>
                  <table width="400" border="0" cellpadding="0" cellspacing="0">
                  	<tr>
                      <td width="100" >
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					创建机构转仓单
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="100"></td>
                      <td width="100" >
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					创建机构间转仓单
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="100"></td>
                    </tr>
                    <tr>
                      <td width="100" >
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td width="100" ></td>
                      <td width="100" >
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td width="100" ></td>
                    </tr>
                    <tr style="display:${ditch.di8}">
                      <td width="100" >
					  <table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					复核机构转仓单
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="100" style="display:${ditch.di9}">待复核(<a href="../warehouse/listStockMoveAction.do?IsAudit=0"><span <c:if test="${smcount>0}" >class="text-red" </c:if>>${smcount}</span></a>)</td>
                      <td width="100">
                      <table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					批准机构间转仓单
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="100" style="display:${ditch.di8}">待批准(<a href="../warehouse/listRatifyMoveApplyAction.do?IsRatify=0"><span <c:if test="${macount>0}" >class="text-red" </c:if>>${macount}</span></a>)</td>
                    </tr>
                    
                    
                    <tr>
                      <td width="100" >
                      <table width="100" height="35" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="right">
                  					<img src="<%=request.getContextPath()%>/images/arrow2.png" width="28" height="35"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td width="100" ></td>
                      <td width="100" >
                      <table width="100" height="35" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="left">
                  					<img src="<%=request.getContextPath()%>/images/arrow3.png" width="28" height="35"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td width="100" ></td>
                    </tr>
                    
                    
                    
                    <tr align="center">
                      <td colspan="3">
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					采集器下载单据
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
					  <td></td>
                    </tr>
                  	<tr>
                      <td colspan="3" align="center">
                      <table height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    
                    <tr align="center">
                      <td colspan="3">
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					扫码
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
					  <td></td>
                    </tr>
                  	<tr>
                      <td colspan="3" align="center">
                      <table height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    
                    <tr align="center">
                      <td colspan="3">
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					上传条码
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
					  <td></td>
                    </tr>
                  	<tr>
                      <td colspan="3" align="center">
                      <table height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    
                    <tr align="center">
                      <td width="100">&nbsp;</td>
                      <td>
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					扫描单据确认
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
					  <td width="100" align="left" style="display:${ditch.di15}">待出库(<a href="../warehouse/listTakeBillAction.do?IsAudit=0&BSort=2"><span <c:if test="${smttcount>0}" >class="text-red" </c:if>>${smttcount}</span></a>)</td>
					  <td width="100">&nbsp;</td>
                    </tr>
                    
                    <tr>
                      <td colspan="3" align="center">
                      <table height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    
                    <tr align="center">
                      <td width="100">&nbsp;</td>
                      <td>
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					转仓单签收
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
					  <td width="100" align="left" style="display:${ditch.di17}">待签收(<a href="../warehouse/listStockMoveReceiveAction.do?IsComplete=0"><span <c:if test="${smreceivecount>0}" >class="text-red" </c:if>>${smreceivecount}</span></a>)</td>
					  <td width="100">&nbsp;</td>
                    </tr>
                  	<tr>
                  </table></td>
              </tr>
          </table>
          
          </c:if>
          </td>
          
          <td width="20%" align="center" valign="top" >
          <c:if test="${fn:contains(title.ditch, 'block')}">
          
          <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" class="removableObj" id="4" style="display:${title.ditch}">
          <tr>
                <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="title-back">
                  <tr>
                    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1" /></td>
                    <td>渠道退货流程 </td>
                  </tr>
                </table>
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-bar">
                  	<tr>
                      <td width="3%" >
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					创建渠道退货单
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%"></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td width="3%" >
                      <table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					复核渠道退货单
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%" style="display:${ditch.di11}">待复核(<a href="../ditch/listOrganWithdrawAction.do?IsAudit=0&type=1"><span <c:if test="${owcount>0}" >class="text-red" </c:if>>${owcount}</span></a>)</td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td width="3%" >
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					采集器下载单据
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%"></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td width="3%" >
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					扫码
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%"></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td width="3%" >
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					上传条码
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%"></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					扫描单据确认
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%" style="display:${ditch.di15}">待出库(<a href="../warehouse/listTakeBillAction.do?IsAudit=0&BSort=7&withdrawType=OW"><span <c:if test="${owttcount>0}" >class="text-red" </c:if>>${owttcount}</span></a>)</td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td >
                      	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					渠道退货签收
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td style="display:${ditch.di18}">待签收(<a href="../warehouse/listOrganWithdrawReceiveAction.do?IsComplete=0"><span <c:if test="${owreceivecount>0}" >class="text-red" </c:if>>${owreceivecount}</span></a>)</td>
                    </tr>
                  </table></td>
              </tr>
              
          </table>
          
          </c:if>
          </td>
          
          
          <td width="20%" align="center" valign="top" >
          <c:if test="${fn:contains(title.ditch, 'block')}">
          <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" class="removableObj" id="4" style="display:${title.ditch}">
          <tr>
                <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="title-back">
                  <tr>
                    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1" /></td>
                    <td>工厂退回流程 </td>
                  </tr>
                </table>
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-bar">
                  	<tr>
                      <td width="3%" >
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					创建工厂退回单
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%"></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td width="3%" >
                      <table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					复核工厂退回单
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%" style="display:${ditch.di19}">待复核(<a href="../ditch/listPlantWithdrawAction.do?IsAudit=0"><span <c:if test="${powcount>0}" >class="text-red" </c:if>>${powcount}</span></a>)</td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td width="3%" >
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					采集器下载单据
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%"></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td width="3%" >
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					扫码
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%"></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td width="3%" >
					  	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					上传条码
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td width="97%"></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					扫描单据确认
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td style="display:${ditch.di15}">待出库(<a href="../warehouse/listTakeBillAction.do?IsAudit=0&BSort=7&withdrawType=PW"><span <c:if test="${powttcount>0}" >class="text-red" </c:if>>${powttcount}</span></a>)</td>
                    </tr>
                    <tr>
                      <td>
                      <table width="100" height="38" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					<img src="<%=request.getContextPath()%>/images/arrow.png" width="20" height="38"/>
                  				</td>
                 		 	</tr>
               		   </table>
                      </td>
                      <td></td>
                    </tr>
                    <tr>
                      <td >
                      	<table width="100" height="50" border="1" bordercolor="black" cellpadding="0" cellspacing="0">
                  			<tr>
                  				<td align="center">
                  					工厂退回签收
                  				</td>
                 		 	</tr>
               		   </table>
					  </td>
                      <td style="display:${ditch.di20}">待签收(<a href="../warehouse/listPlantWithdrawReceiveAction.do?IsComplete=0&TakeStatus=1"><span <c:if test="${powreceivecount>0}" >class="text-red" </c:if>>${powreceivecount}</span></a>)</td>
                    </tr>
                  </table></td>
              </tr>
          </table>
          </c:if>
          </td>
<%--          
          <td width="34%" valign="top" >
          <c:if test="${fn:contains(title.equip, 'block')}">
          <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" class="removableObj" id="5" style="display:${title.equip}">
              <tr>
                <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="title-back">
                  <tr>
                    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1" /></td>
                    <td > 配送中心 </td>
                  </tr>
                </table>
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-bar">
                    <tr style="display:${equip.eq1}">
                      <td width="3%" >&nbsp;</td>
                      <td width="97%">待转配送单(<a href="../warehouse/listShipmentBillAction.do?IsAudit=1&IsTrans=0"><span <c:if test="${sbcount>0}" >class="text-red" </c:if>>${sbcount}</span></a>)</td>
                    </tr>
                    <tr style="display:${equip.eq2}">
                      <td >&nbsp;</td>
                      <td>待完成配送(<a href="../warehouse/listShipmentBillAction.do?IsAudit=0"><span <c:if test="${asbcount>0}" >class="text-red" </c:if>>${asbcount}</span></a>)</td>
                    </tr>
                  </table></td>
              </tr>
          </table>
          </c:if>
          <c:if test="${fn:contains(title.purchase, 'block')}">
          <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" class="removableObj" id="6" style="display:${title.purchase}">
            <tr>
              <td>
              <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="title-back">
                  <tr>
                    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1" /></td>
                    <td > 产品采购 </td>
                  </tr>
                </table>
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-bar">
                    <tr style="display:${purchase.pu1}">
                      <td >&nbsp;</td>
                      <td>待复核采购计划(<a href="../purchase/listPurchasePlanAction.do?IsAudit=0"><span <c:if test="${plancount>0}">class="text-red"</c:if>>${plancount}</span></a>)</td>
                    </tr>
                    <tr style="display:${purchase.pu2}">
                      <td width="4%" >&nbsp;</td>
                      <td width="96%">待批准采购计划(<a href="../purchase/listPurchasePlanAction.do?IsRatify=0&IsAudit=1"><span <c:if test="${purchaseplanratify>0}">class="text-red"</c:if>>${purchaseplanratify}</span></a>)</td>
                    </tr>
                    <tr style="display:${purchase.pu3}">
                      <td >&nbsp;</td>
                      <td>待复核采购订单(<a href="../purchase/listPurchaseBillAllAction.do?IsAudit=0"><span <c:if test="${pbcount>0}">class="text-red"</c:if>>${pbcount}</span></a>)</td>
                    </tr>
                    <tr style="display:${purchase.pu4}">
                      <td >&nbsp;</td>
                      <td>待批准采购订单(<a href="../purchase/listPurchaseBillAllAction.do?IsRatify=0&IsAudit=1"><span <c:if test="${purchasebillratify>0}">class="text-red"</c:if>>${purchasebillratify}</span></a>)</td>
                    </tr>
                    <tr style="display:${purchase.pu5}">
                      <td >&nbsp;</td>
                      <td>待复核采购发票(<a href="../purchase/listPurchaseInvoiceAllAction.do?IsAudit=0"><span <c:if test="${pinvoicecount>0}">class="text-red"</c:if>>${pinvoicecount}</span></a>)</td>
                    </tr>
                    <tr style="display:${purchase.pu6}">
                      <td >&nbsp;</td>
                      <td>待复核采购退货(<a href="../aftersale/listPurchaseWithdrawAction.do?IsAudit=0"><span <c:if test="${pwcount>0}">class="text-red"</c:if>>${pwcount}</span></a>)</td>
                    </tr>
                    <tr style="display:${purchase.pu7}">
                      <td >&nbsp;</td>
                      <td>待复核采购换货(<a href="../aftersale/listPurchaseTradesAction.do?IsAudit=0"><span <c:if test="${ptcount>0}">class="text-red"</c:if>>${ptcount}</span></a>)</td>
                    </tr>
                    <tr style="display:${purchase.pu8}">
                      <td >&nbsp;</td>
                      <td>待回收采购换货(<a href="../aftersale/listPurchaseTradesAction.do?IsAudit=1&IsReceive=0"><span <c:if test="${rptcount>0}">class="text-red"</c:if>>${rptcount}</span></a>)</td>
                    </tr>
                </table></td>
            </tr>
          </table> 
          </c:if>
           <c:if test="${fn:contains(title.machin, 'block')}">
          <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" class="removableObj" id="7" style="display:${title.machin}">
            <tr>
              <td>
            <table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="title-back">
              <tr>
                <td width="10"><img src="../images/CN/spc.gif" width="10" height="1" /></td>
                <td > 生产组装 </td>
              </tr>
          </table>
            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-bar">
              <tr style="display:${machin.ma1}">
                <td width="4%">&nbsp;</td>
                <td width="96%">待复核组装关系(<a href="../machin/listAssembleRelationAction.do?IsAudit=0"><span <c:if test="${arcount>0}">class="text-red"</c:if>>${arcount}</span></a>)</td>
              </tr>
              <tr style="display:${machin.ma2}">
                <td>&nbsp;</td>
                <td>待复核组装(<a href="../machin/listAssembleAction.do?IsAudit=0"><span <c:if test="${acount>0}">class="text-red"</c:if>>${acount}</span></a>)</td>
              </tr>
              <tr style="display:${machin.ma3}">
                <td>&nbsp;</td>
                <td>待复核委外加工(<a href="../machin/listConsignMachinAction.do?IsAudit=0"><span <c:if test="${cmcount>0}">class="text-red"</c:if>>${cmcount}</span></a>)</td>
              </tr>
          </table>
          </td>
            </tr>
          </table>
          </c:if>
           <c:if test="${fn:contains(title.finance, 'block')}">
           <table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" class="removableObj" id="8" style="display:${title.finance}">
              <tr>
                <td><table width="100%" height="31" border="0" cellpadding="0" cellspacing="0" class="title-back">
                  <tr>
                    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1" /></td>
                    <td>账务管理 </td>
                  </tr>
                </table>
                  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="table-bar">
                    <tr style="display:${finance.fi1}">
                      <td >&nbsp;</td>
                      <td>待复核收款(<a href="../finance/listIncomeLogAction.do?IsAudit=0"><span <c:if test="${ilcount>0}">class="text-red"</c:if> >${ilcount}</span></a>)</td>
                    </tr>
                     <tr style="display:${finance.fi2}">
                      <td >&nbsp;</td>
                      <td>待复核付款(<a href="../finance/listPaymentLogAction.do?IsAudit=0"><span <c:if test="${plcount>0}">class="text-red"</c:if>>${plcount}</span></a>)</td>
                    </tr>
                     <tr style="display:${finance.fi3}">
                       <td >&nbsp;</td>
                       <td>待复核费用(<a href="../finance/listOutlayAction.do?IsAudit=0"><span <c:if test="${ocount>0}">class="text-red"</c:if> >${ocount}</span></a>)</td>
                     </tr>
                     <tr style="display:${finance.fi4}">
                       <td >&nbsp;</td>
                       <td>待结款费用(<a href="../finance/listOutlayAction.do?IsAudit=1&isendcase=0"><span <c:if test="${oecount>0}">class="text-red"</c:if> >${oecount}</span></a>)</td>
                     </tr>
                     <tr style="display:${finance.fi5}">
                       <td >&nbsp;</td>
                       <td>应收款超龄(<a href="../finance/listReceivableAllAction.do?IsClose=0"><span <c:if test="${clcount>0}">class="text-red"</c:if> >${clcount}</span></a>)</td>
                     </tr>
                  </table></td>
              </tr>
          </table>
          </c:if>
          </td>
          --%>
          
        </tr>

      </table>
      </td>
  </tr>
</table>
</body>
</html>
