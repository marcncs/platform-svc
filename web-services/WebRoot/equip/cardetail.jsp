<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
	function print(soid){
			popWin("../equip/printCarDetailAction.do?carbrand="+soid,900,600);
	}
</script>
</head>
<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
          <td width="958"> 出车情况详情 </td>
          <td width="275" align="right"><table width="60"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"><a href="javascript:print('${carbrand}');">打印</a></td>
              </tr>
          </table></td>
  </tr>
</table>

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">			
			<td class="SeparatePage">
				<pages:pager action="../equip/carDetailAction.do" />
			</td>

		</tr>
	</table>
	<table class="sortable" width="100%"  border="0" cellpadding="0" cellspacing="1">
        <tr align="center" class="title-top">
          <td width="12%">对象名称</td>
		  <td width="13%">联系人</td> 
		  <td width="11%">联系电话</td> 
		  <td width="15%">司机</td> 
		 
		  <td width="26%">送货地址</td> 
          <td width="13%" >配送日期</td>
         <!-- <td width="10%">代收金额</td>-->
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.cname}</td> 
		  <td>${p.clinkman}</td>
		  <td>${p.tel}</td>
		 
		  
		  <td>${p.motormanname}</td>
		  <td>${p.addr}</td>
          <td >${p.equipdatename}</td>
          <!--<td>${p.eratotalsum}</td>-->
         
        </tr>
        </logic:iterate> 
      </table>

</td>
  </tr>
</table>
</body>
</html>
