public class Application {

    public static void main(String[] args) {
        GameServer gs = new GameServer(1234);
        gs.serverStart();
        Reactiontest rt = new Reactiontest();
    }
}
