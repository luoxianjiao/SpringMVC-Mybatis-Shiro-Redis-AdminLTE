package test.nosql.redis.p2p;

import java.util.List;

import cn.itcast.ssm.po.Items;

public class OrderTaskConsumerImpl implements TaskConsumer{

	@Override
	public void handle(List<Object> list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Items item = (Items) list.get(i);
			System.out.println("她是" +item.getName() + "!!!");
		}
		
	}

	
}
