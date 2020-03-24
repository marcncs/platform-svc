<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script src="../js/prototype.js"></script>
		<script type="text/javascript" src="../js/function.js"></script>
       <SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
        <SCRIPT language="javascript" src="../js/showSQ.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>WINDRP-分销系统</title>
		<script language="javascript">
			function Audit(soid){
					popWin2("../sales/auditSaleIndentAction.do?SOID="+soid);
			}
			
			function CancelAudit(soid){
					popWin2("../sales/cancelAuditSaleIndentAction.do?SOID="+soid);
			}
			
			function Endcase(soid){
					popWin2("../sales/endcaseSaleIndentAction.do?SOID="+soid);
			}
			
			function CancelEndcase(soid){
					popWin2("../sales/cancelEndcaseSaleIndentAction.do?SOID="+soid);
			}
			
			function prints(soid){
					popWin("../sales/printSaleIndentAction.do?ID="+soid,900,600);
			}
			
			
			function ShowHD(productid) {   // yy xx
			var cid = $F('cid');
				$("hd").style.visibility = "visible" ;
				$("hd").style.top = event.clientY;;
				$("hd").style.left = event.clientX;
				//$("require").removeChild($("require").getElementsByTagName("table")[0]);
				if(cid!=""){
				getHistoryPrice(cid,productid);
				}
			}
			function HiddenHD(){
				hd.style.visibility = "hidden";
				/*
				for(var b=0;b<require.rows.length;b++){
					 	document.getElementById('require').deleteRow(b);
					 	b=b-1;
					 	}
				*/ 
				$("historyprice").removeChild($("historyprice").getElementsByTagName("table")[0]);
			}
			
			function getHistoryPrice(cid,productid){
				   var url = "../sales/getHistoryChenAjax.do?cid="+cid+"&productid="+productid;
				   //document.getElementById("require").style.display="";
				   //document.getElementById("require").innerHTML="姝ｅ湪璇诲彇鏁版嵁...";
			       var pars = '';
			       var myAjax = new Ajax.Request(
			                    url,
			                    {method: 'get', parameters: pars, onComplete: showHistory}
			                    );
			}
			
			function showHistory(originalRequest){
				
				var product = originalRequest.responseXML.getElementsByTagName("product");
				//var x=document.all("require");//.insertRow(desk.rows.length);
				//var strcontent="";
				//alert(proot.length);
				var requireHTML = '<table id="historyprice" width="100%"  border="0" cellpadding="3" cellspacing="0">';
			
					for(var i=0;i<product.length;i++){
						var rm = product[i];
						var hprice = rm.getElementsByTagName("hprice")[0].firstChild.data;
						var hdate =rm.getElementsByTagName("hdate")[0].firstChild.data;
						//var murl =rm.getElementsByTagName("murl")[0].firstChild.data;
						//alert(ispd);
						requireHTML  += "<tr><td width='50%'>"+hprice+"</td><td width='50%'>"+hdate+" </td></tr>";
						//addRows(productname,productcount,stockcount);
					}
			
					$("historyprice").innerHTML = requireHTML + "</table>";
			
					//document.getElementById("result").style.display="none";
				}
				
</script>
		<style type="text/css">
<!--
#hd {
	position: absolute;
	left: 0px;
	top: 0px;
	width: 200px;
	height: auto;
	z-index: 1;
	visibility: hidden;
}

-->
</style>
	</head>
	<div id="hd">
		<table width="100%" height="80" border="0" cellpadding="0"
			cellspacing="0" class="GG">
			<tr>
				<td width="50%" height="32" class="title-back">
					历史成交价
				</td>
				<td width="50%" class="title-back">
					成交价日期
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div id="historyprice">

					</div>
				</td>
			</tr>
		</table>
	</div>
	<body>
		<SCRIPT language=javascript>

