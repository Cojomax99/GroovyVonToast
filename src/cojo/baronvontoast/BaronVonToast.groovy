package cojo.baronvontoast

import cojo.baronvontoast.cojo.baronvontoast.client.ClientRegistry
import cojo.baronvontoast.connection.ConnectionImpl

/**
 * Created by Cojomax99 on 7/13/2014.
 */
class BaronVonToast extends ConnectionImpl {

    BaronVonToast(BotInfo botInfo, NetworkInfo networkInfo) {
        super(botInfo, networkInfo)
    }

    @Override
    void onPRIVMSG(IRCLine line) {
        println ("Params: $line.params")
        println ("Nick: $line.nick")
        if (line.nick.startsWith('Cojo'))
            if (line.getParam(1) == "o7")
                sendToServer("PRIVMSG ${line.getParam(0)} o7")

        if (line.nick.equals('Corosus'))
            if (new Random().nextInt(1) == 0)
                sendToServer("PRIVMSG ${line.getParam(0)} hey there sexy")

    }

    static void main(String[] args) {
        BotInfo bi = new BotInfo("baron.json")
        NetworkInfo ni = new NetworkInfo("networks.json")
        BaronVonToast toasty = new BaronVonToast(bi, ni)

        ClientRegistry.registerClient(toasty)
        ClientRegistry.startAllConnections()
    }
}
