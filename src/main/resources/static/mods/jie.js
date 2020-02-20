/**

 @Name: 求解板块

 */
 
layui.define('fly', function(exports){

  var $ = layui.jquery;
  var layer = layui.layer;
  var util = layui.util;
  var laytpl = layui.laytpl;
  var form = layui.form;
  var fly = layui.fly;
  var element = layui.element;
  var laypage = layui.laypage;

  
  var gather = {}, dom = {
    jieda: $('#jieda')
    ,content: $('#L_content')
    ,jiedaCount: $('#jiedaCount')
  };

  //监听专栏选择
  form.on('select(column)', function(obj){
    var value = obj.value
        //,elemQuiz = $('#LAY_quiz')
        ,tips = {
      tips: 1
      ,maxWidth: 250
      ,time: 2000
    };
    //elemQuiz.addClass('layui-hide');
/*    if(value === '1'){
      layer.tips('下面的信息将便于您获得更好的答案', obj.othis, tips);
      elemQuiz.removeClass('layui-hide');
    } else */if(value === '2'){
      layer.tips('系统会对【分享】类型的帖子予以飞吻奖励，但我们需要审核，通过后方可展示', obj.othis, tips);
    }
  });

  //求解管理
  gather.jieAdmin = {
    //删求解
    del: function(div){
      layer.confirm('确认删除该帖子吗？', function(index){
        layer.close(index);
        fly.json('/jie/delete', {
          id: div.data('id')
        }, function(res){
          if(res.status === 0){
            layer.msg(res.msg,{icon:1,time:2*1000},function () {
              location.href = '/';
            })
          } else {
            layer.msg(res.msg);
          }
        });
      });
    }
    //设置审核
    ,agr:function (div) {
      layer.confirm('确认通过审核吗？', function(index){
        layer.close(index);
        fly.json('/jie/shenhe',{
          id: div.data('id')
        }, function(res){
          if(res.status === 0){
            layer.msg(res.msg,{icon:1,time:2*1000},function () {
              location.href = '/jie/detail/'+div.data('id');
            })
          } else {
            layer.msg(res.msg);
          }
        });
      });
    }
    //设置置顶、状态
    ,set: function(div){
      var othis = $(this);
      layer.confirm('确认更改状态吗？', function(index){
            layer.close(index);
      fly.json('/jie/setstatus', {
        id: div.data('id')
        ,rank: othis.attr('rank')
        ,field: othis.attr('field')
      }, function(res){
        if(res.status === 0){
          layer.msg(res.msg,{icon:1,time:1*1000},function () {
            location.href = '/jie/detail/'+div.data('id');
          })
        }
      });
      });
    }

    //收藏
    ,collect: function(div){
      var othis = $(this), type = othis.data('type');
      fly.json('/collection/'+ type, {
        cid: div.data('id')
      }, function(res){
        if(res.status == 0){
          layer.msg(res.msg,{icon:1,time:1*1000});
        }
        if(type === 'add'){
          othis.data('type', 'remove').html('取消收藏').addClass('layui-btn-danger');
        } else if(type === 'remove'){
          othis.data('type', 'add').html('收藏').removeClass('layui-btn-danger');
        }
      });
    }
  };

  $('body').on('click', '.jie-admin', function(){
    var othis = $(this), type = othis.attr('type');
    gather.jieAdmin[type] && gather.jieAdmin[type].call(this, othis.parent());
  });

  //异步渲染
  var asyncRender = function(){
    var div = $('.fly-admin-box'), jieAdmin = $('#LAY_jieAdmin');
    //查询帖子是否收藏
    if(jieAdmin[0] && layui.cache.user.uid != -1){
      fly.json('/collection/find', {
        cid: div.data('id')
      }, function(res){
        jieAdmin.append('<span class="layui-btn layui-btn-xs jie-admin '+ (res.data.collection ? 'layui-btn-danger' : '') +'" type="collect" data-type="'+ (res.data.collection ? 'remove' : 'add') +'">'+ (res.data.collection ? '取消收藏' : '收藏') +'</span>');
      });
    }
  }();

  //解答操作
  gather.jiedaActive = {
    zan: function(li){ //赞
      var othis = $(this), ok = othis.hasClass('zanok');
      fly.json('/jie/zan', {
        ok: ok
        ,id: li.data('id')
      }, function(res){
        if(res.status === 0){
          var zans = othis.find('em').html()|0;
          othis[ok ? 'removeClass' : 'addClass']('zanok');
          othis.find('em').html(ok ? (--zans) : (++zans));
          //layer.msg(res.msg,{shift:1,time:1*1000});
        } else {
          layer.msg(res.msg);
        }
      });
    }
    ,reply: function(li){ //回复

      var aite = '@'+ li.find('.fly-detail-user cite').text().replace(/\s/g, '');
      //设置被评论者Id评论
      $("#commentTo").val(li.attr("data-userId"));
      $("#commentid").val(li.attr("data-id"));
      $("#type").val(1);
      //设置@了谁
      $("#cText").focus();

      $("#L_content").text(aite);
      $("#replyTo").val(aite);
      $(".reviewInfo").slideDown("fast");
    }
    ,accept: function(li){ //采纳
      var othis = $(this);
      layer.confirm('是否采纳该回答为最佳答案？', function(index){
        layer.close(index);
        fly.json('/jie/accept', {
          id: li.data('id')
        }, function(res){
          if(res.status === 0){
            $('.jieda-accept').remove();
            $('#weijie').remove();
            $('#tiwen').after('<span class="layui-badge" style="background-color: #5FB878;">已结</span>');
            li.addClass('jieda-daan');
            li.find('.detail-about').append('<i class="iconfont icon-caina" title="最佳答案"></i>');
            location.reload();
          } else {
            layer.msg(res.msg);
          }
        });
      });
    }
    ,edit: function(li){ //编辑
      fly.json('/jie/edit', {
        id: li.data('id')
      }, function(res){
        var data = res.data;
        layer.prompt({
          formType: 2
          ,value: data.content
          ,maxlength: 100000
          ,title: '编辑回帖'
          ,area: ['728px', '300px']
          ,success: function(layero){
            fly.layEditor({
              elem: layero.find('textarea')
            });
          }
        }, function(value, index){
          fly.json('/jie/editsubmit', {
            id: li.data('id')
            ,content: value
          }, function(res){
            if (res.status == 0){
              layer.msg(res.msg,{icon:1,time:1*1000},function () {
                layer.close(index);
                li.find('.detail-body').html(fly.content(value));
              })
            }

          });
        });
      });
    }
    ,del: function(li){ //删除
      layer.confirm('确认删除该回答么？', function(index){
        layer.close(index);
        fly.json('/jie/delcomment', {
          id: li.data('id')
        }, function(res){
          if(res.status === 0){
            layer.msg(res.msg,{icon:1,time:1*1000},function () {
              var count = dom.jiedaCount.text()|0;
              dom.jiedaCount.html(--count);
              li.remove();
              //如果删除了最佳答案
              if(li.hasClass('jieda-daan')){
                $('.jie-status').removeClass('jie-status-ok').text('求解中');
              }
            })

          } else {
            layer.msg(res.msg);
          }
        });
      });    
    }
  };

  $('.jieda-reply span').on('click', function(){
    var othis = $(this), type = othis.attr('type');
    gather.jiedaActive[type].call(this, othis.parents('li'));
  });


   // 评论分页
    var count = $("#count").val(),
        id = parseInt($("#topid").val()),
        contextPath = $("#contextPath").val(),
        pageid = $("#pageid").val(),
        size =  $("#size").val();
    console.log($("#topid").val());
    laypage.render({
      elem: 'test1'
      ,count: count
      ,curr:pageid
      ,limit:size
      ,first: '首页'
      ,last: '尾页'
      ,jump:function (obj,first) {
         var page = obj.curr;
         if(!first){
            location.href = contextPath + id +"?page=" + page;
         }
      }
    });


    //分类分页
  var count = $("#count").val(),
      contextPath = $("#contextPath").val(),
      pageid = $("#pageid").val(),
      size =  $("#size").val(),
      column =  $("#column").val(),
      fenlei =  $("#fenlei").val(),
      status =  $("#status").val();

  laypage.render({
    elem: 'catepage'
    ,count: count
    ,curr:pageid
    ,limit:size
    ,first: '首页'
    ,last: '尾页'
    ,jump:function (obj,first) {
      var page = obj.curr;
      if(!first){
        location.href = "?page=" + page;
      }
    }
  });


  //定位分页
  if(/\?page=/.test(location.href) && !location.hash){
    var replyTop = $('#flyReply').offset().top - 80;
    $('html,body').scrollTop(replyTop);
  }

  $('.fly-filter a').on('click', function(){
    var a = $(this);
    a.addClass('layui-this');
  });

  exports('jie', null);
});