</SCRIPT>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="8">
							</td>
							<td width="958">
								零售预订单详情
							</td>
							<td width="275" align="right">
								<table width="180" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="60" align="center">
											<a href="javascript:prints('${sof.id}');">打印</a>
										</td>
										<td width="60" align="center">
											<c:choose>
												<c:when test="${sof.isaudit==0}">
													<a href="javascript:Audit('${sof.id}');">复核</a>
												</c:when>
												<c:otherwise>
													<a href="javascript:CancelAudit('${sof.id}')">取消复核</a>
												</c:otherwise>
											</c:choose>
										</td>
										<td width="60" align="center">
											<c:choose>
												<c:when test="${sof.isendcase==0}">
													<a href="javascript:Endcase('${sof.id}')">结案</a>
												</c:when>
												<c:otherwise>
													<a href="javascript:CancelEndcase('${sof.id}')">取消结案</a>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<fieldset align="center">
						<legend>
							<table width="50" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										基本信息
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
							<tr>
								<td width="9%" align="right">
									<input name="cid" type="hidden" id="cid" value="${sof.cid}">
									会员名称：
								</td>
								<td width="24%">
									${sof.cname}								</td>
								<td width="10%" align="right">
									对方单据编号：								</td>
								<td width="22%">
									${sof.customerbillid}								</td>
								<td width="10%" align="right">
									发运方式：								</td>
								<td width="25%">
									<windrp:getname key='TransportMode' value='${sof.transportmode}'
										p='d' />
							  </td>
							</tr>
							<tr>
								<td align="right">
									零售部门：
								</td>
								<td></td>
								<td align="right">
									收货地址：
								</td>
								<td>
									${sof.transportaddr}
								</td>
								<td align="right">
									收货人：
								</td>
								<td>
									${sof.receiveman}
								</td>
							</tr>
							<tr>
								<td height="18" align="right">
									零售人员：
								</td>
								<td></td>
								<td align="right">
									货运部：
								</td>
								<td>
									<windrp:getname key="Transit" value="${sof.transit}" p="d" />
								</td>
								<td align="right">
									付款方式：
								</td>
								<td colspan="3">
									<windrp:getname key='paymentmode' value='${sof.paymentmode}'
										p='d' />
								</td>
							</tr>
							<tr>
								<td height="23" align="right">
									联系电话：								</td>
								<td>
									${sof.tel}
								</td>
								<td align="right">
									交货日期：
								</td>
								<td>
									${sof.consignmentdate}
								</td>
								<td align="right">
									总金额：
								</td>
								<td colspan="3">
									${sof.totalsum}
								</td>
							</tr>
							<tr>
								<td align="right">
									备注：
								</td>
								<td colspan="5">
									${sof.remark}
						      </td>
							</tr>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="50" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										状态信息
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="table-detail">
							<tr>
							  <td align="right">制单机构：</td>
							  <td><windrp:getname key='organ' value='${sof.makeorganid}' p='d'/></td>
							  <td align="right">制单部门：</td>
							  <td><windrp:getname key='dept' value='${sof.makedeptid}' p='d'/></td>
								<td width="11%" align="right">
									制单人：								</td>
								<td width="25%">
							  <windrp:getname key='users' value='${sof.makeid}' p='d' />								</td>
						  </tr>
							<tr>
								<td width="9%" align="right">
									制单日期：								</td>
								<td width="25%">
							  <windrp:dateformat value='${sof.makedate}' p='yyyy-MM-dd' />								</td>
								<td width="9%" align="right">
									修改人：								</td>
								<td width="21%">
							  <windrp:getname key='users' value='${sof.updateid}' p='d' />								</td>
							
								<td align="right">
									修改日期：								</td>
								<td>
									<windrp:dateformat value='${sof.lastupdate}' p='yyyy-MM-dd' />								</td>
						  </tr>
							<tr>
								<td align="right">
									是否复核：								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${sof.isaudit}' p='f' />								</td>
								<td align="right">
									复核人：								</td>
								<td>
									<windrp:getname key='users' value='${sof.auditid}' p='d' />								</td>
							
								<td align="right">
									复核日期：								</td>
								<td>
									<windrp:dateformat value='${sof.auditdate}' p='yyyy-MM-dd' />								</td>
									</tr>
							<tr>
								<td align="right">
									是否结案：								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${sof.isendcase}' p='f' />								</td>
								<td align="right">
									结案人：								</td>
								<td>
									<windrp:getname key='users' value='${sof.endcaseid}' p='d' />								</td>
							
								<td align="right">
									结案日期：								</td>
								<td>
									<windrp:dateformat value='${sof.endcasedate}' p='yyyy-MM-dd' />								</td>
									</tr>
						
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="100" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										零售订单产品列表
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
							<tr align="center" class="title-top">
								<td width="9%">
									产品编号
								</td>
								<td width="18%">
									产品名称
								</td>
								<td width="16%">
									规格
								</td>
								<td width="9%">
									单位
								</td>
								<td width="8%">
									相关
								</td>
								<td width="9%">
									单价
								</td>
								<td width="8%">
									数量
								</td>
								<td width="8%">
									折扣率
								</td>
								<td width="8%">
									税率
								</td>

								<td width="7%">
									金额
								</td>
							</tr>
							<logic:iterate id="p" name="als">
								<tr class="table-back-colorbar">
									<td>
										${p.productid}
									</td>
									<td>
										${p.productname}
									</td>
									<td>
										${p.specmode}
									</td>
									<td>
										${p.unitidname}
									</td>
									<td>
										<a href="#" onMouseOver="ShowHD('${p.productid}');"
											onMouseOut="HiddenHD();"><img
												src="../images/CN/cheng.gif" width="16" border="0"></a>&nbsp;<a
											href="#" onMouseOver="ShowSQ(this,'${p.productid}');"
											onMouseOut="HiddenSQ();"><img
												src="../images/CN/stock.gif" width="16" border="0"></a>
									</td>
									<td align="right">
										<windrp:format value="${p.unitprice}" p="###,##0.00"/>
									</td>
									<td align="right">
										<windrp:format value="${p.quantity}" p="###,##0.00"/>
									</td>

									<td align="right">
										${p.discount}%
									</td>
									<td align="right">
										${p.taxrate}%
									</td>
									<td  align="right">
										<windrp:format value="${p.subsum}" p="###,##0.00"/>
									</td>
								</tr>
							</logic:iterate>
						</table>
					</fieldset>

				</td>
			</tr>
		</table>
	</body>
</html>
