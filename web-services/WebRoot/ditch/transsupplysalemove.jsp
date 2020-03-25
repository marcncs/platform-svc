<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function Affirm(){
		if(listform.outwarehouseid.value==null || listform.outwarehouseid.value==""){
			alert("请选择调出仓库!")
			//document.listform.receivedate.focus();
			return;
		}
			
		var flag=false;
		var k=0;
		if(document.listform.pid.length>1){
			for(var i=0;i<document.listform.pid.length;i++){
				if(document.listform.pid[i].checked){
					flag=true;//只要选中一个就设为true
					if ( parseFloat(listform.operatorquantity[i].value) > parseFloat(listform.maxquantity[i].value)) {
						alert("本次完成数量不能大于 数量－完成数量!");
						listform.operatorquantity[i].select();
						return;
					}		
				}
			}
		}else{
				if(document.listform.pid.checked){
					flag=true;//只要选中一个就设为true
					if ( parseFloat(listform.operatorquantity.value) > parseFloat(listform.maxquantity.value)) {
						alert("本次完成数量不能大于 数量－完成数量!");
						listform.operatorquantity.select();
						return;
					}
				}
		}
		
		
		if(flag){
			showloading();
			listform.action="transSupplySaleMoveAction.do";
			listform.submit();
		}else{
			alert("请选择产品并设定好数量!");
		}
	}


	
	function Check(){

		var checkche  = document.getElementsByName("pid");
		var checkall = document.getElementById("checkall");
		var operatorquantity = document.getElementsByName("operatorquantity");
		for(i=0;i<checkche.length;i++){
			checkche[i].checked = checkall.checked;
			operatorquantity[i].disabled =!checkall.checked;
		}

	}
	
	
	
	function onlycheck(){
	var pidleng=document.listform.pid.length;
		if(pidleng>1){
			for(var i=0;i<pidleng;i++){
				if(document.listform.pid[i].checked){
					document.listform.operatorquantity[i].disabled=false;
				}
				if(!document.listform.pid[i].checked){
					document.listform.operatorquantity[i].disabled=true;
				}
			}
		}else{
				if(document.listform.pid.checked){
					document.listform.operatorquantity.disabled=false;
				}
				if(!document.listform.pid.checked){
					document.listform.operatorquantity.disabled=true;
				}
		}
	}
	
	function SubTotal(rowx){
	var ssum=0.00;
	var psum=0.00;
	var rowslength=dbtable.rows.length-1;
	//alert("dbtable.rows.length===="+dbtable.rows.length+"    dbtable.rows.==="+dbtable.rows);
	if((dbtable.rows.length-1) <=1){
		psum=(document.forms[0].item("punitprice").value)*(document.forms[0].item("operatorquantity").value);
		document.listform.item("spsubsum").value=psum;
		ssum=(document.forms[0].item("sunitprice").value)*(document.forms[0].item("operatorquantity").value);
		document.listform.item("ssubsum").value=ssum;
	}else{
		for(var m=0;m<rowslength;m++){
			psum=(document.forms[0].item("punitprice")(m).value)*(document.forms[0].item("operatorquantity")(m).value);
			document.listform.item("psubsum")(m).value=psum;	
			ssum=(document.forms[0].item("sunitprice")(m).value)*(document.forms[0].item("operatorquantity")(m).value);
			document.listform.item("ssubsum")(m).value=ssum;	
		}
	}
}


