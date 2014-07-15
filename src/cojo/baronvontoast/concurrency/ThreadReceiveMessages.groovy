package cojo.baronvontoast.concurrency

import cojo.baronvontoast.IRCLine
import cojo.baronvontoast.connection.ConnectionImpl

/**
 * Created by Cojomax99 on 7/13/2014.
 */
class ThreadReceiveMessages extends Thread {

    def isRunning = false

    BufferedReader bufferedReader

    ConnectionImpl connection

    ThreadReceiveMessages(ConnectionImpl connection, BufferedReader reader) {
        this.bufferedReader = reader
        this.connection = connection
    }

    @Override
    void run() {
        isRunning = true;

        def loggedIn = false
        String msg = null

        while (isRunning) {
            if (!loggedIn) {
                dispatch("USER Toasty  8 * : The Baron")
                dispatch("NICK BaronVonToast")
                loggedIn = true
            }

            sleep(1L)

            if ((msg = bufferedReader.readLine()) == null) {
                isRunning = false
                break
            }

            while ((msg = bufferedReader.readLine()) != null) {
                IRCLine line = IRCLine.parse(msg)

                if (line.getCommand() == "PING") {
                    dispatch("PONG :" + line.getParam(0))
                }

                if (line.getCommand() == "376") {
                    connection.onMOTD(line)
                }

                if (line.getCommand() == "PRIVMSG") {
                    connection.onPRIVMSG(line)
                }
            }
        }
    }

    def dispatch(String message) {
        connection.sendToServer(message)
    }
}
