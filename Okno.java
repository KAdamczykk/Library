package mini;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Okno extends JFrame {
    protected String path = "biblioteka.bib";
    List<Ksiazka> list = new ArrayList<>();
    public Okno(){
        this.setVisible(true);
        this.setTitle("Biblioteka");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocation(600,200);
        File file = new File(path);
        if (file.exists() && file.isFile()){
            try {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                list = (List<Ksiazka>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            file = new File("bibdefault.txt");
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String s;
                while ((s = reader.readLine()) != null){
                    String[] splits = s.split(";");
                    Ksiazka ksiazka = new Ksiazka(splits[1], splits[0], splits[2]);
                    list.add(ksiazka);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(list, Comparator.comparing(Ksiazka::getAutor));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS) );

        for (int i = 0; i < list.size(); i++){
            Ksiazka ksiazka = list.get(i);
            JCheckBox checkBox = new JCheckBox();
            checkBox.setText(ksiazka.toString());
            if (ksiazka.wypozyczona){
                checkBox.setSelected(true);
            }
            checkBox.addChangeListener( e -> {
                if (checkBox.isSelected()){
                    ksiazka.wypozyczona = true;
                } else {
                    ksiazka.wypozyczona = false;
                }
            });
            panel.add(checkBox);
        }
        panel.add(addNewBookButton()); // to niedokonczone

        panel.add(addSerializableButton());

        this.getContentPane().add(panel);
        this.pack();


    }
    private JButton addSerializableButton(){
        JButton button = new JButton("Zapisz i wyjdz");
        button.addActionListener(e -> {
            this.dispose();
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
                out.writeObject(list);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        return button;
    }
    private JButton addNewBookButton(){
        JButton button = new JButton();
        button.setText("Dodaj ksiazke");
        button.addActionListener(e -> {
            JPanel panel = new JPanel();
            panel.add(new JLabel("Autor:"));
            JTextField autorField = new JTextField(10);
            panel.add(autorField);
            panel.add(new JLabel("Tytul:"));
            JTextField titleField = new JTextField(10);
            panel.add(titleField);
            panel.add(new JLabel("Rok:"));
            JTextField yearField = new JTextField(10);
            panel.add(yearField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Wprowadź dane", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String autor = autorField.getText();
                String title = titleField.getText();
                String year = yearField.getText();
                Ksiazka ksiazka = new Ksiazka(autor, title, year);
                list.add(ksiazka);
            }
        });
        return button;
    }


}
