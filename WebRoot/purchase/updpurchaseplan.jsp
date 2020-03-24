<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/validator.js"></script>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/Currency.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
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
		var j=x.insertCell(9);
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";
		g.className = "table-back";
		h.className = "table-back";
		i.className = "table-back";
		j.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"12\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"35\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"35\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"8\"><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" readonly>";
        f.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0.00\" size=\"10\" maxlength=\"10\">";
        g.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"0\" size=\"8\" maxlength=\"8\">";
        h.innerHTML="<input name=\"requiredate\" type=\"text\" id=\"requiredate\" size=\"10\" onFocus=\"selectDate(this);\">";
		i.innerHTML="<input name=\"advicedate\" type=\"text\" id=\"advicedate\" size=\"10\" onFocus=\"selectDate(this);\">";
		j.innerHTML="<input name=\"requireexplain\" type=\"text\" size=\"10\">";
		i++;
    chebox=document.all("che");  //計算total值
 
}
	

function SupperSelect(rowx){
	//alert(rowx);
	//document.referForm.item("productname")(rowx-2).value = "b";
	var p=showModalDialog("toSelectAllProviderProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");

	var arrid=p.productid;
	var arrpordocutname=p.productname;
	var arrspecmode = p.specmode;
	var unitid=p.unitid;
	var arrcountunit=p.countunit;
	var arrunitprice=p.unitprice;
	
	if((dbtable.rows.length-1) <1){
		for(var i=0;i<arrid.length;i++){
				addRow();
				if(i==0){//第一次加时还不是数组
					document.referForm.item("productid").value =arrid[0];
					document.referForm.item("productname").value =arrpordocutname[0];
					document.referForm.item("specmode").value=arrspecmode[0];
					document.referForm.item("unitid").value =unitid[0];
					document.referForm.item("unit").value =arrcountunit[0];
					document.referForm.item("quantity").value=1;
					document.referForm.item("unitprice").value =formatCurrency(arrunitprice[0]);
					}else{
						document.referForm.item("productid")[i].value =arrid[i];
						document.referForm.item("productname")[i].value =arrpordocutname[i];
						document.referForm.item("specmode")[i].value=arrspecmode[i];
						document.referForm.item("unitid")[i].value =unitid[i];
						document.referForm.item("unit")[i].value =arrcountunit[i];
						document.referForm.item("quantity")[i].value=1;
						document.referForm.item("unitprice")[i].value =formatCurrency(arrunitprice[i]);
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
			document.referForm.item("quantity")[rowx-1+i].value=1;
			document.referForm.item("unitprice")[rowx-1+i].value =formatCurrency(arrunitprice[i]);
			}
	}
	
}




function SubTotal(rowx){
	var sum=0.00;

	var tmprowx = rowx -1;

	if((dbtable.rows.length-1) <=1){
		sum=(document.forms[0].item("unitprice").value)*(document.forms[0].item("quantity").value)
		//	alert("sum--"+sum);
		document.referForm.item("subsum").value=formatCurrency(sum);
	}else{

	//var subsum = 0.00;
		for(var m=0;m<tmprowx;m++){
//alert(m);
//alert("单价"+parseInt(document.forms[0].item("unitprice")(m).value));
//alert("数量"+document.forms[0].item("quantity")(m).value);
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
		}
		document.referForm.item("subsum")(rowx-2).value=formatCurrency(sum);
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
	document.referForm.totalsum.value=formatCurrency(totalsum);
}


	function Check(){

		var checkche  = document.getElementsByName("che");
		var checkall = document.getElementById("checkall");
		for(i=0;i<checkche.length;i++){
			checkche[i].checked = checkall.checked;
		}

	}
	

	function ChkValue(){
		var plandate = document.referForm.plandate;

		if(plandate.value==null||plandate.value==""){
			alert("计划日期不能为空");
			return false;
		}
				
			
		if ( !Validator.Validate(document.addusers,2) ){
		return false;
		}
		var productid = document.referForm.productid;
		
		if(productid==undefined){
			alert("请选择产品！");
			return false;
		}
		showloading();
		return true;
		
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



</script>
	</head>

	<body style="overflow: auto;" >
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
								修改采购计划单
							</td>
						</tr>
					</table>
					<form name="referForm" method="post"
							action="updPurchasePlanAction.do" onSubmit="return ChkValue();">
					<table width="100%" border="0" cellpadding="0" cellspacing="1">
						
						<tr>
							<td align="right">

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
												<input name="ID" type="hidden" id="ID" value="${ppf.id}">
												采购类型：
											</td>
											<td width="21%">
												<windrp:select key="PurchaseSort" name="purchasesort"
													value="${ppf.purchasesort }" p="n|d" />
											</td>
											<td width="13%" align="right">
												相关单据号：
											</td>
											<td width="23%">
												<input name="billno" type="text" id="billno"
													value="${ppf.billno}">
											</td>
											<td align="right">
												计划日期：
											</td>
											<td>
												<input name="plandate" type="text" id="plandate" dataType="Require" msg="计划日期不能为空!"
													onFocus="selectDate(this);" value="<windrp:dateformat value="${ppf.plandate}" p="yyyy-MM-dd"/>" readonly="readonly">
													<span class="style1">*</span>
											</td>

										</tr>
										<tr>
											<td align="right">
												计划机构：
											</td>
											<td>
												<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${ppf.makeorganid}">
<input name="oname" type="text" id="oname" size="30" value="<windrp:getname key='organ' p='d' value='${ppf.makeorganid}'/>" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
											</td>
											
											<td align="right">
												计划部门：
											</td>
											<td>
												<input type="hidden" name="plandept" id="plandept" value='${ppf.plandept}'>
												<input type="text" name="deptname" id="deptname"
													onClick="selectDUW(this,'plandept',$F('MakeOrganID'),'d')"
													value="<windrp:getname key='dept' value='${ppf.plandept}' p='d'/> " readonly  dataType="Require" msg="计划部门不能为空!">
													<span class="style1">*</span>
											</td>
											<td align="right">
												计划人：
											</td>
											<td>
												<input type="hidden" name="planid" id="planid" value='${ppf.planid}'>
												<input type="text" name="uname" id="uname"
													onClick="selectDUW(this,'planid',$F(plandept),'du')"
													value="<windrp:getname key='users' value='${ppf.planid}' p='d'/> " readonly dataType="Require" msg="计划人不能为空!">
													<span class="style1">*</span>
											</td>
										</tr>
									</table>
								</fieldset>

								<table width="100%" border="0" cellpadding="0" cellspacing="1">
									<tr>
										<td width="100%">
											<table border="0" cellpadding="0" cellspacing="1">
												<tr align="center" class="back-blue-light2">
													<td id="elect">
							
														<img src="../images/CN/selectp.gif" border="0"
															style="cursor: pointer"
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
                            <fieldset>
                            <legend>产品资料</legend>
								<table width="100%" id="dbtable" border="0" cellpadding="0"
									cellspacing="1">
									<tr align="center" class="title-top">
										<td width="2%">
											<input type="checkbox" name="checkall" value="on"
												onClick="Check();">
										</td>
										<td width="6%">
											产品编号
										</td>
										<td width="16%">
											产品名称
										</td>
										<td width="14%">
											规格
										</td>
										<td width="5%">
											单位
										</td>
										<td width="8%">
											单价
										</td>
										<td width="5%">
											数量
										</td>
										<td width="16%">
											需求日期
										</td>
										<td width="15%">
											建议定购日期
										</td>
										<td width="13%">
											说明
										</td>
									</tr>
									<c:set var="count" value="2" />
									<logic:iterate id="p" name="als">
										<tr class="table-back">
											<td>
												<input type="checkbox" value="${count}" name="che">
											</td>
											<td>
												<input name="productid" type="text" value="${p.productid}"
													id="productid" size="12">
											</td>
											<td>
												<input name="productname" type="text"
													value="${p.productname}" id="productname" size="35"
													readonly>
											</td>
											<td>
												<input name="specmode" type="text" value="${p.specmode}"
													size="35">
											</td>
											<td>
												<input name="unitid" type="hidden" value="${p.unitid}">
												<input name="unit" type="text" value="${p.unitname}"
													id="unit" size="8" readonly>
											</td>
											<td>
												<input name="unitprice" type="text"
													value="<fmt:formatNumber value='${p.unitprice}' pattern='0.00'/>"
													id="unitprice" size="10" maxlength="10">
											</td>
											<td>
												<input name="quantity" type="text" value="${p.quantity}"
													id="quantity" size="8" maxlength="8">
											</td>
											<td>
												<input name="requiredate" type="text" size="10"
													value="${p.requiredate}" onFocus="selectDate(this);">
											</td>
											<td>
												<input name="advicedate" type="text" size="10"
													value="${p.advicedate}" onFocus="selectDate(this);">
											</td>
											<td>
												<input name="requireexplain" type="text"
													value="${p.requireexplain}" id="requireexplain" size="10"
													maxlength="10">
											</td>
										</tr>
										<c:set var="count" value="${count+1}" />
									</logic:iterate>
								</table>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr align="center" class="table-back">
										<td width="24">
											<a href="javascript:deleteR();"><img
													src="../images/CN/del.gif"  border="0">
											</a>
										</td>
										<td width="151" align="center">&nbsp;
											
										</td>
										<td width="86" align="center">&nbsp;
											
										</td>
										<td width="856" align="right">&nbsp;
											
										</td>
										<td width="124" align="center">&nbsp;
											
										</td>
									</tr>
								</table>
		
								<table width="100%" border="0" cellpadding="0" cellspacing="1">
									<tr>
										<td width="6%" height="77" align="right">
											备注：
										</td>
										<td width="94%">
											<textarea name="remark" cols="150" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false">${ppf.remark}</textarea><br><span class="td-blankout">(备注长度不能超过256字符)</span>
										</td>
									</tr>
								</table>
                                </fieldset>
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
