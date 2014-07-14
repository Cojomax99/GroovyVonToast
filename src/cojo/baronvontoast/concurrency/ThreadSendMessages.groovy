package cojo.baronvontoast.concurrency

import cojo.baronvontoast.connection.ConnectionImpl

import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Created by Cojomax99 on 7/13/2014.
 */
class ThreadSendMessages extends Thread{

    def isRunning

    def bufferedWriter

    /**
     * Queue the messages are added to before they are sent. Thread-safe :D
     * TODO: Make this use Message instead of String
     */
    def ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<String>()

    public ThreadSendMessages(ConnectionImpl connection, BufferedWriter writer) {
        super("ThreadSendMessages")
        isRunning = false
        bufferedWriter = writer
    }

    @Override
    public void run() {
        isRunning = true

        try {
            while(isRunning) {
                sleep(1L)
                while(!messageQueue.isEmpty()) {
                    out(messageQueue.poll().toString() + "\r\n")
                }
                sleep(1L);
            }
        } catch(Exception e) {
            e.printStackTrace()
        }

        try {
            bufferedWriter.close()
        } catch (IOException e) {
            e.printStackTrace()
        }

    }

    def out(String s) {
        try {
            StringBuffer sb = new StringBuffer(s);
            bufferedWriter.write(sb.toString());
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    def dispatch(def message) {
        messageQueue.offer(message)
    }
}
