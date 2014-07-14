package cojo.baronvontoast

import groovy.json.JsonSlurper

/**
 * Created by Cojosan on 7/14/2014.
 */
class NetworkInfo {
    /** The port number to connect to on this network */
    def int port;

    /** Hostname to connect to on this network */
    def String hostname;

    /** Channels to join on startup */
    def channels;

    /** Load data into variables from the JSON file */
    def load() {
       // def slurper = new JsonSlurper(new File("networks.json"));
        //TODO make this json

        port = 6667
        hostname = "irc.rozznet.net"
        channels = {"#BOT"}
    }
}
