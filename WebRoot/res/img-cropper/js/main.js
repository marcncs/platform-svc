$(function () {

  'use strict';

  var console = window.console || { log: function () {} },
      $alert = $('.docs-alert'),
      $message = $alert.find('.message'),
      showMessage = function (message, type) {
        $message.text(message);

        if (type) {
          $message.addClass(type);
        }

        $alert.fadeIn();

        setTimeout(function () {
          $alert.fadeOut();
        }, 3000);
      };

  (function () {
    var $image = $('#img2crop'),
        $dataX = $('#dataX'),
        $dataY = $('#dataY'),
        $dataHeight = $('#dataHeight'),
        $dataWidth = $('#dataWidth'),
        $dataRotate = $('#dataRotate'),
        aspectRatioX = $("#cropper_aspect_ratio").attr("data-aspect-ratio-x"),
  	  	aspectRatioY = $("#cropper_aspect_ratio").attr("data-aspect-ratio-y"),
        options = {
//         strict: false,
          // responsive: false,
          // checkImageOrigin: false,
           
           //viewMode Default: 0
           //0: the crop box is just within the container;
           //1: the crop box should be within the canvas
           //2: the canvas should not be within the container
           //3: the container should be within the canvas
           viewMode: 0, 

          // modal: false,
          // guides: false,
//          highlight: false,
//          background: true,

          // autoCrop: false,
          // autoCropArea: 0.5,
          // dragCrop: false,
          // movable: false,
          // resizable: false,
          // rotatable: false,
          // zoomable: false,
          // touchDragZoom: false,
          // mouseWheelZoom: false,

//           minCanvasWidth: 200,
//           minCanvasHeight: 200,
           minCropBoxWidth: 100,
           minCropBoxHeight: 100,
          // minContainerWidth: 320,
          // minContainerHeight: 180,

          // build: null,
          // built: null,
          // dragstart: null,
          // dragmove: null,
          // dragend: null,
          // zoomin: null,
          // zoomout: null,

          aspectRatio: aspectRatioX / aspectRatioY,
          preview: '.img-preview',
          crop: function (data) {
//            $dataX.val(Math.round(data.x));
//            $dataY.val(Math.round(data.y));
//            $dataHeight.val(Math.round(data.height));
//            $dataWidth.val(Math.round(data.width));
//            $dataRotate.val(Math.round(data.rotate));
          }
        };

    $image.on({
      'build.cropper': function (e) {
        console.log(e.type);
      },
      'built.cropper': function (e) {
        console.log(e.type);
        //选择图片后进行方向键的绑定
        $(document.body).on('keydown', function (e) {
          switch (e.which) {
            case 37:
              e.preventDefault();
              $image.cropper('move', -1, 0);
              break;
    
            case 38:
              e.preventDefault();
              $image.cropper('move', 0, -1);
              break;
    
            case 39:
              e.preventDefault();
              $image.cropper('move', 1, 0);
              break;
    
            case 40:
              e.preventDefault();
              $image.cropper('move', 0, 1);
              break;
          }
        });
      },
      'dragstart.cropper': function (e) {
//        console.log(e.type, e.dragType);
      },
      'dragmove.cropper': function (e) {
//        console.log(e.type, e.dragType);
      },
      'dragend.cropper': function (e) {
//        console.log(e.type, e.dragType);
      },
      'zoomin.cropper': function (e) {
//        console.log(e.type);
      },
      'zoomout.cropper': function (e) {
//        console.log(e.type);
      }
    }).cropper(options);


    // Methods
    $(document.body).on('click', '[data-method]', function () {
      var data = $(this).data(),
          $target,
          result;

      if (data.method) {
        data = $.extend({}, data); // Clone a new one

        if (typeof data.target !== 'undefined') {
          $target = $(data.target);
          if (typeof data.option === 'undefined') {
            try {
              data.option = JSON.parse($target.val());
            } catch (e) {
              console.log(e.message);
            }
          }
        }

        result = $image.cropper(data.method, data.option);
        
        if (data.method === 'getCroppedCanvas') {
        	alert('why u click me');
        	/**
//          $('#getCroppedCanvasModal').modal().find('.modal-body').html(result);
			var canvas_data = result.toDataURL("image/jpeg");
			// dataURL 的格式为 “data:image/png;base64,****”,逗号之前都是一些说明性的文字，我们只需要逗号之后的就行了
			var canvas_data_base64 = canvas_data.split(',')[1];
			canvas_data = window.atob(canvas_data_base64);
			var ia = new Uint8Array(canvas_data.length);
			for (var i = 0; i < canvas_data.length; i++) {
			    ia[i] = canvas_data.charCodeAt(i);
			};
			// canvas.toDataURL 返回的默认格式就是 image/bmp
			var blob = new Blob([ia], {type:"image/jpg"});

          	var fd = new FormData();
			fd.append("crop_file", blob);
			
			$.ajax({
				url: "http://127.0.0.1/api/images",
				type: "POST",
				dataType:"json",
				processData: false,
				contentType: false,
				data: fd,
				success: function(d) {
					console.log(d.data);
					console.log(d.data.relativeFilePath);
					console.log(d.data.urlFilePath);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert("server response error");
					var responseText = jQuery.parseJSON(XMLHttpRequest.responseText);
					console.log(responseText.msg);
				}
			});
			*/
        }

        if ($.isPlainObject(result) && $target) {
          try {
            $target.val(JSON.stringify(result));
          } catch (e) {
            console.log(e.message);
          }
        }

      }
    });


    // Import image
    var $inputImage = $('#inputImage'),
        URL = window.URL || window.webkitURL,
        blobURL;

    if (URL) {
      $inputImage.change(function () {
        var files = this.files,
            file;

        if (files && files.length) {
          file = files[0];

          if (/^image\/\w+$/.test(file.type)) {
            blobURL = URL.createObjectURL(file);
            $image.one('built.cropper', function () {
              URL.revokeObjectURL(blobURL); // Revoke when load complete
            }).cropper('reset', true).cropper('replace', blobURL);
            $inputImage.val('');
          } else {
            showMessage('Please choose an image file.');
          }
        }
      });
    } else {
      $inputImage.parent().remove();
    }


    // Options
    $('.docs-options :checkbox').on('change', function () {
      var $this = $(this);

      options[$this.val()] = $this.prop('checked');
      $image.cropper('destroy').cropper(options);
    });


    // Tooltips
    $('[data-toggle="tooltip"]').tooltip();

  }());

});
