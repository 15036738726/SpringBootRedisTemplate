package com.example.demo.utils;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 提供redis缓存服务的分页组件 传入当前页，currentPage 和每页大小pageSize  自动返回redis中缓存的指定范围数据
 */
@Component
public class SupportCachePageUtil {
    Logger logger = LoggerFactory.getLogger(SupportCachePageUtil.class);

    @Autowired
    private OpsForListUtil opsForListUtil;

    @Autowired
    private OpsForStringUtil opsForStringUtil;

    public List queryPage(String prefix,Integer currentPage,Integer pageSize ,FindAllCallBack callBack) {
        String keyForList = prefix + "_LIST_DATA";
        String keyForString = prefix + "_STRING_TOTAL";

        /**
         * 这个地方最好加锁,控制一下,只允许一个线程去读取并设置缓存
         */
        // 判断是否存在对应的key
        if(!opsForStringUtil.hasKey(keyForList)){
            // 没有 则设置
            List all = callBack.findAll();
            logger.info("缓存中没有数据,执行业务查询回调方法,callBack.findAll(),查询数据库,并设置到缓存");
            // 设置total
            opsForStringUtil.setValue(keyForString,all.size());
            // 设置data
            opsForListUtil.tailPushAll(keyForList,all);
        }else{
            logger.info("缓存中获取数据");
        }
        Integer total = (Integer) opsForStringUtil.getValue(keyForString);
        // 传入参数,创建分页对象,构造方法中自动计算相关分页信息
        MyPage myPage = new MyPage(currentPage,pageSize,total);
        Long startIndex = Long.valueOf(myPage.getStartIndex());
        Long endIndex = Long.valueOf(myPage.getEndIndex());
        List list = opsForListUtil.get(keyForList, startIndex, endIndex);
        return  list;
    }

    /**
     * 专为计算redis取数下标
     */
    @Data
    public class MyPage{
        // 每页大小默认设置为10
        private Integer pageSize;
        // 当前页码
        private Integer currentPage;
        // 总数
        private Integer total;
        // 开始下标
        private Integer startIndex;
        // 结束下标
        private Integer endIndex;
        public MyPage(Integer currentPage, Integer pageSize, Integer total){
            // 当前页码最小值合法化
            if(currentPage<=0){
                currentPage = 1;
            }
            // 页面大小最小值合法化
            if(pageSize<=0){
                pageSize = 10;
            }
            // 数据条数最小值合法化
            if(total<=0){
                total = 0;
            }

            this.currentPage = currentPage;
            this.pageSize = pageSize;
            this.total = total;
            // 数据为0
            if(this.total==0){
                this.startIndex = 0;
                this.endIndex = 0;
                return;
            }
            // 页面大小最大值合法化 一页大小超过总记录条数时  9条数据  pageSize=10时 0-9
            if(this.pageSize>=this.total){
                this.startIndex = 0;
                this.endIndex = this.total-1;
                return;
            }

            // 总页码数
            Integer pageNum = this.total / this.pageSize;
            if(this.total % this.pageSize!=0){
                // 没有除尽 则多加一页
                pageNum ++;
            }

            // 当前页码最大值合法化
            if(pageNum<this.currentPage){
                this.currentPage = pageNum;
            }

            // 计算下标
            this.startIndex = (this.currentPage-1)*this.pageSize;
            this.endIndex = this.pageSize*this.currentPage -1;
            // 最后一页的情况
            if(this.currentPage == pageNum){
                // 最后一页
                this.endIndex = this.total-1;
            }
        }
    }

