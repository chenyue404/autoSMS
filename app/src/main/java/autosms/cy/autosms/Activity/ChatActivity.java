package autosms.cy.autosms.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;

import autosms.cy.autosms.Adapter.ChatRecyclerViewAdapter;
import autosms.cy.autosms.Bean.SmsBean;
import autosms.cy.autosms.Bean.SmsListBean;
import autosms.cy.autosms.R;

public class ChatActivity extends AppCompatActivity {

    public final static String SMSBEANLIST = "SMSBEANLIST";
    private ArrayList<SmsBean> smsBeenList = new ArrayList<>();
    private SmsListBean smsListBean;
    private RecyclerView recyclerView;
    private RelativeLayout chat_input_rl;
    private EditText content_et;
    private Button send_bt;
    private String userName;
    private String userTel;
    private ChatRecyclerViewAdapter chatRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initView();
        initActionBar();
        getData();
    }

    public void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_action_navigation_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.chat_list_rv);
        chat_input_rl = (RelativeLayout) findViewById(R.id.chat_input_rl);
        content_et = (EditText) findViewById(R.id.content_et);
        send_bt = (Button) findViewById(R.id.send_bt);

        LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras();
        smsListBean = (SmsListBean) bundle.getSerializable(SMSBEANLIST);
        smsBeenList = smsListBean.getSmsBeanList();
        Collections.reverse(smsBeenList);
        userName = smsListBean.getSenderName();

        setTitle(userName);

        chatRecyclerViewAdapter = new ChatRecyclerViewAdapter(this, smsBeenList);
        recyclerView.setAdapter(chatRecyclerViewAdapter);
    }
}
