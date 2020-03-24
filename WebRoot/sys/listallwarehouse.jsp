<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>机构列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectDate.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<script language="javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script type="text/javascript">
		//jQuery解除与其它js库的冲突
		var $j = jQuery.noConflict(true);
		
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.offsetHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}

var checkid="";
function CheckedObj(obj,objid){
 for(i=0; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;
  pdmenu = getCookie("oCookie");
	 switch(pdmenu){
	 	//case "0":Dept(); break;
	 	case "1":
		 	if(document.getElementById("WarehouseUrl")){
		 		Warehouse(); break;
			}
		//case "2":AlreadyProduct(); break;
		//case "3":ToAddProduct(); break;
		//case "4":
		//	if(document.getElementById("SafetyUrl")){
		//		Safety(); break;
		//	}
		//case "5":
			//if(document.getElementById("AwakeUrl")){
				//Awake();break;
			//}
		case "6":
			if(document.getElementById("DetailUrl")){
				Detail();break;
			}
		case "7":
			if(document.getElementById("LinkManUrl")){
				LinkMan();break;
			}
		//case "8":OrganScan();break;
		//case "9":ProductPrice();break;
		//case "10":OrganRole();break;
		default:Detail();
	 }

}

function addNew(){
	popWin("../users/toAddOrganAction.do",900,450);
	}

function UpdOrgan(){
	if(checkid!=""){
		popWin("toUpdOrganAction.do?ID="+checkid,900,450);		
	}else{
		alert("请选择你要修改的记录!");
	}
}

function Import(){		
		popWin("../users/toImportOrganAction.do",500,300);
	}
	
	function ImportParent(){
		popWin("../users/toImportOrganParentAction.do",500,300);
	}

	function ImportNetnode(){
		popWin("../users/toImportNetnodeAction.do",500,300);
	}
	
	function ImportRegion(){ 
	  popWin("../users/toImportOrganRegionAction.do",500,300);
	}


	function oncheck(){
		search.target="";
		search.action="../users/listStoreHouseAction.do";
		search.submit();
	}
	function OutPut(){
		search.target="";
		search.action="excPutOrganAction.do";
		search.submit();
	}
	
	function DownTxt(){
		search.target="";
		search.action="txtPutOrganAction.do";
		search.submit();
	}

	function Dept(){
	setCookie("oCookie","0");
		if(checkid!="" ){
			document.all.basic.src="../users/listDeptAction.do?OID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Warehouse(){
	setCookie("oCookie","1");
		if(checkid!=""){
			document.all.basic.src="../sys/listWarehouseAction.do?OID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function AlreadyProduct(){
	setCookie("oCookie","2");
		if(checkid!=""){
			document.all.basic.src="../users/listOrganProductAlreadyAction.do?OID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function ToAddProduct(){
	setCookie("oCookie","3");
		if(checkid!=""){
			document.all.basic.src="../users/listOrganProductForSelectAction.do?OID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	
	function Safety(){
	setCookie("oCookie","4");
		if(checkid!=""){
			document.all.basic.src="listOrganSafetyIntercalateAction.do?OID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Awake(){
	setCookie("oCookie","5");
		if(checkid!=""){
			document.all.basic.src="listOrganAwakeAction.do?OID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function Detail(){
	setCookie("oCookie","6");
		if(checkid!=""){
			document.all.basic.src="../users/listWLinkManAction.do?warehouseid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function LinkMan(){
		setCookie("oCookie","7");
		var url =  document.getElementById("linkManUrl");
		if(url){
			if(checkid!=""){
				document.all.basic.src="../users/listWLinkManAction.do?warehouseid="+checkid;
			}else{
				alert("请选择你要操作的记录!");
			}
		}
		
	}
	
	function OrganScan(){
		setCookie("oCookie","8");
		if(checkid!=""){
			document.all.basic.src="listOrganScanAction.do?OID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function ProductPrice(){
		setCookie("oCookie","9");
		if(checkid!=""){
			document.all.basic.src="listOrganProductPriceAction.do?OID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function OrganRole(){
		setCookie("oCookie","10");
		if(checkid!=""){
			document.all.basic.src="listOrganRoleForOrganAction.do?OrganID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	
	function CopyOrganPrice(){
		if(checkid!=""){
			window.open("toCopyOrganPriceAction.do?ID="+checkid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要修改的记录!");
		}
	}

var cobj ="";
function getResult(getobj,toobj){
	if(getobj=='province'){
		buildSelect("",document.getElementById("areas"));
	}
	cobj = toobj;
	var areaID = $F(getobj);
	var url = '../sales/listAreasAction.do';
	var pars = 'parentid=' + areaID;
   var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: pars, onComplete: showResponse}
				);

    }

function showResponse(originalRequest){
		var city = originalRequest.responseXML.getElementsByTagName("area"); 
		var strid = new Array();
		var str = new Array();
		for(var i=0;i<city.length;i++){
			var e = city[i];
			str[i] =new Array(e.getElementsByTagName("areaid")[0].firstChild.data , e.getElementsByTagName("areaname")[0].firstChild.data);
		}
		buildSelect(str,document.getElementById(cobj));//赋值给city选择框
    }

function buildSelect(str,sel) {//赋值给选择框
		sel.options.length=0;
		sel.options[0]=new Option("-选择-","")
		for(var i=0;i<str.length;i++) {
			sel.options[sel.options.length]=new Option(str[i][1],str[i][0])
		}
	}
	
	/**
	网点信息导出
	**/
	function OutPutFontOrgan(){
		search.target="";
		search.action="excPutFrontOrganAction.do";
		search.submit();
	}

	// 机构类型change事件,根据机构类型显示不同的明细
	function changeOrganType(){
		var organTypeVal = $j("#organType").val();
		if(organTypeVal == ""){
			// 请选择
			$j("#organModelTag").hide();
			$j("#organModel1").hide();
			$j("#organModel2").hide();
			$j("#organModel1").attr("disabled","disabled");
			$j("#organModel2").attr("disabled","disabled");
		}else if(organTypeVal == 1){
			// 工厂
			$j("#organModelTag").show();
			$j("#organModel1").show();
			$j("#organModel1").removeAttr("disabled");
			$j("#organModel2").hide();
			$j("#organModel2").attr("disabled","disabled");
		}else if(organTypeVal == 2){
			// 经销商
			$j("#organModelTag").show();
			$j("#organModel1").hide();
			$j("#organModel1").attr("disabled","disabled");
			$j("#organModel2").show();
			$j("#organModel2").removeAttr("disabled");
			
		}else{
			// 
			$j("#organModelTag").hide();
			$j("#organModel1").hide();
			$j("#organModel2").hide();
			$j("#organModel1").attr("disabled","disabled");
			$j("#organModel2").attr("disabled","disabled");
		}
	}
	//加载事件
	$j(function(){
		$j("#organType").change(changeOrganType);
		changeOrganType();
	});
	
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.MakeOrganId.value=p.id;
			document.search.oname.value=p.organname;
	}
	
</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									${menusTrace }
								</td>

							</tr>
						</table>
						<form name="search" method="post" action="../users/listStoreHouseAction.do"
							onsubmit="return oncheck();">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td align="right">
										所属机构：
									</td>
									<td>
										<input name="MakeOrganId" type="hidden" id="MakeOrganId"
											value="${MakeOrganId}">
										<input name="oname" type="text" id="oname" size="30" value="${oname}"
											readonly>
										<a href="javascript:SelectOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
									</td>



									<td align="right">
										关键字：
									</td>
									<td>
										<input type="text" name="KeyWord" value="${KeyWord}"
											maxlength="30" />
									</td>
									<td colspan="7">

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
								<td class="SeparatePage">
									<pages:pager action="../users/listStoreHouseAction.do" />
								</td>

							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
						<FORM METHOD="POST" name="listform" ACTION="">
							<table class="sortable" width="100%" border="0" cellpadding="0"
								cellspacing="1">

								<tr align="center" class="title-top">
									<td>
										编号
									</td>
									<td>
										内部编号
									</td>
									<td>
										仓库名称
									</td>
									<td>
										负责人
									</td>
									<td>
										联系电话
									</td>
									<td>
										仓库属性
									</td>
									<td>
										是否可用
									</td>
									<td>
										所属机构
									</td>
								</tr>
								<logic:iterate id="w" name="wls">
									<tr class="table-back-colorbar"
										onClick="CheckedObj(this,'${w.id}');">
										<td height="20">
											${w.id}
										</td>
										<td>
											${w.nccode }
										</td>
										<td>
											${w.warehousename}
										</td>
										<td>
											${w.username}
										</td>
										<td>
											${w.warehousetel}
										</td>
										<td>
											<windrp:getname key='WarehouseProperty'
												value='${w.warehouseproperty}' p='f' />
										</td>
										<td>
											<windrp:getname key='YesOrNo' value='${w.useflag}' p='f' />
										</td>
										<td>${w.organname}</td>
									</tr>
								</logic:iterate>

							</table>
						</form>
						<br>
						<div style="width: 100%">
							<div id="tabs1">
								<ul>
									<ws:hasAuth operationName="/users/listWLinkManAction.do">
										<li>
											<a id="linkManUrl" href="javascript:LinkMan();"><span>联系人</span>
											</a>
										</li>
									</ws:hasAuth>
								</ul>
							</div>
							<div>
								<IFRAME id="phonebook" style="WIDTH: 100%; HEIGHT: 100%"
									name="basic" src="../sys/remind.htm" frameBorder=0 scrolling=no
									onload="setIframeHeight(this)"></IFRAME>
							</div>
						</div>
					</div>

				</td>
			</tr>
		</table>
	</body>
</html>

