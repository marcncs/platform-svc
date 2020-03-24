<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>新增生产计划</title>
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

function checknum(obj){ 
	obj.value = obj.value.replace(/[^\d]/g,"");
}
	function checkNumber(obj){
		if(isNaN(obj.value)) {
			obj.value = obj.value.replace(/[^\d.]/g,"");
			obj.value = obj.value.replace(/^\./g,"");
			obj.value = obj.value.replace(/\.{2,}/g,".");
			obj.value = obj.value.replace(".","$#$").replace(/\./g,"","").replace("$#$",".");
		}
	}
	
function SupperSelect(){
	var oid = $('#organid').val();
	if(oid==''||oid==null){
		alert("请先选择工厂再选择产品!");
		return;
	}
	var p=showModalDialog("../common/selectSingleProductAction.do?oid="+oid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}else{
		$("#ProductName").val(p.productname);
		$("#ProductID").val(p.id);
		$("#mCode").val(p.nccode);
		$("#specmode").val(p.specmode);
		$("#abc").val(p.abc);
		$("#wise").val(p.wise);
		$("#copys").val(p.copys);
		if(p.ncc==1) {
			$("#needcovertcode").val(1);
			$("#needcc").show();
			$("#codeFrom").val(p.codeseq);
		} else {
			$("#needcovertcode").val(0);
			$("#needcc").hide();
			$("#codeFrom").val("");
		}
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
		var p=showModalDialog("../common/selectOrganAction.do?type=toller",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		var oldOrgan = document.referForm.organid.value;
			document.referForm.organid.value=p.id;
			document.referForm.orgname.value=p.organname;
	}
	
	function checkSubmit(){
		var organid = $('#organid').val();
		if(organid == undefined || organid.trim() ==""){
			alert("请选择工厂");
			return false;
		}
		var PONO = $('#PONO').val();
		if(PONO == undefined || PONO.trim() ==""){
			alert("请输入PO编号");
			return false;
		}
		var boxnum = $('#boxnum').val();
		if(boxnum == undefined || boxnum.trim() ==""){
			alert("请输入生产箱数");
			return false;
		}
		
		var productId = $('#ProductID').val();
		if(productId == undefined || productId.trim() ==""){
			alert("请选择产品");
			return false;
		}
		var copys = $('#copys').val();
		if(copys == undefined || copys.trim() ==""){
			alert("请输入标签份数");
			return false;
		}
		/*var pbatch = $('#pbatch').val();
		if(pbatch == undefined || pbatch.trim() ==""){
			alert("请输入产品批次");
			return false;
		}*/
		var mbatch = $('#mbatch').val();
		if(mbatch == undefined || mbatch.trim() ==""){
			alert("请输入物料批次");
			return false;
		}
		var proDate = $('#proDate').val();
		if(proDate == undefined || proDate.trim() ==""){
			alert("请选择生产日期");
			return false;
		}
		var packDate = $('#packDate').val();
		if(packDate == undefined || packDate.trim() ==""){
			alert("请选择分装日期");
			return false;
		}
		
		var needcc = $("#needcovertcode").val();
		if(needcc == 1) {
			var codeFrom = $('#codeFrom').val();
			if(codeFrom == undefined || codeFrom.trim() ==""){
				alert("请录入暗码序号");
				return false;
			}
		}
		
		var abc = $('#abc').val();
		if(abc==""||abc==null){
			alert("当前工厂、产品未配置托盘信息");
			return false;
		}else{
			var a = boxnum/abc;
			var b = boxnum%abc;
			if(b==0){
			}else{
				if(!window.confirm("当前生产计划,存在非整托,计"+b+"箱。是否提交?")){
					return false;
				}
			}
			//if(window.confirm("当前计划,生产"+a+"托,余"+b+"箱。是否提交?")){
			
		}
		
		var data_wise = $('#wise').val();
		if(data_wise=="1"){
			if(!window.confirm("当前为License-in产品，请检查数据正确性，提交成功后将不能修改。是否提交?")){
					return false;
				}
		}
	}
	
	function checkIdcodeQuantity(){
		var flag = false;
		// ajax同步检查条码数量
   		$.ajax({
			type:"POST",
			url:"../warehouse/ajaxCheckTakeTicketIdcodeAction.do",
			data:$("#search").serialize(),
			dataType:"json",
			async: false,			
			success:function(msg){
				var popMsg = msg.returnMsg + "\r\n是否继续?";
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
	
	
	
</script>

	</head>
	<body style="overflow: auto;">
		<form id="referForm" name="referForm" method="post"
			action="addProductPlanAction.do" onSubmit="return checkSubmit();">
			<input name="needcovertcode" type="hidden" id="needcovertcode"  value="">
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
										工厂：
									</td>
									<td>
										<input name="organid" type="hidden" id="organid" dataType="Require" msg="请选择工厂!"  value="${organid}">
										<input name="orgname" type="text" id="orgname" size="30"
											dataType="Require" msg="必须录入工厂!"  value="${oname}" readonly>
										<a href="javascript:SelectOrgan2();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
										<span class="style1">*</span>
									</td>
									
									<td align="right">
								   		PO编号：
								   </td>
				   					<td>
				   					   <input type="text" name="PONO" id="PONO" 
				   					   maxlength="16"   dataType="Require" msg="必须录入PO编号!">
				   					     <span class="style1">*</span>
				   					</td>								
									<td align="right">
								   		生产箱数：
								   </td>
				   					<td>
				   					   <input type="text" name="boxnum" id="boxnum" 
				   					   maxlength="8" onkeyup="checknum(this)" onblur="checknum(this)"
				   					    dataType="Require" msg="必须录入箱数!">
				   					    <span class="style1">*</span>
				   					</td>
								</tr>
								<tr>
								<td  align="right">
          						 		产品：
          						 </td>
					            <td>
						            <input type="hidden" name="ProductID" id="ProductID" value="${ProductID}" readonly/>
						             <input id="ProductName" type="text"  name="ProductName" value="${ProductName}" readonly 
						             dataType="Require" msg="必须录入产品!" >
						            <a href="javascript:SupperSelect();">
						            <img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
						            <span class="style1">*</span>
					            </td>
									<td align="right">
								   		物料号：
								   </td>
								   <td>
								   	  <input  style="border-style:solid;border-width:0"  id="mCode"   name="mCode" value="${mCode}" readonly/>
				   					</td>
				   					<td align="right">
								   		规格：
								   </td>
								   <td>
								   	  <input  style="border-style:solid;border-width:0"  id="specmode"   name="specmode" value="${specmode}" readonly/>
				   					</td>            
								</tr>
								<tr>
								   <td align="right">
								   		托盘箱数：
								   </td>
								   <td>
								   	  <input  style="border-style:solid;border-width:0"  id="abc"   name="abc" value="${abc}" readonly/>
				   					</td>
				   					<td align="right">
								   		标签份数：
								   </td>
				   					<td>
				   					   <input type="text" readonly="readonly" name="copys" id="copys"  value="${copys}" 
				   					   maxlength="8" onkeyup="checknum(this)" onblur="checknum(this)"
				   					    dataType="Require" msg="必须录入标签份数!">
				   					    <span class="style1">*</span>
				   					</td>
				   					
				   					
				   					<td align="right">
								   		产品批次：
								   </td>
				   					<td>
				   					   <input type="text" name="pbatch" id="pbatch"
				   					   maxlength="16" dataType="Require" msg="必须录入产品批次!">
				   					</td>
				   						
								</tr>
								<tr>
								<td align="right">
								   		物料批次：
								   </td>
				   					<td>
				   					   <input type="text" name="mbatch" id="mbatch"
				   					   maxlength="16" dataType="Require" msg="必须录入物料批次!">
				   					    <span class="style1">*</span>
				   					</td>
								 <td align="right">
								   		生产日期：
								  </td>
								<td>
								<input name="proDate" type="text" id="proDate" size="10" value="${proDate}"
									onFocus="javascript:selectDate(this)" dataType="Require" msg="请选择生产日期!" readonly>
									 <span class="style1">*</span>
								</td>
								<td align="right">
									分装日期：
								</td>
								<td>
								<input name="packDate" type="text" id="packDate" size="10" value="${packDate}"
									onFocus="javascript:selectDate(this)" dataType="Require" msg="请选择分装日期!"  readonly>
									 <span class="style1">*</span>
									</td>
								
								</tr>
								<tr id="needcc" style="display:none;">
								<td align="right" >
								   		暗码：
								   </td>
				   					<td colspan="5">
				   					   <input type="text" name="codeFrom" id="codeFrom" value="${codeFrom}"
				   					   maxlength="11" dataType="Require" msg="必须录入暗码序号!" onkeyup="checknum(this)">
				   					   <span class="style1">*</span>
<%--				   					   - 
				   					   <input type="text" name="codeTo" id="codeTo"
				   					   maxlength="10" dataType="Require" msg="必须录入暗码序号!" onkeyup="checknum(this)">
				   					    <span class="style1">*</span>--%>
				   					</td> 
								
								
								</tr>
								<tr id="Licensein" style="display:none;">
								<td align="right" >
								   		License-in：
								   </td>
				   					<td colspan="5">
				   					   <input  style="border-style:solid;border-width:0"  id="wise"   name="wise" value="${wise}"/>
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
