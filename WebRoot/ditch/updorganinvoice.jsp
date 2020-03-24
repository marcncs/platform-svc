<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>修改采购发票</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/validator.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language=javascript>

  function addRow(p){
    var x = document.all("dbtable").insertRow(dbtable.rows.length);
 
         var a=x.insertCell(0);
        var b=x.insertCell(1);
        var c=x.insertCell(2);
        var d=x.insertCell(3);
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"poid\" type=\"hidden\" id=\"poid\" value='"+p.poid+"'>"+p.poid;
		c.innerHTML="<input name=\"subsum\" type=\"hidden\" id=\"subsum\" size=\"40\" readonly value='"+p.subsum+"'>"+p.subsum;
		d.innerHTML="<input name='type' type='hidden' id='type' value='"+p.type+"'><input name=\"makedate\" type=\"hidden\" id=\"makedate\" size=\"40\" readonly value='"+p.movedate+"'>"+p.movedate;
 		TotalSum()
}
	

function SupperSelect(rowx){
var organid=document.referForm.organid.value;
var inorout = document.referForm.inorout.value;
var billnotype = document.referForm.billnotype.value;
	if(organid==null||organid=="")
	{
		alert("请输入机构！");
		return;
	}
	var p=showModalDialog("../ditch/toSelectStockAlterMoveAction.do?organid="+organid+"&inorout="+inorout+"&billnotype="+billnotype,null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	for(var i=0;i<p.length;i++){
		if ( isready('poid', p[i].poid) ){
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
	document.referForm.InvoiceSum.value=totalsum;
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
			document.referForm.organid.value=p.id;
			document.referForm.organname.value=p.organname;
			document.referForm.sendaddr.value=p.oaddr;
	}


</script>

	</head>

	<body style="overflow:auto">
		<form name="referForm" method="post" action="updOrganInvoiceAction.do"
			onsubmit="return Validator.Validate(document.addusers,2);">
			<input type="hidden" id="id" name="id" value="${ama.id }">
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
								<td >
									修改渠道发票
								</td>
							</tr>
						</table>

						<fieldset align="center">
							<legend>基本信息</legend>
							<table width="100%" border="0" cellpadding="0" cellspacing="1">
								<tr>
								
									<td align="right">
										收支类型：
									</td>
									<td>
										<select name="inorout" id="inorout" onChange="isChang(this)" >
											<option value="0" ${ama.inorout==0?selected:'' }>支出</option>
											<option value="1" ${ama.inorout==1?selected:'' }>收入</option>
										</select>
									</td>
									<td width="12%" align="right">
										发票编号：
									</td>
									<td width="25%">
										<input name="invoicecode" type="text" id="invoicecode" value="${ama.invoicecode }"
											dataType="Require" msg="必须录入发票编号!">
										<span class="STYLE1">*</span>
									</td>
									<td width="9%" align="right">
										发票类型：
									</td>
									<td>
										<windrp:select key="InvoiceType" name="invoicetype" p="n|f" value="${ama.invoicetype}" />
									</td>
								</tr>
								<tr>
										<td width="9%" align="right">
										机构：
									</td>
									<td width="22%">
										<input name="organid" type="hidden" id="organid" value="${ama.organid }">
										<input name="organname" type="text" id="organname" size="30"
										value="<windrp:getname key='organ' p='d' value='${ama.organid }'/>"
											readonly dataType="Require" msg="必须录入机构!"><a  href="#"><img
												src="../images/CN/find.gif" width="18" height="18"
												border="0" align="absmiddle" > </a>
										<span class="STYLE1">*</span>
									</td>
									<td align="right">
										制票日期：
									</td>
									<td>
										<input name="makeinvoicedate" type="text" readonly="readonly"
											id="makeinvoicedate" onFocus="selectDate(this);"
											dataType="Require" msg="必须录入制票日期!"
											value="<windrp:dateformat value="${ama.makeinvoicedate}" p="yyyy-MM-dd"/>" />
										<span class="STYLE1">*</span>
									</td>
									<td align="right">
										开票日期：
									</td>
									<td>
										<input name="invoicedate" type="text" id="invoicedate"
											onFocus="selectDate(this);" readonly="readonly"
											dataType="Require" msg="必须录入开票日期!"
											value="<windrp:dateformat value="${ama.makeinvoicedate}" p="yyyy-MM-dd"/>" />
										<span class="STYLE1">*</span>
									</td>
								</tr>
								<tr>
								
									<td align="right">
										发票抬头：
									</td>
									<td >
										<input type="text" id="invoicetitle" name="invoicetitle" value="${ama.invoicetitle }"/>
									</td>
									<td align="right">
										寄票地址：
									</td>
									<td colspan="3">
										<input type="text" id="sendaddr" name="sendaddr" size="50" maxlength="100" value="${ama.sendaddr }"/>
									</td>
									
								</tr>
								<tr>
								<td align="right">
										单据类型：
									</td>
									<td>
										<windrp:select key="BillNoType" name="billnotype" p="n|f" value="${ama.billnotype}" />
									</td>
									<td align="right">
										发票内容：
									</td>
									<td colspan="3">
										<input type="text" id="invoicecontent" name="invoicecontent" size="50" maxlength="100" value="${ama.invoicecontent }"/>
									</td>
									
								</tr>
							</table>
							
						</fieldset>
						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td colspan="4">

									<img src="../images/CN/selectbill.gif" border="0"
										style="cursor: pointer"
										onClick="SupperSelect(dbtable.rows.length)">
								</td>
							</tr>
						</table>
						<fieldset>
							<legend>订购单信息</legend>
						
					<table width="100%" id="dbtable" border="0" cellpadding="0"
						cellspacing="1">
						<tr align="center" class="title-top">
							<td width="2%">
								<input type="checkbox" name="checkall" value="on"
									onClick="Check();">
							</td>
							<td width="36%">
								订购单号
							</td>
							<td width="25%">
								制单日期
							</td>
							<td width="36%">
								金额
							</td>
						</tr>
						<logic:iterate id="p" name="list" > 
				        <tr class="table-back" onClick="CheckedObj(this);">
				          <td><input type="checkbox" value="" name="che"></td> 
				          <td><input name="poid" type="hidden" id="poid" size="40" readonly value="${p.poid}">${p.poid}</td>
				          <td><input name="makedate" type="hidden" id="makedate" value="${p.makedate}"><windrp:dateformat value='${p.makedate}' p='yyyy-MM-dd'/></td>
				          <td><input name="subsum" type="hidden" id="subsum" value="${p.subsum}"><fmt:formatNumber value='${p.subsum}' pattern='0.00'/></td>
				        </tr>
				        </logic:iterate> 
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr align="center" class="table-back">
							<td width="2%">
								<a href="javascript:deleteR();"><img
										src="../images/CN/del.gif" border="0"> </a>
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
								<input name="invoicesum" type="text" id="InvoiceSum" size="10"
									value="${ama.invoicesum }" maxlength="10" readonly="readonly">
							</td>
						</tr>
					</table>


					<table width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<td height="77" width="9%" align="right">
								备注：
							</td>
							<td>
								<textarea name="memo" style="width: 100%;" rows="4" cols="50" id="memo"
									dataType="Limit" max="256" msg="备注必须在256个字之内" require="false">${ama.memo }</textarea><br>
									<span class="td-blankout">(备注不能超过256个字符!)</span>
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
			</table>

		</form>
	</body>
</html>
