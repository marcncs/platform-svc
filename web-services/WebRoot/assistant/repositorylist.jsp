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
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<script language="JavaScript">
	var checkid=0;
	var checkcname="";
	var pdmenu=0;
	function CheckedObj(obj,objid,objcname){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkcname=objcname;	
	 Detail();
	 
	}

function addNew(psid){
	window.open("../assistant/toAddRepositoryAction.do?PSID="+psid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=yes");
	}

	function Update(){
		if(checkid!=""){
			window.open("../assistant/toUpdRepositoryAction.do?id="+checkid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
			if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../assistant/delRepositoryAction.do?id="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
	setCookie("PdCookie","0");
		if(checkid!=""){
			document.all.submsg.src="repositoryDetailAction.do?id="+checkid;
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
          <td width="772">知识库 
            <input type="hidden" name="ID" value="${id}"></td>
        </tr>
      </table>
       <form name="search" method="post" action="listRepositoryAction.do">
	  <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
		   <tr class="table-back">
            <td  align="right">制单机构：</td>
            <td><select name="MakeOrganID" id="MakeOrganID">
              <option value="" selected>所有机构</option>
              <logic:iterate id="o" name="ols" >
                <option value="${o.id}"><c:forEach var="i" begin="1" end="${fn:length(o.id)/2}" step="1">
                  <c:out value="--"/>
                </c:forEach>${o.organname}</option>
              </logic:iterate>
          </select></td>
            <td width="10%" align="right">制单部门：</td>
            <td width="25%"><select name="MakeDeptID" id="MakeDeptID">
              <option value="">所有部门</option>
              <logic:iterate id="d" name="dls">
                <option value="${d.id}">${d.deptname}</option>
              </logic:iterate>
            </select></td>
            <td width="8%" align="right">制单人：</td>
            <td width="15%"><select name="MakeID" id="MakeID">
              <option value="">所有用户</option>
              <logic:iterate id="u" name="als">
                <option value="${u.userid}">${u.realname}</option>
              </logic:iterate>
            </select></td>
          </tr>
          <tr class="table-back"> 
            <td width="10%"  align="right">制单日期：</td>
            <td width="32%"><input type="text" name="BeginDate" size="12" readonly onFocus="javascript:selectDate(this)">
-
  <input type="text" name="EndDate" size="12" readonly onFocus="javascript:selectDate(this)"></td>
            <td align="right">关键字：</td>
            <td><input name="KeyWord" type="text" id="KeyWord" value="">
              <a href="javascript:SelectCustomer();">
              <input type="submit" name="Submit" value="查询">
              </a></td>
            <td  align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
       
      </table>
       </form>
	   <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="9%">编号</td>
            <td width="32%" >标题</td>
            <td width="35%">创建机构</td>
            <td width="11%">创建人</td>
            <td width="13%">创建日期</td>
          </tr>
          <logic:iterate id="p" name="alapls" > 
          <tr class="table-back" onClick="CheckedObj(this,'${p.id}','${p.title}');"> 
            <td>${p.id}</td>
            <td  title="点击查看详情"><a href="javascript:Update();">${p.title}</a></td>
            <td>${p.makeorganidname}</td>
            <td>${p.makeidname}</td>
            <td>${p.makedate}</td>
          </tr>
          </logic:iterate> 
      
      </table>
        </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="18%"><table  border="0" cellpadding="0" cellspacing="0">
<tr align="center"> 
                <td width="60"><a href="javascript:addNew(${pid});">新增</a></td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
				<td width="60"><a href="javascript:Del();">删除</a></td>
              </tr>
            </table></td>
          <td width="82%"> <table  border="0" cellpadding="0" cellspacing="0" >
              <tr> 
                <td width="50%" align="right"> <presentation:pagination target="/assistant/listRepositoryAction.do"/> 
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
     <table width="80"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <td width="72" align="center"><a href="javascript:Detail();">知识库详情</a></td>
        </tr>
      </table> </td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME> 
</body>
</html>
