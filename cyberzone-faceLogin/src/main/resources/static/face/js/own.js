$(function(){
    //getdata();
    var id,password;
    var editer = false;
    var save = false;
    $('#edit').click(function(event) {
        if(editer){
            $(this).html('编辑');
            $('.p2 input').removeClass('activeInput').attr('readonly', true);
            updata('signature',$('.p2 input').val());
        }else{
            $(this).html('保存');
            $('.p2 input').addClass('activeInput').attr('readonly', false);
        }
        editer = !editer;
        
    });
    $('#username').click(function(event) {
      if($('#username').html() == ''){

      }else{
        $('.black').css('display', 'block');
      }
      
    });
    $('.X').click(function(event) {
      $('.black').css('display', 'none');
    });
    $('#repassword').click(function(event) {
      if(save){
            $('#pwd').css('display', 'none');
        }else{
            $('#pwd').css('display', 'block');
        }
        save = !save;
    });
    $('#savePwd').click(function(event) {
        validate();
    });
    var myfile = document.getElementById('file');
    myfile.onchange = function(){
      var files = this.files; 
      //console.log(files)
      updata('avatarFile',files[0]);
    }
})
//验证用户密码
function validate(){
  var oldp = $('#oldPwd').val();
  var newp = $('#newPwd').val();
  var rep = $('#rePwd').val();
  if( oldp == password && newp == rep){
      $('#errorPwd').css('display', 'none');
      updata('password',rep);
  }else if( oldp == password && newp != rep){
      $('#errorPwd').css('display', 'block');
      $('#errorPwd').html('两次密码不一样！');
  }else if( oldp != password && newp == rep){
    $('#errorPwd').css('display', 'block');
    $('#errorPwd').html('原密码输入错误！');
  }
   
} 
//修改用户信息
 function updata(name,val){
  var data = new FormData();
    data.append('id', id);
    data.append(name, val);
    $.ajax({
          type: 'POST',
          cache: false,
          contentType: false,
          processData: false,
          xhrFields: {
              withCredentials: true
          },
          dataType: "json",
          url: 'https://192.168.5.28:5555/common/sysUser/update',
          data: data,
          success: function (data) { 
             $('#pwd').css('display', 'none');
          },
          error: function (err) {
              console.error(err);
          }
          
      });
 }
 //获取用户信息
 function getdata(){
    $.ajax({
          url:'https://192.168.5.28:5555/common/sysUser/personalCenter',
          type:"get",
          xhrFields: {
                withCredentials: true
          },
          crossDomain: true,
          success:function(data){   
            console.log(data)
           if(data.httpCode == 200){
               var user = data.user;
               id = user.id;
               $('#account').html(data.user.account);
               $('#userNameForm').html(data.user.userName);
               $('#email').html(data.user.email);
               $('#phone').html(data.user.phone);
               $('.p2 input').val(data.user.signature);
               $('#avatar').attr('src', 'https://192.168.5.28:5555/common/sysUser/showPicture?filePath='+data.user.avatar);
               id = data.user.id;
               password = data.user.password;
               var system = data.subsystem;
               $('#li3').html('');
               for(var i=0;i<system.length;i++){
                    if(system[i] == 1){
                        $('#li3').append('<p class="p4">管理系统</p>');
                    }
                    if(system[i] == 2){
                        $('#li3').append('<p class="p4">资源库系统</p>');
                    }
                    if(system[i] == 3){
                        $('#li3').append('<p class="p4">攻防演练系统</p>');
                    }
                    if(system[i] == 4){
                        $('#li3').append('<p class="p4">测评与服务系统</p>');
                    }
                }
             }else{
              alert("网络错误！");
             }
          }
      });
 }