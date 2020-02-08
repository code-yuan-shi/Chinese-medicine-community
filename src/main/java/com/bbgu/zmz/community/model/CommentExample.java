package com.bbgu.zmz.community.model;

import java.util.ArrayList;
import java.util.List;

public class CommentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CommentExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTopicIdIsNull() {
            addCriterion("topic_id is null");
            return (Criteria) this;
        }

        public Criteria andTopicIdIsNotNull() {
            addCriterion("topic_id is not null");
            return (Criteria) this;
        }

        public Criteria andTopicIdEqualTo(Long value) {
            addCriterion("topic_id =", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotEqualTo(Long value) {
            addCriterion("topic_id <>", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdGreaterThan(Long value) {
            addCriterion("topic_id >", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdGreaterThanOrEqualTo(Long value) {
            addCriterion("topic_id >=", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdLessThan(Long value) {
            addCriterion("topic_id <", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdLessThanOrEqualTo(Long value) {
            addCriterion("topic_id <=", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdIn(List<Long> values) {
            addCriterion("topic_id in", values, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotIn(List<Long> values) {
            addCriterion("topic_id not in", values, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdBetween(Long value1, Long value2) {
            addCriterion("topic_id between", value1, value2, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotBetween(Long value1, Long value2) {
            addCriterion("topic_id not between", value1, value2, "topicId");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andCommentCreateIsNull() {
            addCriterion("comment_create is null");
            return (Criteria) this;
        }

        public Criteria andCommentCreateIsNotNull() {
            addCriterion("comment_create is not null");
            return (Criteria) this;
        }

        public Criteria andCommentCreateEqualTo(Long value) {
            addCriterion("comment_create =", value, "commentCreate");
            return (Criteria) this;
        }

        public Criteria andCommentCreateNotEqualTo(Long value) {
            addCriterion("comment_create <>", value, "commentCreate");
            return (Criteria) this;
        }

        public Criteria andCommentCreateGreaterThan(Long value) {
            addCriterion("comment_create >", value, "commentCreate");
            return (Criteria) this;
        }

        public Criteria andCommentCreateGreaterThanOrEqualTo(Long value) {
            addCriterion("comment_create >=", value, "commentCreate");
            return (Criteria) this;
        }

        public Criteria andCommentCreateLessThan(Long value) {
            addCriterion("comment_create <", value, "commentCreate");
            return (Criteria) this;
        }

        public Criteria andCommentCreateLessThanOrEqualTo(Long value) {
            addCriterion("comment_create <=", value, "commentCreate");
            return (Criteria) this;
        }

        public Criteria andCommentCreateIn(List<Long> values) {
            addCriterion("comment_create in", values, "commentCreate");
            return (Criteria) this;
        }

        public Criteria andCommentCreateNotIn(List<Long> values) {
            addCriterion("comment_create not in", values, "commentCreate");
            return (Criteria) this;
        }

        public Criteria andCommentCreateBetween(Long value1, Long value2) {
            addCriterion("comment_create between", value1, value2, "commentCreate");
            return (Criteria) this;
        }

        public Criteria andCommentCreateNotBetween(Long value1, Long value2) {
            addCriterion("comment_create not between", value1, value2, "commentCreate");
            return (Criteria) this;
        }

        public Criteria andCommentModifiedIsNull() {
            addCriterion("comment_modified is null");
            return (Criteria) this;
        }

        public Criteria andCommentModifiedIsNotNull() {
            addCriterion("comment_modified is not null");
            return (Criteria) this;
        }

        public Criteria andCommentModifiedEqualTo(Long value) {
            addCriterion("comment_modified =", value, "commentModified");
            return (Criteria) this;
        }

        public Criteria andCommentModifiedNotEqualTo(Long value) {
            addCriterion("comment_modified <>", value, "commentModified");
            return (Criteria) this;
        }

        public Criteria andCommentModifiedGreaterThan(Long value) {
            addCriterion("comment_modified >", value, "commentModified");
            return (Criteria) this;
        }

        public Criteria andCommentModifiedGreaterThanOrEqualTo(Long value) {
            addCriterion("comment_modified >=", value, "commentModified");
            return (Criteria) this;
        }

        public Criteria andCommentModifiedLessThan(Long value) {
            addCriterion("comment_modified <", value, "commentModified");
            return (Criteria) this;
        }

        public Criteria andCommentModifiedLessThanOrEqualTo(Long value) {
            addCriterion("comment_modified <=", value, "commentModified");
            return (Criteria) this;
        }

        public Criteria andCommentModifiedIn(List<Long> values) {
            addCriterion("comment_modified in", values, "commentModified");
            return (Criteria) this;
        }

        public Criteria andCommentModifiedNotIn(List<Long> values) {
            addCriterion("comment_modified not in", values, "commentModified");
            return (Criteria) this;
        }

        public Criteria andCommentModifiedBetween(Long value1, Long value2) {
            addCriterion("comment_modified between", value1, value2, "commentModified");
            return (Criteria) this;
        }

        public Criteria andCommentModifiedNotBetween(Long value1, Long value2) {
            addCriterion("comment_modified not between", value1, value2, "commentModified");
            return (Criteria) this;
        }

        public Criteria andAgreeNumIsNull() {
            addCriterion("agree_num is null");
            return (Criteria) this;
        }

        public Criteria andAgreeNumIsNotNull() {
            addCriterion("agree_num is not null");
            return (Criteria) this;
        }

        public Criteria andAgreeNumEqualTo(Long value) {
            addCriterion("agree_num =", value, "agreeNum");
            return (Criteria) this;
        }

        public Criteria andAgreeNumNotEqualTo(Long value) {
            addCriterion("agree_num <>", value, "agreeNum");
            return (Criteria) this;
        }

        public Criteria andAgreeNumGreaterThan(Long value) {
            addCriterion("agree_num >", value, "agreeNum");
            return (Criteria) this;
        }

        public Criteria andAgreeNumGreaterThanOrEqualTo(Long value) {
            addCriterion("agree_num >=", value, "agreeNum");
            return (Criteria) this;
        }

        public Criteria andAgreeNumLessThan(Long value) {
            addCriterion("agree_num <", value, "agreeNum");
            return (Criteria) this;
        }

        public Criteria andAgreeNumLessThanOrEqualTo(Long value) {
            addCriterion("agree_num <=", value, "agreeNum");
            return (Criteria) this;
        }

        public Criteria andAgreeNumIn(List<Long> values) {
            addCriterion("agree_num in", values, "agreeNum");
            return (Criteria) this;
        }

        public Criteria andAgreeNumNotIn(List<Long> values) {
            addCriterion("agree_num not in", values, "agreeNum");
            return (Criteria) this;
        }

        public Criteria andAgreeNumBetween(Long value1, Long value2) {
            addCriterion("agree_num between", value1, value2, "agreeNum");
            return (Criteria) this;
        }

        public Criteria andAgreeNumNotBetween(Long value1, Long value2) {
            addCriterion("agree_num not between", value1, value2, "agreeNum");
            return (Criteria) this;
        }

        public Criteria andIsAcceptIsNull() {
            addCriterion("is_accept is null");
            return (Criteria) this;
        }

        public Criteria andIsAcceptIsNotNull() {
            addCriterion("is_accept is not null");
            return (Criteria) this;
        }

        public Criteria andIsAcceptEqualTo(Integer value) {
            addCriterion("is_accept =", value, "isAccept");
            return (Criteria) this;
        }

        public Criteria andIsAcceptNotEqualTo(Integer value) {
            addCriterion("is_accept <>", value, "isAccept");
            return (Criteria) this;
        }

        public Criteria andIsAcceptGreaterThan(Integer value) {
            addCriterion("is_accept >", value, "isAccept");
            return (Criteria) this;
        }

        public Criteria andIsAcceptGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_accept >=", value, "isAccept");
            return (Criteria) this;
        }

        public Criteria andIsAcceptLessThan(Integer value) {
            addCriterion("is_accept <", value, "isAccept");
            return (Criteria) this;
        }

        public Criteria andIsAcceptLessThanOrEqualTo(Integer value) {
            addCriterion("is_accept <=", value, "isAccept");
            return (Criteria) this;
        }

        public Criteria andIsAcceptIn(List<Integer> values) {
            addCriterion("is_accept in", values, "isAccept");
            return (Criteria) this;
        }

        public Criteria andIsAcceptNotIn(List<Integer> values) {
            addCriterion("is_accept not in", values, "isAccept");
            return (Criteria) this;
        }

        public Criteria andIsAcceptBetween(Integer value1, Integer value2) {
            addCriterion("is_accept between", value1, value2, "isAccept");
            return (Criteria) this;
        }

        public Criteria andIsAcceptNotBetween(Integer value1, Integer value2) {
            addCriterion("is_accept not between", value1, value2, "isAccept");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}