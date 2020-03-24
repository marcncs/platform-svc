<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772">产品详情</td>
    <td width="364" align="right"><table width="60"  border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="60" align="center"></td>
            </tr>
          </table></td>
  </tr>
</table>

          <fieldset align="center"> 
          <legend> 
            <table width="50" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>基本信息</td>
            </tr>
          </table>
		  </legend>
            <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
            <tr>
              <td  align="right">产品类别：</td>
              <td>${psidname}</td>
              <td  align="right">类别英文名：</td>
              <td>${psidnameen}</td>
              <td height="20" align="right">物料号：</td>
			   <td width="21%">${p.mCode}</td>
            </tr>
            <tr>
              <td width="11%"  align="right">产品名称：</td>
              <td width="21%">${p.productname}</td>
              <td width="12%" align="right">产品英文名：</td>
              <td width="18%">${p.productnameen}</td>
              <td width="13%" align="right">规格明细：</td>
              <td width="25%">${p.packSizeName}</td>
            </tr>
            <tr>
			   <td align="right">物料中文描述：</td>
			   <td width="21%">${p.matericalChDes}</td>
			   <td align="right">物料英文描述：</td>
			   <td width="21%">${p.matericalEnDes}</td>
			   <td align="right">保质期(天)：</td>
			   <td width="21%">${p.expiryDays}</td>
		      </tr>
		      <tr>
		       <td align="right">产品编号：</td>
               <td>${p.id}</td>
			   <td height="20" align="right">包装规格：</td>
			   <td width="21%">${p.specmode}</td>
			   <td align="right">包装规格英文：</td>
			   <td width="21%">${p.packSizeNameEn}</td>
		      </tr>
		      <tr>
		      	<td align="right">箱码打印：</td>
		      	<td><windrp:getname key='YesOrNo' value='${p.cartonPrintFlag}' p='f'/></td>
				<td align="right">小包装打印：</td>
				<td><windrp:getname key='YesOrNo' value='${p.primaryPrintFlag}' p='f'/></td>
				<td align="right">箱码扫描：</td>
				<td><windrp:getname key='YesOrNo' value='${p.cartonScanning}' p='f'/></td>
		      </tr>
		      <tr>
			   <td align="right">条码类型：</td>
			   <td><windrp:getname key="CodeType" value="${p.codeType}" p="f"/></td>
			    <td align="right">关联模式：</td>
			   <td><windrp:getname key="LinkMode" value="${p.linkMode}" p="f"/></td>
			   <td align="right">标签份数：</td>
			   <td>${p.copys}</td>
			   <td align="right"></td>
			   <td></td>
		      </tr>
		      <tr>
			   <td align="right">登记证类型：</td>
			   <td><windrp:getname key="RegCertType" value="${p.regCertType}" p="f"/></td>
			    <td align="right">登记证号：</td>
			   <td>
			      ${p.regCertCode}
			   </td> 
			   <td align="right">登记证号(6位)：</td>
			   <td>
			       ${p.regCertCodeFixed}
			   </td>
		      </tr>
		      <tr>
			   <td align="right">登记证持有人：</td>
			   <td>
			       ${p.regCertUser}
			   </td>
				   <td align="right">产品规格代码：</td>
			   <td>
				   ${p.specCode}				   	
			   </td>
			    <td align="right">农药标准名称：</td>
			   <td>
			   	   ${p.standardName}
			   </td>
			      </tr>
		      <tr>
			   
			   <td align="right">生产类型：</td>
			   <td>
			       <windrp:getname key="ProduceType" value="${p.produceType}" p="f"/>
			   </td>
			   <td align="right">内部生产类型：</td>
			   <td>
			       <windrp:getname key="InnerProduceType" value="${p.innerProduceType}" p="f"/>
			   </td>
		      </tr>
		      <tr>
		      	<td align="right">生产企业：</td>
			  	<td>${p.inspectionInstitution}</td>
			  	<%-- <td align="right">验证结果：</td>
			  	<td>${p.validResult}</td> --%>
			      </tr>
			</table>
			</fieldset>
			
		<fieldset align="center">
          <legend> 
            <table width="50" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td>产品属性</td>
            </tr>
          </table>
		  </legend>
			<table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
            <tr>
              <td align="right">是否可用：</td>
              <td><windrp:getname key='YesOrNo' value='${p.useflag}' p='f'/></td>
              <td align="right">是否可分包：</td>
              <td><windrp:getname key='YesOrNo' value='${p.isunify}' p='f'/></td>
              <td align="right">是否License-in：</td>
              <td><windrp:getname key='YesOrNo' value='${p.wise}' p='f'/></td>
            </tr>
            <tr>
              <td  align="right">小包装单位：</td>
              <td><windrp:getname key='CountUnit' value='${p.sunit}' p='d'/></td>
              <td  align="right">计量单位：</td>
              <td><windrp:getname key='CountUnit' value='${p.countunit}' p='d'/></td>
              <td  align="right">小包装到计量单位转化率：</td>
              <td>${p.boxquantity}</td>
            </tr>
            <tr>
              <td align="right">是否印刷二维码产品：</td>
              <td><windrp:getname key='YesOrNo' value='${p.isidcode}' p='f'/></td>
              <td align="right"></td>
              <td></td>
              <td align="right"></td>
              <td></td> 
            </tr>
            <tr>
              <td  align="right">制单机构：</td>
              <td><windrp:getname key='organ' value='${p.makeorganid}' p='d'/></td>
              <td align="right">制单人：</td>
              <td><windrp:getname key='users' value='${p.makeid}' p='d'/></td>
              <td align="right">制单日期：</td>
              <td>${p.makedate}</td>
            </tr>
            <tr >
              <td  align="right">备注：</td>
              <td colspan="5" ><textarea rows="10" cols="150"  readonly>${p.memo}</textarea></td>
              </tr>
              <tr>
			   <td  align="right" valign="top">单位：</td>
			   <td colspan="5">
				 <table width="271" border="0" id="xq" cellpadding="0" cellspacing="0">
                 <tr>
                   <td width="50" align="center">单位</td>
                   <td></td>
                   <td width="50">换数率</td>
                 </tr>
            	<logic:iterate id="f" name="afls" > 
                 <tr>
                   <td height="20" align="center">  1 <windrp:getname key='CountUnit' value='${f.funitid}' p='d'/>   </td>
                   <td> =  </td>
                   <td><input  type="text" id="xquantity" size="3" value="${f.xquantity}" readonly>
                      <windrp:getname key='CountUnit' value='${p.sunit}' p='d'/>
                   </td>
                 </tr>
				</logic:iterate> 
               </table>
               </td>
		      </tr>
          </table>
      </fieldset>


    </td>
  </tr>
</table>

</body>
</html>
