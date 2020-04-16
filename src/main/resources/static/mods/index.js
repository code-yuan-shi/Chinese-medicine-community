/**

 @Name: Fly社区主入口

 */
 

layui.define(['layer', 'laytpl', 'form', 'element', 'upload', 'util','laypage'], function(exports){
  
  var $ = layui.jquery
  ,layer = layui.layer
  ,laytpl = layui.laytpl
  ,form = layui.form
  ,element = layui.element
  ,upload = layui.upload
  ,util = layui.util
  ,laypage = layui.laypage
  ,device = layui.device()

  ,DISABLED = 'layui-btn-disabled';
  
  //阻止IE7以下访问
  if(device.ie && device.ie < 8){
    layer.alert('如果您非得使用 IE 浏览器访问Code社区，那么请使用 IE8+');
  }
  
  layui.focusInsert = function(obj, str){
    var result, val = obj.value;
    obj.focus();
    if(document.selection){ //ie
      result = document.selection.createRange(); 
      document.selection.empty(); 
      result.text = str; 
    } else {
      result = [val.substring(0, obj.selectionStart), str, val.substr(obj.selectionEnd)];
      obj.focus();
      obj.value = result.join('');
    }
  };


  //数字前置补零
  layui.laytpl.digit = function(num, length, end){
    var str = '';
    num = String(num);
    length = length || 2;
    for(var i = num.length; i < length; i++){
      str += '0';
    }
    return num < Math.pow(10, length) ? str + (num|0) : num;
  };
  
  var fly = {

    //Ajax
    json: function(url, data, success, options){
      var that = this, type = typeof data === 'function';
      
      if(type){
        options = success
        success = data;
        data = {};
      }

      options = options || {};

      return $.ajax({
        type: options.type || 'post',
        dataType: options.dataType || 'json',
        data: data,
        url: url,
        success: function(res){
          if(res.status === 0) {
            success && success(res);
          } else {
            layer.msg(res.msg || res.data.code, {icon: 2});
            options.error && options.error();
          }
        }, error: function(e){
          layer.msg('请求异常，请重试', {shift: 6});
          options.error && options.error(e);
        }
      });
    }
    //计算字符长度
    ,charLen: function(val){
      var arr = val.split(''), len = 0;
      for(var i = 0; i <  val.length ; i++){
        arr[i].charCodeAt(0) < 299 ? len++ : len += 2;
      }
      return len;
    }
    
    ,form: {}

    //简易编辑器
    ,layEditor: function(options){
      var html = ['<div class="layui-unselect fly-edit">'
        ,'<span type="face" title="插入表情"><i class="iconfont icon-yxj-expression" style="top: 1px;"></i></span>'
        ,'<span type="picture" title="插入图片：img[src]"><i class="iconfont icon-tupian"></i></span>'
        ,'<span type="href" title="超链接格式：a(href)[text]"><i class="iconfont icon-lianjie"></i></span>'
        ,'<span type="quote" title="引用">“”</span>'
        ,'<span type="code" title="插入代码"><i class="iconfont icon-emwdaima" style="top: 1px;"></i></span>'
        ,'<span type="hr" title="插入水平线">hr</span>'
        ,'<span type="a" title="文章标题">A</span>'
        ,'<span type="h1" title="标题一">H1</span>'
        ,'<span type="h2" title="标题二">H2</span>'
        ,'<span type="h3" title="标题三">H3</span>'
        ,'<span type="yulan" title="预览"><i class="iconfont icon-yulan1"></i></span>'
      ,'</div>'].join('');

      var log = {}, mod = {
        face: function(editor, self){ //插入表情
          var str = '', ul, face = fly.faces;
          for(var key in face){
            str += '<li title="'+ key +'"><img src="'+ face[key] +'"></li>';
          }
          str = '<ul id="LAY-editface" class="layui-clear">'+ str +'</ul>';
          layer.tips(str, self, {
            tips: 3
            ,time: 0
            ,skin: 'layui-edit-face'
          });
          $(document).on('click', function(){
            layer.closeAll('tips');
          });
          $('#LAY-editface li').on('click', function(){
            var title = $(this).attr('title') + ' ';
            layui.focusInsert(editor[0], 'face' + title);
          });
        }
        ,picture: function(editor){ //插入图片
          layer.open({
            type: 1
            ,id: 'fly-jie-upload'
            ,title: '插入图片'
            ,shade: false
            ,area: 'auto'
            ,fixed: false
            ,offset: [
              editor.offset().top - $(window).scrollTop() + 'px'
              ,editor.offset().left + 'px'
            ]
            ,skin: 'layui-layer-border'
            ,content: ['<ul class="layui-form layui-form-pane" style="margin: 20px;">'
              ,'<li class="layui-form-item">'
                ,'<label class="layui-form-label">URL</label>'
                ,'<div class="layui-input-inline">'
                    ,'<input required name="image" placeholder="支持直接粘贴远程图片地址" value="" class="layui-input">'
              ,'</div>'
                  ,'<button type="button" class="layui-btn layui-btn-normal" id="uploadImg"><i class="layui-icon">&#xe67c;</i>本地上传</button>&nbsp;'
                  ,'<img style="width: 15%" class="layui-upload-img" id="demo1">'
              ,'</li>'
              ,'<div class="layui-progress" lay-showpercent="true" lay-filter="demo">'
                  ,'<div class="layui-progress-bar" lay-percent="0%"></div>'
              ,'</div>'
              ,'<br><li class="layui-form-item" style="text-align: center;">'
              ,'<button type="button" lay-submit lay-filter="uploadImages" class="layui-btn">确认</button>'
              ,'</li>'
            ,'</ul>'].join('')
            ,success: function(layero, index){
              var image =  layero.find('input[name="image"]');

              //执行上传实例
              upload.render({
                elem: '#uploadImg'
                ,url: '/api/upload'
                ,size: 2048
                ,progress: function(n, elem) {
                  console.log(n)
                  element.progress('demo',  n+"%")
                }
              ,before: function(obj){
                  //预读本地文件示例，不支持ie8
                  obj.preview(function(index, file, result){
                    $('#demo1').attr('src', result); //图片链接（base64）
                  });
                }
                ,done: function(res){
                  if(res.status == 0){
                    layer.msg(res.msg,{icon:1,time:1*1000},function () {
                      image.val(res.data.url);
                    })
                  } else {
                    element.progress('demo', "0%");
                    $('#demo1').removeAttr('src'); //图片链接（base64）
                    layer.msg(res.msg, {icon: 5,time:1000});
                  }
                }
              });
              
              form.on('submit(uploadImages)', function(data){
                var field = data.field;
                if(!field.image) return image.focus();
                layui.focusInsert(editor[0], 'img['+ field.image + ']\n');
                layer.close(index);
              });
            }
          });
        }
        ,href: function(editor){ //超链接
          layer.prompt({
            title: '请输入链接地址'
            ,placeholder:'请输入链接地址'
            ,shade: false
            ,fixed: false
            ,id: 'LAY_flyedit_href'
            ,offset: [
              editor.offset().top - $(window).scrollTop() + 'px'
              ,editor.offset().left + 'px'
            ]
          }, function(val, index, elem){
            if(!/^http(s*):\/\/[\S]/.test(val)){
              layer.tips('以“http(s)://”开头', elem, {tips:1})
              return;
            }
            layui.focusInsert(editor[0], ' a('+ val +')['+ val + '] ');
            layer.close(index);
          });
        }
        ,code: function(editor){ //插入代码
          layer.prompt({
            title: '请贴入代码或任意文本'
            ,formType: 2
            ,maxlength: 10000
            ,shade: false
            ,id: 'LAY_flyedit_code'
            ,area: ['800px', '360px']
          }, function(val, index, elem){
            layui.focusInsert(editor[0], '[quote]\n'+val+ '\n[/quote]\n');
            layer.close(index);
          });
        }
        ,quote: function(editor){ //引用
          layer.prompt({
            title: '请输入引用内容'
            ,formType: 2
            ,maxlength: 10000
            ,shade: false
            ,id: 'LAY_flyedit_quote'

            ,area: ['260px', '100px']
          }, function(val, index, elem){
            layui.focusInsert(editor[0], '[pre]\n'+ val + '\n[/pre]\n');
            layer.close(index);
            editor.trigger('keyup');
          });
        }
        ,hr: function(editor){ //插入水平分割线
          layui.focusInsert(editor[0], '[hr]\n');
        }
        , a: function(editor){ //文章标题
          layer.prompt({
            title: '请输入文章标题'
            ,placeholder:'请输入文章标题'
            ,maxlength: 50
            ,shade: false
            ,fixed: false
            ,id: 'LAY_flyedit_a'
            ,offset: [
              editor.offset().top - $(window).scrollTop() + 'px'
              ,editor.offset().left + 'px'
            ]
          }, function(val, index, elem){
            layui.focusInsert(editor[0], '[h1]'+ val + '[/h1]\n');
            layer.close(index);
          });
        }
        ,h1: function(editor){ //标题一
          layer.prompt({
            title: '请输入标题一'
            ,placeholder:'请输入标题一'
            ,maxlength: 50
            ,shade: false
            ,fixed: false
            ,id: 'LAY_flyedit_h1'
            ,offset: [
              editor.offset().top - $(window).scrollTop() + 'px'
              ,editor.offset().left + 'px'
            ]
          }, function(val, index, elem){
            layui.focusInsert(editor[0], '[h2]'+ val + '[/h2]\n');
            layer.close(index);
          });
        }
        ,h2: function(editor){ //标题二
        layer.prompt({
          title: '请输入标题二'
          ,placeholder:'请输入标题二'
          ,maxlength: 50
          ,shade: false
          ,fixed: false
          ,id: 'LAY_flyedit_h2'
          ,offset: [
            editor.offset().top - $(window).scrollTop() + 'px'
            ,editor.offset().left + 'px'
          ]
        }, function(val, index, elem){
          layui.focusInsert(editor[0], '[h3]'+ val + '[/h3]\n');
          layer.close(index);
        });
      }
        ,h3: function(editor){ //标题三
          layer.prompt({
            title: '请输入标题三'
            ,placeholder:'请输入标题三'
            ,maxlength: 50
            ,shade: false
            ,fixed: false
            ,id: 'LAY_flyedit_h3'
            ,offset: [
              editor.offset().top - $(window).scrollTop() + 'px'
              ,editor.offset().left + 'px'
            ]
          }, function(val, index, elem){
            layui.focusInsert(editor[0], '[h4]'+ val + '[/h4]\n');
            layer.close(index);
          });
        }
        ,yulan: function(editor){ //预览
          var content = editor.val();
          
          content = /^\{html\}/.test(content) 
            ? content.replace(/^\{html\}/, '')
          : fly.content(content);

          layer.open({
            type: 1
            ,title: '预览'
            ,shade: false
            ,offset:'r'
            ,area: ['100%', '100%']
            ,scrollbar: false
            ,content: '<div class="detail-body" style="margin:20px;width: 775px;">'+ content +'</div>'
          });
        }
      };
      
      layui.use('face', function(face){
        options = options || {};
        fly.faces = face;
        $(options.elem).each(function(index){
          var that = this, othis = $(that), parent = othis.parent();
          parent.prepend(html);
          parent.find('.fly-edit span').on('click', function(event){
            var type = $(this).attr('type');
            mod[type].call(that, othis, this);
            if(type === 'face'){
              event.stopPropagation()
            }
          });
        });
      });
      
    }

    ,escape: function(html){
      return String(html||'').replace(/&(?!#?[a-zA-Z0-9]+;)/g, '&amp;')
      .replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/'/g, '&#39;').replace(/"/g, '&quot;');
    }

    //内容转义
    ,content: function(content){
      var util = fly
          ,item = fly.faces;

      //支持的html标签
      var html = function(end){
        return new RegExp('\\n*\\|\\-'+ (end||'') +'(div|span|p|button|table|thead|th|tbody|tr|td|ul|li|ol|li|dl|dt|dd|h2|h3|h4|h5)([\\s\\S]*?)\\-\\|\\n*', 'g');
      };
          //XSS
          content = util.escape(content||'')
          //转义图片
          .replace(/img\[([^\s]+?)\]/g, function(img){
            return '<img src="' + img.replace(/(^img\[)|(\]$)/g, '') + '">';
          })
          //转义@
          .replace(/@@(\S+)(\s+?|$)/g, '@<a href="javascript:;" class="fly-aite">$1</a>$2')
          //转义表情
          .replace(/face\[([^\s\[\]]+?)\]/g, function(face){
            var alt = face.replace(/^face/g, '');
            return '<img alt="'+ alt +'" title="'+ alt +'" src="' + item[alt] + '">';
          })
          //转义脚本
          .replace(/a(\(javascript:)(.+)(;*\))/g, 'a(javascript:layer.msg(\'非法脚本\');)')
          //转义链接
          .replace(/a\([\s\S]+?\)\[[\s\S]*?\]/g, function(str){
            var href = (str.match(/a\(([\s\S]+?)\)\[/)||[])[1];
            var text = (str.match(/\)\[([\s\S]*?)\]/)||[])[1];
            if(!href) return str;
            var rel =  /^(http(s)*:\/\/)\b(?!(\w+\.)*(sentsin.com|layui.com))\b/.test(href.replace(/\s/g, ''));
            return '<a href="'+ href +'" target="_blank"'+ (rel ? ' rel="nofollow"' : '') +'>'+ (text||href) +'</a>';
          })
          //转义横线
          .replace(/\[hr\]\n*/g, '<hr>')
          //转义表格
          .replace(/\[table\]([\s\S]*)\[\/table\]\n*/g, function(str){
            return str.replace(/\[(thead|th|tbody|tr|td)\]\n*/g, '<$1>')
                .replace(/\n*\[\/(thead|th|tbody|tr|td)\]\n*/g, '</$1>')

                .replace(/\[table\]\n*/g, '<table class="layui-table">')
                .replace(/\n*\[\/table\]\n*/g, '</table>');
          })
          //转义 div/span
          .replace(/\n*\[(div|span)([\s\S]*?)\]([\s\S]*?)\[\/(div|span)\]\n*/g, function(str){
            return str.replace(/\[(div|span)([\s\S]*?)\]\n*/g, '<$1 $2>')
                .replace(/\n*\[\/(div|span)\]\n*/g, '</$1>');
          })
          //转义列表
          .replace(/\[ul\]([\s\S]*)\[\/ul\]\n*/g, function(str){
            return str.replace(/\[li\]\n*/g, '<li>')
                .replace(/\n*\[\/li\]\n*/g, '</li>')

                .replace(/\[ul\]\n*/g, '<ul>')
                .replace(/\n*\[\/ul\]\n*/g, '</ul>');
          })
          //转义引用
          .replace(/\[pre\]([\s\S]*)\[\/pre\]\n*/g, function(str){
            return str.replace(/\[pre\]\n*/g, '<pre>')
                .replace(/\n*\[\/pre\]\n*/g, '</pre>');
          })
          //转义标题
          .replace(/\[title\]([\s\S]*)\[\/title\]\n*/g, function(str){
            return str.replace(/\[title\]\n*/g, '<h1>')
                .replace(/\n*\[\/title\]\n*/g, '</h1><br>');
          })
          //转义h1
          .replace(/\[h1\]([\s\S]*)\[\/h1\]\n*/g, function(str){
            return str.replace(/\[h1\]\n*/g, '<h2>')
                .replace(/\n*\[\/h1\]\n*/g, '</h2>');
          })
          //转义h2
          .replace(/\[h2\]([\s\S]*)\[\/h2\]\n*/g, function(str){
            return str.replace(/\[h2\]\n*/g, '<h3>')
                .replace(/\n*\[\/h2\]\n*/g, '</h3>');
          })
          //转义h3
          .replace(/\[h3\]([\s\S]*)\[\/h3\]\n*/g, function(str){
            return str.replace(/\[h3\]\n*/g, '<h4>')
                .replace(/\n*\[\/h3\]\n*/g, '</h4>');
          })
          //转义h4
          .replace(/\[h4\]([\s\S]*)\[\/h4\]\n*/g, function(str){
            return str.replace(/\[h4\]\n*/g, '<h5>')
                .replace(/\n*\[\/h4\]\n*/g, '</h5>');
          })
          //转义代码
          .replace(/\[quote\]([\s\S]*)\[\/quote\]\n*/g, function(str){
            return str.replace(/\[quote\]\n*/g, '<div class="layui-code">')
                .replace(/\n*\[\/quote\]\n*/g, '</div>');
          })
          //转义换行
          .replace(/\n/g, '<br>');
      return content;
    }
    
    //新消息通知
   ,newmsg: function(){
      var elemUser = $('.fly-nav-user');
      var t1=null;
      if(layui.cache.user.uid != -1 && elemUser[0]){
        queryMsgCout();
        var timesRun = 10;
        t1= window.setInterval(queryMsgCout,5000);
        function queryMsgCout() {
          timesRun++;
          fly.json('/message/nums', {
            _: new Date().getTime()
          }, function (res) {
            if (res.status === 0 && res.data.count > 0) {
              var msg = $('<a class="fly-nav-msg" href="javascript:;">' + res.data.count + '</a>');
              elemUser.append(msg);
              msg.on('click', function () {
                fly.json('/message/read', {}, function (res) {
                  if (res.status === 0) {
                    location.href = '/user/message/';
                  }
                });
              });
              layer.tips('你有 ' + res.data.count + ' 条未读消息', msg, {
                tips: 3
                , tipsMore: true
                , fixed: true
              });
              msg.on('mouseenter', function () {
                layer.closeAll('tips');
              })
            }
          });
          if (timesRun === 10) {
            clearInterval(t1);
          }
        }
      }
      return arguments.callee;
    }
    
  };

  //签到
  var tplSignin = ['{{# if(d.signed){ }}'
    ,'<button class="layui-btn layui-btn-disabled">今日已签到</button>'
    ,'<span>获得了<cite>{{ d.experience }}</cite>经验</span>'
    ,'{{# } else { }}'
    ,'<button class="layui-btn layui-btn-danger" id="LAY_signin">今日签到</button>'
    ,'<span>可获得<cite>{{ d.experience }}</cite>经验</span>'
    ,'{{# } }}'].join('')
      ,tplSigninDay = '已连续签到<cite>{{ d.days }}</cite>天'

      ,signRender = function(data){
    laytpl(tplSignin).render(data, function(html){
      elemSigninMain.html(html);
    });
    laytpl(tplSigninDay).render(data, function(html){
      elemSigninDays.html(html);
    });
  }

      ,elemSigninHelp = $('#LAY_signinHelp')
      ,elemSigninTop = $('#LAY_signinTop')
      ,elemSigninMain = $('.fly-signin-main')
      ,elemSigninDays = $('.fly-signin-days');

  if(elemSigninMain[0] && layui.cache.user.uid !=-1){
    fly.json('/sign/status', function(res){
      if(!res.data) return;
      signRender.token = res.data.token;
      signRender(res.data);
    });

  }
  $('body').on('click', '#LAY_signin', function(){
    if(layui.cache.user.uid == -1){
      layer.msg('签到需要登录！');
      return;
    }
    var othis = $(this);
    if(othis.hasClass(DISABLED)) return;

    fly.json('/sign/in', {
      token: signRender.token || 1
    }, function(res){
      signRender(res.data);
    }, {
      error: function(){
        othis.removeClass(DISABLED);
      }
    });

    othis.addClass(DISABLED);
  });
  //签到说明
  elemSigninHelp.on('click', function(){
    layer.open({
      type: 1
      ,title: '签到说明'
      ,area: '300px'
      ,shade: 0.8
      ,shadeClose: true
      ,content: ['<div class="layui-text" style="padding: 20px;">'
        ,'<blockquote class="layui-elem-quote">“签到”可获得社区经验，规则如下</blockquote>'
        ,'<table class="layui-table">'
          ,'<thead>'
            ,'<tr><th>连续签到天数</th><th>每天可获经验</th></tr>'
          ,'</thead>'
          ,'<tbody>'
            ,'<tr><td>＜5</td><td>5</td></tr>'
            ,'<tr><td>≥5</td><td>10</td></tr>'
            ,'<tr><td>≥15</td><td>15</td></tr>'
            ,'<tr><td>≥30</td><td>20</td></tr>'
            ,'<tr><td>≥100</td><td>30</td></tr>'
            ,'<tr><td>≥365</td><td>50</td></tr>'
          ,'</tbody>'
        ,'</table>'
        ,'<ul>'
          ,'<li>中间若有间隔，则连续天数重新计算</li>'
          ,'<li style="color: #FF5722;">不可利用程序自动签到，否则经验清零</li>'
        ,'</ul>'
      ,'</div>'].join('')
    });
  });

  //签到活跃榜
  var tplSigninTop = ['{{# layui.each(d.data, function(index, item){ }}'
    ,'<li>'
      ,'<a href="/user/home/{{item.userId}}" target="_blank">'
        ,'<img src="{{item.avatarUrl}}">'
        ,'<cite class="fly-link">{{item.name}}</cite>'
      ,'</a>'
      ,'{{# var date = new Date(item.qiandaoCreate); if(d.index == 0){ }}'
        ,'<span class="fly-grey">签到于 {{ layui.util.toDateString(date) }}</span>'
      ,'{{# }else if(d.index == 1 ){  }}'
    ,'<span class="fly-grey">签到于 {{ layui.laytpl.digit(date.getHours()) + ":" + layui.laytpl.digit(date.getMinutes()) + ":" + layui.laytpl.digit(date.getSeconds()) }}</span>'
      ,'{{# } else { }}'
        ,'<span class="fly-grey">已连续签到 <i>{{ item.total }}</i> 天</span>'
      ,'{{# } }}'
    ,'</li>'
  ,'{{# }); }}'
  ,'{{# if(d.data.length === 0) { }}'
    ,'{{# if(d.index < 2) { }}'
      ,'<li class="fly-none fly-grey">今天还没有人签到</li>'
    ,'{{# } else { }}'
      ,'<li class="fly-none fly-grey">还没有签到记录</li>'
    ,'{{# } }}'
  ,'{{# } }}'].join('');

  elemSigninTop.on('click', function(){
    var loadIndex = layer.load(1, {shade: 0.8});
    fly.json('/sign/getSign', function(res){ //实际使用，请将 url 改为真实接口
      var tpl = $(['<div class="layui-tab layui-tab-brief" style="margin: 5px 0 0;">'
        ,'<ul class="layui-tab-title">'
          ,'<li class="layui-this">最新签到</li>'
          ,'<li>今日最快</li>'
          ,'<li>总签到榜</li>'
        ,'</ul>'
        ,'<div class="layui-tab-content fly-signin-list" id="LAY_signin_list">'
          ,'<ul class="layui-tab-item layui-show"></ul>'
          ,'<ul class="layui-tab-item">2</ul>'
          ,'<ul class="layui-tab-item">3</ul>'
        ,'</div>'
      ,'</div>'].join(''))
      ,signinItems = tpl.find('.layui-tab-item');

      layer.close(loadIndex);

      layui.each(signinItems, function(index, item){
        var html = laytpl(tplSigninTop).render({
          data: res.data[index]
          ,index: index
        });
        $(item).html(html);
      });

      layer.open({
        type: 1
        ,title: '签到活跃榜 - TOP 20'
        ,area: '300px'
        ,shade: 0.8
        ,shadeClose: true
        ,id: 'layer-pop-signintop'
        ,content: tpl.prop('outerHTML')
      });

    }, {type: 'get'});
  });


  //回帖周榜
  var tplReply = ['{{# layui.each(d.data, function(index, item){ }}'
        ,'<dd>'
          ,'<a href="/user/home/{{item.userId}}">'
            ,'<img src="{{item.avatarUrl}}">'
            ,'<cite>{{item.name}}</cite>'
            ,'<i>{{item.commentNum}}次回答</i>'
          ,'</a>'
        ,'</dd>'
      ,'{{# }); }}'
    ,'{{# if(d.data.length === 0){ }}'
    ,'<dd class="fly-none" style="min-height: 65px; min-width:90px;font-size: 14px;">没有相关数据</dd>'
    ,'{{# } }}'].join('')
  ,elemReply = $('#LAY_replyRank');

  if(elemReply[0]){

    fly.json('/api/top', {
      limit: 20
    }, function(res){
      var html = laytpl(tplReply).render(res);
      elemReply.find('dl').html(html);
    });

  };

  //相册
  if($(window).width() > 750){
    layer.photos({
      photos: '.photos'
      ,zIndex: 9999999999
      ,anim: -1
    });
  } else {
    $('body').on('click', '.photos img', function(e){
      layer.photos({ photos: {"data": [{"src": e.target.src}]},anim:1});
    });
  }


  //搜索
  $('.fly-search').on('click', function(){
    layer.open({
      type: 1
      ,title: false
      ,closeBtn: false
      //,shade: [0.1, '#fff']
      ,shadeClose: true
      ,maxWidth: 10000
      ,skin: 'fly-layer-search'
      ,content: ['<form action="/search">'
        ,'<input autocomplete="off" placeholder="搜索内容，回车跳转" type="text" name="q">'
      ,'</form>'].join('')
      ,success: function(layero){
        var input = layero.find('input');
        input.focus();

        layero.find('form').submit(function(){
          var val = input.val();
          if(val.replace(/\s/g, '') === ''){
            return false;
          }
          input.val(input.val());
      });
      }
    })
  });
  //新消息通知
  fly.newmsg();


  //发送激活邮件
  fly.activate = function(){
    var time = $("#sendtime").val();
    fly.json('/user/resend', {}, function(res){
      if(res.status === 0){
        layer.alert(res.msg, {
          icon: 1
        },function () {
          location.href = res.data.action;
        });
      };
    });
  };
  $('#LAY-activate').on('click', function(){
    fly.activate();
  });

  //点击@
  $('body').on('click', '.fly-aite', function(){
    var othis = $(this), text = othis.text();
    if(othis.attr('href') !== 'javascript:;'){
      return;
    }
    text = text.replace(/^@|（[\s\S]+?）/g, '');
    othis.attr({
      href: '/user/home?username='+ text
      ,target: '_blank'
    });
  });

  //表单提交
  form.on('submit(*)', function(data){
    $(".rest").addClass("layui-btn-disabled");
    $(".rest").attr('disabled', 'disabled');
    var action = $(data.form).attr('action'), button = $(data.elem);
    fly.json(action, data.field, function(res){
        if(res.data.action){
          layer.msg(res.msg,{icon:1,time:2*1000},function () {
            location.href = res.data.action;
          })
        }else{
          layer.msg(res.msg,{icon:1,time:2*1000},function () {
            location.reload();
          })
        }
    },{
      error:function() {
        $(".rest").removeClass("layui-btn-disabled");
        $(".rest").removeAttr("disabled");
      }
    });
    return false;
  });

  //加载特定模块
  if(layui.cache.page && layui.cache.page !== 'index'){
    var extend = {};
    extend[layui.cache.page] = layui.cache.page;
    layui.extend(extend);
    layui.use(layui.cache.page);
  }
  
  //加载IM
  if(!device.android && !device.ios){
    //layui.use('im');
  }

  //加载编辑器
  fly.layEditor({
    elem: '.fly-editor'
  });

  //手机设备的简单适配
  var treeMobile = $('.site-tree-mobile')
  ,shadeMobile = $('.site-mobile-shade')

  treeMobile.on('click', function(){
    $('body').addClass('site-mobile');
  });

  shadeMobile.on('click', function(){
    $('body').removeClass('site-mobile');
  });

  //固定Bar
  util.fixbar({
    bar1: '&#xe642;'
    ,bgcolor: 'rgba(208, 200, 200, 0.7)'
    ,click: function(type){
      if(type === 'bar1'){
        //layer.msg('打开 index.js，开启发表新帖的路径');
          location.href = '/jie/add';
        }
      }
  });

  //验证密码
  form.verify({
    pass: [
      /^[\S]{6,16}$/
      ,'密码为6到16位，且不能出现空格'
    ],
    length:[
        /^[\S\n\s]{6,12}$/
        ,'用户名6到12位'
    ]
  });

  //验证用户是否存在

  $("#L_user").blur(function(){
    var val = $("#L_user").val();
    if(val != ''){
      fly.json('/user/checkuser',{
        accountId:val
      }, function(res){
        if(res.status == 0){
          layer.msg(res.msg,{icon:1,time:2*1000},function () {
          })
        }
      },{
        error:function () {
          $("#L_user").val("");
        }
      });
    }
  })


  //验证邮箱是否存在

  $("#L_email").blur(function(){
    var val = $("#L_email").val();
    if(val != ''){
      fly.json('/user/checkemail',{
        email:val
      }, function(res){
        if(res.status == 0){
          layer.msg(res.msg,{icon:1,time:2*1000},function () {
          })

        }
      },{
        error:function () {
          $("#L_email").val("");
        }
      });
    }

  })

  exports('fly', fly);

});

