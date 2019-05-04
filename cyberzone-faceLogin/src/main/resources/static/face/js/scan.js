// var recoURL='https://192.168.50.28:5000/face';
var recoURL='https://192.168.51.46:5000/face';
var faceidUrl ="https://192.168.50.28:5555/login/user/getfaceid";

var video = null;
var canvas = document.createElement('canvas');
var photo = document.createElement('img');
var capTimer = null;
var width = 300;
var height = 400;
var data = null;
var winWidth = window.screen.width;
var winHeight = window.screen.height;
var scanFrame = document.getElementsByClassName('container-frame')[0];
var count = 1;
var scanCount = 0;
var faceid = null;
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
    width = window.screen.width;
    height = window.screen.height;
    video.setAttribute('width', width);
    video.setAttribute('height', height);


    if (window.URL) {
        //video.src = window.URL.createObjectURL(stream);
        video.srcObject=stream
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
			var form = new FormData();
            form.append("face", data2);
          if (data === data2) {
        } else {
            data = data2;
            var form = new FormData();
            form.append("face", data2);
            $.ajax({
                type: 'POST',
                contentType: false,
                url: recoURL,
                data: form,
                processData: false,
                contentType: false,
                xhrFields: {withCredentials: true},
				error: function (err) {
                    alert('验证失败，请联系管理员');
                    $.ajax({
                      url:"https://192.168.5.178:5555/login/loginOut",
                      type:"get",
                      xhrFields: {
                            withCredentials: true
                      },
                      crossDomain: true,
                      success:function(data){
                       window.location.href = "https://192.168.5.178:8443/login.html"
                      },
                      error:function(e){
                          alert("错误！！");
                      }
                  });
                },
                success: scanSuccess
            });
        }
    } else {
        clearPhoto();
    }
}

function getFaceid(){
    $.ajax({
        type: 'POST',
        contentType: false,
        url: faceidUrl,
        data: data,
		xhrFields:{withCredentials: true},
        error: function (err) {
           console.log(err)
        },
        success: function(data) {
            console.log(data)
            faceid = data.successMsg;
            
        }
    });
}

function scanSuccess(mes) {
    // var d = mes.split(":")
    // if (d.length === 1) {
    //     return;
    // }
    // var remoteFaceid = d[1].trim()
    // var qs = {};
    // var query = location.search.slice(1).split('&').forEach(item => {
    // 	var [key, value] = item.split('=');
    // 	qs[key] = value;
    // })
    // console.log(qs);
    //getFaceid();
    /*这里的qs.ufaceid为数据库查询到的faceid*/
    // if (faceid === remoteFaceid) {

    if (mes.data === true) {
        clearInterval(capTimer);
        capTimer = null;
        $('.sucess').show();
        $('.waiting').hide();
        $('#scan-btn').hide();
        console.log("成功");
        console.log(capTimer);
        var  jj=new FormData();
        jj.append("isfaceLogin","success");
        $.ajax({
            type: "POST",
            url: "https://192.168.50.28:5555/login/faceLogin",
            processData:false,
            contentType:false,
            xhrFields: {withCredentials: true},
            data: jj,
            success: function(msg){
                if("人脸登录成功"===msg.successMsg ){
                    window.location.href="https://192.168.50.28:8443/five.html";
                }
            }
        });
        // window.location.href = "https://www.baidu.com";


        // var capTimer1 = setTimeout(function () {
        //     getcheck();
        // }, 2000);
    } else {
        scanCount++;
        if (scanCount >= 3) {
            console.log("失败")
            clearInterval(capTimer);
            capTimer = null;
            console.log(capTimer)
            $('.waiting').hide();
            $('.sucess').hide();
            $('#scan-btn').hide();
            alert('验证失败，请联系管理员');

            $.ajax({
                url: "https://192.168.50.28:5555/login/loginOut",
                type: "get",
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                success: function (data) {
                    window.location.href = "https://192.168.50.28:8443/login.html"
                },
                error: function (e) {
                    alert("错误！！");
                }
            });

        } else if (scanCount < 3) {
            $('.sucess').hide();
            $('#scan-btn').hide();
            $('.waiting').show();
        }
    }


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
    $('.waiting').show();
    scanLineMove();
    setTimeout(function () {
        capTimer = setInterval(takePicture, 3000);
        clearPhoto();
    }, 1000);
}

function doPostBack(url, backFunc, queryParam, errFunc) {
    $.ajax({
        type: 'POST',
        contentType: 'application/json; charset=UTF-8',
        dataType: 'json', 
        url: url,
        data: JSON.stringify(queryParam),
        error: errFunc,
        success: backFunc
    });
}


function intoScan(id) {
    $('#scan-line').css("top", "0px");
    jump('scan', id);
    jump('scan-btn');
    if (id == 'index') {
        getVideo();
        if (firstInto == 'yes') {
            setTimeout(function () {
                jump('strategy');
                firstInto = 'no';
            }, 500);
        }
    }
    $("#animate").children('div').css('display', 'none');
    $('#animate').hide();
    $('#control_tip').hide();
}

function getcheck() {
	    var user = {
            "faceIsLogin" : 1,
          };
        $.ajax({
                url:"https://192.168.50.28:5555/login/faceLogin",
                type:"post",
                contentType : 'application/json;charset=UTF-8',
                dataType:'json',
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                data:JSON.stringify(user),
                success:function(data){
					window.location.href = 'https://192.168.5.178:8443/five.html';
                },
                error:function(e){
                    alert("错误！！");
                }
            });
} 


getVideo();
getFaceid();




