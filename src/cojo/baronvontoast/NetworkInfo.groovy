package cojo.baronvontoast

import groovy.json.JsonSlurper

/**
 * Created by Cojosan on 7/14/2014.
 */
class NetworkInfo {
    /** The port number to connect to on this network */
    int port;

    /** Hostname to connect to on this network */
    String hostname

    /** Channels to join on startup */
    def channels = []

    NetworkInfo(String fileLoc) {
        load(fileLoc)
    }

    /** Load data into variables from the JSON file */
    def load(String fileLoc) {
        def jsonFile = new File(fileLoc)
        Object json = new JsonSlurper().parseText(jsonFile.text)
        List list = (List)json
        Map map = (Map)list.get(0)

        port = map.get("port")
        hostname = map.get("hostname")
        channels = map.get("channels")
    }
}
