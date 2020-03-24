<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>		 
<html>
<head>

<title>销售产品</title>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<script type="text/javascript">
var checkid=0;
		function CheckedObj(obj,objid){
		
		 for(i=0; i<obj.parentNode.childNodes.length; i++)
		 {
			   obj.parentNode.childNodes[i].className="table-back-colorbar";
		 }
		 
		 obj.className="event";
		  checkid=objid;
		 pdmenu = getCookie("PdCookie");
	 switch(pdmenu){
	 	case "0":Detail(); break;
	 	case "1":ProductPrice(); break;
		//case "2":ProductICode();break;
		default:Detail();
	 }
		
		}
		
		function Detail(){
	setCookie("PdCookie","0");
		if(checkid!=""){
			document.all.submsg.src="../ditch/detailAlreadyProductAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function ProductPrice(){
	setCookie("PdCookie","1");
		if(checkid!=""){
			document.all.submsg.src="../ditch/listProductPictureAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function ProductICode(){
	setCookie("PdCookie","2");
		if(checkid!=""){
			document.all.submsg.src="../ditch/listICodeAction.do?PID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
		this.onload = function abc(){
			document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
		}
		
		function print(){
				excputform.target="_blank";
				excputform.action="../ditch/printAlreadyProductAction.do";
				excputform.submit();
		}
		
		function DownTxt(){
			excputform.action="txtPutAlreadyProductAction.do";
			excputform.submit();
		}	
		
		</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <div id="div1">
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td >${menusTrace }</td>
      </tr>
    </table>
    <form name="search" method="post" action="../ditch/listAlreadyProductAction.do">
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
          
            <tr class="table-back">
              <td width="10%"  align="right">产品类别：</td>
              <td width="25%" ><input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft }">			
			<windrp:pstree id="KeyWordLeft" name="productstruts" value="${productstruts}" /></td>
              <td width="11%"  align="right">关键字：</td>
              <td width="24%" ><input type="text" name="KeyWord" value="${KeyWord}">
              </td>
              <td width="11%" align="right">&nbsp;</td>
              <td width="19%"  class="SeparatePage"><input name="Submit" type="image" id="Submit" 
							src="../images/CN/search.gif" border="0" title="查询"></td>
            </tr>
        
        </table>
          </form>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr class="title-func-back">	
			<td width="50">
			<a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a>
		    </td>
            <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
    		<td width="50"><a href="javascript:DownTxt();"><img src="../images/CN/downpda.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;下载</a></td>
		    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			  <td  class="SeparatePage"><pages:pager action="../ditch/listAlreadyProductAction.do"/></td>				
			</tr>
		</table>
		</div>
		<div id="abc" style="overflow-y: auto; height: 600px;">
        <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr align="center" class="title-top-lock">
              <td width="20%" >产品编号</td>
              <td width="26%">产品名称</td>
              <td width="26%">规格</td>
              <td width="26%">规格明细</td>
              <td width="26%">单位</td>
            </tr>
              <logic:iterate id="d" name="opls" >
                <tr class="table-back-colorbar" onClick="CheckedObj(this,'${d.id}');">
                  <td>${d.productid}</td>
                  <td>${d.productname}</td>
                  <td>${d.specmode}</td>
                  <td>${d.packSizeName}</td>
                  <td><windrp:getname key='CountUnit' value='${d.countunit}' p='d'/></td>
                </tr>
              </logic:iterate>
      </table>
      <br>
      <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:Detail();"><span>产品详情</span></a></li>
            <li><a href="javascript:ProductPrice();"><span>产品图片</span></a></li>
            <%--<li><a href="javascript:ProductICode();"><span>物流码前缀</span></a></li> --%>
          </ul>
        </div>
        <div>
        <IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
		</div>
		</div>
	
     </td>
  </tr>
</table>

<form  name="excputform" method="post" action="">
<input type="hidden" name="KeyWordLeft" id ="KeyWordLeft" value="${KeyWordLeft}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
</form>
</body>
</html>

