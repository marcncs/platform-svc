
//图片上传
function getDomain() {
var str = window.location.href;
var first = str.indexOf('/')+1;
var second = str.indexOf('/',first)+1;
var third = str.indexOf('/',second)+1;
var forth = str.indexOf('/',third)+1;
return str.slice(0,forth);
}

function getContextPath() {
	var str = window.location.href;
	var first = str.indexOf('/')+1;
	var second = str.indexOf('/',first)+1;
	var third = str.indexOf('/',second)+1;
	var forth = str.indexOf('/',third)+1;
	return str.slice(third-1,forth);
}
//button:浏览图片的按钮id
//progressContainer：进度条容器的id
//maxSize：允许上传的图片最大大小，单位M
function imgUploader(button, progressContainer, maxSize, successCallback, errorCallback, multiSelection, completeCallback) {
	//七牛js sdk对象
	var qiniuUploader = new QiniuJsSDK();
	var uploader = qiniuUploader.uploader({
	    runtimes: 'html5,html4',
	    browse_button: button,
	    max_file_size: (maxSize || 5) + 'mb',
	    filters: {
	    	  mime_types : [ //只允许上传图片
	    	    { title : "Image files", extensions : "jpg,jpeg,gif,png" }
	    	  ]
	    	},
	    max_retries: 3,                   //上传失败最大重试次数
	    dragdrop: true,
	    save_key: true,
	    chunk_size: '4mb',
	    uptoken_url: getContextPath()+'qr/api/qiniu/token',
	    domain: getDomain()+'qr/img/',
	    auto_start: true,
	    multi_selection: multiSelection || false,
	    init: {
	        'FilesAdded': function(up, files) {
	            plupload.each(files, function(file) {
	                var progress = new FileProgress(file, progressContainer);
	                progress.setStatus("等待...");
	                progress.bindUploadCancel(up);
	            });
	        },
	        'BeforeUpload': function(up, file) {
	            var progress = new FileProgress(file, progressContainer);
	            //显示进度条
	            jQuery("#" + progressContainer).show();
	        },
	        'UploadProgress': function(up, file) {
	            var progress = new FileProgress(file, progressContainer);
	            var chunk_size = plupload.parseSize(up.getOption('chunk_size'));
	            progress.setProgress(file.percent + "%", file.speed, chunk_size);
	        },
	        'UploadComplete': function() {
//	        	console.log("队列文件处理完毕后,处理相关的事情");
	        	//隐藏进度条
	        	jQuery("#" + progressContainer).hide();
	        	completeCallback && completeCallback();
	        },
	        'FileUploaded': function(up, file, info) {
	        	var progress = new FileProgress(file, progressContainer);
	            progress.setComplete(up, info, true);
	            
	        	var res = jQuery.parseJSON(info);
	        	url = up.getOption('domain') + encodeURI(res.key);
	        	res.url = url;
	        	
	        	successCallback && successCallback(res);
	        },
	        'Error': function(up, err, errTip) {
	        	
	            var progress = new FileProgress(err.file, progressContainer);
	            progress.setError();
	            
	            errorCallback && errorCallback(err, errTip);
	        }
//	        ,'Key': function(up, file) {
//	        	var key = new Date().format("yyyy/MM/dd") + "/";
//	        	// do something with key
//	        	return key
//	        }
	    }
	});
	
	return uploader;
}


//视频上传
function videoUploader(button, progressContainer, maxSize, successCallback, errorCallback) {
	//七牛js sdk对象
	var qiniuUploader = new QiniuJsSDK();
	var uploader = qiniuUploader.uploader({
	    runtimes: 'html5,html4',
	    browse_button: button,
	    max_file_size: (maxSize || 20) + 'mb',
	    filters: {
	    	  mime_types : [ //只允许上传图片
	    	    { title : "Video files", extensions : "mp4,wmv" }
	    	  ]
	    	},
	    max_retries: 3,                   //上传失败最大重试次数
	    dragdrop: true,
	    save_key: true,
	    chunk_size: '4mb',
	    uptoken_url: '/api/qiniu/token',
	    //domain: 'http://cdn.n369.com/',
	    domain: getContextPath()+'qr/img/',
	    auto_start: true,
	    multi_selection: false,
	    init: {
	        'FilesAdded': function(up, files) {
	            plupload.each(files, function(file) {
	                var progress = new FileProgress(file, progressContainer, button);
	                progress.setStatus("等待...");
	                progress.bindUploadCancel(up);
	            });
	            jQuery("#"+button).hide();
	        },
	        'BeforeUpload': function(up, file) {
	            var progress = new FileProgress(file, progressContainer, button);
	        },
	        'UploadProgress': function(up, file) {
	            var progress = new FileProgress(file, progressContainer, button);
	            var chunk_size = plupload.parseSize(this.getOption('chunk_size'));
	            progress.setProgress(file.percent + "%", file.speed, chunk_size);
	        },
	        'UploadComplete': function() {
//	        	console.log("队列文件处理完毕后,处理相关的事情");
	        },
	        'FileUploaded': function(up, file, info) {
	        	var progress = new FileProgress(file, progressContainer, button);
	        	var chunk_size = plupload.parseSize(up.getOption('chunk_size'));
	            progress.setProgress(file.percent + "%", file.speed, chunk_size);
	            progress.setComplete(up, info, false);
	            
	        	var res = jQuery.parseJSON(info);
	        	url = up.getOption('domain') + encodeURI(res.key);
	        	res.url = url;
	        	
	        	successCallback && successCallback(res);
	        },
	        'Error': function(up, err, errTip) {
	        	
	        	jQuery("#"+button).show();
	        	
	            var progress = new FileProgress(err.file, progressContainer, button);
	            progress.setError();
	            
	            errorCallback && errorCallback(err, errTip);
	        }
	            // ,
	            // 'Key': function(up, file) {
	            //     var key = "";
	            //     // do something with key
	            //     return key
	            // }
	    }
	});
}