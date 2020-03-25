<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/validator.js"></script>
<script type="text/javascript" src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/showSQ.js"> </SCRIPT>
<script language=javascript>
 
	



function SubTotal(rowx){
	var sum=0.00;
	var rowslength=dbtable.rows.length-1;
	if((dbtable.rows.length-1) <=1){
		sum=(document.forms[0].item("unitprice").value)*(document.forms[0].item("canquantity").value);
		document.validateProvide.item("subsum").value=sum;
	}else{
		for(var m=0;m<rowslength;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("canquantity")(m).value);
			document.validateProvide.item("subsum")(m).value=sum;	
		}
	}
}


function TotalSum(){
	var totalsum=0.00;
	if((dbtable.rows.length-1) <=1){
		totalsum=totalsum+parseFloat(document.validateProvide.item("subsum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.validateProvide.item("subsum")(i).value);
		}
	}
	document.validateProvide.totalsum.value=totalsum;
}


function Check(){
	if ( !Validator.Validate(document.addusers,2) ){
		return false;
	}

	var canquantity = document.getElementsByName("canquantity");
	var quantity =document.getElementsByName("quantity"); 
	
	for(var i=0;i<quantity.length;i++){
		if (parseFloat(canquantity[i].value) > parseFloat(quantity[i].value)) {
			alert("本次批准数量不能大于 数量!");
			canquantity[i].select();
			return false;
		}	
	}

	return true;
}

</script>

</head>
<body style="overflow: auto;">
<form name="validateProvide" method="post" action="ratifyAlterMoveApplyAction.do" onsubmit="return Check();">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">

  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td > 批准订购申请单</td>
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
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
				<tr>
					<td width="10%" align="right">
						调出机构：
					</td>
					<td width="23%">
						<windrp:getname key="organ" p="d" value="${ama.outorganid}"/>
					</td>
					<td width="11%" align="right">
						调拨需求日期：
					</td>
					<td width="21%">
					<input type="hidden" id="id" name="id" value="${ama.id }">
					<windrp:dateformat value="${ama.movedate }" p="yyyy-MM-dd"/>
						
					</td>
					
					<td width="9%" align="right">
						调入仓库：
					</td>
					<td width="25%">
						<windrp:getname key='warehouse' value='${ama.inwarehouseid}' p='d'/>
					</td>
				</tr>
				<tr>
					<td align="right">
						付款方式：
					</td>
					<td><windrp:getname key="PaymentMode" p="d" value="${ama.paymentmode}"/>
					</td>
					<td align="right">
						发票抬头：
					</td>
					<td>${ama.tickettitle }</td>
					<td align="right">
						开票信息：
					</td>
					<td>
					<windrp:getname key="InvoiceType"  p="f" value="${ama.invmsg}"/>
					</td>


				</tr>
				<tr>
					<td align="right">
						发运方式：
					</td>
					<td>
						<windrp:getname key="TransportMode" value="${ama.transportmode}"
						p="d" />
					</td>
					<td align="right">
						收货地址：
					</td>
					<td colspan="3">
						${ama.transportaddr}
					</td>
				</tr>
				<tr>
					<td align="right">
						调拨原因：
					</td>
					<td colspan="5">${ama.movecause }
					</td>
				</tr>
			</table>
		</fieldset>
<fieldset align="center">
	<legend>
		<table width="50" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td>
					产品信息
				</td>
			</tr>
		</table>
	</legend>
      <table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td>产品编号</td>
          <td> 产品名称 </td>
          <td>规格</td>
          <td> 单位</td>
          <td>库存</td>
          <td> 单价</td>
          <td> 数量</td>
          <td>批准数量</td>
          <td> 金额</td>
        </tr>
        <c:set var="count" value="2"/>
        <logic:iterate id="p" name="list" >
          <tr class="table-back">
            <td>
            <input name="detailid" id="detailid" type="hidden" value="${p.id}">
           ${p.productid}</td>
            <td >${p.productname}</td>
            <td>${p.specmode}</td>
            <td>${p.unitidname}</td>
            <td><a href="#" onMouseOver="ShowSQ(this,'${p.productid}');" onMouseOut="HiddenSQ();"><img src="../images/CN/stock.gif" width="16"  border="0"></a></td>
            <td><input name="unitprice" type="text" value="${p.unitprice}" onChange="SubTotal(${count});TotalSum();" id="unitprice" size="10" maxlength="10" dataType='Currency' msg='必须录入数值类型!' onkeypress="KeyPress(this);"></td>
            <td><input name="quantity" type="hidden" value="${p.quantity }" id="quantity"/>${p.quantity}</td>
            <td><input name="canquantity" type="text" value="${p.quantity}" onChange="SubTotal(${count});TotalSum();" id="canquantity" size="8" maxlength="8" dataType='Currency' msg='必须录入数值类型!' onkeypress="KeyPress(this);"></td>
            <td><input name="subsum" type="text" value="${p.subsum}" id="subsum" size="10" maxlength="10" readonly></td>
          </tr>
          <c:set var="count" value="${count+1}"/>
        </logic:iterate>
      </table>
      <table width="100%"   border="0" cellpadding="0" cellspacing="0">
        <tr align="center" class="table-back">
          <td width="3%" ></td>
          <td width="11%">&nbsp;</td>
          <td width="7%">&nbsp;</td>
          <td width="69%" align="right"><input type="button" name="button2" value="金额小计" onClick="TotalSum();">：  </td>
          <td width="10%"><input name="totalsum" type="text" id="totalsum" value="${ama.totalsum}" size="10" readonly maxlength="10"></td>
        </tr>
      </table>
      </fieldset>
      	<table width="100%" border="0" cellpadding="0" cellspacing="1">
			<tr align="center">
				<td width="33%">
					<input type="submit" name="Submit" value="确定">
					&nbsp;&nbsp;
					<input type="button" name="Submit2" value="取消"
						onClick="window.close();">
				</td>
			</tr>
		</table></td>
  </tr>
</table>
</form>
</body>
</html>