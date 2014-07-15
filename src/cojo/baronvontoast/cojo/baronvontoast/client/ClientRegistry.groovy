package cojo.baronvontoast.cojo.baronvontoast.client

import cojo.baronvontoast.BotInfo
import cojo.baronvontoast.NetworkInfo
import cojo.baronvontoast.connection.ConnectionImpl

/**
 * Created by Cojomax99 on 7/15/2014.
 */
class ClientRegistry {

    static List<ConnectionImpl> connections = []

    static boolean registerClient(ConnectionImpl client) {
        connections.add (client)
    }

    /**
     * Starts all registered connections
     */
    static void startAllConnections() {
        connections.each { it -> it.start() }
    }
}
