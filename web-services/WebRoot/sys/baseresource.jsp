<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title></title>

</head>
<script language="JavaScript">
this.onload =function onLoad(){
	document.getElementById("basic").src=document.getElementById("basicUrl").href;
}
</script>

<body>

<table width="100%" height="100%"  border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="25" colspan="2" valign="top">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td >${menusTrace }</td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td width="130" valign="top" style="border-right: 1px solid #D2E6FF;"> 
    <table width="130" border="0" cellspacing="0" cellpadding="0" class="title-back">
		<tr>
			<td style="text-align: left;">&nbsp;&nbsp;&nbsp;资源设置</td>
		</tr>
	</table>   
    <table height="100%" border="0" cellpadding="0" cellspacing="0" >
        <tr>								
            <td width="130" vAlign="top">    
	<div style="height:100%; overflow-y:auto">
	<table  border="0" cellpadding="0" >
		<%--
        <tr> 
          <td height="18">&nbsp;</td>
          <td height="18"><a href="listBaseResourceAction.do?TagName=Source" target="basic">客户来源</a></td>
        </tr>
		 <tr> 
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=Transit" target="basic">运货部</a></td>
        </tr>
		<tr> 
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=Brand" target="basic">品牌</a></td>
        </tr>
        <tr> 
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=Vocation" target="basic">行业</a></td>
        </tr>
        <tr> 
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=Genre" target="basic">供应商类型</a></td>
        </tr>
        <tr> 
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=PactType" target="basic">合同类型</a></td>
        </tr>
        <tr> 
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=TransportMode" target="basic">发运方式</a></td>
        </tr>
		<tr> 
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=OutlayProject" target="basic">费用项目</a></td>
        </tr>
        <tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=ActID" target="basic">动作</a></td>
        </tr>
		<tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=DitchRank" target="basic">渠道级别</a></td>
        </tr>
		<tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=Prestige" target="basic">信誉</a></td>
        </tr>
		<tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=HapKind" target="basic">机会性质</a></td>
        </tr>
		<tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=HapStatus" target="basic">机会状态</a></td>
        </tr>
        <tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=HapContent" target="basic">机会种类</a></td>
        </tr>
        <tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=HapSrc" target="basic">机会来源</a></td>
        </tr>
		<tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=PStatus" target="basic">项目状态</a></td>
        </tr>
		<tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=SStatus" target="basic">服务状态</a></td>
        </tr>
        <tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=SContent" target="basic">服务种类</a></td>
        </tr>
		<tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=SuitWay" target="basic">投诉方式</a></td>
        </tr>
        <tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=SuitContent" target="basic">投诉种类</a></td>
        </tr>
        <tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=Rank" target="basic">服务等级</a></td>
        </tr>
		<tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=DealWay" target="basic">处理方式</a></td>
        </tr>
		<tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=SelectContact" target="basic">联系内容</a></td>
        </tr>
		<tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=Rank" target="basic">重要性</a></td>
        </tr>
		<tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a href="listBaseResourceAction.do?TagName=CarSort" target="basic">车型</a></td>
        </tr>
		<tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=WithdrawSort" target="basic">退货类型</a></td>
	    </tr>
	    <tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=PurchaseSort" target="basic">采购类型</a></td>
	    </tr>
	     --%>
		<tr>
          <td height="18" >&nbsp;</td>
          <td height="18"  ><a id='basicUrl' href="listBaseResourceAction.do?TagName=CountUnit" target="basic">计量单位</a></td>
        </tr>
		<tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=ReturnReason" target="basic">退货原因</a></td>
	    </tr>
	    <%--
		<tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=OrganPricePolicy" target="basic">经销商价格政策</a></td>
	    </tr>
		<tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=ISort" target="basic">积分类别</a></td>
	    </tr>
		<tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=DSort" target="basic">积分类别详情</a></td>
	    </tr>
		<tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=RKey" target="basic">积分规则键</a></td>
	    </tr>
		<tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=Country" target="basic">国家</a></td>
	    </tr>
		<tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=SaleSort" target="basic">销售方式</a></td>
	    </tr>
		<tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=CallCenter" target="basic">呼叫中心IP</a></td>
	    </tr>  
	    <tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=ProductAlermDate" target="basic">产品预警期</a></td>
	    </tr> 
	     --%>      
	     <tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=PreGeneratedPCodeCnt" target="basic">13位可用小包装码预生成数量</a></td>
	    </tr>
	    <tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=TenDigitsPCodeCount" target="basic">10位可用小包装码预生成数量</a></td>
	    </tr>
	    <tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=BusyTimeDelay" target="basic">忙时每次小包装码生成数量</a></td>
	    </tr>
	     <tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=IdleTimeDelay" target="basic">闲时每次小包装码生成数量</a></td>
	    </tr>
	    <tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=BusyTime" target="basic">系统繁忙时间</a></td>
	    </tr>
	    <tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=CartonLabelCount" target="basic">标签打印份数</a></td>
	    </tr>
	    <tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=CustomerLevel" target="basic">客户级别</a></td>
	    </tr>
	    <tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=PromptInformation" target="basic">提示信息</a></td>
	    </tr>
	    <tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=NoBonusProduct" target="basic">不能积分兑换的产品</a></td>
	    </tr> 
	    <tr>
		  <td height="18" >&nbsp;</td>
		  <td height="18"  ><a href="listBaseResourceAction.do?TagName=PrivacyPolicy" target="basic">APP隐私内容版本号</a></td>
	    </tr> 
      </table> 
      </div>
	</td>
        </tr>
        <tr><td height="27">&nbsp;</td></tr>
    </table>
     
      
	  </td>
    <td width="100%" valign="top" height="100%">
      <IFRAME id="basic" 
      style="WIDTH: 100%; HEIGHT: 100%" 
      name="basic" src="" frameBorder=0 
      scrolling=no></IFRAME></td>
  </tr>
</table>
</body>
</html>
