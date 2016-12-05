package autosms.cy.autosms.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import autosms.cy.autosms.Adapter.MainRecyclerViewAdapter;
import autosms.cy.autosms.Bean.SmsBean;
import autosms.cy.autosms.Bean.SmsListBean;
import autosms.cy.autosms.DataBaseHelper.SmsDataBaseHelper;
import autosms.cy.autosms.R;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MainRecyclerViewAdapter mAdapter;
    private ArrayList<SmsListBean> list;

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private String[] permissionStr = {
            Manifest.permission.READ_CONTACTS
            , Manifest.permission.BROADCAST_SMS
            , Manifest.permission.READ_SMS
            , Manifest.permission.RECEIVE_SMS
            , Manifest.permission.SEND_SMS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkPermissions();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
//                        .setAction("Action", null).show();
                checkPermissions();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.main_rcv);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
//        list = SmsDataBaseHelper.getSmsInPhone(this);
        //创建并设置Adapter
        mAdapter = new MainRecyclerViewAdapter(this, list);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List getSmsData() {
        List list = new ArrayList<SmsBean>();
        for (int i = 0; i < 50; i++) {
            SmsBean smsBean = new SmsBean();
            smsBean.setSenderNumber("num" + i);
            smsBean.setSmsContext("context" + i);
            smsBean.setTime("time" + i);
            list.add(smsBean);
        }
        return list;
    }

    private void getdata() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        list.clear();
        list.addAll(SmsDataBaseHelper.getSmsInPhone(this));
        mAdapter.notifyDataSetChanged();
        progressDialog.cancel();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    permissionStr,
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            getdata();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (Arrays.binarySearch(grantResults, PackageManager.PERMISSION_GRANTED) != -1) {
                Toast.makeText(MainActivity.this, "不给权限用尼玛个锤子", Toast.LENGTH_SHORT).show();
            } else {
                getdata();
            }
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(MainActivity.this, "成功失败", Toast.LENGTH_SHORT).show();
//            } else {
//                // Permission Denied
//                Toast.makeText(MainActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
//            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
