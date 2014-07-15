package cojo.baronvontoast.connection

import cojo.baronvontoast.BotInfo
import cojo.baronvontoast.IRCLine
import cojo.baronvontoast.NetworkInfo
import cojo.baronvontoast.concurrency.ThreadReceiveMessages
import cojo.baronvontoast.concurrency.ThreadSendMessages

/**
 * Created by Cojomax99 on 7/14/2014.
 */
abstract class ConnectionImpl {
    /** ThreadReceiveMessages thread for receiving messages */
    ThreadReceiveMessages threadReceiveMessages

    /** ThreadSendMessages thread for sending messages */
    ThreadSendMessages threadSendMessages

    /** Object containing all relevant info pertaining to this client */
    BotInfo botInfo

    /** Object containing all relevant info to the network this client is connected to */
    NetworkInfo networkInfo

    def ConnectionImpl(BotInfo botInfo, NetworkInfo networkInfo) {

        this.botInfo = botInfo
        this.networkInfo = networkInfo

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

    /**
     * What task to perform when the MOTD is received from the server
     * Usually this is when channel joining is performed, as well as any other startup tasks.
     * Implementers who wish to perform additional startup behavior are encouraged to call super.onMOTD
     * in their overridden implementation.
     *
     * @param line IRCLine object containing the MOTD sent, just in case they want it
     */
    def onMOTD(IRCLine line) {
        networkInfo.channels.each { it -> sendToServer("JOIN " + it)}
    }

    /**
     * What task(s) to perform when a PRIVMSG is received
     * @param line IRCLine object containing the PRIVMSG sent
     */
    abstract void onPRIVMSG(IRCLine line)

    def sendToServer = { String contents ->
        threadSendMessages.dispatch(contents)
    }

    final sendPrivmsg(String channel, String msg) {
        println IRCLine.privmsg(channel, msg)
        sendToServer IRCLine.privmsg(channel, msg)
    }

    def start() {
        threadReceiveMessages.start()
        threadSendMessages.start()
    }
}
