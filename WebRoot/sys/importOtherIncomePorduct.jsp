<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>${title}</title>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
		<script type="text/javascript" src="../js/prototype.js"></script>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
		<script language="JavaScript">
		  function SelectOrgan(){
				var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
				if ( p==undefined){return;}
					document.refer.organid.value=p.id;
					document.refer.orgname.value=p.organname;
					document.refer.outwarehouseid.value=p.wid;
					document.refer.owname.value=p.wname;
			}

			function selectW(dom,type){
				var id = $('#organid').val();
				selectDUW(dom,'outwarehouseid',id,type,'');
				
			}
		function formcheck() {
			if (!Validator.Validate(document.refer, 2)) {
				return false;
			}
			showloading();
			return true;
		}
		
	</script>
	</head>
	<body>
		<form name="refer" method="post" enctype="multipart/form-data"
			action="../machin/importOtherIncomeProductAction.do"
			onSubmit="return formcheck()">
			<input type="hidden" name="incomeSort" value="${incomeSort}">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td>
						${title}
					</td>
				</tr>
			</table>
			<fieldset style="text-align: center;">
				<legend>
					基本信息
				</legend>
				<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<td align="right">
							导入机构：
						</td>
						<td>
							<input name="organid" type="hidden" id="organid" value="${organid}">
							<input name="orgname" type="text" id="orgname" size="30"
								dataType="Require" msg="必须选择导入机构!" value="${oname}" readonly>
							<a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif"
									width="18" height="18" border="0" align="absmiddle"> </a>
							<span class="style1">*</span>
						</td>
						<td align="right">
							导入仓库：
						</td>
						<td>
							<input type="hidden" name="outwarehouseid" id="outwarehouseid"
								value="${WarehouseID}">
							<input type="text" name="owname" id="owname" onClick="selectW(this,'rw')"
								value="${wname}" readonly dataType="Require" msg="必须选择导入机构!">
							<span class="STYLE1">*</span>
						</td>
					</tr>
				</table>
				<table class="table-detail">
					<tr>
						<td width="9%" align="right"></td>
						<td width="21%">
							选择其他入库资料文件：
							<input name="idcodefile" type="file" id="idcodefile" size="40"
								dataType="Filter" accept="xls" msg="只能上传Excel文件!">
						</td>
						<td width="23%">
							<a href="../common/downloadfile.jsp?filename=/templates/<windrp:encode key='其他入库导入模板.xls' />"
								title="模板下载">模板下载</a>
						</td>
					</tr>
				</table>
			</fieldset>
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
				<tr align="center">
					<td>
						<input type="submit" name="Submit" value="确定">
						&nbsp;&nbsp;
						<input type="button" name="cancel" onClick="window.close()" value="取消">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
