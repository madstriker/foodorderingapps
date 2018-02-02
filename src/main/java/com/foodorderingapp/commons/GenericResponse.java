package com.foodorderingapp.commons;

/**
 * Created by TOPSHI KREATS on 1/29/2018.
 */
public class GenericResponse<T> {
    private PageModel pageModel;
    private T responseData;

    public PageModel getPageModel() {
        return pageModel;
    }

    public void setPageModel(PageModel pageModel) {
        this.pageModel = pageModel;
    }

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }
}
