package com.kc.module_home.navigation;

import com.chad.library.adapter.base.entity.SectionEntity;

public class MySection extends SectionEntity<String> {

    public MySection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MySection(String title, String navigator) {
        super(false, title);
        t = navigator;
    }
}