    public static void main(String[] args) {
        /**
         * ---------正常测试---------
         * 当前页：1,页面大小：10,总记录数：50,计算结果：0-9
         * 当前页：5,页面大小：10,总记录数：50,计算结果：40-49
         * 当前页：6,页面大小：10,总记录数：51,计算结果：50-50 这个没问题，相当于取最后一个数据 （50-51取了2个元素）
         * 当前页：5,页面大小：10,总记录数：49,计算结果：40-48
         * 当前页：4,页面大小：10,总记录数：49,计算结果：30-39
         * 当前页：5,页面大小：10,总记录数：51,计算结果：40-49
         * ---------不合法测试---------
         * 当前页：1,页面大小：10,总记录数：50,计算结果：0-9
         * 当前页：1,页面大小：10,总记录数：50,计算结果：0-9
         * 当前页：1,页面大小：10,总记录数：50,计算结果：0-9
         * 当前页：1,页面大小：10,总记录数：0,计算结果：0-0
         * 当前页：1,页面大小：100,总记录数：50,计算结果：0-49
         * 当前页：5,页面大小：10,总记录数：50,计算结果：40-49
         */

//        System.out.println("---------正常测试---------");
//        MyPage myPage = new MyPage(1L,10L,50L);
//        System.out.println("当前页："+myPage.currentPage+",页面大小："+myPage.getPageSize()+",总记录数："+myPage.getTotal()+",计算结果："+myPage.getStartIndex()+"-"+myPage.getEndIndex());
//        MyPage myPage2 = new MyPage(5L,10L,50L);
//        System.out.println("当前页："+myPage2.currentPage+",页面大小："+myPage2.getPageSize()+",总记录数："+myPage2.getTotal()+",计算结果："+myPage2.getStartIndex()+"-"+myPage2.getEndIndex());
//        MyPage myPage7 = new MyPage(6L,10L,51L);
//        System.out.println("当前页："+myPage7.currentPage+",页面大小："+myPage7.getPageSize()+",总记录数："+myPage7.getTotal()+",计算结果："+myPage7.getStartIndex()+"-"+myPage7.getEndIndex());
//        MyPage myPage8 = new MyPage(5L,10L,49L);
//        System.out.println("当前页："+myPage8.currentPage+",页面大小："+myPage8.getPageSize()+",总记录数："+myPage8.getTotal()+",计算结果："+myPage8.getStartIndex()+"-"+myPage8.getEndIndex());
//        MyPage myPage11 = new MyPage(4L,10L,49L);
//        System.out.println("当前页："+myPage11.currentPage+",页面大小："+myPage11.getPageSize()+",总记录数："+myPage11.getTotal()+",计算结果："+myPage11.getStartIndex()+"-"+myPage11.getEndIndex());
//        MyPage myPage12 = new MyPage(5L,10L,51L);
//        System.out.println("当前页："+myPage12.currentPage+",页面大小："+myPage12.getPageSize()+",总记录数："+myPage12.getTotal()+",计算结果："+myPage12.getStartIndex()+"-"+myPage12.getEndIndex());
//
//
//        System.out.println("---------不合法测试---------");
//        MyPage myPage3 = new MyPage(0L,10L,50L);
//        System.out.println("当前页："+myPage3.currentPage+",页面大小："+myPage3.getPageSize()+",总记录数："+myPage3.getTotal()+",计算结果："+myPage3.getStartIndex()+"-"+myPage3.getEndIndex());
//        MyPage myPage4 = new MyPage(-1L,10L,50L);
//        System.out.println("当前页："+myPage4.currentPage+",页面大小："+myPage4.getPageSize()+",总记录数："+myPage4.getTotal()+",计算结果："+myPage4.getStartIndex()+"-"+myPage4.getEndIndex());
//        MyPage myPage5 = new MyPage(1L,-10L,50L);
//        System.out.println("当前页："+myPage5.currentPage+",页面大小："+myPage5.getPageSize()+",总记录数："+myPage5.getTotal()+",计算结果："+myPage5.getStartIndex()+"-"+myPage5.getEndIndex());
//        MyPage myPage6 = new MyPage(1L,10L,-50L);
//        System.out.println("当前页："+myPage6.currentPage+",页面大小："+myPage6.getPageSize()+",总记录数："+myPage6.getTotal()+",计算结果："+myPage6.getStartIndex()+"-"+myPage6.getEndIndex());
//        MyPage myPage9 = new MyPage(1L,100L,50L);
//        System.out.println("当前页："+myPage9.currentPage+",页面大小："+myPage9.getPageSize()+",总记录数："+myPage9.getTotal()+",计算结果："+myPage9.getStartIndex()+"-"+myPage9.getEndIndex());
//        MyPage myPage10 = new MyPage(100L,10L,50L);
//        System.out.println("当前页："+myPage10.currentPage+",页面大小："+myPage10.getPageSize()+",总记录数："+myPage10.getTotal()+",计算结果："+myPage10.getStartIndex()+"-"+myPage10.getEndIndex());
//
    }
}
