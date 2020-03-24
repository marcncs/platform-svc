<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>

<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<%--<script src="../js/prototype.js"></script>--%>
		<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
		<style type="text/css">
<!--
.style1 {
	color: #FF0000
}
-->
</style>
	</head>
	<script language="javascript">
var product_hasdouble=false;
/*function checkProduct(){
	var oid = $F('id');
	if ( oid == ''){
		return;
	}
	product_hasdouble = false;
	var url = '../purchase/ajaxProductAction.do';
	var pars = 'id=' + oid;
   	var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showorgan}
				);
}*/
/*
function showorgan(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.product;
	if ( lk == undefined ){
	}else{	
		product_hasdouble=true;	
		alert($F("id")+"此产品编号已经存在，请重新录入！");		
		$('id').select();
	}
}*/

function formChk(){
	var psid = document.validateProduct.psid;
//	var id = document.validateProduct.id.value.trim();
//	var countunit = document.validateProduct.countunit;
	//var sunit = document.validateProduct.sunit;	
	
	if(psid.value==""){
		alert("请选择区域！");
		return false;
	}
	 if(psid.value.length!=5){
 		alert("请选择办事处！");
		return false;
    	}
	/*
	if(id.length!=${mc.extent}){
		alert("请输入"+${mc.extent}+"位产品编号！");
		return false;
	}*/

//	if($("#provinces").val()=="" && $("#citys").val()=="" && $("#areas").val()==""){
//		alert("请选择省，市，区中的一个作为要关联的行政区域！");
//		return false;
//	}
	
	if ( !Validator.Validate(document.validateProduct,2) ){
		return false;
	}
/*
	if(product_hasdouble){
		alert(id.value+"此产品编号已经存在，请重新录入！");		
		return false;
	}
	if(countunit.value != sunit.value){
		var funitid = document.all("funitid");
		var temp = true;
		if(funitid!=undefined){
			if(typeof(funitid.options)!="undefined"){
				if ( funitid.options[funitid.selectedIndex].value == sunit.value){
					temp = false;
				}
			}else{
				if (funitid.length>1) {
					for (var j=0;j<funitid.length;j++){
						if ( funitid[j].value==sunit.value){
							temp = false;
							break;
						}
					}
				}else{
					if ( funitid.options[funitid.selectedIndex].value==sunit.value){
						temp = false;
					}
				}
			}
		}
		if ( temp){
			alert("标准计量单位与最小计量单位不一致，请设置它们的换算率！");
			return false;
		}		
	}*/
	showloading();
	return true;
 }
 
  function addRow(){ 
    var x = document.all("xq").insertRow(xq.rows.length);

	var a=x.insertCell(0);
	var b=x.insertCell(1);
	var c=x.insertCell(2);

	a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
	b.innerHTML='1 ${funitid}';
	c.innerHTML="=<input name=\"xquantity\" type=\"text\" id=\"xquantity\" onKeyUp=\"clearNoNum(this)\" size=\"3\" maxlength='10' dataType=\"Double\" msg=\"换数率只能是数字!\">最小单位"; 
}

function deleteR(){
	chebox=document.all("che");
	if(chebox!=null){	
		if (chebox.length >=1){
			for(var i=1;i<=chebox.length;i++){
			  if (chebox[i-1].checked) {
				document.getElementById('xq').deleteRow ( i);
				i=i-1;
			  }
			}
		}else{
			if (chebox.checked){
			 document.all('xq').deleteRow(1);
			}
		}
 	 }
}

