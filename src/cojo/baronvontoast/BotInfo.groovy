package cojo.baronvontoast

import groovy.json.JsonSlurper

/**
 * Created by Cojomax99 on 7/14/2014.
 */
class BotInfo {
    /** Nick used that shows up when sending a message, seen directly by other clients */
    def nick;

    /** Username used when logging on */
    def username;

    /** Real name */
    def realname;

    /** Version number */
    def version;

    /** NickServ password for logging in */
    def nickPass;

    /** Message to be displayed upon quitting the network */
    def quitmsg;

    BotInfo(String fileLoc) {
        load(fileLoc)
    }

    def load(String fileLoc) {
        def jsonFile = new File(fileLoc)
        Object json = new JsonSlurper().parseText(jsonFile.text)
        Map map = (Map)json

        nick = map.get("nick")
        username = map.get("username")
        realname = map.get("realname")
        version = map.get("version")
        nickPass = map.get("nickPass")
        quitmsg = map.get("quitmsg")
    }
}
