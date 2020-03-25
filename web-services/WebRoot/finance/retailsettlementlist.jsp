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
<script language="javascript">
	
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 Detail();
	}
	
	function Affirm(){

		if(window.confirm("您确认要结算所选的单据吗？")){
		document.listform.submit();
			//window.open("../sales/delRetailAction.do?RID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}

		
	}
	
	function Check(){
		if(document.listform.checkall.checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.listform.length;i++){

			if (!document.listform.elements[i].checked)
				if(listform.elements[i].name != "checkall"){
				document.listform.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.listform.length;i++){
			if (document.listform.elements[i].checked)
				if(listform.elements[i].name != "checkall"){
				document.listform.elements[i].checked=false;
				}
		}
	}
	
	function Detail(){
		if(checkid!=""){
			document.all.submsg.src="../sales/retailDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 零售结算</td>
  </tr>
</table>
 <form name="search" method="post" action="../finance/listRetailSettlementAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="10%"  align="right"><input name="IsSettlement" type="hidden" id="IsSettlement" value="0">
            出货仓库：</td>
            <td width="20%">
              <select name="WarehouseID" id="WarehouseID">
                <option value="" selected>所有仓库</option>
                <logic:iterate id="w" name="alw" >
                  <option value="${w.id}">${w.warehousename}</option>
                </logic:iterate>
              </select>
            </td>
            <td width="10%" align="right">制单日期：</td>
            <td width="34%"><input type="text" name="BeginDate" size="12" readonly onFocus="javascript:selectDate(this)">
-
  <input type="text" name="EndDate" size="12" readonly onFocus="javascript:selectDate(this)"></td>
            <td width="8%" align="right">制单人：</td>
            <td width="18%"><select name="MakeID" id="MakeID">
              <logic:iterate id="u" name="als">
                <option value="${u.userid}">${u.realname}</option>
              </logic:iterate>
            </select>
              <input type="submit" name="Submit" value="查询"></td>
          </tr>
       
      </table>
       </form>
 <FORM METHOD="POST" name="listform" ACTION="../finance/retailSettlementAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top">
            <td width="2%"><input type="checkbox" name="checkall" onClick="Check();" ></td> 
            <td width="5%">编号</td>
            <td width="31%" >客户名称</td>
            <td width="21%">联系电话</td>
            <td width="13%">出货仓库</td>
            <td width="8%">制单人</td>
            <td width="8%">制单日期</td>
            <td width="12%">总金额</td>
          </tr>
		  <c:set var="totalcount" value="0"/>
	<logic:iterate id="s" name="alsb" > 
          <tr class="table-back" onClick="CheckedObj(this,'${s.id}');">
            <td><input type="checkbox" name="id" value="${s.id}"></td> 
            <td>${s.id}</td>
            <td >${s.cname}</td>
            <td>${s.tel}</td>
            <td>${s.warehouseidname}</td>
            <td>${s.makeidname}</td>
            <td>${s.makedate}</td>
            <td>${s.totalsum}</td>
            </tr>
			<c:set var="totalcount" value="${totalcount+s.totalsum}"/>
		</logic:iterate>
          <tr class="table-back">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td >&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td align="right">合计：</td>
            <td><c:out value="${totalcount}" /></td>
          </tr>
       
      </table>
       </form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table width="240"  border="0" cellpadding="0" cellspacing="0">
              <tr align="center">
                <td width="60"><a href="javascript:Affirm();">结算</a>                </td>
              </tr>
            </table></td>
          <td width="52%" align="right"> 
            <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/finance/listRetailSettlementAction.do"/>	
            </td>
          </tr>
       
      </table>
          </td>
  </tr>
</table>

    <table width="65"  border="0" cellpadding="0" cellspacing="1">
      <tr align="center" class="back-bntgray2">
        <td width="65"><a href="javascript:Detail();">零售单详情</a></td>
      </tr>
    </table></td>
  </tr>
</table>

<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>
