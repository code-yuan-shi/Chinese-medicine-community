package com.bbgu.zmz.community.service;

import com.bbgu.zmz.community.dto.Result;
import com.bbgu.zmz.community.enums.MsgEnum;
import com.bbgu.zmz.community.mapper.*;
import com.bbgu.zmz.community.mapper.TopicinfoExtMapper;
import com.bbgu.zmz.community.model.*;
import com.bbgu.zmz.community.model.TopicinfoExt;
import com.bbgu.zmz.community.util.StringDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private CommentExtMapper commentExtMapper;


    /*
    获取置顶帖子信息 || 获取最新综合帖子信息
     */
    public List<TopicinfoExt> topicTop(Integer istop,Integer offset,Integer size){
        List<TopicinfoExt> topicinfoExtList =  topicinfoExtMapper.getTopTopic(istop,offset,size);
        for(TopicinfoExt topicinfoExt:topicinfoExtList){
            topicinfoExt.setTime(StringDate.getStringDate(new Date(topicinfoExt.getTopicCreate())));
        }
        return topicinfoExtList;
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
    查看帖子详情
     */
    public TopicinfoExt showDetail(Long id){
        TopicinfoExt topicinfoExt = topicinfoExtMapper.getTopicDetails(id);
        topicinfoExt.setTime(StringDate.getStringDate(new Date(topicinfoExt.getTopicCreate())));
        return topicinfoExt;

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
    public List<CommentExt> findComment(Long id,Integer page,Integer size,User userinfo){
        Integer offset = (page -1)*size;
       List<CommentExt> commentExtList = commentExtMapper.getCommentByTid(id,offset,size);

        for(CommentExt commentExt:commentExtList){
            //查询是否点赞
        if(userinfo != null){
            CommentagreeExample commentagreeExample = new CommentagreeExample();
            commentagreeExample.createCriteria().andUseridEqualTo(userinfo.getAccountId()).andCommentIdEqualTo(commentExt.getId());
            List<Commentagree> commentagreeList = commentagreeMapper.selectByExample(commentagreeExample);
            if(commentagreeList.size()>0){
                commentExt.setZan(true);
            }else{
                commentExt.setZan(false);
            }
        }
        //转换时间
        commentExt.setTime(StringDate.getStringDate(new Date(commentExt.getCommentCreate())));
    }
        return commentExtList;
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
    public Result jiedaZan(User user, Long id, Boolean ok){
        if(user != null)
        {
            if(ok == false)//点赞
            {
                CommentagreeExample commentagreeExample = new CommentagreeExample();
                commentagreeExample.createCriteria().andCommentIdEqualTo(id).andUseridEqualTo(user.getAccountId());
                List<Commentagree> commentagreeList  =commentagreeMapper.selectByExample(commentagreeExample);
                if(commentagreeList.size() == 0){
                    commentExtMapper.updateAgreeNumAdd(id);
                    Commentagree commentagree = new Commentagree();
                    commentagree.setCommentId(id);
                    commentagree.setUserid(user.getAccountId());
                    commentagreeMapper.insertSelective(commentagree);
                    return new Result().ok(MsgEnum.ZAN_SUCCESS);
                }else{
                    return new Result().error(MsgEnum.ZAN_FAILE);
                }
            }else{  //取消点赞
                CommentagreeExample commentagreeExample = new CommentagreeExample();
                commentagreeExample.createCriteria().andCommentIdEqualTo(id).andUseridEqualTo(user.getAccountId());
                List<Commentagree> commentagreeList  =commentagreeMapper.selectByExample(commentagreeExample);
                if(commentagreeList.size() == 0){
                    return new Result().error(MsgEnum.ZAN_FAILE);
                }else{
                    commentExtMapper.updateAgreeNumSub(id);
                    commentagreeMapper.deleteByExample(commentagreeExample);
                    return new Result().ok(MsgEnum.ZAN_CANCEL);
                }
            }
        }else{
            return new Result().error(MsgEnum.NOTLOGIN);
        }
    }
    /*
    采纳评论
     */

    public Result acceptComment(Long id){
        Comment comment = new Comment();
        comment.setId(id);
        comment.setIsAccept(1);
        commentMapper.updateByPrimaryKeySelective(comment);
        comment = commentMapper.selectByPrimaryKey(id);
        Topicinfo topicinfo = new Topicinfo();
        topicinfo.setId(comment.getTopicId());
        topicinfo.setIsEnd(1);
        topicinfoMapper.updateByPrimaryKeySelective(topicinfo);
        return new Result().ok(MsgEnum.OK);
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
    public Result editCommentSub(Long id,String content){
        Comment comment = new Comment();
        comment.setId(id);
        comment.setContent(content);
        comment.setCommentModified(System.currentTimeMillis());
        commentMapper.updateByPrimaryKeySelective(comment);
        return new Result().ok(MsgEnum.UPDATE_COMMENT);
    }

    /*
    删除评论
     */
    public Result delComment(Long id) {
        commentMapper.deleteByPrimaryKey(id);
        //删除通知消息
        MessageExample messageExample = new MessageExample();
        messageExample.createCriteria().andCommentIdEqualTo(id);
        messageMapper.deleteByExample(messageExample);
        //删除点赞信息
        CommentagreeExample commentagreeExample = new CommentagreeExample();
        commentagreeExample.createCriteria().andCommentIdEqualTo(id);
        commentagreeMapper.deleteByExample(commentagreeExample);
        return new Result().ok(MsgEnum.DELETE_SUCCESS);
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
    public List<TopicinfoExt> searchTopic(String q, Integer offset, Integer size){
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

    /*
    查询未审帖子
     */
    public List<Topicinfo> findTopicStatus(){
        TopicinfoExample topicinfoExample = new TopicinfoExample();
        topicinfoExample.setOrderByClause("topic_create desc");
        topicinfoExample.createCriteria().andStatusEqualTo(0);
        return  topicinfoMapper.selectByExample(topicinfoExample);
    }
}
