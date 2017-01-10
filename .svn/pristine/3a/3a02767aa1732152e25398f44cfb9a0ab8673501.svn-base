package core.common.util.push;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javapns.communication.ConnectionToAppleServer;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServer;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import core.common.constants.Constants;
import core.common.util.JsonUtil;
import core.logic.bean.persistence.UserBean;

public class PushUtil
{

	/**
	 * 단일 푸시 전송
	 * 
	 * @param deviceTypeID
	 * @param registrationKey
	 * @param data
	 */
	public static void sendPush(int deviceTypeID, String registrationKey, DataBean data)
	{
		if(deviceTypeID == PushConstants.PUSH_DEVICE_TYPE_AND)
			GCM.sendPush(registrationKey, data);
		else if(deviceTypeID == PushConstants.PUSH_DEVICE_TYPE_IOS)
			APNS.sendPush(registrationKey, data);
	}

	/**
	 * 멀티 푸시 전송
	 * 
	 * @param pushTargetList
	 * @param data
	 */
	public static void sendPush_Multi(List<UserBean> pushTargetList, DataBean data)
	{
		List<String> regKeyList_AND = new ArrayList<String>();
		List<String> regKeyList_IOS = new ArrayList<String>();

		for (UserBean pushTarget : pushTargetList)
		{
			if(pushTarget.getDeviceType() == 1 && !pushTarget.getRegistrationKey().equals(""))
				regKeyList_AND.add(pushTarget.getRegistrationKey());
			else if(pushTarget.getDeviceType() == 2 && !pushTarget.getRegistrationKey().equals(""))
				regKeyList_IOS.add(pushTarget.getRegistrationKey());
		}

		if(regKeyList_AND.size() > 0)
			GCM.sendPush(regKeyList_AND, data);
		else if(regKeyList_IOS.size() > 0)
			APNS.sendPush(regKeyList_IOS, data);
	}

	/**
	 * GCM 클래스
	 * 
	 * @author inyeong
	 *
	 */
	public static class GCM
	{

		private static final boolean	IS_DEBUG			= Constants.IS_DEBUG;

		// Put your Google API Server Key here
		private static final String		GOOGLE_SERVER_KEY	= Constants.GOOGLE_PUSH_KEY;
		private static final String		MESSAGE_KEY			= "message";
		private static final String		COLLAPSE_KEY		= "score_update";

		// timeToLive - 메세지 생존시간
		private static final int		TIME_TO_LIVE		= 1;

		// delayWhileIdle - false : 절전시간과 상관없이 전송 / true : 절전시간일 경우 안감
		private static final boolean	DELAY_WHILE_IDLE	= false;

		/**
		 * 단일 푸시 전송
		 * 
		 * @param registrationKey
		 * @param userMessage
		 */
		public static void sendPush(String registrationKey, DataBean data)
		{
			Result result = null;

			System.out.println(registrationKey);
			
			if(registrationKey == null || registrationKey.equals("") || data == null || data.getMessage().equals(""))
				return;

			try
			{

				// System.out.println("log_path: 1");
				Sender sender = new Sender(GOOGLE_SERVER_KEY);
				// System.out.println("log_path: 2");

				// 메세지 생성
				Builder msgBuilder = new Builder();
				msgBuilder.collapseKey(COLLAPSE_KEY);
				msgBuilder.timeToLive(TIME_TO_LIVE);
				msgBuilder.delayWhileIdle(DELAY_WHILE_IDLE);
				msgBuilder.addData(MESSAGE_KEY, toString_PushData(data));
				Message message = msgBuilder.build();

				// System.out.println("log_path: 3");
				result = sender.send(message, registrationKey, 1);
				// System.out.println("log_path: 4");

				// System.out.println("result: " + result);

				if(IS_DEBUG)
				{
					System.out.println("registrationKey: " + registrationKey);
					System.out.println("userMessage: " + data.getMessage());
					System.out.println("pushStatus : " + result.toString());
				}
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
				System.out.println("RegId required : " + ioe.toString());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("pushStatus : " + e.toString());
			}

		}

		/**
		 * 단체 푸시 전송
		 * 
		 * @param registrationKey
		 * @param userMessage
		 */
		public static void sendPush(List<String> registrationKey, DataBean data)
		{
			MulticastResult result = null;

			if(registrationKey == null || registrationKey.contains("") || data == null || data.getMessage().equals(""))
				return;

			try
			{

				Sender sender = new Sender(GOOGLE_SERVER_KEY);
				// 메세지 생성
				Builder msgBuilder = new Builder();
				msgBuilder.collapseKey(COLLAPSE_KEY);
				msgBuilder.timeToLive(TIME_TO_LIVE);
				msgBuilder.delayWhileIdle(DELAY_WHILE_IDLE);
				msgBuilder.addData(MESSAGE_KEY, toString_PushData(data));
				Message message = msgBuilder.build();

				result = sender.send(message, registrationKey, 1);

				if(IS_DEBUG)
				{
					System.out.println("registrationKey: " + registrationKey);
					System.out.println("userMessage: " + data.getMessage());
					System.out.println("pushStatus : " + result.toString());
				}
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
				System.out.println("RegId required : " + ioe.toString());
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("pushStatus : " + e.toString());
			}

		}

		private static JSONObject toJson_PushData(DataBean data)
		{
			JSONObject json = new JSONObject();
			JsonUtil.Obj.put(json, "message", data.getMessage());
			JsonUtil.Obj.put(json, "isAlim", data.isAlim());
			JsonUtil.Obj.put(json, "pushTypeID", data.getPushTypeID());

			for (Entry<String, String> entry : data.getCustomData().entrySet())
			{
				JsonUtil.Obj.put(json, entry.getKey(), entry.getValue());
			}

			return json;
		}

