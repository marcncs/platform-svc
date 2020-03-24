<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<title>新增采购换货</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
				<script type="text/javascript" src="../js/validator.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
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
	 
	        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
	        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" value='"+p.productid+"' size=\"12\">";
			c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"40\" readonly value='"+p.productname+"'>";
			d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"40\" readonly value='"+p.specmode+"'>";
	        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"6\" value='"+p.unitid+"'><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" readonly value='"+p.unitname+"'>";
	        f.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value='"+p.price+"' size=\"10\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
	        g.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" onclick='this.select()' onkeydown='onlyNumber(event)' value=\"1\" size=\"8\" maxlength=\"8\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
	        h.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"10\" maxlength=\"10\">";
	
	 		SubTotal(dbtable.rows);
			TotalSum();
		}
	

		function SupperSelect(){
			var pid=document.referForm.cid.value;
			if(pid==null||pid=="")
			{
				alert("请输入供应商！");
				return;
			}
			var p=showModalDialog("../aftersale/toSelectPurchaseWithdrawProductAction.do?pid="+pid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
			for(var i=0;i<p.length;i++){	
				if ( isready('productid', p[i].productid) ){
					continue;
				}
				addRow(p[i]);						
			}
			
		}

		function SelectProvide(){
			var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
			if(p==undefined){
				return;
			}
			document.referForm.cid.value=p.pid;
			document.referForm.cname.value=p.pname;
		}
		
		function SelectLinkman(){
			var pid=document.referForm.cid.value;
			if(pid==null||pid=="")
			{
				alert("请选择供应商！");
				return;
			}
			var l=showModalDialog("../common/selectPlinkmanAction.do?pid="+pid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
			if ( l == undefined ){
				return;
			}
			document.referForm.clinkman.value=l.lname;
			document.referForm.tel.value=l.lmobile;
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
	var pid = document.all("che");
	var checkall = document.all("checkall");
	if (pid==undefined){return;}
	if (pid.length){
		for(i=0;i<pid.length;i++){
				pid[i].checked=checkall.checked;
		}
	}else{
		pid.checked=checkall.checked;
	}		
}

function ChkValue(){
	var cname = document.referForm.cname;
	var clinkman=document.referForm.clinkman;
	var productid = document.referForm.productid;
	
	if(cname.value==null||cname.value==""){
		alert("供应商不能为空!");
		return false;
	}
	if(clinkman.value==null||clinkman.value==""){
		alert("预计取货日期不能为空!");
		return false;
	}		
	if(productid==undefined){
		alert("请选择产品！");
		return false;
	}		
	if ( !Validator.Validate(document.referForm,2) ){
		return false;
	}

	showloading();
	return true;
}
</script>
	</head>

	<body style="overflow: auto;">
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
								修改采购换货
							</td>
						</tr>
					</table>
					<form name="referForm" method="post" action="../aftersale/updPurchaseTradesAction.do" onSubmit="return ChkValue();">
					<table width="100%" border="0" cellpadding="0" cellspacing="1">
						
                        <input name="id" type="hidden" id="id" value="${sof.id}">
						<tr>
							<td>

								<fieldset align="center">
									<legend>
										<table width="50" border="0" cellpadding="0" cellspacing="0">
											<tr>
												<td>
													基本资料
												</td>
											</tr>
										</table>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr>
											<td width="9%" align="right">
												<input name="cid" type="hidden" id="cid" value="${sof.provideid}">
												供应商：
											</td>
											<td width="21%">
												<input name="cname" type="text" id="cname"  dataType="Require" msg="必须录入供应商!" value="${sof.providename}" readonly="readonly"><a href="#"><img src="../images/CN/find.gif" width="18" height="18" align="absmiddle" border="0"></a>
												 <span class="STYLE1">*</span>
											</td>
											<td width="13%" align="right">
												联系人：
											</td>
											<td width="23%">
												<input name="clinkman" type="text" id="clinkman" dataType="Require" msg="必须录入联系人!" value="${sof.plinkman}" readonly><a href="javascript:SelectLinkman();"><img
														src="../images/CN/find.gif" width="18" height="18"
														align="absmiddle" border="0">
												</a>
												<span class="STYLE1">*</span>
											</td>
											<td width="9%" align="right">
												联系电话：
											</td>
											<td width="25%">
												<input name="tel" type="text" id="tel" value="${sof.tel}" dataType="PhoneOrMobile" msg="必须录入正确联系电话!"><span class="STYLE1">*</span>
											</td>
										</tr>
										<tr>

											<td align="right">
												出货仓库：
											</td>
											<td>
												<windrp:warehouse name="warehouseoutid" value="${sof.warehouseoutid}"/>
											</td>
											<td align="right">
												入货仓库：
											</td>
											<td>
												<windrp:warehouse name="warehouseinid" value="${sof.warehouseinid}"/>
											</td>
											<td align="right">
												预计取货日期：
											</td>
											<td>
												<input name="tradesdate" type="text" id="tradesdate"
													readonly onFocus="javascript:selectDate(this)"  dataType="Require" msg="必须录入预计取货时间!"
													value="${sof.tradesdate}" >
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
														<img src="../images/CN/selectp.gif" width="72"  height="21"
															border="0" align="absmiddle" style="cursor: pointer"
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
										<td width="15%">
											产品编号
										</td>
										<td width="20%">
											产品名称
										</td>
										<td width="18%">
											规格型号
										</td>
										<td width="11%">
											单位
										</td>
										<td width="12%">
											单价
										</td>
										<td width="12%">
											数量
										</td>
										<td width="12%">
											金额
										</td>
									</tr>
                                    <c:set var="count" value="2" />
									<logic:iterate id="p" name="als">
										<tr class="table-back">
											<td>
												<input type="checkbox" value="${count}" name="che">
											</td>
											<td>
												<input name="productid" type="text" id="productid"
value="${p.productid}" size="12">
											</td>
											<td>
												<input name="productname" type="text" value="${p.productname}" id="productname" size="40" readonly>
											</td>
											<td>
												<input name="specmode" type="text" value="${p.specmode}" id="specmode" size="40" readonly>
											</td>
											<td>
												<input name="unitid" type="hidden" value="${p.unitid}"><input name="unit" type="text" value="<windrp:getname key='CountUnit' value='${p.unitid}' p='d'/>" id="unit" size="8" readonly>
											</td>
											<td>
												<input name="unitprice" type="text" value="${p.unitprice}" onChange="SubTotal(${count});TotalSum();" id="unitprice" size="10" maxlength="10">
											</td>
											<td><input name="quantity" type="text" value="${p.quantity}" onChange="SubTotal(${count});TotalSum();" id="quantity" size="8" maxlength="8">
											</td>
											<td><input name="subsum" type="text" value="${p.subsum}" id="subsum" size="10" maxlength="10">
											</td>
										</tr>
										<c:set var="count" value="${count+1}" />
									</logic:iterate>
								</table>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr align="center" class="table-back">
										<td width="2%">
											<a href="javascript:deleteR('che','dbtable');"><img
													src="../images/CN/del.gif"  border="0">
											</a>
										</td>
										<td width="11%">&nbsp;
											
										</td>
										<td width="7%">&nbsp;
											
										</td>
										<td width="64%" align="right">
											<input type="button" name="button" value="金额小计"  onClick="TotalSum();">：
										</td>
										<td width="15%">
											<input name="totalsum" type="text" id="totalsum" size="10" maxlength="10">
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
											<textarea name="remark" cols="180" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false">${sof.remark}</textarea><br><span class="td-blankout">(备注长度不能超过256字符)</span>
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
