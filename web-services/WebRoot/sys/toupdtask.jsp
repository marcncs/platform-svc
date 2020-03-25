<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
<head >
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript">

</script>
</head>
<body>
<form id="form1" action="../sys/updTaskAction.do" method="post">
<input type="hidden" name="tid" value="${task.id }" />
<!-- 类型现在只有一种,即循环 -->
<input type="hidden" id="taskType" name="type" value="2"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
  	<td>&nbsp;</td>
  </tr>
  <tr>
    <td>
    <fieldset align="center"> 
    	<legend>
      	<table width="60" border="0" cellpadding="0" cellspacing="0">
        	<tr>
          		<td><STRONG><span style="color: #AA0708;font-size: 14px;">任务设置</span></STRONG></td>
        	</tr>
      	</table>
	  	</legend>
        <table width="100%" class="specialTable1">
            <tr>
              <td class="deepColor">任务名称：</td>
              <td class="normal">
              	<span>
              	    ${task.taskName }
              	</span>
              </td>
            </tr>
            <tr>
              <td class="deepColor">执行策略：</td>
              <td  class="normal">
                 <div id="everytime" style="display: block;">
                    <table>
                        <tr>
                           <td><input class="delayType" type="radio" name="delayType" value="0" ${ (null == task.delayType || task.delayType == 0)?"checked":"" }/></td>
                           <td>每隔(秒):</td>
                           <td rowspan="2">
                              <div style="width:1px;height:50px;background:#000;"></div>
                           </td>
                           <td rowspan="2">
                           <select name="cycleJobInterval">
                           	   <logic:iterate id="u" name="intervalSecMap" >
                           	   		<option value="${u.key }" ${(u.key == task.cycleJobInterval )?"selected":"" } >${u.value } </option>
                           		</logic:iterate>
                           	</select>
                           </td>
                        </tr>
                        <tr>
                           <td><input class="delayType" type="radio" name="delayType" value="1" ${ (null == task.delayType || task.delayType == 1)?"checked":"" }/></td>
                           <td>每隔(分):</td>
                           <%--<td>
                               <select name="cycleJobInterval" >
                           	   <logic:iterate id="u" name="intervalMinMap" >
                           	   		<option value="${u.key }" ${(u.key == task.cycleJobInterval && task.delayType== 1)?"selected":"" }>${u.value }</option>
                           		</logic:iterate>
                           	</select>
                           </td>
                        --%></tr>
                        <tr>
                          <td colspan="4">
                             <hr style="width:100%"/>
                          </td>
                        </tr>
                        <tr>
                           <td><input class="delayType" type="radio" name="delayType" value="2" ${ task.delayType == 2 ?"checked":""}/></td>
                           <td>每天(时:分:秒):</td>
                           <td rowspan="3">
                              <div style="width:1px;height:50px;background:#000;"></div>
                           </td>
                           <td rowspan="3">
                               <select name="delayHour">
	                           	   <logic:iterate id="u" name="hourMap" >
	                           	   		<option value="${u.key }" ${(u.key == task.delayHour && task.delayType >= 2)?"selected":"" }  >${u.value }</option>
	                           		</logic:iterate>
                           		</select>
                               		时
                               <select name="delayMinute" >
	                           	   <logic:iterate id="u" name="minMap" >
	                           	   		<option value="${u.key }" ${(u.key == task.delayMinute && task.delayType >= 2)?"selected":"" } >${u.value }</option>
	                           		</logic:iterate>
                           		</select name="delaySecond" >
                              		 分
                               <select name="delaySecond">
	                           	   <logic:iterate id="u" name="secMap" >
	                           	   		<option value="${u.key }"  ${(u.key == task.delaySecond && task.delayType >= 2)?"selected":"" } >${u.value }</option>
	                           		</logic:iterate>
                           		</select>
                              		 秒
                           </td>
                        </tr>
                         <tr>
                           <td><input class="delayType" type="radio" name="delayType" value="3" ${ task.delayType == 3 ?"checked":""} /></td>
                           <td>每周
                               <select name="delayWeek" >
	                           	   <logic:iterate id="u" name="weekMap" >
	                           	   		<option value="${u.key }" ${(u.key == task.delayWeek && task.delayType == 3)?"selected":"" }>${u.value }</option>
	                           		</logic:iterate>
                           		</select>:
                           </td>
                        </tr>
                        <tr>
                           <td><input class="delayType" type="radio" name="delayType" value="4" ${ task.delayType == 4 ?"checked":""}/></td>
                           <td>每月
                               <select name="delayMonthDay" >
	                           	   <logic:iterate id="u" name="monthMap" >
	                           	   		<option value="${u.key }" ${(u.key == task.delayMonthDay && task.delayType == 4)?"selected":""} >${u.value }</option>
	                           		</logic:iterate>
                           		</select>:
                           </td>
                        </tr>
                    </table>
                 </div>
              </td>
            </tr>
            <tr>
              <td class="deepColor">任务描述：</td>
              <td class="normal">
              <%--<input  name="remark"  value="${task.remark }" />
              --%>
              <textarea name="remark" rows="2" cols="65">${task.remark }</textarea>
              </td>
            </tr>
            <%--
            <tr>
              <td class="deepColor">状态：</td>
              <td  class="normal">
              	<windrp:select key="taskStatus" name="status" value="${task.status}" p="n|d"/>
              </td>
            </tr>
      	--%>
      	</table>
      </fieldset>
		<table width="100%" border="0" cellpadding="0" cellspacing="1">
			<tr><td>&nbsp;</td></tr>
            <tr align="center">
              <td><input type="submit" name="Submit" value="提交">&nbsp;&nbsp;
              <input type="button" name="canle" value="取消" onClick="javascript:window.close();"></td>
            </tr>
      	</table>
      </td>
  </tr>
</table>
<form>
</body>
</html>
