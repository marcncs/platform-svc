<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/sorttable.js"></script>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/showCQ.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
</head>
	<body>
		<script language="javascript">
		//jQuery解除与其它js库的冲突
		var $j = jQuery.noConflict(true);
	var checkid=0;
	function CheckedObj(obj){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	}
	

	function Receive(id){
			showloading();
			popWin("../warehouse/completePlantWithdrawReceiveAction.do?ID="+id,500,250);
	}
	
	function CancelReceive(id){
		showloading();
		popWin("../warehouse/cancelCompletePlantWithdrawReceiveAction.do?ID="+id,500,250);
	}
	function toaddidcode(billid,bdid,wid,isaudit){
			popWin4("../warehouse/toAddOrganWithdrawIdcodeiiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit+"&flag=PW",1000,650,"idcode");
	}
	function toaddbit(billid,bdid,wid,isaudit){
			popWin4("../warehouse/toAddOrganWithdrawIdcodeiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,600,"idcode");
	}
	function prints(id){
			popWin("../ditch/printOrganWithdrawReceiveAction.do?ID="+id,900,600);
	}
	
	//验证条码数量与订单数量是否一致
	function toQuantityCheck(id){
		$j.post("../warehouse/ajaxQuantityCompleteOrganWithdrawAction.do?id="+id, function(result){
		   	var data = eval('(' + result + ')');
			if(data.state=="1"){
				var con=confirm("条码数量与出库数量不符，是否签收？");
				if(con==true){
					Receive(data.id);
				}
				if(con==false){
					return;
				}
			}else{
				var con=confirm("是否签收？");
				if(con==true){
					Receive(data.id);
				}
				if(con==false){
					return;
				}
			}
	  	}); 
	}	
	 function excput(){
		 document.getElementById("excput").target="";
		 document.getElementById("excput").submit();
   	 }
</script>

		<form id="excput" name="excput" method="post"
							action="../warehouse/excPutPlantWithdrawReceiveAction.do">
		    <input type="hidden" name="owid" value="${ID}">
		    <input type="hidden" name="excputType" value="idcode">
		</form>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0"
						class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="8">
							</td>
							<td>
								${menu }详情
							</td>
							<td align="right">
								<table border="0" cellpadding="0" cellspacing="0">
									<tr>
										<ws:hasAuth operationName="/ditch/printPlantWithdrawReceiveAction.do">
											<td width="60" align="center">
											<input type="button" name="prints" id="prints" value="打印"
													onclick="javascript:prints('${ama.id}');" />
												<%--<a href="javascript:prints('${ama.id}');">打印</a>--%>
											</td>
										</ws:hasAuth>
										<ws:hasAuth
											operationName="/warehouse/completePlantWithdrawReceiveAction.do">
											<td style="float: right; padding-right: 20px;">
												<c:choose>
													<c:when test="${ama.iscomplete==0 and ama.takestatus == 1}">
													<input type="button" name="toquantitycheck" id="toquantitycheck" value="签收"
													onclick="javascript:toQuantityCheck('${ama.id}');" />
														<%--<a href="javascript:toQuantityCheck('${ama.id}');">签收</a>--%>
													</c:when>
													<%--<c:otherwise><a href="javascript:CancelReceive('${ama.id }')">取消签收</a></c:otherwise>	--%>
												</c:choose>
											</td>
										</ws:hasAuth>
										<ws:hasAuth operationName="/warehouse/excPutPlantWithdrawReceiveAction.do">
										    <td width="80">
												<a href="javascript:excput();"><img
													src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
													align="absmiddle">&nbsp;导出条码</a>
											</td>
										</ws:hasAuth>
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
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="table-detail">
							<tr>
								<td width="11%" align="right">
									编号：
								</td>
								<td width="22%">
									${ama.id}
								</td>
								<td width="10%" align="right">
									供货机构：
								</td>
								<td width="23%">
									<windrp:getname key="organ" p="d" value="${ama.porganid}" />
								</td>
								<td width="9%" align="right">
									机构联系人：
								</td>
								<td width="25%">
									${ama.plinkman }
								</td>
							</tr>
							<tr>
								<td align="right">
									出货仓库：
								</td>
								<td>
									<windrp:getname key='warehouse' value='${ama.warehouseid}' p='d' />
								</td>
								<%--<td align="right">
									总金额：
								</td>
								<td>
									${ama.totalsum }
								</td>
								--%><td align="right">
									联系电话：
								</td>
								<td>
									${ama.tel }
								</td>
							</tr>
							<tr>
								<td align="right">
									入货仓库：
								</td>
								<td colspan="5">
									<windrp:getname key='warehouse' value='${ama.inwarehouseid}' p='d' />
								</td>
							</tr>
							<tr>
								<td align="right">
									退货原因：
								</td>
								<td colspan="5">
								
									<windrp:getname key="ReturnReason" p="f" value="${ama.remark}" />
								</td>
							</tr>
							<tr>
								<td align="right">
									备注：
								</td>
								<td colspan="5">
									${ama.withdrawcause}
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
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="table-detail">
							<tr>
								<td width="11%" align="right">
									制单机构：
								</td>
								<td width="22%">
									<windrp:getname key='organ' value='${ama.makeorganid}' p='d' />
								</td><%--
								<td width="10%" align="right">
									制单部门：
								</td>
								<td>
									<windrp:getname key='dept' value='${ama.makedeptid}' p='d' />
								</td>
								--%><td align="right">
									制单人：
								</td>
								<td>
									<windrp:getname key='users' value='${ama.makeid}' p='d' />
								</td>
							</tr>
							<tr>
								<td align="right">
									制单日期：
								</td>
								<td>
									<windrp:dateformat value='${ama.makedate}' p='yyyy-MM-dd' />
								</td>
								<%--<td align="right">
									检货状态：
								</td>
								<td>
									<windrp:getname key="TakeStatus" p="f" value="${ama.takestatus}" />
								</td>
								--%><td align="right">
									打印次数：
								</td>
								<td>
									${ama.printtimes}
								</td>
							</tr>
							<tr>
								<td width="11%" align="right">
									是否复核：
								</td>
								<td width="22%">
									<windrp:getname key='YesOrNo' value='${ama.isaudit}' p='f' />
								</td>
								<td width="10%" align="right">
									复核人：
								</td>
								<td width="23%">
									<windrp:getname key='users' value='${ama.auditid}' p='d' />
								</td>
								<td width="9%" align="right">
									复核日期：
								</td>
								<td width="25%">
									<windrp:dateformat value='${ama.auditdate}' p='yyyy-MM-dd' />
								</td>
							</tr>
							<tr>
								<td align="right">
									是否签收：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${ama.iscomplete}' p='f' />
								</td>
								<td align="right">
									签收人：
								</td>
								<td>
									<windrp:getname key='users' value='${ama.receiveid}' p='d' />
								</td>
								<td align="right">
									签收日期：
								</td>
								<td>
									<windrp:dateformat value='${ama.receivedate}' p='yyyy-MM-dd' />
								</td>
							</tr>
<%--							
							<tr>
								<td align="right">
									是否作废：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${ama.isblankout}' p='f' />
								</td>
								<td align="right">
									作废人：
								</td>
								<td>
									<windrp:getname key='users' value='${ama.blankoutid}' p='d' />
								</td>
								<td align="right">
									作废日期：
								</td>
								<td>
									<windrp:dateformat value='${ama.blankoutdate}' p='yyyy-MM-dd' />
								</td>
							</tr>

							<tr>
								<td align="right">
									作废原因：
								</td>
								<td colspan="5">
									${ama.blankoutreason }
								</td>
							</tr>
							--%>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="120" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										${menu }产品列表
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top">
								<td width="10%">
									产品编号
								</td>
								<td width="10%">
									物料号
								</td>
								<td width="17%">
									产品名称
								</td>
								<td width="12%">
									规格
								</td>
								<td width="5%">
									序号
								</td>
								<td width="5%">
									单位
								</td>
								<td width="7%">
									计划数量
								</td>
								<td width="7%">
									出库数量
								</td>
								<td width="7%">
									签收数量
								</td>
							</tr>
							<logic:iterate id="p" name="list">
								<tr class="table-back-colorbar" onClick="CheckedObj(this);">
									<td>
										${p.productid}
									</td>
									<td>
										${p.nccode}
									</td>
									<td>
										${p.productname}
									</td>
									<td>
										${p.specmode}
									</td>
									<td><span onmouseover="ShowCQ(this,'${p.productid}','${ama.id}','7');"><windrp:idcodebit value="${p.productid}" idcodeclick="toaddidcode('${ama.id}','${p.id}','${ama.inwarehouseid}','${ama.iscomplete}')" bitclick="toaddbit('${ama.id}','${p.id}','${ama.inwarehouseid}','${ama.iscomplete}')"/></span></td>
									<td>
										<windrp:getname key='CountUnit' value='${p.unitid}' p='d' />
									</td>
									<td>
										<fmt:formatNumber value='${p.quantity}' pattern='0.00' />
									</td>
									<td>
										<fmt:formatNumber value='${p.takequantity}' pattern='0.00' />
									</td>
									<td>
										<c:if test="${ama.iscomplete == 1 }">
											<fmt:formatNumber value='${p.takequantity}' pattern='0.00' />
										</c:if>
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
