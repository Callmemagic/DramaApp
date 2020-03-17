package com.joe.dramaapp.manager;

import com.joe.dramaapp.bean.DramaBean;

import java.util.ArrayList;

/**
 * author: Joe Cheng
 */
public class DramaManager {
    private static DramaManager mInstance;

    private ArrayList<DramaBean> alDramaBean;
    private ArrayList<DramaBean> alFilteredDramaBean;
//    private String lastDramaKeyword;

    public static DramaManager getInstance() {
        if(mInstance == null)
        {
            mInstance = new DramaManager();
        }
        return mInstance;
    }

    public ArrayList<DramaBean> getDramaBeanList() {
        return alDramaBean;
    }

    public void setDramaBeanList(ArrayList<DramaBean> alDramaBean) {
        this.alDramaBean = alDramaBean;
    }

    public ArrayList<DramaBean> getAlFilteredDramaBean() {
        return alFilteredDramaBean;
    }

    public void setAlFilteredDramaBean(ArrayList<DramaBean> alFilteredDramaBean) {
        this.alFilteredDramaBean = alFilteredDramaBean;
    }
}
