package autosms.cy.autosms.bean;

import java.io.Serializable;

/**
 * Created by chenyue on 2016/8/29 0029.
 */
public class SmsBean implements Serializable {
    private String senderNumber;
    private String smsContext;
    private String time;
    private String thread_id;
    private String senderIndex;
    private String protocol;
    private String isReaded;
    private String status;
    private String sendOrReceive;
    private String service_center;

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getSenderIndex() {
        return senderIndex;
    }

    public void setSenderIndex(String senderIndex) {
        this.senderIndex = senderIndex;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(String isReaded) {
        this.isReaded = isReaded;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSendOrReceive() {
        return sendOrReceive;
    }

    public void setSendOrReceive(String sendOrReceive) {
        this.sendOrReceive = sendOrReceive;
    }

    public String getService_center() {
        return service_center;
    }

    public void setService_center(String service_center) {
        this.service_center = service_center;
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
