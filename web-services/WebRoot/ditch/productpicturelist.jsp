<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	Detail();
	}
	
	function Detail(){
		if(checkid!=""){
		document.all.submsg2.src="../purchase/productPictureAction.do?ID="+checkid;
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
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 产品图片 </td>
        </tr>
      </table>
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top">
            <td width="16%" >编号</td>
            <td width="21%">产品编号</td>
            <td width="19%">产品名称</td>
            <td width="44%">图片路径</td>
          </tr>
            <logic:iterate id="l" name="alpl" >
              <tr class="table-back-colorbar" onClick="CheckedObj(this,${l.id});">
                <td >${l.id}</td>
                <td>${l.productid}</td>
                <td>${l.productidname}</td>
                <td>${l.pictureurl}</td>
              </tr>
            </logic:iterate>
      </table>    
      <br>
      <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:Detail();"><span>详情</span></a></li>
         </ul>
        </div>
        <div>
        	<IFRAME id="submsg2" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg2"  src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setParentIframeHeight(this,'submsg','abc');setIframeHeight(this)"></IFRAME>
        </div>
      </div> 
     </td>
  </tr>
</table>

</body>
</html>
