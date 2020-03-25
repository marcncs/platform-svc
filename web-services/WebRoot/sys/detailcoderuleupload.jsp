<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/function.js"></SCRIPT>

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function Update(){
	popWin("toUpdCodeRuleUploadAction.do",700,400);		
}
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td> 
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772">采集条码规则</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>
	
	    <table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr class="title-func-back">
				<td width="50" align="center">
					<a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
					<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
				<td style="text-align:right">&nbsp;</td>
			</tr>
		</table>
	   <table class="sortable" width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="18%">属性</td>
          <td width="13%" > 起始位</td>
          <td width="14%">长度</td>
		  <td width="55%">&nbsp;</td>
         </tr>
		 <c:forEach var="cu" items="${culist}"><tr class="table-back-colorbar">
          <td width="18%"><windrp:getname key='CRUProperty' value='${cu.cruproperty}' p='f'/>：</td>
          <td width="13%">${cu.startno}</td>
          <td width="14%">${cu.lno}</td>
		  <td width="55%">&nbsp;</td>
         </tr>	
		 </c:forEach>	 
      </table>

	
	</td>
  </tr>
</table>	
    </td>
  </tr>
</table>

</body>
</html>
