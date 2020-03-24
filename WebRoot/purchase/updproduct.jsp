<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT LANGUAGE="javascript" src="../js/validator.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/jquery-1.4.2.min.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</head>
<script type="text/javascript">


function formChk(){ 
 	var psid = document.validateProduct.psid;
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
	showloading();
	return true;
 }
 
 
 
 var i=1;
 var chebox=null;
 
  function addRow(){
	  var sunitObj = document.getElementById("sunit");
		var sunitName = sunitObj.options[sunitObj.selectedIndex].text;

	    var x = document.all("xq").insertRow(xq.rows.length);

    
        var a=x.insertCell(0);
        var b=x.insertCell(1);
        var c=x.insertCell(2);

        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML='1 ${funitid}';
		c.innerHTML="=<input name=\"xquantity\" type=\"text\" id=\"xquantity\" onKeyUp=\"clearNoNum(this)\" dataType=\"Double\" msg=\"换数率只能是数字!\" maxlength='10' size=\"3\">" + "<span name='unitName'>" + sunitName + "</span>";

		i++;
    chebox=document.all("che");  //計算total值
 
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

//更改最小包装单位事件
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
</script>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772"> 修改产品</td>
  </tr>
</table>

        <form action="updProductAction.do" method="post" name="validateProduct" onSubmit="return formChk();">
          <fieldset align="center">
          <legend> 
            <table width="50" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>基本信息</td>
            </tr>
          </table>
		  </legend>
            <table width="100%"  border="0" cellpadding="0" cellspacing="1">
            <tr>
              <td width="11%"  align="right"><input type="hidden" name="id" value="${p.id}">产品类别：</td>
              <td width="20%">
			  <input type="hidden" name="psid" id="psid" value="${p.psid}">			
			  <windrp:pstree id="psid" name="productstruts" value="${psidname}" callBack="changePsNameEn()"/>
			  <span class="style1">*</span></td>
			  <td height="20" align="right">类别英文名：</td>
			   <td><input id="psNameEn" name="psNameEn" type="text" value="${psidnameEn }" readonly="readonly"/></td>
			 <td width="9%" align="right">产品编号：</td>
              <td width="25%"><input name="id" type="text" id="id" maxlength="32" onBlur="checkProduct()" value="${p.id}" readonly><span class="style1">*</span> </td>
			 </tr>
			 <tr>
               <td height="20" align="right">物料号：</td>
			   <td><input name="mCode" type="text" id="mCode" value="${p.mCode}" dataType="Require" msg="物料号不能为空!"></td>
              <td align="right">物料中文描述：</td>
			   <td><input name="matericalChDes" type="text" id="matericalChDes" value="${p.matericalChDes}"></td>
			   <td align="right" nowrap>物料英文描述：</td>
			   <td><input name="matericalEnDes" type="text" id="matericalEnDes" value="${p.matericalEnDes}"></td>
            </tr>
			<tr>
			  <td height="20" align="right">产品名称：</td>
			  <td><input name="productname" type="text" value="${p.productname}" id="productname" size="30" maxlength="128" dataType="Require" msg="产品名称不能为空!"><span class="style1">*</span></td>
			  <td align="right">产品英文名：</td>
			  <td><input name="productnameen" type="text" id="productnameen" value="${p.productnameen}" size="30" maxlength="128"></td><%--
			  <td align="right">规格：</td>
			  <td><input name="specmode" type="text" id="specmode" value="${p.specmode}" size="35" maxlength="64"></td>
			  --%>
			  <td align="right">保质期(天)：</td>
			   <td><input name="expiryDays" type="text" id="expiryDays" value="${p.expiryDays}" dataType="Number" msg="保质期必须为数字"></td>
			  </tr>
			  <tr>
			   <td height="20" align="right">包装规格：</td>
			   <td><input name="specmode" type="text" id="specmode" value="${p.specmode}" dataType="Require" msg="包装规格不能为空!"><span class="style1">*</span></td>
			   <td align="right">包装规格英文：</td>
			   <td><input name="packSizeNameEn" type="text" id="packSizeNameEn" value="${p.packSizeNameEn}"></td>
			   <td height="20" align="right">规格明细：</td>
			   <td><input name="packSizeName" type="text" id="packSizeName" value="${p.packSizeName}"></td>
		      </tr>
		      <tr>
			   
		      </tr>
		      <tr>
		      	<td align="right">箱码打印：</td>
				<td><windrp:select key="YesOrNo" name="cartonPrintFlag" value="${p.cartonPrintFlag}" p="n|f" /></td>
				<td align="right">小包装打印：</td>
				<td><windrp:select key="YesOrNo" name="primaryPrintFlag" value="${p.primaryPrintFlag}" p="n|f"/></td>
				<td align="right">箱码扫描：</td>
				<td><windrp:select key="YesOrNo" name="cartonScanning" value="${p.cartonScanning}" p="n|f"/></td>
		      </tr>
			  <tr>
				   <td align="right">编码规则：</td>
				   <td><windrp:select key="CodeType" name="codeType" value="${p.codeType}" p="y|f"/><span class="style1">* </span></td>
				    <td align="right">关联模式：</td>
				   <td><windrp:select key="LinkMode" name="linkMode" value="${p.linkMode}" p="y|f"/><span class="style1">* </span></td>
				   <td align="right">标签份数：</td>
				   <td>
				       <input name="copys" type="text" id="copys" dataType="Require" msg="标签份数不能为空!" value="${p.copys}">
				   	   <span class="style1">* </span>	
				   </td>
				   <td align="right"></td>
				   <td></td>
			      </tr>
			      <tr>
				   <td align="right">登记证类型：</td>
				   <td><windrp:select key="RegCertType" name="regCertType" value="${p.regCertType}" p="y|f"/><span class="style1">* </span></td>
				    <td align="right">登记证号：</td>
				   <td>
				       <input name="regCertCode" type="text" id="regCertCode" value="${p.regCertCode}" dataType="Require" msg="登记证号不能为空!" value="${p.regCertCode}">
				   	   <span class="style1">* </span>	
				   </td>
				   <td align="right">登记证号(6位)：</td>
				   <td> 
				       <input name="regCertCodeFixed" type="text" maxlength="6" id="regCertCodeFixed" dataType="Require" msg="6位登记证号不能为空!" value="${p.regCertCodeFixed}">
				   	   <span class="style1">* </span>
				   </td>
			      </tr>
			      <tr>
			      	<td align="right">登记证持有人：</td>
				   <td>
				       <input name="regCertUser" type="text" id="regCertUser" dataType="Require" msg="登记证持有人不能为空!" value="${p.regCertUser}">
				   	   <span class="style1">* </span>	
				   </td>
				   <td align="right">产品规格代码：</td>
				   <td>
					   <input name="specCode" type="text" id="specCode" maxlength="3" dataType="Require" msg="产品规格代码不能为空!" value="${p.specCode}">
				   	   <span class="style1">* </span>				   	
				   </td>
				    <td align="right">农药标准名称：</td>
				   <td>
				   	   <input name="standardName" type="text" id="standardName" dataType="Require" msg="农药标准名称不能为空!" value="${p.standardName}">
				   	   <span class="style1">* </span>
				   </td>
			      </tr>
			      <tr>
				   <td align="right">生产类型：</td>
				   <td>
				       <windrp:select key="ProduceType" name="produceType" value="${p.produceType}" p="y|f"/>
				       <span class="style1">* </span>
				   </td>
				   <td align="right">内部生产类型：</td>
				   <td>
				       <windrp:select key="InnerProduceType" name="innerProduceType" value="${p.innerProduceType}" p="y|f"/>
				       <span class="style1">* </span>
				   </td>
				   <td align="right">产品类型：</td>
				   <td>
				   		<select name="productType" id="productType">
				   			<option value="">
								-请选择-
							</option>
							<logic:iterate id="c" name="productTypes">
							<option value="${c.value}" ${c.value == p.productType ?"selected":""} >${c.name}</option>
							</logic:iterate>
				   		</select>
				       <span class="style1">* </span>
				   </td>
			      </tr>
			      <tr>
				   <td align="right">生产企业：</td>
				   <td>
				       <input name="inspectionInstitution" type="text" id="inspectionInstitution" dataType="Require" msg="生产企业不能为空!" value="${p.inspectionInstitution}">
				   	   <span class="style1">* </span>	
				   </td>
				   <%-- <td align="right">验证结果：</td>
				   <td>
				       <input name="validResult" type="text" id="validResult" dataType="Require" msg="检验机构不能为空!" value="${p.validResult}">
				   	   <span class="style1">* </span>	
				   </td> --%>
			      </tr>
			  <tr>
              <td  align="right">备注：</td>
              <td colspan="5"><textarea name="memo" cols="120" rows="10" id="memo" dataType="Limit" max="512"  msg="备注必须在512个字之内" require="false">${p.memo}</textarea><br><span class="td-blankout">(备注长度不能超过512字符)</span></td>
              </tr>
			  </table>
		  </fieldset>
		  
		  
		  <fieldset align="center">  <legend> 
            <table width="50" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>产品属性</td>
            </tr>
          </table>
		  </legend>
		  
            <table width="100%"  border="0" cellpadding="0" cellspacing="1">
             <tr>
             	<td align="right">是否可用：</td>
                <td><windrp:select key="YesOrNo" name="useflag" value="${p.useflag}" p="n|f"/></td>
                <td align="right">是否可分包：</td>
                <td><windrp:select key="YesOrNo" name="isunify" value="${p.isunify}" p="n|f"/></td>
                <td align="right">是否License-in：</td>
                <td><windrp:select key="YesOrNo" name="wise" value="${p.wise}" p="n|f"/></td>
             </tr> 
			 <tr>
			    <td align="right">小包装单位：</td>
			    <td><windrp:select key="CountUnit" name="sunit" value="${p.sunit}" p="n|d"/></td>
                <td align="right">计量单位：</td>
			    <td><windrp:select key="CountUnit" name="countunit" value="${p.countunit}" p="n|d"/></td>
                <td align="right">小包装到计量单位转化率：</td>
				   <td><input name="boxquantity" type="text" value="${p.boxquantity}" id="boxquantity" size="10"
				    maxlength="10" dataType="Double" msg="最小包装到计量单位转化率只能为数字!" >
				     <span class="style1">* </span>
				    </td>
			 </tr>
		  <tr>
		   <td align="right">是否印刷二维码产品：</td>
		   <td><windrp:select key="YesOrNo" name="isidcode" value="${p.isidcode}" p="n|f"/></td>
		   <td align="right"></td>
		   <td></td>
		   <td align="right"></td>
		   <td></td>
	      </tr>
			  </table>
		  </fieldset>
		  
		  
			  
		 <fieldset align="center">
          <legend> 
            <table width="25" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>单位</td>
            </tr>
          </table>
		  </legend>
			   <table width="100%"  border="0" cellpadding="0" cellspacing="1">
			   	<tr>
			   <td  align="right" valign="top">辅助单位：</td>
			   <td colspan="5"><a href="javascript:addRow();">
			   <c:if test="${canupdate == 1}">
			   <img src="../images/nolines_plus.gif" width="16" height="18" border="0" title="新增"></a>			     <a href="javascript:deleteR();">
			     <img src="../images/nolines_minus.gif" width="16" height="18" border="0" title="删除"></a>
			    </c:if>
				 <table width="271" border="0" id="xq" cellpadding="0" cellspacing="0">
                 <tr>
                   <td width="71">
                   <c:if test="${canupdate == 1}">
                  	 <input type="checkbox" name="checkall" value="on" onClick="Check();">
                   </c:if>
                   </td>
                   <td width="71" >单位</td>
                   <td width="129">换数率</td>
                 </tr>
            	<logic:iterate id="f" name="afls" > 
                 <tr>
                   	<td>
	                   	<c:if test="${canupdate == 1}">
	                   		<input type="checkbox" value="${count}" name="che">
	                   	</c:if>
                   	</td>
                   	<c:if test="${canupdate == 1}">
                   		<td height="20">1 <windrp:select key="CountUnit" name="funitid" value="${f.funitid}" p="n|d"/></td>
                   	</c:if>
                   	<c:if test="${canupdate == 0}">
                   		<td height="20">&nbsp;1&nbsp;
                   			<windrp:getname key='CountUnit' value='${f.funitid}' p='d'/>
                   			<input name="funitid" type="hidden" id="CountUnit" value="${f.funitid}"></td>
                   		</td>
                   	</c:if>
                   <td>=<input name="xquantity" type="text" id="xquantity" size="3" ${canupdate == 0 ?'readonly':''} value="${f.xquantity}" onKeyUp="clearNoNum(this)" dataType="Double" msg="换数率只能是数字!" maxlength='10'>
                   		<span name="unitName"><windrp:getname key='CountUnit' value='${p.sunit}' p='d'/></span>
                   </td>
                 </tr>
				</logic:iterate> 
               </table></td>
		      </tr>
         	   </table>
          </fieldset>

          <table width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr>
              <td><div align="center">
  				<input type="submit" name="Submit" value="确定">&nbsp;&nbsp;<input type="button" name="cancel" value="取消" onClick="window.close();">
              </div></td>
            </tr>
          </table>
        </form>

    </td>
  </tr>
</table>
</body>
</html>
