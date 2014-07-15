package cojo.baronvontoast

/**
 * Created by Cojomax99 on 7/14/2014.
 *
 * Based almost entirely on the original implementation by mrschlauch
 */
class IRCLine {
    def raw
    def prefix
    def nick
    def username
    def host
    SenderType senderType
    def command

    /** params[0] is channel, params[1] is the message received (FOR PRIVMSG) */
    List<String> params

    private IRCLine(String raw, String prefix, String command, List<String> params) {
        this.raw = raw
        this.prefix = prefix
        this.command = command
        this.params = params

        if (prefix != null) {
            int ex = prefix.indexOf("!")
            int at = prefix.indexOf("@")

            if (ex != -1 && at != -1 && at > ex) {
                this.nick = prefix.substring(0, ex)
                this.username = prefix.substring(ex + 1, at)
                this.host = prefix.substring(at + 1)
                this.senderType = SenderType.USER
            } else {
                this.senderType = SenderType.SERVER
            }
        } else {
            this.senderType = SenderType.NONE
        }
    }

    /**
     * Builds an outgoing IRC line (without the \r\n!) from the given parts.
     *
     * @param command the IRC command.
     * @param params the List of parameters.
     * @param trailing a parameter to be prefixed with : and added at the end.
     * @return a String ready to be sent to the server (if you tack on a \r\n)
     */
    static String assemble(String command, List<String> params, String trailing) {
        StringBuilder sb = new StringBuilder(command)

        params.each {
            sb.append(' ')
            sb.append(it)
        }

        if (trailing != null) {
            sb.append(" :")
            sb.append(trailing)
        }

        sb.toString()
    }

    /**
     * Builds an outgoing IRC line (without the \r\n!) from the given parts.
     * @param command the IRC command.
     * @param params the array of parameters.
     * @return a String ready to be sent to the server (if you tack on a \r\n)
     */
    static String assemble(String command, String... params) {
        assemble(command, Arrays.asList(params), null)
    }

    /**
     * Construct a parsed IRCLine from the given raw incoming line.
     * @param raw the raw incoming line:
     * @return the constructed IRCLine, or null if the line breaks the RFC spec.
     */
    static IRCLine parse(String raw) {
        if (raw.isEmpty()) {
            return null;
        }

        if (raw.endsWith("\r\n")) {
            raw = raw.substring(0, raw.length()-2);
        }

        LinkedList<String> parts = new LinkedList<String>(Arrays.asList(raw
                .split(" ")));

        String prefix = null;

        if (parts.get(0).startsWith(":")) {
            prefix = parts.removeFirst().substring(1);
        }

        String command = parts.removeFirst().toUpperCase();
        List<String> params = new ArrayList<String>();

        StringBuilder sb = null;
        parts.each {
            if (sb != null) {
                sb.append(" ");
                sb.append(it);
            } else if (it.startsWith(":")) {
                sb = new StringBuilder();
                sb.append(it.substring(1));
            } else if (!it.isEmpty()) {
                params.add(it);
            } else {
                // ignore, extra space between params
            }
        }

        if (sb != null) {
            params.add(sb.toString());
        }

        new IRCLine(raw, prefix, command, params);
    }

    String getParam(int i) {
        this.params.get(i);
    }
}
