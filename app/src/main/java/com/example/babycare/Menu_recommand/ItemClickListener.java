package com.example.babycare.Menu_recommand;

import android.view.MotionEvent;
import android.view.View;

interface ItemClickListener {
    void onClick (View view, int position, boolean isLongClick, MotionEvent motionEvent);
}
