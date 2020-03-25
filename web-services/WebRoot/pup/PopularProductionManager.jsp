<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.commons.lang.time.DateFormatUtils"%>
<%@page import="com.winsafe.drp.action.common.YunParameter"%>
<%@page import="com.winsafe.drp.dao.*"%>
<%@page import="com.winsafe.drp.metadata.*"%>
<%@ include file="../common/tag.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<title>WINDRP-分销系统</title>

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<script language="javascript" src="../js/select2.min.js"></script>
<script language="JavaScript">
	var checkid=0;
	var checkcname="";
	var pdmenu=0;
	function CheckedObj(obj,objid,objcname){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkcname=objcname;
	 pdmenu = getCookie("PdCookie");
	 Detail();
	 /* switch(pdmenu){
	 	case "1":ProductPrice(); break;
		case "2":OrganPrice(); break;
		case "3":ProvideCompare(); break;
		case "4":ProductIntegral(); break;
		case "5":ProductPicture();break;
		case "6":IntegralExchange();break;
		case "7":ProductPriceHistory();break;
		case "8":OrganPriceHistory();break;
		case "9":ProductPriceii();break;
		case "10":ProductICode();break;
		default:Detail();
	 } */
	 
	}

	function addNew(){
		popWin("../yun/toAddPopularProductAction.do",400,300);
	}
	
	function addDetail(){
		if(checkid){
			popWin("../mfr_admin/product_content_edit.jsp?product_id=" + checkid);
		}else{
			alert("请选择要操作的记录!");
		}
	}

	function Update(){
		if(checkid!=""){
			popWin("../purchase/toUpdProductAction.do?ID="+checkid,900,800);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){ 
		if(checkid!=""){
			if ( confirm("你确认要删除编号为:"+checkid+"的产品信息吗?") ){
				popWin2("../yun/delPopularProductAction.do?ID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Audit(flag){ 
		if(checkid!=""){
			var msg = "你确认要审核编号为:"+checkid+"的产品吗?";
			if(flag === 0) {
				msg = "你确认要取消审核编号为:"+checkid+"的产品吗?";
			}
			if ( confirm(msg) ){
				popWin2("../yun/auditPopularProductAction.do?ID="+checkid+"&isAudit="+flag);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		if(checkid!=""){
			document.all.submsg.src="../qr/mfr_admin/product/"+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	
	function ProductPicture(){
	setCookie("PdCookie","5");
		if(checkid!=""){
			document.all.submsg.src="listProductPictureAction.do?PID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	this.onload =function onLoadDivHeight(){
		document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
	}	
 	/* //全选框点击事件
	function checkAll(){ 
		if($("#checkAll").attr('checked') == true){
			$("input:checkbox").attr('checked','checked');
		}else{
			$("input:checkbox").removeAttr('checked');
		}
		 
	}
 
	function changeCheckAll(){
 		setCheckAll();
 	}
 	//设置全选框
 	function setCheckAll(){
 		var allCheckFlag = true;
 		$("#form1").find("input:checkbox").each(function(i){
 			if(this.name == "rid" && this.checked == false){
 				  allCheckFlag = false;
 			}
 		}); 
 		if(allCheckFlag == true){
 			$("#checkAll").attr('checked','checked');
 		}else{
 			$("#checkAll").removeAttr('checked');
 		}
 		
 	} */
 	 
</script>
</head>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
		<div id="bodydiv">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">云单页>>产品管理 
            <input type="hidden" name="ID" value="${id}"></td>
        </tr>
      </table>
      <form name="search" method="post" action="listProductManagerAction.do">
	  <table width="100%"   border="0" cellpadding="0" cellspacing="0">
  
  <tr class="table-back"> 
      <td width="9%" align="right">是否审核：</td>
       <td width="11%"><windrp:select key="YesOrNo" name="auditStatus" value="${auditStatus}" p="y|f"/>
  </td>
  <td width="8%"  align="right">关键字：</td>
      <td width="16%"><input type="text" name="KeyWord" value="${KeyWord}" maxlength="30"></td>
  <td width="9%" align="right"></td>
       <td width="11%">
  </td>
	<td width="4%" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
    </tr>
 
</table> 
</form>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="title-func-back"> 
	<ws:hasAuth operationName="/yun/toAddPopularProductAction.do">
		<td width="50"><a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
	 <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	</ws:hasAuth>
	<%-- <ws:hasAuth operationName="/yun/addPopularProductDetailAction.do">
		<td width="100"><a href="javascript:addDetail();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;图文详情</a></td>
	 <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	</ws:hasAuth> --%>
	<ws:hasAuth operationName="/yun/toAuditPopularProductAction.do">
	<td width="50"><a href="javascript:Audit(1);"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;审核</a></td>
	<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	</ws:hasAuth>
	<ws:hasAuth operationName="/yun/toAuditPopularProductAction.do">
	<td width="80"><a href="javascript:Audit(0);"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消审核</a></td>
	<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	</ws:hasAuth>
	<ws:hasAuth operationName="/yun/toDelPopularProductAction.do">
	<td width="50"><a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>
	<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
	</ws:hasAuth>
	<td class="SeparatePage"> <pages:pager action="../yun/listProductManagerAction.do"/> 
	</td>
	</tr>
</table>
	  </div>
	</td>
</tr>
<tr>
	<td>
	<div id="listdiv" style="overflow-y: auto; height: 600px;" >
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
<!--           	<td width="16px" class="sorttable_nosort"><input type="checkbox" id="checkAll" onclick="checkAll()"></td> -->
            <td style="width: auto;">编号</td>
            <td style="width: auto;">产品名称</td>
            <td style="width: auto;">规格</td>
            <td style="width: auto;">审核状态</td>
            <td style="width: auto;" >宣传口号</td>
            <td style="width: auto;">有效成分</td>
            <td style="width: auto;">登记证号</td>
            <td style="width: auto;">修改日期</td>
<!-- 			<td style="width: auto;">产品规格</td> -->
          </tr>
          <logic:iterate id="p" name="popularProducts" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}','${p.name}');"> 
<%--             <td width="16px"><input type="checkbox" value="${p.id}" name="pid" onclick="addDetail('${p.id}');"></td> --%>
            <td>${p.id}</td>
            <td>${p.name }</td>
            <td>${p.sku }</td>
            <td><windrp:getname key='YesOrNo' value='${p.auditStatus}' p='f'/></td>
            <td>${p.slogan}</td>
            <td>${p.component}</td>
            <td>${p.certification}</td>
            <td>${p.lastModifyTime}</td>
<%-- 			<td>${p.sku}</td> --%>
          </tr>
          </logic:iterate> 
      </table>
      <br>
      <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:Detail();"><span>产品详情</span></a></li>
            <%-- <ws:hasAuth operationName="/purchase/listProductPictureAction.do">
            <li><a href="javascript:ProductPicture();"><span>产品图片</span></a></li>
            </ws:hasAuth> --%>
            <%--<li><a href="javascript:ProductIntegral();"><span>积分设定</span></a></li>
            <li><a href="javascript:IntegralExchange();"><span>积分兑换</span></a></li>
            <li><a href="javascript:ProductPrice();"><span>零售价格设定</span></a></li>
            <li><a href="javascript:OrganPrice();"><span>机构零售定价</span></a></li>
            <li><a href="javascript:ProductPriceii();"><span>机构价格设定</span></a></li>
            <li><a href="javascript:ProvideCompare();"><span>供应商比较</span></a></li>
            <li><a href="javascript:ProductICode();"><span>物流码前缀</span></a></li>
             --%>
          </ul>
        </div>
        <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>			
	  </div>
	  </td>
  </tr>
</table>
</body>
</html>