		private static String toString_PushData(DataBean data)
		{
			return toJson_PushData(data).toString();
		}
	}

	/**
	 * APNS 클래스
	 * 
	 * @author inyeong
	 *
	 */
	public static class APNS
	{

		private static final boolean	IS_DEBUG		= Constants.IS_DEBUG;

		// 테스트
		private static final String		HOST			= "gateway.sandbox.push.apple.com";
		private static final String		KEYSTORE		= "/setting/ssamtong_dev.p12";
		private static final String		KEYSTORE_PASS	= "pass";
		private static final int		PORT			= 2195;

		// 운영
		// private static final String HOST = "gateway.push.apple.com";
		// private static final String KEYSTORE = Constants.PROJECT_PATH + "/setting/ssamtong_dev.p12";
		// private static final String KEYSTORE_PASS = "pass";
		// private static final int PORT = 2195;

		/**
		 * 푸시 전송
		 * 
		 * @param devices
		 * @param msg
		 */
		public static void sendPush(List<String> tokens, DataBean data)
		{

			if(tokens == null || tokens.contains("") || data == null || data.getMessage().equals(""))
				return;

			/* Initialize the push manager's connection to the custom server */
			try
			{

				/* Gather communication details for your custom server */
				AppleNotificationServer customServer = new AppleNotificationServerBasicImpl(KEYSTORE, KEYSTORE_PASS, ConnectionToAppleServer.KEYSTORE_TYPE_PKCS12, HOST, PORT);

				/* Prepare a simple payload to push */
				PushNotificationPayload payload = makePayload(data);

				/* Create a push notification manager */
				PushNotificationManager pushManager = new PushNotificationManager();

				List<Device> devices = new ArrayList<Device>();
				for (String token : tokens)
				{
					devices.add(new BasicDevice(token.replaceAll(" ", "")));
				}

				pushManager.initializeConnection(customServer);

				List<PushedNotification> notifications = pushManager.sendNotifications(payload, devices);

				if(IS_DEBUG)
					printPushedNotifications(notifications);

				pushManager.stopConnection();
			}
			catch (CommunicationException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (KeystoreException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally
			{

			}

		}

		public static void sendPush(String token, DataBean data)
		{

			if(token == null || token.equals("") || data == null || data.getMessage().equals(""))
				return;

			try
			{
				/* Gather communication details for your custom server */
				AppleNotificationServer customServer = new AppleNotificationServerBasicImpl(KEYSTORE, KEYSTORE_PASS, ConnectionToAppleServer.KEYSTORE_TYPE_PKCS12, HOST, PORT);

				/* Prepare a simple payload to push */
				PushNotificationPayload payload = makePayload(data);

				/* Create a push notification manager */
				PushNotificationManager pushManager = new PushNotificationManager();

				Device device = new BasicDevice(token.replaceAll(" ", ""));
				pushManager.initializeConnection(customServer);

				List<PushedNotification> notifications = pushManager.sendNotifications(payload, device);

				if(IS_DEBUG)
					printPushedNotifications(notifications);

				pushManager.stopConnection();
			}
			catch (CommunicationException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (KeystoreException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			finally
			{

			}

		}

		/**
		 * 페이로드 생성
		 * 
		 * @param data
		 * @return
		 */
		private static PushNotificationPayload makePayload(DataBean data)
		{

			PushNotificationPayload payload = PushNotificationPayload.alert(data.getMessage());

			try
			{

				// 푸시 타입 저장
				payload.addCustomDictionary("pushTypeID", data.getPushTypeID());

				if(data.isAlim())
					payload.addSound("default");
				else
					payload.addSound("");

				if(!data.getCustomData().isEmpty())
				{
					for (Entry<String, String> entry : data.getCustomData().entrySet())
					{
						payload.addCustomDictionary(entry.getKey(), entry.getValue());
					}
				}
			}
			catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return payload;
		}

		/**
		 * Print to the console a comprehensive report of all pushed notifications and results.
		 * 
		 * @param notifications
		 *            a raw list of pushed notifications
		 */
		public static void printPushedNotifications(List<PushedNotification> notifications)
		{
			List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
			List<PushedNotification> successfulNotifications = PushedNotification.findSuccessfulNotifications(notifications);
			int failed = failedNotifications.size();
			int successful = successfulNotifications.size();

			if(successful > 0 && failed == 0)
			{
				printPushedNotifications("All notifications pushed successfully (" + successfulNotifications.size() + "):", successfulNotifications);
			}
			else if(successful == 0 && failed > 0)
			{
				printPushedNotifications("All notifications failed (" + failedNotifications.size() + "):", failedNotifications);
			}
			else if(successful == 0 && failed == 0)
			{
				System.out.println("No notifications could be sent, probably because of a critical error");
			}
			else
			{
				printPushedNotifications("Some notifications failed (" + failedNotifications.size() + "):", failedNotifications);
				printPushedNotifications("Others succeeded (" + successfulNotifications.size() + "):", successfulNotifications);
			}
		}

		/**
		 * Print to the console a list of pushed notifications.
		 * 
		 * @param description
		 *            a title for this list of notifications
		 * @param notifications
		 *            a list of pushed notifications to print
		 */
		public static void printPushedNotifications(String description, List<PushedNotification> notifications)
		{
			System.out.println(description);
			for (PushedNotification notification : notifications)
			{
				try
				{
					System.out.println("  " + notification.toString());
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

	}

}
