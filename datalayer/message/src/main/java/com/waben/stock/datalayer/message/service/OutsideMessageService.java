package com.waben.stock.datalayer.message.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waben.stock.datalayer.message.entity.MessageReceipt;
import com.waben.stock.datalayer.message.entity.Messaging;
import com.waben.stock.datalayer.message.entity.OutsidePushConfig;
import com.waben.stock.datalayer.message.repository.MessageReceiptDao;
import com.waben.stock.datalayer.message.repository.MessagingDao;
import com.waben.stock.datalayer.message.repository.OutsidePushConfigDao;
import com.waben.stock.interfaces.enums.MessageType;
import com.waben.stock.interfaces.enums.OutsideMessageType;
import com.waben.stock.interfaces.enums.RedisCacheKeyType;
import com.waben.stock.interfaces.enums.ResourceType;
import com.waben.stock.interfaces.pojo.message.OutsideMessage;

@Service
public class OutsideMessageService {

	@Autowired
	private JiguangService jiguangService;

	@Autowired
	private MessagingDao messagingDao;

	@Autowired
	private MessageReceiptDao messageReceiptDao;
	
	@Autowired
	private OutsidePushConfigDao outsidePushConfigDao;

	@Autowired
	private RedisCache redisCache;
	
	public void send(OutsideMessage message) {
		String registrationId = redisCache.get(String.format(RedisCacheKeyType.AppRegistrationId.getKey(), message.getPublisherId()));
		OutsidePushConfig config = null;
		if(registrationId.indexOf("_") > 0) {
			String[] strArr = registrationId.split("_");
			registrationId = strArr[2];
			config = outsidePushConfigDao.retrieveByDeviceTypeAndShellIndex(Integer.parseInt(strArr[0]), Integer.parseInt(strArr[1]));
		} else {
			config = outsidePushConfigDao.getDefaultConfig();
		}
		if (config != null && registrationId != null && !"".equals(registrationId.trim())) {
			jiguangService.pushSingleDevice(registrationId, message.getTitle(), message.getContent(),
					message.getExtras(), config != null ? config.getAppKey() : null, config != null ? config.getMasterSecret() : null);
			// 保存消息
			Messaging messaging = new Messaging();
			messaging.setIsOutside(true);
			messaging.setOutsideMsgType(OutsideMessageType.getByType(message.getExtras().get("type")));
			messaging.setCreateTime(new Date());
			messaging.setTitle(message.getTitle());
			messaging.setType(MessageType.POIT);
			if (message.getExtras() != null && message.getExtras().get("resourceType") != null) {
				messaging.setResourceType(ResourceType.getByIndex(message.getExtras().get("resourceType")));
				messaging.setResourceId(Long.parseLong(message.getExtras().get("resourceId")));
			}
			messaging.setContent(message.getExtras().get("content"));
			messagingDao.create(messaging);
			MessageReceipt receipt = new MessageReceipt();
			receipt.setMessage(messaging);
			receipt.setRecipient(String.valueOf(message.getPublisherId()));
			receipt.setState(false);
			messageReceiptDao.create(receipt);
		}
	}

}
