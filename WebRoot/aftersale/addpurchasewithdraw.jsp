<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/validator.js"></script>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
		<script type="text/javascript" src="../js/Currency.js"></script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language=javascript>
		var iteration=0;
		var i=1;
		var chebox=null;
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
			i++;
	   		chebox=document.all("che");  //計算total值
	
	 		SubTotal(dbtable.rows);
			TotalSum();
		}
	

		function SupperSelect(rowx){
			var pid=document.referForm.pid.value;
			if(pid==null||pid=="")
			{
				alert("请输入供应商！");
				return;
			}
			var p=showModalDialog("../aftersale/toSelectPurchaseWithdrawProductAction.do?pid="+pid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
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
			document.referForm.pid.value=p.pid;
			document.referForm.pname.value=p.pname;
			getLinkman(p.pid);
		}
		
		function getLinkman(v_cid){
			var url = "../sales/ajaxProviderLinkmanAction.do?cid="+v_cid;
			var myAjax = new Ajax.Request(
					url,
					{method: 'get', parameters: '', onComplete: showLman}
					);	
		}
		function showLman(originalRequest){
			var data = eval('(' + originalRequest.responseText + ')');
			var lk = data.linkman;
			if ( lk != undefined ){			
				document.referForm.plinkman.value=lk.name;
				document.referForm.tel.value=lk.mobile;
			}
		}
		
		function SelectLinkman(){
			var pid=document.referForm.pid.value;
			if(pid==null||pid=="")
			{
				alert("请选择供应商！");
				return;
			}
			var l=showModalDialog("../common/selectPlinkmanAction.do?pid="+pid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
			if ( l == undefined ){
				return;
			}
			document.referForm.plinkman.value=l.lname;
			document.referForm.tel.value=l.lmobile;
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
			
	

	
	
	
	function SetCode(c){
		if(c=="r"){
			record.style.display = "";
			elect.style.display = "none";
			bar.style.display = "none";
		}
		if(c=="e"){
			record.style.display = "none";
			elect.style.display = "";
			bar.style.display = "none";
		}
		if(c=="b"){
			record.style.display = "none";
			elect.style.display = "none";
			bar.style.display = "";
		}
		
	}
	
	function RPIDToSearch(){
	
		var rpid = document.validateProvide.RPID.value;
		//alert(rpid);
		if(event.keyCode==13){
		//alert("aa");
		document.getElementById('RPID').select();
		getProductByRPID(rpid);
		}
		//alert("bb");
	}


	function getProductByRPID(rpid){
	//alert("cc");
	   var url = "../sales/getProductByRPIDAjax.do?RPID="+rpid;
	   //document.getElementById("require").style.display="";
	   //document.getElementById("require").innerHTML="正在读取数据...";
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse}
                    );
	}

	function showResponse(originalRequest){
	var product = originalRequest.responseXML.getElementsByTagName("product");
	if(product.length>=1){
	var rm = product[0];
		if((dbtable.rows.length-1) <1){
			addRow();
			//alert("-11-"+product);
			document.validateProvide.item("productid").value =rm.getElementsByTagName("productid")[0].firstChild.data;
			document.validateProvide.item("productname").value =rm.getElementsByTagName("productname")[0].firstChild.data;
			document.validateProvide.item("specmode").value=rm.getElementsByTagName("specmode")[0].firstChild.data;
			document.validateProvide.item("unitid").value =rm.getElementsByTagName("unitid")[0].firstChild.data;
			document.validateProvide.item("unit").value =rm.getElementsByTagName("unitidname")[0].firstChild.data;
			document.validateProvide.item("quantity").value=0;
			document.validateProvide.item("discount").value=0;
			document.validateProvide.item("taxrate").value=0;
			document.validateProvide.item("unitprice").value =rm.getElementsByTagName("unitprice")[0].firstChild.data;
		}else{
		var rowx = dbtable.rows.length;
		  var i=1;
			addRow();
			//alert("-22-");
			document.validateProvide.item("productid")[rowx-1].value =rm.getElementsByTagName("productid")[0].firstChild.data;
			document.validateProvide.item("productname")[rowx-1].value =rm.getElementsByTagName("productname")[0].firstChild.data;
			document.validateProvide.item("specmode")[rowx-1].value=rm.getElementsByTagName("specmode")[0].firstChild.data;
			document.validateProvide.item("unitid")[rowx-1].value =rm.getElementsByTagName("unitid")[0].firstChild.data;
			document.validateProvide.item("unit")[rowx-1].value =rm.getElementsByTagName("unitidname")[0].firstChild.data;
			document.validateProvide.item("quantity")[rowx-1].value=0;
			document.validateProvide.item("discount")[rowx-1].value=0;
			document.validateProvide.item("taxrate")[rowx-1].value=0;
			document.validateProvide.item("unitprice")[rowx-1].value =rm.getElementsByTagName("unitprice")[0].firstChild.data;
		}
		
	}else{
		alert("该编号不存在");
	}

	}


function ShowHD(tbr) {   // yy xx
//alert("db"+dbtable.rows.length);
//alert("abc="+(tbr-2));
var pid = $F('pid');
var productid="";
if(tbr-2<=0){
	if(dbtable.rows.length<=2){
	productid=document.validateProvide.item("productid").value;
	}else{
	productid=document.validateProvide.item("productid")[tbr-2].value;
	}
}else{
//alert(">>>>>2"+document.validateProvide.item("productid")[0].value);
productid=document.validateProvide.item("productid")[tbr-2].value;
}
	$("hd").style.visibility = "visible" ;
	$("hd").style.top = event.clientY;;
	$("hd").style.left = event.clientX;
	//$("require").removeChild($("require").getElementsByTagName("table")[0]);
	if(pid!=""){
	getHistoryPrice(pid,productid);
	}
}
function HiddenHD(){
	hd.style.visibility = "hidden";
	/*
	for(var b=0;b<require.rows.length;b++){
		 	document.getElementById('require').deleteRow(b);
		 	b=b-1;
		 	}
	*/ 
	$("historyprice").removeChild($("historyprice").getElementsByTagName("table")[0]);
}

function getHistoryPrice(pid,productid){
	   var url = "../sales/getHistoryChenAjax.do?pid="+pid+"&productid="+productid;
	   //document.getElementById("require").style.display="";
	   //document.getElementById("require").innerHTML="姝ｅ湪璇诲彇鏁版嵁...";
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showHistory}
                    );
}

