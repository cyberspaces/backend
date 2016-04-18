/**
 * Created  on 16/1/13.
 */

public class Test {
    public static void main(String[] args){
        TerminalMessage[] a=new TerminalMessage[]{new TerminalMessage("terminal message")};
        Message[] b=a;
        b[0].console();
        a[0].console();

    }
}
