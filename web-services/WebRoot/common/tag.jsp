<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/taglib/windrp.tld" prefix="windrp" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<%@ taglib uri="/WEB-INF/taglib/pages.tld" prefix="pages" %>
<%@ taglib prefix="ws" uri="ws" %>
<%
 	response.setHeader("Pragma", "No-cache"); 
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", 0); 
	String localeLanguage = (String)session.getValue("localeLanguage").toString();
	
	String path = "/".equalsIgnoreCase(request.getContextPath())?"":request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort();
	String cssPath = path + "/css";
	String scriptPath = path + "/js";
	String imgPath = path + "/image";
	
%>