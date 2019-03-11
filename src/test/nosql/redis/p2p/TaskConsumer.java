package test.nosql.redis.p2p;

import java.util.List;

public interface TaskConsumer {

	public abstract void handle(List<Object> list) throws Exception;
	
}
