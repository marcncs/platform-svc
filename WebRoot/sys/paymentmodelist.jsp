<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script type="text/javascript" src="../js/function.js"></script>
<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	
	}
	
	function addNew(){
		popWin("toAddWarehouseAction.do",700,400);
	}
	
	function Update(){
		if(checkid>-1){
			popWin("toUpdPaymentModeAction.do?ID="+checkid,700,400);
		}else{
			alert("请选择要操作的记录！");
		}
	}
	
	
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td > 收款方式列表</td>
        </tr>
      </table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">			
			<td width="50" align="center">
				<a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
				<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>			
		  <td class="SeparatePage"><pages:pager action="../sys/listPaymentModeAction.do"/></td>
							
		</tr>
	</table>
	 <FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td width="18%"  >编号</td>
            <td width="25%" >分类</td>
            <td width="20%" >收款方式</td>
            <td width="37%" >积分比例</td>
          </tr>
          <logic:iterate id="w" name="wls" ><tr class="table-back-colorbar" onClick="CheckedObj(this,${w.id});"> 
            <td height="20">${w.id}</td>
            <td>${w.irid}</td>
            <td>${w.paymentname}</td>
            <td><windrp:format value='${w.integralrate}'/></td>
            </tr>
          </logic:iterate> 
        
      </table> 
      </form>    
    </td>
  </tr>
</table>
</body>
</html>
