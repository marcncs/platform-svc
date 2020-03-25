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
		var checkid="";
		var brands="";
		function CheckedObj(obj,objid,objbrand){
		 for(i=0; i<obj.parentNode.childNodes.length; i++)
		 {
			   obj.parentNode.childNodes[i].className="table-back-colorbar";
		 }
		 
		 obj.className="event";
		 checkid=objid;
		 brands=objbrand;
		 CarDetail();
		
		}

		function addNew(){
			popWin("toAddCarAction.do",800,500);
		}

		function Update(){
			if(checkid!=""){
			popWin("toUpdCarAction.do?ID="+checkid,800,500);
			}else{
			alert("请选择你要操作的记录!");
			}
		}

		function Del(){
			if(checkid!=""){
				if(window.confirm("您确认要删除车牌为："+brands+"的车辆资料吗？如果删除将永远不能恢复!")){
					popWin2("../equip/delCarAction.do?CID="+checkid);
				}
			}else{
				alert("请选择你要操作的记录!");
			}
		}
	
		function CarDetail(){
			if(checkid!=""){
				document.all.submsg.src="../equip/carDetailAction.do?brand="+checkid;
			}else{
				alert("请选择你要操作的记录!");
			}
		}
	
	function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.search.MakeOrganID.value=p.id;
	document.search.oname.value=p.organname;
	clearUser("MakeID","uname");
}

		this.onload = function abc(){
			document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
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
					<table width="100%" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td>
								配送中心>>车辆资料
							</td>
						</tr>
					</table>
				<form name="search" method="post" action="listCarAction.do">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						
						<tr class="table-back">
							<td align="right">
								车牌号码：							</td>
							<td >
						  <input name="CarBrand" type="text" id="CarBrand" maxlength="30" value="${CarBrand}">						  </td>
							<td align="right">
								车型：							</td>
							<td >
								<windrp:select key="CarSort" name="CarSort" p="y|d" value="${CarSort}"/>						  </td>
							<td  align="right">
								是否空闲：							</td>
							<td >
								<windrp:select key="YesOrNo" name="IsLeisure" p="y|f" value="${IsLeisure}"/>						  </td>
							<td ></td>
						</tr>
						<tr class="table-back">
							<td align="right">
								创建日期：							</td>
							<td>
								<input type="text" name="BeginDate"  value="${BeginDate}"
									onFocus="javascript:selectDate(this)" size="12" readonly>
								-
								<input type="text" name="EndDate" value="${EndDate}"
									onFocus="javascript:selectDate(this)" size="12" readonly>							</td>
							<td align="right">
								制单机构：							</td>
						  <td><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
						    <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a> </td>
							<td align="right">
								制单人：							</td>
							<td><input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
								<input type="text" name="uname" id="uname"
									onClick="selectDUW(this,'MakeID',$F(MakeOrganID),'ou')"
									value="${uname}" readonly></td>
							<td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
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
							<td width="50">
								<a href="javascript:Del()"><img
										src="../images/CN/delete.gif" width="16" height="16"
										border="0" align="absmiddle">&nbsp;删除</a>
							</td>
							<td width="1">
								<img src="../images/CN/hline.gif" width="2" height="14">
							</td>
							<td class="SeparatePage">
								<pages:pager action="../equip/listCarAction.do" />
							</td>

						</tr>
					</table>
				  </div>
					<div id="abc" style="overflow-y: auto; height: 600px;">
					<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
						<tr align="center" class="title-top" >
							<td width="12%">
								车牌	</td>
							<td width="12%">
								车型	</td>
							<td width="12%">
								购买日期	</td>
							<td width="11%">
								价值	</td>
							<td width="7%">
								是否空闲	</td>
							<td width="14%">
								制单机构	</td>
							<td width="11%">
								制单人</td>
							<td width="21%">
								制单日期	</td>
							<!--td width="8%" >订单状态</td-->
						</tr>
						<logic:iterate id="s" name="also">
							<tr class="table-back-colorbar"
								onClick="CheckedObj(this,'${s.id}','${s.carbrand}');">
								<td>
									${s.carbrand}
								</td>
								<td>
									<windrp:getname key="CarSort" p="d" value="${s.carsort}"/>
								</td>
								<td>
									
									<windrp:dateformat value="${s.purchasedate}" p="yyyy-MM-dd"/>
								</td>
								<td><windrp:format value='${s.worth}'/>
								</td>
								<td>
									<windrp:getname key="YesOrNo" p="f" value="${s.isleisure}"/>
								</td>
								<td>									
									<windrp:getname key="organ" p="d" value="${s.makeorganid}"/>
								</td>
								<td>									
									<windrp:getname key="users" p="d" value="${s.makeid}"/>
								</td>
								<td>									
									<windrp:dateformat value="${s.makedate}"/>
								</td>
							</tr>
						</logic:iterate>
					</table>
					<br>
                    <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:CarDetail();"><span>车辆详情</span></a></li>
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
