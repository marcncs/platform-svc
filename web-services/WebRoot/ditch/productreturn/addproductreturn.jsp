<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../../common/tag.jsp"%>

<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/javascript" src="../js/jquery-1.4.2.min.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/validator.js"></script>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectunit.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/Currency.js"></script>
<script language=javascript>
  function addRow(p){
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
		
 
        a.innerHTML="<input type='checkbox' value='' name='che'>";
        b.innerHTML="<input name='productid' type='text' id='productid' value='"+p.productid+"' readonly>";
		c.innerHTML="<input name='productname' type='text' id='productname' size='40' readonly value='"+p.productname+"'>";
		d.innerHTML="<input name='specmode' type='text' id='specmode' size='40' readonly value='"+p.specmode+"'>";
        e.innerHTML="<input name='unitid' type='hidden' id='unitid' size='6' value='"+p.unitid+"'><input name='unit' type='text' id='unit' size='8' readonly value='"+p.unitidname+"' >";
        f.innerHTML="<input name='batch' type='text' id='batch' value='"+p.batch+"' size='10' maxlength='10' readonly>";
        g.innerHTML="<input name='unitprice' type='text' id='unitprice' value='"+p.unitprice+"' size='10' maxlength='10'  readonly>";
        h.innerHTML="<input name='quantity' type='text' id='quantity'  dataType='Currency' msg='必须录入数值类型!' onKeyPress='KeyPress(this)' value='1' size='8' maxlength='8' onchange='SubTotal("+dbtable.rows.length+");TotalSum();' onFocus='SubTotal("+dbtable.rows.length+");TotalSum();'>";
        
        var subsum = formatCurrency(p.unitprice*1);
        i.innerHTML="<input name='subsum' type='text' id='subsum' value='"+subsum+"' size='10' maxlength='10' readonly>";

	TotalSum();
}

function SupperSelect(rowx){
	var wid = document.referForm.outwarehouseid.value;
	if(wid==null||wid=="")
	{
		alert("请选择出货仓库！");
		return;
	}
	var p=showModalDialog("../ditch/toSelectOrganWithdrawProductAction.do?wid="+wid,null,"dialogWidth:22cm;dialogHeight:17cm;center:yes;status:no;scrolling:auto;");
	if(p==undefined){return;}
	for(var i=0;i<p.length;i++){	
		if ( isready('batch', p[i].batch) && isready('productid', p[i].productid) ){
			continue;
		}
		addRow(p[i]);	
			
	}
	
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
		document.referForm.item("subsum").value=formatCurrency(sum);
	}else{

		for(var m=0;m<tmprowx;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
			document.referForm.item("subsum")(m).value=formatCurrency(sum);
		}

	}
	
}

function TotalSum(){
	var totalsum=0.00;
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
	

	
function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
		document.referForm.porganid.value=p.id;
		document.referForm.porganname.value=p.organname;
}

 function SelectOrgan2(){
	var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
		document.referForm.organid.value=p.id;
		document.referForm.orgname.value=p.organname;
		//document.referForm.transportaddr.value=p.oaddr;
		document.referForm.owname.value="";
	//	getOrganLinkmanBycid(p.id);
		
}
	
function SelectLinkman(){
	var porganid=document.referForm.organid.value;
	if(porganid==null||porganid=="")
	{
		alert("请选择供货机构！");
		return;
	}

		var lk=showModalDialog("../common/selectOrganLinkmanAction.do?cid="+porganid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
		document.referForm.plinkman.value=lk.lname;
		var tel = lk.mobile;
		if(tel==""){
			tel=lk.ltel;
		}
		document.referForm.tel.value=tel;
	
}
	
	
function ChkValue(){
	
	if ( !Validator.Validate(document.referForm,2) ){
	return false;
	}
	 if( $F('warehouseid') ==""){
		alert("必须录入出货仓库!");
		return false;
	}
	var productid = document.referForm.productid;
	if(productid==null){
		alert("请选择产品");
		return false;
	}
	return true;
	
}

</script>

	</head>

	<body style="overflow: auto;">
		<form name="referForm" method="post" action="doAddProductReturn.do"	onsubmit="return ChkValue();">
			
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
								<td>
									新增退货
								</td>
							</tr>
						</table>
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
												<td width="11%" align="right">
													供货机构：
												</td>
												<td width="25%">
													<input name="porganid" type="hidden" value="${organid}" id="organid">
													<input name="porganname" value="${orgname}" type="text" id="orgname" size="30" dataType="Require" msg="必须录入调出机构!" readonly><a href="javascript:SelectOrgan2();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
												</td>
												<td width="9%" align="right">
													联系人：
												</td>
												<td width="25%">

													<input type="text" name="plinkman" value="${plinkman }" id="plinkman" dataType="Require" msg="必须录入联系人!"><a href="javascript:SelectLinkman();"><img	src="../images/CN/find.gif" width="19" height="18" align="absmiddle" border="0"> </a>
													<span class="STYLE1">*</span>
												</td>
												<td width="11%" align="right">
													联系电话：
												</td>
												<td width="21%">
													<input name="tel" name="tel" value="${plinkman }" type="text" id="tel" size="10" dataType="PhoneOrMobile" msg="必须录入正确的联系电话!">
													<span class="STYLE1">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													出货仓库：
												</td>
												<td colspan="5">
													<input type="hidden" name="outwarehouseid" value="${outwarehouseid }" id="outwarehouseid">
													<input type="text" name="owname" value="${owname }" id="owname" onClick="selectDUW(this,'outwarehouseid',$F('organid'),'w','')" value="" readonly>	
													<span class="STYLE1">*</span>
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
										<table width="100%" id="dbtable" border="0" cellpadding="0"
											cellspacing="1">
											<tr align="center" class="title-top">
												<td width="2%">
													<input type="checkbox" name="checkall" value="on"
														onClick="Check();">
												</td>
												<td width="18%" >
													产品编号
												</td>
												<td width="18%" >
													产品名称
												</td>
												<td width="10%">
													规格
												</td>
												<td width="10%">
													单位
												</td>
												<td width="10%">
													批次
												</td>
												<td width="10%" >
													单价
												</td>
												<td width="10%" >
													数量
												</td>
												<td width="11%" >
													金额
												</td>
											</tr>
										</table>
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr align="center" class="table-back">
												<td width="2%">
													<a href="javascript:deleteR();"><img
															src="../images/CN/del.gif" border="0"> </a>
												</td>
												<td width="12%">&nbsp;
													
												</td>
												<td width="7%">&nbsp;
													
												</td>
												<td width="64%" align="right">
													<input type="button" name="button" value="总金额"
														onClick="TotalSum();">
												</td>
												<td width="15%">
													<input name="totalsum" type="text" id="totalsum" size="10"
														maxlength="10" readonly="readonly">
												</td>
											</tr>
										</table>
										<table width="100%" border="0" cellpadding="0" cellspacing="1">
											<tr>
												<td align="right" width="10%">
													退货原因：
												</td>
												<td>
													<textarea name="withdrawcause" cols="100"
														style="width: 100%" rows="4" id="withdrawcause"
														dataType="Limit" max="256" msg="备注必须在500个字之内"
														require="false"></textarea><br>
													<span class="td-blankout">(退货原因不能超过256个字符!)</span>
												</td>
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

									</table>
								</td>
							</tr>
							<tr>
							</tr>
						</table>

					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
