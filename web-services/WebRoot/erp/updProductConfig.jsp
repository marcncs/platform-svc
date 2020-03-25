<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>新增ERP导入设置</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/validator.js"> </SCRIPT>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/showSQ.js"></SCRIPT>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script language=javascript>

function SupperSelect(rowx){
	
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}else{
		$("#mcode").val(p.nccode);
		$("#productName").val(p.productname);
		$("#specmode").val(p.specmode);
		$("#productid").val(p.id);
	}
	
}

function delRow(obj){
	var rowNo = getTableRowNo(obj);
	document.getElementById('dbtable').deleteRow (rowNo);
} 
function getTableRowNo(obj){
	var trSeq = $(obj).parent().parent().parent().find("tr").index($(obj).parent().parent()[0]);
	return trSeq;
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
	var rowslength=dbtable.rows.length-1;
	if((dbtable.rows.length-1) <=1){
		sum=(document.forms[0].item("unitprice").value)*(document.forms[0].item("quantity").value);
		document.referForm.item("subsum").value=sum;
	}else{
		for(var m=0;m<rowslength;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
			document.referForm.item("subsum")(m).value=sum;	
		}
	}
}
	function Check(){
	
		var checkche  = document.getElementsByName("che");
		var checkall = document.getElementById("checkall");
		for(i=0;i<checkche.length;i++){
			checkche[i].checked = checkall.checked;
		}
	
	}
	
    function getOrganLinkmanBycid(objcid){
        var url = '../sales/ajaxOrganLinkmanAction.do?cid='+objcid;
        var pars = '';
        
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showLinkman}
                    );	
    }
    
    function showLinkman(originalRequest)
    {
		var data = eval('(' + originalRequest.responseText + ')');
		var lk = data.linkman;
		if ( lk == undefined ){
			document.referForm.olinkman.value='';
			document.referForm.otel.value='';
		}else{
			document.referForm.olinkman.value=lk.name;
			document.referForm.otel.value=lk.mobile;
			if(document.referForm.otel.value == ""){
				document.referForm.otel.value=lk.officetel;
			}
		}
	}
	
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=vw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.referForm.receiveorganid.value=p.id;
			document.referForm.oname.value=p.organname;
			document.referForm.wname.value=p.wname;
			document.referForm.inwarehouseid.value=p.wid;
			document.referForm.transportaddr.value=p.waddr;
			getOrganLinkmanBycid(p.id);
			
	}
  function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		var oldOrgan = document.referForm.organid.value;
			document.referForm.organid.value=p.id;
			document.referForm.orgname.value=p.organname;
			//document.referForm.transportaddr.value=p.oaddr;
			document.referForm.outwarehouseid.value=p.wid;
			document.referForm.owname.value=p.wname;
		//	getOrganLinkmanBycid(p.id);
		if(oldOrgan != p.id) {
			clearProductList()
		}
	}
	function clearProductList() {
		var rowNum = document.getElementById('dbtable').rows.length;
		for(var i =0 ; i< rowNum -1 ;i++) {
			document.getElementById('dbtable').deleteRow(1);
		}
	}
	function SelectLinkman(){
		var receiveorganid=document.referForm.receiveorganid.value;
		if(receiveorganid==null||receiveorganid=="")
		{
			alert("请选择调入机构！");
			return;
		}
			var lk=showModalDialog("../common/selectOrganLinkmanAction.do?cid="+receiveorganid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
			document.referForm.olinkman.value=lk.lname;
			var tel = lk.ltel;
			if(tel==""){
				tel=lk.mobile;
			}
			document.referForm.otel.value=tel;
		
	}
	
	function ChkValue(){
		var nums = document.all.item("quantity");
		if($('#organid').val()==""){
			alert("必须录入调出机构!");
			return false;
		}
		if( $('#outwarehouseid').val() ==""){
			alert("必须录入调出仓库!");
			return false;
		}
		if ( !Validator.Validate(document.referForm,2) ){
		return false;
		}		
		if($('#receiveorganid').val()==""){
			alert("必须录入调入机构!");
			return false;
		}
		if( $('#inwarehouseid').val() ==""){
			alert("必须录入调入仓库!");
			return false;
		}
		var productid = document.referForm.productid;
		if(productid==undefined){
			alert("请选择产品");
			return false;
		}
		if(nums.length>=1){
			for(var i = 0; i<nums.length ; i++){
				if(nums[i].value <= 0){
					alert("产品数量不能为0");
					return false;
				 }else if(nums[i].value==""||nums[i].value==null){
					 alert("产品数量不能为空");
					 return false;
				 }
		  }
	    }else if(document.getElementById("quantity").value==null||document.getElementById("quantity").value==""){
		    	alert("产品数量不能为空");
				return false;
	    }else if(document.getElementById("quantity").value==0){
	    	alert("产品数量不能为0");
			return false;
   		}	
   		//检查库存
   	   	return checkStock();
	}
	
	function checkStock(){
		var flag = false;
		// ajax同步检查库存 
   		$.ajax({
			type:"POST",
			url:"../warehouse/ajaxCheckStockpile.do",
			data:$("#referForm").serialize(),
			dataType:"json",
			async: false,			
			success:function(msg){
				var popMsg = msg.returnMsg + "\r\n是否继续保存?";
				var code = msg.returnCode;
				if(code == 0){
					flag = true;
				}else{
					if(confirm(popMsg)){
						flag = true;
					}else{
						flag = false;
					}
				}
			}
   	   	});
   	   	return flag;
	}
	function selectW(dom,type){
		var id = $('#organid').val();
		selectDUW(dom,'outwarehouseid',id,type,'');
		
	}
	function selectWIn(dom){
		var id = $('#receiveorganid').val();
		selectDUW(dom,'inwarehouseid',id,'w','transportaddr')
	}
	function checkSubmit(){
		var organid = $('#organid').val();
		if(organid == undefined || organid.trim() ==""){
			alert("请选择机构");
			return false;
		}
		var productId = $('#productId').val();
		if(productId == undefined || productId.trim() ==""){
			alert("请输入对应产品编号");
			return false;
		}
		var mcode = $('#mcode').val();
		if(mcode == undefined || mcode.trim() ==""){
			alert("请选择物料号");
			return false;
		}
	}
</script>

	</head>
	<body style="overflow: auto;">
		<form id="referForm" name="referForm" method="post"
			action="updProductConfigAction.do" onSubmit="return checkSubmit();">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				bordercolor="#BFC0C1">

				<tr>
					<td>
						<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td>
									${operateName }${menu }
								</td>
							</tr>
						</table>

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

									<td align="right">
										机构：
									</td>
									<td>
										<input name="organid" type="hidden" id="organid"
											value="${productConfigForm.organId}">
										<input name="orgname" type="text" id="orgname" size="30"
											dataType="Require" msg="必须录入调出机构!"
											value="<windrp:getname p='d' key='organ' value='${productConfigForm.organId}' />"
											readonly>
										<a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
										<span class="style1">*</span>
									</td>
									<td align="right">
										ERP产品编号：
									</td>
									<td>
										<input type="hidden" name="id" id="id" value="${productConfigForm.id}" />
										<input type="text" name="productId" id="productId"
											value="${productConfigForm.erpProductId}">
										<span class="STYLE1">*</span>
									</td>
								</tr>
								<tr>

									<td align="right">
										物料号：
									</td>
									<td>
										<input type="text" name="mcode" id="mcode" readonly size="30" value="${productConfigForm.mCode}">
										<a href="javascript:SupperSelect();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
										<span class="STYLE1">*</span>
									</td>
									<td align="right">
										产品名称：
									</td>
									<td>
										<input type="hidden" name="productid" id="productid" value="${productConfigForm.productId}">
										<input type="text" name="productName" id="productName" readonly value="${productConfigForm.productName}">
									</td>
								</tr>
								<tr>
									<td align="right">
										规格：
									</td>
									<td>
										<input type="text" name="specmode" id="specmode" readonly value="${productConfigForm.specmode}">
									</td>
								</tr>
							</table>
						</fieldset>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td align="center">
									<input type="submit" name="Submit" value="提交">
									&nbsp;&nbsp;
									<input type="button" name="button" value="取消" onClick="window.close();">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
