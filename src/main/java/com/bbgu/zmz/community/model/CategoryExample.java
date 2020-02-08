package com.bbgu.zmz.community.model;

import java.util.ArrayList;
import java.util.List;

public class CategoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CategoryExample() {
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

        public Criteria andCatenameIsNull() {
            addCriterion("catename is null");
            return (Criteria) this;
        }

        public Criteria andCatenameIsNotNull() {
            addCriterion("catename is not null");
            return (Criteria) this;
        }

        public Criteria andCatenameEqualTo(String value) {
            addCriterion("catename =", value, "catename");
            return (Criteria) this;
        }

        public Criteria andCatenameNotEqualTo(String value) {
            addCriterion("catename <>", value, "catename");
            return (Criteria) this;
        }

        public Criteria andCatenameGreaterThan(String value) {
            addCriterion("catename >", value, "catename");
            return (Criteria) this;
        }

        public Criteria andCatenameGreaterThanOrEqualTo(String value) {
            addCriterion("catename >=", value, "catename");
            return (Criteria) this;
        }

        public Criteria andCatenameLessThan(String value) {
            addCriterion("catename <", value, "catename");
            return (Criteria) this;
        }

        public Criteria andCatenameLessThanOrEqualTo(String value) {
            addCriterion("catename <=", value, "catename");
            return (Criteria) this;
        }

        public Criteria andCatenameLike(String value) {
            addCriterion("catename like", value, "catename");
            return (Criteria) this;
        }

        public Criteria andCatenameNotLike(String value) {
            addCriterion("catename not like", value, "catename");
            return (Criteria) this;
        }

        public Criteria andCatenameIn(List<String> values) {
            addCriterion("catename in", values, "catename");
            return (Criteria) this;
        }

        public Criteria andCatenameNotIn(List<String> values) {
            addCriterion("catename not in", values, "catename");
            return (Criteria) this;
        }

        public Criteria andCatenameBetween(String value1, String value2) {
            addCriterion("catename between", value1, value2, "catename");
            return (Criteria) this;
        }

        public Criteria andCatenameNotBetween(String value1, String value2) {
            addCriterion("catename not between", value1, value2, "catename");
            return (Criteria) this;
        }

        public Criteria andCategoryCreateIsNull() {
            addCriterion("category_create is null");
            return (Criteria) this;
        }

        public Criteria andCategoryCreateIsNotNull() {
            addCriterion("category_create is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryCreateEqualTo(Long value) {
            addCriterion("category_create =", value, "categoryCreate");
            return (Criteria) this;
        }

        public Criteria andCategoryCreateNotEqualTo(Long value) {
            addCriterion("category_create <>", value, "categoryCreate");
            return (Criteria) this;
        }

        public Criteria andCategoryCreateGreaterThan(Long value) {
            addCriterion("category_create >", value, "categoryCreate");
            return (Criteria) this;
        }

        public Criteria andCategoryCreateGreaterThanOrEqualTo(Long value) {
            addCriterion("category_create >=", value, "categoryCreate");
            return (Criteria) this;
        }

        public Criteria andCategoryCreateLessThan(Long value) {
            addCriterion("category_create <", value, "categoryCreate");
            return (Criteria) this;
        }

        public Criteria andCategoryCreateLessThanOrEqualTo(Long value) {
            addCriterion("category_create <=", value, "categoryCreate");
            return (Criteria) this;
        }

        public Criteria andCategoryCreateIn(List<Long> values) {
            addCriterion("category_create in", values, "categoryCreate");
            return (Criteria) this;
        }

        public Criteria andCategoryCreateNotIn(List<Long> values) {
            addCriterion("category_create not in", values, "categoryCreate");
            return (Criteria) this;
        }

        public Criteria andCategoryCreateBetween(Long value1, Long value2) {
            addCriterion("category_create between", value1, value2, "categoryCreate");
            return (Criteria) this;
        }

        public Criteria andCategoryCreateNotBetween(Long value1, Long value2) {
            addCriterion("category_create not between", value1, value2, "categoryCreate");
            return (Criteria) this;
        }

        public Criteria andCategoryModifiedIsNull() {
            addCriterion("category_modified is null");
            return (Criteria) this;
        }

        public Criteria andCategoryModifiedIsNotNull() {
            addCriterion("category_modified is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryModifiedEqualTo(Long value) {
            addCriterion("category_modified =", value, "categoryModified");
            return (Criteria) this;
        }

        public Criteria andCategoryModifiedNotEqualTo(Long value) {
            addCriterion("category_modified <>", value, "categoryModified");
            return (Criteria) this;
        }

        public Criteria andCategoryModifiedGreaterThan(Long value) {
            addCriterion("category_modified >", value, "categoryModified");
            return (Criteria) this;
        }

        public Criteria andCategoryModifiedGreaterThanOrEqualTo(Long value) {
            addCriterion("category_modified >=", value, "categoryModified");
            return (Criteria) this;
        }

        public Criteria andCategoryModifiedLessThan(Long value) {
            addCriterion("category_modified <", value, "categoryModified");
            return (Criteria) this;
        }

        public Criteria andCategoryModifiedLessThanOrEqualTo(Long value) {
            addCriterion("category_modified <=", value, "categoryModified");
            return (Criteria) this;
        }

        public Criteria andCategoryModifiedIn(List<Long> values) {
            addCriterion("category_modified in", values, "categoryModified");
            return (Criteria) this;
        }

        public Criteria andCategoryModifiedNotIn(List<Long> values) {
            addCriterion("category_modified not in", values, "categoryModified");
            return (Criteria) this;
        }

        public Criteria andCategoryModifiedBetween(Long value1, Long value2) {
            addCriterion("category_modified between", value1, value2, "categoryModified");
            return (Criteria) this;
        }

        public Criteria andCategoryModifiedNotBetween(Long value1, Long value2) {
            addCriterion("category_modified not between", value1, value2, "categoryModified");
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