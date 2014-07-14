package cojo.baronvontoast.concurrency

import cojo.baronvontoast.IRCLine
import cojo.baronvontoast.connection.ConnectionImpl

/**
 * Created by Cojomax99 on 7/13/2014.
 */
class ThreadReceiveMessages extends Thread {

    def isRunning = false

    def BufferedReader bufferedReader

    def ConnectionImpl connection

    public ThreadReceiveMessages(ConnectionImpl connection, BufferedReader reader) {
        this.bufferedReader = reader
        this.connection = connection
    }

    @Override
    public void run() {
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
                def IRCLine line = IRCLine.parse(msg)

                println (line.raw)

                if (line.getCommand() == "PING") {
                    dispatch("PONG :" + line.getParam(0))
                }

                if (line.getCommand() == "376") {
                    dispatch("JOIN #TROPICRAFT")
                }
            }
        }
    }

    def dispatch(def message) {
        connection.sendToServer(message)
    }
}
