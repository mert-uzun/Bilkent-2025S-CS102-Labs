package Lab5;

public class Main {
    public static void main(String[] args) {
        MazeFrame frame = new MazeFrame();
        ButtonsFrame buttonsFrame = new ButtonsFrame(frame);

        frame.setLocation(450,160);
        frame.setVisible(true);
        buttonsFrame.setLocation(850, 160);
        buttonsFrame.setVisible(true);
    }
}
