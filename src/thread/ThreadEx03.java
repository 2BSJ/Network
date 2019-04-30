package thread;

public class ThreadEx03 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread thread1 = new DigitThread();
		Thread thread2 = new AlphabetThread();
		Thread thread3 = new Thread(new UppercaseAlphabetRunnableimpl());
		
		thread1.start();
		thread2.start();
		thread3.start();
	}

}
