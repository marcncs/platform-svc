<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>


<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<script type="text/javascript" src="../js/prototype.js"></script>
<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
<script language="JavaScript">
function formcheck(){
	if ( !Validator.Validate(document.refer,2) ){
		return false;
	}
	showloading();
	return true;
}
 function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
		var oldOrgan = document.refer.organid.value;
			document.refer.organid.value=p.id;
			document.refer.orgname.value=p.organname;
			//document.referForm.transportaddr.value=p.oaddr;
			document.refer.outwarehouseid.value=p.wid;
			document.refer.owname.value=p.wname;
		//	getOrganLinkmanBycid(p.id);
	}
function selectW(dom,type){
		var id = $('#organid').val();
		selectDUW(dom,'outwarehouseid',id,type,'');
		
	}
function selectT(dom,type){
		var id = $('#organid').val();
		selectDUW(dom,'templateNo',id,type,'');
		
	}
function checknum(obj){
	obj.value = obj.value.replace(/[^\d]/g,"");
}
</script>
</head>
<html:errors/>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
              <td width="772">导入</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		<form name="refer" method="post" enctype="multipart/form-data" action="../warehouse/stockAlterMoveImportAction.do" onSubmit="return formcheck()">
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	 
	    <tr>
          <td width="200" align="right">调出机构：</td>
           <td width="30%">
	      <input name="organid" type="hidden" id="organid" value="${organid}">
		  <input name="orgname" type="text" id="orgname" size="30" dataType="Require" msg="调出机构不能为空!" value="${oname}" readonly><a href="javascript:SelectOrgan2();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
		<span class="style1">*</span>
	    	</td>
           <td width="20%" align="right">调出仓库：</td>
	      <td width="30%">
	      	<input type="hidden" name="outwarehouseid" id="outwarehouseid" value="${outwarehouseid}">
			<input type="text" name="owname" id="owname" onClick="selectW(this,'rw')" value="${wname}" dataType="Require" msg="调出仓库不能为空!" readonly>	
			<span class="STYLE1">*</span></td>
        </tr>
         <tr>
          <td width="200" align="right">模板：</td>
           <td width="30%">
			<input type="text" name="templateNo" id="templateNo" onClick="selectT(this,'tn')" value="${templateNo}" dataType="Require" msg="模板不能为空!" readonly>	
			<span class="STYLE1">*</span></td>
	    	</td>
           <td width="20%" align="right"></td>
	      <td width="30%">
        </tr>
<%--       
	 	 <tr>
          <td width="200" align="right">表头行号：</td>
           <td width="30%">
		  <input name="titleRowNo" type="text" id="titleRowNo" dataType="Require" msg="表头行号不能为空!" value="${titleRowNo}" onkeyup="checknum(this)">
		<span class="style1">*</span>
	    	</td>
           <td width="20%" align="right">数据起始行号：</td>
	      <td width="30%">
			<input type="text" name="dataRowNo" id="dataRowNo" value="${dataRowNo}" dataType="Require" msg="数据起始行号不能为空!" onkeyup="checknum(this)">	
			<span class="STYLE1">*</span></td>
        </tr>
         --%>
	  <tr>
	  	<td align="right"></td>
          <td >选择xls文件：
            <input name="idcodefile" type="file" id="idcodefile" size="40" dataType="Filter" accept="xls" msg="只能上传xls格式的文件!"></td>
          <td align="right">&nbsp;</td>
          <td>&nbsp;</td>
           <td>&nbsp;</td>
          </tr>
	  </table>
	</fieldset>
		
		<table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr align="center">
                <td ><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
                    <input type="button" name="cancel" onClick="window.close()" value="取消"></td>
                </tr>
        </table> 
		</form>
		</td>
      </tr>
    </table></td>
  </tr>
</table>

</body>
</html>
