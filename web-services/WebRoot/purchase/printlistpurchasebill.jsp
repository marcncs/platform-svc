<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<script type="text/javascript" src="../js/function.js"></script>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
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
				采购订单列表
			</div>
             <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right">供应商：</td>
            <td >${ProvideName}</td>
            <td align="right">是否复核：</td>
            <td><windrp:getname key='YesOrNo' value='${IsAudit}' p='f'/></td>
            <td align="right">是否批准：</td>
            <td><windrp:getname key='YesOrNo' value='${IsRatify}' p='f'/></td>
          </tr>
          <tr >
            <td  align="right">制单机构：</td>
            <td><windrp:getname key='Genre' value='${Genre}' p='d'/>
            ${oname}</td>
            <td align="right">制单部门：</td>
            <td><windrp:getname key='AbcSort' value='${AbcSort}' p='f'/>
            ${deptname}</td>
            <td align="right">制单人：</td>
            <td >${uname}</td>
          </tr>
          <tr >
            <td  align="right">预计到货日期：</td>
            <td><windrp:getname key='Genre' value='${Genre}' p='d'/>
            ${BeginDate}-${EndDate}</td>
            <td align="right">关键字：</td>
            <td><windrp:getname key='AbcSort' value='${AbcSort}' p='f'/>
            ${KeyWord}</td>
            <td align="right">&nbsp;</td>
            <td >&nbsp;</td>
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
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="sortable">
<tr align="center" class="title-top">
								<td width="120">
									编号
								</td>
								<td>
									供应商
								</td>
								<td>
									金额
								</td>
								<td>
									预计到货日期
								</td>
								<td width="70">
									是否复核
								</td>
								<td width="70">
									是否批准
								</td>
								<td>
									结算方式
								</td>
								<td>
									制单机构
								</td>
								<td>
									制单部门
								</td>
								<td>
									制单人
								</td>
							</tr>
							<logic:iterate id="p" name="alpb">
								<tr class="table-back-colorbar"
									>
									<td>
										${p.id}
									</td>
									<td>
										${p.pname}
									</td>
									<td>
										${p.totalsum}
									</td>
									<td>
										<windrp:dateformat value='${p.receivedate}' p='yyyy-MM-dd' />
									</td>
									<td>
										<windrp:getname key='YesOrNo' value='${p.isaudit}' p='f' />
									</td>
									<td>
										<windrp:getname key='YesOrNo' value='${p.isratify}' p='f' />
									</td>
									<td>
										<windrp:getname key='PayMode' value='${p.paymode}' p='f' />
									</td>
									<td>
										<windrp:getname key='organ' value='${p.makeorganid}' p='d' />
									</td>
									<td>
										<windrp:getname key='dept' value='${p.makedeptid}' p='d'/> 
									</td>
									<td>
										<windrp:getname key='users' value='${p.makeid}' p='d'/>
									</td>
								</tr>
							</logic:iterate>
						</table>
						<br>
					</div>
	</body>
</html>
