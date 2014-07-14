package cojo.baronvontoast.connection

import cojo.baronvontoast.BotInfo
import cojo.baronvontoast.NetworkInfo
import cojo.baronvontoast.concurrency.ThreadReceiveMessages
import cojo.baronvontoast.concurrency.ThreadSendMessages

/**
 * Created by Cojomax99 on 7/14/2014.
 */
class ConnectionImpl {
    /** ThreadReceiveMessages thread for receiving messages */
    ThreadReceiveMessages threadReceiveMessages

    /** ThreadSendMessages thread for sending messages */
    ThreadSendMessages threadSendMessages

    def ConnectionImpl(BotInfo botInfo, NetworkInfo networkInfo) {
        final Socket socket

        try {
            socket = new Socket(networkInfo.hostname, networkInfo.port)
            threadReceiveMessages = new ThreadReceiveMessages(this, new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8")))
            threadSendMessages = new ThreadSendMessages(this, new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8")))

        } catch (Exception e) {
            e.printStackTrace()
        } finally {
        }
    }

    def sendToServer(def contents) {
        threadSendMessages.dispatch(contents)
    }

    def start() {
        threadReceiveMessages.start()
        threadSendMessages.start()
    }
}
