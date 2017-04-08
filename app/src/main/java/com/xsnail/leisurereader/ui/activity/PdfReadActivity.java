package com.xsnail.leisurereader.ui.activity;

import android.content.Context;
import android.content.Intent;
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
    public static final String PDF_PATH = "";
    public static final String PDF_NAME = "";
    private String pdfPath;
    private String pdfName;
    private PDFPagerAdapter pdfPagerAdapter;

    public static void startActivity(Context context,String pdfPath,String pdfName){
        Intent intent = new Intent(context,PdfReadActivity.class);
        intent.putExtra(PDF_PATH,pdfPath);
        intent.putExtra(PDF_NAME,pdfName);
        context.startActivity(intent);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolBar() {
        pdfPath = getIntent().getStringExtra(PDF_PATH);
        pdfName = getIntent().getStringExtra(PDF_NAME);
        mCommonToolbar.setTitle(pdfName);
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
    }

    @Override
    public void initView() {
        Log.d("test",getPdfPathOnSDCard(pdfPath));
        pdfPagerAdapter = new PDFPagerAdapter(mContext,getPdfPathOnSDCard(pdfPath));
        pdfViewPager.setAdapter(pdfPagerAdapter);
    }

    protected String getPdfPathOnSDCard(String pdfPath) {
        File f = new File(getExternalFilesDir("pdf"), pdfPath+".pdf");
        return f.getAbsolutePath();
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
