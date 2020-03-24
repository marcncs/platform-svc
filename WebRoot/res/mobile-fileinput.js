function init() {
	var u = new UploadPic();
	u.init({
		maxSize: 5 * 1024 * 1024,
		input : document.querySelector('.input'),
		callback : function(base64) {
			jQuery.ajax({
				url: "/api/images/encoding",
				type: "POST",
				dataType:"json",
				async: false,
				data: {encoding_img:base64,type:this.fileType},
				success: function(d) {
					jQuery(".upload_image").attr("src", d.data.urlFilePath);
					jQuery("#txt_image_url").attr("value", d.data.urlFilePath);
					jQuery("#btn_delete_image").show();
				},
				error: function(result) {
					var responseText = result.responseText;
					var responseObj = jQuery.parseJSON(responseText);
					if (responseObj.code == "WZ130001") {
						window.location.href="/elabel/signin.jsp";
					} else {
						jQuery(".upload_image").attr("src", "/images/add_img.png");
						jQuery("#txt_image_url").attr("value", "");
						jQuery("#btn_delete_image").hide();
					}
				}
			})
		},
		loading : function() {
			
		}
	});
}

function UploadPic() {
	this.sw = 0;
	this.sh = 0;
	this.tw = 0;
	this.th = 0;
	this.scale = 0;
	this.maxWidth = 0;
//	this.maxHeight = 0;
	this.maxSize = 0;
	this.fileSize = 0;
	this.fileDate = null;
	this.fileType = '';
	this.fileName = '';
	this.input = null;
	this.canvas = null;
	this.mime = {};
	this.type = '';
	this.callback = function() {
	};
	this.loading = function() {
	};
}

UploadPic.prototype.init = function(options) {
	this.maxWidth = options.maxWidth;
//	this.maxHeight = options.maxHeight || 500;
	this.maxSize = options.maxSize || 3 * 1024 * 1024;
	this.input = options.input;
	this.mime = {
		'png' : 'image/png',
		'jpg' : 'image/jpeg',
		'jpeg' : 'image/jpeg',
		'gif' : 'image/gif'
	};
	this.callback = options.callback || function() {
	};
	this.loading = options.loading || function() {
	};

	this._addEvent();
};

/**
 * @description 绑定事件
 * @param {Object} elm 元素
 * @param {Function} fn 绑定函数
 */
UploadPic.prototype._addEvent = function() {
	var _this = this;

	function tmpSelectFile(ev) {
		_this._handelSelectFile(ev);
	}

	this.input.addEventListener('change', tmpSelectFile, false);
};

/**
 * @description 绑定事件
 * @param {Object} elm 元素
 * @param {Function} fn 绑定函数
 */
UploadPic.prototype._handelSelectFile = function(ev) {
	//删除提示
	jQuery(ev.target).next('label').eq(0).remove();
	//清空原来的图片
	jQuery(".upload_image").attr("src", "/elabel/images/loading.gif");
	jQuery("#txt_image_url").attr("value", "");
	jQuery("#btn_delete_image").hide();
	
	var file = ev.target.files[0];
	
	if (!file || file.length == 0) {
		jQuery(".upload_image").attr("src", "/images/add_img.png");
		return ;
	}
	
	this.type = file.type

	// 如果没有文件类型，则通过后缀名判断（解决微信及360浏览器无法获取图片类型问题）
	if (!this.type) {
		this.type = this.mime[file.name.match(/\.([^\.]+)$/i)[1]];
	}

	if (!/image.(png|jpg|jpeg|bmp)/.test(this.type)) {
		jQuery(ev.target).after('<label class="error">选择的文件不是图片</label>');
		return;
	}
	
	if (file.size > this.maxSize) {
		jQuery(ev.target).after('<label class="error">图片大小不能超过' + this.maxSize / 1024 / 1024 + 'M，请重新选择</label>');
		return;
	}

	this.fileName = file.name;
	this.fileSize = file.size;
	this.fileType = this.type;
	this.fileDate = file.lastModifiedDate;

	this._readImage(file);
};

/**
 * @description 读取图片文件
 * @param {Object} image 图片文件
 */
UploadPic.prototype._readImage = function(file) {
	var _this = this;

	function tmpCreateImage(uri) {
		_this._createImage(uri);
	}

	this.loading();

	this._getURI(file, tmpCreateImage);
};

/**
 * @description 通过文件获得URI
 * @param {Object} file 文件
 * @param {Function} callback 回调函数，返回文件对应URI 
 * return {Bool} 返回false
 */
UploadPic.prototype._getURI = function(file, callback) {
	var reader = new FileReader();
	var _this = this;

	function tmpLoad() {
		// 头不带图片格式，需填写格式
		var re = /^data:base64,/;
		var ret = this.result + '';

		if (re.test(ret))
			ret = ret.replace(re, 'data:' + _this.mime[_this.fileType]
					+ ';base64,');

		callback && callback(ret);
	}

	reader.onload = tmpLoad;

	reader.readAsDataURL(file);

	return false;
};

/**
 * @description 创建图片
 * @param {Object} image 图片文件
 */
UploadPic.prototype._createImage = function(uri) {
	var img = new Image();
	var _this = this;

	function tmpLoad() {
		_this._drawImage(this);
	}

	img.onload = tmpLoad;

	img.src = uri;
};

/**
 * @description 创建Canvas将图片画至其中，并获得压缩后的文件
 * @param {Object} img 图片文件
 * @param {Number}  width 图片最大宽度
 * @param {Number} height 图片最大高度
 * @param {Function} callback 回调函数，参数为图片base64编码 
 * return {Object} 返回压缩后的图片
 */
UploadPic.prototype._drawImage = function(img, callback) {
	this.sw = img.width;
	this.sh = img.height;
	this.tw = img.width;
	this.th = img.height;

	this.scale = (this.tw / this.th).toFixed(2);
	
	if (!this.maxWidth) {
		var pixel = this.sw * this.sh;
		if (pixel <= 3000000) {
			this.maxWidth = 900;
		} else if (pixel > 3000000 && pixel <= 10000000 ) {
			this.maxWidth = Math.round(this.sw * 0.5);
		} else {
			this.maxWidth = Math.round(this.sw * 0.4);
		}
	}
	
	if (this.sw > this.maxWidth) {
		this.sw = this.maxWidth;
		this.sh = Math.round(this.sw / this.scale);
	}

	this.canvas = document.createElement('canvas');
	var ctx = this.canvas.getContext('2d');

	this.canvas.width = this.sw;
	this.canvas.height = this.sh;

	ctx.drawImage(img, 0, 0, img.width, img.height, 0, 0, this.sw, this.sh);
	
//	var userAgent = navigator.userAgent;
//	
//	// 兼容 Android
//    if (/Android/i.test(userAgent)) {
//        try {
//            this.callback(ctx.getImageData(0, 0, this.canvas.width, this.canvas.height), 1);
//        } catch (_error) {
//            alert('未引用mobile补丁，无法生成图片。');
//        }
//    }
//
//    // 其他情况&IOS
//    else {
//        this.callback(this.canvas.toDataURL(this.type), 1);
//    }
    
    this.callback(this.canvas.toDataURL(this.type), 1);

	ctx.clearRect(0, 0, this.tw, this.th);
	this.canvas.width = 0;
	this.canvas.height = 0;
	this.canvas = null;
};