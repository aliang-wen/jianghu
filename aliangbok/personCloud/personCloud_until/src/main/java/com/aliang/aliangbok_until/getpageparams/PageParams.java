package com.aliang.aliangbok_until.getpageparams;

/**
 * @Title：${enclosing_method}
 * @Description: [功能描述]
 * @Param: ${tags}
 * @author: 作者
 * @CreateDate: ${date} ${time}</p>
 * @update: [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class PageParams {
    //必须获取的属性
    private Integer currentPage;//当前页
    private Integer totalNum;//一共有多少条信息
    private Integer pageSize;//单页信息长度一页显示多少行
    private Integer longPage;//最大下标  例如我想先显示10页码在下面
    //需要计算的必要属性
    private Integer pageCount;//总页数
    private Integer lastPage;//前一页
    private Integer nextPage;//下一页
    private Integer startIndex;//分页查询需要的开始坐标
    private Integer endIndex;//分页查询需要的结束坐标

    //计算并获取必要值
    public void culPage(Integer currentPage, Integer totalNum, Integer pageSize) {
        //获取基本的四个值
        this.currentPage = currentPage;//当前在第几页
        this.totalNum = totalNum;//这里是总共多少条
        this.pageSize = pageSize;//这里是当前页面设置显示多少条
        //计算总页数,如果总信息数对单页信息长度取余为零,则取totalNum/pageSize,否则页数加一
        this.pageCount = totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
        //判断前一页和后一页,为其加以限制,即不可小于1 和 不可大于总页数
        this.lastPage = currentPage - 1;
        if(currentPage - 1 < 1){
            this.lastPage = 1;
        }
        this.nextPage = currentPage + 1;
        if(currentPage + 1 > pageCount){
            this.nextPage = pageCount;
        }
        //计算每一页的第一行和最后一行
        this.startIndex = (currentPage - 1)*pageSize + 0;
        this.endIndex = currentPage*pageSize>totalNum?totalNum :currentPage*pageSize;
    }

    //无参构造方法
    public PageParams() {
    }

    //getter方法

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getLongPage() {
        return longPage;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

}
