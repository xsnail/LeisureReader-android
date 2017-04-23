package com.xsnail.leisurereader.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.xsnail.leisurereader.R;
import com.xsnail.leisurereader.base.BaseActivity;
import com.xsnail.leisurereader.di.components.AppComponent;
import com.xsnail.leisurereader.utils.LogUtils;

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

    public static final String PDF_PATH = "";
    public static final String PDF_NAME = "";
    private String pdfPath = "";
    private String pdfName = "";


    public static void startActivity(Context context,String pdfPath){
        Intent intent = new Intent(context, PdfReadActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.fromFile(new File(pdfPath)));
        context.startActivity(intent);
    }

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
        if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
            pdfPath = Uri.decode(getIntent().getDataString().replace("file://", ""));
            pdfName = pdfPath.substring(pdfPath.lastIndexOf("/") + 1, pdfPath.lastIndexOf("."));
        }else{
            pdfPath = getPdfPathOnSDCard(getIntent().getStringExtra(PDF_PATH));
            pdfName = getIntent().getStringExtra(PDF_NAME);
        }
        Log.d("test","pdfPath is "+pdfPath+"pdfName is "+pdfName);
        mCommonToolbar.setTitle(pdfName);
        mCommonToolbar.setNavigationIcon(R.drawable.ab_back);
    }

    @Override
    public void initDatas() {
    }

    @Override
    public void initView() {
//        String filePath = "";
//        if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
//            filePath = Uri.decode(getIntent().getDataString().replace("file://", ""));
//        }else{
//            filePath = getIntent().getStringExtra(PDF_PATH);
//        }
        pdfPagerAdapter = new PDFPagerAdapter(mContext,pdfPath);
        pdfViewPager.setAdapter(pdfPagerAdapter);
    }

    protected String getPdfPathOnSDCard(String pdfPath) {
        File f = new File(getExternalFilesDir("pdf"), pdfPath);
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
