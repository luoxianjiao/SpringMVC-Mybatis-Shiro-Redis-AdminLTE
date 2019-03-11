package test.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.RandomAccess;

/**
 * position用于标记buffer读的位置，limit表示之前写进了多少个byte、char等——现在能读取多少个byte、char等。
 * 1、数据写到buffer
 * 	 1.1 从Channel写到Buffer里：int offs = fileChannel.read(buf);
 *   1.2通过put方法写Buffer的例子：buf.put(127)
 * 2、从Buffer中读取数据
 * 	 2.1 从Buffer读取到Channel fileChannel.write(buf);
 *   2.2 使用get()方法从Buffer中读取数据 buf.get()
 * buf.rewind()  将position设为0，所以可以重读buffer中的所有数据。limit保持不变，仍然能表示能从buffer中读取多少个元素（byte、char等）
 * @author xianjiao.luo
 *
 */
public class TestNio {

	public static void main(String[] args) throws Exception {
		RandomAccessFile af = new RandomAccessFile("C:/Users/Administrator/Desktop/贷款客户设计/Webpage.java", "rw");
		FileChannel fileChannel = af.getChannel();  
		ByteBuffer buf = ByteBuffer.allocate(48);//分配缓冲区（字节为单位）
		int offs = fileChannel.read(buf);//读数据到buffer（从Channel写到Buffer），然后反转buffer，接着再从Buffer中读取数据。
		
		while (offs != -1) {
			System.out.println("Read " + offs);
			buf.flip();//反转，flip()将Buffer从写模式切换到读模式，调用flip()方法会将position设回0，并将limit设为之前的position的值
			while (buf.hasRemaining()) { 
				System.out.println((char) buf.get());
			}
			buf.clear();
			offs = fileChannel.read(buf);
			System.out.println("offs=" + offs);
		}
		af.close();
	}
	
}
