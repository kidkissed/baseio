package com.generallycloud.nio.buffer.v5;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.ReentrantLock;

import com.generallycloud.nio.buffer.AbstractMemoryPool;
import com.generallycloud.nio.buffer.ByteBuf;

public abstract class MemoryPoolV5 extends AbstractMemoryPool {

//	private Logger			logger	= LoggerFactory.getLogger(MemoryPoolV3.class);

	private MemoryUnitV5[]	memoryUnits;

	private int			mask;

	protected ByteBuffer	memory;

	protected void doStart() throws Exception {

		int capacity = this.capacity;

		this.memory = allocateMemory(capacity * unitMemorySize);

		this.memoryUnits = new MemoryUnitV5[capacity];

		for (int i = 0; i < capacity; i++) {
			MemoryUnitV5 unit = new MemoryUnitV5();
			unit.free = true;
			unit.index = i;
			memoryUnits[i] = unit;
		}
	}

	public void setEmploy(MemoryUnitV5 memoryStart, MemoryUnitV5 memoryEnd,int blockEnd) {
//		logger.debug("setEmploy,start={},end={}", memoryStart.index, memoryEnd.index);
		// new Exception().printStackTrace();

		memoryStart.free = false;
		memoryStart.blockEnd = blockEnd;
		memoryEnd.free = false;
	}

	public ByteBuf allocate(int capacity) {
		
		int size = (capacity + unitMemorySize - 1) / unitMemorySize;

		ReentrantLock lock = this.lock;

		lock.lock();
		
//		logger.info("allocate : {}",capacity);

		try {

			ByteBuf buf = allocate(capacity, mask, this.capacity - size, size);

			if (buf == null) {

				return allocate(capacity, 0, mask - size, size);
			}

			return buf;

		} finally {
			lock.unlock();
		}
	}

	private ByteBuf allocate(int capacity, int start, int end, int size) {

		MemoryUnitV5[] memoryUnits = this.memoryUnits;

		int freeSize = 0;

		for (; start < end;) {

			MemoryUnitV5 unit = memoryUnits[start];

			if (!unit.free) {

				start = unit.blockEnd;
				
				freeSize = 0;

				continue;
			}

			if (++freeSize == size) {

				int blockEnd = unit.index + 1;
				start = blockEnd - size;

				MemoryUnitV5 memoryStart = memoryUnits[start];
				MemoryUnitV5 memoryEnd = unit;
				
				setEmploy(memoryStart, memoryEnd,blockEnd);

				MemoryBlockV5 memoryBlock = new MemoryBlockV5(this, memory.duplicate());

				memoryBlock.setMemory(memoryStart, memoryEnd);

				mask = blockEnd;

				return memoryBlock.produce(capacity);
			}

			start++;
		}

		return null;
	}

	public MemoryPoolV5(int capacity) {
		this(capacity, 1024);
	}

	public MemoryPoolV5(int capacity, int unitMemorySize) {
		super(capacity, unitMemorySize);
	}

	public void release(ByteBuf memoryBlock) {

		MemoryBlockV5 block = (MemoryBlockV5) memoryBlock;

		ReentrantLock lock = this.lock;

		lock.lock();

		try {
			
//			logger.info("release : {}",memoryBlock.capacity());

			MemoryUnitV5 memoryStart = block.memoryStart;
			MemoryUnitV5 memoryEnd = block.memoryEnd;

//			logger.debug("setFree,start={},end={}", memoryStart.index, memoryEnd.index );
			// new Exception().printStackTrace();

			memoryStart.free = true;
			memoryStart.blockEnd = -1;
			memoryEnd.free = true;

		} finally {
			lock.unlock();
		}
	}

	public String toString() {

		MemoryUnitV5[] memoryUnits = this.memoryUnits;

		int free = 0;

		for (MemoryUnitV5 b : memoryUnits) {

			if (b.free) {
				free++;
			}
		}

		StringBuilder b = new StringBuilder();
//		b.append(this.getClass().getSimpleName());
		b.append("[free=");
		b.append(free);
		b.append(",memory=");
		b.append(capacity);
		b.append("]");

		return b.toString();
	}

	public String toSimpleString() {

		MemoryUnitV5[] memoryUnits = this.memoryUnits;

		int free = 0;

		for (MemoryUnitV5 b : memoryUnits) {

			if (b.free) {
				free++;
			}
		}

		StringBuilder b = new StringBuilder();
		b.append("[free=");
		b.append(free);
		b.append(",memory=");
		b.append(capacity);
		b.append("]");

		return b.toString();
	}

}
