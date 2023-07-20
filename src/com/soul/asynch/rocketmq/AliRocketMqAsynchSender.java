package com.soul.asynch.rocketmq;

import com.aliyun.openservices.ons.api.SendResult;
import com.soul.asynch.basic.AsynchSender;
import com.soul.publisher.api.PublisherApi;

public class AliRocketMqAsynchSender extends AsynchSender<SendResult> {

	public AliRocketMqAsynchSender(PublisherApi publisher, String message) {
		super(publisher, message);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected SendResult send() throws Exception {
		// TODO Auto-generated method stub
		SendResult result;
		result = (SendResult) publisher.send(message);
		return result;
	}

}
