<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
				<link href="../css/pss.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
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
				采购换货列表
			</div>
             <table width="100%" border="0" cellpadding="0" cellspacing="0">
           <tr >
            <td  align="right">供应商：</td>
            <td><windrp:getname key='Genre' value='${Genre}' p='d'/>
              ${ProvideName}</td>
            <td align="right">是否复核：</td>
            <td><windrp:getname key='YesOrNo' value='${IsAudit}' p='f'/></td>
            <td align="right">制单日期：</td>
            <td >${BeginDate}-${EndDate}</td>
          </tr>
          <tr>
            <td align="right">是否回收：</td>
            <td ><windrp:getname key='YesOrNo' value='${IsReceive}' p='f'/></td>
            <td align="right">关键字：</td>
            <td>${KeyWord}</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
		   <tr>
          <td width="13%"  align="right">打印机构：</td>
          <td width="21%">${porganname}</td>
           <td width="10%"  align="right">打印人：</td>
          <td width="23%">${pusername}</td>
           <td width="9%"  align="right">打印时间：</td>
          <td width="24%">${ptime}</td>
        </tr>
      </table>
						<table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
							<tr align="center" class="title-top">
								<td width="12%">
									编号
								</td>
								<td width="20%">
									供应商
								</td>
								<td width="13%">
									联系人
								</td>								
								<td width="11%">
									制单人
								</td>
								<td width="16%">
									制单日期
								</td>
                                <td width="7%">
									是否复核
								</td>
								<td width="7%">
									是否回收
								</td>
                                <td width="7%">
									是否作废
								</td>
							</tr>
							<logic:iterate id="s" name="also">
								<tr class="table-back-colorbar" >
									<td>
										${s.id}
									</td>
									<td>
										${s.providename}
										<a href="../sales/listCustomerAction.do?CID=${s.provideid}"><img
												src="../images/CN/go.gif" width="10" height="10" border="0">
										</a>
									</td>
									<td>
										${s.plinkman}
									</td>									
									<td>
										<windrp:getname key="users" p="d" value="${s.makeid}"/>
									</td>
									<td>
										${s.makedate}
									</td>
                                    <td>
										<windrp:getname key="YesOrNo" p="f" value="${s.isaudit}"/>
									</td>
									<td>										
										<windrp:getname key="YesOrNo" p="f" value="${s.isreceive}"/>
									</td>
                                    <td>										
										<windrp:getname key="YesOrNo" p="f" value="${s.isblankout}"/>
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br />
					</div>
		
	</body>
</html>
