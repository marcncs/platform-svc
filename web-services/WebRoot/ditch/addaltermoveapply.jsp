<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/validator.js"></script>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
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
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";
		g.className = "table-back";
		h.className = "table-back";
 
        a.innerHTML="<input type='checkbox' value='' name='che'>";
        b.innerHTML="<input name='productid' type='text' id='productid' value='"+p.productid+"'>";
		c.innerHTML="<input name='productname' type='text' id='productname' size='40' readonly value='"+p.productname+"'>";
		d.innerHTML="<input name='specmode' type='text' id='specmode' size='15' readonly value='"+p.specmode+"'>";
        e.innerHTML="<input name='unitid' type='hidden' id='unitid' size='6' value='"+p.unitid+"'><input name='unit' type='text' id='unit' size='8' readonly value='"+p.unitidname+"'>";
        f.innerHTML="<input name='unitprice' type='text' id='unitprice' readonly value='"+p.unitprice+"' size='10' maxlength='10' onchange='SubTotal("+dbtable.rows.length+");TotalSum();' onFocus='SubTotal("+dbtable.rows.length+");TotalSum();'>";
        g.innerHTML="<input name='quantity' type='text' id='quantity'  dataType='Currency' msg='必须录入数值类型!' onKeyPress='KeyPress(this)' value='1' size='8' maxlength='8' onchange='SubTotal("+dbtable.rows.length+");TotalSum();' onFocus='SubTotal("+dbtable.rows.length+");TotalSum();'>";
        var subsum = formatCurrency(p.unitprice*1);
        h.innerHTML="<input name='subsum' type='text' id='subsum' readonly value='"+subsum+"' size='10' maxlength='10'>";

	TotalSum();
}
	

function SupperSelect(rowx){
	var oid=document.referForm.makeorganid.value;
	if(oid==null||oid=="")
	{
		alert("请选择调人机构！");
		return;
	}
	var p=showModalDialog("../common/toSelectOrganProductPriceAction.do?OID="+oid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	for(var i=0;i<p.length;i++){	
		if ( isready('productid', p[i].productid) ){
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
	
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.referForm.outorganid.value=p.id;
			document.referForm.oname.value=p.organname;

	}
	
	
	function ChkValue(){
		if( $F('inwarehouseid') ==""){
			alert("必须录入调入仓库!");
			return false;
		}
		if ( !Validator.Validate(document.addusers,2) ){
		return false;
		}
		var productid = document.referForm.productid;
		if(productid==null){
			alert("请选择产品");
			return false;
		}
		showloading();
		return true;
		
	}

</script>

	</head>

	<body style="overflow: auto;">
		<form name="referForm" method="post"
			action="addAlterMoveApplyAction.do"
			onsubmit="return ChkValue();">
			<input type="hidden" id="makeorganid" name="makeorganid" value="${organid }">
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
									新增订购申请
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
												
												<td width="10%" align="right">
													调出机构：
												</td>
												<td width="23%">
													<input name="outorganid" type="hidden" id="outorganid" value="${parentOrganid }">
													 <windrp:getname key="organ" p="d" value="${parentOrganid}"/>
												</td>
												<td width="11%" align="right">
													需求日期：
											  </td>
												<td width="21%">
													<input name="movedate" type="text" id="movedate" size="10"
														onFocus="javascript:selectDate(this)" readonly="readonly" 
														value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>" 
														dataType="Require" msg="必须录入调拨需求日期!">
													<span class="STYLE1">*</span>
												</td>
												<td width="9%" align="right">
													调入仓库：
												</td>
												<td width="25%">
                                                <input type="hidden" name="inwarehouseid" id="inwarehouseid" >
									<input type="text" name="wname" id="wname" onClick="selectDUW(this,'inwarehouseid','${organid}','w','transportaddr')" 
									value="<windrp:getname key='warehouse' p='d' value='${smf.inwarehouseid }'/>" readonly>	<span class="STYLE1">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													付款方式：
												</td>
												<td><windrp:paymentmode name="paymentmode" />
												</td>
												<td align="right">
													发票抬头：
												</td>
												<td><input name="tickettitle" type="text" id="tickettitle"
													size="35" ></td>
												<td align="right">
													开票信息：
												</td>
												<td>
												<windrp:select key="InvoiceType" name="invmsg" p="n|f" value=""/>
												</td>
											</tr>
											<tr>
												<td align="right">
													发运方式：
												</td>
												<td>
													<windrp:select key="TransportMode" name="transportmode"
													p="n|d" />
												</td>
												<td align="right">
													收货地址：
												</td>
												<td colspan="3">
													<input type="text" name="transportaddr" id="transportaddr" value="${oaddr }" size="80" maxlength="100" dataType="Require" msg="必须收货地址!">
													<span class="style1">*</span>
												</td>
											</tr>
											<tr>
												<td align="right">
													订购原因：
												</td>
												<td colspan="5">
												<textarea name="movecause" cols="100" style="width: 100%" rows="3"
												id="movecause" dataType="Limit" max="256" msg="订购原因必须在256个字之内"
													require="false"></textarea><br><span class="td-blankout">(订购原因不能超过256个字符!)</span>
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
								<td>
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
											<td width="3%">
												<input type="checkbox" name="checkall" value="on"
													onClick="Check();">
											</td>
											<td width="13%">
												产品编号
											</td>
											<td width="14%">
												产品名称
											</td>
											<td width="14%">
												规格
											</td>
											<td width="14%">
												单位
											</td>
											<td width="14%">
												单价
											</td>
											<td width="14%">
												数量
											</td>
											<td width="14%">
												金额
											</td>
										</tr>
									</table>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr align="center" class="table-back">
											<td width="3%">
												<a href="javascript:deleteR();"><img
														src="../images/CN/del.gif" border="0">
												</a>
											</td>
											<td width="11%">&nbsp;
												
											</td>
											<td width="7%">&nbsp;
												
											</td>
											<td width="64%" align="right">
												<input type="button" name="button" value="总金额"
													onClick="TotalSum();">
											</td>
											<td width="15%">
												<input name="totalsum" type="text" id="totalsum" size="10"
													maxlength="10" readonly>
											</td>
										</tr>
									</table>
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td align="right" width="10%">
												备注：
											</td>
											<td>
												<textarea name="remark" cols="100" style="width: 100%" rows="3"
													id="remark" dataType="Limit" max="256" msg="备注必须在256个字之内"
													require="false"></textarea><br><span class="td-blankout">(备注不能超过256个字符!)</span>
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
