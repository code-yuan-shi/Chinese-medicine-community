package com.bbgu.zmz.community.service;

import com.bbgu.zmz.community.dto.RegRespObj;
import com.bbgu.zmz.community.dto.ReplyDTO;
import com.bbgu.zmz.community.dto.TopicInfoDTO;
import com.bbgu.zmz.community.mapper.*;
import com.bbgu.zmz.community.model.*;
import com.bbgu.zmz.community.util.StringDate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class TopicService {
    @Autowired
    private TopicinfoMapper topicinfoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private KindMapper kindMapper;
    @Autowired
    private TopicinfoExtMapper topicinfoExtMapper;
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private CommentagreeMapper commentagreeMapper;


    /*
    获取置顶帖子信息，查询4条
     */
    public List<TopicInfoDTO> topicTop(Integer istop,Integer num){
        List<TopicInfoDTO> topicInfoDTOList = new ArrayList<TopicInfoDTO>();
        //获取帖子信息
        TopicinfoExample topicinfoExample = new TopicinfoExample();
        topicinfoExample.createCriteria().andIsTopEqualTo(istop);
        topicinfoExample.setOrderByClause("topic_create desc");
        List<Topicinfo> topicinfos = topicinfoMapper.selectByExampleWithRowbounds(topicinfoExample,new RowBounds(0,num));
        for (Topicinfo topicinfo:topicinfos) {
            TopicInfoDTO topicInfoDTO  =  getDetails(topicinfo);
            topicInfoDTOList.add(topicInfoDTO);
        }
        return topicInfoDTOList;
    }
    /*
    查询一级分类
     */
    public List<Category> findCate(){
        List<Category> categoryList = categoryMapper.selectByExample(new CategoryExample());
        return categoryList;
    }
    /*
    查询二级分类
     */
    public List<Kind> findKind(){
        List<Kind> kindList = kindMapper.selectByExample( new KindExample());
        return kindList;
    }
    /*
    插入帖子信息
     */
    public int addTopic(Topicinfo topicinfo){
        if (topicinfo.getId() != null){
            topicinfoMapper.updateByPrimaryKeySelective(topicinfo);
            return 10;
        }else{
            int result = topicinfoMapper.insertSelective(topicinfo);
            return result;
        }
    }

    /*
    查看详情
     */
    public TopicInfoDTO showDetail(Long id){
        //获得帖子信息
        Topicinfo topicinfo = topicinfoMapper.selectByPrimaryKey(id);
        TopicInfoDTO topicInfoDTO = getDetails(topicinfo);
        return topicInfoDTO;
    }
    /*
    获取帖子相关信息
     */
    public TopicInfoDTO getDetails(Topicinfo topicinfo){
        TopicInfoDTO topicInfoDTO = new TopicInfoDTO();
        topicInfoDTO.setTopicinfo(topicinfo);
        //获取时间
        String time = StringDate.getStringDate(new Date(topicinfo.getTopicCreate()));
        topicInfoDTO.setTime(time);
        //获取用户信息
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(topicinfo.getUserId());
        List<User> users = userMapper.selectByExample(userExample);
        User user = users.get(0);
        topicInfoDTO.setUser(user);
        //获取帖子分类
        Category category = categoryMapper.selectByPrimaryKey(topicinfo.getCategoryId());
        topicInfoDTO.setCategory(category);
        //获取帖子二级分类
        Kind kind = kindMapper.selectByPrimaryKey(topicinfo.getKindId());
        topicInfoDTO.setKind(kind);
        //获得评论总数
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andTopicIdEqualTo(topicinfo.getId());
        Long comment_count = commentMapper.countByExample(commentExample);
        topicInfoDTO.setComment_count(comment_count);
        return topicInfoDTO;
    }
    /*
    更新浏览数
     */
    public void incView(Topicinfo topicinfo){
        topicinfoExtMapper.incView(topicinfo);
    }
    /*
    插入评论
     */
    public Long insertComment(Comment comment){
        commentMapper.insertSelective(comment);
        Long id = comment.getId();
        return id;
    }
    /*
    查询评论
     */
    public List<ReplyDTO> findComment(Long id,Integer page,Integer size,User userinfo){
        List<ReplyDTO> replyDTOList = new ArrayList<ReplyDTO>();
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andTopicIdEqualTo(id);
        Long count = commentMapper.countByExample(commentExample);
        Long totalPage;
        if(count % size ==0){
            totalPage = count / size;
        }else{
            totalPage = count / size + 1;
        }
        if(page < 1){
            page=1;
        }
        if(page > totalPage){
            page = Integer.parseInt(String.valueOf(totalPage));
        }
        Integer offset = size * (page - 1);
       // commentExample.setOrderByClause("comment_create desc");
        List<Comment> commentList = commentMapper.selectByExampleWithRowbounds(commentExample,new RowBounds(offset,size));

       for(Comment comment:commentList){
           ReplyDTO replyDTO = new ReplyDTO();
           String time = StringDate.getStringDate(new Date(comment.getCommentCreate()));
           replyDTO.setComment(comment);
           UserExample userExample = new UserExample();
           userExample.createCriteria().andAccountIdEqualTo(comment.getUserId());
           List<User> users = userMapper.selectByExample(userExample);
           User user = users.get(0);
           //查询是否点赞
           if(userinfo != null){
               CommentagreeExample commentagreeExample = new CommentagreeExample();
               commentagreeExample.createCriteria().andUseridEqualTo(userinfo.getAccountId()).andCommentIdEqualTo(comment.getId());
               List<Commentagree> commentagreeList = commentagreeMapper.selectByExample(commentagreeExample);
               if(commentagreeList.size()>0){
                   replyDTO.setZan(true);
               }else{
                   replyDTO.setZan(false);
               }
           }
           replyDTO.setCount(count);
           replyDTO.setTime(time);
           replyDTO.setUser(user);
           replyDTOList.add(replyDTO);
       }
        return  replyDTOList;
    }
    /*
    查询采纳评论
     */
    public ReplyDTO findAcceptComment(Long id,User userinfo){
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andTopicIdEqualTo(id).andIsAcceptEqualTo(1);
        List<Comment> comments =commentMapper.selectByExample(commentExample);
        if(comments.size() != 0) {
            ReplyDTO replyDTO = new ReplyDTO();
            Comment comment = comments.get(0);
            if(userinfo != null){
                CommentagreeExample commentagreeExample = new CommentagreeExample();
                commentagreeExample.createCriteria().andUseridEqualTo(userinfo.getAccountId()).andCommentIdEqualTo(comment.getId());
                List<Commentagree> commentagreeList = commentagreeMapper.selectByExample(commentagreeExample);
                if(commentagreeList.size()>0){
                    replyDTO.setZan(true);
                }else{
                    replyDTO.setZan(false);
                }
            }
            replyDTO.setComment(comment);
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(comment.getUserId());
            List<User> users = userMapper.selectByExample(userExample);
            User user = users.get(0);
            replyDTO.setCount(99L);
            replyDTO.setUser(user);
            return replyDTO;
        }
        return null;
    }




    /*
    审核帖子
     */
    public void shenhe(Long id){
        Topicinfo topicinfo = new Topicinfo();
        topicinfo.setId(id);
        topicinfo.setStatus(1);
        topicinfoMapper.updateByPrimaryKeySelective(topicinfo);
    }
    /*
    设置置顶、加精状态
     */
    public void setTopAndGood(Long id,Integer rank,String field){
        Topicinfo topicinfo =topicinfoMapper.selectByPrimaryKey(id);
        if(field.equals("stick")){
            topicinfo.setIsTop(rank);
        }else if(field.equals("status")){
            topicinfo.setIsGood(rank);
        }
        topicinfoMapper.updateByPrimaryKeySelective(topicinfo);
    }
    /*
    删除帖子
     */
    public void delTopicAndComment(Long id){
        //删除帖子
        topicinfoMapper.deleteByPrimaryKey(id);
        //删除相关评论
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andTopicIdEqualTo(id);
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        commentMapper.deleteByExample(commentExample);
        //删除相关评论点赞
        for(Comment comment:commentList){
            CommentagreeExample commentagreeExample = new CommentagreeExample();
            commentagreeExample.createCriteria().andCommentIdEqualTo(comment.getId());
            commentagreeMapper.deleteByExample(commentagreeExample);
        }
        //删除相关收藏信息
        CollectExample collectExample = new CollectExample();
        collectExample.createCriteria().andTopicIdEqualTo(id);
        collectMapper.deleteByExample(collectExample);
        //删除相关通知信息
        MessageExample messageExample = new MessageExample();
        messageExample.createCriteria().andTopicIdEqualTo(id);
        messageMapper.deleteByExample(messageExample);
    }
    /*
    点赞
     */
    public RegRespObj jiedaZan(User user,Long id,Boolean ok){
        RegRespObj regRespObj = new RegRespObj();
        if(user != null)
        {
            if(ok == false)//点赞
            {
                Comment comment = commentMapper.selectByPrimaryKey(id);
                comment.setAgreeNum(comment.getAgreeNum() + 1);
                commentMapper.updateByPrimaryKeySelective(comment);
                Commentagree commentagree = new Commentagree();
                commentagree.setCommentId(id);
                commentagree.setUserid(user.getAccountId());
                commentagreeMapper.insertSelective(commentagree);
                regRespObj.setStatus(0);
                regRespObj.setMsg("点赞成功！");
            }else{  //取消点赞
                Comment comment = commentMapper.selectByPrimaryKey(id);
                comment.setAgreeNum(comment.getAgreeNum() - 1);
                commentMapper.updateByPrimaryKeySelective(comment);
                CommentagreeExample commentagreeExample = new CommentagreeExample();
                commentagreeExample.createCriteria().andCommentIdEqualTo(id).andUseridEqualTo(user.getAccountId());
                commentagreeMapper.deleteByExample(commentagreeExample);
                regRespObj.setStatus(0);
                regRespObj.setMsg("取消成功！");
            }
        }else{
            regRespObj.setStatus(1);
            regRespObj.setMsg("请登陆后再操作！");
        }
    return regRespObj;
    }
    /*
    采纳评论
     */

    public RegRespObj acceptComment(Long id,User user){
        RegRespObj regRespObj = new RegRespObj();
        Comment comment = new Comment();
        comment.setId(id);
        comment.setIsAccept(1);
        commentMapper.updateByPrimaryKeySelective(comment);
        comment = commentMapper.selectByPrimaryKey(id);
        Topicinfo topicinfo = new Topicinfo();
        topicinfo.setId(comment.getTopicId());
        topicinfo.setIsEnd(1);
        topicinfoMapper.updateByPrimaryKeySelective(topicinfo);
        regRespObj.setStatus(0);
        return regRespObj;
    }

    /*
    获得编辑评论内容
     */
    public String getCommentContentById(Long id){
        Comment comment = commentMapper.selectByPrimaryKey(id);
        return comment.getContent();
    }

    /*
    提交编辑后的新评论
     */
    public RegRespObj editCommentSub(Long id,String content){
        Comment comment = new Comment();
        comment.setId(id);
        comment.setContent(content);
        comment.setCommentModified(System.currentTimeMillis());
        commentMapper.updateByPrimaryKeySelective(comment);
        RegRespObj regRespObj = new RegRespObj();
        regRespObj.setStatus(0);
        regRespObj.setMsg("评论已更新！");
        return regRespObj;
    }

    /*
    删除评论
     */
    public RegRespObj delComment(Long id) {
        commentMapper.deleteByPrimaryKey(id);
        //删除通知消息
        MessageExample messageExample = new MessageExample();
        messageExample.createCriteria().andCommentIdEqualTo(id);
        messageMapper.deleteByExample(messageExample);
        //删除点赞信息
        CommentagreeExample commentagreeExample = new CommentagreeExample();
        commentagreeExample.createCriteria().andCommentIdEqualTo(id);
        commentagreeMapper.deleteByExample(commentagreeExample);
        RegRespObj regRespObj = new RegRespObj();
        regRespObj.setStatus(0);
        regRespObj.setMsg("删除成功！");
        return regRespObj;
    }
    /*
    修改帖子信息
     */
    public Topicinfo findTopicById(Long id){
        Topicinfo topicinfo = topicinfoMapper.selectByPrimaryKey(id);
        return topicinfo;
    }

    /*
    查询一级分类的帖子
     */
    public List<TopicinfoExt> findCateTopic(Long categoryId,Integer offset,Integer size,String status){
        if(categoryId == 0){
            if(status.equals("all")){
                return topicinfoExtMapper.getAllTopic(offset,size);
            }else if(status.equals("isend")){
                return topicinfoExtMapper.getAllTopicIsEnd(offset,size);
            }else if(status.equals("notend")){
                return topicinfoExtMapper.getAllTopicNotEnd(offset,size);
            }else if(status.equals("isgood")){
                return topicinfoExtMapper.getAllTopicIsGood(offset,size);
            }else{
                return topicinfoExtMapper.getAllTopic(offset,size);
            }

        }else {
            if(status.equals("all")){
                return topicinfoExtMapper.getCateTopic(categoryId,offset,size);
            }else if(status.equals("isend")){
                return topicinfoExtMapper.getCateTopicIsEnd(categoryId,offset,size);
            }else if(status.equals("notend")){
                return topicinfoExtMapper.getCateTopicNotEnd(categoryId,offset,size);
            }else if(status.equals("isgood")){
                return topicinfoExtMapper.getCateTopicIsGood(categoryId,offset,size);
            }else{
                return topicinfoExtMapper.getCateTopic(categoryId,offset,size);
            }

        }
    }
    /*
    查询一级分类帖子的总数
     */
    public Long findCateCount(Long categoryId,String status){
        TopicinfoExample topicinfoExample = new TopicinfoExample();
        if(categoryId == 0){
            if(status.equals("all")){
                return topicinfoMapper.countByExample(new TopicinfoExample());
            }else if(status.equals("isend")){
                topicinfoExample.createCriteria().andIsEndEqualTo(1);
                return topicinfoMapper.countByExample(topicinfoExample);
            }else if(status.equals("notend")){
                topicinfoExample.createCriteria().andIsEndEqualTo(0);
                return topicinfoMapper.countByExample(topicinfoExample);
            }else if(status.equals("isgood")){
                topicinfoExample.createCriteria().andIsGoodEqualTo(1);
                return topicinfoMapper.countByExample(topicinfoExample);
            }else{
                return topicinfoMapper.countByExample(new TopicinfoExample());
            }
        }else{
            if(status.equals("all")){
                topicinfoExample.createCriteria().andCategoryIdEqualTo(categoryId);
                return topicinfoMapper.countByExample(topicinfoExample);
            }else if(status.equals("isend")){
                topicinfoExample.createCriteria().andCategoryIdEqualTo(categoryId).andIsEndEqualTo(1);
                return topicinfoMapper.countByExample(topicinfoExample);
            }else if(status.equals("notend")){
                topicinfoExample.createCriteria().andCategoryIdEqualTo(categoryId).andIsEndEqualTo(0);
                return topicinfoMapper.countByExample(topicinfoExample);
            }else if(status.equals("isgood")){
                topicinfoExample.createCriteria().andCategoryIdEqualTo(categoryId).andIsGoodEqualTo(1);
                return topicinfoMapper.countByExample(topicinfoExample);
            }else{
                topicinfoExample.createCriteria().andCategoryIdEqualTo(categoryId);
                return topicinfoMapper.countByExample(topicinfoExample);
            }
        }

    }

    /*
    查询二级分类的帖子
     */
    public List<TopicinfoExt> findKindTopic(Long categoryId,Long kindId,Integer offset,Integer size,String status){
        if(categoryId == 0){
            if(status.equals("all")){
                return topicinfoExtMapper.getAllTopicByKindId(kindId,offset,size);
            }else if(status.equals("isend")){
                return topicinfoExtMapper.getAllTopicByKindIdIsEnd(kindId,offset,size);
            }else if(status.equals("notend")){
                return topicinfoExtMapper.getAllTopicByKindIdNotEnd(kindId,offset,size);
            }else if(status.equals("isgood")){
                return topicinfoExtMapper.getAllTopicByKindIdIsGood(kindId,offset,size);
            }else{
                return topicinfoExtMapper.getAllTopicByKindId(kindId,offset,size);
            }

        }else {
            if(status.equals("all")){
                return topicinfoExtMapper.getKindTopic(categoryId,kindId,offset,size);
            }else if(status.equals("isend")){
                return topicinfoExtMapper.getKindTopicIsEnd(categoryId,kindId,offset,size);
            }else if(status.equals("notend")){
                return topicinfoExtMapper.getKindTopicNotEnd(categoryId,kindId,offset,size);
            }else if(status.equals("isgood")){
                return topicinfoExtMapper.getKindTopicIsGood(categoryId,kindId,offset,size);
            }else{
                return topicinfoExtMapper.getKindTopic(categoryId,kindId,offset,size);
            }
        }

    }
    /*
    查询二级分类帖子的总数
     */
    public Long findKindCount(Long categoryId,Long kindId,String status){
        TopicinfoExample topicinfoExample = new TopicinfoExample();
        if(categoryId == 0){
            if(status.equals("all")){
                topicinfoExample.createCriteria().andKindIdEqualTo(kindId);
                return topicinfoMapper.countByExample(topicinfoExample);
            }else if(status.equals("isend")){
                topicinfoExample.createCriteria().andKindIdEqualTo(kindId).andIsEndEqualTo(1);
                return topicinfoMapper.countByExample(topicinfoExample);
            }else if(status.equals("notend")){
                topicinfoExample.createCriteria().andKindIdEqualTo(kindId).andIsEndEqualTo(0);
                return topicinfoMapper.countByExample(topicinfoExample);
            }else if(status.equals("isgood")){
                topicinfoExample.createCriteria().andKindIdEqualTo(kindId).andIsGoodEqualTo(1);
                return topicinfoMapper.countByExample(topicinfoExample);
            }else{
                topicinfoExample.createCriteria().andKindIdEqualTo(kindId);
                return topicinfoMapper.countByExample(topicinfoExample);
            }

        }else{
           if(status.equals("all")){
               topicinfoExample.createCriteria().andCategoryIdEqualTo(categoryId).andKindIdEqualTo(kindId);
               return topicinfoMapper.countByExample(topicinfoExample);
           }else if(status.equals("isend")){
               topicinfoExample.createCriteria().andCategoryIdEqualTo(categoryId).andKindIdEqualTo(kindId).andIsEndEqualTo(1);
               return topicinfoMapper.countByExample(topicinfoExample);
           }else if(status.equals("notend")){
               topicinfoExample.createCriteria().andCategoryIdEqualTo(categoryId).andKindIdEqualTo(kindId).andIsEndEqualTo(0);
               return topicinfoMapper.countByExample(topicinfoExample);
           }else if(status.equals("isgood")){
               topicinfoExample.createCriteria().andCategoryIdEqualTo(categoryId).andKindIdEqualTo(kindId).andIsGoodEqualTo(1);
               return topicinfoMapper.countByExample(topicinfoExample);
           }else{
               topicinfoExample.createCriteria().andCategoryIdEqualTo(categoryId).andKindIdEqualTo(kindId);
               return topicinfoMapper.countByExample(topicinfoExample);
           }

        }

    }
    /*
    搜索
     */
    public List<TopicinfoExt> searchTopic(String q,Integer offset, Integer size){
        return topicinfoExtMapper.searchTopic(q,offset,size);
    }

    /*
    统计搜索总数
     */
    public Long searchTopicCount(String q){
        TopicinfoExample topicinfoExample = new TopicinfoExample();
        topicinfoExample.createCriteria().andTitleLike("%"+q+"%");
        return topicinfoMapper.countByExample(topicinfoExample);
    }
}
