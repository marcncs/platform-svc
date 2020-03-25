<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>新增采购计划</title>
		<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
        <SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<script type="text/javascript" src="../js/validator.js"></script>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
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
        g.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1\" size=\"8\" maxlength=\"8\">";
        h.innerHTML="<input name=\"requiredate\" type=\"text\" id=\"requiredate\" size=\"10\" value=\"<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>\" onFocus=\"selectDate(this);\">";
		i.innerHTML="<input name=\"advicedate\" type=\"text\" id=\"advicedate\" size=\"10\" value=\"<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>\" onFocus=\"selectDate(this);\">";
		j.innerHTML="<input name=\"requireexplain\" type=\"text\" size=\"10\">";
		i++;
    chebox=document.all("che");  //計算total值
 
}
	

function SupperSelect(rowx){
	var p=showModalDialog("toSelectAllProviderProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	
	if(p==undefined)return;
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
		return true;
		
	}
	function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
		document.referForm.MakeOrganID.value=p.id;
		document.referForm.oname.value=p.organname;
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
								新增采购计划单
							</td>
						</tr>
					</table>
					<form name="referForm" method="post"
							action="addPurchasePlanAction.do" onSubmit="return ChkValue();">
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
											<td align="right">采购类型：</td>
											<td>
												<windrp:select key="PurchaseSort" name="purchasesort" p="n|d" />
											</td>
											<td align="right">
												相关单据号：
											</td>
											<td>
												<input name="billno" type="text" id="billno">
											</td>
											<td align="right">
												计划日期：
											</td>
											<td>
												<input name="plandate" type="text" id="plandate" readonly
													onFocus="selectDate(this);" dataType="Require" msg="计划日期不能为空!"
													value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>">
													<span class="style1">*</span>
											</td>
										</tr>
										<tr>
											<td align="right">
												计划机构：
											</td>
											<td>
												<input name="MakeOrganID" type="hidden" id="MakeOrganID">
<input name="oname" type="text" id="oname" size="30" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
											</td>
											<td align="right">
												计划部门：
											</td>
											<td>
												<input type="hidden" name="plandept" id="plandept">
												<input type="text" name="deptname" id="deptname"
													onClick="selectDUW(this,'plandept',$F('MakeOrganID'),'d')"
													value="" readonly dataType="Require" msg="计划部门不能为空!">
													<span class="style1">*</span>
											</td>
											<td align="right">
												计划人：
											</td>
											<td>
												<input type="hidden" name="planid" id="planid">
												<input type="text" name="uname" id="uname"
													onClick="selectDUW(this,'planid',$F(plandept),'du')"
													value="" readonly dataType="Require" msg="计划人不能为空!">
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
															style="cursor: pointer" align="absmiddle"
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
										<td width="3%">
											<input type="checkbox" name="checkall" value="on"
												onClick="Check();">
										</td>
										<td width="10%">
											产品编号
										</td>
										<td width="19%">
											产品名称
										</td>
										<td width="19%">
											规格
										</td>
										<td width="5%">
											单位
										</td>
										<td width="6%">
											单价
										</td>
										<td width="6%">
											数量
										</td>
										<td width="10%">
											需求日期
										</td>
										<td width="10%">
											建议定购日期
										</td>
										<td width="12%">
											说明
										</td>
									</tr>
								</table>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr align="center" class="table-back">
										<td width="20">
											<a href="javascript:deleteR();"><img
													src="../images/CN/del.gif"  border="0">
											</a>
										</td>
										<td width="11%" align="center">&nbsp;
											
										</td>
										<td width="7%" align="center">&nbsp;
											
										</td>
										<td width="69%" align="right">&nbsp;
											
										</td>
										<td width="10%" align="center">&nbsp;
											
										</td>
									</tr>
								</table>
		
								<table width="100%" border="0" cellpadding="0" cellspacing="1">
									<tr>
										<td width="6%" height="77" align="right">
											备注：
										</td>
										<td width="94%">
											<textarea name="remark" cols="100" style="width: 100%" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false"></textarea><br><span class="td-blankout">(备注长度不能超过256字符)</span>
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
