package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;

public class playerMainMenu  {

    @FXML
    private Label lblChooseMusic;
    private MediaPlayer mediaPlayer;

    @FXML
    private ProgressBar musicPrograss;

    @FXML
    void chooseMusic(MouseEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select your music");
        File file = chooser.showOpenDialog(null);
        if(file!=null){
            String selectedFile = file.toURI().toString();
            Media media = new Media(selectedFile);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(()->{
                lblChooseMusic.setText(file.getName());
            });
            updateProgressBar();
        }



    }

    @FXML
    void pause(MouseEvent event) {
        mediaPlayer.pause();
    }

    @FXML
    void play(MouseEvent event) {
        mediaPlayer.play();
    }

    @FXML
    void stop(MouseEvent event) {
        mediaPlayer.stop();
        musicPrograss.setProgress(0);
    }
    private void updateProgressBar() {
        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            Duration currentTime = newValue;
            Duration totalDuration = mediaPlayer.getTotalDuration();

            if(totalDuration != null && totalDuration.toMillis() > 0) {
                double progress = currentTime.toMillis() / totalDuration.toMillis();
                musicPrograss.setProgress(progress);
            }
        });

        // Reset progress bar when song ends
        mediaPlayer.setOnEndOfMedia(() -> {
            musicPrograss.setProgress(0);
        });
    }
}
