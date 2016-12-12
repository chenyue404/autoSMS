package autosms.cy.autosms.receiver;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.Toast;

import autosms.cy.autosms.R;
import autosms.cy.autosms.support.SMSCodeUtils;


/**
 * Created by Rikka on 2015/12/7.
 */
public class SMSBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Object[] pdus = (Object[]) intent.getExtras().get("pdus");

        for (Object p : pdus){
            byte[] sms = (byte[]) p;

            SmsMessage message = SmsMessage.createFromPdu(sms);

            String content = message.getMessageBody();
            String number = message.getOriginatingAddress();

            SMSCodeUtils.SMSInfo smsInfo = SMSCodeUtils.findSMSCode(content);

            if (smsInfo != null) {
                // toast
                Toast.makeText(context, String.format(context.getString(R.string.toast_format), smsInfo.code), Toast.LENGTH_LONG).show();

                // 复制到剪贴板
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("SmsCode", smsInfo.code);
                clipboardManager.setPrimaryClip(clipData);

                // 来条通知吗 0.0
                /*
                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setPriority(Notification.PRIORITY_HIGH)
                                .setCategory(Notification.CATEGORY_MESSAGE)
                                .setAutoCancel(true)
                                .setContentTitle(number);

                if (sender.size() > 0)
                    builder.setContentText(String.format(context.getString(R.string.notify_format), sender.get(0), code.get(0)));
                else
                    builder.setContentText(String.format(context.getString(R.string.notify_no_sender_format), code.get(0)));

                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(0, builder.build());*/
            }
        }
    }
}
