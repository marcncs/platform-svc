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
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<script type="text/javascript" src="../javascripts/prototype.js"></script>  
<script language="JavaScript">
	var checkid=0;
	var checkcname="";
	var submenu=0;
	function CheckedObj(obj,objid,objcname){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkcname=objcname;
	 submenu = getCookie("CookieMenu");
	 switch(submenu){
	 	//case 0: Detail() break
		case "1":CList(); break;
		case "2":Contact();break;
		default:Detail();
	 }
	 
	}

function addNew(){
	window.open("../sales/toAddDitchAction.do","","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdDitchAction.do?id="+checkid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
	setCookie("CookieMenu","0");
		if(checkid!=""){
		document.all.submsg.src="ditchDetailAction.do?id="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	
	function Share(){
		if(checkid!=""){
			window.open("../sales/toShareCustomerAction.do?cid="+checkid,"newwindow","height=280,width=520,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}	
	
	function Appoints(){
		if(checkid!=""){
			window.open("../sales/toAppointsDitchAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function CList(){
	setCookie("CookieMenu","1");
		if(checkid!=""){
		document.all.submsg.src="listCustomerByDitchAction.do?Did="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	

	
	function Contact(){
	setCookie("CookieMenu","2");
		if(checkid!=""){
		document.all.submsg.src="listContactByDitchAction.do?Did="+checkid;
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
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772"> 渠道资料</td>
  </tr>
</table>
 <form name="search" method="post" action="listDitchAction.do">
      <table width="100%" height="44"  border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="10%"  align="right">渠道等级：</td>
            <td width="24%">${ditchrankselect}</td>
            <td width="10%" align="right">信誉度：</td>
            <td width="23%">${prestigeselect}</td>
            <td width="8%" align="right">所属行业：</td>
            <td width="25%">${vocationselect}</td>
          </tr>
          <tr class="table-back">
            <td  align="right">关键字：</td>
            <td><input type="text" name="KeyWord" value=""></td>
            <td align="right">所属用户：</td>
            <td><select name="UserID" id="UserID">
              <option value="">所有用户</option>
              <logic:iterate id="u" name="als">
                <option value="${u.userid}">${u.realname}</option>
              </logic:iterate>
            </select>
            <input type="submit" name="Submit" value="查询"></td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        
      </table></form>
<FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="23%"   align="center">渠道名称</td>
            <td width="15%"  align="center">办公电话</td>
            <td width="12%"  align="center">渠道等级</td>
            <td width="11%"  align="center">信誉度</td>
            <td width="9%"  align="center">所属行业</td>
            <td width="17%" align="center">登记日期</td>
          </tr>
		<c:set var="count" value="0"/>
		  <logic:iterate id="d" name="rList" >
		 
          <tr class="table-back" onClick="CheckedObj(this,'${d.id}','${d.dname}');"> 
            <td >${d.dname}</td>
            <td>${d.telone}</td>
            <td>${d.ditchrankname}</td>
            <td>${d.prestigename}</td>
            <td>${d.vocationname}</td>
            <td>${d.makedate}</td>
          </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate>
        
      </table>
      </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="#" onClick="javascript:addNew();">新增</a></td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <td width="60"><a href="javascript:Appoints();">指派</a></td>
              </tr>
            </table></td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sales/listDitchAction.do"/>	
            </td>
          </tr>
       
      </table></td>
  </tr>
</table>

      <table  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <td width="60"><c:if test="${count>0}"><a href="javascript:Detail();">渠道详情</a></c:if></td> 
          <td width="60"><c:if test="${count>0}"><a href="javascript:CList();">客户列表</a></c:if></td>
          <td width="60"><c:if test="${count>0}"><a href="javascript:Contact();">联系记录</a></c:if></td>
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
