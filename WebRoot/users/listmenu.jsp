<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>	 
<html>
<head>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
var checkid=0;
function CheckedObj(obj,objid){

 for(i=1; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back";
 }
 
 obj.className="event";
 checkid=objid;
 
 //obj.setAttribute("onmouseover","");
 //obj.setAttribute("onmouseout","");
}

function UpdMenu(){
	if(checkid>0){
		location.href="toUpdMenuAction.do?menuid="+checkid;
	}else{
		alert("请选择你要修改的记录!");
	}
}
</script>
<title>角色列表</title>

</head>
<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="70"> 菜单列表</td>
        <td>&nbsp;</td>
      </tr>
    </table>
     <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top">
            <td width="6%" >编号</td>
            <td width="16%">功能菜单名</td>
            <td width="17%">所属模块</td>
            <td width="25%">URL</td>
            <td width="36%">描述</td>
          </tr>
          <logic:iterate id="m" name="mlist">
            <tr align="center" class="table-back" onClick="CheckedObj(this,${m.id});">
              <td >${m.id}</td>
              <td>${m.menuname}</td>
              <td>${m.modulename}</td>
              <td>${m.url}</td>
              <td>${m.describes}</td>
            </tr>
          </logic:iterate>
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center">
                <td width="60"><a href="toAddMenuAction.do">添加</a></td>
                <td width="60"><a href="javascript:UpdMenu();">修改</a></td>
                <td width="60">删除</td>
              </tr>
          </table></td>
          <td width="52%"><presentation:pagination target="/rolepower/listMenuAction.do"/>
          </td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>



