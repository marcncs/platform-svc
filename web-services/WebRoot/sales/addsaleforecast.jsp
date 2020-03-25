<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>新增零售预测</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<script type="text/javascript" src="../js/function.js"></script>
        <SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
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
	        b.innerHTML="<input name='productid' type='text' id='productid' size='12' value='"+p.productid+"' readonly>";
			c.innerHTML="<input name='productname' type='text' id='productname' value='"+p.productname+"' size='40' readonly>";
			d.innerHTML="<input name='specmode' type='text' id='specmode' size='15' value='"+p.specmode+"' readonly>";
			e.innerHTML="<input name='unitid' type='hidden' id='unitid' value='"+p.unitid+"'><input name='unitidname' type='text' id='unitidname' size='10' value='"+p.unitidname+"' readonly>";
	        f.innerHTML="<input name='quantity' type='text' id='quantity' value='1' size='8' maxlength='8' onchange='SubTotal();TotalSum();' onFocus='SubTotal();TotalSum();' >";
			g.innerHTML="<input name='unitprice' type='text' id='unitprice' value='"+p.unitprice+"' size='8' readonly>";
	        h.innerHTML="<input name='subsum' type='text' id='subsum' value='"+p.unitprice+"' size='10' maxlength='10'  readonly>";
	 		SubTotal();TotalSum();
		}
	

		function SupperSelect(rowx){
			var cid = document.referForm.cid.value;
			var objsort = document.referForm.objsort.value;
			var url="";
			if ( cid == "" ){
				alert("请选择客户!");	
				return;
			}
			if ( objsort==0){
				url="../common/toSelectOrganProductPriceAction.do?OID="+cid;
			}else{
				url="../common/toSelectMemberProductPriceAction.do?cid="+cid;
			}
			var p=showModalDialog(url,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
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

function SubTotal(){
	var sum=0.00;
	var unitprice=document.referForm.unitprice;
	var quantity=document.referForm.quantity;
	var objsubsum=document.referForm.subsum;
	if ( unitprice.length){
		for (var m=0; m<unitprice.length; m++){
			objsubsum[m].value=formatCurrency(parseFloat(unitprice[m].value*quantity[m].value));
		}
	}else{
		objsubsum.value=formatCurrency(parseFloat(unitprice.value*quantity.value));
	}
}


function TotalSum(){
	var totalsum=0.00;
	var objsubsum=document.referForm.subsum;
	if ( objsubsum.length){
		for (var m=0; m<objsubsum.length; m++){
			totalsum=totalsum+parseFloat(objsubsum[m].value);
		}
	}else{
		totalsum=parseFloat(objsubsum.value);
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

		function SelectName(){
			var objsort = document.referForm.objsort;
			if(objsort.value ==1){
				SelectCustomer();
			}else{
				SelectOrgan();
			}
		}
		
		function SelectCustomer(){
			var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( c == undefined ){
				return;
			}
			document.referForm.cid.value=c.cid;
			document.referForm.cname.value=c.cname;
		}
		
		function SelectOrgan(){
			var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:25cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if(o==undefined){return;}
			document.referForm.cid.value=o.id;
			document.referForm.cname.value=o.organname;
		}	

		function ChkValue(){
			if ( !Validator.Validate(document.referForm,2) ){
				return false;
			}
			var productid = document.referForm.productid;	
			if ( productid==undefined){
				alert("请选择产品！");
				return false;
			}
			referForm.submit();
			showloading();
		}


</script>

	</head>



	<body style="overflow: auto;">

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
								新增销售预测
							</td>
						</tr>
					</table>
					<form name="referForm" method="post"
							action="addSaleForecastAction.do" onSubmit="return ChkValue();">
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
												对象类型：	</td>
											<td width="25%">
												<windrp:select key="ObjSort" name="objsort" p="n|f" />	</td>

											<td width="12%" align="right">
												客户名称：											</td>
											<td width="20%">
												<input name="cid" type="hidden" id="cid">
												<input name="cname" type="text" id="cname"  dataType="Require" msg="客户不能为空!" readonly><a href="javascript:SelectName();"><img
														src="../images/CN/find.gif" width="18" height="18"
														align="absmiddle" border="0"> </a><span class="style1">*</span>											</td>
											<td align="right">开始日期：											</td>
											<td><input name="forestartdate" type="text" id="forestartdate"
													onFocus="selectDate(this);" readonly="readonly"
													value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"></td>
										</tr>
										<tr>
											<td align="right">结束日期：</td>
											<td><input name="foreenddate" type="text" id="foreenddate"
													onFocus="selectDate(this);" readonly
													value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"></td>
											<td align="right">&nbsp;</td>
											<td>&nbsp;</td>
											<td width="9%" align="right">											</td>
											<td width="25%">											</td>
										</tr>
									</table>
								</fieldset>

								<table width="100%" border="0" cellpadding="0" cellspacing="1">
									<tr>
										<td colspan="4">
											<table border="0" cellpadding="0" cellspacing="1">
												<tr align="center" class="back-blue-light2">
													<td id="elect">
														<img src="../images/CN/selectp.gif" 
															align="absmiddle" border="0" style="cursor: pointer"
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
										<td>
											产品编号
										</td>
										<td>
											产品名称
										</td>
										<td>
											规格
										</td>
                                        <td>
											单位
										</td>
										<td>
											数量
										</td>
                                        <td>
											单价
										</td>
										<td>
											金额
										</td>
									</tr>
								</table>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr align="center" class="table-back">
										<td width="2%">
											<a href="javascript:deleteR();"><img
													src="../images/CN/del.gif"  border="0"> </a>
										</td>
										<td width="11%">&nbsp;
											
										</td>
										<td width="7%">&nbsp;
											
										</td>
										<td width="64%" align="right">
											<input type="button" name="button" value="金额小计"
												onClick="TotalSum();">
										</td>
										<td width="15%">
											<input name="totalsum" type="text" id="totalsum" size="10"
												maxlength="10" readonly="readonly">
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center">
								<input type="button" name="Submit" onClick="ChkValue();"
									value="提交">
								&nbsp;&nbsp;
								<input type="button" name="Submit2" value="取消"
									onClick="javascript:window.close();">
							</td>
						</tr>
						
					</table>
					</form>
				</td>
			</tr>
		</table>
	</body>
</html>
