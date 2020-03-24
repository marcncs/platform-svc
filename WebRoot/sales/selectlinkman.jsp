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
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var checkid=0;
	var checkname="";
	var checktel="";
	var transit=0;
	var checktransportaddr="";
	var mobile="";
	function CheckedObj(obj,objid,objname,objtel,objtransportaddr,objmobile){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkname = objname;
	 checktel = objtel;
	 checktransportaddr=objtransportaddr;
	 mobile=objmobile;
	// transit=objtransit;
	}

	function Affirm(){
	if(checkid!=""){		
		var lk={lid:checkid,lname:checkname,ltel:checktel,ltransportaddr:checktransportaddr,transit:transit,mobile:mobile};
		window.returnValue=lk;
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	
</script>
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false;}">
<SCRIPT language=javascript>
;
</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择联系人</td>
        </tr>
      </table>
       <form name="search" method="post" action="selectLinkmanAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="16%"  align="right">名称关键字：</td>
            <td width="84%" ><input type="text" name="KeyWord">
            <input type="submit" name="Submit" value="查询"></td>
          </tr>
        
      </table>
      </form>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <!--<td width="13%" >联系人编号</td>-->
            <td width="21%" >联系人姓名</td>
            <td width="15%">性别</td>
            <td width="20%">电话</td>
			<td width="20%">手机</td>
            <td width="21%">部门</td>
            <td width="23%">职务</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
          <tr class="table-back" onClick="CheckedObj(this,${p.id},'${p.name}','${p.officetel}','${p.addr}','${p.mobile}');" onDblClick="Affirm();"> 
            <!--<td >${p.id}</td>-->
            <td >${p.name}</td>
            <td>${p.sexname}</td>
            <td>${p.officetel}</td>
			<td>${p.mobile}</td>
            <td>${p.department}</td>
            <td>${p.duty}</td>
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
          <td width="70%"> <presentation:pagination target="/sales/selectLinkmanAction.do"/> 
          </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
