<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../../../common/tag.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="chrome=1"/>
		<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		<link rel="stylesheet" type="text/css" href="../../css/trace/style_new.css"/>
        <script language="javascript" src="../../js/jquery-1.11.1.min.js"></script>
		<title>合格</title>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#btn").click(viewDetail);
			});
			function viewDetail() {
				$("#btn").hide();
				$("#codeAndUrl").hide();
				$("#detail").show();
			}
		</script>
	</head>
	<body>
		<div class="logo tx-c">
			<img src="../../images/trace/logo.png" alt="" />
		</div>
		<div class="tx-c result">
			<p>扫描结果</p>
			<span></span>
		</div>
				<div class="contentBox" >
				<div id="codeAndUrl" class="tx-c">
					<p>追溯网站</p>
					<p><a href="#" onclick="viewDetail();">${traceUrl }</a></p>
					<p>单元识别码</p>
					<p>${code }</p>
					<button type="button" id="btn">详细信息</button>
				</div>
				<div id="detail" style="display:none;">
				<p><label for="">农药名称:</label><span>${result.standardName }</span></p>
				<p><label for="">登记证持有人:</label><span>${result.regCertUser }</span></p>
				<p><label for="">产品生产批次:</label><span>${result.batch}</span></p>
				<p><label for="">产品检验结果:</label><span class="active">合格</span></p>
				<p><label for="">产品检验单位:</label><span>${result.inspectionInstitution}</span></p>
				<p><label for="">产品检验时间:</label><span><windrp:dateformat value='${result.inspectionDate}' p='yyyy-MM' /></span></p>
				</div>
	     </div>
	     <p class="company tx-c">拜耳作物科学（中国）有限公司</p>
	</body>
</html>