function TotalSum(){
	var totalsum=0.00;
	//alert(dbtable.rows.length);
	if((dbtable.rows.length-1) <=1){
		totalsum=totalsum+parseFloat(document.listform.item("psubsum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.listform.item("psubsum")(i).value);
		}
	}
	document.listform.totalsum.value=totalsum;
}

	
</script>
</head>

<body style="overflow: auto;">
<form name="listform" method="post"  action="../ditch/transSupplySaleMoveAction.do">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">

  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 生成代销单</td>
        </tr>
      </table>
	  
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <td width="9%"  align="right"><input name="aaid" type="hidden" id="aaid" value="${smf.id}">
          	  调拨需求日期：</td>
          <td width="25%">
          <windrp:dateformat value="${smf.movedate}" p="yyyy-MM-dd"/></td>
          <td width="9%" align="right"> 调出机构：</td>
          <td width="24%">
           <windrp:getname key='organ' value='${smf.outorganid}' p='d'/> </td>
          <td width="10%" align="right">调出仓库：</td>
          <td width="23%">
			<windrp:warehouse name="outwarehouseid"/>
          </td>
        </tr>
        <tr>
          <td  align="right">调入机构：</td>
          <td>
             <windrp:getname key='organ' value='${smf.makeorganid}' p='d'/></td>
           	<td  align="right">调入仓库：</td>
          <td>
             <windrp:getname key='warehouse' value='${smf.inwarehouseid}' p='d'/></td>
          <td align="right">付款方式：</td>
          <td>
          
          <windrp:getname key='PaymentMode' value='${smf.paymentmode}' p='f'/></td>
        </tr>
        <tr>
        <td  align="right">联系人：</td>
		<td>${smf.olinkman }
		</td>
		<td  align="right">联系电话：</td>
		<td>
		${smf.otel }
		</td>
        <td align="right">开票信息：</td>
          <td>
          <windrp:getname key="InvoiceType" p="f" value="${smf.invmsg}"/>
          </td>
         
        </tr>
        <tr>
         <td  align="right">发票抬头：</td>
          <td> ${smf.tickettitle}</td>
          <td align="right">发运方式：</td>
          
          <td><windrp:getname key='TransportMode' value='${smf.transportmode}' p='f'/></td>
          <td></td>
          <td></td>
        </tr>
        <tr>
          <td  align="right">收货地址：</td>
          <td colspan="5">
          ${smf.transportaddr}</td>
        </tr>
        <tr>
          <td  align="right">调拨原因：</td>
          <td colspan="5">${smf.movecause}</td>
        </tr>
        <tr>
          <td  align="right">备注：</td>
          <td colspan="5">${smf.remark}</td>
        </tr>
      </table>
	  </fieldset>
	  <fieldset align="center">
		<legend>
						产品信息
		</legend>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" id="dbtable">
          <tr align="center" class="title-top">
            <td><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td>产品编号</td>
            <td>产品名称</td>
            <td>规格</td>
            <td>单位</td>
            <td>订购单价</td>
             <td>销售单价</td>
            <td>数量</td>
			<td>完成数量</td>
			<td>本次完成数量</td>
			<td>订购金额</td>
			<td>销售金额</td>
          </tr>
          <c:set var="count" value="2"/>
		  <logic:iterate id="a" name="als" > 
          <tr align="center" class="table-back">
            <td><input type="checkbox" name="pid" value="${count-2}" onClick="onlycheck();"></td>
            <td><input name="detailid" type="hidden" value="${a.id}" ><input name="productid" type="hidden" id="productid" value="${a.productid}" size="12" >${a.productid}</td>
            <td ><input name="productname" type="hidden" id="productname" value="${a.productname}">${a.productname}</td>
            <td><input name="specmode" type="hidden" id="specmode" value="${a.specmode}" >${a.specmode}</td>			 
            <td><input name="unitid" type="hidden" value="${a.unitid}" size="12"><windrp:getname key="CountUnit" p="d" value="${a.unitid}" /></td>
			<td><input name="punitprice" type="hidden" id="punitprice" size="8" value="${a.punitprice}"> ${a.punitprice}</td>
			<td><input name="sunitprice" type="hidden" id="sunitprice" size="8" value="${a.sunitprice}"> ${a.sunitprice}</td>
			<td>${a.canquantity}<input type="hidden" name="quantity" value="${a.canquantity}"></td>
			<td>${a.alreadyquantity}<input type="hidden" name="maxquantity" value="${a.canquantity - a.alreadyquantity}"></td>
			<td><input name="operatorquantity" type="text" id="operatorquantity" size="8" value="${a.canquantity - a.alreadyquantity}"   onchange="SubTotal(${count});TotalSum();" onFocus="this.select();SubTotal(${count});TotalSum();" disabled></td>
			<td><input name="psubsum" type="text" id="psubsum" size="8" value="${a.psubsum}" readonly></td>
			<td><input name="ssubsum" type="text" id="ssubsum" size="8" value="${a.ssubsum}" readonly></td>
          </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
      </table>
      <table width="100%"   border="0" cellpadding="0" cellspacing="0">
        <tr align="center" class="table-back">
          <td width="7%" >&nbsp;</td>
          <td width="7%">&nbsp;</td>
          <td width="7%">&nbsp;</td>
          <td width="64%" align="right"><input type="button" value="金额小计" onClick="TotalSum()">：</td>
          <td width="15%"><input name="totalsum" type="text" id="totalsum" value="${smf.totalsum}" size="12" maxlength="10" readonly></td>
        </tr>
      </table>
      </fieldset>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
		<tr align="center">
			<td width="33%">
				<input type="button" name="Submit" value="确定" onClick="Affirm()">
				&nbsp;&nbsp;
				<input type="button" name="Submit2" value="取消"
					onClick="window.close();">
			</td>
		</tr>
	</table>
    </td>
  </tr>
 
</table>
 </form>
</body>
</html>
