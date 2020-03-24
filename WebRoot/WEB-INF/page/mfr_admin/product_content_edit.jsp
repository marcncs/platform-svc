<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = "/".equalsIgnoreCase(request.getContextPath())?"":request.getContextPath();
	String productId = request.getParameter("product_id");
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>图文详情</title>

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
    
    	<!-- <div class="header-left">
        
        <div class="logopanel">
            <h1><span>[</span> 云单页 <span>]</span></h1>
        </div>logopanel
        
      	</div>header-left -->
      
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
          <h4 class="panel-title">编辑图文讲解内容</h4>
        </div>
        <!-- panel-body -->
        <div class="panel-body form-horizontal">
        	<div class="form-group">
						<label class="col-sm-2 control-label">产品海报<span class="asterisk">*</span></label>
						<div class="col-sm-10">
							<p>为节省用户流量，建议上传小于5M的图片，建议宽度960-1080像素之间</p>
              <button class="btn btn-sm btn-info" id="btn_product_poster_pic_upload">本地上传</button>
              <button class="btn btn-sm btn-info" onclick="chooseImgFromMedias()">从图片库选择</button>
              <input id="txt_product_poster_pic_url" type="hidden"/>
              <div id="img_product_poster_container" style="padding-top: 15px; display:none;">
              	<img id="img_product_poster" width="200px">
              </div>
              <div id="progress_bar" style="width:100%;"></div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">内容 <span class="asterisk">*</span></label>
						<div class="col-sm-10">
							<!-- 加载编辑器的容器 -->
							<script id="ueditor_product_content" type="text/plain"></script>
						</div>
					</div>
					
        </div><!-- panel-body -->
        
        <div class="fixed-footer">
				 	<button id="btn_submit" class="btn btn-primary">确 定</button>&nbsp;
					<button id="btn_close" class="btn btn-default">关 闭</button>
			  </div><!-- panel-footer -->
        
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
	
	<!-- ueditor配置文件 -->
  <script src="<%=path%>/res/ueditor/ueditor.config.js"></script>
  <!-- ueditor编辑器源码文件 -->
  <script src="<%=path%>/res/ueditor/ueditor.all.js"></script>
  <!--ueditor添加自定义的标题目录按钮-->
  <script src="<%=path%>/res/custom/ueditor-customize-img-button.js"></script>
  
  <script src="<%=path%>/res/jquery.scrollTo.js"></script>

	<!-- 七牛相关js -->
	<script src="<%=path%>/res/plupload/plupload.full.min.js"></script>
	<script src="<%=path%>/res/plupload/i18n/zh_CN.js"></script>
	<script src="<%=path%>/res/qiniu/qiniu.js"></script>
	<script src="<%=path%>/res/custom/file-progress-ui.js"></script>
	<script src="<%=path%>/res/custom/file-uploader.js?ver=201804251150"></script>
  <script src="<%=path%>/res/custom/ajax-paging-query.js"></script>
  <script src="<%=path%>/res/custom/wz-media.js"></script>
  <script src="<%=path%>/res/custom/api-product.js"></script>
	
	<script src="<%=path%>/res/input-len-counter.js"></script>
	
	<script type="text/javascript">
	
		$().ready(function() {
			
			//初始化ueditor
			var ue = UE.getEditor('ueditor_product_content', {
				//这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
	      toolbars:[['source', '|', 'undo', 'redo', '|', 'bold', 'italic', 'underline', 'superscript', 'subscript', 'forecolor', 'formatmatch', 'fontsize', '|',
	                 'insertorderedlist', 'insertunorderedlist', 'justifyleft', 'justifycenter', 'justifyright', '|',
	                 'link', 'horizontal', 'spechars', 'inserttable', '|', 'preview', 'searchreplace']],
	      zIndex : 1030
			});
			//为编辑器添加获取焦点事件
			ue.addListener("focus", function (type, event) {
				$("#ueditor_product_content").next('label').eq(0).remove();
	    });
			
			//初始化图片上传
			imgUploader("btn_product_poster_pic_upload", "progress_bar", 5, 
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
					$("#txt_product_poster_pic_url").attr("value", data.url);
					$("#img_product_poster_container").show();
					$("#img_product_poster").attr("src", data.url);
				}, 
				function(err, errTip) {
					if (err.code == -600) {
						$("#progress_bar").next('label').eq(0).remove();
						$("#progress_bar").after('<label for="name" class="error">产品海报大小不能超过5M</label>');
					}
				}
			);
			
			//初始化产品图文内容
			ue.ready(function() {
	    	//获取产品信息
				$.ajax({url: "<%=path%>/qr/api/product/<%=productId %>", type: "GET", dataType: "json", async: false, 
					success: function(result) {
						product = result.data;
						if (product) {
							//$("#form_update_product_content").attr("action", "/mfr_admin/product/update/content/" + product.id);
							if (product.poster_url) {
								$("#img_product_poster").attr("src", product.poster_url);
								$("#txt_product_poster_pic_url").attr("value", product.poster_url);
								$("#img_product_poster_container").show();
							}
							ue.setContent(product.content);
						}
					}
				});
	    });
			
			$("#txt_product_poster_pic_url").change(function(){
				$("#progress_bar").next('label').eq(0).remove();
			});
		
			//编辑产品图文内容
      $("#btn_submit").click(function(e) {
      	var content = ue.getContent();
      	var picUrl = $("#txt_product_poster_pic_url").val();
      	console.log(content);
      	console.log(picUrl);
      	$("#progress_bar").next('label').eq(0).remove();
      	$("#ueditor_product_content").next('label').eq(0).remove();
      	if (picUrl == null || $.trim(picUrl) == '') {
					$("#progress_bar").after('<label for="name" class="error">请选择产品海报</label>');
					$("body").scrollTo(".panel-body", 500);
					return ;
      	}
      	if (content == null || $.trim(content) == '') {
      		$("#ueditor_add_news_content").after('<label for="name" class="error">请输入图文内容</label>');
      		return ;
      	}
      	
      	try {
      		editProductContent('<%=productId %>', picUrl, content);
      		window.location.href="<%=path%>/qr/mfr_admin/product/<%=productId %>";
      	} catch (e) {
      		console.log(e);
      	}
				    	
      });
			
			$("#btn_close").on("click", function(e) {
				window.opener = null; 
				window.open('', '_self'); 
				window.close() 
			});
			
		});
		
		function chooseImgFromMedias() {
			showMediaImageChooser(1, function(data) {
     		if (data) {
     			$("#progress_bar").next('label').eq(0).remove();
					$("#txt_product_poster_pic_url").attr("value", data[0].url);
					$("#img_product_poster_container").show();
					$("#img_product_poster").attr("src", data[0].url);
     		}
    	});
		}
	</script>

	<!-- 图片选择器 -->
	<jsp:include page="/mfr_admin/media_image_chooser.jsp" flush="true" />
</body>
</html>
