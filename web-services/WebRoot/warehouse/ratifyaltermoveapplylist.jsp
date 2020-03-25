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
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
StockMoveDetail();
	}
	
	function addNew(){
	window.open("../warehouse/toAddAlterMoveApplyAction.do","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("../warehouse/toUpdAlterMoveApplyAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=yes");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function StockMoveDetail(){
		if(checkid!=""){
			document.all.submsg.src="ratifyAlterMoveApplyDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	
	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的产品吗？如果删除将永远不能恢复!")){
			window.open("../warehouse/delStockAlterMoveAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="2" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">批准机构机构间转仓申请</td>
        </tr>
      </table>
      <form name="search" method="post" action="listRatifyAlterMoveApplyAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
	    
        <tr class="table-back">
          <td width="12%"  align="right">调出机构：</td>
          <td width="23%"><select name="OutOrganID" id="OutOrganID">
              <option value="" selected>所有机构</option>
              <logic:iterate id="o" name="alos" >
                <option value="${o.id}"><c:forEach var="i" begin="1" end="${fn:length(o.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>${o.organname}</option>
              </logic:iterate>
          </select></td>
          <td align="right">是否批准：</td>
          <td>${isratifyselect}</td>
          <td align="right">是否作废：</td>
          <td>${isblankoutselect}</td>
        </tr>
      	<tr class="table-back">
            <td align="right">制单机构：</td>
            <td><select name="MakeOrganID" id="MakeOrganID">
              <option value="" selected>所有机构</option>
              <logic:iterate id="ro" name="ols" >
                <option value="${ro.id}">
                  <c:forEach var="i" begin="1" end="${fn:length(ro.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>
                  ${ro.organname}</option>
              </logic:iterate>
            </select></td>
            <td width="9%" align="right">制单部门：</td>
            <td width="23%"><select name="MakeDeptID" id="MakeDeptID">
              <option value="">所有部门</option>
              <logic:iterate id="d" name="dls">
                <option value="${d.id}">${d.deptname}</option>
              </logic:iterate>
            </select></td>
            <td width="9%" align="right">制单人：</td>
            <td width="24%"><select name="MakeID" id="MakeID">
              <option value="">所有用户</option>
              <logic:iterate id="u" name="auls">
                <option value="${u.userid}">${u.realname}</option>
              </logic:iterate>
            </select></td>
          </tr>
          <tr class="table-back">
            <td align="right">关键字：</td>
            <td><input type="text" name="KeyWord" value="${KeyWord}"><input type="submit" name="Submit" value="查询"></td>
            <td width="9%" align="right"></td>
            <td width="23%"></td>
            <td width="9%" align="right"></td>
            <td width="24%"></td>
          </tr>
        
      </table>
      </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="5%" >编号</td>
            <td width="10%" >机构间转仓需求日期</td>
            <td width="17%" >调出机构</td>
            <td width="10%" >付款方式</td>
            <td width="8%" >开票信息</td>
            <td width="7%" >是否批准</td>
			<td width="7%" >是否作废</td>
			<td width="15%" >制单机构</td>
			<td width="10%" >制单人</td>
          </tr>
          <logic:iterate id="sa" name="als" > 
          <tr class="table-back" onClick="CheckedObj(this,'${sa.id}');"> 
		  <td  class="${sa.isblankout==1?'text2-red':''}">${sa.id}</td>
            <td class="${sa.isblankout==1?'text2-red':''}">${sa.movedate}</td>
            <td class="${sa.isblankout==1?'text2-red':''}">${sa.outorganidname}</td>
            <td class="${sa.isblankout==1?'text2-red':''}">${sa.paymentmodename}</td>
            <td class="${sa.isblankout==1?'text2-red':''}">${sa.invmsgname}</td>
            <td class="${sa.isblankout==1?'text2-red':''}">${sa.isratifyname}</td>
			<td class="${sa.isblankout==1?'text2-red':''}">${sa.isblankoutname}</td>
			<td class="${sa.isblankout==1?'text2-red':''}">${sa.makeorganidname}</td>
			<td class="${sa.isblankout==1?'text2-red':''}">${sa.makeidname}</td>
			</tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="48%">
		  <!--<table  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:addNew();">新增</a></td>
              <td width="60" align="center"><a href="javascript:Update();">修改</a></td>
              <td width="60" align="center"><a href="javascript:Del();">删除</a></td>
              </tr>
          </table>-->
		  </td>
          <td width="52%" align="right"> <presentation:pagination target="/warehouse/listRatifyAlterMoveApplyAction.do"/></td>
        </tr>
      </table>      
      <table width="60"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <td width="85"><a href="javascript:StockMoveDetail();">详情</a></td>
        </tr>
      </table></td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
