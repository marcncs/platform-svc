<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script src="../js/prototype.js"></script>
<script src="../js/function.js"></script>
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</head>
<script language="javascript">
var KeyWord="";
var makeorganid = "";
var parray=new Array(); 
Array.prototype.remove=function(dx) { 
    if(isNaN(dx)||dx>this.length){
		return false;
	} 
    for(var i=0,n=0;i<this.length;i++){ 
        if(this[i]!=this[dx]){ 
            this[n++]=this[i];
        } 
    } 
    this.length-=1;
} 

function formcheck(){
	if( parray.length<=0 ){
		alert("请选择仓库!");
		return false;
	}
	validateProvide.speedstr.value=parray;
	showloading();
	return true;
}
 

function setIframeHeight(v_obj){
	var cwin=v_obj;
	if (document.getElementById){ 
		if (cwin && !window.opera){ 
			if (cwin.contentDocument && cwin.contentDocument.body.offsetHeight){
				cwin.style.height = (cwin.contentDocument.body.offsetHeight)+"px"; 
			}
			else if(cwin.Document && cwin.Document.body.scrollHeight){				
				cwin.style.height = (cwin.Document.body.scrollHeight)+"px";
			}
		}
	}
}

function submitCheckAll(){
	validateProvide.KeyWord.value=KeyWord;
	validateProvide.makeorganid.value=makeorganid;
	validateProvide.action="../users/setRuleUserWarehouseAction.do?uid=${uid}&type=checkAll";
	validateProvide.submit();
}


</script>

<body> 

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1" >
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td width="772">选择仓库</td>
      </tr>
    </table>
       
			<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="../users/selectRuleUserWHAction.do?uid=${uid}" frameBorder="0" scrolling="no" onload="setIframeHeight(this)"></IFRAME>  
         <form action="../users/setRuleUserWarehouseAction.do?uid=${uid}" method="post"  name="validateProvide" onSubmit="return formcheck();">
          <input type="hidden" name="KeyWord">
          <input type="hidden" name="makeorganid">
          <table width="100%" border="0" cellpadding="0" cellspacing="1">
		    
            <tr>
              <td><div align="center">
  				<input type="submit" name="Submit" value="确定">&nbsp;&nbsp;<input type="button" name="cancel" value="取消" onClick="window.close();"><input type="hidden" name="speedstr">
  				&nbsp;&nbsp;<input type="button" name="button1" value="全部选中" onclick="submitCheckAll()">&nbsp;&nbsp;
              </div></td>
            </tr>
			 
          </table>
       </form>
 </td>
</tr>
</table>

</body>
</html>
