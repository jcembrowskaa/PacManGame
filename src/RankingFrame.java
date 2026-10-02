import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class RankingFrame extends JFrame {
    public RankingFrame() {
        setTitle("Ranking");
        setSize(400, 400);
        setLocationRelativeTo(null);


        ArrayList<Ranking> rankingList = Ranking.loadRanking();
        DefaultListModel<String> rankingModel = new DefaultListModel<>();

        for (Ranking oneLine : rankingList) {
            rankingModel.addElement(oneLine.getNickname() + " — " + oneLine.getScore());
        }

        JList<String> rankingListView = new JList<>(rankingModel);
        rankingListView.setFont(new Font("Courier New", Font.BOLD, 16));
        rankingListView.setBackground(new Color(36, 7, 116));
        rankingListView.setForeground(new Color(118, 206, 246));
        rankingListView.setSelectionBackground(new Color(99, 57, 207));
        rankingListView.setSelectionForeground(Color.WHITE);

        JScrollPane rankingScrollPane = new JScrollPane(rankingListView);
        rankingScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rankingScrollPane.setBackground(new Color(36, 7, 116));
        rankingScrollPane.getViewport().setBackground(new Color(36, 7, 116));

        getContentPane().setBackground(new Color(36, 7, 116));
        add(rankingScrollPane);
        setVisible(true);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED &&
                        e.getKeyCode() == KeyEvent.VK_Q &&
                        e.isControlDown() &&
                        e.isShiftDown()) {

                    SwingUtilities.invokeLater(() -> {
                        Window activeWindow = KeyboardFocusManager.getCurrentKeyboardFocusManager().getActiveWindow();
                        if (activeWindow != null) activeWindow.dispose();
                        new menuFrame();
                    });

                    return true;
                }
                return false;
            }
        });
    }
}
