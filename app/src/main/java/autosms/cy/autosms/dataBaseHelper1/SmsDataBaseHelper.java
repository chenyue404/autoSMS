package autosms.cy.autosms.dataBaseHelper1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import autosms.cy.autosms.bean1.SmsBean;
import autosms.cy.autosms.bean1.SmsListBean;

/**
 * Created by chenyue on 2016/9/7 0007.
 */
public class SmsDataBaseHelper {
    public static List getSmsInPhone(Context context) {
        //短信数据库的位置是/data/data/com.android.providers.telephony/dababases/mmssms.db
        //电话数据库的位置是/data/data/data/com.android.providers.contacts/contacts2.db

        List<SmsListBean> smsListBeanList = new ArrayList<>();
        List<SmsBean> smsList = new ArrayList<>();
        SmsBean smsBean;

        final String SMS_URI_ALL = "content://sms/";
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_SEND = "content://sms/sent";
        final String SMS_URI_DRAFT = "content://sms/draft";
        final String SMS_URI_OUTBOX = "content://sms/outbox";
        final String SMS_URI_FAILED = "content://sms/failed";
        final String SMS_URI_QUEUED = "content://sms/queued";

        StringBuilder smsBuilder = new StringBuilder();

        //  _id => 短消息序号 如100
        //  thread_id => 对话的序号 如100，同一个号码则这个数字相等
        //  address => 发件人地址，手机号.如+8613811810000
        //  person => 发件人，返回一个数字就是联系人列表里的序号，陌生人为null
        //  date => 日期  long型。如1256539465022
        //  protocol => 协议 0 SMS_RPOTO, 1 MMS_PROTO
        //  read => 是否阅读 0未读， 1已读
        //  status => 状态 -1接收，0 complete, 64 pending, 128 failed
        //  type => 类型 1是接收到的，2是已发出
        //  body => 短消息内容
        //  service_center => 短信服务中心号码编号。如+8613800755500

        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            String[] projection = new String[]{
                    "_id",
                    "address",
                    "person",
                    "body",
                    "date",
                    "type",
                    "thread_id",
                    "protocol",
                    "read",
                    "status"};
            Cursor cur = context.getContentResolver().query(uri, projection, null, null, "date desc");      // 获取手机内部短信

            if (cur.moveToFirst()) {
                int index_Address = cur.getColumnIndex("address");
                int index_Person = cur.getColumnIndex("person");
                int index_Body = cur.getColumnIndex("body");
                int index_Date = cur.getColumnIndex("date");
                int index_Type = cur.getColumnIndex("type");
                int index_Thread_id = cur.getColumnIndex("thread_id");
                int index_Protocol = cur.getColumnIndex("protocol");
                int index_Read = cur.getColumnIndex("read");
                int index_Status = cur.getColumnIndex("status");
                int index_Service_center = cur.getColumnIndex("service_center");

                do {
                    int person = cur.getInt(index_Person);
                    int protocol = cur.getInt(index_Protocol);
                    int read = cur.getInt(index_Read);
                    int status = cur.getInt(index_Status);
                    int type = cur.getInt(index_Type);
                    long date = cur.getLong(index_Date);
                    String address = cur.getString(index_Address);
                    String body = cur.getString(index_Body);
                    String thread_id = cur.getString(index_Thread_id);


                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date d = new Date(date);
                    String strDate = dateFormat.format(d);

                    smsBuilder.append("[ ");
                    smsBuilder.append(address + ", ");
                    smsBuilder.append(person + ", ");
                    smsBuilder.append(body + ", ");
                    smsBuilder.append(strDate + ", ");
                    smsBuilder.append(type);
                    smsBuilder.append(" ]\n\n");

                    smsBean = new SmsBean();
                    smsBean.setTime(strDate);
                    smsBean.setSmsContext(body);
                    smsBean.setSenderNumber(address);
                    smsBean.setIsReaded(read + "");
                    smsBean.setProtocol(protocol + "");
                    smsBean.setSenderIndex(person + "");
                    smsBean.setSendOrReceive(type + "");
                    smsBean.setThread_id(thread_id);
                    smsBean.setStatus(status + "");

                    Boolean isThisSmsBeanIn = false;
                    SmsListBean smsListBean = new SmsListBean();
                    for (int i = 0; i < smsListBeanList.size(); i++) {
                        smsListBean = smsListBeanList.get(i);
                        if (!TextUtils.isEmpty(smsListBean.getThread_id())
                                && !TextUtils.isEmpty(smsBean.getThread_id())
                                && smsListBean.getThread_id().equals(smsBean.getThread_id())) {
                            isThisSmsBeanIn = true;
                            break;
                        }
                    }
                    if (!isThisSmsBeanIn) {
                        smsListBean = new SmsListBean();
                        smsListBean.setSenderNumber(smsBean.getSenderNumber());
                        smsListBean.setSmsContext(smsBean.getSmsContext());
                        smsListBean.setThread_id(smsBean.getThread_id());
                        smsListBean.setTime(smsBean.getTime());
                        String name = getPeopleNameFromPerson(smsBean.getSenderNumber(), context);
                        smsListBean.setSenderName(name);
                        smsListBean.getSmsBeanList().add(smsBean);
                        smsListBeanList.add(smsListBean);
                    } else {
                        smsListBean.getSmsBeanList().add(smsBean);
                    }

                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                    cur = null;
                }
            } else {
                smsBuilder.append("no result!");
            } // end if

            smsBuilder.append("getSmsInPhone has executed!");

        } catch (SQLiteException ex) {
            Log.d("SQLiteException in getSmsInPhone", ex.getMessage());
        }

        return smsListBeanList;
    }

    // 通过address手机号关联Contacts联系人的显示名字
    private static String getPeopleNameFromPerson(String address, Context context) {
        if (TextUtils.isEmpty(address)) {
            return "no number";
        }

        String strPerson = "";
        String[] projection = new String[]{Phone.DISPLAY_NAME, Phone.NUMBER};

        Uri uri_Person = Uri.withAppendedPath(ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI, address);  // address 手机号过滤
        Cursor cursor = context.getContentResolver().query(uri_Person, projection, null, null, null);

        if (cursor.moveToFirst()) {
            int index_PeopleName = cursor.getColumnIndex(Phone.DISPLAY_NAME);
            String strPeopleName = cursor.getString(index_PeopleName);
            strPerson = strPeopleName;
        }
        cursor.close();

        if (TextUtils.isEmpty(strPerson)) {
            strPerson = address;
        }

        return strPerson;
    }
}
