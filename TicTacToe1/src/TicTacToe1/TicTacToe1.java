
package TicTacToe1;

// File: TicTacToe.java
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe1 implements ActionListener {
    private JFrame frame;
    private JPanel textPanel;
    private JPanel boardPanel;
    private JLabel textLabel;
    private JButton[][] buttons;
    
    private String playerX = "X";
    private String playerO = "O";
    private String currentPlayer = playerX;
    private boolean gameOver = false;
    
    public TicTacToe1() {
        // Inisialisasi frame
        frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 650);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        
        // Inisialisasi panel teks
        textPanel = new JPanel();
        textPanel.setBackground(new Color(25, 25, 25));
        textPanel.setPreferredSize(new Dimension(600, 100));
        
        textLabel = new JLabel();
        textLabel.setForeground(new Color(255, 255, 255));
        textLabel.setFont(new Font("Arial", Font.BOLD, 36));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Giliran " + currentPlayer);
        textPanel.add(textLabel);
        
        // Inisialisasi panel papan game
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(new Color(150, 150, 150));
        
        buttons = new JButton[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton button = new JButton();
                buttons[r][c] = button;
                boardPanel.add(button);
                
                button.setBackground(new Color(50, 50, 50));
                button.setForeground(Color.WHITE);
                button.setFont(new Font("Arial", Font.BOLD, 120));
                button.setFocusable(false);
                button.addActionListener(this);
            }
        }
        
        frame.add(textPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;
        
        JButton button = (JButton) e.getSource();
        
        // Periksa apakah tombol kosong
        if (button.getText().equals("")) {
            button.setText(currentPlayer);
            
            // Set warna berdasarkan pemain
            if (currentPlayer.equals(playerX)) {
                button.setForeground(new Color(255, 85, 85)); // Merah untuk X
            } else {
                button.setForeground(new Color(85, 85, 255)); // Biru untuk O
            }
            
            // Periksa pemenang
            checkWinner();
            
            if (!gameOver) {
                // Ganti pemain
                currentPlayer = currentPlayer.equals(playerX) 
                ? playerO : playerX;
                textLabel.setText("Giliran " + 
                currentPlayer);
            }
        }
    }
    
    private void checkWinner() {
        // Periksa baris horizontal
        for (int r = 0; r < 3; r++) {
            if (buttons[r][0].getText().equals(buttons[r][1].getText()) &&
                buttons[r][1].getText().equals(buttons[r][2].getText()) &&
                !buttons[r][0].getText().equals("")) {
                
                // Highlight baris pemenang
                for (int i = 0; i < 3; i++) {
                    buttons[r][i].setBackground(new Color(0, 255, 0));
                }
                gameOver(buttons[r][0].getText());
                return;
            }
        }
        
        // Periksa kolom vertikal
        for (int c = 0; c < 3; c++) {
            if (buttons[0][c].getText().equals(buttons[1][c].getText()) &&
                buttons[1][c].getText().equals(buttons[2][c].getText()) &&
                !buttons[0][c].getText().equals("")) {
                
                // Highlight kolom pemenang
                for (int i = 0; i < 3; i++) {
                    buttons[i][c].setBackground(new Color(0, 255, 0));
                }
                gameOver(buttons[0][c].getText());
                return;
            }
        }
        
        // Periksa diagonal kiri atas ke kanan bawah
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
            buttons[1][1].getText().equals(buttons[2][2].getText()) &&
            !buttons[0][0].getText().equals("")) {
            
            // Highlight diagonal pemenang
            for (int i = 0; i < 3; i++) {
                buttons[i][i].setBackground(new Color(0, 255, 0));
            }
            gameOver(buttons[0][0].getText());
            return;
        }
        
        // Periksa diagonal kanan atas ke kiri bawah
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
            buttons[1][1].getText().equals(buttons[2][0].getText()) &&
            !buttons[0][2].getText().equals("")) {
            
            // Highlight diagonal pemenang
            buttons[0][2].setBackground(new Color(0, 255, 0));
            buttons[1][1].setBackground(new Color(0, 255, 0));
            buttons[2][0].setBackground(new Color(0, 255, 0));
            gameOver(buttons[0][2].getText());
            return;
        }
        
        // Periksa permainan seri
        if (isBoardFull()) {
            gameOver(null);
        }
    }
    
    private void gameOver(String winner) {
        gameOver = true;
        
        if (winner != null) {
            textLabel.setText(winner + " Menang!");
        } else {
            textLabel.setText("Permainan Seri!");
        }
        
        // Nonaktifkan semua tombol
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                buttons[r][c].setEnabled(false);
            }
        }
        
        // Reset otomatis setelah 3 detik
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private boolean isBoardFull() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (buttons[r][c].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void resetGame() {
        gameOver = false;
        currentPlayer = playerX;
        textLabel.setText("Giliran " + currentPlayer);
        
        // Reset semua tombol
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                buttons[r][c].setText("");
                buttons[r][c].setBackground(new Color(50, 50, 50));
                buttons[r][c].setEnabled(true);
            }
        }
    }
}