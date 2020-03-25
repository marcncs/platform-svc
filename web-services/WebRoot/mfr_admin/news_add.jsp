<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
String path = "/".equalsIgnoreCase(request.getContextPath())?"":request.getContextPath();
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>云单页厂家管理平台</title>

  <link href="<%=path%>/res/bracket/css/style.default.css" rel="stylesheet">
	<link href="<%=path%>/res/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet" />
	<link href="<%=path%>/res/bracket/css/jquery.tagsinput.css" rel="stylesheet" />
	
	<style type="text/css">
		.progress {
			margin-top:15px;
		}
		.progress-cancel {
			float:right;
		}
		.filemanager-options {
			margin-bottom:0 !important;
		}
		.fixed-footer {
			position: fixed;
	    bottom: 0;
	    left: 0;
	    right: 0;
	    z-index: 1040;
	    background-color: #fff;
	    border-top: 1px solid #d9dadc;
	    padding: 10px;
	    margin:auto;
	    text-align: center;
		}
		.main-body {
		    margin: 0 0 80px;
		}
	</style>

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="js/html5shiv.js"></script>
  <script src="js/respond.min.js"></script>
  <![endif]-->
</head>

<body class="horizontal-menu fixed notfound">

<section>
  
  <div class="mainpanel">
    
    <div class="headerbar">
    
    	<div class="header-left">
        
        <!-- <div class="logopanel">
            <h1><span>[</span> 云单页 <span>]</span></h1>
        </div>logopanel -->
        
      </div><!-- header-left -->
      
      <a class="menutoggle"><i class="fa fa-bars"></i></a>

			<!-- header-right -->
<%-- 			<jsp:include page="/mfr_admin/include_header.jsp" flush="true" /> --%>
    </div><!-- headerbar -->
        
    <!-- 
    <div class="pageheader">
      <h2><i class="fa fa-home"></i> 素材管理 <span>添加视频</span></h2>
      <div class="breadcrumb-wrapper">
      </div>
    </div>
     -->
     
    <div class="contentpanel main-body">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h4 class="panel-title">添加成功故事</h4>
        </div>
        <!-- panel-body -->
        <div class="panel-body form-horizontal">
        	<div class="form-group">
						<label class="col-sm-2 control-label">封面图片<span class="asterisk">*</span></label>
						<div class="col-sm-10">
							<div class="input-group">
								<p>为节省用户流量，建议上传小于5M的图片，建议宽度960-1080像素之间</p>
                <button class="btn btn-sm btn-info" id="btn_add_news_file_upload">本地上传</button>
                <button class="btn btn-sm btn-info" onclick="chooseImgFromMedias()">从图片库选择</button>
                <input id="txt_add_news_pic_url" type="hidden"/>
                <div id="img_add_news_container" style="padding-top: 15px; display:none;">
                	<img id="img_add_news" width="200px">
                </div>
                <div id="progress_bar"></div>
              </div>
              <div style="display:none;" class="checkbox block"><label><input id="chk_add_news_pic_is_show" type="checkbox" checked> 封面图片显示在正文中</label></div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">故事标题 <span class="asterisk">*</span></label>
						<div class="col-sm-10">
							<div id="div_add_news_title" class="input-group">
                 <input id="txt_add_news_title" type="text" class="form-control" data-max-len="35" required> 
               </div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">故事摘要 <span class="asterisk">*</span></label>
						<div id="div_add_news_summary" class="col-sm-10">
							<textarea id="txt_add_news_summary" class="form-control" rows="2" data-max-len="40" required></textarea>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">内容 <span class="asterisk">*</span></label>
						<div class="col-sm-10">
							<!-- 加载编辑器的容器 -->
							<script id="ueditor_add_news_content" type="text/plain"></script>
						</div>
					</div>
					
        </div><!-- panel-body -->
        
        <div class="fixed-footer">
		      <button id="btn_submit_add_news" class="btn btn-primary">确 定</button>&nbsp;
					<button id="btn_close_add_news" class="btn btn-default">关 闭</button>
		    </div><!-- headerbar -->
        
      </div>
    </div>
    
  </div><!-- mainpanel -->
  
