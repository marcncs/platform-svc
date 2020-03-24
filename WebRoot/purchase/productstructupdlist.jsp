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
<script language="JavaScript">
function Update(){
	var value = CheckboxOnlyOne();
	if( value ==0 ){
		return;
	}
	//window.open("updCalendarAction.do?ID="+value,"newwindow","height=250,width=470,top=250,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
	location.href("../purchase/updProductStructAction.do?ID="+value);
}
function CheckboxOnlyOne(){
		var onlyOneObj = document.listform.Psid;
		var count = 0;
		if(onlyOneObj.length){
            for (i=0; i<onlyOneObj.length; i++) {
				if (onlyOneObj[i].checked) {
                    count += 1;
				}
			}
			if(count !=1 )
			{
        		alert("您必须且只能选择一条记录！");
				return 0;
			}
			else{
				for (i=0; i<onlyOneObj.length; i++) {
					if (onlyOneObj[i].checked) {
                    return onlyOneObj[i].value;
					}
				}
			}
		}else{
            if (!onlyOneObj.checked) {
				alert("请选择记录!");
				return 0;
			}else{
				return  onlyOneObj.value;
			}
		}
	}
	
	function Check(){
			//alert("document.activeform.checkall.values");
		if(document.listform.checkall.checked){
			//alert(document.activeform.checkall.values);
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
</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
    <form name="search" action="" method="post">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">

  <tr> 
      <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="772"> 产品父类型
      <input type="hidden" name="ParentID" value="${parentid}">
        ：${toptitle}</td>
  </tr>

</table>
  </form>
<FORM METHOD="POST" name="listform" ACTION="">
<table width="100%" border="0" cellpadding="0" cellspacing="1">

  <tr align="center" class="title-top"> 
    <td ><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
    <td>编号</td>
    <td>类型名称</td>
    <td>父类型</td>
    <td>是否可用</td>
    <td>备注</td>
  </tr>
  <logic:iterate id="ps" name="apsls" >
  <tr align="center" class="table-back"> 
    <td><input type="checkbox" name="Psid" value="${ps.id}"></td>
	<td>${ps.id}</td>
    <td title="点击查看详情"><a href="toUpdProductStructAction.do?UrlID=${ps.id}">${ps.sortname}</a></td>
    <td>${ps.parentsort}</td>
    <td>${ps.useflag}</td>
    <td>${ps.remark}</td>
  </tr>
  </logic:iterate>
 
</table>
 </form>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="20%"><table  border="0" cellpadding="0" cellspacing="0">
<tr align="center"> 
                <td width="60"><a href="javascript:history.back();">返回</a></td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
        </tr>
      </table></td>
    <td width="80%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/purchase/toUpdProductStructAction.do"/>
			
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
