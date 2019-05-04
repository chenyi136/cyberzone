// var recoURL='https://192.168.4.130:5000/register';
var recoURL='https://192.168.51.46:5000/register';
var iD=parseInt(Math.random()*10000000000+1);
var registerCount = 0;
var LIMIT = 10;
var video = null;
var canvas = document.createElement('canvas');
var capTimer = null;
var width = 300;
var height = 400;
var data = null;
var requestCount = 0;
var winWidth = window.screen.width;
var winHeight = window.screen.height;
var scanFrame = document.getElementsByClassName('container-frame')[0];


    var frameWidth = scanFrame.offsetWidth;

function getVideo() {
    if (!navigator.getUserMedia)
        navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia ||
            navigator.mozGetUserMedia || navigator.msGetUserMedia;


    if (!navigator.mediaDevices || !navigator.mediaDevices.enumerateDevices) {
        console.log("enumerateDevices() not supported.");
        return;
    }

    var exArray = [];
    navigator.mediaDevices.enumerateDevices()
        .then(function (devices) {
            devices.forEach(function (device) {
                if (device.kind === 'videoinput') {
                    exArray.push(device.deviceId);
                }
            });
            if (navigator.getUserMedia) {
                navigator.getUserMedia({video: {deviceId: exArray[1]}}, captureVideo, function (e) {
                    alert('Error capturing audio.');
                });
            } else {
                alert('getUserMedia not supported in this browser.');
            }
        })
        .catch(function (err) {
            console.log(err.name + ": " + err.message);
        });

}

function captureVideo(stream) {
    video = document.getElementById('video');
        // width = frameWidth;
        // height = frameWidth;
        // video.setAttribute('width', width);
        // video.setAttribute('height', height);


    if (window.URL) {
        video.srcObject = stream;
    } else {
        video.src = stream;
    }

    video.autoplay = true;
}

function takePicture() {
    var size=video.videoHeight;
    canvas.width = size;
    canvas.height = size;

    var context = canvas.getContext('2d');

    if (width && height) {
        var sx = Math.floor((video.videoWidth - size) / 2);
        var sy = 0;
        var w = size;
        var h = size;
        context.drawImage(video, sx, sy, w, h, 0, 0, w, h);

        var data2 = canvas.toDataURL('image/jpeg');
        if (data === data2) {
        } else {
            data = data2;
	        id=("0000000000"+iD).toString().substr(-10);
          /*  $.ajax({
		        type: 'POST',
                contentType: false,
                url: recoURL,
                data: id+data,
                error: function (err) {
                    alert('网络错误！');
                        $.ajax({
                          url:"https://192.168.5.28:5555/login/loginOut",
                          type:"get",
                          xhrFields: {
                                withCredentials: true
                          },
                          crossDomain: true,
                          success:function(data){   
                           window.location.href = "https://192.168.5.28:8443/login.html"
                          },
                          error:function(e){
                              alert("错误！！");
                          }
                      });                
                    },
                success: function (data) {
                    if (data == 'registered') {
                        registerCount++;
                    }
                    console.log(text);
                    if (capTimer == null) {
                        var text = ' '
                        console.log(registerCount);
                        if (registerCount > LIMIT) {
                            text='注册成功,id: '+id
                            //var uid = location.search.split("=")[1]
                            sendFaceId(id);
                            console.log(2)
                        }else{
                            alert('验证失败，请联系管理员');
                            $.ajax({
                              url:"https://192.168.5.28:5555/login/loginOut",
                              type:"get",
                              xhrFields: {
                                    withCredentials: true
                              },
                              crossDomain: true,
                              success:function(data){   
                               window.location.href = "https://192.168.5.28:8443/login.html"
                              },
                              error:function(e){
                                  alert("错误！！");
                              }
                          });
                        }
                        registerResult(text)
                        // window.location.href = 'http://192.168.40.98/user/login';
                    }
                    
                }
            });*/
				var form = new FormData();
				  form.append("face",data);
				　$.ajax({
								url:recoURL,
								type:"post",
								data:form,
								processData:false,
								contentType:false,
                                xhrFields: {withCredentials: true},
								success: function (data) {
								if (data.msg === 'success') {
									registerCount++;
								}else{
									alert("未检测到人脸，请重试");
							       window.location.href = "https://192.168.50.28:8443/reg.html";
								}
								console.log(data);
								if (capTimer == null) {
									var text = ' '
									console.log(registerCount);
									if (registerCount > LIMIT) {
										text='注册成功,id: '+id
										//var uid = location.search.split("=")[1]
										//alert("成功"+data );
										if(data!="faild"){										
										sendFaceId(data);
										}else{
										 alert("没有检测到人脸请重新注册");	
										}
										//console.log(2)
									}else{
										alert('验证失败，请联系管理员');
										$.ajax({
										  url:"https://192.168.5.28:5555/login/loginOut",
										  type:"get",
										  xhrFields: {
												withCredentials: true
										  },
										  crossDomain: true,
										  success:function(data){   
										   window.location.href = "https://192.168.5.28:8443/login.html"
										  },
										  error:function(e){
											  alert("错误！！");
										  }
									  });
									}
									registerResult(text);
									window.location.href = 'scan.html';
								}
								
							}
				});
            clearPhoto();
        }
    } else {
        clearPhoto();
    }
}

function sendFaceId(id) {
    var date = new FormData();
    date.append("faceid", id);	
    $.ajax({
            type: 'POST',
            cache: false,
            contentType: false,
            processData: false,
            xhrFields: {
                withCredentials: true
            },
			async:false, //简直无语如果想兼容firefox 必须改为同步
            dataType: "json",
            url: 'https://192.168.50.28:5555/login/user/faceregister',
            //data: {faceid:id},
			data: date,
            error: function (err) {
                console.error(err);
            },
            success: function (message) {
                // window.location.href = 'https://192.168.5.28:8443/five.html';
				alert("注册成功!!!");
            }
        });
}

function registerResult(text) {
    clearInterval(capTimer);
    var tip = document.getElementsByClassName('container-tip')[0];
    tip.innerText = text;
}

function clearPhoto() {
    var context = canvas.getContext('2d');
    context.fillStyle = "#AAA";
    context.fillRect(0, 0, canvas.width, canvas.height);
}


function scanLineMove() {
    var height = document.getElementsByClassName('container-frame')[0].offsetHeight;
    height = height - 20;
    $('#scan-line').css("top", "0px");
    $('#scan-line').animate({top: height}, 2000, "linear", scanLineMove);
}

function startScan() {
    $('#scan-btn').hide();
    var tips = ['请将镜头对准脸部', '请保持姿势..', '请保持姿势....', '请保持姿势......', '请保持姿势..........', '请耐心等待...'];
    var text = document.getElementsByClassName('container-tip')[0];
    var index = 0;
    function updateTip() {
        text.innerText = tips[index++];
    }

    text.innerText = '请将镜头对准脸部';

    setTimeout(function () {

	capTimer = setInterval(takePicture, 1000);
        var tipTimer = setInterval(function () {
            if (index == tips.length) {
                clearInterval(tipTimer);
                clearInterval(capTimer);
		        capTimer = null;
                console.log(1)
                takePicture();
            } else {
                updateTip(index);
            }
        }, 3000);


        clearPhoto();

    }, 3000);
}

       
getVideo();



