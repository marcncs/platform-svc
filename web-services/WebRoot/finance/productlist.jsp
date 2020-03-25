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
	 pdmenu = getCookie("PdCookie");
	 switch(pdmenu){
		case "1":ProvideCompare(); break;
		case "2":ProductHistory(); break;
		default:ProvideCompare();
	 }
	}

function addNew(psid){
	window.open("../purchase/toAddProductAction.do?PSID="+psid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
			window.open("../finance/toUpdProductAction.do?ID="+checkid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function ProvideCompare(){
	setCookie("PdCookie","1");
		if(checkid!=""){
			document.all.submsg.src="productProviderCompareAction.do?PDID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function ProductHistory(){
	setCookie("PdCookie","2");
		if(checkid!=""){
			document.all.submsg.src="listProductHistoryAction.do?productid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 产品 
            <input type="hidden" name="ID" value="${id}"></td>
        </tr>
      </table>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
            <td width="9%">编号</td>
            <td width="32%" >产品名称</td>
            <td width="28%">规格</td>
            <td width="16%">计量单位</td>
            <td width="15%">是否可用</td>
          </tr>
          <logic:iterate id="p" name="alapls" > 
          <tr class="table-back" onClick="CheckedObj(this,'${p.id}','${p.productname}');"> 
            <td>${p.id}</td>
            <td  title="点击查看详情"><a href="javascript:Update();">${p.productname}</a></td>
            <td>${p.specmode}</td>
            <td>${p.countunitname}</td>
            <td>${p.userflagname}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="18%"><table  border="0" cellpadding="0" cellspacing="0">
<tr align="center"> 
               
                <td width="60"><a href="javascript:Update();">调整价格</a></td>
              </tr>
            </table></td>
          <td width="82%"> <table  border="0" cellpadding="0" cellspacing="0" >
              <tr> 
                <td width="50%" align="right"> <presentation:pagination target="/finance/listProductAction.do"/> 
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
    </td>
  </tr>
</table>

</body>
</html>
