<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<SCRIPT type=text/javascript src="../js/prototype.js"></SCRIPT>
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
        //var e=x.insertCell(4);
        //var f=x.insertCell(5);
		a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML='${outlayprojectselect}';
        c.innerHTML="<input name=\"remark\" type=\"text\" size=\"50\" >";
        d.innerHTML="<input name=\"outlaysum\" type=\"text\" id=\"outlaysum\" size=\"10\" value=\"0.00\" onchange=\"TotalSum();\">";
		//e.innerHTML="<input name=\"remark\" type=\"text\" id=\"remark\" size=\"30\">";
        //f.innerHTML="<input name=\"outlaysum\" type=\"text\" id=\"outlaysum\" onchange=\"TotalSum();\" size=\"10\" maxlength=\"10\">";
		i++;
    chebox=document.all("che");  //計算total值


 }

 
function deleteR(){
 // 第一行不能放 alert EX.alert("document.table1.chebox[0].checked="+chebox[0].checked);
 //用if來解決chebox在第一次load時其為undifined而不能delete時所使用的方
	chebox=document.all("che");
    if(chebox!=null){
		  if(document.all('dbtable').rows.length==2){
			  if(chebox.checked){
				 document.all('dbtable').deleteRow(1);  //Row 行是從0開始
			  }
			  }else{
			  for(var i=1;i<=chebox.length;i++){
						if (chebox[i-1].checked) {
				 	 	document.getElementById('dbtable').deleteRow ( i);
						i=i-1;
				}
		 
			  }
		}
    }
}



function TotalSum(){
	var totalsum=0.00;
	//alert(dbtable.rows.length);
	if((dbtable.rows.length-1) <=1){
		totalsum=totalsum+parseFloat(document.validateProvide.item("outlaysum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.validateProvide.item("outlaysum")(i).value);
		}
	}
	document.validateProvide.totaloutlay.value=totalsum;
}



	function Check(){
			//alert("document.activeform.checkall.values");
		if(document.validateProvide.checkall.checked){
			//alert(document.activeform.checkall.values);
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.validateProvide.length;i++){

			if (!document.validateProvide.elements[i].checked)
				if(validateProvide.elements[i].name != "checkall"){
				document.validateProvide.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.validateProvide.length;i++){
			if (document.validateProvide.elements[i].checked)
				if(validateProvide.elements[i].name != "checkall"){
				document.validateProvide.elements[i].checked=false;
				}
		}
	}

	function ChkValue(){
		var CName = document.validateProvide.CName;
		var paydate = document.validateProvide.paydate;

		if(CName.value==null||CName.value==""){
			alert("客户名不能为空");
			//totalsum.focus();
			return false;
		}
		if(paydate.value==null||paydate.value==""){
			alert("支出日期不能为空");
			//totalsum.focus();
			return false;
		}

		
		return true;
	}


</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改退货单</td>
        </tr>
      </table>
       <form name="validateProvide" method="post" action="updOutlayAction.do" onSubmit="return ChkValue();">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr> 
            <td >
			
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="9%"  align="right"><input name="id" type="hidden" id="id" value="${olf.id}">
支出日期：</td>
          <td width="21%"><input name="paydate" type="text" id="paydate" onFocus="javascript:selectDate(this)" value="${olf.paydate}"></td>
          <td width="13%" align="right">客户：</td>
          <td width="23%"><input name="cid" type="hidden" id="cid" value="${olf.customerid}">
            <input name="CName" type="text" id="CName" value="${olf.customeridname}">
            <a href="javascript:SelectCustomer();"> <img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
	      <td width="9%" align="right">核算部门：</td>
	      <td width="25%"><select name="castdept" id="castdept">
            <logic:iterate id="d" name="aldept">
              <option value="${d.id}" <c:if test="${d.id==olf.castdept}"> selected </c:if>
                >${d.deptname}</option>
            </logic:iterate>
          </select></td>
	  </tr>
	  <tr>
	    <td  align="right">核算员：</td>
	    <td><select name="caster" id="caster">
          <logic:iterate id="u" name="auls">
            <option value="${u.userid}" <c:if test="${u.userid==olf.caster}"> selected </c:if>
              >${u.realname} </option>
          </logic:iterate>
        </select></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td width="100%" align="right"><table  border="0" cellpadding="0" cellspacing="1">
                    <tr align="center" class="back-blue-light2">
                      <td width="60"><a href="javascript:addRow();">增加</a></td>
                      <td width="60"><a href="javascript:deleteR();">删除</a></td>
                    </tr>
                </table></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="right">
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
                <td width="5%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="30%" > 费用类别</td>
                <td width="44%">备注</td>
                <td width="21%">费用金额</td>
              </tr>
			  <c:set var="count" value="2"/>
            <logic:iterate id="p" name="als" > 
              <tr>
                <td class="table-back"><input type="checkbox" value="${count}" name="che"></td>
                <td  class="table-back">${p.outlayprojectidname}</td>
                <td class="table-back"><input name="remark" type="text" size="50" value="${p.remark}"></td>
                <td class="table-back"><input name="outlaysum" type="text" id="outlaysum" size="10" value="${p.outlaysum}" onChange="TotalSum();"></td>
              </tr>
			  <c:set var="count" value="${count+1}"/>
			</logic:iterate> 
            </table>
            <table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="7%" >&nbsp;</td>
                <td width="7%">&nbsp;</td>
                <td width="7%">&nbsp;</td>
                <td width="64%" align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();">：</td>
                <td width="15%"><input name="totaloutlay" type="text" id="totaloutlay" value="${olf.totaloutlay}" size="10" maxlength="10"></td>
              </tr>
            </table>
            
            <table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td width="8%" height="77" align="right"> 费用事由： </td>
                <td width="92%"><textarea name="memo" cols="145" rows="4" id="memo">${olf.remark}</textarea></td>
              </tr>
            </table></td>
          </tr>

          <tr>
            <td  align="center"><input type="submit" name="Submit" value="提交">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="history.back();"></td>
          </tr>
       
      </table>
       </form>
    </td>
  </tr>
</table>
</body>
</html>
