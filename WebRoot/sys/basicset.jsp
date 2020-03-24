<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title></title>

</head>

<body>
<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="25" colspan="2" valign="top">
	<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td >系统设置&gt;&gt;基础设置</td>
      </tr>
    </table></td>
  </tr>
  <tr>
   <td width="130" valign="top" style="border-right: 1px solid #D2E6FF;"> 
    <table width="130" border="0" cellspacing="0" cellpadding="0" class="title-back">
		<tr>
			<td style="text-align: left;">&nbsp;&nbsp;&nbsp;基础设置</td>
			<td>&nbsp;</td>
		</tr>
	</table> 
	<table width="100%"  border="0" cellpadding="0" cellspacing="2" >  
	    <ws:hasAuth operationName="/sys/listMemberGradeAction.do">
	    	<tr>
	          <td >&nbsp;</td>
	          <td  ><a href="listMemberGradeAction.do" target="basic">会员级别</a></td>
	        </tr>
	    </ws:hasAuth>
	    <ws:hasAuth operationName="/sys/listOrganGradeAction.do">
		    <tr>
	          <td >&nbsp;</td>
	          <td  ><a href="listOrganGradeAction.do" target="basic">经销商级别</a></td>
	        </tr>
	    </ws:hasAuth>
		
		 <ws:hasAuth operationName="/sys/listMemberGradeRuleAction.do">
			<tr>
	          <td >&nbsp;</td>
	          <td  ><a href="listMemberGradeRuleAction.do" target="basic">会员级别晋级规则</a></td>
	        </tr>
	    </ws:hasAuth>
        
		 
        <ws:hasAuth operationName="/sys/listIntegralRuleAction.do">
			<tr>
	          <td >&nbsp;</td>
	          <td  ><a href="listIntegralRuleAction.do?RMode=OM" target="basic">订货方式积分规则</a></td>
	        </tr>
	    </ws:hasAuth>
        
        
         <ws:hasAuth operationName="/sys/listIntegralRuleAction.do">
			 <tr>
	          <td >&nbsp;</td>
	          <td  ><a href="listIntegralRuleAction.do?RMode=PM" target="basic">付款方式积分规则</a></td>
	        </tr>
	    </ws:hasAuth>
        
         <ws:hasAuth operationName="/sys/listIntegralRuleAction.do">
			 <tr>
	          <td >&nbsp;</td>
	          <td  ><a href="listIntegralRuleAction.do?RMode=SM" target="basic">送货时间积分规则</a></td>
	        </tr>
	    </ws:hasAuth>
        <ws:hasAuth operationName="/sys/listPaymentModeAction.do">
			<tr> 
	          <td>&nbsp;</td>
			  <td  ><a href="listPaymentModeAction.do" target="basic">收款方式</a></td>
	        </tr>
	    </ws:hasAuth>
        <ws:hasAuth operationName="/sys/listSendTimeAction.do">
			 <tr> 
	          <td >&nbsp;</td>
	          <td  ><a href="listSendTimeAction.do" target="basic">送货时间积分</a></td>
	        </tr>
	    </ws:hasAuth>
       
        <ws:hasAuth operationName="/sys/listCountryAreaAction.do">
			<tr> 
	          <td >&nbsp;</td>
	          <td  ><a href="listCountryAreaAction.do" target="basic">地区设置</a></td>
	        </tr>
	    </ws:hasAuth>
        
	<!--	<tr> 
          <td >&nbsp;</td>
          <td  ><a href="listApproveFlowAction.do" target="basic">审批流设置</a></td>
        </tr>-->
		 <ws:hasAuth operationName="/sys/listMakeConfAction.do">
			<tr> 
	          <td >&nbsp;</td>
	          <td  ><a href="listMakeConfAction.do" target="basic">编码规则设置</a></td>
	        </tr>
	    </ws:hasAuth>
        
        <ws:hasAuth operationName="/sys/listBaseResourceAction.do">
			<tr> 
	          <td >&nbsp;</td>
	          <td  ><a href="listBaseResourceAction.do?TagName=UploadIdcodeFlag" target="basic">条码上传设置</a></td>
	        </tr>
	    </ws:hasAuth>
         
         <ws:hasAuth operationName="/sys/listSystemResourceAction.do">
			<tr> 
	          <td >&nbsp;</td>
	          <td  ><a href="listSystemResourceAction.do" target="basic">系统参数设置</a></td>
	        </tr>
	    </ws:hasAuth>
         <ws:hasAuth operationName="/sys/listStockPileAgeingAction.do">
			<tr> 
	          <td >&nbsp;</td>
	          <td  ><a href="listStockPileAgeingAction.do" target="basic">库龄参数设置</a></td>
	        </tr>
	    </ws:hasAuth>
        
      </table></td>
    <td width="100%" valign="top">
      <IFRAME id="basic" 
      style="WIDTH: 100%; HEIGHT: 100%" 
      name="basic" src="../sys/listMemberGradeAction.do" frameBorder=0 
      scrolling=no></IFRAME></td>
  </tr>
</table>
</body>
</html>
