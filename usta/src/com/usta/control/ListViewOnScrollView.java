package com.usta.control;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class ListViewOnScrollView extends GridView { 

    public ListViewOnScrollView(Context context, AttributeSet attrs) { 
        super(context, attrs); 
    } 

    public ListViewOnScrollView(Context context) { 
        super(context); 
    } 

    public ListViewOnScrollView(Context context, AttributeSet attrs, int defStyle) { 
        super(context, attrs, defStyle); 
    } 
//���Զ���ؼ�ֻ����д��GridView��onMeasure������ʹ�䲻����ֹ�������ScrollViewǶ��ListViewҲ��ͬ���ĵ�������׸���� 
    @Override 
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
        int expandSpec = MeasureSpec.makeMeasureSpec( 
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST); 
        super.onMeasure(widthMeasureSpec, expandSpec); 
    } 
} 