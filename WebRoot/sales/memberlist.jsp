<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>

		<script language="JavaScript">
	var checkid=0;
	var submenu=0;
	function CheckedObj(obj,objid,objcname){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 submenu = getCookie("MemberCookie");
	 switch(submenu){
	 	case "0": Detail(); break;
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
		popWin("../sales/toAddMemberAction.do",900,650);
	}

	function Update(){
		if(checkid!=""){
			popWin("toUpdMemberAction.do?cid="+checkid,900,650);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function UpdateClass(){
		if(checkid!=""){
			popWin("toUpdMemberClassAction.do?cid="+checkid,900,650);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		setCookie("MemberCookie","0");
		if(checkid!=""){
		document.all.submsg.src="../sales/memberDetailAction.do?Cid="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	
	function Appoints(){
		if(checkid!=""){
			popWin("../sales/toAppointsCustomerAction.do?CID="+checkid,520,280);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Linkman(){
	setCookie("MemberCookie","1");
		if(checkid!=""){
		document.all.submsg.src="listLinkManAction.do?Cid="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	function Contact(){
	setCookie("MemberCookie","2");
		if(checkid!=""){
		document.all.submsg.src="listContactLogByCustomerAction.do?Cid="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Hap(){
	setCookie("MemberCookie","3");
		if(checkid!=""){
		document.all.submsg.src="listHapAction.do?Cid="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Project(){
	setCookie("MemberCookie","4");
		if(checkid!=""){
		document.all.submsg.src="listProjectAction.do?Cid="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Product(){
	setCookie("MemberCookie","5");
		if(checkid!=""){
		document.all.submsg.src="../purchase/listCPProductAction.do?pid="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Demand(){
	setCookie("MemberCookie","6");
		if(checkid!=""){
		document.all.submsg.src="listDemandPriceByCustomerAction.do?CID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	
	function SaleOrder(){
	setCookie("MemberCookie","7");
		if(checkid!=""){
		document.all.submsg.src="listSaleOrderByCustomerAction.do?CID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function SaleShipment(){
	setCookie("MemberCookie","8");
		if(checkid!=""){
		document.all.submsg.src="listShipmentBillByCustomerAction.do?Cid="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}


	function SaleInvoice(){
	setCookie("MemberCookie","9");
		if(checkid!=""){
		document.all.submsg.src="listSaleInvoiceByCustomerAction.do?CID="+checkid;
		}else{
		alert("请选择你要修改的记录!");
		}
	}
	
	function Document(){
	setCookie("MemberCookie","10");
		if(checkid!=""){
		document.all.submsg.src="listDocumentAction.do?Cid="+checkid;
		}else{
		alert("请选择你要修改的记录!");
		}
	}
	
	function Pact(){
	setCookie("MemberCookie","11");
	if(checkid!=""){
		document.all.submsg.src="listPactAction.do?Cid="+checkid;
		}else{
		alert("请选择你要修改的记录!");
		}
	}
	
	function Service(){
	setCookie("MemberCookie","12");
	if(checkid!=""){
		document.all.submsg.src="listServiceAgreementAction.do?Cid="+checkid;
		}else{
		alert("请选择你要修改的记录!");
		}
	}
	
	function Suit(){
	setCookie("MemberCookie","13");
	if(checkid!=""){
		document.all.submsg.src="listSuitAction.do?Cid="+checkid;
		}else{
		alert("请选择你要修改的记录!");
		}
	}
	
	function Largess(){
	setCookie("MemberCookie","14");
	if(checkid!=""){
		document.all.submsg.src="listLargessAction.do?Cid="+checkid;
		}else{
		alert("请选择你要修改的记录!");
		}
	}

	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除编号为："+checkid+" 的会员记录吗？如果删除将永远不能恢复!")){
			popWin2("../sales/delMemberAction.do?CID="+checkid);
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Activate(){
		if(checkid!=""){
			popWin("../sales/activateCustomerAction.do?CID="+checkid,550,300);
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
	
	function Import(){
		popWin("../sales/toImportMemberAction.do",500,300);
	}
	

	function OutPutExcel(){
		search.target="";
		search.action="../sales/excPutMemberAction.do";
		search.submit();
	}

	
	function Print(){
		search.target="_blank";
		search.action="../sales/printMemberAction.do";
		search.submit();
		search.target="";
		search.action="../sales/listMemberAction.do";
	}

	function oncheck(){
		search.target="";
		search.action="../sales/listMemberAction.do";
		search.submit();
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
	this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearUser("MakeID","uname");
	}
</script>

		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="div1">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td>
									会员管理>>会员资料
								</td>
							</tr>
						</table>
							<form name="search" method="post"
								action="../sales/listMemberAction.do" >
						<table width="100%" height="44" border="0" cellpadding="0" 
							cellspacing="0">
						
							<tr class="table-back">
								<td width="11%" align="right">
									省份：
								</td>
								<td width="19%">
									<select name="Province"
										id="province" onChange="getResult('province','city');">
										<option value="">
											-省份-
										</option>
										<logic:iterate id="c" name="cals">
										<option value="${c.id}" ${c.id==Province?"selected":""}>${c.areaname}</option>
										</logic:iterate>
									</select>
								</td>
								<td width="14%" align="right">
									城市：
								</td>
								<td width="28%">
									<select name="City" onChange="getResult('city','areas');">
										<option value="">
											-城市-
										</option>
										<option value="${City}" ${City==null?"":"selected"}>
											${CityName }
										</option>
									</select>
								</td>
								<td width="13%" align="right">
									地区：
								</td>
								<td width="15%">
									<select name="Areas" id="Areas">
										<option value="">
											-地区-
										</option>
										<option value="${Areas}" ${City==null?"":"selected"}>
											${AreasName }
										</option>
									</select>
								</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									是否激活：
								</td>
								<td>
									
									<windrp:select key="YesOrNo" name="IsActivate" p="y|f" value="${IsActivate}"/>
								</td>
								<td align="right">
									客户来源：
								</td>
								<td>
									<windrp:select key="Source" name="Source" p="y|d" value="${Source}" />
								</td>
								<td align="right">
									性别：
								</td>
								<td>
									<windrp:select key="Sex" name="MemerSex" p="y|f" value="${MemerSex}"/>
								</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									制单机构：
								</td>
								<td>
									<input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
<input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>

								</td>
								<td align="right">
									制单人：
								</td>
								<td>
									<input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
									<input type="text" name="uname" id="uname"
										onClick="selectDUW(this,'MakeID',$F('MakeOrganID'),'ou')"
										value="${uname}" readonly>
								</td>
								<td align="right">
									手机是否空：
								</td>
								<td>
									<windrp:select key="YesOrNo" name="tel" p="y|f" value="${tel}" />
								</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									登记日期：
								</td>
								<td>
									<input name="BeginDate" type="text" value="${BeginDate}"
										onFocus="javascript:selectDate(this)" size="12" readonly="readonly">
									-
									<input name="EndDate" type="text" value="${EndDate}"
										onFocus="javascript:selectDate(this)" size="12" readonly="readonly">
								</td>
								<td align="right">
									关键字：
								</td>
								<td>
									<input type="text" name="KeyWord" value="${KeyWord}">

								</td>
								<td align="right">&nbsp;
									
								</td>
								<td class="SeparatePage">
									<input name="Submit" type="image" id="Submit" 
									src="../images/CN/search.gif" border="0" title="查询">
								</td>
							</tr>
							
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<td width="50">
									<a href="#" onClick="javascript:addNew();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;新增</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="javascript:Update();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;修改</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="70">
									<a href="javascript:UpdateClass();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;高级修改</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="javascript:Del()"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="javascript:Import()"><img 
									src="../images/CN/import.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;导入
									</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								
								<td width="50">
									<a href="javascript:OutPutExcel();"><img
											src="../images/CN/outputExcel.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;导出</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
			    <td width="50"><a href="javascript:Print();"><img
											src="../images/CN/print.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;打印</a> </td>
								<td class="SeparatePage">
									<pages:pager action="../sales/listMemberAction.do" />
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="abc" style="overflow-y: auto; height: 600px;">
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
							<tr align="center" class="title-top-lock">
								<td width="80" >
									编号
								</td>
								<td width="150" >
									会员名称
								</td>
								<td width="50" >
									性别
								</td>
								<td >
									单位
								</td>
								<td >
									手机
								</td>
								
								<td >
									客户来源
								</td>
								<td width="100">
									登记日期
								</td>
								<!--<td >
									积分
								</td>-->
								<td >
									制单机构
								</td>

							</tr>
							<c:set var="count" value="0" />
							<logic:iterate id="c" name="usList">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${c.cid}');">
									<td>
										${c.cid}
									</td>
									<td>
										${c.cname}
									</td>
									<td>
										${c.membersexname}
									</td>
									<td>
										${c.membercompany}
									</td>
									<td>
										${c.mobile}
									</td>
									<td>
										${c.sourcename}
									</td>
									<td>
										<windrp:dateformat value="${c.makedate}" p="yyyy-MM-dd"/>
									</td>
									
									<td>
										<windrp:getname key="organ" p="d" value="${c.makeorganid}"/>
									</td>
								</tr>
								<c:set var="count" value="${count+1}" />
							</logic:iterate>
						</table>
						<br />

						<div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>会员详情</span></a></li>
                <li><a href="javascript:Linkman();"><span>联系人</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" 
frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>
					</div>
				</td>
			</tr>
		</table>

	</body>
</html>
