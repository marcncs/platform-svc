<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/validator.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language=javascript>
 var iteration=0;
 var i=1;
 var chebox=null;
  function addRow(){
    var x = document.all("dbtable").insertRow(dbtable.rows.length);
 
         var a=x.insertCell(0);
        var b=x.insertCell(1);
        var c=x.insertCell(2);
        var d=x.insertCell(3);
        var e=x.insertCell(4);
        var f=x.insertCell(5);
		var g=x.insertCell(6);
		var h=x.insertCell(7);
		var i=x.insertCell(8);
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";
		g.className = "table-back";
		h.className = "table-back";
		i.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"12\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"40\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"8\"><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" readonly>";
		f.innerHTML="<input name=\"batch\" type=\"text\" id=\"batch\" readonly>";
        g.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0\" size=\"10\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        h.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"0\" size=\"8\" maxlength=\"8\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        i.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"10\" maxlength=\"10\">";
		i++;
    chebox=document.all("che");  //計算total值
 
}
	

function SupperSelect(rowx){
var pid=document.referForm.pid.value;
	if(pid==null||pid=="")
	{
		alert("请输入供应商！");
		return;
	}
	var p=showModalDialog("../purchase/toSelectPurchaseBillAction.do?pid="+pid,null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	
	var arrid=p.productid;
	var arrpordocutname=p.productname;
	var arrspecmode = p.specmode;
	var unitid=p.unitid;
	var arrcountunit=p.countunit;
	var arrunitprice=p.unitprice;
	var arrbatch=p.batch;
	var arrquantity=p.quantity;
	var arrsubsum=p.subsum;
	
	if((dbtable.rows.length-1) <1){
		for(var i=0;i<arrid.length;i++){
				addRow();
				if(i==0){//第一次加时还不是数组
					document.referForm.item("productid").value =arrid[0];
					document.referForm.item("productname").value =arrpordocutname[0];
					document.referForm.item("specmode").value=arrspecmode[0];
					document.referForm.item("unitid").value =unitid[0];
					document.referForm.item("unit").value =arrcountunit[0];
					document.referForm.item("quantity").value=arrquantity[0];
					document.referForm.item("subsum").value=arrsubsum[0];
					document.referForm.item("unitprice").value =arrunitprice[0];
					document.referForm.item("batch").value =arrbatch[0];
					}else{
						document.referForm.item("productid")[i].value =arrid[i];
						document.referForm.item("productname")[i].value =arrpordocutname[i];
						document.referForm.item("specmode")[i].value=arrspecmode[i];
						document.referForm.item("unitid")[i].value =unitid[i];
						document.referForm.item("unit")[i].value =arrcountunit[i];
						document.referForm.item("quantity")[i].value=arrquantity[i];
						document.referForm.item("subsum")[i].value=arrsubsum[i];
						document.referForm.item("unitprice")[i].value =arrunitprice[i];
						document.referForm.item("batch")[i].value =arrbatch[i];
					}
			}
			
			SubTotal(rowx);
			TotalSum();
	}else{
		for(var i=0;i<arrid.length;i++){
			addRow();
			document.referForm.item("productid")[rowx-1+i].value =arrid[i];//加i是从已经有的row行起算从0行开始
			document.referForm.item("productname")[rowx-1+i].value =arrpordocutname[i];
			document.referForm.item("specmode")[rowx-1+i].value=arrspecmode[i];
			document.referForm.item("unitid")[rowx-1+i].value =unitid[i];
			document.referForm.item("unit")[rowx-1+i].value =arrcountunit[i];
			document.referForm.item("quantity")[rowx-1+i].value=arrquantity[i];
			document.referForm.item("subsum")[rowx-1+i].value=arrsubsum[i];
			document.referForm.item("unitprice")[rowx-1+i].value =arrunitprice[i];
			document.referForm.item("batch")[rowx-1+i].value =arrbatch[i];
			}
	}
	
}

function SelectProvide(){
	var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.referForm.pid.value=p.pid;
	document.referForm.providename.value=p.pname;
}

function deleteR(){
	chebox=document.all("che");
	if(chebox!=null){ 
		if (chebox.length >=1){
			for(var i=1;i<=chebox.length;i++){
				if (chebox[i-1].checked) {
					document.getElementById('dbtable').deleteRow (i);
					i=i-1;
			  	}
			}
		}else{
			if (chebox.checked ){
				document.all('dbtable').deleteRow(1);
			}
		}
	}
} 


function SubTotal(rowx){
	var sum=0.00;

	var tmprowx = rowx -1;

	if((dbtable.rows.length-1) <=1){
		sum=(document.forms[0].item("unitprice").value)*(document.forms[0].item("quantity").value)
		//	alert("sum--"+sum);
		document.referForm.item("subsum").value=sum;
	}else{

	//var subsum = 0.00;
		for(var m=0;m<tmprowx;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
		}
		document.referForm.item("subsum")(rowx-2).value=sum;
	}
	
}


function TotalSum(){
	var totalsum=0.00;
	//alert(dbtable.rows.length);
	if((dbtable.rows.length-1) <=1){
		totalsum=totalsum+parseFloat(document.referForm.item("subsum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.referForm.item("subsum")(i).value);
		}
	}
	document.referForm.totalsum.value=totalsum;
}



	function Check(){

		var checkche  = document.getElementsByName("che");
		var checkall = document.getElementById("checkall");
		for(i=0;i<checkche.length;i++){
			checkche[i].checked = checkall.checked;
		}

	}

	
</script>

	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td width="772">
								修改采购发票
							</td>
						</tr>
					</table>
					<form name="referForm" method="post"
							action="updPurchaseInvoiceAction.do"
							onsubmit="return Validator.Validate(document.addusers,2);">
					<table width="100%" border="0" cellpadding="0" cellspacing="1">
						
						<tr>
							<td>

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
											<td width="9%" align="right">
												<input name="id" type="hidden" id="id" value="${pif.id}">
												供应商：
											</td>
											<td width="25%">
												<input name="pid" type="hidden" id="pid"
													value="${pif.provideid}">
												<input name="providename" type="text" id="providename"
													value="${pif.provideidname}" readonly dataType="Require"
													msg="必须录入供应商!"><!--<a href="javascript:SelectProvide();"><img
														src="../images/CN/find.gif" width="18" height="18"
														align="absmiddle" border="0">
												</a>-->
												<span class="STYLE1">*</span>
											</td>
											<td width="9%" align="right">
												发票编号：
											</td>
											<td width="24%">
												<input name="invoicecode" type="text" id="invoicecode"
													value="${pif.invoicecode}" dataType="Require" msg="必须录入发票编号!"><span class="STYLE1">*</span>
											</td>
											<td width="8%" align="right">
												发票类型：
											</td>
											<td width="25%">
												${pif.invoicetypename}
											</td>
										</tr>
										<tr>
											<td align="right">
												制票日期：
											</td>
											<td>
												<input name="makeinvoicedate" type="text" dataType="Require" msg="必须录入制票日期!"
													id="makeinvoicedate" onFocus="selectDate(this);"
													value="${pif.makeinvoicedate}" readonly="readonly">
													<span class="STYLE1">*</span>
											</td>
											<td align="right">
												开票日期：
											</td>
											<td>
												<input name="invoicedate" type="text" id="invoicedate" dataType="Require" msg="必须录入开票日期!"
													onFocus="selectDate(this);" value="${pif.invoicedate}" readonly="readonly">
													<span class="STYLE1">*</span>
											</td>
											<td align="right">&nbsp;
												
											</td>
											<td>&nbsp;
												
											</td>
										</tr>
									</table>
								</fieldset>

								<table width="100%" border="0" cellpadding="0" cellspacing="1">
									<tr>
										<td colspan="4">
											<table border="0" cellpadding="0" cellspacing="1">
												<tr align="center" class="back-blue-light2">
													<td id="elect">
														<img src="../images/CN/selectbill.gif" width="72" height="21"
															border="0" style="cursor: pointer"
															onClick="SupperSelect(dbtable.rows.length)">
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="right">
								<table width="100%" id="dbtable" border="0" cellpadding="0"
									cellspacing="1">
									<tr align="center" class="title-top">
										<td width="2%">
											<input type="checkbox" name="checkall" value="on"
												onClick="Check();">
										</td>
										<td width="39%">
											采购订单号
										</td>
										<td width="34%">
											金额
										</td>
										<td width="26%">
											制单日期
										</td>
									</tr>
									<c:set var="count" value="2" />
									<logic:iterate id="p" name="als">
										<tr class="table-back">
											<td>
												<input type="checkbox" value="${count}" name="che">
											</td>
											<td>
												<input name="poid" type="text" value="${p.poid}" id="poid"
													size="12" readonly>
											</td>
											<td>
												<input name="subsum" type="text" value="${p.subsum}"
													id="subsum" size="40" readonly>
												<a href="javascript:SupperSelect(${count});">
											</td>
											<td>
												<input name="makedate" type="text" id="makedate"
													value="${p.makedate}">
											</td>
										</tr>
										<c:set var="count" value="${count+1}" />
									</logic:iterate>
								</table>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr align="center" class="table-back">
										<td width="2%">
											<a href="javascript:deleteR();"><img
													src="../images/CN/del.gif" border="0">
											</a>
										</td>
										<td width="11%">&nbsp;
											
										</td>
										<td width="7%">&nbsp;
											
										</td>
										<td width="64%" align="right">
											<input type="button" name="button" value="金额小计"
												onClick="TotalSum();">
											：
										</td>
										<td width="15%">
											<input name="totalsum" type="text" id="totalsum"
												value="${pif.invoicesum}" size="10" maxlength="10">
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center">
								<table width="100%" border="0" cellpadding="0" cellspacing="1">
									<tr>
										<td width="6%" height="77" align="right">
											备注：
										</td>
										<td width="94%">
											<textarea name="memo" cols="180" rows="4" id="memo" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false">${pif.memo}</textarea><br><span class="td-blankout">(备注长度不能超过256字符)</span>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center">
								<input type="submit" name="Submit" 
									value="提交">
								&nbsp;&nbsp;
								<input type="button" name="Submit2" value="取消"
									onClick="window.close();">
							</td>
						</tr>
						
					</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
