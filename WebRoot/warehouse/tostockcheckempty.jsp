<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>导出盘点空表 </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<script language=javascript>

function SelectSingleProduct(){
	var psid=$F("psid");
	var wid=$F("warehouseid");
	var wbit=$F("warehousebit");
	var p=showModalDialog("../warehouse/selectSingleCheckProductAction.do?IsIDCode=0&KeyWordLeft="+psid+"&wid="+wid+"&wbit="+wbit,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	document.search.productid.value=p.id;
	document.search.ProductName.value=p.productname;
}	
</script>
</head>

<body>

<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td> 导出盘点空表 </td>
        </tr>
      </table>
       <form name="search" method="post" action="outPutCheckEmptyAction.do" target="_blank">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td align="right">仓库：</td>
            <td><windrp:warehouse name="warehouseid"/></td>
            <td align="right">仓位：</td>
            <td><input type="hidden" name="warehousebit" id="warehousebit">
<input type="text" name="bitname" id="bitname" onClick="selectDUW(this,'warehousebit',$F('warehouseid'),'b')" value="请选择" readonly></td>
            <td></td>
            <td></td>
          </tr>
           <tr class="table-back"> 
            <td width="14%"  align="right">产品类别：</td>
            <td width="25%" ><input type="hidden" name="psid" id="psid">
	        <windrp:pstree id="psid" name="productstruts"/></td>
            <td width="12%"  align="right">产品：</td>
            <td width="23%" ><input name="productid" type="hidden" id="productid">
              <input id="ProductName" name="ProductName" readonly><a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" align="absmiddle"  border="0"></a></td>
            <td align="right">是否盘点零库存：</td>
            <td><windrp:select key="YesOrNo" name="iszero" p="y|f"/></td>
            
          </tr>
         <tr>
          <td colspan="6" align="center"><input type="submit" name="Submit"  value="提交">&nbsp;&nbsp;
          <input type="button" name="button" value="取消" onClick="window.close();"></td>
        </tr>
       
      </table>
        </form>
      
    </td>
  </tr>
</table>
</body>
</html>
