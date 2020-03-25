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
		case "1":Linkman(); break;
		case "2":Contact(); break;
		case "3":Hap();break;
		case "4":Project();break;
		case "5":Product();break;
		case "6":Demand();break;
		case "7":SaleOrder(); break;
		case "8":SaleShipment();break;
		case "9":SaleInvoice(); break;
		case "10":Document(); break;
		case "11":Pact(); break;
		case "12":Service(); break;
		case "13":Suit(); break;
		case "14":Largess(); break;
		default:Detail();
	 }
	 
	}

function addNew(){
	window.open("../sales/toAddCustomerAction.do","","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdCustomerAction.do?Cid="+checkid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
	setCookie("CookieMenu","0");
		if(checkid!=""){
		document.all.submsg.src="../sales/customerDetailAction.do?Cid="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	
	function Appoints(){
		if(checkid!=""){
			window.open("../sales/toAppointsCustomerAction.do?CID="+checkid,"newwindow","height=280,width=520,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Linkman(){
	setCookie("CookieMenu","1");
		if(checkid!=""){
		document.all.submsg.src="listLinkManAction.do?Cid="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	function Contact(){
	setCookie("CookieMenu","2");
		if(checkid!=""){
		document.all.submsg.src="listContactLogByCustomerAction.do?Cid="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Hap(){
	setCookie("CookieMenu","3");
		if(checkid!=""){
		document.all.submsg.src="listHapAction.do?Cid="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Project(){
	setCookie("CookieMenu","4");
		if(checkid!=""){
		document.all.submsg.src="listProjectAction.do?Cid="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Product(){
	setCookie("CookieMenu","5");
		if(checkid!=""){
		document.all.submsg.src="../purchase/listCPProductAction.do?pid="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Demand(){
	setCookie("CookieMenu","6");
		if(checkid!=""){
		document.all.submsg.src="listDemandPriceByCustomerAction.do?CID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	
	function SaleOrder(){
	setCookie("CookieMenu","7");
		if(checkid!=""){
		document.all.submsg.src="listSaleOrderByCustomerAction.do?CID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function SaleShipment(){
	setCookie("CookieMenu","8");
		if(checkid!=""){
		document.all.submsg.src="listShipmentBillByCustomerAction.do?Cid="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}


	function SaleInvoice(){
	setCookie("CookieMenu","9");
		if(checkid!=""){
		document.all.submsg.src="listSaleInvoiceByCustomerAction.do?CID="+checkid;
		}else{
		alert("请选择你要修改的记录!");
		}
	}
	
	function Document(){
	setCookie("CookieMenu","10");
		if(checkid!=""){
		document.all.submsg.src="listDocumentAction.do?Cid="+checkid;
		}else{
		alert("请选择你要修改的记录!");
		}
	}
	
	function Pact(){
	setCookie("CookieMenu","11");
	if(checkid!=""){
		document.all.submsg.src="listPactAction.do?Cid="+checkid;
		}else{
		alert("请选择你要修改的记录!");
		}
	}
	
	function Service(){
	setCookie("CookieMenu","12");
	if(checkid!=""){
		document.all.submsg.src="listServiceAgreementAction.do?Cid="+checkid;
		}else{
		alert("请选择你要修改的记录!");
		}
	}
	
	function Suit(){
	setCookie("CookieMenu","13");
	if(checkid!=""){
		document.all.submsg.src="listSuitAction.do?Cid="+checkid;
		}else{
		alert("请选择你要修改的记录!");
		}
	}
	
	function Largess(){
	setCookie("CookieMenu","14");
	if(checkid!=""){
		document.all.submsg.src="listLargessAction.do?Cid="+checkid;
		}else{
		alert("请选择你要修改的记录!");
		}
	}

	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除所选的记录吗？如果删除将永远不能恢复!")){
			window.open("../sales/delCustomerAction.do?CID="+checkid,"newwindow",		"height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Activate(){
		if(checkid!=""){
			window.open("../sales/activateCustomerAction.do?CID="+checkid,"newwindow",		"height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function NoActivate(){
		if(checkid!=""){
			window.open("../sales/cancelActivateCustomerAction.do?CID="+checkid,"newwindow",		"height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	var cobj ="";
    function getResult(getobj,toobj)
    {
		//alert(getobj);
		cobj = toobj;
        var areaID = $F(getobj);
        //var y = $F('lstYears');
        var url = 'listAreasAction.do';
        var pars = 'parentid=' + areaID;
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse}
                    );

    }

    function showResponse(originalRequest)
    {
        //put returned XML in the textarea
		var city = originalRequest.responseXML.getElementsByTagName("area"); 
						//alert(city.length);
						var strid = new Array();
						var str = new Array();
						for(var i=0;i<city.length;i++){
							var e = city[i];
							str[i] =new Array(e.getElementsByTagName("areaid")[0].firstChild.data , e.getElementsByTagName("areaname")[0].firstChild.data);
							//alert(str);
						}
						buildSelect(str,document.getElementById(cobj));//赋值给city选择框
        //$('cc').value = originalRequest.responseXML.getElementsByTagName("area");
    }

	function buildSelect(str,sel) {//赋值给选择框
	//alert(str.length);

		sel.options.length=0;
		sel.options[0]=new Option("选择","")
		for(var i=0;i<str.length;i++) {
			//alert(str[i]);	
				sel.options[sel.options.length]=new Option(str[i][1],str[i][0])
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
    <td width="772"> 客户资料</td>
  </tr>
</table>
 <form name="search" method="post" action="listCustomerAction.do">
      <table width="100%" height="44"  border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="8%"  align="right">客户状态：</td>
            <td width="16%">${customerstatusselect}</td>
            <td width="11%" align="right">分类：</td>
            <td width="15%"><select name="Sort">
              <option value="">选择</option>
              <c:forEach var="sort" items="${sortlist}">
                <option value="${sort.id}">${sort.sortname}</option>
              </c:forEach>
            </select></td>
            <td width="10%" align="right">大区：</td>
            <td width="20%"><select name="region"  onChange="getResult('region','province');">
              <option value="">-省份-</option>
              <logic:iterate id="c" name="cals">
                <option value="${c.id}">${c.areaname}</option>
              </logic:iterate>
            </select></td>
          </tr>
          <tr class="table-back">
            <td  align="right">省份：</td>
            <td><select name="province"  id="province" onChange="getResult('province','city');">
              <option value="">-省份-</option>
            </select></td>
            <td align="right">城市：</td>
            <td><select name="City" onChange="getResult('city','areas');">
              <option value="">-城市-</option>
            </select></td>
            <td align="right">地区：</td>
            <td><select name="Areas" id="areas" >
              <option value="">-地区-</option>
            </select></td>
          </tr>
		   <tr class="table-back">
            <td  align="right">所属用户：</td>
            <td><select name="SpecializeID" id="SpecializeID">
              <option value="">所有用户</option>
              <logic:iterate id="u" name="als">
                <option value="${u.userid}">${u.realname}</option>
              </logic:iterate>
            </select></td>
            <td align="right">是否激活：</td>
            <td>${isactivateselect}</td>
            <td align="right">关键字：</td>
            <td><input type="text" name="KeyWord" value="">
              <input type="submit" name="Submit2" value="查询"></td>
          </tr>

      </table>
        </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td width="6%"   align="center">编号</td>
            <td width="23%"  align="center">客户名称</td>
            <td width="7%" align="center">活跃率</td>
            <td width="15%"  align="center">办公电话</td>
            <td width="12%"  align="center">客户状态</td>
            <td width="11%"  align="center">ABC类别</td>
            <td width="9%"  align="center">客户来源</td>
            <td width="17%" align="center">登记日期</td>
          </tr>
		<c:set var="count" value="0"/>
		  <logic:iterate id="c" name="usList" >
		 
          <tr class="table-back" onClick="CheckedObj(this,'${c.cid}','${c.cname}');"> 
            <td >${c.cid}</td>
            <td>${c.cname}</td>
            <td>${c.yauldname}</td>
            <td>${c.officetel}</td>
            <td>${c.customerstatusname}</td>
            <td>${c.customertypename}</td>
            <td>${c.sourcename}</td>
            <td>${c.makedate}</td>
          </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate>
      </table>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="#" onClick="javascript:addNew();">新增</a>                </td>
                <td width="60"><a href="javascript:Update();">修改</a></td>
                <!--<td width="60"><a href="javascript:Appoints();">指派</a></td>-->
                <td width="60"><a href="javascript:Del();">删除</a></td>
				<!--<td width="60"><a href="javascript:Activate();">激活</a></td>
				<td width="60"><a href="javascript:NoActivate();">停用</a></td>-->
              </tr>
            </table></td>
    <td width="52%"> 
      <table  border="0" cellpadding="0" cellspacing="0" >

          <tr> 
            <td width="50%" align="right">
			<presentation:pagination target="/sales/listCustomerAction.do"/>	
            </td>
          </tr>
       
      </table></td>
  </tr>
</table>

      <table  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="back-bntgray2">
          <td width="56"><c:if test="${count>0}"><a href="javascript:Detail();">客户详情</a></c:if></td> 
          <td width="56"><c:if test="${count>0}"><a href="javascript:Linkman();">联系人</a></c:if></td>
          <td width="56"><c:if test="${count>0}"><a href="javascript:Contact();">联系记录</a></c:if></td>
          <td width="56"><c:if test="${count>0}"><a href="javascript:Hap();">零售机会</a></c:if></td>
          <td width="56"><c:if test="${count>0}"><a href="javascript:Project();">项目</a></c:if></td>
          <td width="56"><c:if test="${count>0}"><a href="javascript:Product();">指定产品</a></c:if></td>
          <td width="56"><c:if test="${count>0}"><a href="javascript:Demand();">零售报价</a></c:if></td>
          <td width="56"><c:if test="${count>0}"><a href="javascript:SaleOrder();">零售单</a></c:if></td>
		  <td width="56"><c:if test="${count>0}"><a href="javascript:SaleShipment();">送货清单</a></c:if></td>
		  <td width="56"><c:if test="${count>0}"><a href="javascript:SaleInvoice();">零售发票</a></c:if></td>
          <td width="56"><c:if test="${count>0}"><a href="javascript:Document();">相关文档</a></c:if></td>
		  <td width="56"><c:if test="${count>0}"><a href="javascript:Pact();">相关合同</a></c:if></td>
          <td width="56"><c:if test="${count>0}"><a href="javascript:Service();">服务预约</a></c:if></td>
          <td width="56"><c:if test="${count>0}"><a href="javascript:Suit();">抱怨投诉</a></c:if></td>
          <!--<td width="56"><c:if test="${count>0}"><a href="javascript:Largess();"></c:if>赠品</a></td>-->
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
