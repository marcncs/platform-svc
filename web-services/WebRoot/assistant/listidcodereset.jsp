<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<script language="JavaScript">
	var checkid="";
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 MsgDetail();
	}
	
	function addNew(){
	window.open("../assistant/toAddIdcodeResetAction.do","","height=600,width=800,top=80,left=80,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}	
	

	function updNew(){
		if(checkid!=""){
		window.open("../assistant/toUpdIdcodeResetAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function MsgDetail(){
		if(checkid!=""){
			document.all.submsg.src="idcodeResetDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../assistant/delIdcodeDetailAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	


</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 序号重置</td>
        </tr>
      </table>
      <form name="search" method="post" action="../assistant/listIdcodeResetAction.do">
         
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
         <tr class="table-back"> 
            <td width="10%"  align="right">制单机构：</td>
            <td width="29%"><select name="MakeOrganID" id="MakeOrganID">
              <option value="" selected>所有机构</option>
              <logic:iterate id="o" name="ols" >
                <option value="${o.id}">
                  <c:forEach var="i" begin="1" end="${fn:length(o.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${o.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="8%" align="right">制单人：</td>
            <td width="20%"><select name="MakeID" id="MakeID">
              <option value="">所有用户</option>
              <logic:iterate id="u" name="als">
                <option value="${u.userid}">${u.realname}</option>
              </logic:iterate>
            </select></td>
            <td width="12%" align="right">是否复核：</td>
            <td width="21%">${isauditselect}</td>
          </tr>
          <tr class="table-back"> 
            <td  align="right">制单日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" size="10" onFocus="javascript:selectDate(this)">
-
  <input name="EndDate" type="text" id="EndDate" size="10" onFocus="javascript:selectDate(this)">
  <input type="submit" name="Submit" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        
      </table>
      </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="4%"  >编号</td>
            <td width="18%" >备注</td>
            <td width="13%" >制单机构</td>
            <td width="8%" >制单人</td>
            <td width="12%" >制单日期</td>
			<td width="7%" >是否复核</td>
          </tr>
          <logic:iterate id="pi" name="alsb" > 
          <tr class="table-back" onClick="CheckedObj(this,'${pi.id}');"> 
            <td >${pi.id}</td>
            <td><div title='${pi.memo}' style="width:200; overflow:hidden; text-overflow:ellipsis; white-space:nowrap;">${pi.memo}
</div></td>
            <td>${pi.makeorganidname}</td>            
            <td>${pi.makeidname}</td>
            <td>${pi.makedate}</td>
			<td>${pi.isauditname}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:addNew();">新增</a></td>
				<td width="60"><a href="javascript:updNew();">修改</a></td>
                <td width="60"><a href="javascript:Del();">删除</a></td>
              </tr>
            </table> </td>
          <td width="52%" align="right"> <presentation:pagination target="/assistant/listIdcodeResetAction.do"/>          </td>
        </tr>
      </table>      
      <table width="80"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <td width="72" align="center"><a href="javascript:Detail();">详情</a></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
