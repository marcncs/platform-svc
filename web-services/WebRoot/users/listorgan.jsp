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
		<script language="javascript" src="../js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript">
		//jQuery解除与其它js库的冲突
		var $j = jQuery.noConflict(true);
		
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}

var checkid="";
var organModel="";
function CheckedObj(obj,objid,omodel){
 for(i=0; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;
 organModel=omodel;
  pdmenu = getCookie("oCookie");
	 switch(pdmenu){
	 	//case "0":Dept(); break;
	 	case "11":
		 	if(document.getElementById("WarehouseUrl")){
		 		Warehouse(); break;
			}
		case "2":AlreadyProduct(); break;
		case "3":ToAddProduct(); break;
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
		case "1":
			if(document.getElementById("OrganRelationUrl")){
				OrganRelation();break;
			}
		case "11":
			if(document.getElementById("OrganSalesmanUrl")){
				OrganSalesman();break;
			}
		//case "8":OrganScan();break;
		//case "9":ProductPrice();break;
		//case "10":OrganRole();break;
		default:Detail();
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
		search.action="listOrganAction.do";
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
	setCookie("oCookie","11");
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
			document.all.basic.src="organDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function LinkMan(){
		setCookie("oCookie","7");
		var url =  document.getElementById("linkManUrl");
		if(url){
			if(checkid!=""){
				document.all.basic.src="listOlinkmanAction.do?OID="+checkid;
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
	
	function OrganRelation(){
	setCookie("oCookie","1");
		if(checkid!=""){
			document.all.basic.src="../sys/listOrganRelationAction.do?OID="+checkid+"&organModel="+organModel;
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function OrganSalesman(){
		setCookie("oCookie","11");
			if(checkid!=""){
				document.all.basic.src="../keyretailer/listOrganSalesmanAction.do?OID="+checkid+"&organModel="+organModel;
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

			$j('#keyRt').hide();
			$j('#spaceKeyRt').show();
			$j('#keyRtSelect').hide();
			$j('select[name="isKeyRetailer"]').prop("disabled",true);
		}else if(organTypeVal == 1){
			// 工厂
			$j("#organModelTag").show();
			$j("#organModel1").show();
			$j("#organModel1").removeAttr("disabled");
			$j("#organModel2").hide();
			$j("#organModel2").attr("disabled","disabled");

			$j('#keyRt').hide();
			$j('#spaceKeyRt').show();
			$j('#keyRtSelect').hide();
			$j('select[name="isKeyRetailer"]').prop("disabled",true);
		}else if(organTypeVal == 2){
			// 经销商
			$j("#organModelTag").show();
			$j("#organModel1").hide();
			$j("#organModel1").attr("disabled","disabled");
			$j("#organModel2").show();
			$j("#organModel2").removeAttr("disabled");

			var ss = $j('#organModel2 select').val();
			if (ss == 2 || ss==3) {
				$j('#keyRt').show();
				$j('#spaceKeyRt').hide();
				$j('#keyRtSelect').show();
				$j('select[name="isKeyRetailer"]').prop("disabled",false);
				//console.log($j('select[name="isKeyRetailer"]'));
			} else {
				$j('#keyRt').hide();
				$j('#spaceKeyRt').show();
				$j('#keyRtSelect').hide();
				$j('select[name="isKeyRetailer"]').prop("disabled",true);
			}
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

		$j('select[name="isKeyRetailer"]').val(${isKeyRetailer}); // 回显
		$j('#organModel2 select').change(function(){
			var ss = $j(this).val();
			if (ss == 2 || ss==3) {
				$j('#keyRt').show();
				$j('#spaceKeyRt').hide();
				$j('#keyRtSelect').show();
				$j('select[name="isKeyRetailer"]').prop("disabled",false);
			} else {
				$j('#keyRt').hide();
				$j('#spaceKeyRt').show();
				$j('#keyRtSelect').hide();
				$j('select[name="isKeyRetailer"]').prop("disabled",true);
			}
		});
	});
	function SelectOutOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.ParentID.value=p.id;
			document.search.ParentIDName.value=p.organname;
	}
	function SelectOutOlinkman(){
		//var cid=document.search.CompanyID.value;
		var p1=showModalDialog("../common/selectOrganLinkmanAction.do?allSelect=1",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		document.search.CompanyID.value=p1.organid;
		document.search.CompanyName.value=p1.lname;
		
	}
	function SelectWareOutOrgan(){
		//var cid=document.search.CompanyID.value;
		var p1=showModalDialog("../common/SelectOrganWlinkmanAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		document.search.WareHouseName.value=p1.checkname;
		document.search.WareHouseID.value=p1.checkid;
			
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
						<form name="search" method="post" action="listOrganAction.do"
								onsubmit="return oncheck();">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							
							<tr class="table-back">
								<td width="9%" align="right">
									省份：
								</td>
								<td width="20%">
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
								<td width="10%" align="right">
									城市：
								</td>
								<td width="26%">
									<select id="city" name="City" onChange="getResult('city','areas');">
										<option value="">
											-选择-
										</option>
										<logic:iterate id="c" name="Citys">
										<option value="${c.id}" ${c.id==City?"selected":""}>${c.areaname}</option>
										</logic:iterate>
									</select>
								</td>
								<td width="10%" align="right">
									地区：
								</td>
								<td width="25%">
									<select id="areas" name="Areas" id="areas">
										<option value="">
											-选择-
										</option>
										<logic:iterate id="c" name="Areass">
										<option value="${c.id}" ${c.id==Areas?"selected":""}>${c.areaname}</option>
										</logic:iterate>
									</select>
								</td>
								<td></td>
							</tr>
							<tr class="table-back">
								<td align="right">
										上级机构：
								</td>
								<td>
										<input name="ParentID" type="hidden" id="ParentID"
											value="${ParentID}">
										<input name="ParentIDName" type="text" id="ParentIDName" size="30"
											value="${ParentIDName}" readonly>
										<a href="javascript:SelectOutOrgan();"><img
												src="../images/CN/find.gif" width="18" height="18" border="0"
												align="absmiddle">
										</a>
								</td>
							
							
							
								<td align="right">
									机构类别：
								</td>
								<td>
									<windrp:select key="OrganType" name="organType" p="y|f"
														value="${organType}" />
								</td>
								<td id="organModelTag" style="display:none;" align="right">
									机构类型：
								</td>
								<td id="organModel1" style="display:none;">
									<windrp:select key="PlantType" name="organModel"  p="y|f" 
											value="${organModel}" />
								</td>
								<td id="organModel2" style="display:none;">
									<windrp:select key="DealerType" name="organModel" p="y|f"
										value="${organModel}" />
								</td>
					           </tr>
							<tr class="table-back">
								<td align="right">
									是否撤消：
								</td>
								<td>
									<windrp:select key="YesOrNo" name="IsRepeal" p="y|f"
										value="${IsRepeal}" />
								</td>
								 <td width="8%" align="right">更新日期：</td>
					            <td width="26%"><input type="text" name="BeginDate" value="${BeginDate}" onFocus="javascript:selectDate(this)" size="12" readonly="readonly">
							- <input type="text" name="EndDate" value="${EndDate}" onFocus="javascript:selectDate(this)" size="12" readonly="readonly">
							
								<td align="right">
									审核状态：
								</td>
								<td>
									<windrp:select key="ValidateStatus" name="ValidateStatus" p="y|f"
										value="${ValidateStatus}" />
								</td>
									</tr>
							<tr class="table-back">
							
								<td align="right">
									关键字：
								</td>
								<td>
									<input type="text" name="KeyWord" value="${KeyWord}"
										maxlength="30" />
								</td> 
								<td align="right">
									联系人：
								</td>
								<td>
									<input type="text" id="linkman" name="linkman" value="${linkman}"
										maxlength="30" />
								</td> 
								<td align="right" id="keyRt" style="display:none">
									是否银河会员：
								</td>
								<td id="keyRtSelect" style="display:none">
									<select name="isKeyRetailer" disabled="disabled">
										<option value="">-选择-</option>
										<option value="1">是</option>
										<option value="0">否</option>
									</select>
								</td>
								<td id="spaceKeyRt"></td>
								<td class="SeparatePage">
									<input name="Submit" type="image" id="Submit"
										src="../images/CN/search.gif" border="0" title="查询">
								</td>
							</tr>
							
						</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
								<ws:hasAuth operationName="/users/toAddOrganAction.do">
								<td width="50" align="center">
									<a  href="javascript:addNew();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">新增</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<ws:hasAuth operationName="/users/toUpdOrganAction.do">
								<td width="50" align="center">
									<a href="javascript:UpdOrgan();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">修改</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<%--
								<td width="90" align="center">
									<a href="javascript:CopyOrganPrice();"><img
											src="../images/CN/copyprice.gif" width="16" height="16"
											border="0" align="absmiddle">机构价格克隆</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								--%>
								<ws:hasAuth operationName="/users/toImportOrganAction.do">
								<td width="50">
									<a href="javascript:Import()"><img
											src="../images/CN/import.gif" width="16" height="16"
											border="0" align="absmiddle">导入 </a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<%--
								<td width="80">
									<a href="javascript:ImportParent()"><img
											src="../images/CN/import.gif" width="16" height="16"
											border="0" align="absmiddle">经销商导入 </a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								<td width="70">
									<a href="javascript:ImportNetnode()"><img
											src="../images/CN/import.gif" width="16" height="16"
											border="0" align="absmiddle">网点导入 </a>
								</td>
								
									<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								<td width="90">
									<a href="javascript:ImportRegion()"><img
											src="../images/CN/import.gif" width="16" height="16"
											border="0" align="absmiddle">区域信息导入</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								--%>
								<ws:hasAuth operationName="/users/excPutOrganAction.do">
								<td width="50">
									<a href="javascript:OutPut();"><img
											src="../images/CN/outputExcel.gif" width="16" height="16"
											border="0" align="absmiddle">导出</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="1" height="14">
								</td>
								</ws:hasAuth>
								<%--
								<td width="90">
									<a href="javascript:OutPutFontOrgan();"><img
											src="../images/CN/outputExcel.gif" width="16" height="16"
											border="0" align="absmiddle">网点信息导出</a>
								</td>
								--%>
								<!--<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
                                <td width="50"><a href="javascript:DownTxt();"><img src="../images/CN/downpda.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;下载</a></td>-->
								<td class="SeparatePage">
									<pages:pager action="../users/listOrganAction.do" />
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
									内部编码
								</td>
								<td>
									机构名称
								</td>
								<td>
									机构类别
								</td>
								<td>
									机构类型
								</td>
								<td>
									省
								</td>
								<td>
									市
								</td>
								<td>
									区
								</td>
								<td>
									上级机构
								</td>
								<td width="5%">
									是否撤消
								</td>
								<td width="5%">
									是否银河会员
								</td>
								<td>
									审核状态
								</td>
								<td>
									大区
								</td>
								<td>
									地区
								</td>
								<td>
									小区
								</td>
							</tr>
							<logic:iterate id="d" name="dpt">
								<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${d.id}','${d.organModel}');">
									<td>
										${d.id}
									</td>
									<td>
										${d.oecode}
									</td>
									<td>
										${d.organname}
									</td>
									<td>
										<windrp:getname key='OrganType' value='${d.organType}' p='f'/>
									</td>
									<td>
										<windrp:getname key="${d.organType==1?'PlantType':'DealerType' }" value='${d.organModel}' p='f'/>
									</td>
									<td>
										${d.provincename}
									</td>
									<td>
										${d.cityname}
									</td>
									<td>
										${d.areasname}
									</td>
									<td>
										${d.parentidname}
									</td>
									<td>
										<windrp:getname key='YesOrNo' value='${d.isrepeal}' p='f' />
									</td>
									<td>
										<c:if test="${d.isKeyRetailer == 0}">否</c:if>
										<c:if test="${d.isKeyRetailer == 1}">是</c:if>
									</td>
									<td>
										<windrp:getname key='ValidateStatus' value='${d.validatestatus}' p='f' />
									</td>
									<td>
										${d.firstAreaName}
									</td>
									<td>
										${d.secondAreaName}
									</td>
									<td>
										${d.thirdAreaName}
									</td>
								</tr>
							</logic:iterate>
						</table>
						</form>
						<br>
						<div style="width: 100%">
							<div id="tabs1">
								<ul>
									<li><a id="DetailUrl" href="javascript:Detail();"><span>详情</span></a></li>
									<ws:hasAuth operationName="/users/listOlinkmanAction.do">
									<li><a id="linkManUrl" href="javascript:LinkMan();"><span>联系人</span></a></li>
									</ws:hasAuth>
									<ws:hasAuth operationName="/sys/listWarehouseAction.do">
										<li><a id="WarehouseUrl" href="javascript:Warehouse();"><span>仓库设置</span></a></li>
									</ws:hasAuth>
									<ws:hasAuth operationName="/sys/listOrganRelationAction.do">
										<li><a id="OrganRelationUrl" href="javascript:OrganRelation();"><span>进货关系设置</span></a></li>
									</ws:hasAuth>
									<ws:hasAuth operationName="/keyretailer/listOrganSalesmanAction.do">
										<li><a id="OrganSalesmanUrl" href="javascript:OrganSalesman();"><span>关联销售人员</span></a></li>
									</ws:hasAuth>
									<ws:hasAuth operationName="/users/listOrganProductAlreadyAction.do">
									<li>
										<a href="javascript:AlreadyProduct();"><span>已经营产品</span>
										</a>
									</li>
									</ws:hasAuth>
									<ws:hasAuth operationName="/users/listOrganProductForSelectAction.do">
									<li>
										<a href="javascript:ToAddProduct();"><span>选择经营产品</span> </a>
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

