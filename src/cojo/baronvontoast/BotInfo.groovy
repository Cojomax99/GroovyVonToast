package cojo.baronvontoast

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

    def load() {
        //TODO load from json
        nick = "BaronVonToast"
        username = "Toasty"
        realname = "TheBaron"
        version = "v0.1"
        nickPass = ""
        quitmsg = "Stay toasty, my friends"
    }
}
