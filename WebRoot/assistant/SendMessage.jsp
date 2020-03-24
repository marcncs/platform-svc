<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
try{
	  String userid,password,destnumbers,msg,sendtime;
	  String retinfo = ""; 
	  String xmlstring="";
	  String qurl;
	  
	  String bstrMoMessageID="";
	  String bstrBusinessCode="";
	  String bstrLongCode="";
	  String bstrFeeMsisdn="";
	  
	  String bstrDesMsisdn="";
	  String bstrMessageContent="";
	  String lTpPid="";
	  String lTpUdhi="";
	  
	  String lSendDate="";
	  String lSendTime="";
	  String lExpireDate="";
	  String lExpireTime="";
	 
      response.setDateHeader("Expires", 0);
    
	  userid = request.getParameter("userid");
	  if (userid == null) userid = "";
	  
	   password = request.getParameter("password");
	  if (password == null) password = "";
	//-----------------1-----------------
	  bstrMoMessageID = request.getParameter("bstrMoMessageID");
	  if (bstrMoMessageID == null) bstrMoMessageID = "";
	  
	  bstrBusinessCode = request.getParameter("bstrBusinessCode");
	  if (bstrBusinessCode == null) bstrBusinessCode = "";
	  
	  bstrLongCode = request.getParameter("bstrLongCode");
		if (bstrLongCode == null) bstrLongCode = "";
		
		bstrFeeMsisdn = request.getParameter("bstrFeeMsisdn");
		if (bstrFeeMsisdn == null) bstrFeeMsisdn = "";
		
		  //-----------------2-----------------
	  bstrDesMsisdn = request.getParameter("bstrDesMsisdn");
	  if (bstrDesMsisdn == null) bstrDesMsisdn = "";
	  
	  bstrMessageContent = request.getParameter("bstrMessageContent");
	  if (bstrMessageContent == null) bstrMessageContent = "";
	  
	  lTpPid = request.getParameter("lTpPid");
		if (lTpPid == null) lTpPid = "";
		
		lTpUdhi = request.getParameter("lTpUdhi");
		if (lTpUdhi == null) lTpUdhi = "";
		
	//-----------------3-----------------
	  lSendDate = request.getParameter("lSendDate");
	  if (lSendDate == null) lSendDate = "";
	  
	  lSendTime = request.getParameter("lSendTime");
	  if (lSendTime == null) lSendTime = "";
	  
	  lExpireDate = request.getParameter("lExpireDate");
		if (lExpireDate == null) lExpireDate = "";
		
		lExpireTime = request.getParameter("lExpireTime");
		if (lExpireTime == null) lExpireTime = "";
		
		 if ( ( userid.length() > 0 ) && (password.length() > 0) ){      
     		 qurl = "http://"+userid+":"+java.net.URLEncoder.encode(password,"UTF-8")+"@59.151.7.69/SmbpHttpAgent/SmbpHttpAgent.asmx/SmbppSendUnicodeMessage?bstrMoMessageID="+ 
	   bstrMoMessageID+"&bstrBusinessCode="+bstrBusinessCode+
	   "&bstrLongCode="+bstrLongCode+"&bstrFeeMsisdn="+bstrFeeMsisdn+
	   "&bstrDesMsisdn="+bstrDesMsisdn+"&bstrMessageContent="+java.net.URLEncoder.encode(bstrMessageContent,"UTF-8")+
	   "&lTpPid="+lTpPid+"&lTpUdhi="+lTpUdhi+
	   "&lSendDate="+lSendDate+"&lSendTime="+lSendTime+
	   "&lExpireDate="+lExpireDate+"&lExpireTime="+lExpireTime;
	   
	   		//这里用了 dom4j 来分析返回的XML
       org.dom4j.io.SAXReader reader = new org.dom4j.io.SAXReader();
       org.dom4j.Document doc = reader.read(new java.net.URL(qurl));
      /*
       if ("0".endsWith(doc.valueOf("/root/@return"))) {
         // 节点"/root/@return" 的值为 0 时调用成功
         retinfo = "return=0,成功";
      }else{
         // 节点"/root/@return" 的值不为 0 时调用失败
         retinfo = "return=" + doc.valueOf("/root/@return") + ",info=" + doc.valueOf("/root/@info");
      }
	  */
       //用于打印返回XML的源以用于调试
       xmlstring = new String(doc.asXML().getBytes(),"UTF-8");
       	 }
	%>
	
	<html>
      <title>SendMessage 测试</title>
      <script language="JavaScript">
      </script>
      <style type="text/css">
        body,table,td,th{
        font-size: 9pt;
        }
      </style>
      <body>
        <b>SendMessage:发送短信</b>
        <FORM action="SendMessage.jsp" method="post">
          <b>帐 号:</b>
          <input name="userid" type="text" size="16" value="<%out.print(userid);%>"/>
         <br/>
          <b>密 码:</b>
          <input name="password" type="text" size="16" value="<%out.print(password);%>"/>
          <br/>          
          <b>bstrMoMessageID:</b><br/>
          <input type="text" name="bstrMoMessageID" value="<%=bstrMoMessageID%>">
          <br/>
          <b>bstrBusinessCode:</b><br/>
          <input type="text" name="bstrBusinessCode" value="<%=bstrBusinessCode%>">
          <br/>
          <b>bstrLongCode:</b><br/>
          <input type="text" name="bstrLongCode" value="<%=bstrLongCode%>">
          <br/>
          <b>bstrFeeMsisdn:</b><br/>
          <input type="text" name="bstrFeeMsisdn" value="<%=bstrFeeMsisdn%>">
          <br/>
          <b>bstrDesMsisdn:</b><br/>
          <input type="text" name="bstrDesMsisdn" value="<%=bstrDesMsisdn%>">
          <br/>
          <b>bstrMessageContent:</b><br/>
         <input type="text" name="bstrMessageContent" value="<%=bstrMessageContent%>">
          <br/>
          <b>lTpPid:</b><br/>
         <input type="text" name="lTpPid" value="<%=lTpPid%>">
          <br/>
          <b>lTpUdhi:</b><br/>
         <input type="text" name="lTpUdhi" value="<%=lTpUdhi%>">
          <br/>
          <b>lSendDate:</b><br/>
         <input type="text" name="lSendDate" value="<%=lSendDate%>">
          <br/>
          <b>lSendTime:</b><br/>
         <input type="text" name="lSendTime" value="<%=lSendTime%>">
          <br/>
          <b>lExpireDate:</b><br/>
         <input type="text" name="lExpireDate" value="<%=lExpireDate%>">
          <br/>
          <b>lExpireTime:</b><br/>
         <input type="text" name="lExpireTime" value="<%=lExpireTime%>">
          <br/>
         
		  <br/>
          <input name="Submit" type="button" value="GET 提 交" onClick="
                this.form.method='get';
                this.form.submit(); 
			  ">
          </input>
        </FORM>
        <b>返回的XML: </b><font color="red"><%=retinfo%></font>
        <br/>
        <textarea rows="8" cols="80"><%=xmlstring%></textarea>
      </body>
    </html>
    
<% }catch(Exception e ){
	e.printStackTrace();	
}
%>