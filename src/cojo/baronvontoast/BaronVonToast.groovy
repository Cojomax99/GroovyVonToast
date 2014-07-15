package cojo.baronvontoast

import cojo.baronvontoast.cojo.baronvontoast.client.ClientRegistry
import cojo.baronvontoast.connection.ConnectionImpl

import static java.lang.String.format

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

        if (line.getParam(1) == "o7 baron")
            sendPrivmsg("${line.getParam(0)}", "o7")

        if (line.nick.equals('Corosus'))
            if (new Random().nextInt(3) == 0)
                sendPrivmsg("${line.getParam(0)}", "hey there sexy")

    }

    static void main(String[] args) {
        BotInfo bi = new BotInfo("baron.json")
        NetworkInfo ni = new NetworkInfo("networks.json")
        BaronVonToast toasty = new BaronVonToast(bi, ni)

        ClientRegistry.registerClient(toasty)
        ClientRegistry.startAllConnections()
    }
}
