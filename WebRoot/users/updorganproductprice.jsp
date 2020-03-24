<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title></title>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/validator.js"></script>
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<script language="javascript">
function formChk(){
if ( !Validator.Validate(document.addform,2) ){
		return false;
	}
	showloading();
	return true;
}
</script>
</head>

<body style="overflow:auto">
 <form name="addform" method="post" action="updOrganProductPriceAction.do" onSubmit="return formChk()">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772"> 修改产品订购价格</td>
      </tr>
    </table>
    <fieldset align="center"> <legend>
      <table >
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
        <table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
            <tr align="center" class="title-top">
              <td width="12%" >产品编号</td>
              <td width="19%" >产品名称</td>
              <td width="26%" >规格</td>
              <td width="13%" >单位</td>
              <td width="15%">价格</td>
              <td width="15%">返点比例</td>
            </tr>

			<c:forEach items="${spals}" var="d">
            <tr align="center" class="table-back">
              <td  align="left"><input name="id" type="hidden" id="id" value="${d.id}">${d.productid}</td>
              <td align="left">${d.productname}</td>
              <td align="left">${d.specmode}</td>
               <td align="left"><windrp:getname key='CountUnit' value='${d.unitid}' p='d'/></td>
              <td align="left"><input name="unitprice" type="text" value="${d.unitprice}" size="10" maxlength="8" onKeyPress="KeyPress(this)" dataType="Double" msg="价格只能是数字!"></td>
              <td align="left"><input name="frate" type="text" value="<windrp:format value='${d.frate*100}' n='2'/>" size="4" maxlength="4" onKeyPress="KeyPress(this)" dataType="Double" msg="返利比例只能是数字!">
                %</td>
            </tr>
			</c:forEach> 
        </table>
      </fieldset>
		
		<table width="100%"  border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td><div align="center">
                  <input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
                  <input type="button" name="cancel" onClick="javascript:window.close()" value="取消">
                </div></td>
              </tr>
        </table>
      </td>
  </tr>
</table>
          </form>
</body>
</html>
