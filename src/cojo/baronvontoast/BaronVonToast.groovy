package cojo.baronvontoast

import cojo.baronvontoast.connection.ConnectionDispatcher

/**
 * Created by Cojomax99 on 7/13/2014.
 */
class BaronVonToast {

    public BaronVonToast() {

    }

    public static void main(String[] args) {
        BaronVonToast toasty = new BaronVonToast()
        toasty.start()
    }

    public void start() {
        BotInfo bi = new BotInfo()
        bi.load()
        NetworkInfo ni = new NetworkInfo()
        ni.load()
        bi.nick = "BaronVonToast"
        ni.hostname = "irc.esper.net"
        ni.port = 6667
        ConnectionDispatcher.createNewConnection(bi, ni)

        ConnectionDispatcher.start()
    }
}
