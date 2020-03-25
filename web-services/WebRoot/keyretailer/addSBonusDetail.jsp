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
		<SCRIPT language="javascript" src="../js/selectunit.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/Currency.js"></script>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script language=javascript>
		//jQuery解除与其它js库的冲突
		var $j = jQuery.noConflict(true);
		var errorMsgTitle = "错误:\t\t\t\n\n";
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
		g.className = "table-back";g.align="center";
		h.className = "table-back";h.align="right";
 
		a.innerHTML="<a onclick='delRow(this);'><img src='../images/CN/delete.gif' width='16' border='0'></a>";
        b.innerHTML="<input name='productid' type='text' id='productid' value='"+p.id+"' readonly>";
        c.innerHTML="<input name='nccode' type='text' id='nccode' value='"+p.mCode+"' readonly>";
		d.innerHTML="<input name='productname' type='text' id='productname' size='40' readonly value='"+p.productname+"'>";
		e.innerHTML="<input name='specmode' type='text' id='specmode' size='40' readonly value='"+p.specmode+"'>";
        var quantity="1";
		//f.innerHTML="<input name='quantity' type='text' id='quantity' dataType='Integer' msg='数量必须录入非0的正整数' onkeyup=\"checknum(this);changeUnit(this.value,'" + p.unitid + "','" + p.countUnitName + "','" + p.unitList + "',this,true);\"  value='"+quantity+"' size='8' maxlength='8'>";
		f.innerHTML="<input name='quantity' type='text' id='quantity' dataType='Integer' msg='数量必须录入非0的正整数' onkeyup=\"checknum(this);\"  value='"+quantity+"' size='8' maxlength='8'>";
		g.innerHTML="<input name='bonusPoint' type='text'  id='bonusPoint'  dataType='Integer' msg='分值必须录入非0的整数' size='20' maxlength='20' onblur='checkNumber(this);'>";
		h.innerHTML="<input name='countUnit' type='hidden' value='"+p.countUnit+"'>";
		//显示计量单位
		//changeUnit(1,p.unitid,p.countUnitName,p.unitList,$j(f).children("input"),false);
		}

	
	//用于动态显示计量单位
	function changeUnit(count,unitId,countUnitName,unitList,obj,flag){

		//当前单位
		var currUnitId = unitId;
		// 当前数量
		var quantity = count;
		unitList = unitList.replace(/=/g,":");
	  	var unitListObjs = eval("(" + unitList + ")");
	  	// 将数据转化为Map
	  	var unitMap = {};
	  	for(var i=0 ; i<unitListObjs.length ; i++){
			var unitObj = unitListObjs[i];
			unitMap[unitObj.unitId] = unitObj.xQuantity;
	 	}
	 	// 转化为计量单位 (最小包装数量  * 计量单位转化率) 
	 	// 0 为最小单位转化率
	 	quantity = quantity * unitMap[currUnitId] * unitMap[0];
	    //设置箱数
	    $j(obj).parent().next().next().html(formatFloat(quantity,2) + "&nbsp;" + countUnitName);
	}

	
	function delRow(obj){
		var rowNo = getTableRowNo(obj);
		document.getElementById('dbtable').deleteRow (rowNo);
	} 
	function getTableRowNo(obj){
		var trSeq = $j(obj).parent().parent().parent().find("tr").index($j(obj).parent().parent()[0]);
		return trSeq;
	}
  function checknum(obj){
	  obj.value = obj.value.replace(/^0|[^\d]/g,"");
  }

  function checkNumber(obj){
	 
	  //var patrn=/^-?[1-9]?\d*$/;
	  var patrn=/^[-]?[1-9]\d*$/;
	  if(!patrn.exec(obj.value)){
          obj.value = obj.value.replace(obj.value,"");
          alert("分值请输入非0的整数");
	  }
 }


function SupperSelect(rowx){
	
	
	var p=showModalDialog("../sap/selectProductAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	if(isreadyPidAndBatch(p.id)){
		return;
	}
	 addRow(p);
	 $j("#quantity").focus();
}