function Check(){
		if(document.validateProduct.checkall.checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
function checkAll(){
	var che=document.all("che");
	if(che!=null){	
		if (che.length){
			for(j=0; j<che.length; j++){
				che[j].checked=true;
			}
		}else{
			che.checked=true;
		}
	 }
}

function uncheckAll(){
	var che=document.all("che");
	if(che!=null){	
		if (che.length){
			for(j=0; j<che.length; j++){
				che[j].checked=false;
			}
		}else{
			che.checked=false;
		}
	 }
}

//单个机构选择
function SelectOrgan(){
var p=showModalDialog("../common/selectOrganRigionAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
if ( p==undefined){return;}
document.validateProduct.MakeOrganID.value=p.id;
document.validateProduct.oname.value=p.organname;
clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
}
//多个机构的选择
function SelectOrganRigion(){
    var oid=new Array();
	var oname=new Array();
	var MakeOrganIDs = document.getElementById('MakeOrganID').value;
	var pArray=showModalDialog("../common/selectOrganRigionAction.do?MakeOrganID="+MakeOrganIDs,null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
    var o;
    if(pArray){
       for(var i=0;i<pArray.length;i++){
         o=pArray[i];
            if (o==undefined){return;}
            oid.push(o.id);
            oname.push(o.organname);
       }
    }
    document.validateProduct.MakeOrganID.value=oid;
    document.validateProduct.oname.value=oname;
}



function selectSaleMan(){
	var pid=new Array();
	var pname=new Array();
	var uid = document.getElementById('uid').value;
	var psid=document.getElementById("psid").value;
	var pArray=showModalDialog("../common/selectSaleManAction.do?uid="+uid+"&psid="+psid,null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
    var p;
    if(pArray){
		for(var i=0;i<pArray.length;i++){
	          p=pArray[i];
	          if ( p==undefined){return;}
	          pid.push(p.uid);
	          pname.push(p.uname);
		   } 
	}
  	document.validateProduct.uid.value=pid;
  	document.validateProduct.uname.value=pname;
}
function SelectProvince(){
	var ids = $("#provinces").val();
	popWin4('../sys/getCountryAreaAction.do?cids='+ids+'&parentids=0&type=0',800,500,'选择省份');
}

function addProvince(ids,name){
$("#provinces").val(ids);
$("#provincesName").val(name);
}

function SelectCity(){
var provinces = $("#provinces").val();
var ids = $("#citys").val();
if(provinces==""){
	alert("请先选择省份");
}else{
	popWin4('../sys/getCountryAreaAction.do?cids='+ids+'&parentids='+provinces+'&type=1',800,500,'选择城市');
}
}

function addCity(ids,name){
$("#citys").val(ids);
$("#citysName").val(name);
}

function SelectAreas(){
	var citys = $("#citys").val();
	var ids = $("#areas").val();
	if(citys==""){
		alert("请先选择城市");
	}else{
		popWin4('../sys/getCountryAreaAction.do?cids='+ids+'&parentids='+citys+'&type=2',800,500,'选择城市');
	}
}

function addArea(ids,name){
	$("#areas").val(ids);
	$("#areasName").val(name);
}



</script>

	<body style="overflow: auto">

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
								关联办事处用户
							</td>
						</tr>
					</table>
					<form action="addRegionAreaAction.do" method="post"
						name="validateProduct" onSubmit="return formChk();">

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
								<!--  -->
								<td width="12%" ></td>
									<td width="12%" align="right">
										区域：
									</td>
							    
									<td width="20%">
										<input type="hidden" name="psid" id="psid" value="${psid}">
										<input type="text" value="${sortname}" readonly>
									</td>
								      <td align="right" width="20%">
										用户选择：
									</td>
									<td>
										<input name="uid" type="hidden" id="uid"
											value="${uid}">
										<input name="uname" type="text" id="uname" size="30"
											value="${uname}" readonly>
										<a href="javascript:selectSaleMan();"><img
												src="../images/CN/find.gif" width="18" height="18"
												border="0" align="absmiddle">
										</a>
									</td>
								</tr>
								
								<!-- 
								<tr>
									<td width="12%" align="right">
										省份：
									</td>
									<td width="22%">
										<input type="hidden" id="provinces" name="provinces" value="" />
										<input type="text" id="provincesName" name="provincesName"
											readonly="readonly" value="" />
										<a href="javascript:SelectProvince();"><img
												src="../images/CN/find.gif" width="18" height="18"
												border="0" align="absmiddle">
										</a>
									</td>
									<td width="10%" align="right">
										城市：
									</td>
									<td width="23%">
										<input type="hidden" id="citys" name="citys" value="" />
										<input type="text" id="citysName" name="citysName"
											readonly="readonly" value="" />
										<a href="javascript:SelectCity();"><img
												src="../images/CN/find.gif" width="18" height="18"
												border="0" align="absmiddle">
										</a>
									</td>
									<td width="12%" align="right">
										地区：
									</td>
									<td width="23%">
										<input type="hidden" id="areas" name="areas" value="" />
										<input type="text" id="areasName" name="areasName"
											readonly="readonly" value="" />
										<a href="javascript:SelectAreas();"><img
												src="../images/CN/find.gif" width="18" height="18"
												border="0" align="absmiddle">
										</a>
									</td>
								</tr>
								 -->
							</table>
						</fieldset>

						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td>
									<div align="center">
										<input type="submit" name="Submit" value="确定">
										&nbsp;&nbsp;
										<input type="button" name="cancel" value="取消"
											onClick="window.close()">
									</div>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
		`
	</body>
</html>
