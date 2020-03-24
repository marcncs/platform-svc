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
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}

var checkid="";
function CheckedObj(obj,objid){
 for(i=0; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;
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
		var sonid = $j("#sonid").val();
		var sid = $j("#sid").val();
		search.target="";
		search.action="../sys/selectListOrganAction.do?SID="+sid+"&OID="+sonid;
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
	setCookie("oCookie","11");
		if(checkid!=""){
			document.all.basic.src="../sys/listOrganRelationAction.do?OID="+checkid;
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
	
	function SelectOutOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.search.ParentID.value=p.id;
			document.search.ParentIDName.value=p.organname;
	}
	//添加进货关系
	function Affirm(){
			var sonid = $j("#sonid").val();
			var sid = $j("#sid").val();
			if(checkid!=""){
				$j.ajax({
					type :'post',
					url :'../sys/ajaxAddTranferRelationAction.do', 
					data:{"bkrid":sonid,"parentid":checkid,"sid":sid} ,
					dataType : 'json',
					async: false,
					success : function(result) {
						if(result.success=="true"){
							alert("操作成功！");
							 window.close();
							 window.opener.location.reload();
						 }else{
							 alert("操作失败！");
						 }
					}
				});
			}else{
				alert("请选择你要操作的记录!");
			}
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
									请选择进货机构
								</td>

							</tr>
						</table>
						<form name="search" method="post" action=""
								onsubmit="return oncheck();">
								<input type="hidden"  id="sonid" value="${OID }"/>
								<input type="hidden"  id="sid" value="${sid }"/>
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
							</tr>
							<tr class="table-back">
								<td align="right">
									关键字：
								</td>
								<td colspan="4">
									<input type="text" name="KeyWord" value="${KeyWord}"
										maxlength="30" />
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
									<a href="#" onClick="javascript:Affirm();"><img
											src="../images/CN/addnew.gif" width="16" height="16" border="0"
											align="absmiddle">&nbsp;确定</a>
								</td>
								<td width="1">
									<img src="../images/CN/hline.gif" width="2" height="14">
								</td>
								<td width="50">
									<a href="#" onClick="javascript:window.close();"><img
											src="../images/CN/cancelx.gif" width="16" height="16" border="0"
											align="absmiddle">&nbsp;取消</a>
								</td>
							<td class="SeparatePage"><pages:pager
									action="../sys/selectListOrganAction.do" /></td>
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
								<td width="10%">
									编号
								</td>
								<td width="12%">
									内部编码
								</td>
								<td width="17%">
									机构名称
								</td>
								<td width="6%">
									机构类别
								</td>
								<td width="6%">
									机构类型
								</td>
								<td width="8%">
									省
								</td>
								<td width="6%">
									市
								</td>
								<td width="6%">
									区
								</td>
							</tr>
							<logic:iterate id="d" name="dpt">
								<tr align="left" class="table-back-colorbar"
									onClick="CheckedObj(this,'${d.id}');">
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
								</tr>
							</logic:iterate>
						</table>
						</form>
					</div>

				</td>
			</tr>
		</table>
	</body>
</html>

