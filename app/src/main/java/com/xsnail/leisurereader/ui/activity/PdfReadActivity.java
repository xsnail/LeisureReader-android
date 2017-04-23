package com.xsnail.leisurereader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseActivity;
import com.xsnail.leisurereader.di.components.AppComponent;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;

/**
 * Created by xsnail on 2017/4/8.
 */

public class PdfReadActivity extends BaseActivity {
    @BindView(R.id.pdfViewPager)
    PDFViewPager pdfViewPager;
    private PDFPagerAdapter pdfPagerAdapter;

    public static void startActivity(Context context,String pdfPath){
        Intent intent = new Intent(context, PdfReadActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.fromFile(new File(pdfPath)));
        context.startActivity(intent);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        String filePath = Uri.decode(getIntent().getDataString().replace("file://", ""));
        String pdfName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
        mCommonToolbar.setTitle(pdfName);
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
    }

    @Override
    public void initView() {
        String filePath = "";
        if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
            filePath = Uri.decode(getIntent().getDataString().replace("file://", ""));
        }
        pdfPagerAdapter = new PDFPagerAdapter(mContext,filePath);
        pdfViewPager.setAdapter(pdfPagerAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pdf_read;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pdfPagerAdapter.close();
    }
}
