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
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var checkid=0;
	var checkname="";
	function CheckedObj(obj,objid,objname){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkname = objname;
	}

	function Affirm(){
		if(checkid!=""){		
		var c={cid:checkid,cname:checkname};
		window.returnValue=c;
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}

	}

</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择客户</td>
        </tr>
      </table>
      <form name="search" method="post" action="../sales/selectRetailCustomerAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
		      <td width="13%"  align="right">分类：</td>
            <td width="24%" ><select name="Sort">
                      <option value="">选择</option>
					  <c:forEach var="sort" items="${sortlist}">
                        <option value="${sort.id}">${sort.sortname}</option>
                      </c:forEach>
            </select></td>
            <td width="21%"  align="right">名称关键字：</td>
            <td width="42%" ><input type="text" name="KeyWord">
            <input type="submit" name="Submit" value="查询"></td>
          </tr>
       
      </table>
       </form>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="13%" >客户编号</td>
            <td width="58%">名称</td>
            <td width="21%">办公电话</td>
          </tr>
          <logic:iterate id="c" name="sls" > 
          <tr class="table-back" onClick="CheckedObj(this,'${c.cid}','${c.cname}');" onDblClick="Affirm();"> 
            <td >${c.cid}</td>
            <td>${c.cname}</td>
            <td>${c.officetel}</td>
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
          <td width="70%"> <presentation:pagination target="/sales/selectRetailCustomerAction.do"/> 
          </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
