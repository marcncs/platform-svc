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
	var ssum=0.00;
	var psum=0.00;
	var rowslength=dbtable.rows.length-1;
	if((dbtable.rows.length-1) <=1){
		psum=(document.forms[0].item("punitprice").value)*(document.forms[0].item("canquantity").value);
		document.validateProvide.item("psubsum").value=psum;
		ssum=(document.forms[0].item("sunitprice").value)*(document.forms[0].item("canquantity").value);
		document.validateProvide.item("ssubsum").value=ssum;
	}else{
		for(var m=0;m<rowslength;m++){
			psum=(document.forms[0].item("punitprice")(m).value)*(document.forms[0].item("canquantity")(m).value);
			document.validateProvide.item("psubsum")(m).value=psum;	
			ssum=(document.forms[0].item("sunitprice")(m).value)*(document.forms[0].item("canquantity")(m).value);
			document.validateProvide.item("ssubsum")(m).value=ssum;	
		}
	}
}


function TotalSum(){
	var totalsum=0.00;
	if((dbtable.rows.length-1) <=1){
		totalsum=totalsum+parseFloat(document.validateProvide.item("psubsum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.validateProvide.item("psubsum")(i).value);
		}
	}
	document.validateProvide.totalsum.value=totalsum;
}


	function ChkValue(){
		
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
<form name="validateProvide" method="post" action="ratifySupplySaleApplyAction.do" onsubmit="return ChkValue();">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td > 批准代销申请单</td>
        </tr>
      </table>
	  <fieldset style="text-align: center;">
			<legend>基本信息</legend>
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
					<td><input type="text" id="tickettitle" name="tickettitle" value="${ama.tickettitle }"></td>
					<td align="right">
						开票信息：
					</td>
					<td>
					<windrp:select name="invmsg"  key="InvoiceType"  p="n|f" value="${ama.invmsg}"/>
					</td>
				</tr>
				<tr>
				<td align="right">
						联系人：
					</td>
					<td>
						${ama.olinkman }
					</td>
					<td align="right">
						联系电话：
					</td>
					<td>
					${ama.otel }
					</td>
					<td align="right">
						发运方式：
					</td>
					<td>
					<windrp:select name="transportmode"  key="TransportMode"  p="n|d" value="${ama.transportmode}"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						收货地址：
					</td>
					<td colspan="5">
						<input type="text" size="80" id="transportaddr" name="transportaddr" value="${ama.transportaddr}" dataType="Require" msg="必须录入送货地址!">
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
<fieldset style="text-align: center;">
	<legend>产品信息</legend>
      <table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td>产品编号</td>
          <td> 产品名称 </td>
          <td>规格</td>
          <td> 单位</td>
          <td>库存</td>
          <td>订购单价</td>
          <td>销售单价</td>
          <td> 数量</td>
          <td>批准数量</td>
          <td>订购金额</td>
          <td>销售金额</td>
        </tr>
        <c:set var="count" value="2"/>
        <logic:iterate id="p" name="list" >
          <tr class="table-back">
            <td>
            <input name="detailid" id="detailid" type="hidden" value="${p.id}">
            ${p.productid}</td>
            <td >${p.productname}</td>
            <td>${p.specmode}</td>
            <td><windrp:getname key="CountUnit" p="d" value="${p.unitid}"/></td>
           <td><a href="#" onMouseOver="ShowSQ(this,'${p.productid}');" onMouseOut="HiddenSQ();"><img src="../images/CN/stock.gif" width="16"  border="0"></a></td>
            <td><input name="punitprice" type="text" value="${p.punitprice}" onChange="SubTotal(${count});TotalSum();" id="punitprice" size="10" maxlength="10" dataType='Currency' msg='必须录入数值类型!' onkeypress="KeyPress(this);"></td>
            <td><input name="sunitprice" type="text" value="${p.sunitprice}" onChange="SubTotal(${count});TotalSum();" id="sunitprice" size="10" maxlength="10" dataType='Currency' msg='必须录入数值类型!' onkeypress="KeyPress(this);"></td>
            <td><input name="quantity" type="hidden" value="${p.quantity}"/>${p.quantity}</td>
            <td><input name="canquantity" type="text" value="${p.quantity}" onChange="SubTotal(${count});TotalSum();" id="canquantity" size="8" maxlength="8" dataType='Currency' msg='必须录入数值类型!' onkeypress="KeyPress(this);"></td>
            <td><input name="psubsum" type="text" value="${p.psubsum}" id="psubsum" size="10" maxlength="10" readonly="readonly"></td>
            <td><input name="ssubsum" type="text" value="${p.ssubsum}" id="ssubsum" size="10" maxlength="10" readonly></td>
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
          <td width="10%"><input name="totalsum" type="text" id="totalsum" value="${ama.totalsum}" size="15" readonly ></td>
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