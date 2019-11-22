package com.kc.module_home.navigation;

import com.chad.library.adapter.base.entity.SectionEntity;

public class MySection extends SectionEntity<String> {

    public MySection(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public MySection(String s) {
        super(s);
    }
}
