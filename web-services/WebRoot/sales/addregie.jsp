<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ page import="com.winsafe.hbm.util.Internation"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>
<script src="../js/prototype.js"></script>
<script language="JavaScript">
arr = new Array();
<c:set var="temp" value="0"/>
	<logic:iterate id="ca" name="cals">
		arr[${temp}] = new Array("${ca.id}","${ca.areaname}","${ca.parentid}");
		<c:set var="temp" value="${temp+1}"/>
	</logic:iterate>
temp=${temp};


  function ChangeCountry(oSelect)
  {   
     document.addform.city.length=0; 
	 //document.addform.areas.length=0; 
     var i = 0; 
     document.addform.city.options[0]=new Option('-城市-',''); 
	 //document.addform.areas.options[0]=new Option('-区域-',''); 
     for(i=0;i<temp;i++){ 
       if(arr[i][2]==oSelect.value){
         insertOption(document.addform.city,arr[i][1],arr[i][0])
       } 
     } 
  } 

  function ChangeProvince(serialtotype)
  {
	document.addform.areas.length=0;
	var t=0;
	document.addform.areas.options[0]=new Option('-区域-','');
	for(t=0;t<temp;t++){
		if(arr[t][2]==serialtotype.value){
			insertOption(document.addform.areas,arr[t][1],arr[t][0])
		}
	}
  }


  function insertOption(oSelect,text,value)
  {
        var oOption = document.createElement("OPTION");
        oOption.text = text;
        oOption.value = value;
        oSelect.options.add(oOption);
  }

function SelectCustomer(){
	showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	//document.addform.cid.value=getCookie("cid");
	document.addform.regienob.value=getCookie("cname");
}
</script>
<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 添加专卖店</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
    <form name="addform" method="post" action="addRegieAction.do" > 
    <table width="100%" height="183"  border="0" cellpadding="0" cellspacing="1">
     
        <tr class="table-back">
          <td width="11%"  align="right">专卖店名称：</td>
          <td width="38%" ><input name="regiename" type="text" id="regiename"></td>
          <td width="13%"  align="right">店面类型：</td>
          <td width="38%" >${regietypename}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">面积大小：</td>
          <td ><input name="acreage" type="text" id="acreage" value="0">
          平米</td>
          <td  align="right">现状：</td>
          <td >${actualityname}</td>
        </tr>
        <tr class="table-back">
          <td  align="right">店长：</td>
          <td ><input name="regienob" type="text" id="regienob">
            <a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
          <td  align="right">开店日期：</td>
          <td ><input name="shopdate" type="text" id="shopdate" onFocus="javascript:selectDate(this)"></td>
        </tr>
        <tr class="table-back">
          <td  align="right">专管人员：</td>
          <td ><select name="specializeid" id="specializeid">
             <logic:iterate id="u" name="als">
               <option value="${u.userid}">${u.realname}</option>
             </logic:iterate>
           </select></td>
          <td  align="right">省份：</td>
          <td ><select name="province"  onChange="ChangeCountry(this);">
              <option value="">-省份-</option>
              <logic:present name="cls"> <logic:iterate id="c" name="cls">
              <option value="${c.id}">${c.areaname}</option>
              </logic:iterate> </logic:present>
          </select></td>
        </tr>
        <tr class="table-back">
          <td  align="right">城市：</td>
          <td ><select name="city" >
              <option value="">-城市-</option>
          </select></td>
          <td  align="right">地址：</td>
          <td ><input name="regieaddr" type="text" id="regieaddr"></td>
        </tr>
        <tr class="table-back">
          <td  align="right">备注：</td>
          <td  colspan="3"><textarea name="remark" cols="140" rows="5"></textarea></td>
        </tr>
        <tr class="table-back">
          <td  align="right">&nbsp;</td>
          <td ><input type="submit" name="Submit" value="确定">
              <input type="reset" name="cancel" onClick="javascript:history.back()" value="取消"></td>
          <td >&nbsp;</td>
          <td >&nbsp;</td>
        </tr>
     
     
    </table>
     </form></td>
  </tr>
</table>
	</td>
  </tr>
</table>

</body>
</html>