function showHistory(originalRequest){
	
	var product = originalRequest.responseXML.getElementsByTagName("product");
	//var x=document.all("require");//.insertRow(desk.rows.length);
	//var strcontent="";
	//alert(proot.length);
	var requireHTML = '<table id="historyprice" width="100%"  border="0" cellpadding="3" cellspacing="0">';

		for(var i=0;i<product.length;i++){
			var rm = product[i];
			var hprice = rm.getElementsByTagName("hprice")[0].firstChild.data;
			var hdate =rm.getElementsByTagName("hdate")[0].firstChild.data;
			//var murl =rm.getElementsByTagName("murl")[0].firstChild.data;
			//alert(ispd);
			requireHTML  += "<tr><td width='50%'>"+hprice+"</td><td width='50%'>"+hdate+" </td></tr>";
			//addRows(productname,productcount,stockcount);
		}

		$("historyprice").innerHTML = requireHTML + "</table>";

		//document.getElementById("result").style.display="none";
	}

</script>
		<style type="text/css">
<!--
.STYLE1 {
	color: #FF0000
}
-->
</style>

		<style type="text/css">
<!--
#hd {
	position: absolute;
	left: 0px;
	top: 0px;
	width: 200px;
	height: auto;
	z-index: 1;
	visibility: hidden;
}
-->
</style>
	</head>

	<body style="overflow: auto;">


		<div id="hd">
			<table width="100%" height="80" border="0" cellpadding="0"
				cellspacing="0" class="GG">
				<tr>
					<td width="50%" height="32" class="title-back">
						历史成交价
					</td>
					<td width="50%" class="title-back">
						成交日期
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div id="historyprice">

						</div>
					</td>
				</tr>
			</table>
		</div>

		<table width="100%" border="1" cellpadding="0" cellspacing="1"
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
								新增采购退货
							</td>
						</tr>
					</table>
					<form name="referForm" method="post"
							action="../aftersale/addPurchaseWithdrawAction.do"
							onsubmit="return Validator.Validate(document.addusers,2);">
					<table width="100%" border="0" cellpadding="0" cellspacing="1">
						
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
												<input name="pid" type="hidden" id="pid">
												供应商：
											</td>
											<td width="21%">
												<input name="pname" type="text" id="pname"  dataType="Require"
													msg="必须录入供应商!" readonly="readonly"><a href="javascript:SelectProvide();"><img
														src="../images/CN/find.gif" width="19" height="18"
														align="absmiddle" border="0">
												</a>
												<span class="STYLE1">*</span>
											</td>
											<td width="13%" align="right">
												联系人：
											</td>
											<td width="23%">
												<input name="plinkman" type="text" id="plinkman" dataType="Require" msg="必须录入联系人!" readonly><a href="javascript:SelectLinkman();"><img
														src="../images/CN/find.gif" width="19" height="18"
														align="absmiddle" border="0">
												</a>
												<span class="STYLE1">*</span>
											</td>
											<td width="9%" align="right">
												联系电话：
											</td>
											<td width="25%">
												<input name="tel" type="text" id="tel" dataType="PhoneOrMobile" msg="必须录入正确联系电话!"><span class="STYLE1">*</span>
											</td>
										</tr>
										<tr>

											<td align="right">
												出货仓库：
											</td>
											<td>
												<select name="warehouseid">
													<logic:iterate id="w" name="alw">
														<option value="${w.id}">
															${w.warehousename}
														</option>
													</logic:iterate>
												</select>
											</td>
											<td align="right">
												退货原因：
											</td>
											<td>
												<input name="withdrawcause" type="text" id="withdrawcause" maxlength="80"
													size="50">
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
										<td width="100%">
											<table border="0" cellpadding="0" cellspacing="1">
												<tr align="center" class="back-blue-light2">

													<td>
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
										<td width="7%">
											产品编号
										</td>
										<td width="16%">
											产品名称
										</td>
										<td width="14%">
											规格型号
										</td>
										<td width="7%">
											单位
										</td>
										<td width="6%">
											单价
										</td>
										<td width="8%">
											数量
										</td>
										<td width="11%">
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
											<input type="button" name="button" value="金额小计"
												onClick="TotalSum();">
											：
										</td>
										<td width="15%">
											<input name="totalsum" type="text" id="totalsum" size="10"
												maxlength="10">
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center">
								<input type="submit" name="Submit" 
									value="确定">
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
