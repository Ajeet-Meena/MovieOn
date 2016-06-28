package com.ajeet_meena.movieon.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.ajeet_meena.movieon.R;
import com.ajeet_meena.movieon.Utils.Montserrat;

/**
 * Created by Ajeet Kumar Meena on 18-06-2016.
 */
public class MontserratButton extends Button {

    private Context context;

    public MontserratButton(Context context) {
        super(context);
        this.context = context;
        if (isInEditMode()) return;
        parseAttributes(null);
    }

    public MontserratButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (isInEditMode()) return;
        parseAttributes(attrs);
    }

    public MontserratButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        if (isInEditMode()) return;
        parseAttributes(attrs);
    }

    private void parseAttributes(AttributeSet attrs) {
        int typeface;
        if (attrs == null) { //Not created from xml
            typeface = Montserrat.MONTSERRAT_REGULAR;
        } else {
            TypedArray values = getContext().obtainStyledAttributes(attrs, R.styleable.MontserratTextView);
            typeface = values.getInt(R.styleable.MontserratTextView_typeface, Montserrat.MONTSERRAT_REGULAR);
            values.recycle();
        }
        setTypeface(getRoboto(typeface));
    }

    public void setRobotoTypeface(int typeface) {
        setTypeface(getRoboto(typeface));
    }

    private Typeface getRoboto(int typeface) {
        return Montserrat.getRoboto(getContext(), typeface);
    }


}
