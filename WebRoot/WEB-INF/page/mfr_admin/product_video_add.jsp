<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
	String productId = request.getParameter("product_id");
%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>云单页厂家管理平台</title>

  <link href="/res/bracket/css/style.default.css" rel="stylesheet">
	<link href="/res/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet" />
	<link href="/res/bracket/css/jquery.tagsinput.css" rel="stylesheet" />
	
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
        
        <div class="logopanel">
            <h1><span>[</span> 云单页 <span>]</span></h1>
        </div><!-- logopanel -->
        
      </div><!-- header-left -->
      
      <a class="menutoggle"><i class="fa fa-bars"></i></a>

			<!-- header-right -->
			<jsp:include page="/mfr_admin/include_header.jsp" flush="true" />
    </div><!-- headerbar -->
        
    <!-- 
    <div class="pageheader">
      <h2><i class="fa fa-home"></i> 素材管理 <span>添加视频</span></h2>
      <div class="breadcrumb-wrapper">
      </div>
    </div>
     -->
     
    <div class="contentpanel">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h4 class="panel-title">新增视频</h4>
        </div>
        <!-- panel-body -->
        <div class="panel-body form-horizontal">
        	<div class="form-group">
						<label class="col-sm-2 control-label">视 频 <span class="asterisk">*</span></label>
						<div class="col-sm-10">
							<p>为节省用户流量，建议上传小于30M的视频。</p>
              <button class="btn btn-sm btn-info" onclick="chooseVideoFromMedias()">选择视频</button>
              <input id="txt_product_video_img_url" type="hidden"/>
              <input id="txt_product_video_fsize" type="hidden"/>
              <div id="product_video_container" style="padding-top: 15px; display:none;">
              	<video id="v_product_video" controls width="560px"><source type="video"></video>
              </div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">视频标题 <span class="asterisk">*</span></label>
						<div class="col-sm-5">
							<div id="div_add_product_video_title" class="input-group">
                 <input id="txt_product_video_title" type="text" name="title" class="form-control" data-max-len="20" required />
               </div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">拍摄时间</label>
						<div class="col-sm-3">
							<input id="txt_product_video_shoot_time" type="text" name="shoot_time" class="form-control date" required />
						</div>
					</div>
					
        </div><!-- panel-body -->
        
        <div class="panel-footer">
				 <div class="row">
					<div class="col-sm-6 col-sm-offset-3">
					  <button id="btn_submit" class="btn btn-primary">确 定</button>&nbsp;
					  <button id="btn_close" class="btn btn-default">关 闭</button>
					</div>
				 </div>
			  </div><!-- panel-footer -->
        
      </div>
    </div>
    
  </div><!-- mainpanel -->
  
</section>

	<script src="/res/bracket/js/jquery-1.11.1.min.js"></script>
	<script src="/res/bracket/js/jquery-migrate-1.2.1.min.js"></script>
	<script src="/res/bracket/js/bootstrap.min.js"></script>
	<script src="/res/bracket/js/modernizr.min.js"></script>
	<script src="/res/bracket/js/jquery.sparkline.min.js"></script>
	<script src="/res/bracket/js/toggles.min.js"></script>
	<script src="/res/bracket/js/retina.min.js"></script>
	<script src="/res/bracket/js/jquery.cookies.js"></script>
	
	<script src="/res/bracket/js/jquery-ui-1.10.3.min.js"></script>
	<script src="/res/bracket/js/select2.min.js"></script>
	<script src="/res/bracket/js/jquery.validate.min.js"></script>
	
	<script src="/res/bracket/js/custom.js"></script>
	
  <script src="/res/jquery.scrollTo.js"></script>
  
  <script src='/res/datetime/bootstrap-datetimepicker.min.js'></script>
  <script src='/res/datetime/locales/bootstrap-datetimepicker.zh-CN.js'></script>

	<!-- 七牛相关js -->
	<script src="/res/plupload/plupload.full.min.js"></script>
	<script src="/res/plupload/i18n/zh_CN.js"></script>
	<script src="/res/qiniu/qiniu.js"></script>
	<script src="/res/custom/file-progress-ui.js"></script>
	<script src="/res/custom/file-uploader.js?ver=201804251150"></script>
  <script src="/res/custom/ajax-paging-query.js"></script>
  <script src="/res/custom/wz-media.js"></script>
  <script src="/res/custom/api-product.js"></script>
	
	<script src="/res/input-len-counter.js"></script>
	<script src="/res/datetime.js"></script>
	
	<script type="text/javascript">
	
		var title_max_len = 20;
	
		$().ready(function() {
			
			// Date Picker
		  $('#txt_product_video_shoot_time').datetimepicker({
           format: 'yyyy-mm-dd',
           language: 'zh-CN',
           weekStart: 0,
           todayBtn:  1,
           autoclose: 1,
           todayHighlight: 1,
           startView: 2,
           minView: 2
       });
			
			$("#txt_product_poster_pic_url").change(function(){
				$("#progress_bar").next('label').eq(0).remove();
			});
		
			//新增产品视频
      $("#btn_submit").click(function(e) {
				var title = $("#txt_product_video_title").val();
				var videoUrl = $("#v_product_video").attr("src");
				var videoImgUrl = $("#txt_product_video_img_url").val();
      	var fsize = $("#txt_product_video_fsize").val();
      	var shootTime = $("#txt_product_video_shoot_time").val();
      	
      	$("#product_video_container").next('label').eq(0).remove();
      	$("#txt_product_video_title").next('label').eq(0).remove();
      	if (videoUrl == null || $.trim(videoUrl) == '') {
					$("#product_video_container").after('<label for="name" class="error">请选择视频</label>');
					return ;
      	}
      	if (!title || $.trim(title).length > title_max_len) {
					$("#txt_product_video_title").after('<label for="name" class="error">标题不能为空且长度不能超过' + title_max_len + '字</label>');
					return ;
				}
      	
      	try {
      		addProductVideo('<%=productId %>', title, videoUrl, videoImgUrl, fsize, shootTime);
      		window.location.href="/mfr_admin/product/<%=productId %>?tab_name=videos";
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
		
		function chooseVideoFromMedias() {
			showMediaVideoChooser(function(data) {
     		if (data) {
     			$("#progress_bar").next('label').eq(0).remove();
					$("#v_product_video").attr("src", data.url);
					$("#txt_product_video_img_url").attr("value", data.img_url);
					$("#txt_product_video_fsize").attr("value", data.fsize);
					$("#product_video_container").show();
     		}
    	});
		}
	</script>

	<!-- 图片选择器 -->
	<jsp:include page="/mfr_admin/media_video_chooser.jsp" flush="true" />
</body>
</html>
