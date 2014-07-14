package cojo.baronvontoast.connection

import cojo.baronvontoast.BotInfo
import cojo.baronvontoast.NetworkInfo

/**
 * Created by Cojomax99 on 7/14/2014.
 */
class ConnectionDispatcher {

    /** Map of name to ConnectionImpl (or bot, maybe later) */
    def static connections = []

    def static ConnectionImpl createNewConnection(BotInfo botInfo, NetworkInfo networkInfo) {
        def cnxn = new ConnectionImpl(botInfo, networkInfo)

        connections.add(cnxn)

        return cnxn
    }

    def static start() {
        connections.each { it.start() }
    }
}
