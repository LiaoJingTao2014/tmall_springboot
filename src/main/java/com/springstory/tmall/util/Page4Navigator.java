package com.springstory.tmall.util;

import java.util.List;

import org.springframework.data.domain.Page;

public class Page4Navigator<T> {

    Page<T> pageFromJPA;

    int navigatePages;

    int totalPages;

    int number;

    long totalElements;

    int size;

    int numberOfElements;

    List<T> content;

    boolean isHasContent;

    boolean first;

    boolean last;

    boolean isHasNext;

    boolean isHasPrevious;

    int[] navigatepageNums;

    public Page4Navigator() {

    }

    public Page4Navigator(Page<T> pageFromJPA, int navigatePages) {
        this.pageFromJPA = pageFromJPA;// 相当与一个list<category>
        this.navigatePages = navigatePages;// 分页下显示多少个导航
        totalPages = pageFromJPA.getTotalPages();// 总页数
        number = pageFromJPA.getNumber();// 当前返回的页码非负
        size = pageFromJPA.getSize();// 当前每页返回数据大小
        totalElements = pageFromJPA.getTotalElements();// 返回元素总数
        numberOfElements = pageFromJPA.getNumberOfElements();// 返回当前页上的元素数
        content = pageFromJPA.getContent();// 将所有数据返回为List
        isHasContent = pageFromJPA.hasContent();// 返回数据是否有内容
        first = pageFromJPA.isFirst();// 返回当前页是否为第一页
        last = pageFromJPA.isLast();// 返回当前页是否为最后一页
        isHasNext = pageFromJPA.hasNext();// 返回如果有下一页
        isHasPrevious = pageFromJPA.hasPrevious();// 返回如果有上一页
        calcNavigatepageNums();
    }

    private void calcNavigatepageNums() {
        int navigatepageNums[];// 分页栏显示的数字
        int totalPages = getTotalPages();
        int number = getNumber();// 当前页码

        if (totalPages <= navigatePages) {
            // 如果总页码小于等于要显示数字的个数 如: totalPages = 4,
            // navigatePages = 5,
            // 则navigatepageNums = [1,2,3,4]
            navigatepageNums = new int[totalPages];
            for (int i = 0; i < totalPages; i++) {
                navigatepageNums[i] = i + 1;
            }
        } else {
            // 如果总页码大于要显示数字的个数 如: totalPages = 11,
            // navigatePages = 5, number = 5
            // 则navigatepageNums = [3, 4, 5, 6, 7]
            navigatepageNums = new int[navigatePages];
            int startNum = number - navigatePages / 2;
            int endNum = number + navigatePages / 2;
            if (startNum < 1) {
                startNum = 1;
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > totalPages) {
                endNum = totalPages;
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }
        this.navigatepageNums = navigatepageNums;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public boolean isHasContent() {
        return isHasContent;
    }

    public void setHasContent(boolean isHasContent) {
        this.isHasContent = isHasContent;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public boolean isHasNext() {
        return isHasNext;
    }

    public void setHasNext(boolean isHasNext) {
        this.isHasNext = isHasNext;
    }

    public boolean isHasPrevious() {
        return isHasPrevious;
    }

    public void setHasPrevious(boolean isHasPrevious) {
        this.isHasPrevious = isHasPrevious;
    }

    public int[] getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

}
