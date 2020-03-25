<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="../css/xtree.css" type="text/css">
<script src="../js/prototype.js"></script>
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xtree.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xmlextras.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xloadtree.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/treeselect.js"></SCRIPT>

<style type="text/css">

<!--
.style1 {color: #FF0000}
-->
</style>
</head>
<script language="javascript"> 
var product_hasdouble=false;
function checkProduct(){
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
}

function showorgan(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.product;
	if ( lk == undefined ){
	}else{	
		product_hasdouble=true;	
		alert($F("id")+"此产品编号已经存在，请重新录入！");		
		$('id').select();
	}
}

function formChk(){
	var psid = document.validateProduct.psid;
	var id = document.validateProduct.id.value.trim();
	var countunit = document.validateProduct.countunit;
	var sunit = document.validateProduct.sunit;	
	if(psid.value==1 || psid.value==""){
		alert("产品类别请选择下级类别！");
		return false;
	}

	if($("#codeType").val()=="") {
		alert("请选择编码规则!");
		return false;
	}
	if($("#linkMode").val()=="") { 
		alert("请选择关联模式!");
		return false;
	}
	if($("#regCertType").val()=="") {
		alert("请选择登记证类型!");
		return false;
	}
	if($("#produceType").val()=="") {	
		alert("请选择生产类型!");
		return false; 
	}
	if($("#innerProduceType").val()=="") {
		alert("请选择内部生产类型!");
		return false;
	}
	if($("#productType").val()=="") {
		alert("请选择产品类型!");
		return false; 
	}
	
	if ( !Validator.Validate(document.validateProduct,2) ){
		return false;
	}

	if(product_hasdouble){
		alert(id.value+"此产品编号已经存在，请重新录入！");		
		return false;
	}
	showloading();
	return true;
 }
 
  function addRow(){ 
	var sunitObj = document.getElementById("sunit");
	var sunitName = sunitObj.options[sunitObj.selectedIndex].text;
    var x = document.all("xq").insertRow(xq.rows.length);

	var a=x.insertCell(0);
	var b=x.insertCell(1);
	var c=x.insertCell(2);
	

	a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
	b.innerHTML='1 ${funitid}';
	c.innerHTML="=<input name=\"xquantity\" type=\"text\" id=\"xquantity\" onKeyUp=\"clearNoNum(this)\" size=\"3\" maxlength='10' dataType=\"Double\" msg=\"换数率只能是数字!\"> " + "<span name='unitName'>" + sunitName + "</span>"; 
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
//异步更改类别英文名
function changePsNameEn(){
	var psNameEnObj = document.getElementById('psNameEn');
	var psidVal = document.getElementById('psid').value;
	var url = "../purchase/ajaxProductStructAction.do";
	$.post(url,{psid:psidVal},function(data){
		psNameEnObj.value=data.ps.sortnameen;
	},"json");
}
// 更改最小包装单位事件
function changeSunit(){
	var sunitObj = document.getElementById("sunit");
	var sunitName = sunitObj.options[sunitObj.selectedIndex].text;
	var objs = $("span[name='unitName']");
	$.each(objs,function(key,obj){
		obj.innerHTML = sunitName;
	});
}

//加载事件
$(function(){
	changePsNameEn();
	var sunitObj = document.getElementById("sunit");
	sunitObj.onchange=changeSunit;
});
//登记证持有人输入的同时将生产企业赋值
function copyValue(){
	document.getElementById('inspectionInstitution').value = document.getElementById('regCertUser').value;
};
</script>

<body style="overflow:auto" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 新增产品</td>
      </tr>
    </table>
        <form action="addProductAction.do" method="post"  name="validateProduct" onSubmit="return formChk();">
         <fieldset align="center">  <legend> 
            <table width="50" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>基本信息</td>
            </tr>
          </table>
		  </legend>
		  
            <table width="100%"  border="0" cellpadding="0" cellspacing="1">
            <tr>
              <td width="12%"  align="right">产品类别：</td>
              <td width="19%">
			   <input type="hidden" name="psid" id="psid" value="${psid}">			
			   <windrp:pstree id="psid" name="productstruts" value="${psidname}" callBack="changePsNameEn()"/>
			   <span class="style1">*</span></td>
			  <td height="20" align="right">类别英文名：</td>
			   <td><input id="psNameEn" name="psNameEn" type="text" value="${psidnameEn }" readonly="readonly" /></td>
				<td width="12%" align="right">产品编号：</td>
              <td width="20%">
                <input name="id" type="text" id="id" maxlength="32" onBlur="checkProduct()" dataType="Require" msg="产品编号不能为空!" value="${productid}" ${isread}>
              <span class="style1">* </span></td>
			</tr>
			<tr>
              <td height="20" align="right">物料号：</td>
			  <td><input name="mCode" type="text" id="mCode"  dataType="Require" msg="物料号不能为空!"><span class="style1">* </span></td>
              <td align="right">物料中文描述：</td>
			   <td><input name="matericalChDes" type="text" id="matericalChDes" ></td>
			   <td align="right">物料英文描述：</td>
			   <td><input name="matericalEnDes" type="text" id="matericalEnDes" ></td>
            </tr>
			 <tr>
			   <td height="20" align="right">产品名称：</td>
			   <td><input name="productname" type="text" id="productname"  maxlength="120" dataType="Require" msg="产品名称不能为空!"> <span class="style1">* </span></td>
			   <td align="right">产品英文名：</td>
			   <td><input name="productnameen" type="text" id="productnameen" size="30" maxlength="128"></td>
			   <td align="right">保质期(天)：</td>
			   <td><input name="expiryDays" type="text" id="expiryDays" dataType="Number" msg="保质期必须为数字" ><span class="style1">* </span></td>
			   <%--<td align="right">规格：</td>
			   <td><input name="specmode" type="text" id="specmode" size="35" maxlength="64"></td>
		      --%></tr>
		      <tr>
			   <td height="20" align="right">包装规格：</td>
			   <td><input name="specmode" type="text" id="specmode" dataType="Require" msg="包装规格不能为空!"><span class="style1">* </span></td>
			   <td align="right">包装规格英文：</td>
			   <td><input name="packSizeNameEn" type="text" id="packSizeNameEn" ></td>
			   <td align="right">规格明细：</td>
			   <td><input name="packSizeName" type="text" id="packSizeName"></td>
		      </tr>
		      <tr>
			   
		      </tr>
		      <tr>
		      	<td align="right">箱码打印：</td>
				<td><windrp:select key="YesOrNo" name="cartonPrintFlag" value="1" p="n|f"/></td>
				<td align="right">小包装打印：</td>
				<td><windrp:select key="YesOrNo" name="primaryPrintFlag" value="1" p="n|f"/></td>
				<td align="right">箱码扫描：</td>
				<td><windrp:select key="YesOrNo" name="cartonScanning" value="1" p="n|f"/></td>
		      </tr>
		      <tr>
				   <td align="right">编码规则：</td>
				   <td><windrp:select key="CodeType" name="codeType" p="y|f"/><span class="style1">* </span></td>
				    <td align="right">关联模式：</td>
				   <td><windrp:select key="LinkMode" name="linkMode" p="y|f"/><span class="style1">* </span></td>
				   <td align="right">标签份数：</td>
				   <td>
				       <input name="copys" type="text" id="copys" dataType="Require" msg="标签份数不能为空!" value="${copys}">
				   	   <span class="style1">* </span>	
				   </td>
				   <td align="right"></td>
				   <td></td>
			      </tr>
			      <tr>
				   <td align="right">登记证类型：</td>
				   <td><windrp:select key="RegCertType" name="regCertType" p="y|f"/><span class="style1">* </span></td>
				    <td align="right">登记证号：</td>
				   <td>
				       <input name="regCertCode" type="text" id="regCertCode" dataType="Require" msg="登记证号不能为空!" value="${regCertCode}">
				   	   <span class="style1">* </span>	
				   </td>
				   <td align="right">登记证号(6位)：</td>
				   <td>
				       <input name="regCertCodeFixed" type="text" maxlength="6" id="regCertCodeFixed" dataType="Require" msg="6位登记证号不能为空!" value="${regCertCodeFixed}">
				   	   <span class="style1">* </span>	
				   </td>
			      </tr>
			      <tr>
			      	<td align="right">登记证持有人：</td>
				   <td>
				       <input name="regCertUser" type="text" id="regCertUser" dataType="Require" msg="登记证持有人不能为空!" value="${regCertUser}" onkeyup="copyValue()">
				   	   <span class="style1">* </span>	
				   </td>
				   <td align="right">产品规格代码：</td>
				   <td>
					   <input name="specCode" type="text" id="specCode" maxlength="3" dataType="Require" msg="产品规格代码不能为空!" value="${standardName}">
				   	   <span class="style1">* </span>				   	
				   </td>
				    <td align="right">农药标准名称：</td>
				   <td>
				   	   <input name="standardName" type="text" id="standardName" dataType="Require" msg="农药标准名称不能为空!" value="${standardName}">
				   	   <span class="style1">* </span>
				   </td>
			      </tr>
			      <tr>
				   <td align="right">生产类型：</td>
				   <td>
				       <windrp:select key="ProduceType" name="produceType" p="y|f"/>
				       <span class="style1">* </span>
				   </td>
				   <td align="right">内部生产类型：</td>
				   <td>
				       <windrp:select key="InnerProduceType" name="innerProduceType" p="y|f"/>
				       <span class="style1">* </span>
				   </td>
				   <td align="right">产品类型：</td>
				   <td>
				   		<select name="productType" id="productType">
				   			<option value="">
								-请选择-
							</option>
							<logic:iterate id="c" name="productTypes">
							<option value="${c.value}">${c.name}</option>
							</logic:iterate>
				   		</select>
				       <span class="style1">* </span>
				   </td>
			      </tr>
			      <tr>
				   <td align="right">生产企业：</td>
				   <td>
				       <input name="inspectionInstitution" type="text" id="inspectionInstitution" dataType="Require" msg="生产企业不能为空!" value="${inspectionInstitution}">
				   	   <span class="style1">* </span>	
				   </td>
<%-- 				   <td align="right">验证结果：</td>
				   <td>
				       <input name="validResult" type="text" id="validResult" dataType="Require" msg="检验机构不能为空!" value="${validResult}">
				   	   <span class="style1">* </span>	
				   </td> --%>
			      </tr>
		       <tr>
             <td  align="right" valign="top">备注：</td>
             <td colspan="5">
             <textarea name="memo" cols="120" rows="10" id="memo" dataType="Limit" max="512"  msg="备注必须在512个字之内" require="false"></textarea><br><span class="td-blankout">(备注长度不能超过512字符)</span></td>
             </tr>
			  </table>
		  </fieldset>
			  <fieldset align="center">  
			  <legend> 
            <table width="50" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>产品属性</td>
            </tr>
          </table>
		  </legend>
		  
            <table width="100%"  border="0" cellpadding="0" cellspacing="1">
            	<tr>
				   <td align="right">是否可用：</td>
				   <td><windrp:select key="YesOrNo" name="useflag" value="1" p="n|f"/></td>
				    <td align="right">是否可分包：</td>
				   <td><windrp:select key="YesOrNo" name="isunify" value="0" p="n|f"/></td>
				   <td align="right">是否License-in：</td>
				   <td><windrp:select key="YesOrNo" name="wise" value="0" p="n|f"/></td>
			      </tr>
				 <tr>
				   <td align="right">小包装单位：</td>
				   <td><windrp:select key="CountUnit" name="sunit" p="n|d"/></td>
				   <td align="right">计量单位：</td>
				   <td><windrp:select key="CountUnit" name="countunit" p="n|d"/></td>
				   <td align="right">小包装到计量单位转化率：</td>
				   <td><input name="boxquantity" type="text" id="boxquantity" size="10" maxlength="10" 
				     dataType="Double" msg="最小包装到计量单位转化率只能为数字!" >
				     <span class="style1">* </span>
				    </td>
			      </tr>
			      <tr>
				   <td align="right">是否印刷二维码产品：</td>
				   <td><windrp:select key="YesOrNo" name="isidcode" value="0" p="n|f"/></td>
				   <td align="right"></td>
				   <td></td>
				   <td align="right"></td>
				   <td></td> 
			      </tr>
             </table>
			  </fieldset>
			  <fieldset align="center"> 
			   <legend> 
			   <table width="25"  border="0" cellpadding="0" cellspacing="0">
	            <tr>
	              <td>单位</td>
	            </tr>
          		</table>
		  	</legend>
			  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
			  <tr>
			   <td  align="right" valign="top">辅助单位：</td>
			   <td colspan="5"><a href="javascript:addRow();"><img src="../images/nolines_plus.gif" width="16" height="18" border="0" title="新增"></a>
			     <a href="javascript:deleteR();"><img src="../images/nolines_minus.gif" width="16" height="18" border="0" title="删除"></a>
			     
				 <table width="271" border="0" id="xq" cellpadding="0" cellspacing="0">
                 <tr>
                   <td width="71"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                   <td width="71" >单位</td>
                   <td width="129">换数率</td>
                 </tr>
                 </table>
          </table>
		  </fieldset>

          <table width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr>
              <td><div align="center">
  				<input type="submit" name="Submit" value="确定">&nbsp;&nbsp;<input type="button" name="cancel" value="取消" onClick="window.close()">
              </div></td>
            </tr>
          </table>
        </form>
 </td>
</tr>
</table>
</body>
</html>
