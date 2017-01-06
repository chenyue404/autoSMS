package autosms.cy.autosms.bean1;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by chenyue on 2016/9/7 0007.
 */
public class SmsListBean implements Serializable {

    ArrayList<SmsBean> smsBeanList;
    private String senderNumber;
    private String smsContext;
    private String time;
    private String thread_id;
    private String senderName;

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public ArrayList<SmsBean> getSmsBeanList() {
        if (smsBeanList == null) {
            smsBeanList = new ArrayList<SmsBean>();
        }
        return smsBeanList;
    }

    public void setSmsBeanList(ArrayList<SmsBean> smsBeanList) {
        this.smsBeanList = smsBeanList;
    }

    public String getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getSmsContext() {
        return smsContext;
    }

    public void setSmsContext(String smsContext) {
        this.smsContext = smsContext;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
