<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<link href="../css/pss.css" rel="stylesheet" type="text/css">
<style media=print>
.Noprint {
	display: none;
}
</style>
	</head>

<body style="overflow: auto;">
<center class="Noprint">
<div class="printstyle">
<img src="../images/print.gif" onClick="javascript:window.print();"></img>
</div>
<hr align="center" width="100%" size="1" noshade>
</center>
		<div id="abc">
			<div align="center"
				class="print_title_style">
				会员资料
			</div>
            <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right">省份：</td>
            <td ><windrp:getname key='countryarea' value='${Province}' p='d'/>
</td>
            <td align="right">城市：</td>
            <td><windrp:getname key='countryarea' value='${City}' p='d'/>
</td>
            <td align="right">地区：</td>
            <td><windrp:getname key='countryarea' value='${Areas}' p='d'/>
</td>
          </tr>
           <tr>
            <td align="right">是否激活：</td>
            <td ><windrp:getname key='YesOrNo' value='${IsActivate}' p='f'/></td>
            <td align="right">客户来源：</td>
            <td><windrp:getname key='Source' value='${Source}' p='d'/></td>
            <td align="right">性别：</td>
            <td><windrp:getname key='Sex' value='${MemerSex}' p='f'/></td>
          </tr>
          <tr>
            <td align="right">制单机构：</td>
            <td >${oname}</td>
            <td align="right">制单人：</td>
            <td>${uname}</td>
            <td align="right">手机是否空：</td>
            <td><windrp:getname key='YesOrNo' value='${tel}' p='f'/></td>
          </tr>
          <tr >
            <td  align="right">登记日期：</td>
            <td>${BeginDate}-${EndDate}</td>
            <td align="right">关键字：</td>
            <td>${KeyWord}</td>
            <td align="right">&nbsp;</td>
            <td >&nbsp;</td>
          </tr>
		   <tr>
          <td width="13%"  align="right">打印机构：</td>
          <td width="21%">${porganname}</td>
           <td width="10%"  align="right">打印人：</td>
          <td width="23%">${pusername}</td>
           <td width="14%"  align="right">打印时间：</td>
          <td width="19%">${ptime}</td>
        </tr>
      </table>
			<table width="100%" border="0" cellpadding="0" cellspacing="1"
				class="sortable">
			  <tr align="center" class="title-top">
					<td width="80" >
									编号
								</td>
								<td width="150" >
									会员名称
								</td>
								<td width="50" >
									性别
								</td>
								<td >
									单位
								</td>
								<td >
									手机
								</td>
								
								<td >
									客户来源
								</td>
								<td width="100">
									登记日期
								</td>								
								<td >
									制单机构
								</td>
				</tr>
				<logic:iterate id="c" name="usList">
								<tr class="table-back-colorbar"
									onClick="CheckedObj(this,'${c.cid}');">
									<td>
										${c.cid}
									</td>
									<td>
										${c.cname}
									</td>
									<td>
										${c.membersexname}
									</td>
									<td>
										${c.membercompany}
									</td>
									<td>
										${c.mobile}
									</td>
									<td>
										${c.sourcename}
									</td>
									<td>
										<windrp:dateformat value="${c.makedate}" p="yyyy-MM-dd"/>
									</td>
									
									<td>
										<windrp:getname key="organ" p="d" value="${c.makeorganid}"/>
									</td>
								</tr>
							</logic:iterate>
			</table>
		</div>
	</body>
</html>
