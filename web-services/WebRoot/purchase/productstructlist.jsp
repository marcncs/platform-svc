<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="ws" uri="ws" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>

<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/xtree.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xmlextras.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xloadtree.js"></SCRIPT>
<link rel="stylesheet" href="../css/xtree.css" type="text/css">

<script type="text/javascript">
var checkid="";
function show(psid){
	checkid=psid;
	document.all.product.src="../purchase/listProductAction.do?OtherKey="+psid;
}

function AddNew(){
	if(checkid!=""){
		window.open("../purchase/toAddProductStructAction.do?acode="+checkid,"newwindow","height=450,width=700,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}else{
	alert("请选择产品类别!");
	}
}

function Update(){
	if(checkid!=""){
		window.open("../purchase/toUpdProductStructAction.do?acode="+checkid,"newwindow","height=450,width=700,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}else{
	alert("请选择产品类别!");
	}
}		


function Del(){
	if(checkid!=""){
		if (checkid==1){
			alert("不能删除总类别!");
			return;
		}
		if(window.confirm("您确认要删除所选的产品类别吗？如果删除将永远不能恢复!")){
		window.open("../purchase/delProductStructAction.do?acode="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
		}
	}else{
	alert("请选择你要操作的记录!");
	}
}
</script>
</head>

<body >
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#6893CF">
			<tr>
				<td height="27" colspan="2">
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td>
								产品管理>>产品资料
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td width="160" valign="top" style="border-right: 1px solid #D2E6FF;">
					<table width="100%" border="0" cellspacing="0" cellpadding="0"
						class="title-back">
						<tr>
							<td style="text-align: left;">
								&nbsp;&nbsp;&nbsp;产品类别
							</td>
						</tr>                        
					</table>
                     <table width="100%" border="0" cellspacing="0" cellpadding="0"
                        class="title-func-back">
                        <tr>
                        	<ws:hasAuth operationName="/purchase/toAddProductStructAction.do">
                            <td><a href="javascript:AddNew();"><img
                                        src="../images/CN/addnew.gif" width="16" height="16"
                                        border="0" align="absmiddle">&nbsp;新增</a></td>
                            <td width="1">
                                <img src="../images/CN/hline.gif" width="2" height="14">
                          </td>
                            </ws:hasAuth>
                            <ws:hasAuth operationName="/purchase/toUpdProductStructAction.do">
                            <td><a href="javascript:Update();"><img
                                        src="../images/CN/update.gif" width="16" height="16"
                                        border="0" align="absmiddle">&nbsp;修改</a></td>
                            <td width="1">
                                <img src="../images/CN/hline.gif" width="2" height="14">
                          </td>
                          </ws:hasAuth>
                          <ws:hasAuth operationName="/purchase/delProductStructAction.do">
                            <td><a href="javascript:Del();"><img
                                src="../images/CN/delete.gif" width="16" height="16"
                                border="0" align="absmiddle">&nbsp;删除</a></td>	
                           </ws:hasAuth>							
                        </tr>
                    </table>
					<table height="100%" border="0" cellpadding="0" cellspacing="0" >
						<tr>								
							<td width="160" vAlign="top">
                            <div style="width:160px;height:100%; overflow:auto">
                               <table height="98%" border="0" >
                                <tr>
                                    <td valign="top">
                                    <script>
                                    var pstree = new WebFXLoadTree("All","../productStrutsAction.do?id=1","javascript:show('1')");
                                    document.write(pstree);
                                    </script>					
                                    </td>
                                   </tr>
                                 </table>
                                </div>
							</td>
						</tr>
                        <tr><td height="55">&nbsp;</td></tr>
					</table>
				</td>
				<td width="100%">
					<IFRAME id="product" style="WIDTH: 100%; HEIGHT: 100%"
						name="product" src="../purchase/listProductAction.do"
						frameBorder=0 scrolling=no></IFRAME>
				</td>
			</tr>
		</table>
		
		
		
</body>
</html>
