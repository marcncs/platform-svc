<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var checkid=0;
	var checkcode="";
	var checkname="";
	var checkspec="";
	var checkunitid = "";
	var checkunit="";
	function CheckedObj(obj,objid,objcode,objname,objspec,objunitid,objunitidname){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkcode=objcode;
	 checkname = objname;
	 checkspec = objspec;
	 checkunitid = objunitid;
	 checkunit= objunitidname;
	}

	function Affirm(){
	if(checkid!=""){
		setCookie("id",checkid);
		setCookie("productcode",checkcode);
		setCookie("productname",checkname);
		setCookie("specmode",checkspec);
		setCookie("unitid",checkunitid);
		setCookie("unitidname",checkunit);
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	
</script>
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert('本页拒绝使用右键!');}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择产品</td>
        </tr>
      </table>
      <form name="search" method="post" action="selectSingleProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="20%"  align="right">产品类别：</td>
            <td width="21%"><select name="KeyWordLeft" id="KeyWordLeft">
                <logic:iterate id="ps" name="uls" >
                  <option value="${ps.structcode}">
                  <c:forEach var="i" begin="1" end="${fn:length(ps.structcode)/3}" step="1">
                    <c:out value="--"/>
                  </c:forEach>
                    ${ps.sortname}</option>
                </logic:iterate>
            </select></td> 
            <td width="14%" align="right">关键字：</td>
            <td width="45%"><input type="text" name="KeyWord">
              <input type="submit" name="Submit" value="查询"></td>
          </tr>
       
      </table>
       </form>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="13%" >产品编码</td>
            <td width="58%">产品名称</td>
            <td width="21%">规格</td>
            <td width="21%">单位</td>
            <td width="21%">条码</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
          <tr align="center" class="table-back" onClick="CheckedObj(this,'${p.id}','${p.productcode}','${p.productname}','${p.specmode}','${p.countunit}','${p.countunitname}');" onDblClick="Affirm();"> 
            <td >${p.productcode}</td>
            <td>${p.productname}</td>
            <td>${p.specmode}</td>
            <td>${p.countunitname}</td>
            <td>${p.barcode}</td>
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:Affirm();">确定</a></td>
                <td width="60">取消</td>
              </tr>
            </table></td>
          <td width="70%" align="right"> <presentation:pagination target="/machin/selectSingleProductAction.do"/>          </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
