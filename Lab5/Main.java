package Lab5;

public class Main {
    public static void main(String[] args) {
        MazeFrame frame = new MazeFrame();
        ButtonsFrame buttonsFrame = new ButtonsFrame(frame);

        frame.setVisible(true);
        buttonsFrame.setVisible(true);
    }
}