function isreadyPidAndBatch(id){
	var productid = document.all.item("productid");
	if ( productid == null){
		return false;
	}else{
		alert("只能选择一条产品!");
		return true;
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
		var p=showModalDialog("../common/selectOrganAction.do?type=vw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.referForm.receiveorganid.value=p.id;
			document.referForm.oname.value=p.organname;
			getOrganLinkmanBycid(p.id);
			
	}

	function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			var oldOrgan = document.referForm.porganid.value;
			document.referForm.porganid.value=p.id;
			document.referForm.porganname.value=p.organname;
			if(oldOrgan != p.id) {
			clearProductList();
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
			document.referForm.plinkman.value='';
			document.referForm.otel.value='';
		}else{
			document.referForm.plinkman.value=lk.name;
			document.referForm.tel.value=lk.mobile;
			if(document.referForm.tel.value == ""){
				document.referForm.tel.value=lk.officetel;
			}
		}
	}

    
    function SelectLinkman(){
		var receiveorganid=document.referForm.receiveorganid.value;
		if(!receiveorganid){
			document.referForm.receiveorganid.style.color = "red";
			alert(errorMsgTitle+"请选择调入机构!");
			return;
		}
		
		var lk=showModalDialog("../common/selectOrganLinkmanAction.do?cid="+receiveorganid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
		document.referForm.plinkman.value=lk.lname;
		var tel = lk.ltel;
		if(tel==""){
			tel=lk.mobile;
		}
		document.referForm.tel.value=tel;
		
	}
	
	function ChkValue(){
		if ( !Validator.Validate(document.referForm,2) ){
			return false;
		}
		var length = dbtable.rows.length-1;
		if(length==0){
			alert("请选择产品");
			return false;
		}
		showloading();
   	   	return true;
	}

	function clearProductList() {
		var rowNum = document.getElementById('dbtable').rows.length;
		for(var i =0 ; i< rowNum -1 ;i++) {
			document.getElementById('dbtable').deleteRow(1);
		}
	}
</script>

	</head>

	<body style="overflow: auto;">
		<form name="referForm" id="referForm" method="post"
			action="addSBonusDetailAction.do" onsubmit="return ChkValue();">

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
									${operateName }${menu }
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
													当前机构：
												</td>
												<td>
													<input name="porganid" type="hidden" id=porganid value="${MakeOrganID}">
													<input name="porganname" type="text" id="porganname" value="${oname}"
														size="30" dataType="Require" msg="必须录入当前机构!" readonly>
													<a href="javascript:SelectOrgan2();"><img
															src="../images/CN/find.gif" width="18" height="18"
															border="0" align="absmiddle">
													</a>
													<span class="style1">*</span>
												</td>
												<td align="right">
													对方机构：
												</td>
												<td>
													<input name="receiveorganid" type="hidden" id="receiveorganid">
													<input name="oname" type="text" id="oname" size="30" dataType="Require" msg="必须录入对方机构!" readonly>
													<a href="javascript:SelectOrgan();"><img
															src="../images/CN/find.gif" width="18" height="18"
															border="0" align="absmiddle" dataType="Require" msg="必须录入对方机构!">
													</a>
													<span class="style1">*</span>
												</td>
												<%--<td align="right">
													日期：
												</td>
												<td>
													<input name="years" type="text" id="years" size="30" value="${years}"
													onFocus="javascript:selectDate(this)" readonly>
													<span class="style1">*</span>
												</td>
											--%></tr>
											<tr>
												<td align="right">
													
												</td>
												<td>
													<%--<input type="text" id="bonusPoint" name="bonusPoint" size="30"  maxlength="18" dataType="Require" msg="必须录入联系人!">
													<span class="style1">*</span>
												--%></td>
												<td align="right">
													
												</td>
												<td>
												</td>
												<td></td>
												<td></td>
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
												</td>
												<td width="15%">
													产品编号
												</td>
												<td width="16%">
													物料号
												</td>
												<td width="18%">
													产品名称
												</td>
												<td width="10%">
													规格
												</td>
												<td width="10%">
													数量
												</td>
												<td width="10%">
													分值
												</td >
												<td></td>
											</tr>
										</table>
										<table width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr align="center" class="table-back">
												<td width="12%">
													&nbsp;

												</td>
												<td width="7%">
													&nbsp;

												</td>
											</tr>
										</table>
										<table width="100%" border="0" cellpadding="0" cellspacing="1">
											<tr>
												<td align="right" width="10%">
													备注：
												</td>
												<td>
													<textarea name="remark" cols="100"
														style="width: 100%" rows="4" id="remark"
														dataType="Limit" max="50" msg="备注必须在50个字之内"></textarea>
													<br>
													<span class="td-blankout">(调整原因不能超过50个字符!)</span>
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
