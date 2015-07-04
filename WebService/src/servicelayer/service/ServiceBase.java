package servicelayer.service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServiceBase {

	public final Lock transactionLock = new ReentrantLock();
}