</section>

	<script src="<%=path%>/res/bracket/js/jquery-1.11.1.min.js"></script>
	<script src="<%=path%>/res/bracket/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="<%=path%>/res/bracket/js/bootstrap.min.js"></script>
	<script src="<%=path%>/res/bracket/js/modernizr.min.js"></script>
	<script src="<%=path%>/res/bracket/js/jquery.sparkline.min.js"></script>
	<script src="<%=path%>/res/bracket/js/toggles.min.js"></script>
	<script src="<%=path%>/res/bracket/js/retina.min.js"></script>
	<script src="<%=path%>/res/bracket/js/jquery.cookies.js"></script>
	
	<script src="<%=path%>/res/bracket/js/jquery-ui-1.10.3.min.js"></script>
	<script src="<%=path%>/res/bracket/js/select2.min.js"></script>
	<script src="<%=path%>/res/bracket/js/jquery.validate.min.js"></script>
	
	<script src="<%=path%>/res/bracket/js/custom.js"></script>
	
	<!-- 配置文件 -->
  <script type="text/javascript" src="<%=path%>/res/ueditor/ueditor.config.js"></script>
  <!-- 编辑器源码文件 -->
  <script type="text/javascript" src="<%=path%>/res/ueditor/ueditor.all.js?v=1"></script>
  
  <script src="<%=path%>/res/jquery.scrollTo.js"></script>

	<!-- 七牛相关js -->
	<script src="<%=path%>/res/plupload/plupload.full.min.js"></script>
	<script src="<%=path%>/res/plupload/i18n/zh_CN.js"></script>
	<script src="<%=path%>/res/qiniu/qiniu.js"></script>
	<script src="<%=path%>/res/custom/file-progress-ui.js"></script>
	<script src="<%=path%>/res/custom/file-uploader.js?ver=201804251150"></script>
	<!--ueditor添加自定义的标题目录按钮-->
  <script src="<%=path%>/res/custom/ueditor-customize-img-button.js"></script>
  <script type="text/javascript" src="<%=path%>/res/custom/ajax-paging-query.js"></script>
  <script src="<%=path%>/res/custom/wz-media.js"></script>
	
	<script src="<%=path%>/res/input-len-counter.js"></script>
	
	<script type="text/javascript">
		//新闻标题和新闻概要最大字符数
		var news_title_max_len = 35;
		var news_summary_max_len = 40;
	
		$().ready(function() {
			
			//初始化ueditor
			var ue = UE.getEditor('ueditor_add_news_content', {
				//这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
	      toolbars:[['source', '|', 'undo', 'redo', '|', 'bold', 'italic', 'underline', 'superscript', 'subscript', 'forecolor', 'formatmatch', 'fontsize', '|',
	                 'insertorderedlist', 'insertunorderedlist', 'justifyleft', 'justifycenter', 'justifyright', '|',
	                 'link', 'horizontal', 'spechars', 'inserttable', '|', 'preview', 'searchreplace']],
	      zIndex : 1030
			});
			//为编辑器添加获取焦点事件
			ue.addListener("focus", function (type, event) {
				$("#ueditor_add_news_content").next('label').eq(0).remove();
	    });
			
			//初始化图片上传
			imgUploader("btn_add_news_file_upload", "progress_bar", 5, 
				function(data) {
					//加入到素材库中
					try {
						var title = data.fname;
						if (title === null || title === '') {
							title = data.key;
						}
						wzMedia.addImage(title, data.url, data.fsize, 0);
					} catch (e) {
						console.log(e);
					}
					
					$("#progress_bar").next('label').eq(0).remove();
					$("#txt_add_news_pic_url").attr("value", data.url);
					$("#img_add_news_container").show();
					$("#img_add_news").attr("src", data.url);
				}, 
				function(err, errTip) {
					if (err.code == -600) {
						$("#progress_bar").next('label').eq(0).remove();
						$("#progress_bar").after('<label for="name" class="error">新闻封面图大小不能超过5M</label>');
					}
				}
			);
			
			$("#txt_add_news_pic_url").change(function(){
				$("#progress_bar").next('label').eq(0).remove();
			});
	    $("#txt_add_news_title").focus(function(){
				$("#div_add_news_title").next('label').eq(0).remove();
			});
			$("#txt_add_news_summary").focus(function(){
				$("#div_add_news_summary > label:last").remove();
			});
			
			//新增新闻
      $("#btn_submit_add_news").click(function(e) {
      	var title = $("#txt_add_news_title").val();
      	var summary = $("#txt_add_news_summary").val();
      	var content = UE.getEditor('ueditor_add_news_content').getContent();
      	var picUrl = $("#txt_add_news_pic_url").val();
    		var isShowPic = $("#chk_add_news_pic_is_show").is(':checked');
      	
      	$("#progress_bar").next('label').eq(0).remove();
      	$("#div_add_news_title").next('label').eq(0).remove();
      	$("#div_add_news_summary > label:last").remove();
      	$("#ueditor_add_news_content").next('label').eq(0).remove();
      	if (picUrl == null || $.trim(picUrl) == '') {
					$("#progress_bar").after('<label for="name" class="error">请选择封面图片</label>');
					$("body").scrollTo(".panel-body", 500);
					return ;
      	}
      	if (title == null || $.trim(title) == '' || $.trim(title).length > news_title_max_len) {
      		$("#div_add_news_title").after('<label for="name" class="error">新闻标题不能为空且长度不能超过' + news_title_max_len + '字</label>');
      		$("body").scrollTo(".panel-body", 500);
      		return ;
      	}
      	if (summary == null || $.trim(summary) == '' || $.trim(summary).length > news_summary_max_len) {
      		$("#div_add_news_summary").append('<label for="name" class="error">摘要不能为空且长度不能超过' + news_summary_max_len + '字</label>');
      		$("body").scrollTo(".panel-body", 500);
      		return ;
      	}
      	if (content == null || $.trim(content) == '') {
      		$("#ueditor_add_news_content").after('<label for="name" class="error">请输入新闻内容</label>');
      		return ;
      	}
      	
      	var data = {pic_url : picUrl, title : title, summary : summary, content : content, is_show_pic : isShowPic};
      	$.ajax({url: "<%=path%>/qr/mfr_admin/news/add", type: "POST", dataType:"json", data: data,
					success: function(d) {
						window.location.href="<%=path%>/qr/mfr_admin/news";
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						var result = jQuery.parseJSON(XMLHttpRequest.responseText);
						if (result) {
							if (result.code == '1') {
								$("#div_add_product_cases_title").addClass("has-error");
							} else if (result.code == '2') {
								console.log(result.msg);
							}
						}
					}
				});
				    	
      });
			
			$("#btn_close_add_news").on("click", function(e) {
				window.opener = null; 
				window.open('', '_self'); 
				window.close() 
			});
			
		});
		
		function chooseImgFromMedias() {
			showMediaImageChooser(1, function(data) {
     		if (data) {
     			$("#progress_bar").next('label').eq(0).remove();
     			$("#txt_add_news_pic_url").attr("value", data[0].url);
					$("#img_add_news").attr("src", data[0].url);
					$("#img_add_news_container").show();
     		}
    	});
		}
	</script>

	<!-- 图片选择器 -->
	<jsp:include page="/mfr_admin/media_image_chooser.jsp" flush="true" />
</body>
</html>
